public class Cliente{

	private String usuario,nome,canal;

	public Cliente(){
	}
	
	public Cliente(String usuario,String nome, String canal){
		this.usuario = usuario;
		this.nome = nome;
		this.canal = canal;
	}
	
	public String getNome(){
		return nome;
	}
	
	public void setNome(String nome){
		this.nome = nome;
	}
	
	public String getUsuario(){
		return usuario;
	}
	
	public void setUsuario(String nome){
		this.nome = nome;
	}
	
	public void setCanal(String canal){
		this.canal = canal;
	}
	
	public String getCanal(){
		return canal;
	}
}