package presentacion.formulario;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JComboBox;

public class CampoFormularioSelector<E, T> extends CampoFormulario {

	private static final long serialVersionUID = 1L;
	protected Map<E, T> datos;
	protected JComboBox<E> selector;

	public CampoFormularioSelector(String nombre, Map<E, T> campos) {
		super(nombre);
		this.datos = campos;
		selector = new JComboBox<E>();
		if (campos != null) {
			Iterator<E> it = datos.keySet().iterator();
			while (it.hasNext())
				selector.addItem(it.next());
		}

		this.add(selector, BorderLayout.CENTER);
	}

	public void setMap(Map<E, T> mapa) {
		this.datos = mapa;
		selector.removeAllItems();
		if (mapa != null) {
			Iterator<E> it = datos.keySet().iterator();
			while (it.hasNext())
				selector.addItem(it.next());
		}
	}

	@Override
	public boolean esCorrecto() {
		return datos != null && selector.getSelectedItem() != null;
	}

	public void addActionListener(ActionListener l) {
		this.selector.addActionListener(l);
	}

	public void removeActionListener(ActionListener l) {
		this.selector.removeActionListener(l);
	}

	@Override
	public void setModificable(boolean estado) {
		selector.setEnabled(estado);
	}

	@Override
	public T getResultado() {
		if (datos == null)
			return null;
		return datos.get(selector.getSelectedItem());
	}

	@Override
	public void setValue(Object value) {
		selector.setSelectedItem(value);
	}
}
