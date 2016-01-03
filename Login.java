import javax.swing.*;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.sql.*;
import java.awt.GridLayout;

public class Login extends JFrame{
	
	private JTextField usuarioTF,senhaTF,canalTF;
	private JButton logarB,cadastrarB;
	private JFrame nextFrame;
	
	public Login(String titulo){

		super(titulo);		
		
		setSize(350,250);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout(0,0));
		
		// Centraliza a Tela em Relação ao Monitor quando ficar Visível.
		setLocationRelativeTo(null);
		
		// Constrói o Layout da Tela.
		construirLayout();
		
		// Remove todos os Espaços vázios e encolhe a Aplicação.
		// pack();
	}
	
	private void construirLayout(){
		
		Color fundoAzul = new Color(21,48,93);
		Color fundoVerde = new Color(96,178,164);
		Color fundoBranco = new Color(204,213,217);
		
		
		//Painel da Logo
		JPanel topo = new JPanel();
		topo.setBackground(fundoAzul);
		
		JLabel logo = new JLabel();
		ImageIcon imgLogo = new ImageIcon("imagem/logo.png");
		
		logo.setIcon(imgLogo);
		logo.setAlignmentX(Component.CENTER_ALIGNMENT);
		topo.add(logo);
		
			
		// Painel dos EditTexts e Labels.
		JPanel painel = new JPanel();
		
		
		// Associa o Layout BoxLayout ao JPanel

		//painel.setLayout(new BoxLayout(painel,BoxLayout.Y_AXIS));
		painel.setLayout(null);
		painel.setBackground(fundoAzul);
		

		// Painel dos Botões.
		JPanel painel2 = new JPanel();
		painel2.setBackground(fundoAzul);
		
		// Instancia um Novo TextField para o Usuário.
		usuarioTF = new JTextField("Usuario",15);
		Dimension sizeTxt = usuarioTF.getPreferredSize();
		usuarioTF.setBounds(85,15,sizeTxt.width,sizeTxt.height);
		
		// Cria uma Classe Anônima para adicionar um Listener para o Campo Usuario.
		usuarioTF.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                usuarioTF.setText("");
            }
        });

		// Instancia um Novo TextField para a Senha.
		senhaTF = new JTextField("Senha",15);
		Dimension sizeTxt2 = senhaTF.getPreferredSize();
		senhaTF.setBounds(85,45,sizeTxt2.width,sizeTxt2.height);
		
		// Cria uma Classe Anônima para adicionar um Listener para o Campo Senha.
		senhaTF.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                senhaTF.setText("");
            }
        });
		
		// Instancia um Novo TextField para a Canal.
		canalTF = new JTextField("Global",15);
		Dimension sizeTxt3 = canalTF.getPreferredSize();
		canalTF.setBounds(85,75,sizeTxt3.width,sizeTxt3.height);
		
		// Cria uma Classe Anônima para adicionar um Listener para o Campo Canal.
		canalTF.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                canalTF.setText("");
            }
        });
				
		// Instancia o Botão de Login.
		logarB = new JButton("Login");
		logarB.setForeground(fundoAzul);
		logarB.setBackground(fundoBranco);
		
		// Cria uma Classe Anônima para adicionar um Listener para o Botão.
		logarB.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				LoginClick();
			}
			
		});
		
		// Instancia o Botão de Cadastro
		cadastrarB = new JButton("Cadastrar");
		cadastrarB.setForeground(fundoAzul);
		cadastrarB.setBackground(fundoBranco);
		
		// Cria uma Classe Anônima para adicionar um Listener para o Botão.
		cadastrarB.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				CadastrarClick();
			}
			
		});
		
		// Adiciona os Objetos aos Respectivos Painéis.

		painel.add(usuarioTF);
		painel.add(senhaTF);
		painel.add(canalTF);
		painel2.add(logarB);
		painel2.add(cadastrarB);
	
		add(topo,BorderLayout.NORTH);
		add(painel,BorderLayout.CENTER);
		add(painel2,BorderLayout.SOUTH);
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
						nextFrame.setVisible(true);
						
						// Encerra a conexão com o Banco de Dados.
						conn.close();
						
						// Deixa o JFrame atual Invisível.
						setVisible(false);
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
		setVisible(false);
		
		Cadastro cadastro = new Cadastro("Tela de Cadastro",this);
		
		cadastro.setVisible(true);
	}
	
	public void close(){
		dispose();
	}
	
	public JFrame getNextFrame() throws NullPointerException{
		return nextFrame;
	}
}