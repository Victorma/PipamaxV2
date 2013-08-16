package integracion.transaction;

public interface Transaction {
	public boolean start();

	public boolean commit();

	public boolean rollback();

	public Object getResource();

}
