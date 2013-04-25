package negocio.pedidos;

import negocio.Retorno;
import negocio.proveedores.TransferProveedor;


public interface SAPedidos {
	
	/**
	 * Crea el pedido comprobando que existan todos los productos, que sean suministrados y
	 * que exista el provedor. Pone el pedido en estado "P" o "En proceso".
	 * Devuelve los siguientes errores:
	 *  - proveedorNoEncontrado
	 *  - pedidoSinProductos
	 *  - productoNoEncontrado
	 *  - pedidoProductoNoSuministrado
	 *  - pedidoNoCreado
	 *  - errorDeAcceso
	 * @param pedido el pedido a crear
	 * @return Una lista de errores con los posibles errores que hayan ocurrido
	 */
	public Retorno crearPedido(TransferPedido pedido);
	
	/**
	 * Borra el pedido siempre y cuando este o completado o anulado.
	 * Puede devolver los siguientes errores:
	 *  - pedidoNoEncontrado
	 *  - pedidoEnProceso
	 *  - pedidoNoBorrado
	 *  - errorDeAcceso
	 * @param pedido el pedido a borrar (id)
	 * @return Una lista de errores con los posibles errores que hayan ocurrido
	 */
	public Retorno borrarPedido(TransferPedido pedido);
	
	/**
	 * Anula el pedido siempre que este en proceso.
	 * Puede devolver:
	 *  - pedidoNoEncontrado
	 *  - pedidoNoEnProceso
	 *  - pedidoNoAnulado
	 *  - errorDeAcceso
	 * @param pedido el pedido a anular (id)
	 * @return Una lista de errores con los posibles errores que hayan ocurrido
	 */
	public Retorno anularPedido(TransferPedido pedido);
	
	/**
	 * Genera el listado de pedidos.
	 * Puede devolver:
	 *  - errorDeAcceso
	 * @param pedido el listado de pedidos a rellenar
	 * @return Una lista de errores con los posibles errores que hayan ocurrido
	 */
	public Retorno principalPedidos();
	
	/**
	 * Consulta el pedido, el proveedor, los productos y las marcas y lo monta todo en el
	 * parametro pedido.
	 * Puede retornar los errores:
	 *  - pedidoNoEncontrado
	 *  - proveedorNoEncontrado
	 *  - productoNoEncontrado
	 *  - marcaNoEncontrada
	 *  - errorDeAcceso
	 * @param pedido el pedido a montar con su id
	 * @return Una lista de errores con los posibles errores que hayan ocurrido
	 */
	public Retorno consultarPedido(TransferPedido pedido);
	
	/**
	 * Actualiza el stock de los productos porque se ha recibido un pedido.
	 * Ademas marca el pedido como completado:
	 * Puede retornar:
	 *  - pedidoNoEncontrado
	 *  - productoNoEncontrado
	 *  - productoNoModificado
	 *  - pedidoNoCompletado
	 *  - pedidoNoEnProceso
	 *  - errorDeAcceso
	 * @param pedido el pedido a completar
	 * @return Una lista de errores con los posibles errores que hayan ocurrido
	 */
	public Retorno completarPedido(TransferPedido pedido);

	/**
	 * Consulta la lista de productos de un proveedor para poder mostrarla en la interfaz.
	 * Puede retornar:
	 *  - proveedorNoEncontrado
	 *  - errorDeAcceso
	 * @param tComProvProd lista donde se montara la lista de productos y el proveedor.
	 * @return Una lista de errores con los posibles errores que hayan ocurrido
	 */
	public Retorno consultarProductosProveedor(TransferProveedor proveedor);
}
