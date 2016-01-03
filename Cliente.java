public class Cliente{

	private String usuario,nome,privilegio,canal;

	public Cliente(){
	}
	
	public Cliente(String usuario,String nome,String privilegio,String canal){
		this.usuario = usuario;
		this.nome = nome;
		this.privilegio = privilegio;
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
	
	public void setPrivilegio(String privilegio){
		this.privilegio = privilegio;
	}
	
	public String getPrivilegio(){
		return privilegio;
	}
}