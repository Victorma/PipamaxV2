package negocio.controlador.command.productos;

import integracion.DAOException;
import integracion.transaction.LockModes;
import integracion.transaction.Transaction;
import integracion.transaction.transactionManager.TransactionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import negocio.Retorno;
import negocio.controlador.command.Command;
import negocio.productos.TransferProducto;
import constantes.Errores;

public class CommandQueryProductosMasVendidos implements Command{

	@Override
	public Retorno execute() {
		
		TransactionManager.getInstancia().createTransaction();
		Transaction tran = TransactionManager.getInstancia().getTransaction();
		tran.start();
		tran.lock(LockModes.LockAll, null);
		Connection conn = (Connection)TransactionManager.getInstancia().getTransaction().getResource();
		
		Retorno retorno = new Retorno();
		
		try {
			Statement stmt = conn.createStatement();
			
			stmt.execute("SELECT p.id, p.nombre, p.id_marca, p.precio, m.cantidad, m.total " +
							"FROM productos AS p, " +
							"(SELECT sum(t.precio*t.cantidad*(1-v.descuento)) AS total, sum(t.cantidad) AS cantidad, idProducto " +
								"FROM lineasventa AS t, ventas AS v " +
								"WHERE t.idVenta = v.id AND v.cerrada = '1' AND v.activo = '1' " +
								"GROUP BY idProducto " +
								"ORDER BY cantidad DESC " +
								") AS m " +
							"WHERE p.id = m.idProducto AND p.borrado = '0' " +
							"ORDER BY m.cantidad DESC LIMIT 5");
			
			ResultSet rs = stmt.getResultSet();
			
			List<Object[]> productos = new ArrayList<>();
			
			while (rs.next()) {
				TransferProducto producto = new TransferProducto();
				producto.setId(rs.getInt("id"));
				producto.setNombre(rs.getString("nombre"));
				producto.setIdMarca(rs.getInt("id_marca"));
				producto.setPrecio(rs.getDouble("precio"));
				
				productos.add(new Object[]{producto,
						rs.getInt("cantidad"),
						rs.getDouble("total")});
			}
			retorno.setDatos(productos);
			
		} catch (SQLException e) {
			retorno.addError(Errores.errorDeAcceso, new DAOException(e.getMessage()));
		}
		
		tran.commit(); // To unlock the tables and close the transaction
		TransactionManager.getInstancia().deleteTransaction();
		
		
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		// TODO Auto-generated method stub

	}
	
}
