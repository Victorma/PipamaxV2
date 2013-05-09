package constantes;

public abstract class Constantes {
	public static final int noError = 0;

	//Errores Generales
	public static final int errorDeConexion = 1;
	public static final int errorDeAcceso = 2;

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

	//Errores de pedidos
	public static final int pedidoNoEncontrado = 501;

	//Errores de ventas
	public static final int ventaNoEncontrada = 601;
	public static final int ventaProductoStockInsuficiente = 602;
	public static final int ventaNoCreada = 603;
	public static final int ventaNoBorrada = 604;
	public static final int ventaDevolucionNoRealizada = 605;

	
	//empleados parciales tienen turno
	public static enum tTurno{mañana, tarde};
	
	
}
