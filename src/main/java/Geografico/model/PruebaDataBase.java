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
		//dataBaseFunctions.añadirUbicacionUsuario("usuario29", 0.00, 0.00, "Castellón", "centro");
		//dataBaseFunctions.añadirUbicacionUsuario("usuario29", 30.00, 0.00, "Valencia", "centro");
		//dataBaseFunctions.altaAliasUbicacion("usuario29", "Castellón","capital");
		/*List<Ubicacion> listaUbicaciones = dataBaseFunctions.listarUbicacionesUsuario("usuario29");
		for (Ubicacion u: listaUbicaciones){
			System.out.println("Alias: "+ u.getAlias());
			System.out.println("Latitud: " + u.getLatitud());
			System.out.println("Longitud: " + u.getLongitud());
		}*/
//		Ubicacion ubicacion = new Ubicacion();
//		ubicacion.setNombre("Castellón");
//		String alias = dataBaseFunctions.getAliasUbicacion("usuario29","Valencia");
//		System.out.println(alias);
//		List<Ubicacion> ubicacionesUser = dataBaseFunctions.listarUbicacionesUsuario("usuario29");
//		System.out.println(ubicacionesUser);
//		List<Ubicacion> activas = dataBaseFunctions.getUbicacionesActivas("usuario1");
//		System.out.println(activas);
//		Boolean active = dataBaseFunctions.isLocationActive("usuario1", ubicacion);
//		System.out.println(active);
	}
}
