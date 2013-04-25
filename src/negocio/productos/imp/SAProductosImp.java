/**
 * 
 */
package negocio.productos.imp;

import java.util.Iterator;

import constantes.Errores;
import integracion.DAOException;
import integracion.marcas.DAOMarcas;
import integracion.marcas.factoria.FactoriaDAOMarcas;
import integracion.pedidos.DAOPedidos;
import integracion.pedidos.factoria.FactoriaDAOPedidos;
import integracion.productos.DAOProductos;
import integracion.productos.factoria.FactoriaDAOProductos;
import integracion.proveedores.DAOProveedores;
import integracion.proveedores.factoria.FactoriaDAOProveedores;
import integracion.transaction.Transaction;
import integracion.transaction.transactionManager.TransactionManager;
import negocio.Retorno;
import negocio.marcas.TransferMarca;
import negocio.productos.SAProductos;
import negocio.productos.TComProducto;
import negocio.productos.TransferListaProductos;
import negocio.productos.TransferProducto;
import negocio.proveedores.TransferListaSuministros;
import negocio.proveedores.TransferProveedor;
import negocio.proveedores.TransferSuministro;

public class SAProductosImp implements SAProductos {

	@Override
	public Retorno crearProducto(TransferProducto producto) {
		Retorno retorno = new Retorno();

		DAOProductos DAO = FactoriaDAOProductos.getInstancia().getInstanciaDAOProductos();
		DAOMarcas DAOM = FactoriaDAOMarcas.getInstancia().getInstanciaDAOMarcas();
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
				
				TransferMarca marca = new TransferMarca();
				marca.setId(producto.getIdMarca());
				
				//Bloqueamos la tabla productos
				DAO.bloquearTablas(4);
			
				marca = DAOM.consultarMarca(marca, 3);

				if(marca.getId() == -1 || !marca.isActiva()){
					right=false;
					retorno.addError(Errores.marcaNoEncontrada, producto.getIdMarca());
				}else {
					right&=DAO.crearProducto(producto);
					if(!right)
						retorno.addError(Errores.productoNoCreado, null);
				}
			}catch(DAOException ex){
				right=false;
				retorno.addError(Errores.errorDeAcceso, ex);
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
	public Retorno borrarProducto(TransferProducto producto) {
		Retorno retorno = new Retorno();

		DAOProductos DAO = FactoriaDAOProductos.getInstancia().getInstanciaDAOProductos();
		DAOPedidos DAOP = FactoriaDAOPedidos.getInstancia().getInstanciaDAOPedidos();

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
				//Bloqueamos la tabla productos
				DAO.bloquearTablas(6);
				right&= !DAOP.compruebaProductoNoPendienteDeRecepcion(producto, 0);
				if(!right)
					retorno.addError(Errores.productoEnProceso, producto.getId());
				else{ 
					right&=DAO.borrarProducto(producto);
					if(!right)
						retorno.addError(Errores.productoNoBorrado, null);
				}
			}catch(DAOException ex){
				right=false;
				retorno.addError(Errores.errorDeAcceso, ex);
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
	public Retorno modificarProducto(TransferProducto producto) {
		Retorno retorno = new Retorno();

		DAOProductos DAO = FactoriaDAOProductos.getInstancia().getInstanciaDAOProductos();
		DAOMarcas DAOM = FactoriaDAOMarcas.getInstancia().getInstanciaDAOMarcas();
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
				TransferMarca marca = new TransferMarca();
				marca.setId(producto.getIdMarca());
				
				//Bloqueamos la tabla productos
				DAO.bloquearTablas(4);
				
				marca = DAOM.consultarMarca(marca, 3);

				if(marca.getId() == -1 || !marca.isActiva()){
					right=false;
					retorno.addError(Errores.marcaNoEncontrada, producto.getIdMarca());
				}else if(!DAO.modificarProducto(producto))
					retorno.addError(Errores.productoNoModificado, null);

			}catch(DAOException ex){
				right=false;
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
	public Retorno consultarProducto(TransferProducto producto) {
		Retorno retorno = new Retorno();

		DAOProductos DAO = FactoriaDAOProductos.getInstancia().getInstanciaDAOProductos();
		DAOMarcas DAOM = FactoriaDAOMarcas.getInstancia().getInstanciaDAOMarcas();

		int idProducto = producto.getId();

		TComProducto tComProducto = new TComProducto();
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
				tComProducto.setProducto(DAO.consultaProducto(producto, 0));
				if(tComProducto.getProducto().getId() == -1 || tComProducto.getProducto().getBorrado()){
					right=false;
					retorno.addError(Errores.productoNoEncontrado, idProducto);
				}
				else{
					TransferMarca marca = new TransferMarca();
					marca.setId(tComProducto.getProducto().getIdMarca());

					tComProducto.setMarca(DAOM.consultarMarca(marca, 0));
					if(marca.getId() == -1){ // En principio este caso nunca se dara
						right=false;
						retorno.addError(Errores.marcaNoEncontrada, tComProducto.getProducto().getIdMarca());

					}
					else
						retorno.setDatos(tComProducto);
				}

			}catch(DAOException ex){
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
	public Retorno consultarListaSuministros(TransferProducto pro) {
		Retorno retorno = new Retorno();

		TransferListaSuministros listaSuministros;
		DAOProductos DAO = FactoriaDAOProductos.getInstancia().getInstanciaDAOProductos();
		DAOProveedores DAOP = FactoriaDAOProveedores.getInstancia().getInstanciaDAOProveedores();
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
				TransferProducto producto = DAO.consultaProducto(pro, 0);
				if(producto.getId() == -1 || producto.getBorrado()){
					right=false;
					retorno.addError(Errores.productoNoEncontrado, pro.getId());
				}
				else{
					TransferProveedor current = new TransferProveedor();
					listaSuministros = DAO.consultarListaSuministros(producto, 0);
					for(int i = listaSuministros.getList().size()-1; i>=0; i--){
						current.setId(listaSuministros.getList().get(i).getIdProveedor());
						current = DAOP.consultarProveedor(current,3);
						if(current.getId() == -1){
							right=false;
							retorno.addError(Errores.proveedorNoEncontrado, listaSuministros.getList().get(i).getIdProveedor());
						}
						else if(!current.isActivo())
							listaSuministros.getList().remove(i);
					}
					retorno.setDatos(listaSuministros);
				}
			}catch(DAOException ex){
				right=false;
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
	public Retorno crearSuministro(TransferSuministro suministro) {
		Retorno retorno = new Retorno();

		DAOProductos DAO = FactoriaDAOProductos.getInstancia().getInstanciaDAOProductos();
		DAOProveedores DAOProv = FactoriaDAOProveedores.getInstancia().getInstanciaDAOProveedores();
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
				//Consultamos que el producto exista
				TransferProducto producto = new TransferProducto();
				producto.setId(suministro.getIdProducto());
				producto = DAO.consultaProducto(producto, 3);
				if(producto.getId() == -1 || producto.getBorrado()){
					right=false;
					retorno.addError(Errores.productoNoEncontrado, suministro.getIdProducto());
				}
				else{
					//Consultamos que el proveedor exista
					TransferProveedor proveedor = new TransferProveedor();
					proveedor.setId(suministro.getIdProveedor());
					proveedor = DAOProv.consultarProveedor(proveedor, 3);

					if(producto.getId() == -1 || producto.getBorrado()){
						right=false;
						retorno.addError(Errores.proveedorNoEncontrado, suministro.getIdProveedor());
					}
					else{
						//Comprobamos que el proveedor no suministra ya ese producto
						Iterator<TransferSuministro> it = DAO.consultarListaSuministros(producto, 0).getList().iterator();
						boolean yaSuministrado = false;
						while(it.hasNext() && !yaSuministrado)
							yaSuministrado = it.next().getIdProveedor() == suministro.getIdProveedor();
						if(yaSuministrado)
							retorno.addError(Errores.productoYaSuministrado, suministro);
						right&=	DAO.crearSuministro(suministro);
						if(!right)
							//Creamos el suministro
							retorno.addError(Errores.productoSuministroNoCreado, null);
					}
				}
			}catch(DAOException ex){
				right=false;
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
	public Retorno borrarSuministro(TransferSuministro suministro) {
		Retorno retorno = new Retorno();

		DAOProductos DAO = FactoriaDAOProductos.getInstancia().getInstanciaDAOProductos();
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
				//Bloqueamos la tabla productos
				DAO.bloquearTablas(5);
				
				right&=DAO.borrarSuministro(suministro);
				if(!right)
					retorno.addError(Errores.productoSuministroNoBorrado, null);
			}catch(DAOException ex){
				right=false;
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
	public Retorno consultarListadoProducto() {
		Retorno retorno = new Retorno();

		DAOProductos DAO = FactoriaDAOProductos.getInstancia().getInstanciaDAOProductos();
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
				TransferListaProductos aux = new TransferListaProductos();
				aux.setList(DAO.consultaListadoProductos(3).getList());
				retorno.setDatos(aux);
			}catch(DAOException ex){
				right=false;
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