package negocio.proveedores;

import java.util.ArrayList;
import java.util.List;

public class TransferListaSuministros {
	private List<TransferSuministro> proveProdus;

	public TransferListaSuministros() {
		proveProdus = new ArrayList<TransferSuministro>();
	}

	public void add(TransferSuministro _tr) {
		proveProdus.add(_tr);
	}

	public List<TransferSuministro> getList() {
		return proveProdus;
	}

	public void setList(List<TransferSuministro> proveProdus) {
		this.proveProdus = proveProdus;
	}
}
