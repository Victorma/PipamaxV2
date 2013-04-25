package presentacion.marcas;

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

import presentacion.GUI;
import presentacion.controlador.*;

import negocio.Retorno;
import negocio.marcas.TransferMarca;

public class EditarMarcaGUI extends GUI {
	private static final long serialVersionUID = 1L;

	// Paneles de la ventana
	JPanel norte;
	JPanel centro;
	JPanel sur;

	// Componentes de la ventana
	JButton aceptar;
	JButton cancelar;
	JLabel labelId;
	JTextField cajaNombre;

	//padre de la ventana
	MarcasGUI padre;

	public EditarMarcaGUI(Integer id, MarcasGUI padre) {
		super(padre);

		this.padre = padre;

		this.setLayout(new BorderLayout());
		this.setResizable(false);

		norte = new JPanel();
		norte.setLayout(new FlowLayout());
		norte.add(new JLabel("Introduzca los datos de la nueva marca."));

		this.add(norte, BorderLayout.NORTH);

		initializeFormFields();

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
		this.setSize(400, 130);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);

		// Consultamos los datos del cliente.
		TransferMarca marca = new TransferMarca();
		marca.setId(id);
		ControladorFrontal.getInstancia().accion(Acciones.marcasConsultar,
				marca);
	}

	public void initializeFormFields() {
		centro = new JPanel();

		centro.add(new JLabel("Id: "));

		labelId = new JLabel();
		centro.add(labelId);

		centro.add(new JLabel("Nombre: "));
		cajaNombre = new JTextField();
		cajaNombre.setPreferredSize(new Dimension(300, 30));
		centro.add(cajaNombre);

		this.add(centro, BorderLayout.CENTER);
	}

	class ListenerAceptar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				TransferMarca marca = new TransferMarca();
				marca.setId(Integer.parseInt(labelId.getText()));
				cajaNombre.setBackground(Color.red);
				if (cajaNombre.getText().equals(""))
					throw new InputMismatchException();
				marca.setNombre(cajaNombre.getText());
				cajaNombre.setBackground(Color.green);

				ControladorFrontal.getInstancia().accion(Acciones.marcasEditar,
						marca);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(EditarMarcaGUI.this,
						"Deber introducir numeros.");
			} catch (InputMismatchException ex) {
				JOptionPane.showMessageDialog(EditarMarcaGUI.this,
						"No se permiten campos vacios.");
			}

		}
	}

	class ListenerCancelar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			EditarMarcaGUI.this.dispose();
		}
	}

	@Override
	public void actualiza(Integer evento, Retorno datos) {
		switch (evento) {
		case Acciones.marcasConsultar:
			if (!datos.tieneErrores()) {
				TransferMarca marca = ((TransferMarca) datos.getDatos());
				labelId.setText("" + marca.getId());
				cajaNombre.setText(marca.getNombre());
			} else {
				JOptionPane.showMessageDialog(this,
						"Error al consultar la marca.");
				this.dispose();
			}
			break;
		case Acciones.marcasEditar:
			if (!datos.tieneErrores()) {
				JOptionPane.showMessageDialog(this,
						"Marca editada correctamente.");
			} else {
				JOptionPane
						.showMessageDialog(this, "Error al editar la marca.");
			}
			this.dispose();
			break;
		}
	}

	@Override
	public void alVolver(GUI who) {
	}
}
