package presentacion.clientes;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import presentacion.GUI;
import negocio.controlador.ControladorAplicacion;

import negocio.Retorno;
import negocio.clientes.TransferCliente;

import constantes.Acciones;

public class BorrarClienteGUI extends GUI {

	private static final long serialVersionUID = 1L;

	private Integer id;

	//paneles
	private JPanel north;
	private JPanel south;

	public BorrarClienteGUI(Integer id, GUI padre) {
		super(padre);

		this.id = id;
		this.setLayout(new BorderLayout());

		north = new JPanel();
		// desea borrar? aceptar o cancelar.
		north.add(new JLabel("¿Deseas borrar el cliente con identificador "
				+ id + " ?"));

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
	}

	private class ListenerAceptar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			TransferCliente cliente = new TransferCliente();

			cliente.setId(id);
			ControladorAplicacion.getInstancia().accion(Acciones.clientesBorrar,
					cliente);
		}
	}

	private class ListenerCancelar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			BorrarClienteGUI.this.setVisible(false);
			BorrarClienteGUI.this.dispose();
		}
	}

	@Override
	public void actualiza(Acciones evento, Retorno datos) {
		if (!datos.tieneErrores()) {
			JOptionPane
					.showMessageDialog(this, "Cliente borrado correctamente");
			this.dispose();
		} else {
			JOptionPane.showMessageDialog(this, "Error al borrar cliente");
			this.dispose();
		}
	}

	@Override
	public void alVolver(GUI who) {
	}
}
