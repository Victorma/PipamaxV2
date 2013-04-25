package integracion.transaction.imp;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import integracion.transaction.Transaction;

public class TransactionMysql implements Transaction {
	//The conection to the data base
	private Connection connection;

	@Override
	public boolean start() {
		//Connect to the data base
		Properties props = new Properties();
		// Apertura de fichero como recurso
		try {
			InputStream fichero = TransactionMysql.class.getClassLoader()
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

		try {
			connection = DriverManager.getConnection(
					props.getProperty("jdbc.url"),
					props.getProperty("jdbc.username"),
					props.getProperty("jdbc.password"));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("No se ha podido conectar a la BD");
		}

		//Set the AutoCommit to 0 and start the transaction
		Statement stmt = null;
		//ResultSet rs = null;
		boolean error = false;

		try {
			stmt = connection.createStatement();

			stmt.execute("SET AUTOCOMMIT = 0");
			//rs = stmt.getResultSet();
			stmt.addBatch("START TRANSACTION");

			stmt.executeBatch();

		} catch (SQLException ex) {
			error = true;
			//throw new DAOException(ex);
		}

		return !error;
	}

	@Override
	public boolean commit() {
		boolean error = false;

		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			
			int editadas = stmt.executeUpdate("COMMIT");
			stmt.executeUpdate("UNLOCK TABLES");
			if (editadas != 1) {
				error = true;
			}
		} catch (SQLException ex) {
			error = true;
		}

		return !error;
	}

	@Override
	public boolean rollback() {
		boolean error = false;

		Statement stmt = null;
		try {
			stmt = connection.createStatement();

			int editadas = stmt.executeUpdate("ROLLBACK");
			stmt.executeUpdate("UNLOCK TABLES");
			if (editadas != 1) {
				error = true;
			}
		} catch (SQLException ex) {
			error = true;
		}

		return !error;
	}

	@Override
	public Connection getResource() {
		return connection;
	}

}