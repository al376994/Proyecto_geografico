package Geografico.model;

public class ListaUsuario {
	private DataBaseFunctions dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());

	public Usuario getUsuario(String nombre){
		return null;
	}
	public void addUsuario(Usuario usuario) {
		dataBaseFunctions.addUsuario(usuario);
	}
}
