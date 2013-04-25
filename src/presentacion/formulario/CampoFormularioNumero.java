package presentacion.formulario;

public class CampoFormularioNumero extends CampoFormularioTexto {

	private static final long serialVersionUID = 3633691120831406492L;

	public CampoFormularioNumero(String nombre, Double valorInicial) {
		super(nombre, "" + valorInicial);
	}

}