package integracion;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class BBDDConnection {

	public static Connection getConnection() {
		Properties props = new Properties();
		// Apertura de fichero como recurso
		try {
			InputStream fichero = BBDDConnection.class.getClassLoader()
					.getResourceAsStream("database.properties");
			if (fichero == null)
				throw new RuntimeException(
						"No se ha encontrado el fichero de configuracion database.properties");
			props.load(fichero);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new RuntimeException(
					"No se ha podido leer el fichero de configuración");
		}

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(props.getProperty("jdbc.url"),
					props.getProperty("jdbc.username"),
					props.getProperty("jdbc.password"));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("No se ha podido conectar a la BD");
		}
		return conn;
	}

}
