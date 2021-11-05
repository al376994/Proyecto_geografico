package Geografico.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/* Test para la base de datos en Heroku
Eliminar cuando funcionen los métodos correctamente */
public class PruebaDataBase {
	public static void main(String[] args) throws SQLException {
		Connection connection = DataBaseConnector.getConnection();
		DataBaseFunctions dataBaseFunctions = new DataBaseFunctions(connection);
		dataBaseFunctions.añadirUbicacionUsuario("usuario3", 0.00, 0.00, "centro");
		List<Ubicacion> listaUbicaciones = dataBaseFunctions.listarUbicacionesUsuario("usuario3");
		for (Ubicacion u: listaUbicaciones){
			System.out.println("Alias: "+ u.getAlias());
			System.out.println("Latitud: " + u.getLatitud());
			System.out.println("Longitud: " + u.getLongitud());
		}
	}
}
