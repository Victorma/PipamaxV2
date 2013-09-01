package negocio.controlador.command.proveedores;

import negocio.Retorno;
import negocio.controlador.command.Command;
import negocio.proveedores.SAProveedores;
import negocio.proveedores.TransferListaProveedores;
import negocio.proveedores.factoria.FactoriaSAProveedores;

public class CommandProveedorListado implements Command {

	private Retorno retorno;
	private TransferListaProveedores datos;

	public CommandProveedorListado() {
		retorno = null;
		datos = null;
	}

	public CommandProveedorListado(TransferListaProveedores datos) {
		retorno = null;
		this.datos = datos;
	}

	@Override
	public Retorno execute() {
		SAProveedores SA = FactoriaSAProveedores.getInstancia()
				.getInstanciaSAProveedores();

		retorno = SA.principalProveedores(datos);
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		this.datos = (TransferListaProveedores) datos;
	}

}
