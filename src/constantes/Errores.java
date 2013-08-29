package constantes;

public abstract class Errores {
	public static final int noError = 0;

	//Errores Generales	
	/**
	 * datos class is DAOException
	 */
	public static final int errorDeAcceso = 1;
	public static final int errorDeAccesoConcurrente = 2;

	//Errores de Clientes
	public static final int clienteNoEncontrado = 101;
	public static final int clienteDNIRepetido = 102;
	public static final int clienteNoEncontradoDNI = 103;
	public static final int clienteNoModificado = 104;
	public static final int clienteNoBorrado = 105;
	public static final int clienteNoCreado = 106;
	public static final int clienteNoRecuperado = 107;

	//Errores de Proveedores
	public static final int proveedorNoEncontrado = 201;
	public static final int proveedorNIFRepetido = 202;
	public static final int proveedorNoEncontradoNIF = 203;
	public static final int proveedorNoModificado = 204;
	public static final int proveedorNoBorrado = 205;
	public static final int proveedorNoCreado = 206;
	public static final int proveedorNoRecuperado = 207;

	//Errores de Marcas
	public static final int marcaNoEncontrada = 301;
	public static final int marcaNombreRepetido = 302;
	public static final int marcaNoEncontradoNombre = 303;
	public static final int marcaNoModificada = 304;
	public static final int marcaNoBorrada = 305;
	public static final int marcaNoCreada = 306;
	public static final int marcaNoRecuperada = 307;

	//Errores de productos
	public static final int productoNoEncontrado = 401;
	public static final int productoNoEncontradoDNI = 403;
	public static final int productoNoModificado = 404;
	public static final int productoNoBorrado = 405;
	public static final int productoNoCreado = 406;
	public static final int productoNoRecuperado = 407;
	public static final int productoEnProceso = 408;
	public static final int productoYaSuministrado = 410;
	public static final int productoSuministroNoCreado = 411;
	public static final int productoSuministroNoBorrado = 412;

	//Errores de pedidos
	public static final int pedidoNoEncontrado = 501;
	public static final int pedidoProductoNoSuministrado = 502;
	public static final int pedidoNoCreado = 503;
	public static final int pedidoNoBorrado = 504;
	public static final int pedidoNoCompletado = 505;
	public static final int pedidoNoAnulado = 506;
	public static final int pedidoSinProductos = 507;
	public static final int pedidoEnProceso = 508;
	public static final int pedidoNoEnProceso = 509;

	//Errores de ventas
	public static final int ventaNoEncontrada = 601;
	public static final int ventaProductoStockInsuficiente = 602;
	public static final int ventaNoCreada = 603;
	public static final int ventaNoBorrada = 604;
	public static final int ventaDevolucionNoRealizada = 605;
	public static final int ventaSinProductos = 606;
	public static final int ventaProductoNoPertenece = 607;
	public static final int ventaNoCerrada = 608;

	//Errores de empleados
	public static final int empleadoNoEncontrado = 701;
	public static final int empleadoDNIrepetido = 702;
	public static final int empleadoNoCreado = 703;
	public static final int empleadoIdDiferente = 704;
	public static final int empleadoNoBorrado = 705;
	public static final int empleadoDepartamentoNoEncontrado = 706;
	public static final int empleadoTieneProyectos = 707;

	//Errores de departamentos
	public static final int departamentoNoEncontrado = 801;
	public static final int departamentoNombreRepetido = 802;
	public static final int departamentoNoCreado = 803;
	public static final int departamentoCodigoRepetido = 804;
	public static final int departamentoNoBorrado = 805;
	public static final int departamentoConEmpleados = 806;

	//Errores de proyectos
	public static final int proyectoNoEncontrado = 901;
	public static final int proyectoNombreRepetido = 902;
	public static final int proyectoNoCreado = 903;
	public static final int proyectoNoBorrado = 904;
	public static final int proyectoNoModificado = 905;









}
