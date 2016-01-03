import javax.swing.*;
import java.sql.*;

public class Administrador extends Cliente{
	
	public static int CLEAR_ALL = 0;
	public static int CLEAR_LAST = 1;
	
	public Administrador(Cliente cliente){
		// Invoca o construtor do Pai.
		super(cliente.getUsuario(),cliente.getNome(),cliente.getPrivilegio(),cliente.getCanal());
	}
	
	// Retorna Quantas Mensagens foram Apagadas.
	public int clearChat(int option) throws SQLException{
		
		// Instancia um Objeto de JDBC.
		jdbc conn = new jdbc(jdbc.MySQL);

		// Abre uma Conexão com o Banco de Dados.
		conn.connect();
		
		// Formata o Canal do EditText para Evitar Problemas na QUERY.
		String formatedCanal = "'" + getCanal() + "'";
	
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
		
		return rowsChanged;
	}
}