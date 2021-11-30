package Geografico.model;

import java.sql.SQLException;

public class ListaUsuario {
	private DataBaseFunctions dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());

	public Usuario getUsuario(String nombre, String contraseña) throws SQLException {
		return dataBaseFunctions.getUsuario(nombre, contraseña);
	}
	public void addUsuario(Usuario usuario) throws SQLException {
		dataBaseFunctions.addUsuario(usuario);
	}
	public void deleteUsuario(String nombre, String contraseña) throws SQLException {
		dataBaseFunctions.deleteUsuario(nombre, contraseña);
	}
}
