import javax.swing.*;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.sql.*;


public class Login extends JFrame{
	
	private JTextField usuarioTF,senhaTF,canalTF;
	private JButton logarB,cadastrarB;
	private JFrame nextFrame;
	
	public Login(String titulo){
		
		super(titulo);
		
		setSize(500,350);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout(20,20));
		
		// Centraliza a Tela em Relação ao Monitor quando ficar Visível.
		setLocationRelativeTo(null);
		
		// Constrói o Layout da Tela.
		construirLayout();
		
		// Remove todos os Espaços vázios e encolhe a Aplicação.
		// pack();
	}
	
	private void construirLayout(){
		
		Color fundoRed = new Color(21,48,93);
		
		// Painel dos EditTexts e Labels.
		JPanel painel = new JPanel();
		
		// Associa o Layout BoxLayout ao JPanel
		painel.setLayout(new BoxLayout(painel,BoxLayout.Y_AXIS));
		painel.setBackground(fundoRed);
	
		// Painel dos Botões.
		JPanel painel2 = new JPanel();
		
		// Instancia um Novo TextField para o Usuário.
		usuarioTF = new JTextField("",5);

		// Instancia um Novo TextField para a Senha.
		senhaTF = new JTextField("",5);
		
		// Instancia um Novo TextField para a Canal.
		canalTF = new JTextField("Global",5);
		
		// Instancia o Botão de Login.
		logarB = new JButton("Login");
		
		// Cria uma Classe Anônima para adicionar um Listener para o Botão.
		logarB.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				LoginClick();
			}
			
		});
		
		// Instancia o Botão de Cadastro
		cadastrarB = new JButton("Cadastrar");
		
		// Cria uma Classe Anônima para adicionar um Listener para o Botão.
		cadastrarB.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				CadastrarClick();
			}
			
		});
		
		// Adiciona os Objetos aos Respectivos Painéis.
		
		painel.add(new JLabel("Usuario"));
		painel.add(usuarioTF);
		painel.add(new JLabel("Senha"));
		painel.add(senhaTF);
		painel.add(new JLabel("Canal"));
		painel.add(canalTF);
		painel2.add(logarB);
		painel2.add(cadastrarB);
	
		add(painel,BorderLayout.NORTH);
		add(painel2,BorderLayout.CENTER);
		
	}

	// Evento de Clique do Botão LOGIN.
	private void LoginClick(){
		
		// Tratamento de ERROR Básico.
		if (usuarioTF.getText().compareTo("") == 0 || senhaTF.getText().compareTo("") == 0)
			JOptionPane.showMessageDialog(this, "Usuario ou Senha Invalidos!","Error",JOptionPane.ERROR_MESSAGE);
		else if (canalTF.getText().isEmpty())
			JOptionPane.showMessageDialog(this, "Canal Vazio! Digite o Nome do Canal que deseja Entrar!\n\nEx: UERJ, Noticias, etc...","Error",JOptionPane.ERROR_MESSAGE);
		else{
			
			try{
				
				jdbc conn = new jdbc(jdbc.MySQL);
		
				// Abre uma Conexão com o Banco de Dados.
				conn.connect();
				
				// Formata o Usuário do EditText para Evitar Problemas na QUERY.
				String formatedUser = "'" + usuarioTF.getText() + "'";
			
				// Monta a Operação da QUERY.
				String Query = "Select * FROM Usuarios WHERE Usuario=" + formatedUser;
				
				// Executa a Query e Retorna um ResultSet contendo seu Resultado.
				ResultSet result = conn.selectQuery(Query);
				
				// Caso não exista Rows da Operação, exibe um ERROR na Tela.
				if (!result.next())
					JOptionPane.showMessageDialog(this, "Usuario ou Senha Invalidos!","Error",JOptionPane.ERROR_MESSAGE);
				else{
					
					// Compara a Senha Escrita com a do Banco de Dados.
					if (result.getString("Senha").compareTo(senhaTF.getText()) == 0){
					
						// Armazena o Resultado da Operação no Objeto Cliente para ser Usado posteriormente.
						Cliente cliente = new Cliente(result.getString("Usuario"),result.getString("Nome"),result.getString("Privilegio"),canalTF.getText());
						
						// Armazena a próxima Tela ao nextFrame.
						nextFrame = new GUIPrincipal(this,"CHAT - Canal: " + canalTF.getText(),cliente);
						
						// Seta a Visibilidade do JFrame seguinte para visível.
						nextFrame.show(true);
						
						// Encerra a conexão com o Banco de Dados.
						conn.close();
						
						// Deixa o JFrame atual Invisível.
						show(false);
					}
					else
						JOptionPane.showMessageDialog(this, "Usuario ou Senha Invalidos!","Error",JOptionPane.ERROR_MESSAGE);
				}
				
			}catch(SQLException SQL_e){
				JOptionPane.showMessageDialog(this, "Ocorreu um Error ao Logar! Tente Novamente!\n" + SQL_e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			}catch (Exception e){
				JOptionPane.showMessageDialog(this, "Ocorreu um Error ao Logar! Tente Novamente!\n" + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			}
		}		
	}
	
	private void CadastrarClick(){
		// Deixa o JFrame atual Invisível.
		show(false);
		
		Cadastro cadastro = new Cadastro("Tela de Cadastro",this);
		
		cadastro.show(true);
	}
	
	public void show(Boolean show){
		setVisible(show);
	}
	
	public void close(){
		dispose();
	}
	
	public JFrame getNextFrame() throws NullPointerException{
		return nextFrame;
	}
}