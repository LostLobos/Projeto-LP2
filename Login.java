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

		// Invoca o construtor do Pai.
		super(titulo);		
		
		// Configurações da Tela.
		setSize(450,250);
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
		
		Cor c = new Cor();

		
		//Painel da Logo
		JPanel topo = new JPanel();
		topo.setBackground(c.getCor(1));
		
		//Inserindo propriedades da logo
		JLabel logo = new JLabel();
		ImageIcon imgLogo = new ImageIcon("imagem/logo.png");
		logo.setIcon(imgLogo);
		logo.setAlignmentX(Component.CENTER_ALIGNMENT);
		
			
		
			
		// Painel dos EditTexts e Labels.
		JPanel painel = new JPanel();
		
		
		// Associa o Layout BoxLayout ao JPanel
		
		//Propriedades do painel dos EditTexts e Labels
		painel.setLayout(null);
		painel.setBackground(c.getCor(1));
		

		// Painel dos Botões e suas propriedades
		JPanel painel2 = new JPanel();
		painel2.setBackground(c.getCor(1));
			
		
		
		// Instancia um Novo TextField para o Usuário
		usuarioTF = new JTextField("",15);
		Dimension sizeUsarioTF = usuarioTF.getPreferredSize();
		usuarioTF.setBounds(150,15,sizeUsarioTF.width,sizeUsarioTF.height);

		
		//Instancia uma Label para Usuário
		JLabel usuarioLB = new JLabel();
		usuarioLB.setText("Usuario");
		Dimension sizeUsuarioLB = usuarioLB.getPreferredSize();
		usuarioLB.setBounds(85,15,sizeUsuarioLB.width,sizeUsarioTF.height);
		usuarioLB.setForeground(c.getCor(2));
		
		
		// Instancia um Novo TextField para a Senha.
		senhaTF = new JPasswordField("",15);
		Dimension sizeSenhaTF = senhaTF.getPreferredSize();
		senhaTF.setBounds(150,45,sizeSenhaTF.width,sizeSenhaTF.height);
		
		//Intancia uma Label para a senha
		JLabel senhaLB = new JLabel();
		senhaLB.setText("Senha");
		Dimension sizeSenhaLB = senhaLB.getPreferredSize();
		senhaLB.setBounds(85,45,sizeSenhaLB.width,sizeSenhaLB.height);
		senhaLB.setForeground(c.getCor(2));
		

		
		// Instancia um Novo TextField para o Canal.
		canalTF = new JTextField("Global",15);
		Dimension sizeTxt3 = canalTF.getPreferredSize();
		canalTF.setBounds(150,75,sizeTxt3.width,sizeTxt3.height);
		
		//Intancia uma label para o Canal
		JLabel canalLB = new JLabel();
		canalLB.setText("Canal");
		Dimension sizeCanalLB = canalLB.getPreferredSize();
		canalLB.setBounds(85,75,sizeCanalLB.width,sizeCanalLB.height);
		canalLB.setForeground(c.getCor(2));
		
		// Cria uma Classe Anônima para adicionar um Listener para o Campo Canal.
		canalTF.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                canalTF.setText("");
            }
        });
		
		
		// Instancia o Botão de Login.
		logarB = new JButton("Logar");
		logarB.setForeground(c.getCor(1));
		logarB.setBackground(c.getCor(2));
		
		// Cria uma Classe Anônima para adicionar um Listener para o Botão.
		logarB.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				LoginClick();
			}
			
		});
		
		// Instancia o Botão de Cadastro
		cadastrarB = new JButton("Cadastrar");
		cadastrarB.setForeground(c.getCor(1));
		cadastrarB.setBackground(c.getCor(2));
		
		// Cria uma Classe Anônima para adicionar um Listener para o Botão.
		cadastrarB.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				CadastrarClick();
			}
			
		});
		
		// Adiciona os Objetos aos Respectivos Painéis.

		topo.add(logo);
		painel.add(usuarioTF);
		painel.add(usuarioLB);
		painel.add(senhaTF);
		painel.add(senhaLB);
		painel.add(canalTF);
		painel.add(canalLB);
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