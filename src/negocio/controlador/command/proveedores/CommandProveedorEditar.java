package negocio.controlador.command.proveedores;

import negocio.Retorno;
import negocio.controlador.command.Command;
import negocio.proveedores.SAProveedores;
import negocio.proveedores.TransferProveedor;
import negocio.proveedores.factoria.FactoriaSAProveedores;

public class CommandProveedorEditar implements Command {

	private Retorno retorno;
	private TransferProveedor datos;

	public CommandProveedorEditar() {
		retorno = null;
		datos = null;
	}

	public CommandProveedorEditar(TransferProveedor datos) {
		retorno = null;
		this.datos = datos;
	}

	@Override
	public Retorno execute() {
		SAProveedores SA = FactoriaSAProveedores.getInstancia()
				.getInstanciaSAProveedores();

		retorno = SA.editarProveedor(datos);
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		this.datos = (TransferProveedor) datos;
	}

}
