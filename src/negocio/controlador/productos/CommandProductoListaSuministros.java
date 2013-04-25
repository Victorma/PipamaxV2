package negocio.controlador.productos;

import negocio.Retorno;
import negocio.controlador.Command;
import negocio.productos.SAProductos;
import negocio.productos.TransferProducto;
import negocio.productos.factoria.FactoriaSAProductos;

public class CommandProductoListaSuministros implements Command {

	private Retorno retorno;
	private TransferProducto datos;

	public CommandProductoListaSuministros() {
		retorno = null;
		datos = null;
	}

	public CommandProductoListaSuministros(TransferProducto datos) {
		retorno = null;
		this.datos = datos;
	}

	@Override
	public Retorno execute() {
		SAProductos SA = FactoriaSAProductos.getInstancia()
				.getInstanciaSAProductos();

		retorno = SA.consultarListaSuministros(datos);
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		this.datos = (TransferProducto) datos;
	}

}
