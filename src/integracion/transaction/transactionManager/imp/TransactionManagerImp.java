package integracion.transaction.transactionManager.imp;
import java.util.HashMap;

import integracion.transaction.Transaction;
import integracion.transaction.factoriaTransaction.FactoriaTransaction;
import integracion.transaction.transactionManager.TransactionManager;

public class TransactionManagerImp  extends TransactionManager 
{
	private static HashMap<Long, Transaction> hashMap = new HashMap<Long, Transaction>();

	@Override
	public void createTransaction()
	{
		hashMap.put(Thread.currentThread().getId(), FactoriaTransaction.getInstancia().createTransaction());
	}

	@Override
	public Transaction getTransaction()
	{
		return hashMap.get(Thread.currentThread().getId());
	}

	@Override
	public void deleteTransaction() 
	{
		hashMap.remove(Thread.currentThread().getId());
	}
}
