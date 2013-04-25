package integracion.transaction;

import java.sql.Connection;

public interface Transaction {
	public boolean start();

	public boolean commit();

	public boolean rollback();

	public Connection getResource();

}
