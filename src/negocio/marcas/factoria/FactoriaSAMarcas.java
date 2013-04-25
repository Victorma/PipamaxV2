package negocio.marcas.factoria;

/*
 * External imports
 */

/*
 * Internal imports
 */
import negocio.marcas.SAMarcas;
import negocio.marcas.factoria.imp.FactoriaSAMarcasImp;

public abstract class FactoriaSAMarcas {
	private static FactoriaSAMarcas instancia;

	public static FactoriaSAMarcas getInstancia() {
		if (instancia == null)
			instancia = new FactoriaSAMarcasImp();
		return instancia;
	}

	public abstract SAMarcas getInstanciaSAMarcas();
}
