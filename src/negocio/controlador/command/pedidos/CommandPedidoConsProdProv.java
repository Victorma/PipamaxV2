package negocio.controlador.command.pedidos;

import negocio.Retorno;
import negocio.controlador.command.Command;
import negocio.pedidos.SAPedidos;
import negocio.pedidos.factoria.FactoriaSAPedidos;
import negocio.proveedores.TComProveedorListaProductos;

public class CommandPedidoConsProdProv implements Command {

	private TComProveedorListaProductos datos;
	private Retorno retorno;

	public CommandPedidoConsProdProv() {
		datos = null;
	}

	public CommandPedidoConsProdProv(TComProveedorListaProductos datos) {
		this.datos = datos;
	}

	@Override
	public Retorno execute() {
		SAPedidos SA = FactoriaSAPedidos.getInstancia().getInstanciaSAPedidos();

		retorno = SA.consultarProductosProveedor(datos.getProveedor());

		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		this.datos = (TComProveedorListaProductos) datos;
	}

}
