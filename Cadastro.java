import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.sql.*;

public class Cadastro extends JFrame{
	
	private JTextField usuarioTF,nomeTF,senhaTF;
	private JButton enviarB,voltarB;
	private JFrame previousFrame;
	
	public Cadastro(String titulo,JFrame previousFrame){
		
		super(titulo);
		
		this.previousFrame = previousFrame;
		
		setSize(400,200);
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
		
		// Painel dos EditTexts e Labels.
		JPanel painel = new JPanel();
		
		// Associa o Layout BoxLayout ao JPanel
		painel.setLayout(new BoxLayout(painel,BoxLayout.Y_AXIS));
		
		// Painel dos Botões.
		JPanel painel2 = new JPanel();
		
		// Instancia um Novo TextField para o Usuário.
		usuarioTF = new JTextField("",5);
		
		// Instancia um Novo TextField para o Nome.
		nomeTF = new JTextField("",5);

		// Instancia um Novo TextField para a Senha.
		senhaTF = new JTextField("",5);
		
		// Instancia o Botão de Cadastro.
		enviarB = new JButton("Cadastrar");
		
		// Cria uma Classe Anônima para adicionar um Listener para o Botão.
		enviarB.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				EnviarClick();
			}
			
		});
		
		// Instancia o Botão de Cadastro.
		voltarB = new JButton("Voltar");
		
		// Cria uma Classe Anônima para adicionar um Listener para o Botão.
		voltarB.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				VoltarClick();
			}
			
		});
		
		// Adiciona os Objetos aos Respectivos Painéis.
		
		painel.add(new JLabel("Usuario"));
		painel.add(usuarioTF);
		painel.add(new JLabel("Nome"));
		painel.add(nomeTF);
		painel.add(new JLabel("Senha"));
		painel.add(senhaTF);
		painel2.add(enviarB);
		painel2.add(voltarB);
	
		add(painel,BorderLayout.NORTH);
		add(painel2,BorderLayout.CENTER);
		
	}

	// Evento de Clique do Botão CADASTRAR.
	private void EnviarClick(){
		
		// Tratamento de ERROR Básico.
		if (usuarioTF.getText().compareTo("") == 0 || senhaTF.getText().compareTo("") == 0 || nomeTF.getText().compareTo("") == 0)
			JOptionPane.showMessageDialog(this, "Preencha todos os Campos Corretamente!","Error",JOptionPane.ERROR_MESSAGE);
		else{
			// Tenta Rodar a QUERY se não estoura uma Exceção.
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
				
				// Caso o Cadastro já exista na Tabela exibe um ERROR na Tela.
				if (result.next())
					JOptionPane.showMessageDialog(this, "Usuario Existente!","Error",JOptionPane.ERROR_MESSAGE);
				else{
					
					// Formata o Nome do EditText para Evitar Problemas na QUERY.
					String formatedName = "'" + nomeTF.getText() + "'";
					
					// Formata a Senha do EditText para Evitar Problemas na QUERY.
					String formatedPass = "'" + senhaTF.getText() + "'";
					
					// Monta a Operação da QUERY.
					Query = "Insert INTO Usuarios (Usuario,Nome,Senha,Privilegio) VALUES (" + formatedUser + "," + formatedName + "," + formatedPass + ",'Usuario')";
					
					// Executa a Query e Retorna o Número de Linhas Afetadas pela Query ( No caso do Cadastro, apenas 1 ).
					int rowsChanged = conn.updateQuery(Query);
					
					JOptionPane.showMessageDialog(this, "Cadastro Efetuado com Sucesso!","Sucesso",JOptionPane.INFORMATION_MESSAGE);
					
					VoltarClick();
				}
				
			}catch(SQLException SQL_e){
				JOptionPane.showMessageDialog(this, "Ocorreu um Error ao Logar! Tente Novamente!\n" + SQL_e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			}catch (Exception e){
				JOptionPane.showMessageDialog(this, "Ocorreu um Error ao Logar! Tente Novamente!\n" + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			}
		}		
	}
	
	// Evento de Clique do Botão VOLTAR
	private void VoltarClick(){
		previousFrame.show(true);
		close();
	}
	
	public void show(Boolean show){
		setVisible(show);
	}
	
	public void close(){
		dispose();
	}
	
	public JFrame getPreviousFrame() throws NullPointerException{
		return previousFrame;
	}
}