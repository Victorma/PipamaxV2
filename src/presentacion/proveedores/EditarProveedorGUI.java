package presentacion.proveedores;

import presentacion.GUI;
import presentacion.controlador.*;
import presentacion.proveedores.EditarProveedorGUI;
import constantes.Acciones;

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
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;

import javax.swing.*;

import negocio.Retorno;
import negocio.proveedores.TransferProveedor;

public class EditarProveedorGUI extends GUI {

	private static final long serialVersionUID = 1L;
	// Paneles de la ventana
	private JPanel norte;
	private JPanel centro;
	private JPanel sur;

	// Componentes de la ventana
	private JButton aceptar;
	private JButton cancelar;
	private JLabel labelId;
	private JTextField cajaNIF;
	private JTextField cajaNombre;
	private JTextField cajaTelefono;
	private JTextField cajaEmail;

	public EditarProveedorGUI(Integer id, GUI father) {
		super(father);

		this.setLayout(new BorderLayout());
		this.setResizable(false);

		norte = new JPanel(new FlowLayout());
		norte.add(new JLabel("Introduzca los datos del nuevo Proveedor."));

		this.add(norte, BorderLayout.NORTH);

		centro = new JPanel(new GridLayout(5, 2, 10, 10));
		centro.setBorder(BorderFactory.createTitledBorder("Datos"));

		centro.add(new JLabel("Id: "));

		labelId = new JLabel(id.toString());
		centro.add(labelId);

		centro.add(new JLabel("NIF: "));
		cajaNIF = new JTextField();
		cajaNIF.setPreferredSize(new Dimension(300, 30));
		centro.add(cajaNIF);

		centro.add(new JLabel("Nombre: "));
		cajaNombre = new JTextField();
		cajaNombre.setPreferredSize(new Dimension(300, 30));
		centro.add(cajaNombre);

		centro.add(new JLabel("Nº telefono: "));
		cajaTelefono = new JTextField();
		cajaTelefono.setPreferredSize(new Dimension(300, 30));
		centro.add(cajaTelefono);

		centro.add(new JLabel("Email: "));
		cajaEmail = new JTextField();
		cajaEmail.setPreferredSize(new Dimension(300, 30));
		centro.add(cajaEmail);

		this.add(centro, BorderLayout.CENTER);

		sur = new JPanel(new GridLayout(1, 2, 10, 10));

		aceptar = new JButton("Aceptar");
		aceptar.addActionListener(new ListenerAceptar());
		sur.add(aceptar);

		cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new ListenerCancelar());
		sur.add(cancelar);

		this.add(sur, BorderLayout.SOUTH);

		//set the configurations of the window
		this.setSize(400, 230);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);

		// Consultamos los datos del Proveedor.
		TransferProveedor Proveedor = new TransferProveedor();
		Proveedor.setId(id);
		ControladorFrontal.getInstancia().accion(Acciones.proveedoresConsultar,
				Proveedor);
	}

	private class ListenerAceptar implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			try {
				TransferProveedor Proveedor = new TransferProveedor();

				Proveedor.setId(Integer.parseInt(labelId.getText()));

				cajaNIF.setBackground(Color.red);
				Proveedor.setNif(Integer.parseInt(cajaNIF.getText()));
				if (Proveedor.getNif() < 0)
					throw new NumberFormatException();
				cajaNIF.setBackground(Color.green);

				cajaNombre.setBackground(Color.red);
				if (cajaNombre.getText().equals(""))
					throw new InputMismatchException();
				Proveedor.setName(cajaNombre.getText());
				cajaNombre.setBackground(Color.green);

				cajaTelefono.setBackground(Color.red);
				Proveedor.setTelephoneNumber(Long.parseLong(cajaTelefono
						.getText()));
				if (Proveedor.getTelephoneNumber() < 0)
					throw new NumberFormatException();
				cajaTelefono.setBackground(Color.green);

				cajaEmail.setBackground(Color.red);
				if (cajaEmail.getText().equals(""))
					throw new InputMismatchException();
				Proveedor.setEmail(cajaEmail.getText());
				cajaEmail.setBackground(Color.green);

				ControladorFrontal.getInstancia().accion(
						Acciones.proveedoresEditar, Proveedor);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(EditarProveedorGUI.this,
						"Introduce números");
			} catch (InputMismatchException ex) {
				JOptionPane.showMessageDialog(EditarProveedorGUI.this,
						"No se permiten campos vacios");
			}
		}
	}

	private class ListenerCancelar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			EditarProveedorGUI.this.dispose();
		}
	}

	@Override
	public void actualiza(Integer evento, Retorno datos) {
		switch (evento) {
		case Acciones.proveedoresConsultar:
			if (!datos.tieneErrores()) {
				TransferProveedor cliente = (TransferProveedor) datos
						.getDatos();
				labelId.setText("" + cliente.getId());
				cajaNombre.setText(cliente.getName());
				cajaTelefono.setText("" + cliente.getTelephoneNumber());
				cajaEmail.setText(cliente.getEmail());
				cajaNIF.setText("" + cliente.getNif());

			} else {
				JOptionPane.showMessageDialog(this,
						"Error al consultar proveedor.");
				this.dispose();
			}
			break;
		case Acciones.proveedoresEditar:
			if (!datos.tieneErrores()) {
				JOptionPane.showMessageDialog(this,	"Proveedor modificado correctamente.");
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Error modificando los datos del Proveedor.");
				this.dispose();
			}
			break;
		}
	}

	@Override
	public void alVolver(GUI who) {
	}
}
