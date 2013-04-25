package negocio.clientes;

import negocio.Retorno;

public interface SAClientes {
	
	/**
	 * Crear cliente añade un cliente a la base de datos, puede devolver tres
	 * tipos de errores: de DNI repetido, de cliente No creado, o de acceso
	 * a la base de datos. 
	 * @param cliente con los datos del cliente a crear
	 * @return ListaTError con los errores producidos.
	 */
	public Retorno crearCliente(TransferCliente cliente);
	
	/**
	 * Editar cliente modifica los datos del cliente que se está editando, puede
	 * devolver tres tipos de errores: de DNI repetido, de cliente No modificado, o 
	 * de acceso a la base de datos. 
	 * @param cliente con los nuevos datos del cliente a editar.
	 * @return ListaTError con los errores producidos.
	 */
	public Retorno editarCliente(TransferCliente cliente);
	
	/**
	 * Principal Clientes carga los registros de la base de datos en una lista
	 * y la devuelve. Puede devolver un tipo de error: De acceso a la base de datos.
	 * @param cliente con la lista inicializada.
	 * @return ListaTError con los errores producidos.
	 */
	public Retorno principalClientes();
	
	/**
	 * Consultar cliente recoje los datos del cliente a consultar y los devuelve
	 * en un TransferCliente. Puede devolver dos tipos de errores: de cliente
	 * no encontrado, o de acceso a la base de datos.
	 * @param cliente con la ID del cliente a consultar
	 * @return ListaTError con los errores producidos.
	 */
	public Retorno consultarCliente(TransferCliente cliente);
	
	/**
	 * Borrar cliente cambia el estado de un cliente a borrado. Puede devolver
	 * dos tipos de errores: De cliente no borrado y de acceso a la base de datos.
	 * @param cliente con la ID del cliente a borrar.
	 * @return ListaTError con los errores producidos.
	 */
	public Retorno borrarCliente(TransferCliente cliente);
	
	/**
	 * Reactivar cliente reactiva un cliente borrado. Puede devolver tres tipos de
	 * errores: De DNI no encontrado, de No recuperado, o de acceso a la base de datos.
	 * @param cliente con el DNI del cliente a reactivar
	 * @return ListaTError con los errores producidos.
	 */
	public Retorno reactivarCliente(TransferCliente cliente);
}
