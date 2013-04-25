package negocio.controlador.productos;

import negocio.Retorno;
import negocio.controlador.Command;
import negocio.productos.SAProductos;
import negocio.productos.factoria.FactoriaSAProductos;
import negocio.proveedores.TransferSuministro;

public class CommandProductoCrearSuministro implements Command {

	private Retorno retorno;
	private TransferSuministro datos;

	public CommandProductoCrearSuministro() {
		retorno = null;
		datos = null;
	}

	public CommandProductoCrearSuministro(TransferSuministro datos) {
		retorno = null;
		this.datos = datos;
	}

	@Override
	public Retorno execute() {
		SAProductos SA = FactoriaSAProductos.getInstancia()
				.getInstanciaSAProductos();

		retorno = SA.crearSuministro(datos);
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		this.datos = (TransferSuministro) datos;
	}

}
