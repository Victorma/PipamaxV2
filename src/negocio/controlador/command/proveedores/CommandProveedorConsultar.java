package negocio.controlador.command.proveedores;

import negocio.Retorno;
import negocio.controlador.command.Command;
import negocio.proveedores.SAProveedores;
import negocio.proveedores.TransferProveedor;
import negocio.proveedores.factoria.FactoriaSAProveedores;

public class CommandProveedorConsultar implements Command {

	private Retorno retorno;
	private TransferProveedor datos;

	public CommandProveedorConsultar() {
		retorno = null;
		datos = null;
	}

	public CommandProveedorConsultar(TransferProveedor datos) {
		retorno = null;
		this.datos = datos;
	}

	@Override
	public Retorno execute() {
		SAProveedores SA = FactoriaSAProveedores.getInstancia()
				.getInstanciaSAProveedores();

		retorno = SA.consultarProveedor(datos);
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		this.datos = (TransferProveedor) datos;
	}

}
