package presentacion.productos;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import presentacion.GUI;
import presentacion.controlador.ControladorFrontal;

import negocio.Retorno;
import negocio.proveedores.TransferListaProveedores;
import negocio.proveedores.TransferProveedor;
import negocio.proveedores.TransferSuministro;

import constantes.Acciones;

public class CrearProveProduGUI extends GUI {

	private static final long serialVersionUID = -4662496094595923018L;
	private JTextField idProve, precio;
	private JComboBox<String> comboProves;
	private List<TransferProveedor> listaproves;
	private Document documentIdProve;

	private int buscaIdProve(int _idmarca) {
		int ret = 0;
		try {
			for (int i = 0; i < listaproves.size() && ret == 0; i++) {
				if (listaproves.get(i).getId() == _idmarca)
					ret = i + 1;
			}
		} catch (NullPointerException ex) {
		}
		;

		return ret;
	}

	public CrearProveProduGUI(GUI father) {

		super(father);
		this.setLayout(new BorderLayout());

		JPanel center = new JPanel();

		center.setLayout(new GridLayout(2, 2));
		center.setBorder(BorderFactory.createTitledBorder("Datos"));

		center.add(new JLabel("Id Proveedor: "));
		//SCROLL DE PROVEEDOR
		JPanel jProve = new JPanel(new BorderLayout());
		idProve = new JTextField("");
		idProve.setPreferredSize(new Dimension(50, 20));
		comboProves = new JComboBox<String>();
		comboProves.addActionListener(new CambiaScroll());
		comboProves.addItem("-----------");

		documentIdProve = idProve.getDocument();
		documentIdProve.addDocumentListener(new JComboBoxStateController());
		jProve.add(idProve, BorderLayout.WEST);
		jProve.add(comboProves, BorderLayout.CENTER);
		//FIN DE SCROLL DE PROVEEDOR
		center.add(jProve);

		center.add(new JLabel("Precio: "));
		precio = new JTextField();
		precio.setPreferredSize(new Dimension(300, 30));
		center.add(precio);

		this.add(center, BorderLayout.CENTER);

		JPanel sur = new JPanel();
		sur.setLayout(new GridLayout(1, 2, 10, 10));
		JButton aceptar = new JButton("Aceptar");
		JButton cancelar = new JButton("Cancelar");
		aceptar.addActionListener(new AceptarListener());
		cancelar.addActionListener(new CancelarListener());
		sur.add(aceptar);
		sur.add(cancelar);
		this.add(sur, BorderLayout.SOUTH);

		//set the configurations of the window
		this.setSize(350, 120);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		ControladorFrontal.getInstancia().accion(Acciones.proveedoresListado,
				null);
	}

	private class CambiaScroll implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				if (comboProves.getSelectedIndex() != 0
						&& (comboProves.getSelectedIndex()) != buscaIdProve(Integer
								.parseInt(idProve.getText())))
					idProve.setText(""
							+ ((listaproves.get(comboProves.getSelectedIndex() - 1))
									.getId()));
			} catch (NumberFormatException ex) {
				idProve.setText(""
						+ ((listaproves.get(comboProves.getSelectedIndex() - 1))
								.getId()));
			}
		}

	}

	private class JComboBoxStateController implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent arg0) {
		}

		@Override
		public void insertUpdate(DocumentEvent arg0) {
			try {
				comboProves.setSelectedIndex(buscaIdProve(Integer
						.parseInt(idProve.getText())));
			} catch (NumberFormatException ex) {
				comboProves.setSelectedIndex(0);
			}
		}

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			try {
				comboProves.setSelectedIndex(buscaIdProve(Integer
						.parseInt(idProve.getText())));
			} catch (NumberFormatException ex) {
				comboProves.setSelectedIndex(0);
			}
		}

	}

	private class AceptarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (idProve.getText().equals("") || precio.getText().equals("")) {
				JOptionPane.showMessageDialog(CrearProveProduGUI.this,
						"Faltan Datos por Rellenar.");
			} else {
				try {
					if (idProve.getText().equals("")
							|| precio.getText().equals("")) {
						JOptionPane.showMessageDialog(CrearProveProduGUI.this,
								"Faltan Datos por Rellenar.");
					} else if (comboProves.getSelectedIndex() == 0) {
						JOptionPane.showMessageDialog(CrearProveProduGUI.this,
								"El proveedor introducido no es valido.");
					} else {
						TransferSuministro _aEnviar = new TransferSuministro();
						_aEnviar.setIdProducto(((EditarProductoGUI) father)
								.getId());
						_aEnviar.setIdProveedor(Integer.parseInt(idProve
								.getText()));
						_aEnviar.setPrecio(Double.parseDouble(precio.getText()));
						if (_aEnviar.getPrecio() <= 0)
							throw new NumberFormatException();

						ControladorFrontal.getInstancia().accion(
								Acciones.productosCrearSuministro, _aEnviar);
						dispose();
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(CrearProveProduGUI.this,
							"Debes introducir números.");
				}
			}
		}

	}

	private class CancelarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}

	}

	@Override
	public void actualiza(Acciones evento, Retorno datos) {
		if (evento == Acciones.proveedoresListado) {
			listaproves = ((TransferListaProveedores) datos.getDatos())
					.getLista();
			for (int i = 0; i < listaproves.size(); i++) {
				comboProves.addItem("" + listaproves.get(i).getName());
			}
			try {
				comboProves.setSelectedIndex(buscaIdProve(Integer
						.parseInt(idProve.getText())));
			} catch (NumberFormatException ex) {
				comboProves.setSelectedIndex(0);
			}
		}

	}

	@Override
	public void alVolver(GUI who) {
	}

}
