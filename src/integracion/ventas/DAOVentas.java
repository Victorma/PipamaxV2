package integracion.ventas;

import integracion.DAOException;
import negocio.clientes.TransferCliente;
import negocio.productos.TransferProducto;
import negocio.ventas.TransferListaVentas;
import negocio.ventas.TransferVenta;

public interface DAOVentas {

	/** 
	 * Abrir venta recibe un transfercliente con los datos del cliente y si con
	 * correctos crea una venta y la devuelve.
	 * @param cliente
	 * @return 
	 * @throws DAOException
	 */
	public Integer abrirVenta(TransferVenta venta) throws DAOException;

	/** 
	 * Modifica Venta recibe un TransferVenta con los datos de la venta editada
	 * y la añade a la base de datos devolviendo errores si los hay.
	 * @param transferVenta
	 * @return
	 */
	public boolean agregarProducto(TransferVenta venta, TransferProducto producto, Integer cantidad) throws DAOException;
	
	/**
	 * Recibe un transferVenta con el id de la venta sobre la que deseamos realizar la
	 * devolución, un transfer producto con el id del producto que queremos devolver y
	 * un integer con la cantidad deseada. Si todo se produce correctamente aumentará
	 * el stock en el almacén y devolverá true.
	 * @param transferVenta
	 * @param producto
	 * @param cantidad
	 * @return
	 * @throws DAOException
	 */
	public boolean modificarProducto(TransferVenta transferVenta, TransferProducto producto, Integer cantidad) throws DAOException;
	
	/** 
	 * Borrar venta recive un TransferVenta con la ID de la venta a borrar y la
	 * marca como borrada.
	 * @param transferVenta
	 * @return
	 * @throws DAOException
	 */
	public boolean quitarProducto(TransferVenta venta, TransferProducto producto) throws DAOException;
	
	/** 
	 * Borrar venta recive un TransferVenta con la ID de la venta a borrar y la
	 * marca como borrada.
	 * @param transferVenta
	 * @return
	 * @throws DAOException
	 */
	public boolean cerrarVenta(TransferVenta venta) throws DAOException;

	/** 
	 * Borrar venta recive un TransferVenta con la ID de la venta a borrar y la
	 * marca como borrada.
	 * @param transferVenta
	 * @return
	 * @throws DAOException
	 */
	public boolean borraVenta(TransferVenta transferVenta) throws DAOException;
	
	/** 
	 * Consultar Venta recive un TransferVenta con la ID de la venta a consultar y
	 * devuelve un TransferVenta con el contenido de la venta ya consultada, devolviendo
	 * errores si los hubiera.
	 * @param transferVenta
	 * @return
	 * @throws DAOException
	 */
	public TransferVenta consultaVenta(TransferVenta transferVenta) throws DAOException;

	/** 
	 * Consultar Listado Ventas prepara una lista con todas las ventas y las devuelve
	 * en un TransferListaVentas.
	 * @return
	 * @throws DAOException
	 */
	public TransferListaVentas consultaListadoVentas() throws DAOException;
	
}
