package negocio.controlador.marcas;

import negocio.Retorno;
import negocio.controlador.Command;
import negocio.marcas.SAMarcas;
import negocio.marcas.TComMarcaListaProductos;
import negocio.marcas.factoria.FactoriaSAMarcas;

public class CommandMarcaListadoProductos implements Command {

	private Retorno retorno;
	private TComMarcaListaProductos datos;

	public CommandMarcaListadoProductos() {
		retorno = null;
		datos = null;
	}

	public CommandMarcaListadoProductos(TComMarcaListaProductos datos) {
		retorno = null;
		this.datos = datos;
	}

	@Override
	public Retorno execute() {
		SAMarcas SA = FactoriaSAMarcas.getInstancia().getInstanciaSAMarcas();

		retorno = SA.consultarListaProductosMarca(datos);
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		this.datos = (TComMarcaListaProductos) datos;
	}

}
