package Geografico.model;

import java.sql.SQLException;

public class ListaUsuario {
	private DataBaseFunctions dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());

	public Usuario getUsuario(String nombre){
		return null;
	}
	public void addUsuario(Usuario usuario) throws SQLException {
		dataBaseFunctions.addUsuario(usuario);
	}
}
