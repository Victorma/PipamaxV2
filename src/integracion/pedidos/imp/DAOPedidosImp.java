package integracion.pedidos.imp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import negocio.pedidos.TransferListaPedidos;
import negocio.pedidos.TransferPedido;
import negocio.productos.TransferProducto;
import negocio.proveedores.TransferProveedor;
import integracion.BBDDConnection;
import integracion.DAOException;
import integracion.pedidos.DAOPedidos;
import integracion.transaction.transactionManager.TransactionManager;


public class DAOPedidosImp implements DAOPedidos {

	@Override
	public boolean crearPedido(TransferPedido pedido) throws DAOException
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
					
				stmt.addBatch("INSERT INTO pedidos (id_proveedor,fecha, estado) " + 
						"VALUES(" +
						pedido.getIdProveedor() +
						", STR_TO_DATE('"+ pedido.getFecha() +"','%d-%m-%Y'), 'P')" 
					);
				
				for(int i=0; i< pedido.getNumLineasPedido(); i++)
					stmt.addBatch("INSERT INTO linea_pedido (id_pedido, id_prod, precio_uni, cantidad) " + 
							"VALUES(" +
							"(SELECT coalesce(MAX(id),'1') FROM pedidos), " +
							pedido.getLineaPedido(i).getIdProducto() + ", " +
							pedido.getLineaPedido(i).getPrecio() + ", " +
							pedido.getLineaPedido(i).getCantidad() + ")"
						);
				
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
					
				stmt.addBatch("INSERT INTO pedidos (id_proveedor,fecha, estado) " + 
						"VALUES(" +
						pedido.getIdProveedor() +
						", STR_TO_DATE('"+ pedido.getFecha() +"','%d-%m-%Y'), 'P')" 
					);
				
				for(int i=0; i< pedido.getNumLineasPedido(); i++)
					stmt.addBatch("INSERT INTO linea_pedido (id_pedido, id_prod, precio_uni, cantidad) " + 
							"VALUES(" +
							"(SELECT coalesce(MAX(id),'1') FROM pedidos), " +
							pedido.getLineaPedido(i).getIdProducto() + ", " +
							pedido.getLineaPedido(i).getPrecio() + ", " +
							pedido.getLineaPedido(i).getCantidad() + ")"
						);
				
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
	public boolean completarPedido(TransferPedido pedido) throws DAOException {
		
		boolean error = false;
		
		Statement stmt = null;
		
		//Get the connection from the transaction
		Connection connection = null;
		try{connection = (Connection)TransactionManager.getInstancia().getTransaction().getResource();
		}catch(ClassCastException ex){};
		
		if(connection == null)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try {	
				connection = BBDDConnection.getConnection();
				stmt = connection.createStatement();
				int editadas = stmt.executeUpdate("UPDATE pedidos SET " +
						"estado = 'C' " +
						"WHERE id = " + pedido.getId()
					);
				if(editadas != 1){
					error = true;
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
				
				int editadas = stmt.executeUpdate("UPDATE pedidos SET " +
						"estado = 'C' " +
						"WHERE id = " + pedido.getId()
					);
				if(editadas != 1){
					error = true;
				}
				
			}
			catch(SQLException ex) {
				throw new DAOException(ex);
			}
		}
		
		return !error;
	}

	@Override
	public boolean borrarPedido(TransferPedido pedido) throws DAOException {
		boolean error = false;


		Statement stmt = null;
		
		//Get the connection from the transaction
		Connection connection = null;
		try{connection = (Connection)TransactionManager.getInstancia().getTransaction().getResource();
		}catch(ClassCastException ex){};
		
		if(connection == null)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try {	
				connection = BBDDConnection.getConnection();
				stmt = connection.createStatement();
				int editadas = stmt.executeUpdate("UPDATE pedidos SET " +
						"activo = 0 " +
						"WHERE id = " + pedido.getId() + " AND activo = 1"
					);
				if(editadas != 1){
					error = true;
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
				
				int editadas = stmt.executeUpdate("UPDATE pedidos SET " +
						"activo = 0 " +
						"WHERE id = " + pedido.getId() + " AND activo = 1"
					);
				if(editadas != 1){
					error = true;
				}
				
			}
			catch(SQLException ex) {
				throw new DAOException(ex);
			}
		}
		
		return !error;
	}

	@Override
	public TransferPedido consultarPedido(TransferPedido pedido, int lockMode) throws DAOException {
		
		TransferPedido out = new TransferPedido();

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
				
				stmt.execute("SELECT id, fecha, id_proveedor, estado FROM pedidos " +
				"WHERE id = '" + pedido.getId() +
				"' AND activo = '1'");
				rs = stmt.getResultSet();
	
				if(rs.next()) {
					
					out.setId(rs.getInt(1));
					out.setFecha(rs.getString(2));
					out.setIdProveedor(rs.getInt(3));
					out.setEstado(rs.getString(4).toCharArray()[0]);
					
					stmt.execute("SELECT id_prod, cantidad, precio_uni, id_pedido FROM linea_pedido " +
							"WHERE id_pedido = '" + pedido.getId() + "'");
					
					rs = stmt.getResultSet();
					while(rs.next()) {
						out.addLineaPedido(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getInt(4));
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
				
				stmt.execute("SELECT id, fecha, id_proveedor, estado FROM pedidos " +
				"WHERE id = '" + pedido.getId() +
				"' AND activo = '1' FOR UPDATE");
				rs = stmt.getResultSet();
	
				if(rs.next()) {
					
					out.setId(rs.getInt(1));
					out.setFecha(rs.getString(2));
					out.setIdProveedor(rs.getInt(3));
					out.setEstado(rs.getString(4).toCharArray()[0]);
					
					stmt.execute("SELECT id_prod, cantidad, precio_uni, id_pedido FROM linea_pedido " +
							"WHERE id_pedido = '" + pedido.getId() + "' FOR UPDATE");
					
					rs = stmt.getResultSet();
					while(rs.next()) {
						out.addLineaPedido(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getInt(4));
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
	public boolean PedidosPendientesProveedor(TransferProveedor proveedor) throws DAOException{
			Statement stmt = null;
			boolean tienePedidos = false;
			
			//Get the connection from the transaction
			Connection connection = null;
			try{
				connection = (Connection)TransactionManager.getInstancia().getTransaction().getResource();
			}catch(ClassCastException ex){};
			
			try {
				//if there was no active transaction, we get the connection using the normal way.
				if(connection == null)
					connection = BBDDConnection.getConnection();
				
				stmt = connection.createStatement();
				
				stmt.execute("SELECT id FROM pedidos "
						+ "WHERE id_proveedor = " + proveedor.getId()
						+ " AND estado LIKE 'P'"
						+ " AND activo = '1'" );
				
				if(stmt.getResultSet().next())
					tienePedidos = true;
				
			}catch(SQLException sqlex){
				throw new DAOException(sqlex);
			}
		return tienePedidos;
	}
	
	@Override
	public boolean anularPedido(TransferPedido pedido) throws DAOException {
		boolean error = false;

		Statement stmt = null;
		
		//Get the connection from the transaction
		Connection connection = null;
		try{connection = (Connection)TransactionManager.getInstancia().getTransaction().getResource();
		}catch(ClassCastException ex){};
		
		if(connection == null)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try {
				connection = BBDDConnection.getConnection();
				stmt = connection.createStatement();
				int editadas = stmt.executeUpdate("UPDATE pedidos SET " +
						"estado = 'A' " +
						"WHERE id = " + pedido.getId()
					);
				if(editadas != 1){
					error = true;
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
				
				int editadas = stmt.executeUpdate("UPDATE pedidos SET " +
						"estado = 'A' " +
						"WHERE id = " + pedido.getId()
					);
				if(editadas != 1){
					error = true;
				}
				
			}
			catch(SQLException ex) {
				throw new DAOException(ex);
			}
		}
		
		return !error;
	}
	
	@Override
	public TransferListaPedidos principalPedido(int lockMode) throws DAOException{
		
		TransferListaPedidos out = new TransferListaPedidos();
		out.setLista(new ArrayList<TransferPedido>());

		Statement stmt = null;
		ResultSet rs = null;
		
		//Get the connection from the transaction
		Connection connection = null;
		try{connection = (Connection)TransactionManager.getInstancia().getTransaction().getResource();
		}catch(ClassCastException ex){};
		
		if(connection == null || lockMode == 0)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try {
				if(connection == null)
					connection = BBDDConnection.getConnection();
				stmt = connection.createStatement();
				
				stmt.execute("SELECT id, id_proveedor, fecha, estado " +
						"FROM pedidos WHERE activo = 1");
				rs = stmt.getResultSet();
				
				TransferPedido aux;
				while(rs.next()) {
					
					aux = new TransferPedido();
					aux.setId(rs.getInt(1));
					aux.setIdProveedor(rs.getInt(2));
					aux.setFecha(rs.getString(3));
					aux.setEstado(rs.getString(4).toCharArray()[0]);
					out.getLista().add(aux);				
					
				}
				
			}catch(SQLException sqlex){
				throw new DAOException(sqlex);
			}
		}
		else
		{
			try {
				stmt = connection.createStatement();
				
				stmt.execute("SELECT id, id_proveedor, fecha, estado " +
						"FROM pedidos WHERE activo = 1 FOR UPDATE");
				rs = stmt.getResultSet();
				
				TransferPedido aux;
				while(rs.next()) {
					
					aux = new TransferPedido();
					aux.setId(rs.getInt(1));
					aux.setIdProveedor(rs.getInt(2));
					aux.setFecha(rs.getString(3));
					aux.setEstado(rs.getString(4).toCharArray()[0]);
					out.getLista().add(aux);				
					
				}
				
			}catch(SQLException sqlex){
				throw new DAOException(sqlex);
			}		
		}
		return out;
	}

	/*public TransferListaProductos consultarProductos(TransferProveedor proveedor, int lockMode) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}*/
	
	@Override
	public boolean compruebaProductoNoPendienteDeRecepcion(TransferProducto producto, int lockMode) throws DAOException{
		boolean aparece = false;

		Statement stmt = null;
		ResultSet rs = null;
		
		//Get the connection from the transaction
		Connection connection = null;
		try{connection = (Connection)TransactionManager.getInstancia().getTransaction().getResource();
		}catch(ClassCastException ex){};
		
		if(connection == null || lockMode == 0)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try {
				if(connection == null)
					connection = BBDDConnection.getConnection();
				stmt = connection.createStatement();
				
				stmt.execute("SELECT count(*) " +
						" FROM pedidos , linea_pedido  WHERE pedidos.id = linea_pedido.id_pedido " +
						" AND linea_pedido.id_prod = " +producto.getId() +
						" AND pedidos.estado = 'P'" +
						" AND pedidos.activo = 1");
				rs = stmt.getResultSet();
				int apariciones = 0;
				if(rs.next()) {
					apariciones = rs.getInt(1);
				}
				if(apariciones > 0)
					aparece = true;
				
			}catch(SQLException sqlex){
				throw new DAOException(sqlex);
			}		
		}
		else
		{
			try {
				stmt = connection.createStatement();
				
				stmt.execute("SELECT count(*) " +
						" FROM pedidos p, linea_pedido lp WHERE p.id = lp.id_pedido " +
						" AND lp.id_prod = " +producto.getId() +
						" AND p.estado = 'P'" +
						" AND p.activo = 1 FOR UPDATE");
				rs = stmt.getResultSet();
				int apariciones = 0;
				if(rs.next()) {
					apariciones = rs.getInt(1);
				}
				if(apariciones > 0)
					aparece = true;
				
			}catch(SQLException sqlex){
				throw new DAOException(sqlex);
			}		
		}
		return aparece;
	}
}


