package negocio.controlador.productos;

import negocio.Retorno;
import negocio.controlador.Command;
import negocio.productos.SAProductos;
import negocio.productos.TransferProducto;
import negocio.productos.factoria.FactoriaSAProductos;

public class CommandProductoBorrar implements Command {

	private Retorno retorno;
	private TransferProducto datos;

	public CommandProductoBorrar() {
		retorno = null;
		datos = null;
	}

	public CommandProductoBorrar(TransferProducto datos) {
		retorno = null;
		this.datos = datos;
	}

	@Override
	public Retorno execute() {
		SAProductos SA = FactoriaSAProductos.getInstancia()
				.getInstanciaSAProductos();

		retorno = SA.borrarProducto(datos);
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		this.datos = (TransferProducto) datos;
	}

}
