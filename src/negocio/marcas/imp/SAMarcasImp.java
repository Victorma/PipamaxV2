package negocio.marcas.imp;

import java.util.List;

import constantes.Errores;
import integracion.DAOException;
import integracion.marcas.DAOMarcas;
import integracion.marcas.factoria.FactoriaDAOMarcas;
import integracion.productos.DAOProductos;
import integracion.productos.factoria.FactoriaDAOProductos;
import integracion.transaction.Transaction;
import integracion.transaction.transactionManager.TransactionManager;
import negocio.Retorno;
import negocio.marcas.SAMarcas;
import negocio.marcas.TComMarcaListaProductos;
import negocio.marcas.TransferListaMarcas;
import negocio.marcas.TransferMarca;
import negocio.productos.TransferListaProductos;
import negocio.productos.TransferProducto;

public class SAMarcasImp implements SAMarcas
{

	@Override
	public Retorno crearMarca(TransferMarca marca)
	{
		Retorno retorno = new Retorno();

		//creamos un objeto DAOMarcas
		DAOMarcas DAO = FactoriaDAOMarcas.getInstancia().getInstanciaDAOMarcas();
		String nombreMarca = marca.getNombre();	
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
			//Ejecutamos un try por el lanzamiento de retorno en el acceso a la base de datos
			try 
			{
				//Bloqueamos la tabla marcas
				DAO.bloquearTablas(4);
				right &= DAO.consultarMarcaNombre(marca,0).getId() == -1;
				if(!right)
					retorno.addError(Errores.marcaNombreRepetido, nombreMarca);
				else{
					right &= DAO.crearMarca(marca);
					if(!right)
						retorno.addError(Errores.marcaNoCreada, null);
				}
			}catch(DAOException ex){
				retorno.addError(Errores.errorDeAcceso, ex);
				right = false;
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
	public Retorno editarMarca(TransferMarca marca) 
	{

		Retorno retorno = new Retorno();

		//creamos un objeto DAOMarcas
		DAOMarcas DAO = FactoriaDAOMarcas.getInstancia().getInstanciaDAOMarcas();
		String nombreMarca = marca.getNombre();	
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
			//Ejecutamos un try por el lanzamiento de retorno en el acceso a la base de datos
			try 
			{
				//Bloqueamos la tabla marcas
				DAO.bloquearTablas(3);
				TransferMarca aux = new TransferMarca();
				aux.setNombre(nombreMarca);
				aux = DAO.consultarMarcaNombre(aux,0);

				if(aux.getId() != -1 && aux.getId() != marca.getId())
					retorno.addError(Errores.marcaNombreRepetido, nombreMarca);
				else if(!DAO.editarMarca(marca))
					retorno.addError(Errores.marcaNoModificada, null);
			}catch(DAOException ex){
				retorno.addError(Errores.errorDeAcceso, ex);
				right = false;
			}
			
			//If everything was OK commit, else the queries does not work
			if(right)
				transaction.commit();
			else
				transaction.rollback();//borramos la transaccion del transaction manager

		}
		
		transactionManager.deleteTransaction();
		return retorno;

	}

	@Override
	public Retorno principalMarca(TransferListaMarcas marcas)
	{
		Retorno retorno = new Retorno();

		//creamos un objeto DAOMarcas
		DAOMarcas DAO = FactoriaDAOMarcas.getInstancia().getInstanciaDAOMarcas();
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
			//Ejecutamos un try por el lanzamiento de retorno en el acceso a la base de datos
			try {
				TransferListaMarcas aux = new TransferListaMarcas();
				aux.setLista(DAO.principalMarca(0).getLista());
				retorno.setDatos(aux);

			}catch(DAOException ex){
				retorno.addError(Errores.errorDeAcceso, ex);
				right = false;
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
	public Retorno consultarMarca(TransferMarca marca) {

		Retorno retorno = new Retorno();

		int idMarca = marca.getId();

		DAOMarcas DAO = FactoriaDAOMarcas.getInstancia().getInstanciaDAOMarcas();

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
				retorno.setDatos(DAO.consultarMarca(marca,0));
				if(marca.getId() == -1 || !marca.isActiva())
					retorno.addError(Errores.marcaNoEncontrada, idMarca);
			}catch(DAOException ex){
				retorno.addError(Errores.errorDeAcceso, ex);
				right = false;
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
	public Retorno borrarMarca(TransferMarca marca)
	{
		Retorno retorno = new Retorno();

		//creamos un objeto DAOMarcas
		DAOMarcas DAO = FactoriaDAOMarcas.getInstancia().getInstanciaDAOMarcas();
		DAOProductos DAOP = FactoriaDAOProductos.getInstancia().getInstanciaDAOProductos();
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
			//Ejecutamos un try por el lanzamiento de retorno en el acceso a la base de datos
			try {
				//Bloqueamos la tabla marcas
				DAO.bloquearTablas(4);
				
				TransferListaProductos prods = DAOP.productosPorMarca(marca,0);
				List<TransferProducto> lista = prods.getList();
				boolean esta = false;
				while(!esta && !lista.isEmpty())
				{
					esta = esta || !lista.get(0).getBorrado();
					lista.remove(0);
				}
				right &= !esta;
				if(!right || !DAO.borrarMarca(marca))
					retorno.addError(Errores.marcaNoBorrada, null);
						
			}catch(DAOException ex){
				retorno.addError(Errores.errorDeAcceso, ex);
				right = false;
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
	public Retorno recuperarMarca(TransferMarca marca)
	{

		Retorno retorno = new Retorno();

		String nombreMarca = marca.getNombre();
		//creamos un objeto DAOMarcas
		DAOMarcas DAO = FactoriaDAOMarcas.getInstancia().getInstanciaDAOMarcas();
		//Ejecutamos un try por el lanzamiento de retorno en el acceso a la base de datos

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
				//Bloqueamos la tabla marcas
				DAO.bloquearTablas(3);
				if(DAO.consultarMarcaNombre(marca,0).getId() == -1)
					retorno.addError(Errores.marcaNoEncontradoNombre, nombreMarca);
				else
					if(!DAO.recuperarMarca(marca))
						retorno.addError(Errores.marcaNoRecuperada, marca.getId());	

			}catch(DAOException ex){
				retorno.addError(Errores.errorDeAcceso, ex);
				right = false;
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
	public Retorno consultarListaProductosMarca(TComMarcaListaProductos marcaListaProductos)
	{
		Retorno retorno = new Retorno();

		DAOMarcas DAO = FactoriaDAOMarcas.getInstancia().getInstanciaDAOMarcas();
		DAOProductos DAOP = FactoriaDAOProductos.getInstancia().getInstanciaDAOProductos();

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
			//Ejecutamos un try por el lanzamiento de retorno en el acceso a la base de datos
			try 
			{
				
				TransferMarca marca = new TransferMarca();
				marca.setId(marcaListaProductos.getMarca().getId());

				marca = DAO.consultarMarca(marca,3);
				if(marca.getId() == -1 || !marca.isActiva())
					retorno.addError(Errores.marcaNoEncontrada, marcaListaProductos.getMarca().getId());
				else{
					TComMarcaListaProductos aux = new TComMarcaListaProductos();
					aux.setMarca(marca);
					//TODO modificar tb DAO producto para que funcione 
					aux.setProductos(DAOP.productosPorMarca(marca,3));
					retorno.setDatos(aux);
				}
			}catch(DAOException ex){
				retorno.addError(Errores.errorDeAcceso, ex);
				right = false;
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
