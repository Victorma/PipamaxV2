package presentacion.proyectos;

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
import negocio.Retorno;
import negocio.TError;
import negocio.proyectos.Proyecto;
import presentacion.GUI;
import presentacion.controlador.ControladorFrontal;
import presentacion.proyectos.tabla.TablaProyectos;

public class ProyectosGUI extends GUI {

	private static final long serialVersionUID = -1124401987989317611L;
	private TablaProyectos tablaProyectosModel;
	private JTable tablaProyectos;	
	
	public ProyectosGUI(GUI father) {
		super(father);
		this.setLayout(new BorderLayout());
		this.setTitle("PIPAMAX - PROYECTOS");
		
		JPanel top = new JPanel(new FlowLayout());
		top.setBorder(BorderFactory.createTitledBorder(BorderFactory.createDashedBorder(Color.MAGENTA,5,4),"Estadisticas"));
		JLabel textoTop = new JLabel("Proyectos");
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
		JPanel leftTop = new JPanel(new FlowLayout()), leftBot = new JPanel(new FlowLayout());
		JButton alta, consultar, editar, borrar, salir;
		
		alta = new JButton("Alta");
		alta.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				new CrearProyectoGUI(ProyectosGUI.this);
			}
		});
		alta.setPreferredSize(new Dimension(130,30));
		leftTop.add(alta);
		
		consultar = new JButton("Consultar");
		consultar.setPreferredSize(new Dimension(130,30));
		consultar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Proyecto proyecto = getSelectedProyecto(); 
				if(proyecto != null);
					new ConsultaProyectoGUI(ProyectosGUI.this, proyecto);

			}
		});
		leftTop.add(consultar);
		
		editar = new JButton("Editar");
		editar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Proyecto proyecto = getSelectedProyecto(); 
				if(proyecto != null);
					new EditarProyectoGUI(ProyectosGUI.this, proyecto);

			}
		});
		editar.setPreferredSize(new Dimension(130,30));
		leftTop.add(editar);
		
		borrar = new JButton("Borrar");
		borrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				Proyecto proyecto = getSelectedProyecto(); 
				if(proyecto != null){
					GUI consultar = new ConsultaProyectoGUI(ProyectosGUI.this, proyecto);
					if(getChild() != null){
						int respuesta = JOptionPane.showConfirmDialog(consultar, "¿Desea borrar este proyecto?", "Borrar", JOptionPane.YES_NO_OPTION);
						consultar.dispose();
						if(respuesta == JOptionPane.YES_OPTION){
							ControladorFrontal.getInstancia().accion(Acciones.proyectosBorrar, proyecto);
							ControladorFrontal.getInstancia().accion(Acciones.empleadosListado, null);
						}
					}
				}
			}
		});
		borrar.setPreferredSize(new Dimension(130,30));
		leftTop.add(borrar);

		salir = new JButton("Salir");
		salir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});	
		salir.setPreferredSize(new Dimension(130,30));
		leftBot.add(salir);
		
		left.add(leftBot, BorderLayout.SOUTH);
		left.add(leftTop, BorderLayout.CENTER);

		left.setPreferredSize(new Dimension(150,0));
		this.add(left, BorderLayout.WEST);
		
		/* #############
		 * PANEL EMPLEADOS
		 * #############
		 */		
		
		JPanel right = new JPanel(new GridLayout());
		right.setBorder(BorderFactory.createTitledBorder("Ventas"));
		
		tablaProyectosModel = new TablaProyectos();
		tablaProyectos = new JTable(tablaProyectosModel);
		
		right.add(new JScrollPane(tablaProyectos));
		
		this.add(right, BorderLayout.CENTER);
				
		/* #############
		 * CONFIGURACIONES
		 * #############
		 */		
		
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		ControladorFrontal.getInstancia().accion(Acciones.proyectosListado, null);
	}
	
	private Proyecto getSelectedProyecto(){
		return( (tablaProyectos.getSelectedRow() != -1)? tablaProyectosModel.getProyecto(tablaProyectos.getSelectedRow()):null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void actualiza(Integer evento, Retorno datos) {
		if(!transmiteActualiza(evento, datos)){
			if(evento == Acciones.proyectosListado){
				if(datos.tieneErrores()){
					
				}else{
					tablaProyectosModel.update((Collection<Proyecto>)datos.getDatos());
				}
			}else if(evento == Acciones.proyectosBorrar){
				if(datos.tieneErrores()){
					StringBuilder errores = new StringBuilder("Se produjeron los siguientes errores: \n");
					for(TError error: datos.getErrores().getLista()){
						errores.append("  - ");
						switch(error.getErrorId()){
						default: errores.append(error.getDatos().toString()); break;				
						}
						errores.append("\n");
					}
					JOptionPane.showMessageDialog(this, errores.toString(), "Error", JOptionPane.ERROR_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(this, "Proyecto borrado con éxito.");
					ControladorFrontal.getInstancia().accion(Acciones.proyectosListado, null);
				}
			}			
		}
		
	}

	@Override
	public void alVolver(GUI who) {
		ControladorFrontal.getInstancia().accion(Acciones.proyectosListado, null);
	}

}
