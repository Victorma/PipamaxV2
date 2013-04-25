package integracion.marcas.factoria.imp;

import integracion.marcas.DAOMarcas;
import integracion.marcas.factoria.FactoriaDAOMarcas;
import integracion.marcas.imp.DAOMarcasImp;

public class FactoriaDAOMarcasImp extends FactoriaDAOMarcas {

	@Override
	public DAOMarcas getInstanciaDAOMarcas() {
		return new DAOMarcasImp();
	}

}
