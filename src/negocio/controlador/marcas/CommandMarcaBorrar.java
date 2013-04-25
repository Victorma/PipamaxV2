package negocio.controlador.marcas;

import negocio.Retorno;
import negocio.controlador.Command;
import negocio.marcas.SAMarcas;
import negocio.marcas.TransferMarca;
import negocio.marcas.factoria.FactoriaSAMarcas;

public class CommandMarcaBorrar implements Command {

	private Retorno retorno;
	private TransferMarca datos;

	public CommandMarcaBorrar() {
		retorno = null;
		datos = null;
	}

	public CommandMarcaBorrar(TransferMarca datos) {
		retorno = null;
		this.datos = datos;
	}

	@Override
	public Retorno execute() {
		SAMarcas SA = FactoriaSAMarcas.getInstancia().getInstanciaSAMarcas();

		retorno = SA.borrarMarca(datos);
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		this.datos = (TransferMarca) datos;
	}

}
