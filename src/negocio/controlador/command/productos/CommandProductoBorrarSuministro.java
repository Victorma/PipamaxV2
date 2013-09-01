package negocio.controlador.command.productos;

import negocio.Retorno;
import negocio.controlador.command.Command;
import negocio.productos.SAProductos;
import negocio.productos.factoria.FactoriaSAProductos;
import negocio.proveedores.TransferSuministro;

public class CommandProductoBorrarSuministro implements Command {

	private Retorno retorno;
	private TransferSuministro datos;

	public CommandProductoBorrarSuministro() {
		retorno = null;
		datos = null;
	}

	public CommandProductoBorrarSuministro(TransferSuministro datos) {
		retorno = null;
		this.datos = datos;
	}

	@Override
	public Retorno execute() {
		SAProductos SA = FactoriaSAProductos.getInstancia()
				.getInstanciaSAProductos();

		retorno = SA.borrarSuministro(datos);
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		this.datos = (TransferSuministro) datos;
	}

}
