package presentacion.clientes;

/*
 * ----------
 * internal imports
 * ----------
 */

import constantes.Acciones;
import constantes.Errores;

/*
 * ----------
 * External libraries
 * ----------
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import presentacion.GUI;
import presentacion.controlador.*;

import negocio.Retorno;
import negocio.TError;
import negocio.clientes.TransferCliente;

public class CrearClienteGUI extends GUI {

	private static final long serialVersionUID = 1L;
	// Paneles de la ventana
	private JPanel norte;
	private JPanel centro;
	private JPanel sur;

	// Componentes de la ventana
	private JButton aceptar;
	private JButton cancelar;
	private JTextField cajaNombre;
	private JTextField cajaApellido;
	private JTextField cajaCiudad;
	private JTextField cajaCodigoPostal;
	private JTextField cajaDireccion;
	private JTextField cajaTelefono;
	private JTextField cajaEmail;
	private JTextField cajaDNI;
	private JTextField cajaDescuento;
	private JComboBox<String> cajaTipo;

	public CrearClienteGUI(GUI padre) {
		super(padre);

		//create the frame
		this.requestFocusInWindow();
		this.setLayout(new BorderLayout());
		this.setResizable(false);

		norte = new JPanel();
		norte.setLayout(new FlowLayout());
		norte.add(new JLabel("Introduzca los datos del nuevo cliente."));

		this.add(norte, BorderLayout.NORTH);

		centro = new JPanel();
		centro.setBorder(BorderFactory.createTitledBorder("Datos"));
		centro.setLayout(new GridLayout(10, 2, 10, 10));

		centro.add(new JLabel("DNI: "));
		cajaDNI = new JTextField();
		cajaDNI.setPreferredSize(new Dimension(320, 30));
		centro.add(cajaDNI);

		centro.add(new JLabel("Nombre: "));
		cajaNombre = new JTextField();
		cajaNombre.setPreferredSize(new Dimension(310, 30));
		centro.add(cajaNombre);

		centro.add(new JLabel("Apellidos: "));
		cajaApellido = new JTextField();
		cajaApellido.setPreferredSize(new Dimension(310, 30));
		centro.add(cajaApellido);

		centro.add(new JLabel("Ciudad: "));
		cajaCiudad = new JTextField();
		cajaCiudad.setPreferredSize(new Dimension(320, 30));
		centro.add(cajaCiudad);

		centro.add(new JLabel("Código Postal: "));
		cajaCodigoPostal = new JTextField();
		cajaCodigoPostal.setPreferredSize(new Dimension(300, 30));
		centro.add(cajaCodigoPostal);

		centro.add(new JLabel("Direccion: "));
		cajaDireccion = new JTextField();
		cajaDireccion.setPreferredSize(new Dimension(320, 30));
		centro.add(cajaDireccion);

		centro.add(new JLabel("Nº teléfono: "));
		cajaTelefono = new JTextField();
		cajaTelefono.setPreferredSize(new Dimension(300, 30));
		centro.add(cajaTelefono);

		centro.add(new JLabel("Email: "));
		cajaEmail = new JTextField();
		cajaEmail.setPreferredSize(new Dimension(320, 30));
		centro.add(cajaEmail);

		centro.add(new JLabel("Tipo: "));
		cajaTipo = new JComboBox<String>();
		cajaTipo.setPreferredSize(new Dimension(320, 30));
		cajaTipo.addItem("VIP");
		cajaTipo.addItem("NoVIP");
		cajaTipo.addActionListener(new ListenerTipoCambia());
		centro.add(cajaTipo);

		centro.add(new JLabel("Descuento: "));
		cajaDescuento = new JTextField();
		cajaDescuento.setPreferredSize(new Dimension(320, 30));
		centro.add(cajaDescuento);

		this.add(centro, BorderLayout.CENTER);

		sur = new JPanel();
		sur.setLayout(new GridLayout(1, 2, 10, 10));

		aceptar = new JButton("Aceptar");
		aceptar.addActionListener(new ListenerAceptar());
		sur.add(aceptar);

		cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new ListenerCancelar());
		sur.add(cancelar);

		this.add(sur, BorderLayout.SOUTH);

		//set the configurations of the window
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private class ListenerAceptar implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			try {
				TransferCliente cliente = new TransferCliente();
				cajaDNI.setBackground(Color.red);
				cliente.setDNI(Integer.parseInt(cajaDNI.getText()));
				if (cliente.getDNI() < 0)
					throw new NumberFormatException();
				cajaDNI.setBackground(Color.green);

				cajaNombre.setBackground(Color.red);
				if (cajaNombre.getText().equals(""))
					throw new InputMismatchException();
				cliente.setName(cajaNombre.getText());
				cajaNombre.setBackground(Color.green);

				cajaApellido.setBackground(Color.red);
				if (cajaApellido.getText().equals(""))
					throw new InputMismatchException();
				cliente.setLastName(cajaApellido.getText());
				cajaApellido.setBackground(Color.green);

				cajaCiudad.setBackground(Color.red);
				if (cajaCiudad.getText().equals(""))
					throw new InputMismatchException();
				cliente.setCity(cajaCiudad.getText());
				cajaCiudad.setBackground(Color.green);

				cajaCodigoPostal.setBackground(Color.red);
				cliente.setPostalCode(Integer.parseInt(cajaCodigoPostal
						.getText()));
				if (cliente.getPostalCode() < 0)
					throw new NumberFormatException();
				cajaCodigoPostal.setBackground(Color.green);

				cajaDireccion.setBackground(Color.red);
				if (cajaDireccion.getText().equals(""))
					throw new InputMismatchException();
				cliente.setAdress(cajaDireccion.getText());
				cajaDireccion.setBackground(Color.green);

				cajaTelefono.setBackground(Color.red);
				cliente.setTelephoneNumber(Long.parseLong(cajaTelefono
						.getText()));
				if (cliente.getTelephoneNumber() < 0)
					throw new NumberFormatException();
				cajaTelefono.setBackground(Color.green);

				cajaEmail.setBackground(Color.red);
				if (cajaEmail.getText().equals(""))
					throw new InputMismatchException();
				cliente.setEmail(cajaEmail.getText());
				cajaEmail.setBackground(Color.green);

				cajaTipo.setBackground(Color.red);
				cliente.setTipo((String) cajaTipo.getSelectedItem());
				cajaTipo.setBackground(Color.green);

				cajaDescuento.setBackground(Color.red);
				if (cajaDescuento.isEnabled()) {
					cliente.setDescuento(Float.parseFloat(cajaDescuento
							.getText()));
					if (cliente.getDescuento() >= 1
							|| cliente.getDescuento() < 0) {
						JOptionPane
								.showMessageDialog(CrearClienteGUI.this,
										"Solo se admiten valores entre [0,1) para el descuento.");
						cliente.setDescuento(-1);
					}
				} else {
					cliente.setDescuento(0);
				}
				cajaDescuento.setBackground(Color.green);
				if (cliente.getDescuento() != -1)
					ControladorFrontal.getInstancia().accion(
							Acciones.clientesCrear, cliente);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(CrearClienteGUI.this,
						"Introduce Numeros");
			} catch (InputMismatchException ex) {
				JOptionPane.showMessageDialog(CrearClienteGUI.this,
						"No se admiten campos vacios");
			}

		}
	}

	private class ListenerTipoCambia implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			cajaDescuento.setEnabled(cajaTipo.getSelectedItem().toString()
					.equals("VIP"));
		}
	}

	public void actualiza(Acciones evento, Retorno datos) {

		if (!datos.tieneErrores()) {
			switch (evento) {
			case clientesCrear:
				JOptionPane.showMessageDialog(this,
						"Cliente creado correctamente");
				break;
			case clientesReactivar:
				JOptionPane.showMessageDialog(this,
						"Cliente Reactivado correctamente");
				break;
			}
			this.dispose();

		} else {
			Iterator<TError> it = datos.getErrores().getLista().iterator();
			TError current = null;

			while (it.hasNext()) {

				current = it.next();
				switch (current.getErrorId()) {
				case Errores.errorDeAcceso:
					JOptionPane.showMessageDialog(this,
							"Error al crear el cliente");
					break;
				case Errores.clienteNoRecuperado:
					JOptionPane
							.showMessageDialog(this,
									"No se ha podido recuperar el Cliente (¿El cliente esta ya activado?)");
					break;
				case Errores.clienteDNIRepetido:
					if (JOptionPane.YES_OPTION == JOptionPane
							.showConfirmDialog(this,
									"El DNI ya existe. ¿Desea reactivarlo? (Se conservaran los antiguos datos)")) {
						TransferCliente transfer = new TransferCliente();
						transfer.setDNI(Integer.parseInt(cajaDNI.getText()));
						ControladorFrontal.getInstancia().accion(
								Acciones.clientesReactivar, transfer);
					}
					break;
				}
			}

			this.dispose();
		}
	}

	private class ListenerCancelar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			CrearClienteGUI.this.setVisible(false);
			CrearClienteGUI.this.dispose();
		}
	}

	@Override
	public void alVolver(GUI who) {
	}
}
