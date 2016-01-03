import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;



public class Cadastro extends JFrame{
	
	private JTextField usuarioTF,nomeTF,senhaTF;
	private JButton enviarB,voltarB;
	private JFrame previousFrame;
	
	public Cadastro(String titulo,JFrame previousFrame){
		
		super(titulo);
		
		this.previousFrame = previousFrame;
		
		setSize(400,358);
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
		
		Cor c = new Cor ();
		
		// Painel da imagem 
		JPanel topo = new JPanel();
		topo.setBackground(c.getCor(1));
		
		//Imagem Cadastro
		JLabel iconCadastro = new JLabel();
		ImageIcon img = new ImageIcon("imagem/cadastro.png");
		iconCadastro.setIcon(img);
		iconCadastro.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// Painel dos EditTexts e Labels.
		JPanel painel = new JPanel();
		painel.setBackground(c.getCor(1));
		
		// Associa o Layout BoxLayout ao JPanel
		painel.setLayout(null);
		
		// Painel dos Botões.
		JPanel painel2 = new JPanel();
		painel2.setBackground(c.getCor(2));
		
		// Instancia um Novo TextField para o Usuário.
		usuarioTF = new JTextField("",15);
		Dimension sizeUsarioTF = usuarioTF.getPreferredSize();
		usuarioTF.setBounds(110,55,sizeUsarioTF.width,sizeUsarioTF.height);
		
		//Instancia uma Nova label para o Usuário
		JLabel usuarioLB = new JLabel();
		usuarioLB.setText("Usuario");
		Dimension sizeUsuarioLB = usuarioLB.getPreferredSize();
		usuarioLB.setBounds(110,35,sizeUsuarioLB.width,sizeUsarioTF.height);
		usuarioLB.setForeground(c.getCor(2));
		
		// Instancia um Novo TextField para o Nome.
		nomeTF = new JTextField("",15);
		Dimension sizeNomeTF = nomeTF.getPreferredSize();
		nomeTF.setBounds(110,105,sizeNomeTF.width,sizeNomeTF.height);
		
		//Instancia uma Nova label para o Usuário
		JLabel nomeLB = new JLabel();
		nomeLB.setText("Nome");
		Dimension sizeNomeLB = nomeLB.getPreferredSize();
		nomeLB.setBounds(110,85,sizeNomeLB.width,sizeNomeLB.height);
		nomeLB.setForeground(c.getCor(2));

		// Instancia um Novo TextField para a Senha.
		senhaTF = new JPasswordField("",15);
		Dimension sizeSenhaTF = senhaTF.getPreferredSize();
		senhaTF.setBounds(110,155,sizeSenhaTF.width,sizeSenhaTF.height);
		
		//Instancia uma nova Label para a senha
		JLabel senhaLB = new JLabel();
		senhaLB.setText("Senha");
		Dimension sizeSenhaLB = senhaLB.getPreferredSize();
		senhaLB.setBounds(110,135,sizeSenhaLB.width,sizeSenhaLB.height);
		senhaLB.setForeground(c.getCor(2));
		
		// Instancia o Botão de Cadastro.
		enviarB = new JButton("Cadastrar");
		enviarB.setForeground(c.getCor(2));
		enviarB.setBackground(c.getCor(1));
		
		// Cria uma Classe Anônima para adicionar um Listener para o Botão.
		enviarB.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				EnviarClick();
			}
			
		});
		
		// Instancia o Botão de Cadastro.
		voltarB = new JButton("Voltar");
		voltarB.setForeground(c.getCor(2));
		voltarB.setBackground(c.getCor(1));
		
		// Cria uma Classe Anônima para adicionar um Listener para o Botão.
		voltarB.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				VoltarClick();
			}
			
		});
		
		// Adiciona os Objetos aos Respectivos Painéis.
		
		topo.add(iconCadastro);
		painel.add(usuarioLB);
		painel.add(usuarioTF);
		painel.add(nomeLB);
		painel.add(nomeTF);
		painel.add(senhaLB);
		painel.add(senhaTF);
		painel2.add(enviarB);
		painel2.add(voltarB);
	
		add(topo,BorderLayout.NORTH);
		add(painel,BorderLayout.CENTER);
		add(painel2,BorderLayout.SOUTH);
		
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
					
					// Invoca o Método que retorna pra Tela de Login.
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
		previousFrame.setVisible(true);
		close();
	}
	
	public void close(){
		dispose();
	}
	
	public JFrame getPreviousFrame() throws NullPointerException{
		return previousFrame;
	}
}