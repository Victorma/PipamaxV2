package negocio.ventas.imp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import constantes.Errores;
import integracion.DAOException;
import integracion.clientes.DAOClientes;
import integracion.clientes.factoria.FactoriaDAOClientes;
import integracion.marcas.DAOMarcas;
import integracion.marcas.factoria.FactoriaDAOMarcas;
import integracion.productos.DAOProductos;
import integracion.productos.factoria.FactoriaDAOProductos;
import integracion.transaction.LockModes;
import integracion.transaction.Transaction;
import integracion.transaction.transactionManager.TransactionManager;
import integracion.ventas.DAOVentas;
import integracion.ventas.imp.DAOVentasImp;
import negocio.Retorno;
import negocio.clientes.TransferCliente;
import negocio.marcas.TransferMarca;
import negocio.productos.TComProducto;
import negocio.productos.TransferProducto;
import negocio.ventas.SAVentas;
import negocio.ventas.TComVenta;
import negocio.ventas.TransferListaVentas;
import negocio.ventas.TransferVenta;

public class SAVentasImp implements SAVentas {

	@Override
	public Retorno abrirVenta(TransferCliente cliente) {
		
		Retorno retorno = new Retorno();
		
		DAOVentas daoVentas = new DAOVentasImp();
		DAOClientes daoClientes = FactoriaDAOClientes.getInstancia().getInstanciaDAOClientes();
		
		TransactionManager.getInstancia().createTransaction();
		Transaction trans = TransactionManager.getInstancia().getTransaction();
		
		if (trans.start()) {
			try{
				trans.lock(LockModes.LockAll, null);
				
				TransferCliente comprador = daoClientes.consultarCliente(cliente, 0);
				if(comprador.getId() != -1){
					TransferVenta venta = new TransferVenta();
					venta.setIdCliente(comprador.getId());
					venta.setDescuento(comprador.getDescuento());
					
					DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
					Date date = new Date();
					venta.setFecha(dateFormat.format(date));
					
					Integer id = daoVentas.abrirVenta(venta);
					if (id != null) {
						retorno.setDatos(id);
						trans.commit();
					}else
						retorno.addError(Errores.ventaNoCreada, null);
				}else
					retorno.addError(Errores.clienteNoEncontrado, cliente.getId());
			}catch(DAOException daoe){
				retorno.addError(Errores.errorDeAcceso, daoe);
			}
			
			if (retorno.tieneErrores())
				trans.rollback();
		}
		
		TransactionManager.getInstancia().deleteTransaction();
		
		return retorno;
	}

	@Override
	public Retorno agregarProducto(TransferVenta transferVenta,
			TransferProducto producto, Integer cantidad) {
		Retorno retorno = new Retorno();
		
		DAOVentas daoVentas = new DAOVentasImp();
		DAOProductos daoProductos = FactoriaDAOProductos.getInstancia().getInstanciaDAOProductos();
		
		TransactionManager.getInstancia().createTransaction();
		Transaction trans = TransactionManager.getInstancia().getTransaction();
		
		if (trans.start()) {
			try{
				trans.lock(LockModes.LockAll, null);
				
				TransferVenta venta = daoVentas.consultaVenta(transferVenta);
				if (venta.getId() != -1) {
					TransferProducto productoAgregar = daoProductos.consultaProducto(producto, 0);
					if(productoAgregar.getId()!=-1){
						Integer pos = null;
						for(int i = 0; i< venta.getNumLineasVenta(); i++)
							if (venta.getLineaVenta(i).getIdProducto() == producto.getId()){
								pos = i;
								break;
							}
						
						if (cantidad > 0) {
							boolean agregado = false;
							if (pos == null) {
								agregado = daoVentas.agregarProducto(venta, productoAgregar, cantidad);
							} else {
								agregado = daoVentas.modificarProducto(venta, productoAgregar, venta.getLineaVenta(pos).getCantidad() + cantidad);
							}
							
							if (agregado)
								trans.commit();
							else
								retorno.addError(Errores.ventaNoCreada, null);
						}else
							retorno.addError(Errores.ventaNoCreada, cantidad);
					}else
						retorno.addError(Errores.productoNoEncontrado, producto.getId());
				}else
					retorno.addError(Errores.ventaNoEncontrada, transferVenta.getId());
			}catch(DAOException daoe){
				retorno.addError(Errores.errorDeAcceso, daoe);
			}
			
			if (retorno.tieneErrores())
				trans.rollback();
		}
		
		TransactionManager.getInstancia().deleteTransaction();
		
		return retorno;
	}

	@Override
	public Retorno quitarProducto(TransferVenta transferVenta,
			TransferProducto producto) {
		
		Retorno retorno = new Retorno();
		
		DAOVentas daoVentas = new DAOVentasImp();
		DAOProductos daoProductos = FactoriaDAOProductos.getInstancia().getInstanciaDAOProductos();
		
		TransactionManager.getInstancia().createTransaction();
		Transaction trans = TransactionManager.getInstancia().getTransaction();
		
		if (trans.start()) {
			try{
				trans.lock(LockModes.LockAll, null);
				
				TransferVenta venta = daoVentas.consultaVenta(transferVenta);
				if (venta.getId() != -1) {
					TransferProducto productoQuitar = daoProductos.consultaProducto(producto, 0);
					if(productoQuitar != null){
						Integer pos = null;
						for(int i = 0; i< venta.getNumLineasVenta(); i++)
							if (venta.getLineaVenta(i).getIdProducto() == producto.getId()){
								pos = i;
								break;
							}
						
						if (pos != null) {
							boolean quitado = daoVentas.quitarProducto(venta, productoQuitar);
							if (quitado)
								trans.commit();
							else
								retorno.addError(Errores.ventaNoCreada, null);
						}else
							retorno.addError(Errores.ventaProductoNoPertenece, productoQuitar);
					}else
						retorno.addError(Errores.productoNoEncontrado, producto.getId());
				}else
					retorno.addError(Errores.ventaNoEncontrada, transferVenta.getId());
			}catch(DAOException daoe){
				retorno.addError(Errores.errorDeAcceso, daoe);
			}
			
			if (retorno.tieneErrores())
				trans.rollback();
		}
		
		TransactionManager.getInstancia().deleteTransaction();
		
		return retorno;
	}

	@Override
	public Retorno devolucion(TransferVenta transferVenta,
			TransferProducto producto, Integer cantidad) {
		
		Retorno retorno = new Retorno();
		
		DAOVentas daoVentas = new DAOVentasImp();
		DAOProductos daoProductos = FactoriaDAOProductos.getInstancia().getInstanciaDAOProductos();
		
		TransactionManager.getInstancia().createTransaction();
		Transaction trans = TransactionManager.getInstancia().getTransaction();
		
		if (trans.start()) {
			try{
				trans.lock(LockModes.LockAll, null);
				
				TransferVenta venta = daoVentas.consultaVenta(transferVenta);
				if (venta.getId() != -1) {
					if(venta.isCerrada()) {
						TransferProducto productoQuitar = daoProductos.consultaProducto(producto, 0);
						boolean aumentarStock = true;
						if(productoQuitar.getId() == -1)
							aumentarStock = false;
						
						Integer pos = null;
						for(int i = 0; i< venta.getNumLineasVenta(); i++)
							if (venta.getLineaVenta(i).getIdProducto() == producto.getId()){
								pos = i;
								break;
							}
						
						if (pos != null) {
							if(cantidad > 0 && cantidad <= venta.getLineaVenta(pos).getCantidad()){
								boolean quitado = false;
								if(cantidad < venta.getLineaVenta(pos).getCantidad())
									quitado = daoVentas.modificarProducto(transferVenta, producto, venta.getLineaVenta(pos).getCantidad() - cantidad);
								else
									quitado = daoVentas.quitarProducto(transferVenta, producto);
								
								if (quitado){
									if (aumentarStock) {
										productoQuitar.setStock(productoQuitar.getStock() + cantidad);
										if (daoProductos.modificarProducto(productoQuitar))
											trans.commit();
										else
											retorno.addError(Errores.productoNoModificado, producto.getId());
									}else
										trans.commit();
								}else
									retorno.addError(Errores.ventaDevolucionNoRealizada, null);
							}else
								retorno.addError(Errores.ventaDevolucionNoRealizada, null);
						}else
							retorno.addError(Errores.ventaProductoNoPertenece, productoQuitar);
						//retorno.addError(Errores.productoNoEncontrado, producto.getId());
					}else
						retorno.addError(Errores.ventaNoCerrada, transferVenta.getId());
				}else
					retorno.addError(Errores.ventaNoEncontrada, transferVenta.getId());
			}catch(DAOException daoe){
				retorno.addError(Errores.errorDeAcceso, daoe);
			}
			
			if (retorno.tieneErrores())
				trans.rollback();
		}
		
		TransactionManager.getInstancia().deleteTransaction();
		
		return retorno;
	}

	@Override
	public Retorno consultaListaVentas() {
		Retorno retorno = new Retorno();
		TransferListaVentas ventas = new TransferListaVentas();
		DAOVentas DAO = new DAOVentasImp();

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
				ventas = DAO.consultaListadoVentas();
				if (ventas != null) {
					retorno.setDatos(ventas);
					transaction.commit();
				}else
					right = false;
				
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
	public Retorno consultaVenta(TransferVenta venta) {
		Retorno retorno = new Retorno();

		DAOVentas ventas = new DAOVentasImp();
		DAOClientes clientes = FactoriaDAOClientes.getInstancia().getInstanciaDAOClientes();
		DAOProductos productos = FactoriaDAOProductos.getInstancia().getInstanciaDAOProductos();
		DAOMarcas marcas = FactoriaDAOMarcas.getInstancia().getInstanciaDAOMarcas();	

		int idVenta = venta.getId();

		TComVenta tComVenta = new TComVenta();

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
				//Comprobamos que existe la venta.
				tComVenta.setVenta(ventas.consultaVenta(venta));

				right &= tComVenta.getVenta().getId() != -1;
				if(!right)
					retorno.addError(Errores.ventaNoEncontrada, idVenta);
				else{
					//Buscamos el cliente
					TransferCliente cliente = new TransferCliente();

					int idCliente = tComVenta.getVenta().getIdCliente();

					cliente.setId(idCliente);

					tComVenta.setCliente(clientes.consultarCliente(cliente,3));

					right &= tComVenta.getCliente().getId() != -1;
					if(!right)
						retorno.addError(Errores.clienteNoEncontrado, idCliente);
					else{
						//Buscamos los productos y sus marcas
						TComProducto tComProducto = null;
						TransferProducto producto = null;
						TransferMarca marca = null;

						List<TComProducto> listaProductos = new ArrayList<TComProducto>();

						for(int i = 0; i<tComVenta.getVenta().getNumLineasVenta(); i++){
							//Consultamos el producto
							tComProducto = new TComProducto();
							producto = new TransferProducto();
							producto.setId(tComVenta.getVenta().getLineaVenta(i).getIdProducto());
							producto = productos.consultaProducto(producto,3);
							tComProducto.setProducto(producto);

							right &= producto.getId() != -1;
							if(!right)
								retorno.addError(Errores.productoNoEncontrado, tComVenta.getVenta().getLineaVenta(i).getIdProducto());
							else{
								//Consultamos la marca
								marca = new TransferMarca();
								marca.setId(tComProducto.getProducto().getIdMarca());
								marca = marcas.consultarMarca(marca,3);

								right &= marca.getId() != -1;
								if(!right)
									retorno.addError(Errores.marcaNoEncontrada, tComProducto.getProducto().getIdMarca());
								else{
									tComProducto.setMarca(marca);
									listaProductos.add(tComProducto);
								}
							}
						}
						tComVenta.setProductos(listaProductos);
						retorno.setDatos(tComVenta);
					}
				}
			}catch(DAOException ex){
				retorno.addError(Errores.errorDeAcceso, ex);
				right = false;
			}

			if(right)
				transaction.commit();
			else
				transaction.rollback();

		}
		return retorno;
	}

	@Override
	public Retorno cerrarVenta(TransferVenta venta) {
		Retorno retorno = new Retorno();
		
		DAOVentas daoVentas = new DAOVentasImp();
		DAOProductos daoProductos = FactoriaDAOProductos.getInstancia().getInstanciaDAOProductos();
		
		TransactionManager.getInstancia().createTransaction();
		Transaction trans = TransactionManager.getInstancia().getTransaction();
		
		if (trans.start()) {
			try{
				trans.lock(LockModes.LockAll, null);
				TransferVenta aCerrar = daoVentas.consultaVenta(venta);
				if (aCerrar.getId() != -1) {
					if(aCerrar.getNumLineasVenta() > 0){
						for(int i = 0; i< aCerrar.getNumLineasVenta(); i++){
							TransferProducto producto = new TransferProducto();
							producto.setId(aCerrar.getLineaVenta(i).getIdProducto());
							producto = daoProductos.consultaProducto(producto, 0);
							if (producto.getId()!=-1) {
								if (producto.getStock() >= aCerrar.getLineaVenta(i).getCantidad()) {
									producto.setStock(producto.getStock() - aCerrar.getLineaVenta(i).getCantidad());
									daoProductos.modificarProducto(producto);
								}else
									retorno.addError(Errores.ventaProductoStockInsuficiente, producto);
							}else
								retorno.addError(Errores.productoNoEncontrado, aCerrar.getLineaVenta(i).getIdProducto());
						}
						if (!daoVentas.cerrarVenta(aCerrar))
							retorno.addError(Errores.ventaNoCerrada, null);
					}else
						retorno.addError(Errores.ventaSinProductos, null);
				}else
					retorno.addError(Errores.ventaNoEncontrada, venta.getId());
			}catch(DAOException daoe){
				retorno.addError(Errores.errorDeAcceso, daoe);
			}
			
			if (retorno.tieneErrores())
				trans.rollback();
			else
				trans.commit();
		}
		
		TransactionManager.getInstancia().deleteTransaction();
		
		return retorno;
	}

	@Override
	public Retorno borrarVenta(TransferVenta venta) {
		Retorno retorno = new Retorno();
		DAOVentas DAO = new DAOVentasImp();

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
				//Bloqueamos la tabla Ventas
				List<String> tablas = new ArrayList<String>();
				tablas.add("ventas"); tablas.add("lineasVenta");
				transaction.lock(LockModes.LockAll, null);
				
				right &= DAO.borraVenta(venta);
				if(!right)
					retorno.addError(Errores.ventaNoBorrada, null);
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
