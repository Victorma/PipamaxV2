package negocio.controlador.proveedores;

import negocio.Retorno;
import negocio.controlador.Command;
import negocio.proveedores.SAProveedores;
import negocio.proveedores.TransferProveedor;
import negocio.proveedores.factoria.FactoriaSAProveedores;

public class CommandProveedorCrear implements Command {

	private Retorno retorno;
	private TransferProveedor datos;

	public CommandProveedorCrear() {
		retorno = null;
		datos = null;
	}

	public CommandProveedorCrear(TransferProveedor datos) {
		retorno = null;
		this.datos = datos;
	}

	@Override
	public Retorno execute() {
		SAProveedores SA = FactoriaSAProveedores.getInstancia()
				.getInstanciaSAProveedores();

		retorno = SA.crearProveedor(datos);
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		this.datos = (TransferProveedor) datos;
	}

}
