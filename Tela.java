import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.sql.*;

public class Tela extends JFrame{
	
	private JTextField usuarioTF,senhaTF;
	private JButton logarB,cadastrarB;
	
	public Tela(String titulo){
		
		super(titulo);
		
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
		
		// Instancia um Novo TextField para o Usuário.
		usuarioTF = new JTextField("",5);

		// Instancia um Novo TextField para a Senha.
		senhaTF = new JTextField("",5);
		
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
		painel2.add(logarB);
		painel2.add(cadastrarB);
	
		add(painel,BorderLayout.NORTH);
		add(painel2,BorderLayout.CENTER);
		
	}

	private void LoginClick(){
		
		if (usuarioTF.getText().compareTo("") == 0 || senhaTF.getText().compareTo("") == 0)
			JOptionPane.showMessageDialog(this, "Usuario ou Senha Invalidos!","Error",JOptionPane.ERROR_MESSAGE);
		else{
		
			jdbc conn = new jdbc(jdbc.MySQL);
		
			conn.connect();
		
			String Query = "Select * FROM Usuarios WHERE Usuario = iginho";
			
			try{
				ResultSet result = conn.runQuery(Query);
				
				if (!result.next())
					JOptionPane.showMessageDialog(this, "Usuario ou Senha Invalidos!","Error",JOptionPane.ERROR_MESSAGE);
				else{
					
					Cliente cliente = new Cliente(result.getString("Usuario"),result.getString("Nome"));
					
					System.out.println(cliente.getNome());
					
					conn.close();
				}
				
			}catch(SQLException SQL_e){
				JOptionPane.showMessageDialog(this, "Ocorreu um Error ao Logar! Tente Novamente!\n" + SQL_e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
				JOptionPane.showMessageDialog(this, usuarioTF.getText(),"Error",JOptionPane.ERROR_MESSAGE);
			}
		}		
	}
	
	private void CadastrarClick(){
		System.out.println("Cadastrar Clicado!");
	}
	
	public void show(Boolean show){
		setVisible(show);
	}
}