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
			PreparedStatement statement = conn.prepareStatement("SELECT ubicacion, latitud, longitud, alias FROM usuario_ubicaciones " +
					"where nombre = ?");
			statement.setString(1, usuario);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Ubicacion ubicacion = new Ubicacion();
				ubicacion.setNombre(resultSet.getString("ubicacion"));
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
			statement.setBoolean(5,true);
			statement.setBoolean(6, false);
			statement.setString(7, alias);

			statement.executeUpdate();

			// Esto comprueba si la ubicación ya ha sido registrada antes en la base da datos en general.
			// Si es el caso no la vuelve a registrar
			statement = conn.prepareStatement("SELECT * FROM ubicaciones WHERE nombre=? AND latitud=? AND longitud=?");
			statement.setString(1, nombre);
			statement.setDouble(2, latitud);
			statement.setDouble(3, longitud);
			if(!statement.executeQuery().next()) {
				PreparedStatement statement1 = conn.prepareStatement("INSERT INTO ubicaciones values(?, ?, ?)");
				statement1.setDouble(1, latitud);
				statement1.setDouble(2, longitud);
				statement1.setString(3, nombre);
				statement1.executeUpdate();
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
	}

	public void addUsuario(Usuario usuario) throws SQLException {
		if (usuario.getContrasena().equals("")){
			throw new IllegalArgumentException();
		}
		try{
			PreparedStatement statement = conn.prepareStatement("INSERT INTO usuario values(?,?)");
			statement.setString(1, usuario.getNombre());
			statement.setString(2, usuario.getContrasena());
			statement.executeUpdate();
		}catch (SQLException e2){
			e2.printStackTrace();
		}
	}

	public Usuario getUsuario(String nombre, String contraseña) throws SQLException {
		try{
			PreparedStatement statement = conn.prepareStatement("SELECT nombre FROM usuario WHERE nombre=?");
			statement.setString(1, nombre);
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next()) return null;
			Usuario usuario = new Usuario(resultSet.getString(1));
			// Aqui se añadiran todos los otros datos que falten en usuario (ej: usuario.setEmail("email")),
			// de momento la BBDD solo guarda nombre y contraseña
			// Tambien falta la comprovación de la contraseña
			// (ej: if( !contraseña.equals(resultSet.getString(2)) ) throw new ContraseñaIncorrectaException(); )
			return usuario;
		}catch (SQLException e2){
			e2.printStackTrace();
		}
		return null;
	}

	public void deleteUsuario(String nombre, String contraseña) throws SQLException {
		try{
			PreparedStatement statement = conn.prepareStatement("DELETE FROM usuario WHERE nombre=?");
			statement.setString(1, nombre);
			statement.executeUpdate();
			statement = conn.prepareStatement("DELETE FROM usuario_ubicaciones WHERE nombre=?");
			statement.setString(1, nombre);
			statement.executeUpdate();
		}catch (SQLException e2){
			e2.printStackTrace();
		}
	}

	public void deleteUbicacion(String nombre) {
		/*
		try{
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM ubicaciones");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) System.out.println(resultSet.getString(1) + ": :" + resultSet.getString(2) + ": :" + resultSet.getString(3));
		}catch (SQLException e2){
			e2.printStackTrace();
		}
		*/
		try{
			PreparedStatement statement = conn.prepareStatement("DELETE FROM ubicaciones WHERE nombre=?");
			statement.setString(1, nombre);
			statement.executeUpdate();
		}catch (SQLException e2){
			e2.printStackTrace();
		}
	}

	public boolean altaAliasUbicacion(String usuario, String ubicacion, String alias){
		try{
			PreparedStatement statement = conn.prepareStatement("UPDATE usuario_ubicaciones" +
					" set alias = ? where nombre = ? and ubicacion = ?");
			statement.setString(1, alias);
			statement.setString(2, usuario);
			statement.setString(3, ubicacion);
			statement.executeUpdate();
			return true;
		}catch (SQLException e2){
			e2.printStackTrace();
			return false;
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
				ubicacion.setNombre(ubicaciones.getString("ubicacion"));
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

	public Ubicacion getAddedUbicacionPorToponimo(String toponimo) {
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM ubicaciones WHERE nombre = ?;");
			statement.setString(1, toponimo);
			ResultSet ubicacion = statement.executeQuery();
			if (!ubicacion.next()) return null;
			Ubicacion ubicacionProcesada = new Ubicacion();
			ubicacionProcesada.setNombre(ubicacion.getString("nombre"));
			ubicacionProcesada.setLongitud(ubicacion.getDouble("longitud"));
			ubicacionProcesada.setLatitud(ubicacion.getDouble("latitud"));
			ubicacionProcesada.setAlias(ubicacion.getString("nombre"));

			return ubicacionProcesada;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void activarUbicacion(String nombre, Ubicacion ubicacion) {
		try {
			PreparedStatement statement = conn.prepareStatement("UPDATE usuario_ubicaciones SET activo = 't' WHERE nombre = ? AND ubicacion = ?;");
			statement.setString(1, nombre);
			statement.setString(2, ubicacion.getNombre());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void desactivarUbicacion(String nombre, Ubicacion ubicacion) {
		try {
			PreparedStatement statement = conn.prepareStatement("UPDATE usuario_ubicaciones SET activo = 'f' WHERE nombre = ? AND ubicacion = ?;");
			statement.setString(1, nombre);
			statement.setString(2, ubicacion.getNombre());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Boolean isLocationActive(String nombre, Ubicacion ubicacion) {
		//FIXME
		Boolean active = true;
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT activo FROM usuario_ubicaciones WHERE nombre = ? AND ubicacion = ?;");
			statement.setString(1, nombre);
			statement.setString(2, ubicacion.getNombre());
			ResultSet result = statement.executeQuery();
			if(result.next()) active = result.getBoolean("activo");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return active;
	}

	public List<String> listarUsuarios(){
		ArrayList<String> aux = new ArrayList<>();
		try{
			PreparedStatement statement = conn.prepareStatement("SELECT nombre FROM usuario");
			ResultSet result = statement.executeQuery();
			while(result.next()){
				aux.add(result.getString("nombre"));
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return aux;
	}

	//Devuelve 0 si no está registrado
	//Devuelve 1 si la contrasena es incorrecta
	//Devuelve 2 si todo es correcto
	//Devuelve 3 por defecto
	public int iniciarSesion(String usuario, String contrasena){
		List<String> usuarios = listarUsuarios();
		if (!usuarios.contains(usuario)){
			return 0;
		}
		String pwd = "";
		try{
			PreparedStatement statement = conn.prepareStatement("SELECT contrase¤a FROM usuario where nombre" +
					"= ?");
			statement.setString(1, usuario);
			ResultSet result = statement.executeQuery();
			while(result.next()){
				pwd = result.getString(1);
			}
			if (!pwd.equals(contrasena)){
				return 1;
			}
			else{
				return 2;
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return 3;
	}


	//0 si la vieja contraseña no es correcta
	//1 si se cambia la contraseña
	//2 por defecto
	//3 si la nueva contraseña no es válida
	public int actualizarContraseña(String usuario, String viejaContraseña, String nuevaContraseña){
		if (nuevaContraseña.equals("")){
			return 3;
		}
		String pwd = "";
		try{
			PreparedStatement statement = conn.prepareStatement("SELECT contrase¤a FROM usuario where nombre" +
					"= ?");
			statement.setString(1, usuario);
			ResultSet result = statement.executeQuery();
			while(result.next()){
				pwd = result.getString(1);
			}
			if (!pwd.equals(viejaContraseña)){
				return 0;
			}
			else{
				PreparedStatement statement1 = conn.prepareStatement("UPDATE usuario SET contrase¤a = ? WHERE " +
						"nombre = ?");
				statement1.setString(1, nuevaContraseña);
				statement1.setString(2, usuario);
				statement1.executeUpdate();
				return 1;
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return 2;
	}

	public boolean anadirUbicacionFavorita(String usuario, String ubicacion) throws SQLException {
		if (!listarUbicacionesUsuario(usuario).contains(ubicacion)){
			return false;
		}
		try{
			PreparedStatement statement = conn.prepareStatement("update usuario_ubicaciones set favorito = true " +
					"where nombre = ? and ubicacion = ?");
			statement.setString(1, usuario);
			statement.setString(2, ubicacion);
			statement.executeUpdate();
			return true;
		}catch (SQLException e){
			e.printStackTrace();
			return false;
		}
	}

	public List<String> getUbicacionesFavoritas(String usuario){
		List<String> ubicacionesFavoritas = new ArrayList<>();
		try{
			PreparedStatement statement = conn.prepareStatement("select ubicacion from usuario_ubicaciones " +
					"where nombre = ? and favorito=true");
			statement.setString(1, usuario);
			ResultSet result = statement.executeQuery();
			while(result.next()){
				ubicacionesFavoritas.add(result.getString(1));
			}

		}catch (SQLException e){
			e.printStackTrace();

		}
		return ubicacionesFavoritas;
	}

	public boolean desactivarUbicacionFavorita(String usuario, String ubicacion) throws SQLException {
		if (!getUbicacionesFavoritas(usuario).contains(ubicacion)){
			return false;
		}
		if (!listarUbicacionesUsuario(usuario).get(0).getNombre().equals(ubicacion)){
			return false;
		}
		try{
			PreparedStatement statement = conn.prepareStatement("update usuario_ubicaciones set favorito = false " +
					"where nombre = ? and ubicacion = ?");
			statement.setString(1, usuario);
			statement.setString(2, ubicacion);
			statement.executeUpdate();
			return true;
		}catch (SQLException e){
			e.printStackTrace();
			return false;
		}
	}

	public boolean anadirLigaUsuario(String usuario, String liga){
		try{
			PreparedStatement statement = conn.prepareStatement("insert into usuario_liga " +
					"values(?,?");
			statement.setString(1, usuario);
			statement.setString(2, liga);
			statement.executeQuery();
			return true;
		}catch (SQLException e){
			e.printStackTrace();
			return false;
		}
	}
}
