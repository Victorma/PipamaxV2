package negocio.proveedores.imp;

import java.util.ArrayList;
import java.util.List;

import constantes.Errores;
import integracion.DAOException;
import integracion.pedidos.DAOPedidos;
import integracion.pedidos.factoria.FactoriaDAOPedidos;
import integracion.proveedores.DAOProveedores;
import integracion.proveedores.factoria.FactoriaDAOProveedores;
import integracion.transaction.LockModes;
import integracion.transaction.Transaction;
import integracion.transaction.transactionManager.TransactionManager;
import negocio.Retorno;
import negocio.proveedores.SAProveedores;
import negocio.proveedores.TransferListaProveedores;
import negocio.proveedores.TransferProveedor;



public class SAProveedoresImp implements SAProveedores{

	public Retorno crearProveedor(TransferProveedor proveedor)
	{
		Retorno retorno = new Retorno();

		DAOProveedores DAO = FactoriaDAOProveedores.getInstancia().getInstanciaDAOProveedores();
		int nifProveedor = proveedor.getNif();

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
			try{
				//Bloqueamos la tabla proveedores
				List<String> tablas = new ArrayList<String>();
				tablas.add("proveedores");
				transaction.lock(LockModes.ReadAndWrite, tablas);
				
				right&=DAO.consultarProveedorNIF(proveedor, 0).getId() == -1;
				if(!right)
					retorno.addError(Errores.proveedorNIFRepetido, nifProveedor);
				else {
					right&=DAO.crearProveedor(proveedor);
					if(!right)
						retorno.addError(Errores.proveedorNoCreado, null);
				}

			}catch(DAOException ex){
				retorno.addError(Errores.errorDeAcceso, ex);
				right = false;
			}

			//If everything was OK commit, else the queries do not work
			if(right)
				transaction.commit();
			else
				transaction.rollback();
		}


		//borramos la transaccion del transaction manager
		transactionManager.deleteTransaction();

		return retorno;

	}

	public Retorno editarProveedor(TransferProveedor proveedor)
	{
		Retorno retorno = new Retorno();
		DAOProveedores DAO = FactoriaDAOProveedores.getInstancia().getInstanciaDAOProveedores();
		int nifProveedor = proveedor.getNif();

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

			try{
				//Bloqueamos la tabla proveedores
				List<String> tablas = new ArrayList<String>();
				tablas.add("proveedores");
				transaction.lock(LockModes.ReadAndWrite, tablas);
				
				TransferProveedor aux = new TransferProveedor();
				aux.setNif(nifProveedor);
				aux = DAO.consultarProveedorNIF(aux, 0);
				right&=aux.getId() == -1 || aux.getId() == proveedor.getId(); //??
				if(!right)
					retorno.addError(Errores.proveedorNIFRepetido, nifProveedor);
				else {
					right&=DAO.editarProveedor(proveedor);
					if(!right)
						retorno.addError(Errores.proveedorNoModificado, null);
				}
			}catch(DAOException ex){
				retorno.addError(Errores.errorDeAcceso, ex);
				right = false;
			}

			//If everything was OK commit, else the queries do not work
			if(right)
				transaction.commit();
			else
				transaction.rollback();
		}



		//borramos la transaccion del transaction manager
		transactionManager.deleteTransaction();

		return retorno;
	}

	public Retorno principalProveedores(TransferListaProveedores proveedores)
	{		
		Retorno retorno = new Retorno();
		DAOProveedores DAO = FactoriaDAOProveedores.getInstancia().getInstanciaDAOProveedores();

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
				retorno.setDatos(DAO.principalProveedores(3));
			}catch(DAOException ex){
				retorno.addError(Errores.errorDeAcceso, ex);
				right = false;
			}

			//If everything was OK commit, else the queries do not work
			if(right)
				transaction.commit();
			else
				transaction.rollback();
		}

		//borramos la transaccion del transaction manager
		transactionManager.deleteTransaction();

		return retorno;
	}

	public Retorno consultarProveedor(TransferProveedor proveedor)
	{
		Retorno retorno = new Retorno();
		int idProveedor = proveedor.getId();
		DAOProveedores DAO = FactoriaDAOProveedores.getInstancia().getInstanciaDAOProveedores();

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
				TransferProveedor proveedorSalida = DAO.consultarProveedor(proveedor, 3);
				right&=proveedorSalida.getId() != -1 && proveedorSalida.isActivo(); //??
				if(!right)
					retorno.addError(Errores.proveedorNoEncontrado, idProveedor);
				else
					retorno.setDatos(proveedorSalida);
					
			}catch(DAOException ex){
				retorno.addError(Errores.errorDeAcceso, ex);
				right = false;
			}

			//If everything was OK commit, else the queries do not work
			if(right)
				transaction.commit();
			else
				transaction.rollback();
		}

		//borramos la transaccion del transaction manager
		transactionManager.deleteTransaction();

		return retorno;
	}


	public Retorno borrarProveedor(TransferProveedor proveedor)
	{
		Retorno retorno = new Retorno();
		DAOProveedores DAO = FactoriaDAOProveedores.getInstancia().getInstanciaDAOProveedores();
		DAOPedidos DAOPedidos = FactoriaDAOPedidos.getInstancia().getInstanciaDAOPedidos();
		
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
				//Bloqueamos la tabla proveedores
				List<String> tablas = new ArrayList<String>();
				tablas.add("proveedores"); tablas.add("pedidos");
				transaction.lock(LockModes.ReadAndWrite, tablas);
				
				if(DAOPedidos.PedidosPendientesProveedor(proveedor)){
					retorno.addError(Errores.proveedorConPedidosPendientes, null);
					retorno.addError(Errores.proveedorNoBorrado, null);
				}else{
					right&=DAO.borrarProveedor(proveedor);
					if(!right)
						retorno.addError(Errores.proveedorNoBorrado, null);
				}
			}catch(DAOException ex){
				retorno.addError(Errores.errorDeAcceso, ex);
				right = false;
			}

			//If everything was OK commit, else the queries do not work
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
	public Retorno reactivarProveedor(TransferProveedor proveedor) 
	{
		Retorno retorno = new Retorno();
		int nifProveedor = proveedor.getNif();
		DAOProveedores DAO = FactoriaDAOProveedores.getInstancia().getInstanciaDAOProveedores();

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
				//Bloqueamos la tabla proveedores
				List<String> tablas = new ArrayList<String>();
				tablas.add("proveedores");
				transaction.lock(LockModes.ReadAndWrite, tablas);
				
				right&=DAO.consultarProveedorNIF(proveedor, 0).getId() != -1;
				if(!right)
					retorno.addError(Errores.proveedorNoEncontradoNIF, nifProveedor);
				else {
					right&=DAO.recuperarProveedor(proveedor);
					if(!right)
						retorno.addError(Errores.proveedorNoRecuperado, proveedor.getId());	
				}
			}catch(DAOException ex){
				retorno.addError(Errores.errorDeAcceso, ex);
				right = false;
			}

			//If everything was OK commit, else the queries do not work
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








