package integracion.proveedores.imp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import negocio.proveedores.TransferListaProveedores;
import negocio.proveedores.TransferProveedor;


import integracion.BBDDConnection;
import integracion.DAOException;
import integracion.proveedores.DAOProveedores;
import integracion.transaction.transactionManager.TransactionManager;

public class DAOProveedoresImp implements DAOProveedores{

	@Override
	public boolean crearProveedor(TransferProveedor proveedor) throws DAOException {

		boolean error = false;

		Statement stmt = null;

		//Get the connection from the transaction
		Connection connection = null; try{connection = (Connection)TransactionManager.getInstancia().getTransaction().getResource(); }catch(ClassCastException ex){};
		if(connection == null)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try 
			{
				connection = BBDDConnection.getConnection();

				stmt = connection.createStatement();
				//execute the querys
				stmt.addBatch("INSERT INTO proveedores (nombre, numeroTelefono, email, nif) " + 
						"VALUES('" + 
						proveedor.getName() + "', '" +
						proveedor.getTelephoneNumber() + "', '" +
						proveedor.getEmail()+ "', '" +
						proveedor.getNif()+
						"')"
						);

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

				stmt.addBatch("INSERT INTO proveedores (nombre, numeroTelefono, email, nif) " + 
						"VALUES('" + 
						proveedor.getName() + "', '" +
						proveedor.getTelephoneNumber() + "', '" +
						proveedor.getEmail()+ "', '" +
						proveedor.getNif()+
						"')"
						);


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
	public boolean editarProveedor(TransferProveedor proveedor) throws DAOException {

		boolean error = false;

		Statement stmt = null;
		//Get the connection from the transaction
		Connection connection = null; try{connection = (Connection)TransactionManager.getInstancia().getTransaction().getResource(); }catch(ClassCastException ex){};
		if(connection == null)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try 
			{	
				connection = BBDDConnection.getConnection();
				stmt = connection.createStatement();

				stmt.execute("SELECT tipo FROM proveedores WHERE id ='" + proveedor.getId());

				stmt.addBatch("UPDATE proveedores SET " +
						"nombre = '" + proveedor.getName() + "', " +
						"numeroTelefono = " + proveedor.getTelephoneNumber() + ", " +
						"email = '" + proveedor.getEmail() + "', " +
						"nif = '" + proveedor.getNif() + "' " +
						"WHERE id = " + proveedor.getId()
						);

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

				stmt.addBatch("UPDATE proveedores SET " +
						"nombre = '" + proveedor.getName() + "', " +
						"numeroTelefono = " + proveedor.getTelephoneNumber() + ", " +
						"email = '" + proveedor.getEmail() + "', " +
						"nif = '" + proveedor.getNif() + "' " +
						"WHERE id = " + proveedor.getId()
						);

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
	public boolean borrarProveedor(TransferProveedor proveedor) throws DAOException {
		boolean error = false;

		Statement stmt = null;

		//Get the transction and the connection
		Connection connection = null; try{connection = (Connection)TransactionManager.getInstancia().getTransaction().getResource(); }catch(ClassCastException ex){};
		if(connection == null)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try 
			{	
				connection = BBDDConnection.getConnection();

				stmt = connection.createStatement();

				int editadas = stmt.executeUpdate("UPDATE proveedores SET activo = '0'" +
						"WHERE id = " + proveedor.getId()
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
				int editadas = stmt.executeUpdate("UPDATE proveedores SET activo = '0'" +
						"WHERE id = " + proveedor.getId()
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
	public TransferProveedor consultarProveedor(TransferProveedor proveedor, int lockMode) throws DAOException {

		Statement stmt = null;
		ResultSet rs = null;

		//Get the transction and the connection
		Connection connection = null; try{connection = (Connection)TransactionManager.getInstancia().getTransaction().getResource(); }catch(ClassCastException ex){};

		TransferProveedor proveedorSalida = new TransferProveedor();
		if(connection == null || lockMode == 0)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try 
			{	
				if(connection == null)
					connection = BBDDConnection.getConnection();

				stmt = connection.createStatement();
				stmt.execute("SELECT id, nombre, numeroTelefono, email, nif,activo FROM proveedores " +
						"WHERE id = " + proveedor.getId()
						);
				rs = stmt.getResultSet();

				if (rs.next()) {
					proveedorSalida.setId(rs.getInt(1));
					proveedorSalida.setName(rs.getString(2));				
					proveedorSalida.setTelephoneNumber(rs.getLong(3));
					proveedorSalida.setEmail(rs.getString(4));
					proveedorSalida.setNif(rs.getInt(5));
					proveedorSalida.setActivo(rs.getBoolean(6));
				}
				else{
					proveedorSalida.setId(-1);
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
				stmt.execute("SELECT id, nombre, numeroTelefono, email, nif,activo FROM proveedores " +
						"WHERE id = " + proveedor.getId() + " FOR UPDATE"
						);

				rs = stmt.getResultSet();

				if (rs.next()) {
					proveedorSalida.setId(rs.getInt(1));
					proveedorSalida.setName(rs.getString(2));				
					proveedorSalida.setTelephoneNumber(rs.getLong(3));
					proveedorSalida.setEmail(rs.getString(4));
					proveedorSalida.setNif(rs.getInt(5));
					proveedorSalida.setActivo(rs.getBoolean(6));
				}
				else{
					proveedorSalida.setId(-1);
				}
			}
			catch(SQLException ex){
				throw new DAOException(ex);
			}
		}//End else connection == false

		return proveedorSalida;
	}

	@Override
	public TransferProveedor consultarProveedorNIF(TransferProveedor proveedor, int lockMode) throws DAOException {

		Statement stmt = null;
		ResultSet rs = null;
		TransferProveedor proveedorSalida = new TransferProveedor();
		//Get the transction and the connection
		Connection connection = null; try{connection = (Connection)TransactionManager.getInstancia().getTransaction().getResource(); }catch(ClassCastException ex){};

		if(connection == null || lockMode == 0)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try 
			{	
				if(connection == null)
					connection = BBDDConnection.getConnection();
				stmt = connection.createStatement();

				stmt.execute("SELECT id, nombre, numeroTelefono, email, nif,activo FROM proveedores " +
						"WHERE nif = " + proveedor.getNif()
						);
				rs = stmt.getResultSet();

				if (rs.next()) {
					proveedorSalida.setId(rs.getInt(1));
					proveedorSalida.setName(rs.getString(2));				
					proveedorSalida.setTelephoneNumber(rs.getLong(3));
					proveedorSalida.setEmail(rs.getString(4));
					proveedorSalida.setNif(rs.getInt(5));
					proveedorSalida.setActivo(rs.getBoolean(6));
				}
				else{
					proveedorSalida.setId(-1);
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

				stmt.execute("SELECT id, nombre, numeroTelefono, email, nif,activo FROM proveedores " +
						"WHERE nif = " + proveedor.getNif() + " FOR UPDATE"
						);
				rs = stmt.getResultSet();

				if (rs.next()) {
					proveedorSalida.setId(rs.getInt(1));
					proveedorSalida.setName(rs.getString(2));				
					proveedorSalida.setTelephoneNumber(rs.getLong(3));
					proveedorSalida.setEmail(rs.getString(4));
					proveedorSalida.setNif(rs.getInt(5));
					proveedorSalida.setActivo(rs.getBoolean(6));
				}
				else{
					proveedorSalida.setId(-1);
				}
			}
			catch(SQLException ex){
				throw new DAOException(ex);
			}
		}

		return proveedorSalida;
	}


	@Override
	public TransferListaProveedores principalProveedores(int lockMode) throws DAOException{
		TransferListaProveedores out = new TransferListaProveedores();
		out.setLista(new ArrayList<TransferProveedor>());

		Statement stmt = null;
		ResultSet rs = null;
		//Get the transction and the connection
		Connection connection = null; try{connection = (Connection)TransactionManager.getInstancia().getTransaction().getResource(); }catch(ClassCastException ex){};

		if(connection == null || lockMode == 0)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try 
			{	
				if(connection == null)
					connection = BBDDConnection.getConnection();
				stmt = connection.createStatement();

				stmt.execute("SELECT id, nombre, nif FROM proveedores WHERE activo = 1");
				rs = stmt.getResultSet();
				while(rs.next()){
					TransferProveedor newProveedor = new TransferProveedor();
					newProveedor.setId(rs.getInt(1));
					newProveedor.setName(rs.getString(2));
					newProveedor.setNif(rs.getInt(3));
					out.getLista().add(newProveedor);
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

				stmt.execute("SELECT id, nombre, nif FROM proveedores WHERE activo = 1 FOR UPDATE");
				rs = stmt.getResultSet();
				while(rs.next()){
					TransferProveedor newProveedor = new TransferProveedor();
					newProveedor.setId(rs.getInt(1));
					newProveedor.setName(rs.getString(2));
					newProveedor.setNif(rs.getInt(3));
					out.getLista().add(newProveedor);
				}
			}
			catch(SQLException ex) {
				throw new DAOException(ex);
			}
		}

		return out;
	}

	@Override
	public boolean recuperarProveedor(TransferProveedor proveedor) throws DAOException
	{		
		boolean error = false;

		Statement stmt = null;

		//Get the transction and the connection
		Connection connection = null; try{connection = (Connection)TransactionManager.getInstancia().getTransaction().getResource(); }catch(ClassCastException ex){};

		if(connection == null)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try 
			{				
				connection = BBDDConnection.getConnection();
				stmt = connection.createStatement();
				int editadas = stmt.executeUpdate("UPDATE proveedores SET activo = 1 " +
						"WHERE nif = " + proveedor.getNif() + 
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
			
				int editadas = stmt.executeUpdate("UPDATE proveedores SET activo = 1 " +
						"WHERE nif = " + proveedor.getNif() + 
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
		Connection connection = null; try{connection = (Connection)TransactionManager.getInstancia().getTransaction().getResource(); }catch(ClassCastException ex){};
		try 
		{	
			stmt = connection.createStatement();
			
			if(lockMode == 1)
				stmt.execute("LOCK TABLES proveedores WRITE");
			else if(lockMode == 2)
				stmt.execute("LOCK TABLES proveedores READ");
			else if(lockMode == 3)
				stmt.execute("LOCK TABLES proveedores WRITE, proveedores AS prov READ");
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
		Connection connection = null; try{connection = (Connection)TransactionManager.getInstancia().getTransaction().getResource(); }catch(ClassCastException ex){};
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
