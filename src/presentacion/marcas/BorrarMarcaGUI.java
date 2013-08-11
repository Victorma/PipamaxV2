package presentacion.marcas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import presentacion.GUI;
import presentacion.controlador.ControladorFrontal;

import negocio.Retorno;
import negocio.marcas.TransferListaMarcas;
import negocio.marcas.TransferMarca;

import constantes.Acciones;

public class BorrarMarcaGUI extends GUI {
	private static final long serialVersionUID = 1L;
	Integer id;

	//paneles
	JPanel north, south;

	BorrarMarcaGUI(Integer id, GUI padre) {
		super(padre);

		this.id = id;
		this.setLayout(new BorderLayout());

		north = new JPanel();
		north.add(new JLabel("¿Deseas borrar la marca con identificador " + id
				+ "?"));

		south = new JPanel();
		south.setLayout(new GridLayout(1, 2));

		JButton aceptar = new JButton("Aceptar");
		aceptar.addActionListener(new ListenerAceptar());
		south.add(aceptar);

		JButton cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new ListenerCancelar());
		south.add(cancelar);

		this.add(north, BorderLayout.NORTH);
		this.add(south, BorderLayout.SOUTH);
		//set the configurations of the window
		this.setSize(350, 100);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		setResizable(false);
	}

	class ListenerAceptar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			TransferMarca marca = new TransferMarca();
			marca.setId(id);

			ControladorFrontal.getInstancia().accion(Acciones.marcasBorrar,
					marca);
		}
	}

	class ListenerCancelar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			BorrarMarcaGUI.this.dispose();
		}
	}

	@Override
	public void actualiza(Acciones evento, Retorno datos) {

		if (!datos.tieneErrores()) {
			JOptionPane.showMessageDialog(this, "Marca borrada correctamente.");
		} else {
			JOptionPane.showMessageDialog(this, "Error borrando la marca.");
		}
		this.dispose();
	}

	@Override
	public void alVolver(GUI who) {
		ControladorFrontal.getInstancia().accion(Acciones.marcasListado,
				new TransferListaMarcas());
	}

}
