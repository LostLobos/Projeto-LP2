import java.util.*;
import javax.swing.*;

public class Main{
	
	public static void main(String Args[]){
		
		/*// Armazena a próxima Tela ao nextFrame.
		GUIPrincipal gui = new GUIPrincipal(null,"Menu Principal - CHAT",new Cliente("iginho","Igor Lessa Morse Alves"));
						
		// Seta a Visibilidade do JFrame seguinte para visível.
		gui.show(true);*/
		
		// Instanciar a Tela de Login.
		Login login = new Login("Login");
		
		// Seta sua Visibilidade para true.
		login.show(true);
	}
}