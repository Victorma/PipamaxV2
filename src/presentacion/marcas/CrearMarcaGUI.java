package presentacion.marcas;

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
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;
import java.util.Iterator;

import javax.swing.*;

import presentacion.GUI;
import presentacion.controlador.*;

import negocio.Retorno;
import negocio.TError;
import negocio.marcas.TransferMarca;

public class CrearMarcaGUI extends GUI {
	private static final long serialVersionUID = 1L;

	private final Integer eventoAccion = 301;

	// Paneles de la ventana
	JPanel norte;
	JPanel centro;
	JPanel sur;

	// Componentes de la ventana
	JButton aceptar;
	JButton cancelar;
	JTextField cajaNombre;

	public CrearMarcaGUI(MarcasGUI padre) {
		super(padre);

		//create the frame
		this.setLayout(new BorderLayout());
		this.setResizable(false);

		norte = new JPanel();
		norte.setLayout(new FlowLayout());
		norte.add(new JLabel("Introduzca el nombre de la nueva marca."));

		this.add(norte, BorderLayout.NORTH);

		centro = new JPanel();
		centro.setLayout(new FlowLayout());

		centro.add(new JLabel("Nombre: "));
		cajaNombre = new JTextField();
		cajaNombre.setPreferredSize(new Dimension(300, 30));
		centro.add(cajaNombre);

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
		this.setSize(400, 125);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	class ListenerAceptar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				TransferMarca marca = new TransferMarca();
				cajaNombre.setBackground(Color.red);
				if (cajaNombre.getText().equals("")) {
					throw new InputMismatchException();
				}
				marca.setNombre(cajaNombre.getText());
				cajaNombre.setBackground(Color.green);

				ControladorFrontal.getInstancia().accion(eventoAccion, marca);
			} catch (InputMismatchException ex) {
				JOptionPane.showMessageDialog(CrearMarcaGUI.this,
						"No se permiten campos vacios.");
			}
		}
	}

	class ListenerCancelar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			CrearMarcaGUI.this.dispose();
		}
	}

	@Override
	public void actualiza(Integer evento, Retorno datos) {
		if (!datos.tieneErrores()) {
			switch (evento) {
			case Acciones.marcasCrear:
				JOptionPane.showMessageDialog(this,
						"Marca creada correctamente");
				break;
			case Acciones.marcasReactivar:
				JOptionPane.showMessageDialog(this,
						"Marca reactivada correctamente");
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
							"Error al crear la marca");
					break;
				case Errores.marcaNoRecuperada:
					JOptionPane
							.showMessageDialog(this,
									"No se ha podido recuperar la marca (¿Está la marca ya activada?)");
					break;
				case Errores.marcaNombreRepetido:
					if (JOptionPane.YES_OPTION == JOptionPane
							.showConfirmDialog(this,
									"El nombre ya existe. ¿Desea reactivarlo?")) {
						TransferMarca transfer = new TransferMarca();
						transfer.setNombre(cajaNombre.getText());
						ControladorFrontal.getInstancia().accion(
								Acciones.marcasReactivar, transfer);
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
