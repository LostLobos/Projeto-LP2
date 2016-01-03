import java.util.*;
import java.awt.*;

public class Cor {
	
	private Color fundoAzul = new Color(21,48,93);
	private Color fundoVerde = new Color(96,178,164);
	private Color fundoBranco = new Color(204,213,217);
	
	public Cor(){
		
	}
	


public Color getCor(int c){
		switch(c){
			case 1:
				return fundoAzul;
			case 2:
				return fundoBranco;
			case 3:
				return fundoVerde;
			default:
				return Color.BLACK;	
		}
	
	}

}