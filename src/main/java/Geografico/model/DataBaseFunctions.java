package Geografico.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	public void añadirUbicacionUsuario(String usuario, double latitud,double longitud, String alias){
		try{
			PreparedStatement statement = conn.prepareStatement("INSERT INTO usuario_ubicaciones values(?, ?, ?, ?,?,?)");
			statement.setString(1, usuario);
			statement.setDouble(2, latitud);
			statement.setDouble(3, longitud);
			statement.setString(4, alias);
			statement.setBoolean(5,false);
			statement.setBoolean(6, false);
			statement.executeUpdate();
			PreparedStatement statement1 = conn.prepareStatement("INSERT INTO ubicaciones values(?, ?, ?)");
			statement1.setDouble(1, latitud);
			statement1.setDouble(2, longitud);
			statement1.setString(3, alias);
			statement1.executeUpdate();
		}catch (SQLException e){
			e.printStackTrace();
		}
	}

	public void addUsuario(Usuario usuario){
		try{
			PreparedStatement statement = conn.prepareStatement("INSERT INTO usuario values(?,?)");
			statement.setString(1, usuario.getNombre());
			statement.setString(2, null);
			statement.executeUpdate();
		}catch (SQLException e){
			e.printStackTrace();
		}
	}

}
