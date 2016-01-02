import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.sql.*;
import java.lang.StringBuilder;

public class GUIPrincipal extends JFrame{
	
	private JTextField mensagemTF;
	private JTextArea chatTA;
	private JButton enviarB;
	private JFrame parent,me;
	private Cliente cliente;
	private boolean threadRunning;
	private static final int threadSleep = 5000;
	
	public GUIPrincipal(JFrame parent, String titulo,Cliente cliente){
		
		super(titulo);
		
		// Guarda a Referência deste JFrame.
		this.me = this;
		
		// Guarda a Referência do JFrame Pai.
		this.parent = parent;
		
		// Inicializa a Variável local com os dados do Cliente.
		this.cliente = cliente;
		
		// Define o estado da Thread Inicial para true.
		threadRunning = true;
		
		// Configurações da Tela.
		setSize(400,400);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout(20,20));
		
		// Centraliza a Tela em Relação ao Monitor quando ficar Visível.
		setLocationRelativeTo(null);
		
		// Constrói o Layout da Tela.
		construirLayout();
		
		AtualizarMensagens();
	}
	
	private void construirLayout(){
		
		// Painel dos EditTexts e Labels.
		JPanel painel = new JPanel();
		
		// Associa o Layout BoxLayout ao JPanel
		painel.setLayout(new BoxLayout(painel,BoxLayout.Y_AXIS));
		
		// Painel dos Botões.
		JPanel painel2 = new JPanel();
		
		// Instancia um Novo TextArea para o Chat ser mostrado.
		chatTA = new JTextArea("",100,100);
		
		// Desabilita o Recurso de Edição do TextArea.
		chatTA.setEditable(false);
		
		// Cria um ScrollBar para o TextArea.
		JScrollPane scrollChat = new JScrollPane (chatTA,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		// Deixar o TextArea Visivel.
		scrollChat.getViewport().setOpaque(false);
        scrollChat.setOpaque(false);
		
		// Instancia um Novo TextField para a Mensagem.
		mensagemTF = new JTextField("",25);
		
		// Instancia o Botão de Login.
		enviarB = new JButton("Enviar");
		
		
		// Cria uma Classe Anônima para adicionar um Listener para o Botão.
		enviarB.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				EnviarClick();
			}
			
		});
		
		// Adiciona os Objetos aos Respectivos Painéis.
		
		painel2.add(mensagemTF);
		painel2.add(enviarB);
		
		painel.add(scrollChat);
		painel.add(painel2);
	
		add(painel,BorderLayout.CENTER);		
	}

	// Evento de Clique do Botão ENVIAR.
	private void EnviarClick(){
		
		// Tratamento de ERROR Básico.
		if (mensagemTF.getText().compareTo("") == 0)
			JOptionPane.showMessageDialog(this, "Escreva alguma Mensagem para poder Enviar!","Error",JOptionPane.ERROR_MESSAGE);
		else{
			
			enviarB.setEnabled(false);
			
			// Tenta Rodar a QUERY se não estoura uma Exceção.
			try{
				
				jdbc conn = new jdbc(jdbc.MySQL);
		
				// Abre uma Conexão com o Banco de Dados.
				conn.connect();
				
				// Formata a Mensagem do EditText para Evitar Problemas na QUERY.
				String formatedMessage = "'" + mensagemTF.getText() + "'";
				
				// Formata o Usuário do EditText para Evitar Problemas na QUERY.
				String formatedUser = "'" + cliente.getNome() + "'";
				
				// Formata o Canal do EditText para Evitar Problemas na QUERY.
				String formatedCanal = "'" + cliente.getCanal() + "'";
			
				// Monta a Operação da QUERY.
				String Query = "Insert INTO Chat (Nome,Mensagem,Canal) VALUES (" + formatedUser + "," + formatedMessage + "," + formatedCanal + ")";
				
				// Executa a Query e Retorna um ResultSet contendo seu Resultado.
				int rowsChanged = conn.updateQuery(Query);
				
				mensagemTF.setText("");
				
				JOptionPane.showMessageDialog(this, "Mensagem Enviada com Sucesso!","Sucesso",JOptionPane.INFORMATION_MESSAGE);
				
			}catch(SQLException SQL_e){
				JOptionPane.showMessageDialog(this, "Ocorreu um Error ao Enviar! Tente Novamente!\n" + SQL_e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			}catch (Exception e){
				JOptionPane.showMessageDialog(this, "Ocorreu um Error ao Enviar! Tente Novamente!\n" + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		enviarB.setEnabled(true);
	}		
	
	private void AtualizarMensagens(){
		
		// Instancia uma Nova Thread
		Thread thread = new Thread(){
			
			// Implementa seu Método Run.
			public void run(){	
			
				// Caso a THREAD estoure Exceção, encerra tudo.
				try{
					while (threadRunning){		
						// Tenta Rodar a QUERY se não estoura uma Exceção.
						try{
							
							jdbc conn = new jdbc(jdbc.MySQL);
					
							// Abre uma Conexão com o Banco de Dados.
							conn.connect();
							
							// Formata o Canal do EditText para Evitar Problemas na QUERY.
							String formatedCanal = "'" + cliente.getCanal() + "'";
						
							// Monta a Operação da QUERY.
							String Query = "Select * FROM Chat WHERE Canal=" + formatedCanal;
							
							// Executa a Query e Retorna um ResultSet contendo seu Resultado.
							ResultSet result = conn.selectQuery(Query);
							
							// Instancia um StringBuilder, será usado para dar Append nas Mensagens.
							StringBuilder mensagemChat = new StringBuilder();
							
							// Faz uma Iteração dentre todos os Items recebidos da Tabela Chat.
							while(result.next()){
								// Pega o Nome da Linha Atual da Tabela.
								String Nome = result.getString("Nome");
								
								// Pega a Mensagem da Linha Atual da Tabela.
								String Mensagem = result.getString("Mensagem");
								
								// Realiza o Append das Mensagens para ser Exibido no EditArea.
								mensagemChat.append(Nome + " : " + Mensagem + "\n\n");
							}
							
							// Só atualiza caso a Mensagem do Servidor e a do Cliente estejam fora de Sincronização
							if (mensagemChat.toString().compareTo(chatTA.getText()) != 0)
								chatTA.setText(mensagemChat.toString());
								
						}catch(SQLException SQL_e){
							JOptionPane.showMessageDialog(me, "Ocorreu um Error ao Atualizar o Chat!\n" + SQL_e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
						}catch (Exception e){
							JOptionPane.showMessageDialog(me, "Ocorreu um Error ao Atualizar o Chat!\n" + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
						}
						
						// Faz a Thread atualizar a cada 5 segundos. ( 5000 ms )
						sleep(threadSleep);						
					}
				}catch (InterruptedException i_e){
					JOptionPane.showMessageDialog(me, "Ocorreu um Error Fatal ao Atualizar o Chat! Reinicie para Voltar a Funcionar\n" + i_e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
					threadRunning = false;
				}
			}
		};
		
		// Inicia a Thread.
		thread.start();
	}
	
	public void show(Boolean show){
		setVisible(show);
	}
	
	public void close(){
		dispose();
	}
}