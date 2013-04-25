package negocio.controlador.marcas;

import negocio.Retorno;
import negocio.controlador.Command;
import negocio.marcas.SAMarcas;
import negocio.marcas.TransferListaMarcas;
import negocio.marcas.factoria.FactoriaSAMarcas;

public class CommandMarcaListado implements Command {

	private Retorno retorno;
	private TransferListaMarcas datos;

	public CommandMarcaListado() {
		retorno = null;
		datos = null;
	}

	public CommandMarcaListado(TransferListaMarcas datos) {
		retorno = null;
		this.datos = datos;
	}

	@Override
	public Retorno execute() {
		SAMarcas SA = FactoriaSAMarcas.getInstancia().getInstanciaSAMarcas();

		retorno = SA.principalMarca(datos);
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		this.datos = (TransferListaMarcas) datos;
	}

}
