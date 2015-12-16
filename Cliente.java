public class Cliente{

	private String usuario;
	private String nome;

	public Cliente(){
	}
	
	public Cliente(String usuario,String nome){
		this.usuario = usuario;
		this.nome = nome;
	}
	
	public String getNome(){
		return nome;
	}
	
	public String setNome(String nome){
		this.nome = nome;
	}
	
	public String getUsuario(){
		return usuario;
	}
	
	public String setUsuario(String nome){
		this.nome = nome;
	}
}