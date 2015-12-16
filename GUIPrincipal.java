import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.sql.*;

public class GUIPrincipal extends JFrame{
	
	private JTextField mensagemTF;
	private JTextArea chatTA;
	private JButton enviarB;
	private JFrame parent;
	private Cliente cliente;
	
	public GUIPrincipal(JFrame parent, String titulo,Cliente cliente){
		
		super(titulo);
		
		this.parent = parent;
		this.cliente = cliente;
		
		setSize(400,400);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout(20,20));
		
		// Centraliza a Tela em Relação ao Monitor quando ficar Visível.
		setLocationRelativeTo(null);
		
		// Constrói o Layout da Tela.
		construirLayout();
		
		// pack();
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
		
		// Instancia um ScrollPane para o Chat.
		JScrollPane scrollPane = new JScrollPane(chatTA);
		
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
		
		painel.add(chatTA);
		painel.add(painel2);
	
		add(painel,BorderLayout.CENTER);		
	}

	// Evento de Clique do Botão ENVIAR.
	public void EnviarClick(){
		
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
				
				// Formata o Usuário do EditText para Evitar Problemas na QUERY.
				String formatedMessage = "'" + mensagemTF.getText() + "'";
				
				// Formata o Usuário do EditText para Evitar Problemas na QUERY.
				String formatedUser = "'" + cliente.getNome() + "'";
			
				// Monta a Operação da QUERY.
				String Query = "Insert INTO Chat (Nome,Mensagem) VALUES (" + formatedUser + "," + formatedMessage + ")";
				
				// Executa a Query e Retorna um ResultSet contendo seu Resultado.
				int rowsChanged = conn.updateQuery(Query);
				
				JOptionPane.showMessageDialog(this, "Mensagem Enviada com Sucesso!","Sucesso",JOptionPane.INFORMATION_MESSAGE);
				
			}catch(SQLException SQL_e){
				JOptionPane.showMessageDialog(this, "Ocorreu um Error ao Enviar! Tente Novamente!\n" + SQL_e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			}catch (Exception e){
				JOptionPane.showMessageDialog(this, "Ocorreu um Error ao Enviar! Tente Novamente!\n" + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		enviarB.setEnabled(true);
	}		
	
	public void CadastrarClick(){
		System.out.println("Cadastrar Clicado!");
	}
	
	public void show(Boolean show){
		setVisible(show);
	}
}