package presentacion.formulario;

public class CampoFormularioDNI extends CampoFormularioTexto {

	private static final long serialVersionUID = 1L;

	public CampoFormularioDNI(String nombre, String valorInicial) {
		super(nombre, valorInicial);
	}

	@Override
	public boolean esCorrecto() {

		String dni = this.campoNombre.getText();
		boolean correcto = false;

		if (dni.length() > 0)
			try {
				int numero = Integer
						.parseInt(dni.substring(0, dni.length() - 1));
				char letra = dni.charAt(dni.length() - 1);

				switch (numero % 23) {

				case 0:
					if (letra == 'T' || letra == 't')
						correcto = true;
					break;
				case 1:
					if (letra == 'R' || letra == 'r')
						correcto = true;
					break;
				case 2:
					if (letra == 'W' || letra == 'w')
						correcto = true;
					break;
				case 3:
					if (letra == 'A' || letra == 'a')
						correcto = true;
					break;
				case 4:
					if (letra == 'G' || letra == 'g')
						correcto = true;
					break;
				case 5:
					if (letra == 'M' || letra == 'm')
						correcto = true;
					break;
				case 6:
					if (letra == 'Y' || letra == 'y')
						correcto = true;
					break;
				case 7:
					if (letra == 'F' || letra == 'f')
						correcto = true;
					break;
				case 8:
					if (letra == 'P' || letra == 'p')
						correcto = true;
					break;
				case 9:
					if (letra == 'F' || letra == 'f')
						correcto = true;
					break;
				case 10:
					if (letra == 'X' || letra == 'x')
						correcto = true;
					break;
				case 11:
					if (letra == 'B' || letra == 'b')
						correcto = true;
					break;
				case 12:
					if (letra == 'N' || letra == 'n')
						correcto = true;
					break;
				case 13:
					if (letra == 'J' || letra == 'j')
						correcto = true;
					break;
				case 14:
					if (letra == 'Z' || letra == 'z')
						correcto = true;
					break;
				case 15:
					if (letra == 'S' || letra == 's')
						correcto = true;
					break;
				case 16:
					if (letra == 'Q' || letra == 'q')
						correcto = true;
					break;
				case 17:
					if (letra == 'V' || letra == 'v')
						correcto = true;
					break;
				case 18:
					if (letra == 'H' || letra == 'h')
						correcto = true;
					break;
				case 19:
					if (letra == 'L' || letra == 'l')
						correcto = true;
					break;
				case 20:
					if (letra == 'C' || letra == 'c')
						correcto = true;
					break;
				case 21:
					if (letra == 'K' || letra == 'k')
						correcto = true;
					break;
				case 22:
					if (letra == 'E' || letra == 'e')
						correcto = true;
					break;

				}

			} catch (NumberFormatException nfe) {

			}
		return correcto;
	}

}
