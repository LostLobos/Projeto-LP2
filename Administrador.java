import java.awt.event.*;
import java.sql.*;
import java.lang.StringBuilder;

public class Administrador{
	
	public static int CLEAR_ALL = 0;
	public static int CLEAR_LAST = 1;

	private Cliente cliente;
	private JFrame parent;
	
	public Administrador(Cliente cliente,JFrame parent){
		this.cliente = cliente;
		this.parent = parent;
	}
	
	public void clearChat(int option){
		
		// Tenta Rodar a QUERY se não estoura uma Exceção.
		try{
			
			jdbc conn = new jdbc(jdbc.MySQL);
	
			// Abre uma Conexão com o Banco de Dados.
			conn.connect();
			
			// Formata o Canal do EditText para Evitar Problemas na QUERY.
			String formatedCanal = "'" + cliente.getCanal() + "'";
		
			// Monta a Operação da QUERY.
			String Query = "DELETE FROM Chat WHERE Canal=" + formatedCanal;
			
			// Executa a Query e Retorna um ResultSet contendo seu Resultado.
			int rowsChanged = conn.updateQuery(Query);
			
			JOptionPane.showMessageDialog(parent, "Mensagem(s) Apagada(s) com Sucesso!\n\n" + Integer.toString(rowsChanged) + " Mensagens Apagadas!","Sucesso",JOptionPane.INFORMATION_MESSAGE);
			
		}catch(SQLException SQL_e){
			JOptionPane.showMessageDialog(parent, "Ocorreu um Error ao Apagar! Tente Novamente!\n" + SQL_e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}catch (Exception e){
			JOptionPane.showMessageDialog(parent, "Ocorreu um Error ao Apagar! Tente Novamente!\n" + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}
}