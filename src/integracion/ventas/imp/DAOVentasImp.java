package integracion.ventas.imp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import negocio.productos.TransferProducto;
import negocio.ventas.TransferListaVentas;
import negocio.ventas.TransferVenta;
import integracion.BBDDConnection;
import integracion.DAOException;
import integracion.transaction.Transaction;
import integracion.transaction.transactionManager.TransactionManager;
import integracion.ventas.DAOVentas;

public class DAOVentasImp implements DAOVentas {

	@Override
	public Integer abrirVenta(TransferVenta venta)
			throws DAOException {
		
		Integer id = null;
		
		Connection conn = null;
		Transaction trans = TransactionManager.getInstancia().getTransaction();
		if (trans != null) 
			conn = (Connection) trans.getResource();
		else 
			conn = BBDDConnection.getConnection();

		try {
			Statement stmt = conn.createStatement();
			int actualizadas = stmt.executeUpdate("INSERT INTO ventas (fecha,idCliente,descuento) " + 
					"VALUES(STR_TO_DATE('" + 
						venta.getFecha() + "','%d-%m-%Y'), '" +
						venta.getIdCliente() + "', '" +
						venta.getDescuento() + "')"
					);
			
			if (actualizadas == 1) {
				boolean result = stmt.execute("SELECT max(id) as id FROM ventas");
				if (result) {
					ResultSet rs = stmt.getResultSet();
					rs.next();
					id = rs.getInt("id");
				}
			}
			
		} catch (SQLException ex) {
			throw new DAOException(ex);
		}
		
		return id;
	}

	@Override
	public boolean agregarProducto(TransferVenta venta,
			TransferProducto producto, Integer cantidad) throws DAOException {
		
		boolean result = true;
		
		Connection conn = null;
		Transaction trans = TransactionManager.getInstancia().getTransaction();
		if (trans != null) 
			conn = (Connection) trans.getResource();
		else 
			conn = BBDDConnection.getConnection();

		try {
			Statement stmt = conn.createStatement();
			int actualizadas = stmt.executeUpdate("INSERT INTO lineasventa (idProducto, cantidad, precio, idVenta) " +
					"VALUES('" +
					producto.getId() + "', '" +
					cantidad + "', '"+
					producto.getPrecio() + "', '" +
					venta.getId() + "')");
			
			result = actualizadas == 1;
			
		} catch (SQLException ex) {
			throw new DAOException(ex);
		}
		
		return result;
	}
	
	@Override
	public boolean modificarProducto(TransferVenta transferVenta,
			TransferProducto producto, Integer cantidad) throws DAOException {
		boolean result = true;
		
		Connection conn = null;
		Transaction trans = TransactionManager.getInstancia().getTransaction();
		if (trans != null) 
			conn = (Connection) trans.getResource();
		else 
			conn = BBDDConnection.getConnection();

		try {
			Statement stmt = conn.createStatement();
			int actualizadas = stmt.executeUpdate("UPDATE lineasventa SET cantidad = '" + cantidad + 
					"' WHERE idProducto = '" + producto.getId() + "' AND idVenta = '" + transferVenta.getId() + "'");
			
			if(actualizadas != 1)
				result = false;
			
		} catch (SQLException ex) {
			throw new DAOException(ex);
		}
		
		return result;
	}

	@Override
	public boolean quitarProducto(TransferVenta venta, TransferProducto producto)
			throws DAOException {
		
		boolean result = true;
		
		Connection conn = null;
		Transaction trans = TransactionManager.getInstancia().getTransaction();
		if (trans != null) 
			conn = (Connection) trans.getResource();
		else 
			conn = BBDDConnection.getConnection();

		try {
			Statement stmt = conn.createStatement();
			int actualizadas = stmt.executeUpdate("DELETE FROM lineasventa WHERE idProducto = '" + producto.getId() +
					"' AND idVenta = '" + venta.getId() + "'");
			
			if(actualizadas != 1)
				result = false;
			
		} catch (SQLException ex) {
			throw new DAOException(ex);
		}
		
		return result;
	}

	@Override
	public boolean cerrarVenta(TransferVenta venta) throws DAOException {
		
		boolean error = false;
		
		Connection conn = null;
		Transaction trans = TransactionManager.getInstancia().getTransaction();
		if (trans != null) 
			conn = (Connection) trans.getResource();
		else 
			conn = BBDDConnection.getConnection();

		try {
			Statement stmt = conn.createStatement();
			int actualizadas = stmt.executeUpdate("UPDATE ventas " +
					"SET cerrada = '1' " +
					"WHERE id = '" + venta.getId() +
					"' AND cerrada = '0'"
					);

			if(actualizadas != 1)
					error = true;
			
		} catch (SQLException ex) {
			throw new DAOException(ex);
		}
		
		return !error;
	}

	@Override
	public TransferVenta consultaVenta(TransferVenta venta)
			throws DAOException {
		
		TransferVenta consulta = new TransferVenta();
		
		Connection conn = null;
		Transaction trans = TransactionManager.getInstancia().getTransaction();
		if (trans != null) 
			conn = (Connection) trans.getResource();
		else 
			conn = BBDDConnection.getConnection();

		try {
			Statement stmt = conn.createStatement();
			DateFormat formateador = new SimpleDateFormat("dd-MM-YYYY");
			stmt.execute("SELECT id,fecha,idCliente,descuento,cerrada FROM ventas " +
			"WHERE id = '" + venta.getId() +
			"' AND activo = '1'");
			
			ResultSet rs = stmt.getResultSet();

			if(rs.next()) {
				
				consulta.setId(rs.getInt("id"));
				consulta.setFecha(formateador.format(rs.getDate("fecha")));
				consulta.setIdCliente(rs.getInt("idCliente"));
				consulta.setDescuento(rs.getFloat("descuento"));
				consulta.setCerrada(rs.getInt("cerrada") == 1);
				
				stmt.execute("SELECT * FROM lineasventa " +
						"WHERE idVenta = '" + venta.getId() + "'");
				
				rs = stmt.getResultSet();
				while(rs.next()) 
					consulta.addLineaVenta(rs.getInt("idProducto"), rs.getInt("cantidad"), rs.getDouble("precio"), rs.getInt("idVenta"));
			}else
				consulta.setId(-1);
			
		} catch (SQLException ex) {
			throw new DAOException(ex);
		}
		
		return consulta;
	}

	@Override
	public TransferListaVentas consultaListadoVentas()
			throws DAOException {

		TransferListaVentas consulta = null;
		
		Connection conn = null;
		Transaction trans = TransactionManager.getInstancia().getTransaction();
		if (trans != null) 
			conn = (Connection) trans.getResource();
		else 
			conn = BBDDConnection.getConnection();

		try {
			Statement stmt = conn.createStatement();
			DateFormat formateador = new SimpleDateFormat("dd-MM-YYYY");
			stmt.execute("SELECT id, fecha, idCliente, cerrada FROM ventas WHERE activo = 1");
			ResultSet rs = stmt.getResultSet();
			
			consulta = new TransferListaVentas();
			TransferVenta aux;
			while(rs.next()) {
				aux = new TransferVenta();
				aux.setId(rs.getInt("id"));
				aux.setFecha(formateador.format(rs.getDate("fecha")));
				aux.setIdCliente(rs.getInt("idCliente"));
				aux.setCerrada(rs.getInt("cerrada") == 1);
				consulta.getListaVentas().add(aux);					
			}
			
		} catch (SQLException ex) {
			throw new DAOException(ex);
		}
		
		return consulta;
		
	}

	@Override
	public boolean borraVenta(TransferVenta transferVenta) throws DAOException {

		
		boolean error = false;
		
		Connection conn = null;
		Transaction trans = TransactionManager.getInstancia().getTransaction();
		if (trans != null) 
			conn = (Connection) trans.getResource();
		else 
			conn = BBDDConnection.getConnection();

		try {
			Statement stmt = conn.createStatement();
			int actualizadas = stmt.executeUpdate("UPDATE ventas " +
					"SET activo = '0' " +
					"WHERE id = '" + transferVenta.getId() +
					"' AND activo = '1'"
					);

			if(actualizadas != 1)
					error = true;
			
		} catch (SQLException ex) {
			throw new DAOException(ex);
		}
		
		return !error;
		
	}	

}
