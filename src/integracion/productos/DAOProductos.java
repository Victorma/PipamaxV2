/**
 * 
 */

package integracion.productos;

import negocio.marcas.TransferMarca;
import negocio.productos.TransferListaProductos;
import negocio.productos.TransferProducto;
import negocio.proveedores.TransferListaSuministros;
import negocio.proveedores.TransferProveedor;
import negocio.proveedores.TransferSuministro;
import integracion.DAOException;


public interface DAOProductos {

	/**
	 * Crear un producto.
	 * @param producto producto a crear, su marca debe de existir.
	 * @return true si se ha podido crear, false en caso contrario.
	 * @throws DAOException
	 */
	public boolean crearProducto(TransferProducto producto) throws DAOException;

	/** 
	 * Desactivar un producto.
	 * @param producto producto a desactivar.
	 * @return true si se ha podido borrar, false en caso contrario.
	 * @throws DAOException
	 */
	public boolean borrarProducto(TransferProducto producto) throws DAOException;

	/** 
	 * Modificar un producto.
	 * @param producto producto a modificar.
	 * @return true si se ha podido modificar, false en caso contrario.
	 * @throws DAOException
	 */
	public boolean modificarProducto(TransferProducto producto) throws DAOException;

	/** 
	 * Consultar un producto.
	 * @param producto producto que contiene el id a consultar.
	 * @return transfer con los datos del producto. Id -1 si no se ha encontrado.
	 * @throws DAOException
	 */
	public TransferProducto consultaProducto(TransferProducto producto, int lockMode) throws DAOException;
	
	/** 
	 * Consultar una lista de productos en funcion de una marca.
	 * @param marca marca con el id a consultar.
	 * @return la lista de productos activos que pertenecen a esa marca.
	 * @throws DAOException
	 */
	public TransferListaProductos productosPorMarca(TransferMarca marca, int lockMode) throws DAOException;

	/** 
	 * Consultar el listado de productos activos.
	 * @return la lista de productos activos.
	 * @throws DAOException
	 */
	public TransferListaProductos consultaListadoProductos(int lockMode) throws DAOException;
	
	/**
	 * Crear un suministro.
	 * @param suministro el suministro que contiene el producto, el proveedor y el precio acordado.
	 * @return true si se ha creado, false en caso contrario.
	 * @throws DAOException
	 */
	public boolean crearSuministro(TransferSuministro suministro) throws DAOException;
	
	/**
	 * Borrar un suministro.
	 * @param suministro el suministro que contiene el id que deseamos borrar.
	 * @return true si se ha borrado, false en caso contrario.
	 * @throws DAOException 
	 */
	public boolean borrarSuministro(TransferSuministro suministro) throws DAOException;

	/**
	 * Consultar una lista de suministros.
	 * @param producto el producto del cual queremos conocer los proveedores que lo suministran.
	 * @return la lista de los suministros que existen.
	 * @throws DAOException
	 */
	public TransferListaSuministros consultarListaSuministros(TransferProducto producto, int lockMode) throws DAOException;

	/**
	 * Consultar una lista de suministros.
	 * @param proveedor el proveedor del cual queremos conocer los productos que suministra.
	 * @return la lista de los suministros que existen.
	 * @throws DAOException
	 */
	public TransferListaSuministros consultarListaSuministros(TransferProveedor proveedor, int lockMode) throws DAOException;

	/**
	 * Consulta si existe un suministro entre un proveedor y un producto.
	 * @param suministro contiene id proveedor y id producto.
	 * @return un suministro que contiene ambas ids y el precio o cuya id es -1 si no existe.
	 * @throws DAOException 
	 */
	public TransferSuministro consultarSuministro(TransferSuministro suministro, int lockMode) throws DAOException;

	/**
	 * Metodo para desbloquear las tablas
	 */
	public void desbloquearTablas() throws DAOException;
	
}