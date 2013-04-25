package negocio.controlador.productos;

import negocio.Retorno;
import negocio.controlador.Command;
import negocio.productos.SAProductos;
import negocio.productos.factoria.FactoriaSAProductos;

public class CommandProductoListado implements Command {

	private Retorno retorno;

	public CommandProductoListado() {
		retorno = null;

	}

	@Override
	public Retorno execute() {
		SAProductos SA = FactoriaSAProductos.getInstancia()
				.getInstanciaSAProductos();

		retorno = SA.consultarListadoProducto();
		return retorno;
	}

	@Override
	public void setContext(Object datos) {

	}

}
