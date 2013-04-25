package integracion.clientes;

import integracion.DAOException;


import negocio.clientes.TransferCliente;
import negocio.clientes.TransferListaClientes;

public interface DAOClientes {
	
	/**
	 * Crear un cliente.
	 * @param cliente Transfer en el que se contiene los datos del cliente. (El id puede ser despreciado).
	 * @return true si el cliente se ha creado correctamente, falso en caso contrario.
	 * @throws DAOException
	 */
	public boolean crearCliente(TransferCliente cliente) throws DAOException;
	
	/**
	 * Editar un cliente.
	 * @param cliente cliente con el ID y todos sus datos. 
	 * (Todos seran reemplazados por los nuevos luego para editar hace falta conocer todos los datos en primer lugar.)
	 * @return true si el cliente se ha editado correctamente, false en caso contrario.
	 * @throws DAOException
	 */
	public boolean editarCliente(TransferCliente cliente) throws DAOException;
	
	/**
	 * Borrar un cliente. El cliente pasara a estar inactivo.
	 * @param cliente cliente Transfer en el que se contiene la ID de cliente que queremos borrar.
	 * @return true si se ha podido desactivar, falso en caso contrario.
	 * @throws DAOException
	 */
	public boolean borrarCliente(TransferCliente cliente) throws DAOException;
	
	/**
	 * Consultar los datos de un cliente en funcion de su ID.
	 * @param cliente cliente Transfer en el que se contiene la ID de cliente que queremos buscar
	 * @return el transfer con los datos del cliente rellenos o con el id = -1 si no se ha encontrado.
	 * @throws DAOException
	 */
	public TransferCliente consultarCliente(TransferCliente cliente, int lockMode) throws DAOException;
	
	/**
	 * Consulta una lista de clientes.
	 * @return El listado de clientes activos.
	 * @throws DAOException
	 */
	public TransferListaClientes principalClientes(int lockMode) throws DAOException;
	
	/**
	 * Reactiva un cliente.
	 * @param cliente el cliente que queremos activar conociendo su DNI.
	 * @return true si se ha recuperado, false en caso contrario.
	 * @throws DAOException
	 */
	public boolean recuperarCliente(TransferCliente cliente) throws DAOException;
	
	/**
	 * Consulta un cliente por su DNI.
	 * @param cliente que contiene el DNI a buscar.
	 * @return un cliente con todos los datos del cliente o cuyo id es -1 si no se ha encontrado.
	 * @throws DAOException
	 */
	public TransferCliente consultarClienteDNI(TransferCliente cliente, int lockMode) throws DAOException;
	
	/**
	 * Metodo para bloquear las tablas de clientes
	 */
	public void bloquearTablas(int lockMode) throws DAOException;
	
	/**
	 * Metodo para desbloquear las tablas
	 */
	public void desbloquearTablas() throws DAOException;
	 
}
