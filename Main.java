import java.util.*;

public class Main{
	
	public static void main(String Args[]){
		
		jdbc conn = new jdbc(jdbc.MySQL);
		
		conn.connect();
		
		if (conn.isConnected())
			System.out.println("Conectado");
		else
			System.out.println("Não Conectado");
		
		conn.close();
		
		if (conn.isConnected())
			System.out.println("Conectado");
		else
			System.out.println("Não Conectado");
		
		System.out.println("Hello World");
	}
}