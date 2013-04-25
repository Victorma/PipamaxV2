package presentacion.productos;

/*
 * ----------
 * internal imports
 * ----------
 */

import constantes.Acciones;

/*
 * ----------
 * External libraries
 * ----------
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import presentacion.GUI;
import presentacion.controlador.*;

import negocio.Retorno;
import negocio.marcas.TransferListaMarcas;
import negocio.marcas.TransferMarca;
import negocio.productos.TransferProducto;

public class CrearProductoGUI extends GUI {
	private static final long serialVersionUID = 6981409792531059969L;
	//frames and panels
	private JTextField nombre;
	private JTextField idMarca;
	private JTextField precioVenta;
	private JComboBox<String> scrollmarca;
	private Document documentidmarca;
	private List<TransferMarca> listamarcas;

	//private JFrame padre;

	private int buscaIdMarca(int _idmarca) {
		int ret = 0;
		try {
			for (int i = 0; i < listamarcas.size() && ret == 0; i++) {
				if (listamarcas.get(i).getId() == _idmarca)
					ret = i + 1;
			}
		} catch (NullPointerException ex) {
		}
		;

		return ret;
	}

	public CrearProductoGUI(GUI father) {
		//create the frame
		super(father);
		this.setLayout(new BorderLayout());

		JPanel center = new JPanel();

		center.setLayout(new GridLayout(3, 3));
		center.setBorder(BorderFactory.createTitledBorder("Datos"));

		//SCROLL DE MARCA
		JPanel jMarca = new JPanel(new BorderLayout());
		idMarca = new JTextField();
		idMarca.setPreferredSize(new Dimension(50, 20));
		scrollmarca = new JComboBox<String>();
		scrollmarca.addActionListener(new CambiaScroll());
		scrollmarca.addItem("-----------");
		documentidmarca = idMarca.getDocument();
		documentidmarca.addDocumentListener(new JComboBoxStateController());
		jMarca.add(idMarca, BorderLayout.WEST);
		jMarca.add(scrollmarca, BorderLayout.CENTER);
		//FIN DE SCROLL DE MARCA

		nombre = new JTextField();
		nombre.setPreferredSize(new Dimension(300, 30));
		precioVenta = new JTextField();
		precioVenta.setPreferredSize(new Dimension(300, 30));

		center.add(new JLabel("Nombre: "));
		center.add(nombre);
		center.add(new JLabel("ID Marca: "));
		center.add(jMarca);
		center.add(new JLabel("Precio Venta: "));
		center.add(precioVenta);

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
		this.setAlwaysOnTop(true);
		this.setSize(400, 160);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		TransferListaMarcas tlm = new TransferListaMarcas();
		tlm.setLista(new ArrayList<TransferMarca>());
		ControladorFrontal.getInstancia().accion(300, tlm);

	}

	private class CambiaScroll implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				if (scrollmarca.getSelectedIndex() != 0
						&& (scrollmarca.getSelectedIndex()) != buscaIdMarca(Integer
								.parseInt(idMarca.getText())))
					idMarca.setText(""
							+ ((listamarcas.get(scrollmarca.getSelectedIndex() - 1))
									.getId()));
			} catch (NumberFormatException ex) {
				idMarca.setText(""
						+ ((listamarcas.get(scrollmarca.getSelectedIndex() - 1))
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
				scrollmarca.setSelectedIndex(buscaIdMarca(Integer
						.parseInt(idMarca.getText())));
			} catch (NumberFormatException ex) {
				scrollmarca.setSelectedIndex(0);
			}
		}

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			try {
				scrollmarca.setSelectedIndex(buscaIdMarca(Integer
						.parseInt(idMarca.getText())));
			} catch (NumberFormatException ex) {
				scrollmarca.setSelectedIndex(0);
			}
		}

	}

	private class AceptarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				if (scrollmarca.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(CrearProductoGUI.this,
							"La marca introducida no es válida.");
					scrollmarca.setBackground(Color.red);
				} else {
					TransferProducto _aEnviar = new TransferProducto();

					nombre.setBackground(Color.red);
					if (nombre.getText().equals(""))
						throw new InputMismatchException();
					_aEnviar.setNombre(nombre.getText());
					nombre.setBackground(Color.green);

					idMarca.setBackground(Color.red);
					if (idMarca.getText().equals(""))
						throw new InputMismatchException();
					_aEnviar.setIdMarca(Integer.parseInt(idMarca.getText()));
					idMarca.setBackground(Color.green);

					scrollmarca.setBackground(Color.green);

					precioVenta.setBackground(Color.red);
					if (precioVenta.getText().equals(""))
						throw new InputMismatchException();
					_aEnviar.setPrecio(Double.parseDouble(precioVenta.getText()));
					if (_aEnviar.getPrecio() <= 0)
						throw new NumberFormatException();
					precioVenta.setBackground(Color.green);

					_aEnviar.setStock(0);
					_aEnviar.setBorrado(false);

					ControladorFrontal.getInstancia().accion(
							Acciones.productosCrear, _aEnviar);

					dispose();
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(CrearProductoGUI.this,
						"Debes introducir numeros.");
			} catch (InputMismatchException ex) {
				JOptionPane.showMessageDialog(CrearProductoGUI.this,
						"No se permiten campos vacios.");
			}
		}
	}

	private class CancelarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}

	}

	@Override
	public void actualiza(Integer evento, Retorno datos) {
		switch (evento) {
		case Acciones.marcasListado:
			if (!datos.tieneErrores()) {
				listamarcas = ((TransferListaMarcas) datos.getDatos())
						.getLista();
				for (int i = 0; i < listamarcas.size(); i++) {
					scrollmarca.addItem("" + listamarcas.get(i).getNombre());
				}
				try {
					scrollmarca.setSelectedIndex(buscaIdMarca(Integer
							.parseInt(idMarca.getText())));
				} catch (NumberFormatException ex) {
					scrollmarca.setSelectedIndex(0);
				}
			} else {
				JOptionPane.showMessageDialog(this,
						"Error al obtener el listado de marcas");
			}

			break;
		case Acciones.productosCrear:
			if (!datos.tieneErrores()) {
				JOptionPane.showMessageDialog(this,
						"Producto creado correctamente");
			} else {
				JOptionPane.showMessageDialog(this,
						"Error al crear el producto");
			}
			break;
		default:
		}
	}

	@Override
	public void alVolver(GUI who) {
	}
}
