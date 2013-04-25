package negocio.marcas;

import negocio.Retorno;

public interface SAMarcas
{
	/**
	 * Crear marca añade a la base de datos la nueva marca creada. 
	 * Puede devolver tres tipos de errores: de Nombre repetido, de no
	 * creada y de acceso a la base de datos.
	 * @param marca con los datos de la marca a crear.
	 * @return ListaTError con los errores producidos.
	 */
	public Retorno crearMarca(TransferMarca marca);
	
	/**
	 * Editar marca modifica los datos de la marca recibida. Puede devolver
	 * tres tipos de errores: de Nombre repetido, de no modificada y de acceso
	 * a la base de datos.
	 * @param marca con los datos de la marca editada.
	 * @return ListaTError con los errores producidos.
	 */
	public Retorno editarMarca(TransferMarca marca);
	
	/**
	 * Principal Marca genera una lista de marcas y la devuelve. Puede devolver
	 * un tipo de error: de acceso a la base de datos.
	 * @param marca con la lista de marcas inicializada.
	 * @return ListaTError con los errores producidos.
	 */
	public Retorno principalMarca(TransferListaMarcas marca);
	
	/**
	 * Consultar marca recopila los datos de la marca cuya ID recibe y lo
	 * devuelve. Puede devolver dos tipos de errores: de Marca no econtrada, y de
	 * acceso a la base de datos.
	 * @param marca con la ID de la marca a consultar.
	 * @return ListaTError con los errores producidos.
	 */
	public Retorno consultarMarca(TransferMarca marca);
	
	/**
	 * Borrar marca desactiva la marca cuya ID recibe. Puede devolver dos
	 * tipos de errores: de marca no borrada y de acceso a la base de datos.
	 * @param marca con la ID de la marca a borrar.
	 * @return ListaTError con los errores producidos.
	 */
	public Retorno borrarMarca(TransferMarca marca);
	
	/**
	 * Recuperar marca reactiva la marca cuyo nombre está recibiendo. Puede
	 * devolver tres tipos de errores: de Nombre no encontrado, de marca
	 * no reactivada o de acceso a la base de datos.
	 * @param marca con el nombre de la marca a recuperar.
	 * @return ListaTError con los errores producidos.
	 */
	public Retorno recuperarMarca(TransferMarca marca);

	/**
	 * Consultar Lista Productos Marca genera una lista de productos a partir
	 * de la id de una marca recibida. Puede devolver dos tipos de errores:
	 * De marca no encontrada, o de acceso a la base de datos.
	 * @param marca con la ID de la marca a consultar.
	 * @return ListaTError con los errores producidos.
	 */
	public Retorno consultarListaProductosMarca(TComMarcaListaProductos marca);

	
}