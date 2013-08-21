/**
 * 
 */
package integracion.productos.imp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import negocio.marcas.TransferMarca;
import negocio.productos.TransferListaProductos;
import negocio.productos.TransferProducto;
import negocio.proveedores.TransferListaSuministros;
import negocio.proveedores.TransferProveedor;
import negocio.proveedores.TransferSuministro;
import integracion.BBDDConnection;
import integracion.DAOException;
import integracion.productos.DAOProductos;
import integracion.transaction.transactionManager.TransactionManager;

public class DAOProductosImp implements DAOProductos {

	@Override
	public boolean crearProducto(TransferProducto producto) throws DAOException {

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
				stmt.addBatch("INSERT INTO productos (nombre, id_marca, stock, precio) " + 
						"VALUES('" + 
						producto.getNombre() + "', '" +
						producto.getIdMarca() + "', '" +
						producto.getStock() + "', '" +
						producto.getPrecio() + "')"
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

				stmt.addBatch("INSERT INTO productos (nombre, id_marca, stock, precio) " + 
						"VALUES('" + 
						producto.getNombre() + "', '" +
						producto.getIdMarca() + "', '" +
						producto.getStock() + "', '" +
						producto.getPrecio() + "')"
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
	public boolean borrarProducto(TransferProducto producto) throws DAOException{

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

				int editadas = stmt.executeUpdate("UPDATE productos " +
						"SET borrado = 1 " +
						"WHERE id = " + producto.getId() + " AND borrado = 0" 
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

						stmt.addBatch("UPDATE productos " +
						"SET borrado = 1 " +
						"WHERE id = " + producto.getId() + " AND borrado = 0" 
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

		return !error;
	}

	@Override
	public boolean modificarProducto(TransferProducto producto) throws DAOException
	{
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

				int editadas = stmt.executeUpdate("UPDATE productos SET " +
						"nombre = '" + producto.getNombre() + "', " +
						"id_marca = '" + producto.getIdMarca() + "', " +
						"stock = '" + producto.getStock() + "', " +
						"precio = '" + producto.getPrecio() + "' " +
						"WHERE id = " + producto.getId()
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

				int editadas = stmt.executeUpdate("UPDATE productos SET " +
						"nombre = '" + producto.getNombre() + "', " +
						"id_marca = '" + producto.getIdMarca() + "', " +
						"stock = '" + producto.getStock() + "', " +
						"precio = '" + producto.getPrecio() + "' " +
						"WHERE id = " + producto.getId()
				);
				if(editadas!=1){
					error = true;
				}

			}
			catch(SQLException ex){
				throw new DAOException(ex);
			}
		}//end else connection == null

		return !error;
	}


	@Override
	public TransferListaProductos productosPorMarca(TransferMarca marca, int lockMode) throws DAOException
	{

		TransferListaProductos out = new TransferListaProductos();
		out.setList(new ArrayList<TransferProducto>());


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

				stmt.execute("SELECT id, nombre, precio, stock " +
						"FROM productos " +
						"WHERE borrado = '0' AND id_marca = " + marca.getId()
				);

				rs = stmt.getResultSet();

				while (rs.next()) {
					TransferProducto newProducto = new TransferProducto();
					newProducto.setId(rs.getInt(1));
					newProducto.setNombre(rs.getString(2));
					newProducto.setPrecio(rs.getDouble(3));
					newProducto.setStock(rs.getInt(4));
					out.add(newProducto);
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

				stmt.execute("SELECT id, nombre, precio, stock " +
						"FROM productos " +
						"WHERE borrado = '0' AND id_marca = " + marca.getId() + " FOR UPDATE"
				);

				rs = stmt.getResultSet();

				while (rs.next()) {
					TransferProducto newProducto = new TransferProducto();
					newProducto.setId(rs.getInt(1));
					newProducto.setNombre(rs.getString(2));
					newProducto.setPrecio(rs.getDouble(3));
					newProducto.setStock(rs.getInt(4));
					out.add(newProducto);
				}
			}
			catch(SQLException ex) {
				throw new DAOException(ex);
			}
		}

		return out;
	}


	@Override
	public TransferSuministro consultarSuministro(TransferSuministro suministro, int lockMode) throws DAOException
	{		
		Statement stmt = null;
		ResultSet rs = null;
		TransferSuministro suministroSalida = new TransferSuministro();
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

				stmt.execute("SELECT id_proveedor, id_producto, precio FROM suministros " +
						" WHERE id_Proveedor = '" + suministro.getIdProveedor() + "' AND id_Producto = '" + suministro.getIdProducto() + "'");

				rs = stmt.getResultSet();
				if(rs.next()){
					suministroSalida.setIdProducto(rs.getInt(1));
					suministroSalida.setIdProveedor(rs.getInt(2));
					suministroSalida.setPrecio(rs.getDouble(3));
				}else{
					suministroSalida.setId(-1);
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
				//TODO posible fallo con el for update?? ya que el anterior acababa en + "'" -- ROBER
				stmt.execute("SELECT id_proveedor, id_producto, precio FROM suministros " +
						" WHERE id_Proveedor = '" + suministro.getIdProveedor() + "' AND id_Producto = '" + suministro.getIdProducto() + "' FOR UPDATE");

				rs = stmt.getResultSet();
				if(rs.next()){
					suministroSalida.setIdProducto(rs.getInt(1));
					suministroSalida.setIdProveedor(rs.getInt(2));
					suministroSalida.setPrecio(rs.getDouble(3));
				}else{
					suministroSalida.setId(-1);
				}
			}
			catch(SQLException ex){
				throw new DAOException(ex);
			}
		}

		return suministroSalida;
	}

	@Override
	public boolean crearSuministro(TransferSuministro suministro) throws DAOException
	{
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
				int editadas = stmt.executeUpdate("INSERT INTO suministros (id_proveedor, id_producto, precio)" +
						"VALUES('" + 
						suministro.getIdProveedor() + "', '" +
						suministro.getIdProducto() + "', '" +
						suministro.getPrecio() + "')"
				);
				if(editadas!=1){
					error = true;
				}
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

				stmt = connection.createStatement();
				int editadas = stmt.executeUpdate("INSERT INTO suministros (id_proveedor, id_producto, precio)" +
						"VALUES('" + 
						suministro.getIdProveedor() + "', '" +
						suministro.getIdProducto() + "', '" +
						suministro.getPrecio() + "')"
				);
				if(editadas!=1){
					error = true;
				}


			}
			catch(SQLException ex){
				throw new DAOException(ex);
			}

		}//End else connection == null


		return !error;
	}


	@Override
	public TransferListaSuministros consultarListaSuministros(TransferProducto producto, int lockMode) throws DAOException
	{

		TransferListaSuministros out = new TransferListaSuministros();
		out.setList(new ArrayList<TransferSuministro>());

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


				stmt.execute("SELECT id_proveedor, id_producto, precio FROM suministros WHERE id_producto = " + producto.getId()
						+ " ORDER BY id_proveedor");
				rs = stmt.getResultSet();

				while(rs.next()){
					TransferSuministro newTr = new TransferSuministro(); 
					newTr.setIdProveedor(rs.getInt(1));
					newTr.setIdProducto(rs.getInt(2));
					newTr.setPrecio(rs.getDouble(3));
					out.add(newTr);
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


				stmt.execute("SELECT id_proveedor, id_producto, precio FROM suministros WHERE id_producto = FOR UPDATE" + producto.getId()
						+ " ORDER BY id_proveedor");
				rs = stmt.getResultSet();

				while(rs.next()){
					TransferSuministro newTr = new TransferSuministro(); 
					newTr.setIdProveedor(rs.getInt(1));
					newTr.setIdProducto(rs.getInt(2));
					newTr.setPrecio(rs.getDouble(3));
					out.add(newTr);
				}
			}
			catch(SQLException ex) {
				throw new DAOException(ex);
			}
		}

		return out;
	}
	@Override
	public TransferListaSuministros consultarListaSuministros(TransferProveedor proveedor, int lockMode) throws DAOException
	{

		TransferListaSuministros out = new TransferListaSuministros();
		out.setList(new ArrayList<TransferSuministro>());

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


				stmt.execute("SELECT id_proveedor, id_producto, precio FROM suministros WHERE id_proveedor = " + proveedor.getId()
						+ " ORDER BY id_producto");
				rs = stmt.getResultSet();

				while(rs.next()){
					TransferSuministro newTr = new TransferSuministro(); 
					newTr.setIdProveedor(rs.getInt(1));
					newTr.setIdProducto(rs.getInt(2));
					newTr.setPrecio(rs.getDouble(3));
					out.add(newTr);
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


				stmt.execute("SELECT id_proveedor, id_producto, precio FROM suministros WHERE id_proveedor = " + proveedor.getId()
						+ " ORDER BY id_producto FOR UPDATE");
				rs = stmt.getResultSet();

				while(rs.next()){
					TransferSuministro newTr = new TransferSuministro(); 
					newTr.setIdProveedor(rs.getInt(1));
					newTr.setIdProducto(rs.getInt(2));
					newTr.setPrecio(rs.getDouble(3));
					out.add(newTr);
				}
			}
			catch(SQLException ex) {
				throw new DAOException(ex);
			}
		}
		return out;
	}

	@Override
	public boolean borrarSuministro(TransferSuministro suministro) throws DAOException
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

				int editadas = stmt.executeUpdate("DELETE FROM suministros WHERE " +
						"id_producto = " + suministro.getIdProducto() + " AND " +
						"id_proveedor = " + suministro.getIdProveedor()
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

				int editadas = stmt.executeUpdate("DELETE FROM suministros WHERE " +
						"id_producto = " + suministro.getIdProducto() + " AND " +
						"id_proveedor = " + suministro.getIdProveedor()
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
	public TransferProducto consultaProducto(TransferProducto producto, int lockMode) throws DAOException
	{
		Statement stmt = null;
		ResultSet rs = null;

		//Get the transction and the connection
		Connection connection = null; try{connection = (Connection)TransactionManager.getInstancia().getTransaction().getResource(); }catch(ClassCastException ex){};

		TransferProducto productoSalida = new TransferProducto();
		if(connection == null || lockMode == 0)
		{
			//if we dont find the transaction, make the sentence with no transaction
			try 
			{	
				if(connection == null)
					connection = BBDDConnection.getConnection();

				stmt = connection.createStatement();

				stmt.execute("SELECT id, nombre, id_marca, stock, precio, borrado FROM productos WHERE id = " + producto.getId());
				rs = stmt.getResultSet();

				if (rs.next()){
					productoSalida.setId(rs.getInt(1));
					productoSalida.setNombre(rs.getString(2));
					productoSalida.setIdMarca(rs.getInt(3));
					productoSalida.setStock(rs.getInt(4));
					productoSalida.setPrecio(rs.getDouble(5));
					productoSalida.setBorrado(rs.getBoolean(6));
				}
				else{
					productoSalida.setId(-1);
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

				stmt.execute("SELECT id, nombre, id_marca, stock, precio, borrado FROM productos WHERE id = " + producto.getId());
				rs = stmt.getResultSet();

				if (rs.next()){
					productoSalida.setId(rs.getInt(1));
					productoSalida.setNombre(rs.getString(2));
					productoSalida.setIdMarca(rs.getInt(3));
					productoSalida.setStock(rs.getInt(4));
					productoSalida.setPrecio(rs.getDouble(5));
					productoSalida.setBorrado(rs.getBoolean(6));
				}
				else{
					productoSalida.setId(-1);
				}
			}
			catch(SQLException ex){
				throw new DAOException(ex);
			}
		}//End else connection == false

		return productoSalida;
	}


	@Override
	public TransferListaProductos consultaListadoProductos(int lockMode) throws DAOException
	{

		TransferListaProductos out = new TransferListaProductos();
		out.setList(new ArrayList<TransferProducto>());

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


				stmt.execute("SELECT id, nombre, id_marca, stock, precio FROM productos WHERE borrado = 0");
				rs = stmt.getResultSet();

				while (rs.next()) {
					TransferProducto newProducto = new TransferProducto();
					newProducto.setId(rs.getInt(1));
					newProducto.setNombre(rs.getString(2));
					newProducto.setIdMarca(rs.getInt(3));
					newProducto.setStock(rs.getInt(4));
					newProducto.setPrecio(rs.getDouble(5));
					out.add(newProducto);
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


				stmt.execute("SELECT id, nombre, id_marca, stock, precio FROM productos WHERE borrado = 0 FOR UPDATE");
				rs = stmt.getResultSet();

				while (rs.next()) {
					TransferProducto newProducto = new TransferProducto();
					newProducto.setId(rs.getInt(1));
					newProducto.setNombre(rs.getString(2));
					newProducto.setIdMarca(rs.getInt(3));
					newProducto.setStock(rs.getInt(4));
					newProducto.setPrecio(rs.getDouble(5));
					out.add(newProducto);
				}
			}
			catch(SQLException ex) {
				throw new DAOException(ex);
			}
		}
		return out;
	}
}