package integracion.proveedores;

import negocio.proveedores.TransferListaProveedores;
import negocio.proveedores.TransferProveedor;
import integracion.DAOException;


public interface DAOProveedores {

	/**
	 * Crear un proveedor.
	 * @param proveedor Transfer en el que se contiene los datos del proveedor. (El id puede ser despreciado).
	 * @return true si el proveedor se ha creado correctamente, falso en caso contrario.
	 * @throws DAOException
	 */
	public boolean crearProveedor(TransferProveedor proveedor) throws DAOException;

	/**
	 * Editar un proveedor.
	 * @param proveedor proveedor con el ID y todos sus datos. 
	 * (Todos seran reemplazados por los nuevos luego para editar hace falta conocer todos los datos en primer lugar.)
	 * @return true si el proveedor se ha editado correctamente, false en caso contrario.
	 * @throws DAOException
	 */
	public boolean editarProveedor(TransferProveedor proveedor) throws DAOException;

	/**
	 * Borrar un proveedor. El proveedor pasara a estar inactivo.
	 * @param proveedor proveedor Transfer en el que se contiene la ID de proveedor que queremos borrar.
	 * @return true si se ha podido desactivar, falso en caso contrario.
	 * @throws DAOException
	 */
	public boolean borrarProveedor(TransferProveedor proveedor) throws DAOException;

	/**
	 * Consultar los datos de un proveedor en funcion de su ID.
	 * @param proveedor proveedor Transfer en el que se contiene la ID de proveedor que queremos buscar
	 * @return el transfer con los datos del proveedor rellenos o con el id = -1 si no se ha encontrado.
	 * @throws DAOException
	 */
	public TransferProveedor consultarProveedor(TransferProveedor proveedor, int lockMode) throws DAOException;

	/**
	 * Consulta una lista de proveedores.
	 * @return El listado de proveedores activos.
	 * @throws DAOException
	 */
	public TransferListaProveedores principalProveedores(int lockMode) throws DAOException;

	/**
	 * Reactiva un proveedor.
	 * @param proveedor el proveedor que queremos activar conociendo su DNI.
	 * @return true si se ha recuperado, false en caso contrario.
	 * @throws DAOException
	 */
	public boolean recuperarProveedor(TransferProveedor proveedor) throws DAOException;

	/**
	 * Consulta un proveedor por su NIF.
	 * @param proveedor que contiene el NIF a buscar.
	 * @return un proveedor con todos los datos del proveedor o cuyo id es -1 si no se ha encontrado.
	 * @throws DAOException
	 */
	public TransferProveedor consultarProveedorNIF(TransferProveedor proveedor, int lockMode) throws DAOException;	

	/**
	 * Metodo para desbloquear las tablas
	 */
	public void desbloquearTablas() throws DAOException;
	 
	
}
