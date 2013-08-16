/**
 * 
 */
package integracion.ventas.imp;

import integracion.BBDDConnection;
import integracion.DAOException;
import integracion.transaction.transactionManager.TransactionManager;
import integracion.ventas.DAOVentas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import negocio.ventas.TransferListaVentas;
import negocio.ventas.TransferVenta;

public class DAOVentasImp implements DAOVentas {

	@Override
	public boolean creaVenta(TransferVenta transferVenta) throws DAOException {
		
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
				
				stmt.addBatch("INSERT INTO ventas (fecha,idCliente,descuento,activo) " + 
				"VALUES(STR_TO_DATE('" + 
					transferVenta.getFecha() + "','%d-%m-%Y'), '" +
					transferVenta.getIdCliente() + "', '" +
					transferVenta.getDescuento() + "', '" +
					"1')"
				);
				
				for(int i=0; i< transferVenta.getNumLineasVenta(); i++)
					stmt.addBatch("INSERT INTO lineasVenta (idProducto, cantidad, precio, idVenta) " +
							"VALUES(' " +
							transferVenta.getLineaVenta(i).getIdProducto() + "', '" +
							transferVenta.getLineaVenta(i).getCantidad() + "', '"+
							transferVenta.getLineaVenta(i).getPrecio() + "', " +
							"(SELECT max(Id) from ventas))" +
							"");
				
				for(int current: stmt.executeBatch())
					if(current != 1)
						error = true;
				
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
				
				stmt.addBatch("INSERT INTO ventas (fecha,idCliente,descuento,activo) " + 
				"VALUES(STR_TO_DATE('" + 
					transferVenta.getFecha() + "','%d-%m-%Y'), '" +
					transferVenta.getIdCliente() + "', '" +
					transferVenta.getDescuento() + "', '" +
					"1')"
				);
				
				for(int i=0; i< transferVenta.getNumLineasVenta(); i++)
					stmt.addBatch("INSERT INTO lineasVenta (idProducto, cantidad, precio, idVenta) " +
							"VALUES(' " +
							transferVenta.getLineaVenta(i).getIdProducto() + "', '" +
							transferVenta.getLineaVenta(i).getCantidad() + "', '"+
							transferVenta.getLineaVenta(i).getPrecio() + "', " +
							"(SELECT max(Id) from ventas))" +
							"");
				
				for(int current: stmt.executeBatch())
					if(current != 1)
						error = true;
				
			}
			catch(SQLException ex) {
				throw new DAOException(ex);
			}
		}
		
		return !error;
	}

	@Override
	public boolean modificaVenta(TransferVenta transferVenta)
	{
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean devolucion(TransferVenta transferVenta) throws DAOException {
		
		
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
				
				for(int i=0; i< transferVenta.getNumLineasVenta(); i++)
					stmt.addBatch("UPDATE lineasVenta SET cantidad = cantidad - " + transferVenta.getLineaVenta(i).getCantidad() + " " +
							"WHERE idVenta = " + transferVenta.getLineaVenta(i).getIdVenta() +
							" AND idProducto = " + transferVenta.getLineaVenta(i).getIdProducto()
							);
				
				for(int current: stmt.executeBatch())
					if(current != 1)
						error = true;
				
				stmt.execute("DELETE FROM lineasVenta WHERE cantidad = '0' AND IdVenta = '" + transferVenta.getId() + "'");
				
			}
			catch(SQLException ex) {
				throw new DAOException(ex);
			}
		}
		else
		{
			try {
				stmt = connection.createStatement();
				
				for(int i=0; i< transferVenta.getNumLineasVenta(); i++)
					stmt.addBatch("UPDATE lineasVenta SET cantidad = cantidad - " + transferVenta.getLineaVenta(i).getCantidad() + " " +
							"WHERE idVenta = " + transferVenta.getLineaVenta(i).getIdVenta() +
							" AND idProducto = " + transferVenta.getLineaVenta(i).getIdProducto()
							);
				
				for(int current: stmt.executeBatch())
					if(current != 1)
						error = true;
				
				stmt.execute("DELETE FROM lineasVenta WHERE cantidad = '0' AND IdVenta = '" + transferVenta.getId() + "'");
				
			}
			catch(SQLException ex) {
				throw new DAOException(ex);
			}
		}
		
		return !error;
	}

	@Override
	public boolean borraVenta(TransferVenta transferVenta) throws DAOException {
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
				
				int actualizadas = stmt.executeUpdate("UPDATE ventas " +
					"SET activo = '0' " +
					"WHERE id = '" + transferVenta.getId() +
					"' AND activo = '1'"
					);
	
				if(actualizadas != 1)
					error = true;
				
			}catch(SQLException sqlex){
				throw new DAOException(sqlex);
			}
		}
		else
		{
			try {
				stmt = connection.createStatement();
				
				int actualizadas = stmt.executeUpdate("UPDATE ventas " +
					"SET activo = '0' " +
					"WHERE id = '" + transferVenta.getId() +
					"' AND activo = '1'"
					);

				if(actualizadas != 1)
					error = true;
				
			}catch(SQLException sqlex){
				throw new DAOException(sqlex);
			}
		}
		
		return !error;
	}

	@Override
	public TransferVenta consultaVenta(TransferVenta transferVenta, int lockMode) throws DAOException
	{
		
		TransferVenta out = new TransferVenta();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		//Get the connection from the transaction
		Connection connection = null; try{connection = (Connection)TransactionManager.getInstancia().getTransaction().getResource(); }catch(ClassCastException ex){};
		if(connection == null || lockMode == 0)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try
			{ 
				if(connection == null)
					connection = BBDDConnection.getConnection();
				
				stmt = connection.createStatement();
				DateFormat formateador = new SimpleDateFormat("dd-MM-YYYY");
				stmt.execute("SELECT id,fecha,idCliente,descuento FROM ventas " +
				"WHERE id = '" + transferVenta.getId() +
				"' AND activo = '1'");
				rs = stmt.getResultSet();
	
				if(rs.next()) {
					
					out.setId(rs.getInt(1));
					out.setFecha(formateador.format(rs.getDate(2)));
					out.setIdCliente(rs.getInt(3));
					out.setDescuento(rs.getFloat(4));
					
					stmt.execute("SELECT * FROM lineasVenta " +
							"WHERE idVenta = '" + transferVenta.getId() + "'");
					
					rs = stmt.getResultSet();
					while(rs.next()) {
						out.addLineaVenta(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getInt(4));
					}
					
				}else{
					out.setId(-1);
				}
			}catch(SQLException sqlex){
				throw new DAOException(sqlex);
			}
		}
		else
		{
			try {
				stmt = connection.createStatement();
				DateFormat formateador = new SimpleDateFormat("dd-MM-YYYY");
				stmt.execute("SELECT id,fecha,idCliente,descuento FROM ventas " +
				"WHERE id = '" + transferVenta.getId() +
				"' AND activo = '1' FOR UPDATE");
				rs = stmt.getResultSet();

				if(rs.next()) {
					
					out.setId(rs.getInt(1));
					out.setFecha(formateador.format(rs.getDate(2)));
					out.setIdCliente(rs.getInt(3));
					out.setDescuento(rs.getFloat(4));
					
					stmt.execute("SELECT * FROM lineasVenta " +
							"WHERE idVenta = '" + transferVenta.getId() + "' FOR UPDATE");
					
					rs = stmt.getResultSet();
					while(rs.next()) {
						out.addLineaVenta(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getInt(4));
					}
					
				}else{
					out.setId(-1);
				}
			}catch(SQLException sqlex){
				throw new DAOException(sqlex);
			}
		}
		
		return out;
	}

	@Override
	public TransferListaVentas consultaListadoVentas(int lockMode) throws DAOException {
		
		TransferListaVentas out = new TransferListaVentas();
		out.setListaVentas(new ArrayList<TransferVenta>());
		
		Statement stmt = null;
		ResultSet rs = null;
		
		//Get the connection from the transaction
		Connection connection = null; try{connection = (Connection)TransactionManager.getInstancia().getTransaction().getResource(); }catch(ClassCastException ex){};
		if(connection == null || lockMode == 0)
		{
			//if we dont find the transaction, make the sentence with no transaction
			
			try
			{
				if(connection == null)
					connection = BBDDConnection.getConnection();
				
				stmt = connection.createStatement();
				DateFormat formateador = new SimpleDateFormat("dd-MM-YYYY");
				stmt.execute("SELECT id, fecha, idCliente FROM ventas WHERE activo = 1");
				rs = stmt.getResultSet();
				
				TransferVenta aux;
				while(rs.next()) {
					
					aux = new TransferVenta();
					aux.setId(rs.getInt(1));
					aux.setFecha(formateador.format(rs.getDate(2)));
					aux.setIdCliente(rs.getInt(3));
					out.getListaVentas().add(aux);				
					
				}
				
			}catch(SQLException sqlex){
				throw new DAOException(sqlex);
			}
		}
		else
		{
			try {
				stmt = connection.createStatement();
				DateFormat formateador = new SimpleDateFormat("dd-MM-YYYY");
				stmt.execute("SELECT id, fecha, idCliente FROM ventas WHERE activo = 1 FOR UPDATE");
				rs = stmt.getResultSet();
				
				TransferVenta aux;
				while(rs.next()) {
					
					aux = new TransferVenta();
					aux.setId(rs.getInt(1));
					aux.setFecha(formateador.format(rs.getDate(2)));
					aux.setIdCliente(rs.getInt(3));
					out.getListaVentas().add(aux);				
					
				}
				
			}catch(SQLException sqlex){
				throw new DAOException(sqlex);
			}
		}
		return out;
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