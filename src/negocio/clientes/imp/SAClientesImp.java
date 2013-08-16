package negocio.clientes.imp;

import java.util.ArrayList;
import java.util.List;

import constantes.Errores;
import constantes.LockModes;
import integracion.DAOException;
import integracion.clientes.DAOClientes;
import integracion.clientes.factoria.FactoriaDAOClientes;
import integracion.transaction.Transaction;
import integracion.transaction.transactionManager.TransactionManager;
import negocio.Retorno;
import negocio.clientes.SAClientes;
import negocio.clientes.TransferCliente;

public class SAClientesImp implements SAClientes{

	@Override
	public Retorno crearCliente(TransferCliente cliente)
	{
		Retorno retorno = new Retorno();

		DAOClientes DAO = FactoriaDAOClientes.getInstancia().getInstanciaDAOClientes();

		int dniCliente = cliente.getDNI();
		//Boolean of everything was OK
		boolean right = true;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		//save the transaction in the transactionManager
		transactionManager.createTransaction();
		//get the transaction
		Transaction transaction = transactionManager.getTransaction();
		//Start the transaction
		if(transaction.start())
		{
			try
			{
				//Bloqueamos la tabla clientes
				List<String> tablas = new ArrayList<String>();
				tablas.add("clientes"); tablas.add("clientesvip");
				transaction.lock(LockModes.ReadAndWrite, tablas);
				
				right &= DAO.consultarClienteDNI(cliente,0).getId() == -1;
				if(!right)
					retorno.addError(Errores.clienteDNIRepetido, dniCliente);
				else
				{
					right &= DAO.crearCliente(cliente);
					if(!right)
						retorno.addError(Errores.clienteNoCreado, null);
				}


			}catch(DAOException ex){
				retorno.addError(Errores.errorDeAcceso, ex);
			}

			//If everything was OK commit, else the queries does not work
			if(right)
				transaction.commit();
			else
				transaction.rollback();
		}

		//borramos la transaccion del transaction manager
		transactionManager.deleteTransaction();
		return retorno;
	}

	@Override
	public Retorno editarCliente(TransferCliente cliente)
	{
		Retorno retorno = new Retorno();

		DAOClientes DAO = FactoriaDAOClientes.getInstancia().getInstanciaDAOClientes();

		//Boolean of everything was OK
		boolean right = true;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		//save the transaction in the transactionManager
		transactionManager.createTransaction();
		//get the transaction
		Transaction transaction = transactionManager.getTransaction();
		//Start the transaction
		if(transaction.start())
		{
			try
			{		
				//Bloqueamos la tabla clientes
				List<String> tablas = new ArrayList<String>();
				tablas.add("clientes"); tablas.add("clientesvip");
				transaction.lock(LockModes.ReadAndWrite, tablas);
				
				TransferCliente aux = new TransferCliente();
				
				aux.setDNI(cliente.getDNI());
				
				aux = DAO.consultarClienteDNI(aux,0);
				if(aux.getId() != -1 && aux.getId() != cliente.getId())
				{
					right = false;
					retorno.addError(Errores.clienteDNIRepetido, cliente.getDNI());
				}
				else if(!DAO.editarCliente(cliente))
				{
					right = false;
					retorno.addError(Errores.clienteNoModificado, null);
				}
			}
			catch(DAOException ex)
			{
				right = false;
				retorno.addError(Errores.errorDeAcceso, ex);
			}

			//If everything was OK commit, else the queries does not work
			if(right)
				transaction.commit();
			else
				transaction.rollback();
		}

		//borramos la transaccion del transaction manager
		transactionManager.deleteTransaction();
		return retorno;
	}

	@Override
	public Retorno principalClientes()
	{

		Retorno retorno = new Retorno();

		DAOClientes DAO = FactoriaDAOClientes.getInstancia().getInstanciaDAOClientes();

		//Boolean of everything was OK
		boolean right = true;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		//save the transaction in the transactionManager
		transactionManager.createTransaction();
		//get the transaction
		Transaction transaction = transactionManager.getTransaction();
		//Start the transaction
		if(transaction.start())
		{
			try
			{
				retorno.setDatos(DAO.principalClientes(0));
			}catch(DAOException ex)
			{
				right = false;
				retorno.addError(Errores.errorDeAcceso, ex);
			}
			//If everything was OK commit, else the queries does not work
			if(right)
				transaction.commit();
			else
				transaction.rollback();
		}

		//borramos la transaccion del transaction manager
		transactionManager.deleteTransaction();
		return retorno;
	}

	@Override
	public Retorno consultarCliente(TransferCliente cliente)
	{
		Retorno retorno = new Retorno();

		int idCliente = cliente.getId();

		DAOClientes DAO = FactoriaDAOClientes.getInstancia().getInstanciaDAOClientes();

		//Boolean of everything was OK
		boolean right = true;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		//save the transaction in the transactionManager
		transactionManager.createTransaction();
		//get the transaction
		Transaction transaction = transactionManager.getTransaction();
		//Start the transaction
		if(transaction.start())
		{
			try {
				TransferCliente clienteOut = DAO.consultarCliente(cliente,0);
				if(clienteOut.getId() == -1 || !clienteOut.isActivo())
				{
					right = false;
					retorno.addError(Errores.clienteNoEncontrado, idCliente);
				}else
					retorno.setDatos(clienteOut);
			}catch(DAOException ex)
			{
				right = false;
				retorno.addError(Errores.errorDeAcceso, ex);
			}

			//If everything was OK commit, else the queries does not work
			if(right)
				transaction.commit();
			else
				transaction.rollback();
		}

		//borramos la transaccion del transaction manager
		transactionManager.deleteTransaction();
		return retorno;
	}

	@Override
	public Retorno borrarCliente(TransferCliente cliente)
	{	
		Retorno retorno = new Retorno();

		DAOClientes DAO = FactoriaDAOClientes.getInstancia().getInstanciaDAOClientes();

		//Boolean of everything was OK
		boolean right = true;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		//save the transaction in the transactionManager
		transactionManager.createTransaction();
		//get the transaction
		Transaction transaction = transactionManager.getTransaction();
		//Start the transaction
		if(transaction.start())
		{
			try
			{
				//Bloqueamos la tabla clientes
				List<String> tablas = new ArrayList<String>();
				tablas.add("clientes"); tablas.add("clientesvip");
				transaction.lock(LockModes.ReadAndWrite, tablas);
				
				if(!DAO.borrarCliente(cliente))
				{
					right = false;
					retorno.addError(Errores.clienteNoBorrado, null);
				}
			}
			catch(DAOException ex)
			{
				right = false;
				retorno.addError(Errores.errorDeAcceso, ex);
			}

			//If everything was OK commit, else the queries does not work
			if(right)
				transaction.commit();
			else
				transaction.rollback();
		}

		//borramos la transaccion del transaction manager
		transactionManager.deleteTransaction();
		return retorno;
	}

	@Override
	public Retorno reactivarCliente(TransferCliente cliente) 
	{
		Retorno retorno = new Retorno();

		int dniCliente = cliente.getDNI();

		DAOClientes DAO = FactoriaDAOClientes.getInstancia().getInstanciaDAOClientes();

		//Boolean of everything was OK
		boolean right = true;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		//save the transaction in the transactionManager
		transactionManager.createTransaction();
		//get the transaction
		Transaction transaction = transactionManager.getTransaction();
		//Start the transaction
		if(transaction.start())
		{
			try 
			{
				//Bloqueamos la tabla clientes
				List<String> tablas = new ArrayList<String>();
				tablas.add("clientes"); tablas.add("clientesvip");
				transaction.lock(LockModes.ReadAndWrite, tablas);
				
				if(DAO.consultarClienteDNI(cliente,0).getId() == -1)
				{
					right = false;
					retorno.addError(Errores.clienteNoEncontradoDNI, dniCliente);
				}
				else
					if(!DAO.recuperarCliente(cliente))
					{
						right = false;
						retorno.addError(Errores.clienteNoRecuperado, cliente.getId());	
					}

			}
			catch(DAOException ex)
			{
				right = false;
				retorno.addError(Errores.errorDeAcceso, ex);
			}

			//If everything was OK commit, else the queries does not work
			if(right)
				transaction.commit();
			else
				transaction.rollback();
		}

		//borramos la transaccion del transaction manager
		transactionManager.deleteTransaction();
		return retorno;
	}

}
