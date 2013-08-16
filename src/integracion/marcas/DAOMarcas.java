package integracion.marcas;

import negocio.marcas.TransferListaMarcas;
import negocio.marcas.TransferMarca;
import integracion.DAOException;


public interface DAOMarcas 
{
	/**
	 * Crea una marca. 
	 * @param marca contiene los datos de la marca.
	 * @return true si se ha creado correctamente o false en caso contrario.
	 * @throws DAOException
	 */
	public boolean crearMarca(TransferMarca marca) throws DAOException;
	
	/**
	 * Reactiva una marca.
	 * @param marca contiene el nombre de la marca a reactivar.
	 * @return true si se ha reactivado, false en caso contrario.
	 * @throws DAOException
	 */
	public boolean recuperarMarca(TransferMarca marca) throws DAOException;
	
	/**
	 * Edita una marca.
	 * @param marca Marca con sus nuevos datos.
	 * @return true si se ha editado o false si no se ha podido editar.
	 * @throws DAOException
	 */
	public boolean editarMarca(TransferMarca marca) throws DAOException;
	
	/**
	 * Listado de marcas.
	 * @return Un transfer que contiene el listado de marcas activas.
	 * @throws DAOException
	 */
	public TransferListaMarcas principalMarca(int lockMode) throws DAOException;
	
	/**
	 * Consulta una marca.
	 * @param marca Contiene la id a buscar.
	 * @return Un transfer con todos los datos de la marca.
	 * @throws DAOException
	 */
	public TransferMarca consultarMarca(TransferMarca marca, int lockMode) throws DAOException;
	
	/**
	 * Consulta una marca por nombre.
	 * @param marca Contiene el nombre a buscar.
	 * @return Un transfer con todos los datos de la marca.
	 * @throws DAOException
	 */
	public TransferMarca consultarMarcaNombre(TransferMarca marca, int lockMode) throws DAOException;
	
	/**
	 * Desactiva una marca.
	 * @param marca Contiene el id a desactivar.
	 * @return true si se ha desactivado o false si no se ha podido desactivar.
	 * @throws DAOException
	 */
	public boolean borrarMarca(TransferMarca marca) throws DAOException;

	/**
	 * Metodo para desbloquear las tablas
	 */
	public void desbloquearTablas() throws DAOException;

}
