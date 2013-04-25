package presentacion;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import negocio.Retorno;

public abstract class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	protected GUI child = null, father = null;

	public GUI(GUI father) {
		this.father = father;

		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
		});

		if (father != null) {
			father.child = this;
			father.setEnabled(false);
		}
	}

	public GUI getChild() {
		return child;
	}

	public abstract void actualiza(Integer evento, Retorno datos);

	public abstract void alVolver(GUI who);

	protected boolean transmiteActualiza(Integer evento, Retorno datos) {
		boolean transmitido = false;
		if (child != null) {
			transmitido = true;
			child.actualiza(evento, datos);
		}
		return transmitido;
	}

	public void dispose() {
		close();
		super.dispose();
	}

	private void close() {
		if (father != null) {
			father.child = null;
			father.enabled(this);
		}
	}

	private void enabled(GUI who) {
		setEnabled(true);
		alVolver(who);
		toFront();
	}

}
