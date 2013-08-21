package integracion.marcas.imp;

/*
 * External imports
 */


/*
 * Internal imports
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import negocio.marcas.TransferListaMarcas;
import negocio.marcas.TransferMarca;
import integracion.BBDDConnection;
import integracion.DAOException;
import integracion.marcas.DAOMarcas;
import integracion.transaction.transactionManager.TransactionManager;

public class DAOMarcasImp implements DAOMarcas
{

	@Override
	public boolean crearMarca(TransferMarca marca) throws DAOException 
	{
		boolean error = false;
		
		Statement stmt = null;
		
		//Get the connection from the transaction
		Connection connection = null;
		try{connection = (Connection)TransactionManager.getInstancia().getTransaction().getResource();
		}catch(ClassCastException ex){};
		
		if(connection == null)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try 
			{
				connection = BBDDConnection.getConnection();
				stmt = connection.createStatement();
				int editadas = stmt.executeUpdate("INSERT INTO marcas (nombre) VALUES('"+ 
				marca.getNombre() + 
				"')"
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
				
				int editadas = stmt.executeUpdate("INSERT INTO marcas (nombre) VALUES('"+ 
				marca.getNombre() + 
				"')"
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
	public boolean recuperarMarca(TransferMarca marca) throws DAOException
	{
		boolean error = false;
		
		Statement stmt = null;
		
		//Get the connection from the transaction
		Connection connection = null;
		try{connection = (Connection)TransactionManager.getInstancia().getTransaction().getResource();
		}catch(ClassCastException ex){};
		
		if(connection == null)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try 
			{
				connection = BBDDConnection.getConnection();
				stmt = connection.createStatement();
				int editadas = stmt.executeUpdate("UPDATE marcas SET activo = 1 WHERE nombre LIKE '" + marca.getNombre()+"'");
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
				
				int editadas = stmt.executeUpdate("UPDATE marcas SET activo = 1 WHERE nombre LIKE '" + marca.getNombre()+"'");
				if(editadas!=1){
					error = true;
				}
				
			}catch(SQLException ex){
				throw new DAOException(ex);
			}
		}
			
		return !error;
		
	}
	
	@Override
	public boolean editarMarca(TransferMarca marca) throws DAOException 
	{
		boolean error = false;
		
		Statement stmt = null;
		
		//Get the connection from the transaction
		Connection connection = null;
		try{connection = (Connection)TransactionManager.getInstancia().getTransaction().getResource();
		}catch(ClassCastException ex){};
		
		if(connection == null)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try 
			{
				connection = BBDDConnection.getConnection();
				stmt = connection.createStatement();
				int editadas = stmt.executeUpdate("UPDATE marcas SET " +
					"nombre = '" + marca.getNombre() + "' " +
					"WHERE id = '" + marca.getId()+"'"
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
				
				int editadas = stmt.executeUpdate("UPDATE marcas SET " +
					"nombre = '" + marca.getNombre() + "' " +
					"WHERE id = '" + marca.getId()+"'"
				);
				if(editadas!=1)
				{
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
	public TransferListaMarcas principalMarca(int lockMode) throws DAOException 
	{
		
		TransferListaMarcas out = new TransferListaMarcas();
		out.setLista(new ArrayList<TransferMarca>());

		Statement stmt = null;
		ResultSet rs = null;
		
		//Get the connection from the transaction
		Connection connection = null;
		try{connection = (Connection)TransactionManager.getInstancia().getTransaction().getResource();
		}catch(ClassCastException ex){};
		
		if(connection == null || lockMode == 0)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try 
			{
				if(connection == null)
					connection = BBDDConnection.getConnection();
				
				stmt = connection.createStatement();
				
				stmt.execute("SELECT id, nombre FROM marcas WHERE activo = 1");
				rs = stmt.getResultSet();
				while (rs.next()) 
				{
					TransferMarca newMarca = new TransferMarca();
					newMarca.setNombre(rs.getString(2));
					newMarca.setId(rs.getInt(1));
					out.getLista().add(newMarca);
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
				
				stmt.execute("SELECT id, nombre FROM marcas WHERE activo = 1 FOR UPDATE");
				rs = stmt.getResultSet();
				while (rs.next()) 
				{
					TransferMarca newMarca = new TransferMarca();
					newMarca.setNombre(rs.getString(2));
					newMarca.setId(rs.getInt(1));
					out.getLista().add(newMarca);
				}
			}
			catch(SQLException ex) {
				throw new DAOException(ex);
			}
		}
		return out;
	}

	@Override
	public TransferMarca consultarMarca(TransferMarca marca, int lockMode) throws DAOException 
	{
		Statement stmt = null;
		ResultSet rs = null;
		
		//Get the connection from the transaction
		Connection connection = null;
		try{connection = (Connection)TransactionManager.getInstancia().getTransaction().getResource();
		}catch(ClassCastException ex){};
		
		if(connection == null || lockMode == 0)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try 
			{
				if(connection == null)
					connection = BBDDConnection.getConnection();
				
				stmt = connection.createStatement();
				
				stmt.execute("SELECT id, nombre, activo " +
				"FROM marcas " +
				"WHERE id = '" + marca.getId()+"'"
				);
				rs = stmt.getResultSet();
				
				if (rs.next()) {
					marca.setId(rs.getInt(1));
					marca.setNombre(rs.getString(2));
					marca.setActiva(rs.getBoolean(3));
				}else{
					marca.setId(-1);
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
				
				stmt.execute("SELECT id, nombre, activo " +
				"FROM marcas " +
				"WHERE id = '" + marca.getId()+"' FOR UPDATE"
				);
				rs = stmt.getResultSet();
				
				if (rs.next()) {
					marca.setId(rs.getInt(1));
					marca.setNombre(rs.getString(2));
					marca.setActiva(rs.getBoolean(3));
				}else{
					marca.setId(-1);
				}
			}
			catch(SQLException ex){
				throw new DAOException(ex);
			}
		}
		
		return marca;
		
	}

	@Override
	public boolean borrarMarca(TransferMarca marca) throws DAOException 
	{
		
		boolean error = false;
		
		Statement stmt = null;
		
		//Get the connection from the transaction
		Connection connection = null;
		try{connection = (Connection)TransactionManager.getInstancia().getTransaction().getResource();
		}catch(ClassCastException ex){};
		
		if(connection == null)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try 
			{
				connection = BBDDConnection.getConnection();
				stmt = connection.createStatement();
				
				int editadas = stmt.executeUpdate("UPDATE marcas SET activo = 0 WHERE id = '" + marca.getId()+"'");
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
				
				int editadas = stmt.executeUpdate("UPDATE marcas SET activo = 0 WHERE id = '" + marca.getId()+"'");
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
	public TransferMarca consultarMarcaNombre(TransferMarca marca, int lockMode) throws DAOException 
	{		
		Statement stmt = null;
		ResultSet rs = null;
		
		//Get the connection from the transaction
		Connection connection = null;
		try{connection = (Connection)TransactionManager.getInstancia().getTransaction().getResource();
		}catch(ClassCastException ex){};
		
		if(connection == null || lockMode == 0)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try 
			{
				if(connection == null)
					connection = BBDDConnection.getConnection();
				stmt = connection.createStatement();
				
				stmt.execute("SELECT id, nombre, activo " +
				"FROM marcas " +
				"WHERE nombre LIKE '" + marca.getNombre()+"'"
				);
				rs = stmt.getResultSet();
				
				if (rs.next()) {
					marca.setId(rs.getInt(1));
					marca.setNombre(rs.getString(2));
					marca.setActiva(rs.getBoolean(3));
				}else{
					marca.setId(-1);
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
				
				stmt.execute("SELECT id, nombre, activo " +
				"FROM marcas " +
				"WHERE nombre LIKE '" + marca.getNombre()+"' FOR UPDATE"
				);
				rs = stmt.getResultSet();
				
				if (rs.next()) {
					marca.setId(rs.getInt(1));
					marca.setNombre(rs.getString(2));
					marca.setActiva(rs.getBoolean(3));
				}else{
					marca.setId(-1);
				}
			}
			catch(SQLException ex){
				throw new DAOException(ex);
			}
		}
		
		return marca;
	}
}
