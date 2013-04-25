package presentacion.clientes;

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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;

import javax.swing.*;

import presentacion.GUI;
import presentacion.controlador.*;

import negocio.Retorno;
import negocio.clientes.TransferCliente;

public class ConsultarClienteGUI extends GUI {

	private static final long serialVersionUID = 1L;
	// Paneles de la ventana
	private JPanel norte;
	private JPanel centro;
	private JPanel sur;

	// Componentes de la ventana
	private JButton aceptar;
	private JButton cancelar;
	private JLabel labelNombre;
	private JLabel labelApellido;
	private JLabel labelCiudad;
	private JLabel labelCodigoPostal;
	private JLabel labelDireccion;
	private JLabel labelTelefono;
	private JLabel labelEmail;
	private JLabel labelDNI;
	private JLabel labelTipo;
	private JLabel labelDescuento;

	public ConsultarClienteGUI(Integer id, ClientesGUI padre) {
		super(padre);

		//create the frame
		this.requestFocusInWindow();
		this.setLayout(new BorderLayout());
		this.setResizable(false);

		TransferCliente cliente = new TransferCliente();
		cliente.setId(id);
		ControladorFrontal.getInstancia().accion(Acciones.clientesConsultar,
				cliente);

		//set the configurations of the window
		this.setSize(450, 350);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private class ListenerCancelar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}

	@Override
	public void actualiza(Integer evento, Retorno datos) {

		if (datos.tieneErrores()) {

			centro = new JPanel(new FlowLayout());
			centro.add(new JLabel("Error consultando el cliente"));
			sur = new JPanel(new GridLayout());
			cancelar = new JButton("Cancelar");
			sur.add(cancelar);
			cancelar.addActionListener(new ListenerCancelar());

			this.add(centro, BorderLayout.CENTER);
			this.add(sur, BorderLayout.SOUTH);

		} else {

			TransferCliente cliente = (TransferCliente) datos.getDatos();

			norte = new JPanel();
			norte.setLayout(new FlowLayout());
			norte.add(new JLabel("Datos del cliente."));

			this.add(norte, BorderLayout.NORTH);

			centro = new JPanel();
			centro.setLayout(new GridLayout(11, 2, 10, 10));
			centro.setBorder(BorderFactory.createTitledBorder("Datos"));

			centro.add(new JLabel("Identificador: "));
			labelNombre = new JLabel(String.valueOf(cliente.getId()));
			labelNombre.setPreferredSize(new Dimension(100, 30));
			centro.add(labelNombre);

			centro.add(new JLabel("Nombre: "));
			labelNombre = new JLabel(cliente.getName());
			labelNombre.setPreferredSize(new Dimension(100, 30));
			centro.add(labelNombre);

			centro.add(new JLabel("Apellidos: "));
			labelApellido = new JLabel(cliente.getLastName());
			labelApellido.setPreferredSize(new Dimension(100, 30));
			centro.add(labelApellido);

			centro.add(new JLabel("DNI: "));
			labelDNI = new JLabel(String.valueOf(cliente.getTelephoneNumber()));
			labelDNI.setPreferredSize(new Dimension(100, 30));
			centro.add(labelDNI);

			centro.add(new JLabel("Ciudad: "));
			labelCiudad = new JLabel(cliente.getCity());
			labelCiudad.setPreferredSize(new Dimension(100, 30));
			centro.add(labelCiudad);

			centro.add(new JLabel("Código Postal: "));
			labelCodigoPostal = new JLabel(String.valueOf(cliente
					.getPostalCode()));
			labelCodigoPostal.setPreferredSize(new Dimension(100, 30));
			centro.add(labelCodigoPostal);

			centro.add(new JLabel("Direccion: "));
			labelDireccion = new JLabel(cliente.getAdress());
			labelDireccion.setPreferredSize(new Dimension(100, 30));
			centro.add(labelDireccion);

			centro.add(new JLabel("Nº telefono: "));
			labelTelefono = new JLabel(String.valueOf(cliente
					.getTelephoneNumber()));
			labelTelefono.setPreferredSize(new Dimension(100, 30));
			centro.add(labelTelefono);

			centro.add(new JLabel("Email: "));
			labelEmail = new JLabel(cliente.getEmail());
			labelEmail.setPreferredSize(new Dimension(100, 30));
			centro.add(labelEmail);

			centro.add(new JLabel("Tipo: "));
			labelTipo = new JLabel(String.valueOf(cliente.getTipo()));
			labelTipo.setPreferredSize(new Dimension(100, 30));
			centro.add(labelTipo);

			centro.add(new JLabel("Descuento: "));
			labelDescuento = new JLabel(String.valueOf(cliente.getDescuento()));
			labelDescuento.setPreferredSize(new Dimension(100, 30));
			centro.add(labelDescuento);

			this.add(centro, BorderLayout.CENTER);

			sur = new JPanel();
			sur.setLayout(new GridLayout(1, 2, 10, 10));

			aceptar = new JButton("Aceptar");
			aceptar.addActionListener(new ListenerCancelar());
			sur.add(aceptar);

			this.add(sur, BorderLayout.SOUTH);
		}

	}

	@Override
	public void alVolver(GUI who) {
	}
}
