import java.sql.*;

public class jdbc{
	
	private Connection connection;
	
	private static final String URL = "jdbc:mysql://db4free.net:3306/projetolp2?useSSL=false";
	private static final String USER = "projetolp2";
	private static final String PASS = "projetojbdc";
	
	//private static final String URL = "jdbc:mysql://sql5.freesqldatabase.com:3306/sql5100003?useSSL=false";
	//private static final String USER = "sql5100003";
	//private static final String PASS = "aEJtujANPc";
	
	public static String MySQL = "com.mysql.jdbc.Driver";
	
	public jdbc(String connType){
		
		try {
			Class.forName(connType);
		}
		catch(Exception ex) {
			System.out.println("Error: Não foi possível Carregar o Driver: " + ex.getMessage());
			System.exit(1);
		}
	}
	
	public void connect(){
		try{
			connection = DriverManager.getConnection(URL,USER,PASS);
		}catch (SQLException SQL_e){
			System.out.println("Error: Não foi possível Estabelecer uma Conexão! " + SQL_e.getMessage());
			System.exit(1);
		}
	}
	
	public void close(){
		try{
			connection.close();
		}catch (SQLException SQL_e){
			System.out.println("Error: Não foi possível Encerrar a Conexão! " + SQL_e.getMessage());
			System.exit(1);
		}
	}
	
	public ResultSet runQuery(String Query){
		
		try{
			Statement stmt = connection.createStatement();
			
			ResultSet rs = stmt.executeQuery(Query);
			
			return rs;
			
		}catch (SQLException SQL_e){
			return null;
		}
	}
	
	public boolean isConnected(){
		try{
			return !connection.isClosed();
		}catch(SQLException SQL_e){
			return false;
		}
	}
	
	public Connection getConnection(){
		return connection;
	}
}