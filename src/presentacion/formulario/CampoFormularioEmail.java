package presentacion.formulario;

public class CampoFormularioEmail extends CampoFormularioTexto {

	private static final long serialVersionUID = 7429837500462573011L;

	public CampoFormularioEmail(String nombre, String valorInicial) {
		super(nombre, valorInicial);
	}

	@Override
	public boolean esCorrecto() {
		String[] tokens = this.campoNombre.getText().split("@");
		if (tokens.length == 2) {
			tokens[1] = tokens[1].replace('.', '@');
			String[] derecha = tokens[1].split("@");
			if (derecha.length >= 2) {
				boolean error = false;
				for (String token : derecha)
					if (token.length() == 0)
						error = true;
				if (!error)
					return true;
			}
		}

		return false;
	}

}
