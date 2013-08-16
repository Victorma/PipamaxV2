/**
 * 
 */
package integracion.ventas;


import negocio.ventas.TransferListaVentas;
import negocio.ventas.TransferVenta;
import integracion.DAOException;



public interface DAOVentas {
	/** 
	 * Crear venta recibe un TransferVenta con los datos de la venta y la añade
	 * a la base de datos devolviendo errores si los hay.
	 * @param cliente
	 * @return
	 * @throws DAOException
	 */
	public boolean creaVenta(TransferVenta transferVenta) throws DAOException;

	/** 
	 * Modifica Venta recibe un TransferVenta con los datos de la venta editada
	 * y la añade a la base de datos devolviendo errores si los hay.
	 * @param transferVenta
	 * @return
	 */
	public boolean modificaVenta(TransferVenta transferVenta);

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
	public TransferVenta consultaVenta(TransferVenta transferVenta, int lockMode) throws DAOException;

	/** 
	 * Consultar Listado Ventas prepara una lista con todas las ventas y las devuelve
	 * en un TransferListaVentas.
	 * @return
	 * @throws DAOException
	 */
	public TransferListaVentas consultaListadoVentas(int lockMode) throws DAOException;

	/**
	 * Devolucion decive un TransferVenta con las nuevas lineas de venta que vamos a
	 * actualizar con las cantidades que vamos a poner nuevas, devolviendo errores
	 * si los hubiera.
	 * @param transferVenta
	 * @return
	 * @throws DAOException 
	 */
	public boolean devolucion(TransferVenta transferVenta) throws DAOException;
	
	/**
	 * Metodo para desbloquear las tablas
	 */
	public void desbloquearTablas() throws DAOException;
}