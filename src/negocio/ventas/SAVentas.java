/**
 * 
 */
package negocio.ventas;

import negocio.Retorno;

public interface SAVentas {

	/**
	 * Recibe un transfer venta que contiene la venta a crear y sus lineas de venta
	 * Comprueba que exista el cliente, los productos y que haya al menos una linea de venta.
	 * Crea la venta.
	 * Puede retornar:
	 *  - clienteNoEncontrado
	 *  - ventaSinProductos
	 *  - productoNoEncontrado
	 *  - ventaProductoStockInsuficiente
	 *  - productoNoModificado // No deberia devolver este error, significaria un fallo grave en la BBDD
	 *  - ventaNoCreada // No deberia devolver este error, significaria un fallo grave en la BBDD
	 *  - errorDeAcceso
	 * @param transferVenta
	 * @return Una lista de errores con los posibles errores que hayan ocurrido
	 */
	public Retorno creaVenta(TransferVenta transferVenta);

	/** 
	 * Borra la venta.
	 * Puede retornar:
	 *  - ventaNoBorrada
	 *  - errorDeAcceso
	 * @param transferVenta contiene la id de la venta a borrar.
	 * @return Una lista de errores con los posibles errores que hayan ocurrido
	 */
	public Retorno borraVenta(TransferVenta transferVenta);

	/** 
	 * Devuelve las lineas de venta solicitadas, es decir, los productos que esten en ellas.
	 * Verifica que todas las lineas de venta existan en la venta y calcula las nuevas cantidades.
	 * Modifica el contenido de las lineas para dejar el contenido actual.
	 * Puede retornar:
	 *  - ventaNoEncontrada
	 *  - ventaProductoNoPertenece
	 *  - ventaDevolucionNoRealizada // No deveria devolver este error
	 *  - errorDeAcceso
	 * @param transferVenta contiene las lineas de venta a devolver
	 * @return Una lista de errores con los posibles errores que hayan ocurrido
	 */
	public Retorno devolucion(TransferVenta transferVenta);

	/** 
	 * Se consulta el listado de ventas.
	 * Puede retornar:
	 *  - errorDeAcceso
	 * @param ventas lista donde se almacenara la lista de ventas
	 * @return Una lista de errores con los posibles errores que hayan ocurrido
	 */
	public Retorno consultaListaVentas();

	/** 
	 * Se consulta la venta, productos, marcas y cliente asociados a ese producto.
	 * Se reconstruye la venta.
	 * Puede retornar:
	 *  - ventaNoEncontrada
	 *  - clienteNoEncontrado
	 *  - productoNoEncontrado
	 *  - marcaNoEncontrada
	 *  - errorDeAcceso
	 * @param venta contiene la id de la venta a consultar y es el objeto donde se reconstruye la venta.
	 * @return Una lista de errores con los posibles errores que hayan ocurrido
	 */
	public Retorno consultaVenta(TransferVenta venta);
}