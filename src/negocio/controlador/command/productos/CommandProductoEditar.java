package negocio.controlador.command.productos;

import negocio.Retorno;
import negocio.controlador.command.Command;
import negocio.productos.SAProductos;
import negocio.productos.TransferProducto;
import negocio.productos.factoria.FactoriaSAProductos;

public class CommandProductoEditar implements Command {

	private Retorno retorno;
	private TransferProducto datos;

	public CommandProductoEditar() {
		retorno = null;
		datos = null;
	}

	public CommandProductoEditar(TransferProducto datos) {
		retorno = null;
		this.datos = datos;
	}

	@Override
	public Retorno execute() {
		SAProductos SA = FactoriaSAProductos.getInstancia()
				.getInstanciaSAProductos();

		retorno = SA.modificarProducto(datos);
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		this.datos = (TransferProducto) datos;
	}

}
