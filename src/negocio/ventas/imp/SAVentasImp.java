/**
 * 
 */
package negocio.ventas.imp;

import java.util.ArrayList;
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
import integracion.ventas.factoria.FactoriaDAOVentas;
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

	public Retorno creaVenta(TransferVenta venta)
	{

		DAOClientes clientes = FactoriaDAOClientes.getInstancia().getInstanciaDAOClientes();
		DAOProductos productos = FactoriaDAOProductos.getInstancia().getInstanciaDAOProductos();
		DAOVentas ventas = FactoriaDAOVentas.getInstancia().getInstanciaDAOVentas();
		
		Retorno salida = new Retorno();
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
				//Bloqueamos la tabla ventas
				transaction.lock(LockModes.LockAll, null);
				
				//Buscamos el cliente
				TransferCliente cliente = new TransferCliente();
				cliente.setId(venta.getIdCliente());
				cliente = clientes.consultarCliente(cliente,3);

				//Comprobamos que el cliente exista
				right &= cliente.getId() != -1;
				if(!right)
					salida.addError(Errores.clienteNoEncontrado,venta.getIdCliente());
				else {
					// Comprobamos que la venta tenga productos
					right &= venta.getNumLineasVenta() != 0;
					if(!right)
						salida.addError(Errores.ventaSinProductos, null);
					else {
						venta.setDescuento(cliente.getDescuento());
						//Comprobamos que los productos existan y tengan stock
						TransferProducto producto[] = new TransferProducto[venta.getNumLineasVenta()];

						int i = 0;		
						boolean productoNoEncontrado = false, noSuficienteStock = false;
						while(i < venta.getNumLineasVenta()){

							// Comprobamos que los productos existan y tengan stock y ademas almacenamos los datos de los productos en un array para 
							// poder utilizarlos mas adelante
							producto[i] = new TransferProducto();
							producto[i].setId(venta.getLineaVenta(i).getIdProducto());
							producto[i] = productos.consultaProducto(producto[i],3);

							productoNoEncontrado = productoNoEncontrado || producto[i].getId() == -1 || producto[i].getBorrado(); 
							if(productoNoEncontrado)
							{
								right = false;
								salida.addError(Errores.productoNoEncontrado,venta.getLineaVenta(i).getIdProducto());
							}
							else
							{
								noSuficienteStock = noSuficienteStock || producto[i].getStock() < venta.getLineaVenta(i).getCantidad();
								if(noSuficienteStock)
								{
									salida.addError(Errores.ventaProductoStockInsuficiente,new int[]{venta.getLineaVenta(i).getIdProducto(), producto[i].getStock()});
									right = false;
								}
									
							}
							i++;   
						}
						
						if(!productoNoEncontrado && !noSuficienteStock){
							int i1 = 0;
							//Actualizamos el stock de los productos
							while(i1 < venta.getNumLineasVenta()){
								venta.getLineaVenta(i1).setPrecio(producto[i1].getPrecio()); // Establecemos el precio del producto como el precio actual
								producto[i1].setStock(producto[i1].getStock() - venta.getLineaVenta(i1).getCantidad()); // Modificamos el stock
								right &= productos.modificarProducto(producto[i1]);
								if(!right)
									salida.addError(Errores.productoNoModificado, null); // Este error no deberia producirse nunca
								i1++;
							}
							//Por ultimo creamos la venta
							right &= ventas.creaVenta(venta);
							if(!right)
								salida.addError(Errores.ventaNoCreada, null);
						}
					}
				}
			}catch(DAOException ex){
				right = false;
				salida.addError(Errores.errorDeAcceso, ex);	
			}

			//If everything was OK commit, else the queries does not work
			//if(right)
				transaction.commit();
			//else
				//transaction.rollback();
		}

		//borramos la transaccion del transaction manager
		transactionManager.deleteTransaction();
		return salida;	
	}

	public Retorno borraVenta(TransferVenta transferVenta) {
		Retorno retorno = new Retorno();
		DAOVentas DAO = FactoriaDAOVentas.getInstancia().getInstanciaDAOVentas();

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
				transaction.lock(LockModes.ReadAndWrite, null);
				
				right &= DAO.borraVenta(transferVenta);
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

	public Retorno devolucion(TransferVenta transferVenta){

		Retorno retorno = new Retorno();

		TransferVenta venta = new TransferVenta();
		venta.setId(transferVenta.getId());

		DAOVentas DAO = FactoriaDAOVentas.getInstancia().getInstanciaDAOVentas();

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
				//Bloqueamos la tabla Ventas
				transaction.lock(LockModes.LockAll, null);
				
				venta = DAO.consultaVenta(venta,0);

				right &= venta.getId() != -1;
				if(!right)
					retorno.addError(Errores.ventaNoEncontrada, transferVenta.getId());
				else{
					boolean lineaVentaNoEncontrada = false;
					for(int i = 0; i < transferVenta.getNumLineasVenta(); i++){
						lineaVentaNoEncontrada = true;
						for(int j = 0; j < venta.getNumLineasVenta(); j++)
							if(venta.getLineaVenta(j).getIdProducto() == transferVenta.getLineaVenta(i).getIdProducto()){
								transferVenta.getLineaVenta(i).setIdVenta(venta.getLineaVenta(j).getIdVenta());
								lineaVentaNoEncontrada = false;
							}
						if(lineaVentaNoEncontrada)
							retorno.addError(Errores.ventaProductoNoPertenece, transferVenta.getLineaVenta(i).getIdProducto());
					}
					if(retorno.getErrores().getLista().size()==0){
						right &= DAO.devolucion(transferVenta);
						if(!right)
							retorno.addError(Errores.ventaDevolucionNoRealizada, null);
						else
							retorno.setDatos(transferVenta);
					}
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

	public Retorno consultaListaVentas() 
	{
		Retorno retorno = new Retorno();
		TransferListaVentas ventas = new TransferListaVentas();
		DAOVentas DAO = FactoriaDAOVentas.getInstancia().getInstanciaDAOVentas();

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
				ventas.setListaVentas(DAO.consultaListadoVentas(3).getListaVentas());
				retorno.setDatos(ventas);
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


	public Retorno consultaVenta(TransferVenta venta)
	{
		Retorno retorno = new Retorno();

		DAOVentas ventas = FactoriaDAOVentas.getInstancia().getInstanciaDAOVentas();
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
				tComVenta.setVenta(ventas.consultaVenta(venta,3));

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
}