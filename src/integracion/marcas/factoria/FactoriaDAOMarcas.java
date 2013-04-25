package integracion.marcas.factoria;

import integracion.marcas.DAOMarcas;
import integracion.marcas.factoria.imp.FactoriaDAOMarcasImp;

public abstract class FactoriaDAOMarcas {
	static FactoriaDAOMarcas instancia;

	public static FactoriaDAOMarcas getInstancia() {
		if (instancia == null)
			instancia = new FactoriaDAOMarcasImp();

		return instancia;
	}

	public abstract DAOMarcas getInstanciaDAOMarcas();
}
