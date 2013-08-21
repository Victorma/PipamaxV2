package negocio.pedidos.imp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import integracion.transaction.LockModes;
import integracion.transaction.Transaction;
import integracion.transaction.transactionManager.TransactionManager;
import negocio.Retorno;
import negocio.marcas.TransferMarca;
import negocio.pedidos.SAPedidos;
import negocio.pedidos.TComPedido;
import negocio.pedidos.TransferListaPedidos;
import negocio.pedidos.TransferPedido;
import negocio.productos.TComProducto;
import negocio.productos.TransferListaProductos;
import negocio.productos.TransferProducto;
import negocio.proveedores.TComProveedorListaProductos;
import negocio.proveedores.TransferListaSuministros;
import negocio.proveedores.TransferProveedor;
import negocio.proveedores.TransferSuministro;


public class SAPedidosImp implements SAPedidos{

	@Override	
	public Retorno crearPedido(TransferPedido pedido)
	{
		Retorno retorno = new Retorno();
		
		DAOProveedores proveedores = FactoriaDAOProveedores.getInstancia().getInstanciaDAOProveedores();
		TransferProveedor proveedor = new TransferProveedor();
		
		DAOProductos productos = FactoriaDAOProductos.getInstancia().getInstanciaDAOProductos();
		
		DAOPedidos pedidos = FactoriaDAOPedidos.getInstancia().getInstanciaDAOPedidos();

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
				//Bloqueamos la tabla pedidos
				transaction.lock(LockModes.LockAll, null);
				
				//Buscamos el proveedor				
				proveedor.setId(pedido.getIdProveedor());
				proveedor = proveedores.consultarProveedor(proveedor,0);

				//Comprobamos que el cliente exista
				if(proveedor.getId()==-1){
					right=false;
					retorno.addError(Errores.proveedorNoEncontrado, pedido.getIdProveedor());
				}else if(pedido.getNumLineasPedido() == 0){ // Comprobamos que el pedido tenga productos
					right=false;
					retorno.addError(Errores.pedidoSinProductos, null);
				}else{


					//Comprobamos que los productos existan y sean suministrados por nuestro proveedor

					TransferProducto producto[] = new TransferProducto[pedido.getNumLineasPedido()];
					TransferSuministro suministro = new TransferSuministro();

					int i = 0;		
					boolean productoNoEncontrado = false, productoNoSuministrado = false;
					while(i < pedido.getNumLineasPedido()){

						// Comprobamos que los productos existan y sean suministrados y ademas almacenamos los datos de los productos en un array para 
						// poder utilizarlos mas adelante
						producto[i] = new TransferProducto();
						producto[i].setId(pedido.getLineaPedido(i).getIdProducto());
						producto[i] = productos.consultaProducto(producto[i],3);

						if(producto[i].getId()==-1 || producto[i].getBorrado()){

							if(!productoNoEncontrado){
								productoNoEncontrado = true;
								right=false;
								retorno.addError(Errores.productoNoEncontrado,pedido.getLineaPedido(i).getIdProducto());
							}	
						}else{ 
							//Consultamos que el producto este en el suministro
							suministro = new TransferSuministro();
							suministro.setIdProveedor(pedido.getIdProveedor());
							suministro.setIdProducto(pedido.getLineaPedido(i).getIdProducto());
							suministro = productos.consultarSuministro(suministro,3);
							if(suministro.getId() != -1){
								pedido.getLineaPedido(i).setPrecio(suministro.getPrecio());
							}else{
								if(!productoNoSuministrado){
									productoNoSuministrado = true;
									right=false;
									retorno.addError(Errores.pedidoProductoNoSuministrado, pedido.getLineaPedido(i).getIdProducto());
								}
							}
						}

						i++;              
					}
					if(!productoNoEncontrado && !productoNoSuministrado){
						//Por ultimo creamos el pedido
						if(!pedidos.crearPedido(pedido)){
							retorno.addError(Errores.pedidoNoCreado, null);
							right=false;
						}
					}		
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
	public Retorno principalPedidos(){
		Retorno retorno = new Retorno();

		DAOPedidos DAO = FactoriaDAOPedidos.getInstancia().getInstanciaDAOPedidos();
		TransferListaPedidos pedidos = new TransferListaPedidos();
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
				pedidos.setLista(DAO.principalPedido(0).getLista());
				retorno.setDatos(pedidos);
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
		transactionManager.deleteTransaction();
		return retorno;
	}

	@Override	
	public Retorno consultarPedido(TransferPedido pedido)
	{
		Retorno errores = new Retorno();
		
		TComPedido tComPedido = new TComPedido();
		
		DAOPedidos pedidos = FactoriaDAOPedidos.getInstancia().getInstanciaDAOPedidos();
		DAOProveedores proveedores = FactoriaDAOProveedores.getInstancia().getInstanciaDAOProveedores();
		DAOProductos productos = FactoriaDAOProductos.getInstancia().getInstanciaDAOProductos();
		DAOMarcas marcas = FactoriaDAOMarcas.getInstancia().getInstanciaDAOMarcas();	

		int idPedido = pedido.getId();

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
				//Comprobamos que existe la venta.
				tComPedido.setPedido(pedidos.consultarPedido(pedido,3));
				if(tComPedido.getPedido().getId() == -1){
					right=false;
					errores.addError(Errores.pedidoNoEncontrado, idPedido);
				}
				//Buscamos el cliente
				TransferProveedor proveedor = new TransferProveedor();

				int idProveedor = tComPedido.getPedido().getIdProveedor();

				proveedor.setId(idProveedor);

				tComPedido.setProveedor(proveedores.consultarProveedor(proveedor,3));

				if(tComPedido.getProveedor().getId() == -1){
					right=false;
					errores.addError(Errores.proveedorNoEncontrado, idProveedor);
				}else{
					//Buscamos los productos y sus marcas
					TComProducto tComProducto = null;
					TransferProducto producto = null;
					TransferMarca marca = null;

					List<TComProducto> listaProductos = new ArrayList<TComProducto>();

					for(int i = 0; i<tComPedido.getPedido().getNumLineasPedido(); i++){
						//Consultamos el producto
						tComProducto = new TComProducto();
						producto = new TransferProducto();
						producto.setId(tComPedido.getPedido().getLineaPedido(i).getIdProducto());

						producto = productos.consultaProducto(producto,3);
						tComProducto.setProducto(producto);
						if(producto.getId() == -1){
							right=false;
							errores.addError(Errores.productoNoEncontrado, tComPedido.getPedido().getLineaPedido(i).getIdProducto());
						}else{
							//Consultamos la marca
							marca = new TransferMarca();
							marca.setId(tComProducto.getProducto().getIdMarca());
							marca = marcas.consultarMarca(marca,3);
							if(marca.getId() == -1){
								right=false;
								errores.addError(Errores.marcaNoEncontrada, tComProducto.getProducto().getIdMarca());
							}else{
								tComProducto.setMarca(marca);
								listaProductos.add(tComProducto);
								
							}
						}
					}
					tComPedido.setProductos(listaProductos);
					errores.setDatos(tComPedido);
				}
			}catch(DAOException ex){
				right=false;
				errores.addError(Errores.errorDeAcceso, ex);
			}
			//If everything was OK commit, else the queries does not work
			if(right)
				transaction.commit();
			else
				transaction.rollback();
		}
		transactionManager.deleteTransaction();
		return errores;
	}

	@Override	
	public Retorno anularPedido(TransferPedido pedido)
	{
		Retorno errores = new Retorno();

		DAOPedidos DAO = FactoriaDAOPedidos.getInstancia().getInstanciaDAOPedidos();
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
				List<String> tablas = new ArrayList<String>();
				tablas.add("pedidos"); tablas.add("linea_pedido");
				transaction.lock(LockModes.ReadAndWrite, null);
				
				int idPedido = pedido.getId();
				pedido = DAO.consultarPedido(pedido,0);
				if(pedido.getId()==-1){
					right=false;
					errores.addError(Errores.pedidoNoEncontrado, idPedido);
				}
				else if(pedido.getEstado() != 'P'){
					right=false;
					errores.addError(Errores.pedidoNoEnProceso, null);
				}
				else if(!DAO.anularPedido(pedido)){
					right=false;
					errores.addError(Errores.pedidoNoAnulado, null);
				}
			}catch(DAOException ex){
				right=false;
				errores.addError(Errores.errorDeAcceso, ex);
			}
			//If everything was OK commit, else the queries does not work
			if(right)
				transaction.commit();
			else
				transaction.rollback();
		}
		//borramos la transaccion del transaction manager
		transactionManager.deleteTransaction();
		return errores;
	}

	@Override
	public Retorno completarPedido(TransferPedido pedido)
	{
		Retorno errores = new Retorno();

		DAOPedidos DAO = FactoriaDAOPedidos.getInstancia().getInstanciaDAOPedidos();
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

			try {
				//Bloqueamos la tabla marcas
				transaction.lock(LockModes.LockAll, null);
				
				int idPedido = pedido.getId();
				pedido = DAO.consultarPedido(pedido,0);
				if(pedido.getId()==-1){
					right=false;
					errores.addError(Errores.pedidoNoEncontrado, idPedido);
				}else if(pedido.getEstado() == 'P'){
					TransferProducto producto[] = new TransferProducto[pedido.getNumLineasPedido()];
					for(int i = 0; i<pedido.getNumLineasPedido(); i++){
						producto[i] = new TransferProducto();
						producto[i].setId(pedido.getLineaPedido(i).getIdProducto());
						producto[i] = DAOP.consultaProducto(producto[i],3);//Bloqueamos el producto para despues modificar el stock
						if(producto[i].getId() == -1 || producto[i].getBorrado()){
							right=false;
							errores.addError(Errores.productoNoEncontrado, pedido.getLineaPedido(i).getIdProducto());
						}else
							producto[i].setStock(producto[i].getStock() + pedido.getLineaPedido(i).getCantidad());
					}
					if(errores.getErrores().getLista().size() == 0){
						for(TransferProducto current: producto){
							if(!DAOP.modificarProducto(current)){
								right=false;
								errores.addError(Errores.productoNoModificado, current.getId()); // Este error no se producira pero no esta demas comprobarlo.
							}
						}
						if(!DAO.completarPedido(pedido)){
							right=false;
							errores.addError(Errores.pedidoNoCompletado, null);
						}
					}
				}else
					errores.addError(Errores.pedidoNoEnProceso, null);
			}catch(DAOException ex){
				right=false;
				errores.addError(Errores.errorDeAcceso, ex);
			}
			//If everything was OK commit, else the queries does not work
			if(right)
				transaction.commit();
			else
				transaction.rollback();
		}
		//borramos la transaccion del transaction manager
		transactionManager.deleteTransaction();
		return errores;
	}

	@Override
	public Retorno borrarPedido(TransferPedido pedido)
	{
		Retorno errores = new Retorno();

		DAOPedidos DAO = FactoriaDAOPedidos.getInstancia().getInstanciaDAOPedidos();
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
				List<String> tablas = new ArrayList<String>();
				tablas.add("pedidos"); tablas.add("linea_pedido");
				transaction.lock(LockModes.ReadAndWrite, null);
				
				int idPedido = pedido.getId();
				pedido = DAO.consultarPedido(pedido,0);
				if(pedido.getId()==-1){
					right=false;
					errores.addError(Errores.pedidoNoEncontrado, idPedido);
				}else if(pedido.getEstado()=='P'){
					right=false;
					errores.addError(Errores.pedidoEnProceso, null);
				}else if(!DAO.borrarPedido(pedido)){
					right=false;
					errores.addError(Errores.pedidoNoBorrado, null);
				}	
			}catch(DAOException ex){
				right=false;
				errores.addError(Errores.errorDeAcceso, ex);
			}
			//If everything was OK commit, else the queries does not work
			if(right)
				transaction.commit();
			else
				transaction.rollback();
		}
		//borramos la transaccion del transaction manager
		transactionManager.deleteTransaction();
		return errores;
	}

	@Override
	public Retorno consultarProductosProveedor(TransferProveedor proveedor){

		Retorno retorno = new Retorno();
		TComProveedorListaProductos tComProvProd = new TComProveedorListaProductos();
		DAOProveedores proveedores = FactoriaDAOProveedores.getInstancia().getInstanciaDAOProveedores();
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
				int idProveedor = proveedor.getId();
				tComProvProd.setProveedor(proveedores.consultarProveedor(proveedor,3));
				if(tComProvProd.getProveedor().getId() == -1 || !tComProvProd.getProveedor().isActivo()){
					right=false;
					retorno.addError(Errores.proveedorNoEncontrado, idProveedor);
				}else{

					DAOProductos productos = FactoriaDAOProductos.getInstancia().getInstanciaDAOProductos();
					TransferListaSuministros suministros = productos.consultarListaSuministros(tComProvProd.getProveedor(),3);

					TransferListaProductos listaProductos = new TransferListaProductos();
					Iterator<TransferSuministro> suministrosIt = suministros.getList().iterator();
					TransferSuministro current = null;
					TransferProducto producto = null;

					while(suministrosIt.hasNext()){

						current = suministrosIt.next();
						producto = new TransferProducto();
						producto.setId(current.getIdProducto());
						producto = productos.consultaProducto(producto,3);

						if(!(producto.getId() == -1 || producto.getBorrado())){
							producto.setPrecio(current.getPrecio());
							listaProductos.add(producto);
						}
						//else En principio este error no se dara nunca pero no esta demas verificar la consistencia de la base de datos
						//errores.addError(Errores.productoNoEncontrado, current.getIdProducto());
					}
					tComProvProd.setProductos(listaProductos);
					retorno.setDatos(tComProvProd);
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
}
