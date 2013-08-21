package integracion.transaction;

import java.util.List;

public interface Transaction {
	public boolean start();

	public boolean lock(LockModes mode, List<String> tables);
	
	public boolean unlock();
	
	public boolean commit();

	public boolean rollback();

	public Object getResource();

}
