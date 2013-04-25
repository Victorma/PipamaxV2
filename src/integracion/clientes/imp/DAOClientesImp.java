package integracion.clientes.imp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import negocio.clientes.TransferCliente;
import negocio.clientes.TransferListaClientes;

import integracion.BBDDConnection;
import integracion.DAOException;
import integracion.clientes.DAOClientes;
import integracion.transaction.transactionManager.TransactionManager;

/**
 * Data Access Object que se encarga de realizar las gestiones entre el negocio y la base de datos de clientes.
 * @author Grupo Pipamax
 *
 */
public class DAOClientesImp implements DAOClientes 
{


	@Override
	public boolean crearCliente(TransferCliente cliente) throws DAOException {

		boolean error = false;
		
		Statement stmt = null;
		
		//Get the connection from the transaction
		Connection connection = TransactionManager.getInstancia().getTransaction().getResource();
		if(connection == null)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try 
			{
				connection = BBDDConnection.getConnection();
				
				stmt = connection.createStatement();
				//execute the querys
				stmt.addBatch("INSERT INTO clientes (tipo, DNI, nombre, apellidos, direccion, ciudad, codigoPostal, numeroTelefono, email, activo) " + 
				"VALUES('" + 
					cliente.getTipo() + "', '" + 
					cliente.getDNI() + "', '" + 
					cliente.getName() + "', '" +
					cliente.getLastName() + "', '" +
					cliente.getAdress() + "', '" +
					cliente.getCity() + "', " + 
					cliente.getPostalCode() + ", " +
					cliente.getTelephoneNumber() + ", '" +
					cliente.getEmail()+ "', " +
					"1)"
				);
				if(cliente.getTipo().equals("VIP")){
					stmt.addBatch("INSERT INTO clientesvip (idCliente, descuento) " + 
							"VALUES((SELECT MAX(id) from clientes),'" + 
							cliente.getDescuento() + 	
							"')"
							);
				}
				
				int[] editadas = stmt.executeBatch();
				for(int i: editadas)
					if(!error)
						error = !error || (i != 1);
				
			}
			catch(SQLException ex)
			{
				throw new DAOException(ex);
			}
		}
		else
		{
			try
			{
				stmt = connection.createStatement();
				
				stmt.addBatch("INSERT INTO clientes(tipo, DNI, nombre, apellidos, direccion, ciudad, codigoPostal, numeroTelefono, email, activo) " + 
				"VALUES('" + 
					cliente.getTipo() + "', '" + 
					cliente.getDNI() + "', '" + 
					cliente.getName() + "', '" +
					cliente.getLastName() + "', '" +
					cliente.getAdress() + "', '" +
					cliente.getCity() + "', " + 
					cliente.getPostalCode() + ", " +
					cliente.getTelephoneNumber() + ", '" +
					cliente.getEmail()+ "', " +
					"1)"
				);
				if(cliente.getTipo().equals("VIP")){
					stmt.addBatch("INSERT INTO clientesvip (idCliente, descuento) " + 
							"VALUES((SELECT MAX(id) from clientes),'" + 
							cliente.getDescuento() + 	
							"')"
							);
				}
			
				int[] editadas = stmt.executeBatch();
				for(int i: editadas)
					if(!error)
						error = i != 1;
			}
			catch(SQLException ex){
				throw new DAOException(ex);
			}
			
		}//End else connection == null
		
		
		return !error;
	}

	@Override
	public boolean editarCliente(TransferCliente cliente) throws DAOException {
		
		boolean error = false;
		
		Statement stmt = null;
		ResultSet rs = null;
		//Get the connection from the transaction
		Connection connection = TransactionManager.getInstancia().getTransaction().getResource();
		if(connection == null)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try 
			{	
				connection = BBDDConnection.getConnection();
				stmt = connection.createStatement();
				
				stmt.execute("SELECT tipo FROM clientes WHERE id ='" + cliente.getId()+"'");
				rs = stmt.getResultSet();
				
	
				
				if(rs.next())
				{
					
					stmt.addBatch("UPDATE clientes SET " +
						"DNI = '" + cliente.getDNI() + "', " +
						"tipo = '" + cliente.getTipo() + "', " +
						"nombre = '" + cliente.getName() + "', " +
						"apellidos = '" + cliente.getLastName() + "', " +
						"direccion = '" + cliente.getAdress() + "', " +
						"ciudad = '" + cliente.getCity() + "', " +
						"codigoPostal = " + cliente.getPostalCode() + ", " +
						"numeroTelefono = " + cliente.getTelephoneNumber() + ", " +
						"email = '" + cliente.getEmail() + "' " +
						"WHERE id = " + cliente.getId()
					);
					
					String tipoAnterior = rs.getString(1);
					if(cliente.getTipo().equals(tipoAnterior)){
						if(cliente.getTipo().equals("VIP"))
							stmt.addBatch("UPDATE clientesvip SET descuento = '" + cliente.getDescuento() + "' WHERE idCliente = '" + cliente.getId() +"'");
						
					}else{
						if(cliente.getTipo().equals("VIP"))
							stmt.addBatch("INSERT INTO clientesvip (idCliente, descuento) " + 
									"VALUES('" +
									cliente.getId() + "','" + 
									cliente.getDescuento() + 	
									"')");
						else
							stmt.addBatch("DELETE FROM clientesvip WHERE idCliente = " + cliente.getId());
						
					}
				}
				
				int[] editadas = stmt.executeBatch();
				
				for(int i: editadas)
					if(!error)
						error = i != 1;
			}
			catch(SQLException ex){
				throw new DAOException(ex);
			}
		}
		else
		{
			try 
			{
				stmt = connection.createStatement();
				
				stmt.execute("SELECT tipo FROM clientes WHERE id ='" + cliente.getId()+"'");
				rs = stmt.getResultSet();
				
	
				
				if(rs.next()){
					
					stmt.addBatch("UPDATE clientes SET " +
						"DNI = '" + cliente.getDNI() + "', " +
						"tipo = '" + cliente.getTipo() + "', " +
						"nombre = '" + cliente.getName() + "', " +
						"apellidos = '" + cliente.getLastName() + "', " +
						"direccion = '" + cliente.getAdress() + "', " +
						"ciudad = '" + cliente.getCity() + "', " +
						"codigoPostal = " + cliente.getPostalCode() + ", " +
						"numeroTelefono = " + cliente.getTelephoneNumber() + ", " +
						"email = '" + cliente.getEmail() + "' " +
						"WHERE id = " + cliente.getId()
					);
					
					String tipoAnterior = rs.getString(1);
					if(cliente.getTipo().equals(tipoAnterior)){
						if(cliente.getTipo().equals("VIP"))
							stmt.addBatch("UPDATE clientesvip SET descuento = '" + cliente.getDescuento() + "' WHERE idCliente = '" + cliente.getId() +"'");
						
					}else{
						if(cliente.getTipo().equals("VIP"))
							stmt.addBatch("INSERT INTO clientesvip (idCliente, descuento) " + 
									"VALUES('" +
									cliente.getId() + "','" + 
									cliente.getDescuento() + 	
									"')");
						else
							stmt.addBatch("DELETE FROM clientesvip WHERE idCliente = " + cliente.getId());
						
					}
				}
				
				
				int[] editadas = stmt.executeBatch();
				
				for(int i: editadas)
					if(!error)
						error = i != 1;
				
			}
			catch(SQLException ex){
				throw new DAOException(ex);
			}
		}//end else connection == null
		
		return !error;
	}

	@Override
	public boolean borrarCliente(TransferCliente cliente) throws DAOException {
		
		boolean error = false;
		
		Statement stmt = null;
		
		//Get the transction and the connection
		Connection connection = TransactionManager.getInstancia().getTransaction().getResource();
		if(connection == null)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try 
			{	
				connection = BBDDConnection.getConnection();
				
				stmt = connection.createStatement();
				
				int editadas = stmt.executeUpdate("UPDATE clientes SET " +
					"activo = 0 " +
					"WHERE id = " + cliente.getId()  
				);
				
				if(editadas!=1){
					error = true;
				}
			}
			catch(SQLException ex){
				throw new DAOException(ex);
			}
		}
		else
		{
			try 
			{
				stmt = connection.createStatement();
				
				int editadas = stmt.executeUpdate("UPDATE clientes SET " +
					"activo = 0 " +
					"WHERE id = " + cliente.getId()  
				);
				
				if(editadas!=1){
					error = true;
				}
				
			}
			catch(SQLException ex){
				throw new DAOException(ex);
			}
		}
		
		return !error;
	}

	@Override
	public TransferCliente consultarCliente(TransferCliente cliente, int lockMode) throws DAOException {

		

		Statement stmt = null;
		ResultSet rs = null;
		
		//Get the transction and the connection
		Connection connection = TransactionManager.getInstancia().getTransaction().getResource();
		
		TransferCliente clienteSalida = new TransferCliente();
		if(connection == null || lockMode == 0)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try 
			{	
				if(connection == null)
					connection = BBDDConnection.getConnection();
				
				stmt = connection.createStatement();
				stmt.execute("SELECT * FROM clientes " +
				"WHERE id = " + cliente.getId()
				);
				rs = stmt.getResultSet();
				
				if (rs.next()) {
					clienteSalida.setId(rs.getInt(1));
					clienteSalida.setName(rs.getString(2));
					clienteSalida.setLastName(rs.getString(3));
					clienteSalida.setId(rs.getInt(1));
					clienteSalida.setAdress(rs.getString(4));
					clienteSalida.setCity(rs.getString(5));
					clienteSalida.setPostalCode(rs.getInt(6));
					clienteSalida.setTelephoneNumber(rs.getLong(7));
					clienteSalida.setEmail(rs.getString(8));
					clienteSalida.setDNI(rs.getInt(10));
					clienteSalida.setTipo(rs.getString(11));
					clienteSalida.setActivo(rs.getBoolean(9));
					
					if(clienteSalida.getTipo().equals("VIP")){
						stmt.execute("SELECT descuento FROM clientesvip " +
								"WHERE idCliente = " + cliente.getId()
								);
						rs = stmt.getResultSet();
						if (rs.next()) {
							clienteSalida.setDescuento(rs.getFloat(1));
						}else{
							clienteSalida.setId(-1);
						}
					}
					
				}
				else{
					clienteSalida.setId(-1);
				}
			}
			catch(SQLException ex){
				throw new DAOException(ex);
			}
		}
		else
		{
			try 
			{
				
				stmt = connection.createStatement();
				stmt.execute("SELECT * FROM clientes " +
				"WHERE id = " + cliente.getId() + " FOR UPDATE"
				);
				rs = stmt.getResultSet();
				
				if (rs.next()) {
					clienteSalida.setId(rs.getInt(1));
					clienteSalida.setName(rs.getString(2));
					clienteSalida.setLastName(rs.getString(3));
					clienteSalida.setId(rs.getInt(1));
					clienteSalida.setAdress(rs.getString(4));
					clienteSalida.setCity(rs.getString(5));
					clienteSalida.setPostalCode(rs.getInt(6));
					clienteSalida.setTelephoneNumber(rs.getLong(7));
					clienteSalida.setEmail(rs.getString(8));
					clienteSalida.setDNI(rs.getInt(10));
					clienteSalida.setTipo(rs.getString(11));
					clienteSalida.setActivo(rs.getBoolean(9));
					
					if(clienteSalida.getTipo().equals("VIP")){
						stmt.execute("SELECT descuento FROM clientesvip " +
								"WHERE idCliente = " + cliente.getId() + " FOR UPDATE"
								);
						rs = stmt.getResultSet();
						if (rs.next()) {
							clienteSalida.setDescuento(rs.getFloat(1));
						}else{
							clienteSalida.setId(-1);
						}
					}
					
				}
				else{
					clienteSalida.setId(-1);
				}
			}
			catch(SQLException ex){
				throw new DAOException(ex);
			}
		}//End else connection == false
		
		return clienteSalida;
	}
	
	@Override
	public TransferCliente consultarClienteDNI(TransferCliente cliente, int lockMode) throws DAOException {


		Statement stmt = null;
		ResultSet rs = null;
		TransferCliente clienteSalida = new TransferCliente();
		//Get the transction and the connection
		Connection connection = TransactionManager.getInstancia().getTransaction().getResource();

		if(connection == null || lockMode == 0)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try 
			{	
				if(connection == null)
					connection = BBDDConnection.getConnection();
				stmt = connection.createStatement();
				
				stmt.execute("SELECT * FROM clientes " +
				"WHERE dni = " + cliente.getDNI()
				);
				rs = stmt.getResultSet();
				
				if (rs.next()) {
					clienteSalida.setId(rs.getInt(1));
					clienteSalida.setName(rs.getString(2));
					clienteSalida.setLastName(rs.getString(3));
					clienteSalida.setId(rs.getInt(1));
					clienteSalida.setAdress(rs.getString(4));
					clienteSalida.setCity(rs.getString(5));
					clienteSalida.setPostalCode(rs.getInt(6));
					clienteSalida.setTelephoneNumber(rs.getLong(7));
					clienteSalida.setEmail(rs.getString(8));
					clienteSalida.setDNI(rs.getInt(10));
					clienteSalida.setTipo(rs.getString(11));
					clienteSalida.setActivo(rs.getBoolean(9));
					
					if(clienteSalida.getTipo().equals("VIP")){
						stmt.execute("SELECT descuento FROM clientesvip " +
								"WHERE idCliente = " + clienteSalida.getId()
								);
						rs = stmt.getResultSet();
						if (rs.next()) {
							clienteSalida.setDescuento(rs.getFloat(1));
						}else{
							clienteSalida.setId(-1);
						}
					}
				}
				else{
					clienteSalida.setId(-1);
				}
			}
			catch(SQLException ex){
				throw new DAOException(ex);
			}
		}
		else
		{
			try 
			{	
				stmt = connection.createStatement();
				
				stmt.execute("SELECT * FROM clientes " +
				"WHERE dni = " + cliente.getDNI() + " FOR UPDATE"
				);
				rs = stmt.getResultSet();
				
				if (rs.next()) {
					clienteSalida.setId(rs.getInt(1));
					clienteSalida.setName(rs.getString(2));
					clienteSalida.setLastName(rs.getString(3));
					clienteSalida.setId(rs.getInt(1));
					clienteSalida.setAdress(rs.getString(4));
					clienteSalida.setCity(rs.getString(5));
					clienteSalida.setPostalCode(rs.getInt(6));
					clienteSalida.setTelephoneNumber(rs.getLong(7));
					clienteSalida.setEmail(rs.getString(8));
					clienteSalida.setDNI(rs.getInt(10));
					clienteSalida.setTipo(rs.getString(11));
					clienteSalida.setActivo(rs.getBoolean(9));
					
					if(clienteSalida.getTipo().equals("VIP")){
						stmt.execute("SELECT descuento FROM clientesvip " +
								"WHERE idCliente = " + clienteSalida.getId() + " FOR UPDATE"
								);
						rs = stmt.getResultSet();
						if (rs.next()) {
							clienteSalida.setDescuento(rs.getFloat(1));
						}else{
							clienteSalida.setId(-1);
						}
					}
				}
				else{
					clienteSalida.setId(-1);
				}
			}
			catch(SQLException ex){
				throw new DAOException(ex);
			}
		}
		
		return clienteSalida;
	}

	@Override
	public TransferListaClientes principalClientes(int lockMode) throws DAOException{

		
		TransferListaClientes out = new TransferListaClientes();
		out.setLista(new ArrayList<TransferCliente>());

		Statement stmt = null;
		ResultSet rs = null;
		//Get the transction and the connection
		Connection connection = TransactionManager.getInstancia().getTransaction().getResource();

		if(connection == null || lockMode == 0)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try 
			{	
				if(connection == null)
					connection = BBDDConnection.getConnection();
				stmt = connection.createStatement();
			
				stmt.execute("SELECT id, DNI, nombre, apellidos, tipo FROM clientes WHERE activo = 1");
				rs = stmt.getResultSet();
	
				while (rs.next()) {
					TransferCliente newCliente = new TransferCliente();
					newCliente.setId(rs.getInt(1));
					newCliente.setDNI(Integer.parseInt(rs.getString(2)));
					newCliente.setName(rs.getString(3));
					newCliente.setLastName(rs.getString(4));
					newCliente.setTipo(rs.getString(5));
					out.getLista().add(newCliente);
				}
			}
			catch(SQLException ex) {
				throw new DAOException(ex);
			}
		}
		else
		{
			try 
			{	
				stmt = connection.createStatement();
			
				stmt.execute("SELECT id, DNI, nombre, apellidos, tipo FROM clientes WHERE activo = 1 FOR UPDATE");
				rs = stmt.getResultSet();
	
				while (rs.next()) {
					TransferCliente newCliente = new TransferCliente();
					newCliente.setId(rs.getInt(1));
					newCliente.setDNI(Integer.parseInt(rs.getString(2)));
					newCliente.setName(rs.getString(3));
					newCliente.setLastName(rs.getString(4));
					newCliente.setTipo(rs.getString(5));
					out.getLista().add(newCliente);
				}
			}
			catch(SQLException ex) {
				throw new DAOException(ex);
			}
		}
		
		return out;
	}
	
	@Override
	public boolean recuperarCliente(TransferCliente cliente) throws DAOException
	{		
		boolean error = false;
		
		Statement stmt = null;
		
		//Get the transction and the connection
		Connection connection = TransactionManager.getInstancia().getTransaction().getResource();
		
		if(connection == null)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try 
			{	
				connection = BBDDConnection.getConnection();
				stmt = connection.createStatement();
				int editadas = stmt.executeUpdate("UPDATE clientes SET activo = 1 " +
						"WHERE dni = " + cliente.getDNI() + 
						" AND activo = 0");
				
				if(editadas!=1){
					error = true;
				}
			}catch(SQLException ex){
				throw new DAOException(ex);
			}
		}
		else
		{
			try 
			{	
				stmt = connection.createStatement();
				
				int editadas = stmt.executeUpdate("UPDATE clientes SET activo = 1 " +
						"WHERE dni = " + cliente.getDNI() + 
						" AND activo = 0");
				
				if(editadas!=1){
					error = true;
				}
				
			}catch(SQLException ex){
				throw new DAOException(ex);
			}
		}
			
		return !error;
		
	}


	public void bloquearTablas(int lockMode) throws DAOException
	{
		Statement stmt = null;
		//Get the transction and the connection
		Connection connection = TransactionManager.getInstancia().getTransaction().getResource();
		try 
		{	
			stmt = connection.createStatement();
			
			if(lockMode == 1)
				stmt.execute("LOCK TABLES clientes WRITE,clientesvip WRITE");
			else if(lockMode == 2)
				stmt.execute("LOCK TABLES clientes READ,clientesvip READ");
			else if(lockMode == 3)
				stmt.execute("LOCK TABLES clientes WRITE, clientes AS cli READ, clientesvip WRITE, clientesvip AS clivip READ");
		}
		catch(SQLException ex)
		{
			throw new DAOException(ex);
		}
	}
	
	public void desbloquearTablas() throws DAOException
	{
		Statement stmt = null;
		//Get the transction and the connection
		Connection connection = TransactionManager.getInstancia().getTransaction().getResource();
		try 
		{	
			stmt = connection.createStatement();
			stmt.execute("UNLOCK TABLES");
		}
		catch(SQLException ex)
		{
			throw new DAOException(ex);
		}
	}

}
