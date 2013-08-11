package integracion.transaction.factoriaTransaction;

import integracion.transaction.Transaction;
import integracion.transaction.factoriaTransaction.imp.FactoriaTransactionImp;

public abstract class FactoriaTransaction
{
	private static FactoriaTransaction instancia = null;
	
	public static FactoriaTransaction getInstancia()
	{
		if(instancia == null)
			instancia = new FactoriaTransactionImp();
		return instancia;
	}
	
	public abstract Transaction createTransaction();
}