import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.sql.*;
import java.lang.StringBuilder;
import java.awt.*;
import javax.swing.border.Border;

public class GUIPrincipal extends JFrame{
	
	private JTextField mensagemTF;
	private JTextArea chatTA;
	private JButton enviarB;
	private JFrame parent;
	private Cliente cliente;
	private boolean threadRunning;
	private static final int threadSleep = 2000;
	
	public GUIPrincipal(JFrame parent, String titulo,Cliente cliente){
		
		// Invoca o construtor do Pai.
		super(titulo);
		
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
		
		// Realiza a Atualização das Mensagens.
		AtualizarMensagens();
	}
	
	private void construirMenu(){
		
		// Cria uma barra de menu para o JFrame
        JMenuBar menuBar = new JMenuBar();

		// Seta o MenuBar para o JFrame.
		setJMenuBar(menuBar);
		
		// Define e adiciona um Menu de DropDown.
		JMenu fileMenu = new JMenu("Arquivo");
		
		// Adiciona esse SubMenu.
		menuBar.add(fileMenu);
		
		// Cria e adiciona um item simples para o menu
        JMenuItem logoutAction = new JMenuItem("Logout");
		
		// Cria uma Classe Anônima para adicionar um Listener para a opção de Logout.
		logoutAction.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				parent.setVisible(true);
				close();
			}
			
		});
		
		JMenuItem closeAction = new JMenuItem("Sair e Fechar");
		
		// Cria uma Classe Anônima para adicionar um Listener para a opção de Sair.
		closeAction.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				System.exit(1);
			}
		});
		
		fileMenu.add(logoutAction);
		fileMenu.add(closeAction);
		
		// Caso o Privilégio seja Administrativo cria o Painel Administrativo para o Usuário.
		if (cliente.getPrivilegio().compareTo("Administrador") == 0){
			
			// Inicializa um Objeto Administrador, que é um Cliente.
			// Note que aqui, o mesmo cliente será duas coisas: Cliente Comum e Administrador ao mesmo tempo.
			Administrador admin = new Administrador(cliente);
			
			// Define e adiciona um Menu de DropDown.
			JMenu adminMenu = new JMenu("Painel Administrativo");
			
			// Adiciona esse SubMenu.
			menuBar.add(adminMenu);
			
			// Cria e adiciona um item simples para o menu
			JMenuItem limparAction = new JMenuItem("Limpar Chat");
			JMenuItem limparuAction = new JMenuItem("Apagar Ultima Mensagem");
			
			// Adiciona os SubMenus na Categoria do Menu Principal "Painel Administrativo".
			adminMenu.add(limparAction);
			adminMenu.add(limparuAction);
			
			// Cria uma Classe Anônima para adicionar um Listener para a opção de Limpar CHAT.
			limparAction.addActionListener(new ActionListener(){
				
				@Override
				public void actionPerformed(ActionEvent event){
					
					try{
						int rowsAffected = admin.clearChat(admin.CLEAR_ALL);
						// Mostra a Mensagem de Sucesso.
						JOptionPane.showMessageDialog(GUIPrincipal.this, "Mensagem(s) Apagada(s) com Sucesso!\n\n" + Integer.toString(rowsAffected) + " Mensagens Apagadas!","Sucesso",JOptionPane.INFORMATION_MESSAGE);
					}catch(SQLException SQL_e){
						JOptionPane.showMessageDialog(GUIPrincipal.this, "Ocorreu um Error ao Apagar! Tente Novamente!\n" + SQL_e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
					}catch (Exception e){
						JOptionPane.showMessageDialog(GUIPrincipal.this, "Ocorreu um Error ao Apagar! Tente Novamente!\n" + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			
			// Cria uma Classe Anônima para adicionar um Listener para a opção de Apagar Última Mensagem.
			limparuAction.addActionListener(new ActionListener(){
				
				@Override
				public void actionPerformed(ActionEvent event){
					
					try{
						int rowsAffected = admin.clearChat(admin.CLEAR_LAST);
						// Mostra a Mensagem de Sucesso.
						JOptionPane.showMessageDialog(GUIPrincipal.this, "Mensagem(s) Apagada(s) com Sucesso!\n\n" + Integer.toString(rowsAffected) + " Mensagens Apagadas!","Sucesso",JOptionPane.INFORMATION_MESSAGE);
					}catch(SQLException SQL_e){
						JOptionPane.showMessageDialog(GUIPrincipal.this, "Ocorreu um Error ao Apagar! Tente Novamente!\n" + SQL_e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
					}catch (Exception e){
						JOptionPane.showMessageDialog(GUIPrincipal.this, "Ocorreu um Error ao Apagar! Tente Novamente!\n" + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
	}
	
	private void construirLayout(){
		
		// Invoca o Método para Construção e Inicialização do Menu.
		construirMenu();
		
		Cor c = new Cor();
		
		// Painel dos EditTexts e Labels.
		JPanel painel = new JPanel();
		painel.setBackground(c.getCor(1));
		
		// Associa o Layout BoxLayout ao JPanel
		painel.setLayout(new BoxLayout(painel,BoxLayout.Y_AXIS));
		
		// Painel dos Botões.
		JPanel painel2 = new JPanel();
		painel2.setBackground(c.getCor(1));
		
		// Instancia um Novo TextArea para o Chat ser mostrado.
		chatTA = new JTextArea(19,0);
		// Define a cor de fundo do TextArea.
		chatTA.setBackground(c.getCor(2));
		
		// Habilita a Quebra automática de linha.
		chatTA.setLineWrap(true);
		
		// Faz com que o ScrollBar sempre acompanhe os textos.
		DefaultCaret caret = (DefaultCaret) chatTA.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		// Desabilita o Recurso de Edição do TextArea.
		chatTA.setEditable(false);
		
		// Cria um ScrollBar para o TextArea.
		JScrollPane scrollChat = new JScrollPane (chatTA,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollChat.setBackground(c.getCor(3));
		
		// Deixar o TextArea Visivel.
        scrollChat.setOpaque(false);
		
		// Instancia um Novo TextField para a Mensagem.
		mensagemTF = new JTextField("",25);

		
		// Instancia o Botão de Login.
		enviarB = new JButton("Enviar");
		enviarB.setForeground(c.getCor(1));
		enviarB.setBackground(c.getCor(2));
		
		// Cria uma Classe Anônima para adicionar um Listener para o Botão.
		enviarB.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				EnviarClick();
			}
			
		});
		
		// Cria uma Classe Anônima para adicionar um Listener para Enter no TextField de Mensagem.
		mensagemTF.addActionListener(new ActionListener(){
			
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

		// Coloca o Foco no EditText de Mensagem.
		mensagemTF.requestFocus();
	}

	// Evento de Clique do Botão ENVIAR.
	private void EnviarClick(){
		
		// Tratamento de ERROR Básico.
		if (mensagemTF.getText().compareTo("") == 0)
			JOptionPane.showMessageDialog(this, "Escreva alguma Mensagem para poder Enviar!","Error",JOptionPane.ERROR_MESSAGE);
		else{
			
			enviarB.setEnabled(false);
			mensagemTF.setEnabled(false);
			
			// Instancia uma Classe Anônima de Thread
			Thread thread = new Thread(){

				@Override
				public void run(){
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
						
						// JOptionPane.showMessageDialog(this, "Mensagem Enviada com Sucesso!","Sucesso",JOptionPane.INFORMATION_MESSAGE);
						
					}catch(SQLException SQL_e){
						JOptionPane.showMessageDialog(GUIPrincipal.this, "Ocorreu um Error ao Enviar! Tente Novamente!\n" + SQL_e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
					}catch (Exception e){
						JOptionPane.showMessageDialog(GUIPrincipal.this, "Ocorreu um Error ao Enviar! Tente Novamente!\n" + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
					}
					
					enviarB.setEnabled(true);
					mensagemTF.setEnabled(true);
					mensagemTF.requestFocus();
				}
			};
			
			thread.start();
		}
	}		
	
	private void AtualizarMensagens(){
		
		// Instancia uma Classe Anônima de Thread
		Thread thread = new Thread(){
			
			@Override
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
							JOptionPane.showMessageDialog(GUIPrincipal.this, "Ocorreu um Error ao Atualizar o Chat!\n" + SQL_e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
						}catch (Exception e){
							JOptionPane.showMessageDialog(GUIPrincipal.this, "Ocorreu um Error ao Atualizar o Chat!\n" + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
						}
						
						// Faz a Thread atualizar a cada 5 segundos. ( 5000 ms )
						sleep(threadSleep);						
					}
				}catch (InterruptedException i_e){
					JOptionPane.showMessageDialog(GUIPrincipal.this, "Ocorreu um Error Fatal ao Atualizar o Chat! Reinicie para Voltar a Funcionar\n" + i_e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
					threadRunning = false;
				}
			}
		};
		
		// Inicia a Thread.
		thread.start();
	}
	
	public void close(){
		threadRunning = false;
		dispose();
	}
}