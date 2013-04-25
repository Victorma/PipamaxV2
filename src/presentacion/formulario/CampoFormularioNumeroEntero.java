package presentacion.formulario;

import java.awt.BorderLayout;

import javax.swing.JTextField;

public class CampoFormularioNumeroEntero extends CampoFormulario {

	private static final long serialVersionUID = -5596890062426738828L;
	protected Integer min;
	protected Integer max;
	protected boolean hasMin;
	protected boolean hasMax;
	protected JTextField campoNombre;

	public CampoFormularioNumeroEntero(String nombre, int valorInicial,
			Integer min, Integer max) {
		super(nombre);

		this.add(campoNombre = new JTextField(), BorderLayout.CENTER);
		if (valorInicial != 0)
			this.campoNombre.setText(valorInicial + "");
		if (hasMin = (min != null))
			this.min = min;
		if (hasMax = (max != null))
			this.max = max;

	}

	@Override
	public boolean esCorrecto() {

		boolean correcto = false;
		if (campoNombre.getText().length() > 0)
			try {
				int numero = Integer.parseInt(campoNombre.getText());

				correcto = !(hasMin && (numero < min))
						&& !(hasMax && (numero > max));

			} catch (NumberFormatException nfe) {
			}

		return correcto;

	}

	@Override
	public Integer getResultado() {
		Integer resul = -1;
		if (campoNombre.getText().length() > 0)
			try {
				resul = Integer.parseInt(campoNombre.getText());
			} catch (NumberFormatException nfe) {
			}
		return resul;
	}

	@Override
	public void setModificable(boolean estado) {
		this.campoNombre.setEditable(false);
	}

	@Override
	public void setValue(Object value) {
		this.campoNombre.setText(value.toString());
	}
}