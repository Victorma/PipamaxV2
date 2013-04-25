package negocio.marcas.factoria.imp;

/*
 * External imports
 */

/*
 * Internal imports
 */
import negocio.marcas.imp.SAMarcasImp;
import negocio.marcas.SAMarcas;
import negocio.marcas.factoria.FactoriaSAMarcas;

public class FactoriaSAMarcasImp extends FactoriaSAMarcas {

	@Override
	public SAMarcas getInstanciaSAMarcas() {
		return new SAMarcasImp();
	}

}
