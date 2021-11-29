package Geografico.model;

import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//No añadir nombres con carácteres extraños (accentos)
//La base de datos no lo guarda correctamente

public class DataBaseFunctions {
	private Connection conn;

	public DataBaseFunctions(Connection connection){
		this.conn = connection;
	}

	public List<Ubicacion> listarUbicacionesUsuario(String usuario) throws SQLException {
		List<Ubicacion> ubicaciones = new ArrayList<>();
		try{
			PreparedStatement statement = conn.prepareStatement("SELECT latitud, longitud, alias FROM usuario_ubicaciones " +
					"where nombre = ?");
			statement.setString(1, usuario);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Ubicacion ubicacion = new Ubicacion();
				ubicacion.setAlias(resultSet.getString("alias"));
				ubicacion.setLatitud(resultSet.getDouble("latitud"));
				ubicacion.setLongitud(resultSet.getDouble("longitud"));
				ubicaciones.add(ubicacion);
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return ubicaciones;
	}

	public void añadirUbicacionUsuario(String usuario, double latitud,double longitud, String nombre, String alias){
		try{
			PreparedStatement statement = conn.prepareStatement("INSERT INTO usuario_ubicaciones values(?, ?, ?, ?,?,?,?)");
			statement.setString(1, usuario);
			statement.setDouble(2, latitud);
			statement.setDouble(3, longitud);
			statement.setString(4, nombre);
			statement.setBoolean(5,false);
			statement.setBoolean(6, false);
			statement.setString(7, alias);

			statement.executeUpdate();
			PreparedStatement statement1 = conn.prepareStatement("INSERT INTO ubicaciones values(?, ?, ?)");
			statement1.setDouble(1, latitud);
			statement1.setDouble(2, longitud);
			statement1.setString(3, nombre);
			statement1.executeUpdate();
		}catch (SQLException e){
			e.printStackTrace();
		}
	}

	public void addUsuario(Usuario usuario) throws SQLException {
		try{
			PreparedStatement statement = conn.prepareStatement("INSERT INTO usuario values(?,?)");
			statement.setString(1, usuario.getNombre());
			statement.setString(2, null);
			statement.executeUpdate();
		}catch (SQLException e2){
			e2.printStackTrace();
		}
	}

	public void altaAliasUbicacion(String usuario, String ubicacion, String alias){
		try{
			PreparedStatement statement = conn.prepareStatement("UPDATE usuario_ubicaciones" +
					" set alias = ? where nombre = ? and ubicacion = ?");
			statement.setString(1, alias);
			statement.setString(2, usuario);
			statement.setString(3, ubicacion);
			statement.executeUpdate();
		}catch (SQLException e2){
			e2.printStackTrace();
		}
	}

	public String getAliasUbicacion(String usuario, String ubicacion){
		String alias = "";
		try{
			PreparedStatement statement = conn.prepareStatement("SELECT alias FROM usuario_ubicaciones " +
					"where nombre = ? and ubicacion = ?");
			statement.setString(1, usuario);
			statement.setString(2, ubicacion);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				alias = resultSet.getString(1);
			}
		}catch (SQLException e2){
			e2.printStackTrace();
		}
		return alias;
	}


	public List<Ubicacion> getUbicacionesActivas(String nombre) throws SQLException {
		List<Ubicacion> ubicacionesActivas = new ArrayList<>();
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM usuario_ubicaciones WHERE nombre = ? AND activo = 't';");
			statement.setString(1, nombre);
			ResultSet ubicaciones = statement.executeQuery();
			while(ubicaciones.next()) {
				Ubicacion ubicacion = new Ubicacion();
				ubicacion.setAlias(ubicaciones.getString("nombre"));
				ubicacion.setLongitud(ubicaciones.getDouble("longitud"));
				ubicacion.setLatitud(ubicaciones.getDouble("latitud"));
				ubicacion.setAlias(ubicaciones.getString("alias"));
				ubicacionesActivas.add(ubicacion);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ubicacionesActivas;
	}

}
