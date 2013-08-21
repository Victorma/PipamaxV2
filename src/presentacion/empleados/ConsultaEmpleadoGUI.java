package presentacion.empleados;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import negocio.Retorno;
import negocio.TError;
import negocio.departamentos.Departamento;
import negocio.empleados.Empleado;
import negocio.empleados.EmpleadoCompleto;
import negocio.empleados.EmpleadoParcial;
import presentacion.GUI;
import negocio.controlador.ControladorAplicacion;
import presentacion.formulario.CampoFormularioDNI;
import presentacion.formulario.CampoFormularioNumeroEntero;
import presentacion.formulario.CampoFormularioSelector;
import presentacion.formulario.CampoFormularioTexto;
import presentacion.formulario.Formulario;
import constantes.Acciones;

public class ConsultaEmpleadoGUI extends GUI {

	private static final long serialVersionUID = -1861676066072740318L;
	private CampoFormularioTexto nombre;
	private CampoFormularioTexto apellido1;
	private CampoFormularioTexto apellido2;
	private CampoFormularioDNI dni;
	private CampoFormularioTexto ciudad;
	private CampoFormularioTexto direccion;
	private CampoFormularioNumeroEntero cp;
	private CampoFormularioSelector<String, String> tiempo;
	private CampoFormularioSelector<String, Departamento> departamento;
	private CampoFormularioNumeroEntero telefono;
	private Formulario formulario;
	private String ninguno = " -- Ninguno -- ";

	private Map<String, Departamento> camposDep;

	private JButton calcularSalario;
	
	CampoFormularioNumeroEntero id;
	Formulario formularioId;
	Empleado empCons;

	public ConsultaEmpleadoGUI(GUI father, Empleado emp) {
		super(father);
		this.setLayout(new BorderLayout());
		this.setTitle("Consulta empleado");
		//###############
		// Formulario id
		//###############
		JPanel panelFormuId = new JPanel(new BorderLayout());
		formularioId = new Formulario();
		id = new CampoFormularioNumeroEntero("Id", emp==null?0:emp.getId(), 0, null);
		formularioId.addCampo(id);
		panelFormuId.add(formularioId, BorderLayout.CENTER);
		JButton consultar = new JButton("Consultar");

		consultar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				formularioId.marcaErrores();
				if (formularioId.esCorrecto()) {
					Empleado emp = (tiempo.getResultado().equalsIgnoreCase("completo")?new EmpleadoCompleto():new EmpleadoParcial());
					emp.setId(id.getResultado());
					ControladorAplicacion.getInstancia().accion(Acciones.empleadosConsultar, emp);
				}
			}
		});

		panelFormuId.add(consultar, BorderLayout.EAST);
		this.add(panelFormuId, BorderLayout.NORTH);
		//###############
		// Formulario
		//###############

		formulario = new Formulario();
		nombre = new CampoFormularioTexto("Nombre", "");
		apellido1 = new CampoFormularioTexto("Primer Apellido", "");
		apellido2 = new CampoFormularioTexto("Segundo Apellido", "");
		dni = new CampoFormularioDNI("Dni", "");
		ciudad = new CampoFormularioTexto("Ciudad", "");
		direccion = new CampoFormularioTexto("Direccion", "");
		cp = new CampoFormularioNumeroEntero("CP", 0, 10000, 99999);

		Map<String, String> campos = new TreeMap<String, String>();
		campos.put("Completo", "completo");
		campos.put("Parcial", "parcial");
		tiempo = new CampoFormularioSelector<String, String>("Tiempo", campos);

		camposDep = new TreeMap<String, Departamento>();
		camposDep.put(ninguno, null);
		departamento = new CampoFormularioSelector<String, Departamento>(
				"Departamento", camposDep);

		telefono = new CampoFormularioNumeroEntero("Telefono", 0, 100000000,
				999999999);

		formulario.addCampo(nombre);
		formulario.addCampo(apellido1);
		formulario.addCampo(apellido2);
		formulario.addCampo(dni);
		formulario.addCampo(ciudad);
		formulario.addCampo(direccion);
		formulario.addCampo(cp);
		formulario.addCampo(tiempo);
		formulario.addCampo(departamento);
		formulario.addCampo(telefono);
		formulario.setModificable(false);

		this.add(formulario, BorderLayout.CENTER);

		//###############
		// Botones
		//###############		

		JPanel botonera = new JPanel(new GridLayout(1,2));
		JButton cancelar;
		calcularSalario = new JButton("Calcula salario");
		calcularSalario.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ControladorAplicacion.getInstancia().accion(Acciones.empleadosSalario, empCons);
			}
		});
		
		cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ConsultaEmpleadoGUI.this.dispose();
			}
		});
		if(emp ==null)calcularSalario.setEnabled(false);
		botonera.add(calcularSalario);
		botonera.add(cancelar);
		this.add(botonera, BorderLayout.SOUTH);

		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		if (emp != null && emp.getId() > 0)
			ControladorAplicacion.getInstancia().accion(
					Acciones.empleadosConsultar, emp);
	}

	@Override
	public void actualiza(Acciones evento, Retorno datos) {
		if (evento == Acciones.empleadosConsultar)
			if (datos.tieneErrores()) {
				empCons = null;
				calcularSalario.setEnabled(false);
				id.setIndicador(false);
				nombre.setValue("");
				apellido1.setValue("");
				apellido2.setValue("");
				dni.setValue("");
				ciudad.setValue("");
				direccion.setValue("");
				cp.setValue("");
				telefono.setValue("");
				StringBuilder errores = new StringBuilder(
						"Se produjeron los siguientes errores: \n");
				for (TError error : datos.getErrores().getLista()) {
					errores.append("  - ");
					switch (error.getErrorId()) {
					default:
						errores.append(error.getDatos().toString());
						break;
					}
					errores.append("\n");
				}
				JOptionPane.showMessageDialog(this, errores.toString(),
						"Error", JOptionPane.ERROR_MESSAGE);
			} else {
				calcularSalario.setEnabled(true);
				empCons = (Empleado) datos.getDatos();
				nombre.setValue(empCons.getNombre());
				apellido1.setValue(empCons.getApellido1());
				apellido2.setValue(empCons.getApellido2());
				dni.setValue(empCons.getDni());
				tiempo.setValue(empCons.getClass()==EmpleadoCompleto.class?"Completo":"Parcial");
				ciudad.setValue(empCons.getCiudad());
				direccion.setValue(empCons.getDireccion());
				cp.setValue(empCons.getCp());

				camposDep.clear();
				if (empCons.getDepartamento() != null) {
					camposDep.put(empCons.getDepartamento().toString(),	empCons.getDepartamento());
					departamento.setMap(camposDep);
					departamento.setValue(empCons.getDepartamento().toString());
				} else
					departamento.setValue(ninguno);
				telefono.setValue(empCons.getTelefono());
			}
		else if (evento == Acciones.empleadosSalario){
			if (datos.tieneErrores()) {
				StringBuilder errores = new StringBuilder(
						"Se produjeron los siguientes errores: \n");
				for (TError error : datos.getErrores().getLista()) {
					errores.append("  - ");
					switch (error.getErrorId()) {
					default:
						errores.append(error.getDatos().toString());
						break;
					}
					errores.append("\n");
				}
				JOptionPane.showMessageDialog(this, errores.toString(),
						"Error", JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this,"Salario: " + datos.getDatos().toString()+ ".");
			}
		}
	}

	@Override
	public void alVolver(GUI who) {
	}
}
