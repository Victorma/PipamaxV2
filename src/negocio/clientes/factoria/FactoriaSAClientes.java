package negocio.clientes.factoria;

import negocio.clientes.SAClientes;
import negocio.clientes.factoria.imp.FactoriaSAClientesImp;

public abstract class FactoriaSAClientes {
	private static FactoriaSAClientes instancia;

	public static FactoriaSAClientes getInstancia() {
		if (instancia == null)
			instancia = new FactoriaSAClientesImp();
		return instancia;
	}

	public abstract SAClientes getInstanciaSAClientes();
}
