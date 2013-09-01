package negocio.controlador.command.productos;

import negocio.Retorno;
import negocio.controlador.command.Command;
import negocio.productos.SAProductos;
import negocio.productos.TransferProducto;
import negocio.productos.factoria.FactoriaSAProductos;

public class CommandProductoConsultar implements Command {

	private Retorno retorno;
	private TransferProducto datos;

	public CommandProductoConsultar() {
		retorno = null;
		datos = null;
	}

	public CommandProductoConsultar(TransferProducto datos) {
		retorno = null;
		this.datos = datos;
	}

	@Override
	public Retorno execute() {
		SAProductos SA = FactoriaSAProductos.getInstancia()
				.getInstanciaSAProductos();

		retorno = SA.consultarProducto(datos);
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		this.datos = (TransferProducto) datos;
	}

}
