package Geografico.model;

import Geografico.model.excepciones.AlreadyHasPlaceException;
import Geografico.model.excepciones.NotFoundPlaceException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//No añadir nombres con carácteres extraños (accentos)
//La base de datos no lo guarda correctamente

public class DataBaseFunctions {
	public static final String FOREIGN_KEY_VALUE_INVALID = "23503";
	private final Connection conn;

	public DataBaseFunctions(Connection connection){
		this.conn = connection;
	}

	public List<Ubicacion> listarUbicacionesUsuario(String usuario) throws SQLException {
		List<Ubicacion> ubicaciones = new ArrayList<>();
		try{
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM usuario_ubicaciones " +
					"where nombre = ? ORDER BY alias");
			statement.setString(1, usuario);
			ResultSet resultSetUbicaciones = statement.executeQuery();
			while (resultSetUbicaciones.next()) {
				Ubicacion ubicacion = buildUbicacionFromResultSet(resultSetUbicaciones);
				ubicaciones.add(ubicacion);
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return ubicaciones;
	}

	public List<Ubicacion> getUbicacionesDesactivadasUsuario(String usuario) {
		List<Ubicacion> ubicacionesDesactivadas = new ArrayList<>();
		try{
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM usuario_ubicaciones " +
					"where nombre = ? AND activo=false ORDER BY alias");
			statement.setString(1, usuario);
			ResultSet resultSetUbicaciones = statement.executeQuery();
			while (resultSetUbicaciones.next()) {
				Ubicacion ubicacion = buildUbicacionFromResultSet(resultSetUbicaciones);
				ubicacionesDesactivadas.add(ubicacion);
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return ubicacionesDesactivadas;
	}

	public void addUbicacionUsuario(String usuario, Ubicacion ubicacion) throws AlreadyHasPlaceException {

		if (getUbicacionUsuario(usuario, ubicacion.getNombre()) != null)
			throw new AlreadyHasPlaceException(ubicacion.getNombre());
		try{
			// Esto comprueba si la ubicación ya ha sido registrada antes en la base da datos en general.
			// Si es el caso no la vuelve a registrar
			if (getAddedUbicacionPorToponimo(ubicacion.getNombre()) == null) {
				PreparedStatement statement = conn.prepareStatement("INSERT INTO ubicaciones values(?, ?, ?)");
				statement.setDouble(1, ubicacion.getLatitud());
				statement.setDouble(2, ubicacion.getLongitud());
				statement.setString(3, ubicacion.getNombre());
				statement.executeUpdate();
			}

			if (getUbicacionUsuario(usuario, ubicacion.getNombre()) == null) {
				PreparedStatement statement = conn.prepareStatement(
						"INSERT INTO usuario_ubicaciones values(?, ?, ?, ?,?,?,?)");
				statement.setString(1, usuario);
				statement.setDouble(2, ubicacion.getLatitud());
				statement.setDouble(3, ubicacion.getLongitud());
				statement.setString(4, ubicacion.getNombre());
				statement.setBoolean(5,true);
				statement.setBoolean(6, false);
				if (ubicacion.getAlias() == null) statement.setString(7, ubicacion.getNombre());
				else statement.setString(7, ubicacion.getAlias());
				statement.executeUpdate();
			}

			for (String servicio: getServiciosAPIUsuario(usuario)) {
				PreparedStatement statement = conn.prepareStatement(
						"INSERT INTO usuario_ubicaciones_servicios values(?,?,?)");
				statement.setString(1, usuario);
				statement.setString(2, ubicacion.getNombre());
				statement.setString(3, servicio);
				statement.executeUpdate();
			}

		}catch (SQLException e){
			e.printStackTrace();
		}
	}

	public boolean quitarUbicacionUsuario(String usuario, String ubicacion) {
		try {
			PreparedStatement statement = conn.prepareStatement("DELETE FROM usuario_ubicaciones " +
					"WHERE nombre=? AND ubicacion=?");
			statement.setString(1, usuario);
			statement.setString(2, ubicacion);
			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
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
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM usuario WHERE nombre=?");
			statement.setString(1, nombre);
			ResultSet resultSetUsuario = statement.executeQuery();
			if (!resultSetUsuario.next()) return null;
			Usuario usuario = new Usuario(resultSetUsuario.getString(1), resultSetUsuario.getString(2));
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
			PreparedStatement statement = conn.prepareStatement("DELETE FROM usuario_ubicaciones WHERE nombre=?");
			statement.setString(1, nombre);
			statement.executeUpdate();
			statement = conn.prepareStatement("DELETE FROM usuario_servicios WHERE usuario=?");
			statement.setString(1, nombre);
			statement.executeUpdate();
			statement = conn.prepareStatement("DELETE FROM usuario WHERE nombre=?");
			statement.setString(1, nombre);
			statement.executeUpdate();
		}catch (SQLException e2){
			e2.printStackTrace();
		}
	}

	public void deleteUbicacion(String nombre) {
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

	public Ubicacion getUbicacionUsuario(String usuario, String ubicacion){
		try{
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM usuario_ubicaciones " +
					"WHERE nombre = ? AND ubicacion = ?");
			statement.setString(1, usuario);
			statement.setString(2, ubicacion);
			ResultSet resultSetUbicacion = statement.executeQuery();
			if (resultSetUbicacion.next()) {
				return buildUbicacionFromResultSet(resultSetUbicacion);
			}
		}catch (SQLException e2){
			e2.printStackTrace();
		}
		return null;
	}

	private Ubicacion buildUbicacionFromResultSet(ResultSet ubicacion) throws SQLException {
		Ubicacion ubicacionProcesada = new Ubicacion();
		ubicacionProcesada.setNombre(ubicacion.getString("ubicacion"));
		ubicacionProcesada.setLatitud(ubicacion.getDouble("latitud"));
		ubicacionProcesada.setLongitud(ubicacion.getDouble("longitud"));
		// Si viene de la tabla ubicaciones no tendrá más de estas columnas.
		if(ubicacion.getMetaData().getColumnCount()<=3) return ubicacionProcesada;

		ubicacionProcesada.setAlias(ubicacion.getString("alias"));
		ubicacionProcesada.setActivo(ubicacion.getBoolean("activo"));
		ubicacionProcesada.setFavorito(ubicacion.getBoolean("favorito"));
		return ubicacionProcesada;
	}

	public String getAliasUbicacion(String usuario, String ubicacion){
		String alias = "";
		try{
			PreparedStatement statement = conn.prepareStatement("SELECT alias FROM usuario_ubicaciones " +
					"where nombre = ? and ubicacion = ?");
			statement.setString(1, usuario);
			statement.setString(2, ubicacion);
			ResultSet resultSetAlias = statement.executeQuery();
			while (resultSetAlias.next()) {
				alias = resultSetAlias.getString(1);
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
			ResultSet resultSetUbicaciones = statement.executeQuery();
			while(resultSetUbicaciones.next()) {
				Ubicacion ubicacion = buildUbicacionFromResultSet(resultSetUbicaciones);
				ubicacionesActivas.add(ubicacion);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ubicacionesActivas;
	}

	public Ubicacion getAddedUbicacionPorToponimo(String toponimo) {
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT latitud, longitud, nombre AS ubicacion FROM ubicaciones WHERE UPPER(nombre) = UPPER(?);");
			statement.setString(1, toponimo);
			ResultSet ubicacion = statement.executeQuery();
			if (!ubicacion.next()) return null;
			return buildUbicacionFromResultSet(ubicacion);
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
			PreparedStatement statement = conn.prepareStatement("SELECT nombre FROM usuario;");
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
					"= ?;");
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
					"= ?;");
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
						"nombre = ?;");
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
		if (getUbicacionUsuario(usuario, ubicacion) == null){
			return false;
		}
		try{
			PreparedStatement statement = conn.prepareStatement("update usuario_ubicaciones set favorito = true " +
					"where nombre = ? and ubicacion = ?;");
			statement.setString(1, usuario);
			statement.setString(2, ubicacion);
			statement.executeUpdate();
			return true;
		}catch (SQLException e){
			e.printStackTrace();
			return false;
		}
	}

	public List<Ubicacion> getUbicacionesFavoritas(String usuario){
		List<Ubicacion> ubicacionesFavoritas = new ArrayList<>();
		try{
			PreparedStatement statement = conn.prepareStatement("select * from usuario_ubicaciones " +
					"where nombre = ? and favorito=true and activo=true;");
			statement.setString(1, usuario);
			ResultSet result = statement.executeQuery();
			while(result.next()){
				ubicacionesFavoritas.add(buildUbicacionFromResultSet(result));
			}

		}catch (SQLException e){
			e.printStackTrace();

		}
		return ubicacionesFavoritas;
	}

	public boolean desactivarUbicacionFavorita(String usuario, String ubicacion) throws SQLException {
		if (!getUbicacionUsuario(usuario, ubicacion).isFavorito()){
			return false;
		}
		try{
			PreparedStatement statement = conn.prepareStatement("update usuario_ubicaciones set favorito = false " +
					"where nombre = ? and ubicacion = ?;");
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
					"values(?,?)");
			statement.setString(1, usuario);
			statement.setString(2, liga);
			statement.executeQuery();
			return true;
		}catch (SQLException e){
			e.printStackTrace();
			try{
				PreparedStatement statement = conn.prepareStatement("update usuario_liga " +
						"set liga = ? where usuario = ?");
				statement.setString(1, liga);
				statement.setString(2, usuario);
				statement.executeUpdate();
				return true;
			}catch (SQLException e1){
				e1.printStackTrace();
				return false;
			}
		}
	}

	public String getLigaUsuario(String usuario){
		try{
			PreparedStatement statement = conn.prepareStatement("select liga from usuario_liga" +
					"where usuario = ?");
			statement.setString(1, usuario);
			ResultSet result = statement.executeQuery();
			while(result.next()){
				String liga = result.getString(1);
				System.out.println(liga);
				return liga;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	public void addServicioAPIDisponible(String servicio) {
		try {
			PreparedStatement statement = conn.prepareStatement("INSERT INTO servicios_api VALUES(?);");
			statement.setString(1, servicio);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteServicioAPIDisponible(String servicio) {
		try {
			PreparedStatement statement = conn.prepareStatement("DELETE FROM servicios_api WHERE nombre=?;");
			statement.setString(1, servicio);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<String> getAPIsDisponibles() {
		List<String> APIsDisponibles = new ArrayList<String>();
		try {
			PreparedStatement statement = conn.prepareCall("SELECT * FROM servicios_api;");
			ResultSet result = statement.executeQuery();
			while(result.next()){
				APIsDisponibles.add(result.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return APIsDisponibles;
	}

	public void activarServicioAPIUsuario(String usuario, String servicio) {
		try {
			PreparedStatement statement = conn.prepareStatement("INSERT INTO usuario_servicios VALUES(?,?);");
			statement.setString(1, usuario);
			statement.setString(2, servicio);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void desactivarServicioAPIUsuario(String usuario, String servicio) {
		try {
			PreparedStatement statement = conn.prepareStatement("DELETE FROM usuario_servicios WHERE usuario =? AND servicioapi=?;");
			statement.setString(1, usuario);
			statement.setString(2, servicio);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<String> getServiciosAPIUsuario(String usuario) {
		List<String> servicios = new ArrayList<>();
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT servicioapi FROM usuario_servicios WHERE usuario=?;");
			statement.setString(1, usuario);
			ResultSet resultSetServicios = statement.executeQuery();
			while (resultSetServicios.next()) {
				servicios.add(resultSetServicios.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return servicios;
	}

	public boolean isServicioAPIActivoUsuario(String usuario, String servicio) {
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT 1 FROM usuario_servicios " +
					"WHERE usuario=? AND servicioapi=?;");
			statement.setString(1, usuario);
			statement.setString(2, servicio);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean altaServicioUbicacion(String usuario, String ubicacion, String servicio) throws NotFoundPlaceException {
		try {
			PreparedStatement statement = conn.prepareStatement("INSERT INTO usuario_ubicaciones_servicios VALUES(?,?,?);");
			statement.setString(1, usuario);
			statement.setString(2, ubicacion);
			statement.setString(3, servicio);
			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			if (e.getSQLState().equals(FOREIGN_KEY_VALUE_INVALID)) throw new NotFoundPlaceException(ubicacion);
			e.getErrorCode();
		}
		return false;
	}

	public void bajaServicioUbicacion(String usuario, String ubicacion, String servicio) {
		try {
			PreparedStatement statement = conn.prepareStatement("DELETE FROM usuario_ubicaciones_servicios " +
					"WHERE usuario=? AND ubicacion=? AND servicioapi=?");
			statement.setString(1, usuario);
			statement.setString(2, ubicacion);
			statement.setString(3, servicio);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<String> getServiciosAPIUbicacionUsuario(String usuario, String ubicacion) {
		 try {
			PreparedStatement statement = conn.prepareStatement("SELECT servicioapi FROM usuario_ubicaciones_servicios " +
					"WHERE usuario=? AND ubicacion=?");
			statement.setString(1, usuario);
			statement.setString(2, ubicacion);
			ResultSet result = statement.executeQuery();
			List<String> servicions = new ArrayList<>();
			while(result.next()) {
				servicions.add(result.getString(1));
			}
			return servicions;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Ubicacion> getUbicacionesConServicioUsuario(String usuario, String servicio) {
		List<Ubicacion> ubicacionesConServicio = new ArrayList<>();
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM usuario_ubicaciones_servicios " +
					"JOIN usuario_ubicaciones USING(ubicacion) " +
					"WHERE usuario=? AND servicioapi=? AND nombre='u'");
			statement.setString(1, usuario);
			statement.setString(2, servicio);
			ResultSet resultSetUbicaciones = statement.executeQuery();
			while(resultSetUbicaciones.next()) {
				ubicacionesConServicio.add(buildUbicacionFromResultSet(resultSetUbicaciones));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ubicacionesConServicio;
	}

	public void anadirEquipoClasificacion(EquipoClasificacion equipoClasificacion){
		try {
			PreparedStatement statement = conn.prepareStatement("INSERT INTO clasificacionLiga" +
					" values(?, ?, ?, ?,?,?,?,?)");
			statement.setString(1, equipoClasificacion.getNombre());
			statement.setString(2, equipoClasificacion.getPais());
			statement.setString(3, equipoClasificacion.getId());
			statement.setString(4, equipoClasificacion.getLogo());
			statement.setInt(5, equipoClasificacion.getPuntos());
			statement.setInt(6, equipoClasificacion.getPartidosGanados());
			statement.setInt(7, equipoClasificacion.getPartidosPerdidos());
			statement.setInt(8, equipoClasificacion.getPartidosEmpatados());

			statement.executeUpdate();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void anadirEquipoClasificacionBundesliga(EquipoClasificacion equipoClasificacion){
		try {
			PreparedStatement statement = conn.prepareStatement("INSERT INTO clasificacionbundesliga" +
					" values(?, ?, ?, ?,?,?,?,?)");
			statement.setString(1, equipoClasificacion.getNombre());
			statement.setString(2, equipoClasificacion.getPais());
			statement.setString(3, equipoClasificacion.getId());
			statement.setString(4, equipoClasificacion.getLogo());
			statement.setInt(5, equipoClasificacion.getPuntos());
			statement.setInt(6, equipoClasificacion.getPartidosGanados());
			statement.setInt(7, equipoClasificacion.getPartidosPerdidos());
			statement.setInt(8, equipoClasificacion.getPartidosEmpatados());

			statement.executeUpdate();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public List<EquipoClasificacion> getClasificacionBundesliga(){
		List<EquipoClasificacion> clasificacion = new ArrayList<>();
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM clasificacionbundesliga");
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				EquipoClasificacion e = new EquipoClasificacion(result.getString(1),result.getString(2)
						,result.getString(4),result.getString(3),result.getInt(5),result.getInt(6)
						,result.getInt(7),result.getInt(8));
				clasificacion.add(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clasificacion;
	}

	public List<EquipoClasificacion> getClasificacionLiga(){
		List<EquipoClasificacion> clasificacion = new ArrayList<>();
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM clasificacionLiga ");
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				EquipoClasificacion e = new EquipoClasificacion(result.getString(1),result.getString(2)
						,result.getString(4),result.getString(3),result.getInt(5),result.getInt(6)
						,result.getInt(7),result.getInt(8));
				clasificacion.add(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clasificacion;
	}

	public void anadirPartido(Partido p){
		try {
			PreparedStatement statement = conn.prepareStatement("INSERT INTO Partidos" +
					" values(?, ?, ?, ?,?)");
			statement.setString(1, p.getEquipoCasa().getNombre());
			statement.setString(2, p.getEquipoFuera().getNombre());
			statement.setString(3, p.getEstadio());
			statement.setString(4, p.getResultado());
			statement.setString(5, p.getFecha());
			statement.executeUpdate();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void anadirPartidoBundesliga(Partido p){
		try {
			PreparedStatement statement = conn.prepareStatement("INSERT INTO partidosbundesliga" +
					" values(?, ?, ?, ?,?)");
			statement.setString(1, p.getEquipoCasa().getNombre());
			statement.setString(2, p.getEquipoFuera().getNombre());
			statement.setString(3, p.getEstadio());
			statement.setString(4, p.getResultado());
			statement.setString(5, p.getFecha());
			statement.executeUpdate();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public List<Partido> getPartidos(){
		List<Partido> partidos = new ArrayList<>();
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM partidos");
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				Equipo equipo1 = new Equipo();
				equipo1.setNombre(result.getString(1));
				Equipo equipo2 = new Equipo();
				equipo2.setNombre(result.getString(2));
				Partido p = new Partido(equipo1,equipo2,result.getString(3)
						,result.getString(4),result.getString(5));
				partidos.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<Partido> menosPartidos = partidos.subList(0,50);
		return menosPartidos;
	}

	public List<Partido> getPartidosBundesliga(){
		List<Partido> partidos = new ArrayList<>();
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM partidosbundesliga");
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				Equipo equipo1 = new Equipo();
				equipo1.setNombre(result.getString(1));
				Equipo equipo2 = new Equipo();
				equipo2.setNombre(result.getString(2));
				Partido p = new Partido(equipo1,equipo2,result.getString(3)
						,result.getString(4),result.getString(5));
				partidos.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<Partido> menosPartidos = partidos.subList(0,50);
		return menosPartidos;
	}

	public List<Ubicacion> getHistorialUbicaciones(String usuario) {
		List<Ubicacion> historial = new ArrayList<>();
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM historial_ubicaciones WHERE nombreusuario = ?;");
			statement.setString(1, usuario);
			ResultSet resultSetHistorial = statement.executeQuery();
			while(resultSetHistorial.next()) {
				Ubicacion ubicacion = new Ubicacion();
				ubicacion.setNombre(resultSetHistorial.getString("nombreubicacion"));
				ubicacion.setLatitud(resultSetHistorial.getDouble("latitud"));
				ubicacion.setLongitud(resultSetHistorial.getDouble("longitud"));
				ubicacion.setAlias(resultSetHistorial.getString("alias"));
				historial.add(ubicacion);
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		return historial;
	}

	public boolean reactivarUbicacion(String nombre, String toponimo) {
		try {
			PreparedStatement statement = conn.prepareStatement("DELETE FROM historial_ubicaciones WHERE nombreusuario = ? AND nombreubicacion = ?;");
			statement.setString(1, nombre);
			statement.setString(2, toponimo);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void actualizarContrasena(String username, String password) {
		try {
			System.out.println(password);
			PreparedStatement statement = conn.prepareStatement("UPDATE usuario SET contraseña = ? WHERE nombre = ?;");
			statement.setString(1, password);
			statement.setString(2, username);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
