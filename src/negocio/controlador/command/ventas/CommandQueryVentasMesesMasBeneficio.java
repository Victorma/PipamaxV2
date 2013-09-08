package negocio.controlador.command.ventas;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import constantes.Errores;
import integracion.DAOException;
import integracion.transaction.LockModes;
import integracion.transaction.Transaction;
import integracion.transaction.transactionManager.TransactionManager;
import negocio.Retorno;
import negocio.controlador.command.Command;

public class CommandQueryVentasMesesMasBeneficio implements Command {

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
			
			stmt.execute("SELECT sum(t.beneficio) AS total, MONTH(t.fecha) AS mes, YEAR(t.fecha) AS anio FROM " +
							"(SELECT sum(l.precio*l.cantidad)*(1-v.descuento) AS beneficio, v.fecha "+ 
								"FROM ventas AS v, lineasventa AS l " +
								"WHERE v.id = l.idVenta AND v.activo = '1' AND v.cerrada = '1' " +
								"GROUP BY v.id, v.fecha, v.descuento) AS t " +
							"GROUP BY MONTH(t.fecha), YEAR(t.fecha) " +
							"ORDER BY total DESC " +
							"LIMIT 5");
			
			ResultSet rs = stmt.getResultSet();
			
			List<Object[]> meses = new ArrayList<>();
			
			while (rs.next()) {
				meses.add(new Object[]{rs.getDouble("total"), rs.getInt("mes"), rs.getInt("anio")});
			}
			retorno.setDatos(meses);
			
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
