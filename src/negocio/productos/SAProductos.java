/**
 * 
 */
package negocio.productos;

import negocio.Retorno;
import negocio.proveedores.TransferSuministro;

public interface SAProductos {

	/**
	 * Crear Producto recibe los datos de un producto y lo añade a la
	 * base de datos. Puede devolver tres tipos de errores: de Marca
	 * no encontrada, de producto no creado o de acceso a la base de 
	 * datos.
	 * @param producto con los datos del producto a crear.
	 * @return ListaTError con los errores producidos.
	 */
	public Retorno crearProducto(TransferProducto producto);

	/**
	 * Borrar producto marca el producto recibido como borrado. Puede
	 * devolver tres tipos de errores: de producto en prodeso, De producto 
	 * no borrado o de acceso a la base de datos.
	 * @param producto con la ID del producto a borrar.
	 * @return ListaTError con los errores producidos.
	 */
	public Retorno borrarProducto(TransferProducto producto);

	/** 
	 * Modificar producto edita los datos del producto a modificar. Puede
	 * devolver tres tipos de errores: de marca no encontrada, de producto
	 * no modificado o de acceso a la base de datos.
	 * @param producto con los datos del producto modificados.
	 * @return ListaTError con los errores producidos.
	 */
	public Retorno modificarProducto(TransferProducto producto);

	/** 
	 * Consultar producto recopila los datos de un producto y los de su marca
	 * correspondiente. Puede devolver tres tipos de errores: de producto
	 * no encontrado, de marca no encontrada, o de acceso a la base de datos.
	 * @param producto con la ID del producto a consultar.
	 * @return ListaTError con los errores producidos.
	 */
	public Retorno consultarProducto(TransferProducto producto);
	
	/** 
	 * Consultar lista suministros recopila los suministros correspondientes
	 * al producto consultado. Puede devolver tres tipos de errores: de
	 * producto no encontrado, de proveedor no encontrado o de acceso a la
	 * base de datos.
	 * @param listaSuministros con la lista inicializada.
	 * @return ListaTError con los errores producidos.
	 */
	public Retorno consultarListaSuministros(TransferProducto producto);
	
	/** 
	 * Crear suministro añade el nuevo suministro a la base de datos. Puede
	 * devolver cinco tipos de errores: de producto no encontrado, de proveedor
	 * no encontrado, de producto ya suministrado, de suministro no creado
	 * o por ultimo de acceso a la base de datos.
	 * @param suministro con los datos del suministro a crear.
	 * @return ListaTError con los errores producidos.
	 */
	public Retorno crearSuministro(TransferSuministro suministro);
	
	/** 
	 * Borrar suministro borra el suministro de la base de datos. Puede
	 * devolver dos tipos de errores: de suministro no borrado, o de acceso
	 * a la base de datos.
	 * @param suministro con los datos del suministro a borrar.
	 * @return ListaTError con los errores producidos.
	 */
	public Retorno borrarSuministro(TransferSuministro suministro);
		
	/** 
	 * Consultar Listado Producto recopila un listado con todos los productos
	 * activos de la base de datos y lo devuelve. Puede devolver un tipo de
	 * error: De acceso a la base de datos.
	 * @param listaProductos con la lista inicializada.
	 * @return ListaTError con los errores producidos.
	 */
	public Retorno consultarListadoProducto();
}