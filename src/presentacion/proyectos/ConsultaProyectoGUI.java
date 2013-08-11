package presentacion.proyectos;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import negocio.Retorno;
import negocio.TError;
import negocio.empleados.Empleado;
import negocio.proyectos.Proyecto;
import presentacion.GUI;
import presentacion.controlador.ControladorFrontal;
import presentacion.empleados.tabla.TablaEmpleados;
import presentacion.formulario.CampoFormularioNumeroEntero;
import presentacion.formulario.CampoFormularioTexto;
import presentacion.formulario.Formulario;
import constantes.Acciones;

public class ConsultaProyectoGUI extends GUI {

	private static final long serialVersionUID = -1861676066072740318L;
	private CampoFormularioTexto nombre;
	private CampoFormularioTexto descripcion;
	private Formulario formulario;
	
	private TablaEmpleados tablaEmpleadosModel;
	private JTable tablaEmpleados;
	private Set<Empleado> empleadosProyecto;
	
	CampoFormularioNumeroEntero id;
	Formulario formularioId;
	public ConsultaProyectoGUI(GUI father, Proyecto pry) {
		super(father);
		this.setLayout(new BorderLayout());
		this.setTitle("Consulta proyecto");
		//###############
		// Formulario id
		//###############
		JPanel panelFormuId = new JPanel(new BorderLayout());
		formularioId = new Formulario();
		id = new CampoFormularioNumeroEntero("Id",pry==null?0:pry.getId(),0,null);
		formularioId.addCampo(id);
		panelFormuId.add(formularioId,BorderLayout.CENTER);
		JButton consultar = new JButton("Consultar");
		
		consultar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				formularioId.marcaErrores();
				if(formularioId.esCorrecto()){
					Proyecto pro = new Proyecto();
					pro.setId(id.getResultado());
					ControladorFrontal.getInstancia().accion(Acciones.proyectosConsultar, pro);
				}
			}
		});
		
		panelFormuId.add(consultar,BorderLayout.EAST);
		this.add(panelFormuId,BorderLayout.NORTH);
		//###############
		// Formulario
		//###############
		
		JPanel panelFormuTabla = new JPanel(new BorderLayout());
		
		formulario = new Formulario();
		nombre = new CampoFormularioTexto("Nombre","");
		descripcion = new CampoFormularioTexto("Descripcion","");

		formulario.addCampo(nombre);
		formulario.addCampo(descripcion);
		formulario.setModificable(false);
		
		tablaEmpleadosModel = new TablaEmpleados();
		tablaEmpleados = new JTable(tablaEmpleadosModel);
		
		panelFormuTabla.add(formulario,BorderLayout.NORTH);
		panelFormuTabla.add(new JScrollPane(tablaEmpleados),BorderLayout.CENTER);
		
		this.add(panelFormuTabla, BorderLayout.CENTER);
		
		//###############
		// Botones
		//###############		
		
		JPanel botonera = new JPanel(new GridLayout());
		JButton cancelar;
		cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ConsultaProyectoGUI.this.dispose();
			}
		});
		botonera.add(cancelar);
		this.add(botonera,BorderLayout.SOUTH);
		
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		this.empleadosProyecto = new HashSet<Empleado>();
		if(pry!=null && pry.getId() > 0)
			ControladorFrontal.getInstancia().accion(Acciones.proyectosConsultar, pry);
	}

	@Override
	public void actualiza(Acciones evento, Retorno datos) {
		if(evento == Acciones.proyectosConsultar)
			if(datos.tieneErrores()){
				id.setIndicador(false);
				nombre.setValue("");
				descripcion.setValue("");
				empleadosProyecto.clear();
				tablaEmpleadosModel.update(empleadosProyecto);
				
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
				Proyecto pry = (Proyecto)datos.getDatos();
				
				id.setIndicador(true);
				nombre.setValue(pry.getNombre());
				descripcion.setValue(pry.getDescripcion());
				
				empleadosProyecto.clear();
				for(Empleado e:pry.getEmpleado()){
					empleadosProyecto.add(e);
				}
				tablaEmpleadosModel.update(empleadosProyecto);
				
			}
	}

	@Override
	public void alVolver(GUI who) {}
}
