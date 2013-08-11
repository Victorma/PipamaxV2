package integracion.transaction.factoriaTransaction.imp;

import integracion.transaction.Transaction;
import integracion.transaction.factoriaTransaction.FactoriaTransaction;
import integracion.transaction.imp.TransactionMysql;

public class FactoriaTransactionImp extends FactoriaTransaction {

	@Override
	public Transaction createTransaction() {
		return new TransactionMysql();
	}

}
