package integracion.pedidos;

import negocio.pedidos.TransferListaPedidos;
import negocio.pedidos.TransferPedido;
import integracion.DAOException;
import negocio.productos.TransferProducto;

public interface DAOPedidos {
	/**
	 * Crear pedido, recibe un TransferPedido con el contenido del nuevo pedido a crear
	 * y lo añade a la base de datos devolviendo errores si los hay.
	 * @param pedido
	 * @return
	 * @throws DAOException
	 */
	public boolean crearPedido(TransferPedido pedido) throws DAOException;
	
	/**
	 * Consultar pedido, recibe un TransferPedido cuya ID es la ID que vamos a consultar
	 * y devuelve un Transfer con todos los datos del Pedido consultado devolviendo
	 * errores si los hay.
	 * @param pedido
	 * @return
	 * @throws DAOException
	 */
	public TransferPedido consultarPedido(TransferPedido pedido, int lockMode) throws DAOException;
	
	/**
	 * Principal Pedido devuelve una lista con todos los pedidos y errores si los hay.
	 * @return
	 * @throws DAOException
	 */
	public TransferListaPedidos principalPedido(int lockMode) throws DAOException;
	
	/**
	 * Completar Pedido recibe un TransferPedido con la ID de pedido y cambia su estado
	 * a completado.
	 * @param pedido
	 * @return
	 * @throws DAOException
	 */
	public boolean completarPedido(TransferPedido pedido) throws DAOException;
	
	/**
	 * Anular pedido recibe un TransferPedido con la ID de pedido y cambia su estado
	 * a Anulado.
	 * @param pedido
	 * @return
	 * @throws DAOException
	 */
	public boolean anularPedido(TransferPedido pedido) throws DAOException;
	
	/**
	 * Borrar pedido recibe un TransferPedido con la ID de pedido y cambia su estado
	 * a Borrado
	 * @param pedido
	 * @return
	 * @throws DAOException
	 */
	public boolean borrarPedido(TransferPedido pedido) throws DAOException;
	
	/**
	 * Consultar productos recibe un TransferProveedor con la ID de proveedor y devuelve
	 * un TransferListaProductos con una lista de productos asociados como contenido.
	 * @param proveedor
	 * @return
	 * @throws DAOException
	 */
	//public TransferListaProductos consultarProductos(TransferProveedor proveedor, int lockMode) throws DAOException;

	/**
	 * Comprueba Producto no Pendiente de Recepcion, como el nombre indica, comprueba que
	 * un producto cuya id enviamos en un TransferProducto no está pendiente de recepción.
	 * @param producto
	 * @return
	 * @throws DAOException
	 */
	boolean compruebaProductoNoPendienteDeRecepcion(TransferProducto producto, int lockMode)throws DAOException;

	/**
	 * Metodo para desbloquear las tablas
	 */
	public void desbloquearTablas() throws DAOException;
}
