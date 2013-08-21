package presentacion.empleados;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import constantes.Acciones;
import constantes.Errores;

import negocio.Retorno;
import negocio.TError;
import negocio.empleados.Empleado;
import negocio.proyectos.Proyecto;
import presentacion.GUI;
import negocio.controlador.ControladorAplicacion;
import presentacion.empleados.tabla.TablaEmpleados;

public class EmpleadosGUI extends GUI {

	private static final long serialVersionUID = -1124401987989317611L;
	private TablaEmpleados tablaEmpleadosModel;
	private JTable tablaEmpleados;

	public EmpleadosGUI(GUI father) {
		super(father);
		this.setLayout(new BorderLayout());
		this.setTitle("PIPAMAX - EMPLEADOS");

		JPanel top = new JPanel(new FlowLayout());
		top.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createDashedBorder(Color.GREEN, 5, 4),
				"Estadisticas"));
		JLabel textoTop = new JLabel("Empleados");
		textoTop.setSize(0, 50);
		top.add(textoTop);
		this.add(top, BorderLayout.NORTH);

		//tablaVentas.getRowCount();

		/* #############
		 * PANEL BOTONES
		 * #############
		 */
		JPanel left = new JPanel(new BorderLayout());
		left.setBorder(BorderFactory.createTitledBorder("Menu"));
		JPanel leftTop = new JPanel(new FlowLayout()), leftBot = new JPanel(
				new FlowLayout());
		JButton alta, consultar, editar, borrar, salir;

		alta = new JButton("Alta");
		alta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				new CrearEmpleadoGUI(EmpleadosGUI.this);
			}
		});
		alta.setPreferredSize(new Dimension(130, 30));
		leftTop.add(alta);

		consultar = new JButton("Consultar");
		consultar.setPreferredSize(new Dimension(130, 30));
		consultar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Empleado empleado = getSelectedEmpleado();
				new ConsultaEmpleadoGUI(EmpleadosGUI.this, empleado);

			}
		});
		leftTop.add(consultar);

		editar = new JButton("Editar");
		editar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Empleado empleado = getSelectedEmpleado();
				new EditarEmpleadoGUI(EmpleadosGUI.this, empleado);

			}
		});
		editar.setPreferredSize(new Dimension(130, 30));
		leftTop.add(editar);

		borrar = new JButton("Borrar");
		borrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Empleado empleado = getSelectedEmpleado();
				if (empleado != null) {
					GUI consultar = new ConsultaEmpleadoGUI(EmpleadosGUI.this,
							empleado);
					if (getChild() != null) {
						int respuesta = JOptionPane.showConfirmDialog(
								consultar, "¿Desea borrar este empleado?",
								"Borrar", JOptionPane.YES_NO_OPTION);
						consultar.dispose();
						if (respuesta == JOptionPane.YES_OPTION) {
							ControladorAplicacion.getInstancia().accion(
									Acciones.empleadosBorrar, empleado);
							ControladorAplicacion.getInstancia().accion(
									Acciones.empleadosListado, null);
						}
					}
				}
			}
		});
		borrar.setPreferredSize(new Dimension(130, 30));
		leftTop.add(borrar);

		salir = new JButton("Salir");
		salir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		salir.setPreferredSize(new Dimension(130, 30));
		leftBot.add(salir);

		left.add(leftBot, BorderLayout.SOUTH);
		left.add(leftTop, BorderLayout.CENTER);

		left.setPreferredSize(new Dimension(150, 0));
		this.add(left, BorderLayout.WEST);

		/* #############
		 * PANEL EMPLEADOS
		 * #############
		 */

		JPanel right = new JPanel(new GridLayout());
		right.setBorder(BorderFactory.createTitledBorder("Ventas"));

		tablaEmpleadosModel = new TablaEmpleados();
		tablaEmpleados = new JTable(tablaEmpleadosModel);

		right.add(new JScrollPane(tablaEmpleados));

		this.add(right, BorderLayout.CENTER);

		/* #############
		 * CONFIGURACIONES
		 * #############
		 */

		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		ControladorAplicacion.getInstancia().accion(Acciones.empleadosListado,
				null);
	}

	private Empleado getSelectedEmpleado() {
		return ((tablaEmpleados.getSelectedRow() != -1) ? tablaEmpleadosModel
				.getEmpleado(tablaEmpleados.getSelectedRow()) : null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actualiza(Acciones evento, Retorno datos) {
		if (!transmiteActualiza(evento, datos)) {
			if (evento == Acciones.empleadosListado) {
				if (!datos.tieneErrores()) {
					tablaEmpleadosModel.update((Collection<Empleado>) datos
							.getDatos());
				}
			}else if (evento == Acciones.empleadosBorrar) {
				if (datos.tieneErrores()) {
					StringBuilder errores = new StringBuilder("Se produjeron los siguientes errores: \n");
					for(TError error: datos.getErrores().getLista()){
						errores.append("  - ");
						switch(error.getErrorId()){
						case Errores.empleadoTieneProyectos:{
							errores.append("Pertenece a los siguientes proyectos:\n");
							for(Proyecto p:(Collection<Proyecto>)error.getDatos())
								errores.append("     - Proyecto: " + p.toString()+"\n");
							
						}break;
						default: errores.append(error.getDatos().toString()); break;				
						}
						errores.append("\n");
					}
					JOptionPane.showMessageDialog(this, errores.toString(), "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this,"Empleado borrado correctamente.", "Correcto", JOptionPane.INFORMATION_MESSAGE);
					ControladorAplicacion.getInstancia().accion(Acciones.empleadosListado, null);
				}
			}
		}

	}

	@Override
	public void alVolver(GUI who) {
		ControladorAplicacion.getInstancia().accion(Acciones.empleadosListado,
				null);
	}

}
