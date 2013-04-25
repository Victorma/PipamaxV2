package negocio.proveedores;

import negocio.Retorno;

public interface SAProveedores {
	
	/**
	 * Crea provedor agrega el nuevo proveedor a la base de datos. Puede
	 * devolver tres tipos de error: De NIF repetido, de proveedor no
	 * creado o de acceso a la base de datos.
	 * @param proveedor con los datos del proveedor a crear.
	 * @return ListaTError con los errores producidos.
	 */
	public Retorno crearProveedor(TransferProveedor proveedor);
	
	/**
	 * Editar proveedor modifica los datos del proveedor a editar. Puede
	 * devolver tres tipos de errores: de NIF repetido, de proveedor no
	 * modificado, o de acceso a la base de datos.
	 * @param Proveedor con los datos modificados del proveedor.
	 * @return ListaTError con los errores producidos.
	 */
	public Retorno editarProveedor(TransferProveedor Proveedor);
	
	/**
	 * Principal Proveedores recopila todos los proveedores activos de la base
	 * de datos y los devuelve en una lista. Puede devolver un tipo de error:
	 * de acceso a la base de datos.
	 * @param Proveedor con la lista inicializada.
	 * @return ListaTError con los errores producidos.
	 */
	public Retorno principalProveedores(TransferListaProveedores Proveedor);
	
	/**
	 * Consultar Proveedor recopila los datos del proveedor cuya ID recibe. Puede
	 * devolver dos tipos de errores: De Proveedor no encontrado o de acceso
	 * a la base de datos.
	 * @param Proveedor con la ID del proveedor a consultar.
	 * @return ListaTError con los errores producidos.
	 */
	public Retorno consultarProveedor(TransferProveedor Proveedor);
	
	/**
	 * Borrar proveedor desactiva el proveedor cuya ID recibe. Puede devolver
	 * dos tipos de errores: de proveedor no borrado o de acceso a la base de datos.
	 * @param Proveedor con la ID del proveedor a borrar.
	 * @return ListaTError con los errores producidos.
	 */
	public Retorno borrarProveedor(TransferProveedor Proveedor);
	
	/**
	 * Reactivar Proveedor marca como activo el proveedor cuyo NIF recibe. Puede
	 * devolver dos tipos de errores: de NIF no encontrado, de proveedor no
	 * recuperado o de acceso a la base de datos.
	 * @param proveedor con el NIF del proveedor a reactivar.
	 * @return ListaTError con los errores producidos.
	 */
	public Retorno reactivarProveedor(TransferProveedor proveedor);

}
