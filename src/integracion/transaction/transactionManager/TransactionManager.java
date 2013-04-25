package integracion.transaction.transactionManager;
import integracion.transaction.Transaction;
import integracion.transaction.transactionManager.imp.TransactionManagerImp;
public abstract class TransactionManager
{
	private static TransactionManager instancia = null;
	
	public static TransactionManager getInstancia()
	{
		if(instancia == null)
			instancia = new TransactionManagerImp();
		return instancia;
	}
	
	public abstract void createTransaction();
	public abstract Transaction getTransaction();
	public abstract void deleteTransaction();
}
