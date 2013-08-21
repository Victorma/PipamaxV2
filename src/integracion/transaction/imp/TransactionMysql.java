package integracion.transaction.imp;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import integracion.transaction.LockModes;
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
	public Object getResource() {
		return connection;
	}
	
	@Override
	public boolean lock(LockModes mode, List<String> tables) {
		boolean error = false;
		Statement stmt = null;
		String query = "LOCK TABLES ";
		
		try{	
			stmt = connection.createStatement();
			
			if(mode == LockModes.Write)
			{
				for(String table:tables){query.concat(table); query.concat(" WRITE,");}
				query = query.substring(0, query.length()-1);
				stmt.execute(query);
			}
			else if(mode == LockModes.Read)
			{
				for(String table:tables){query.concat(table); query.concat(" READ,");}
				query = query.substring(0, query.length()-1);
				stmt.execute(query);
			}
			else if(mode == LockModes.ReadAndWrite)
			{
				for(String table:tables){
					query.concat(table); query.concat(" WRITE, ");
					query.concat(table); query.concat(" as "); 
					query.concat(table); query.concat("2 READ,");
				}
				query = query.substring(0, query.length()-1);
				stmt.execute(query);
			}
			
			/* ####### Se guarda este código porque contiene el dominio de tablas al las que afecta cada módulo #######
			else if(mode == LockModes.LockMarcas)
				stmt.execute("LOCK TABLES marcas WRITE, marcas AS mar READ, productos WRITE, productos AS prod READ, pedidos WRITE, pedidos AS ped READ, linea_pedido WRITE, linea_pedido AS liped READ, suministros WRITE, suministros AS sum READ, proveedores WRITE, proveedores AS prov READ, ventas WRITE, ventas AS vent READ");
			else if(mode == LockModes.LockPedidos)
				 stmt.execute("LOCK TABLES pedidos WRITE, pedidos AS ped READ, linea_pedido WRITE, linea_pedido AS lin READ, productos WRITE, productos AS prod READ, suministros WRITE, suministros AS sum READ, proveedores WRITE, proveedores AS prov READ");
			else if (mode == LockModes.LockProductos1)
				 stmt.execute("LOCK TABLES productos WRITE, productos AS prod READ, marcas WRITE, marcas AS mar READ");
			else if (mode == LockModes.LockProductos2)
				 stmt.execute("LOCK TABLES productos WRITE, productos AS prod READ, suministros WRITE, suministros AS sum READ, proveedores WRITE, proveedores AS prov READ");
			else if (mode == LockModes.LockProductos3)
				 stmt.execute("LOCK TABLES productos WRITE, productos AS prod READ, pedidos WRITE, pedidos AS ped READ, linea_pedido WRITE, linea_pedido AS liped READ, suministros WRITE, suministros AS sum READ, proveedores WRITE, proveedores AS prov READ, ventas WRITE, ventas AS vent READ");
			else if (mode == LockModes.LockVentas)
				 stmt.execute("LOCK TABLES ventas WRITE, ventas AS ven READ, lineasventa WRITE, lineasventa AS lv READ, productos WRITE, productos AS prod READ, clientes WRITE, clientes AS cli READ, clientesvip WRITE, clientesvip AS clivip READ");
			   ########################################################################################################*/
			
			else if (mode == LockModes.LockAll)
				 stmt.execute("FLUSH TABLES WITH READ LOCK");
		}
		catch(SQLException ex){error = true;}

		return !error;
	}
	
	@Override
	public boolean unlock(){
		boolean error = true;
		Statement stmt = null;
		
		try 
		{	
			stmt = connection.createStatement();
			stmt.execute("UNLOCK TABLES");
		}
		catch(SQLException ex){error = false;}
		
		return !error;
	}

}