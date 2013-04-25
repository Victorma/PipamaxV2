package presentacion.formulario;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JPanel;

public class Formulario extends JPanel {

	private static final long serialVersionUID = 1L;

	private List<CampoFormulario> campos;
	@SuppressWarnings("unused")
	private int numNormales, numEspeciales;

	private JPanel normales, especiales;

	public Formulario() {
		super(new BorderLayout());
		normales = new JPanel(new GridLayout());
		especiales = new JPanel(new GridLayout());
		this.add(normales, BorderLayout.CENTER);
		this.add(especiales, BorderLayout.SOUTH);
		campos = new ArrayList<CampoFormulario>();
		GridLayout.class.cast(normales.getLayout()).setRows(1);
		GridLayout.class.cast(especiales.getLayout()).setRows(1);
	}

	public void addCampo(CampoFormulario nuevo) {
		campos.add(nuevo);

		/*if (false) {
			numEspeciales++;
			GridLayout.class.cast(especiales.getLayout())
					.setRows(numEspeciales);
			especiales.add(nuevo);
		} else {*/
			numNormales++;
			GridLayout.class.cast(normales.getLayout()).setRows(numNormales);
			normales.add(nuevo);
		//}
	}

	public boolean esCorrecto() {

		boolean correcto = true;

		Iterator<CampoFormulario> itcampos = campos.iterator();

		while (correcto && itcampos.hasNext())
			correcto = itcampos.next().esCorrecto();

		return correcto;
	}

	public void marcaErrores() {
		for (CampoFormulario campo : campos)
			campo.setIndicador(campo.esCorrecto());
	}

	public void setModificable(boolean estado) {
		for (CampoFormulario campo : campos)
			campo.setModificable(false);
	}

}
