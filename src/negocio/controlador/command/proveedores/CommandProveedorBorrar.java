package negocio.controlador.command.proveedores;

import negocio.Retorno;
import negocio.controlador.command.Command;
import negocio.proveedores.SAProveedores;
import negocio.proveedores.TransferProveedor;
import negocio.proveedores.factoria.FactoriaSAProveedores;

public class CommandProveedorBorrar implements Command {
	private Retorno retorno;
	private TransferProveedor datos;

	public CommandProveedorBorrar() {
		retorno = null;
		datos = null;
	}

	public CommandProveedorBorrar(TransferProveedor datos) {
		retorno = null;
		this.datos = datos;
	}

	@Override
	public Retorno execute() {
		SAProveedores SA = FactoriaSAProveedores.getInstancia()
				.getInstanciaSAProveedores();

		retorno = SA.borrarProveedor(datos);
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		this.datos = (TransferProveedor) datos;
	}

}
