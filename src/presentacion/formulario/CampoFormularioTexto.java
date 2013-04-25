package presentacion.formulario;

import java.awt.BorderLayout;

import javax.swing.JTextField;

public class CampoFormularioTexto extends CampoFormulario {

	private static final long serialVersionUID = 1L;
	protected JTextField campoNombre;

	public CampoFormularioTexto(String nombre, String valorInicial) {
		super(nombre);

		this.add(campoNombre = new JTextField(), BorderLayout.CENTER);

	}

	@Override
	public boolean esCorrecto() {
		return campoNombre.getText().length() > 0;
	}

	@Override
	public String getResultado() {
		return campoNombre.getText();
	}

	@Override
	public void setValue(Object value) {
		campoNombre.setText((String) value);
	}

	@Override
	public void setModificable(boolean estado) {
		campoNombre.setEditable(false);
	}
}
