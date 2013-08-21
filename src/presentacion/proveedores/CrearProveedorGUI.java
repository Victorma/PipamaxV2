package presentacion.proveedores;

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
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;
import java.util.Iterator;

import javax.swing.*;

import presentacion.GUI;
import negocio.controlador.ControladorAplicacion;

import negocio.Retorno;
import negocio.TError;
import negocio.proveedores.TransferProveedor;

public class CrearProveedorGUI extends GUI {

	private static final long serialVersionUID = 1L;

	// Paneles de la ventana
	private JPanel norte;
	private JPanel centro;
	private JPanel sur;

	// Componentes de la ventana
	private JButton aceptar;
	private JButton cancelar;
	private JTextField cajaNIF;
	private JTextField cajaNombre;
	private JTextField cajaTelefono;
	private JTextField cajaEmail;

	public CrearProveedorGUI(GUI father) {
		super(father);

		//create the frame
		this.setLayout(new BorderLayout());
		this.setResizable(false);

		norte = new JPanel();
		norte.setLayout(new FlowLayout());
		norte.add(new JLabel("Introduzca los datos del nuevo proveedor."));

		this.add(norte, BorderLayout.NORTH);

		centro = new JPanel();
		centro.setBorder(BorderFactory.createTitledBorder("Datos"));
		centro.setLayout(new GridLayout(4, 2, 10, 10));

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
		this.setSize(400, 200);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private class ListenerAceptar implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			try {
				TransferProveedor proveedor = new TransferProveedor();
				cajaNIF.setBackground(Color.red);
				proveedor.setNif(Integer.parseInt(cajaNIF.getText()));
				if (proveedor.getNif() < 0)
					throw new NumberFormatException();
				cajaNIF.setBackground(Color.green);

				cajaNombre.setBackground(Color.red);
				if (cajaNombre.getText().equals(""))
					throw new InputMismatchException();
				proveedor.setName(cajaNombre.getText());
				cajaNombre.setBackground(Color.green);

				cajaTelefono.setBackground(Color.red);
				proveedor.setTelephoneNumber(Long.parseLong(cajaTelefono
						.getText()));
				if (proveedor.getTelephoneNumber() < 0)
					throw new NumberFormatException();
				cajaTelefono.setBackground(Color.green);

				cajaEmail.setBackground(Color.red);
				if (cajaEmail.getText().equals(""))
					throw new InputMismatchException();
				proveedor.setEmail(cajaEmail.getText());
				cajaEmail.setBackground(Color.green);

				ControladorAplicacion.getInstancia().accion(
						Acciones.proveedoresCrear, proveedor);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(CrearProveedorGUI.this,
						"Debes introducir numeros");
			} catch (InputMismatchException ex) {
				JOptionPane.showMessageDialog(CrearProveedorGUI.this,
						"No se permiten campos vacios");
			}

		}
	}

	private class ListenerCancelar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			CrearProveedorGUI.this.dispose();
		}
	}

	@Override
	public void actualiza(Acciones evento, Retorno datos) {

		if (!datos.tieneErrores()) {
			switch (evento) {
			case proveedoresCrear:
				JOptionPane.showMessageDialog(this,
						"Proveedor creado correctamente");
				break;
			case proveedoresReactivar:
				JOptionPane.showMessageDialog(this,
						"Proveedor Reactivado correctamente");
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
							"Error al crear el proveedor");
					break;
				case Errores.proveedorNoRecuperado:
					JOptionPane
							.showMessageDialog(this,
									"No se ha podido recuperar el proveedor (¿El proveedor está ya activado?)");
					break;
				case Errores.proveedorNIFRepetido:
					if (JOptionPane.YES_OPTION == JOptionPane
							.showConfirmDialog(this,
									"El DNI ya existe. ¿Desea reactivarlo? (Se conservarám los antiguos datos)")) {
						TransferProveedor transfer = new TransferProveedor();
						transfer.setNif(Integer.parseInt(cajaNIF.getText()));
						ControladorAplicacion.getInstancia().accion(
								Acciones.proveedoresReactivar, transfer);
					}
					break;
				}
			}

			this.dispose();
		}
	}

	@Override
	public void alVolver(GUI who) {
	}
}
