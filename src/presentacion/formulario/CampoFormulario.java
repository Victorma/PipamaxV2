package presentacion.formulario;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class CampoFormulario extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel indicador = new JPanel(new FlowLayout());
	private JLabel labelnombre;

	public CampoFormulario(String nombre) {
		super(new BorderLayout());
		labelnombre = new JLabel(" " + nombre) {
			private static final long serialVersionUID = 1L;
			boolean repintado = false;

			@Override
			public void paint(Graphics g) {
				super.paint(g);
				if (!repintado) {
					CampoFormulario.this.repinta();
					repintado = true;
				}
			}

		};
		this.add(labelnombre, BorderLayout.WEST);
		this.add(indicador, BorderLayout.EAST);
		indicador.add(new JLabel("   "));
	}

	protected void repinta() {
		Dimension dimension = new Dimension(80, 20);
		if (labelnombre.getWidth() > 80) {
			dimension = new Dimension(labelnombre.getWidth() + 5, 20);
		}
		labelnombre.setPreferredSize(dimension);
		this.remove(labelnombre);
		this.add(labelnombre, BorderLayout.WEST);
		this.setVisible(true);
	}

	public void setIndicador(boolean check) {
		if (check)
			indicador.setBackground(Color.green);
		else
			indicador.setBackground(Color.red);
	}

	public abstract boolean esCorrecto();

	public abstract void setModificable(boolean estado);

	public abstract Object getResultado();

	public abstract void setValue(Object value);

}
