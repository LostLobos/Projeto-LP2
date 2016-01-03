import javax.swing.*;
import java.sql.*;

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
		
			String Query = "";
		
			// Verifica qual foi a forma de Apagar as Mensagens.
			if (option == 0){
				// Monta a Operação da QUERY para Deletar todas as Mensagens.
				Query = "DELETE FROM Chat WHERE Canal=" + formatedCanal;
			}
			else{
				// Monta a Operação da QUERY para Remover o Último item da categoria.
				Query = "DELETE FROM Chat WHERE Canal=" + formatedCanal + " ORDER BY id_mensagem DESC LIMIT 1";
			}
			
			// Executa a Query e Retorna um ResultSet contendo seu Resultado.
			int rowsChanged = conn.updateQuery(Query);
			
			// Mostra a Mensagem de Sucesso.
			JOptionPane.showMessageDialog(parent, "Mensagem(s) Apagada(s) com Sucesso!\n\n" + Integer.toString(rowsChanged) + " Mensagens Apagadas!","Sucesso",JOptionPane.INFORMATION_MESSAGE);
			
		}catch(SQLException SQL_e){
			JOptionPane.showMessageDialog(parent, "Ocorreu um Error ao Apagar! Tente Novamente!\n" + SQL_e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}catch (Exception e){
			JOptionPane.showMessageDialog(parent, "Ocorreu um Error ao Apagar! Tente Novamente!\n" + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}
}