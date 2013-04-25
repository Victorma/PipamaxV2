package negocio.clientes.factoria.imp;

import negocio.clientes.SAClientes;
import negocio.clientes.factoria.FactoriaSAClientes;
import negocio.clientes.imp.SAClientesImp;

public class FactoriaSAClientesImp extends FactoriaSAClientes {

	public SAClientes getInstanciaSAClientes() {
		return new SAClientesImp();
	}
}
