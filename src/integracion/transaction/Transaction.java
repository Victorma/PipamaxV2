package integracion.transaction;

import java.util.List;
import constantes.LockModes;

public interface Transaction {
	public boolean start();

	public boolean lock(LockModes mode, List<String> tables);
	
	public boolean commit();

	public boolean rollback();

	public Object getResource();

}
