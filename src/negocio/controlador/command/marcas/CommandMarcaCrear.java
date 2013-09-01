package negocio.controlador.command.marcas;

import negocio.Retorno;
import negocio.controlador.command.Command;
import negocio.marcas.SAMarcas;
import negocio.marcas.TransferMarca;
import negocio.marcas.factoria.FactoriaSAMarcas;

public class CommandMarcaCrear implements Command {

	private Retorno retorno;
	private TransferMarca datos;

	public CommandMarcaCrear() {
		retorno = null;
		datos = null;
	}

	public CommandMarcaCrear(TransferMarca datos) {
		retorno = null;
		this.datos = datos;
	}

	@Override
	public Retorno execute() {
		SAMarcas SA = FactoriaSAMarcas.getInstancia().getInstanciaSAMarcas();

		retorno = SA.crearMarca(datos);
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		this.datos = (TransferMarca) datos;
	}

}
