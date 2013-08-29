package presentacion.proveedores;

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
import negocio.TError;
import negocio.proveedores.TransferProveedor;
import constantes.Acciones;
import constantes.Errores;

public class BorrarProveedorGUI extends GUI {

	private static final long serialVersionUID = 1L;

	private Integer id;

	//paneles
	private JPanel north;
	private JPanel south;

	public BorrarProveedorGUI(Integer id, GUI father) {
		super(father);
		this.id = id;
		this.setLayout(new BorderLayout());

		north = new JPanel();
		// desea borrar? aceptar o cancelar.
		north.add(new JLabel("¿Deseas borrar el proveedor con identificador "
				+ id + "?"));

		south = new JPanel();
		south.setLayout(new GridLayout(1, 2));

		JButton aceptar = new JButton("Aceptar");
		//aceptar.setBackground(new Color(200,50,50));
		aceptar.addActionListener(new ListenerAceptar());
		//aceptar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		south.add(aceptar);

		JButton cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new ListenerCancelar());
		south.add(cancelar);
		//	cancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));

		this.add(north, BorderLayout.NORTH);
		this.add(south, BorderLayout.SOUTH);
		//set the configurations of the window

		this.setSize(350, 100);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private class ListenerAceptar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			TransferProveedor proveedor = new TransferProveedor();
			proveedor.setId(id);
			ControladorAplicacion.getInstancia().accion(
					Acciones.proveedoresBorrar, proveedor);
		}
	}

	private class ListenerCancelar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}

	@Override
	public void actualiza(Acciones evento, Retorno datos) {
		if (evento == Acciones.proveedoresBorrar) {
			if (datos.tieneErrores()){
				String message = "Error borrando el proveedor: \n";
				for(TError er : datos.getErrores().getLista()){
					switch(er.getErrorId()){
					case Errores.proveedorNoBorrado: { message += "-El Proveedor no se ha podido borrar.\n";}
					case Errores.proveedorConPedidosPendientes: { message += "-El Proveedor tiene aún pedidos pendientes de completar.\n";}
					}
					
				}
				JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
			}
			else
				JOptionPane.showMessageDialog(this,
						"Proveedor borrado correctamente.", "Correcto",
						JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}

	}

	@Override
	public void alVolver(GUI who) {
	}
}
