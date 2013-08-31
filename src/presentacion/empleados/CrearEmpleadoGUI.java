package presentacion.empleados;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import constantes.Acciones;
import constantes.Errores;
import constantes.Constantes.tTurno;
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

public class CrearEmpleadoGUI extends GUI {

	private static final long serialVersionUID = 7849067484128003361L;
	private CampoFormularioTexto nombre;
	private CampoFormularioTexto apellido1;
	private CampoFormularioTexto apellido2;
	private CampoFormularioDNI dni;
	private CampoFormularioTexto ciudad;
	private CampoFormularioTexto direccion;
	private CampoFormularioNumeroEntero cp;
	private CampoFormularioSelector<String, String> tiempo;
	//
	private CampoFormularioSelector<String, String> esFijo;
	private CampoFormularioSelector<String, tTurno> turno;
	//
	private CampoFormularioSelector<String, Departamento> departamento;
	private CampoFormularioNumeroEntero telefono;
	private Formulario formulario;
	private String ninguno = " -- Ninguno -- ";

	private Map<String, Departamento> camposDep;

	public CrearEmpleadoGUI(GUI father) {
		super(father);
		this.setLayout(new BorderLayout());
		this.setTitle("Alta empleado");
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
		String completo = "Completo";
		campos.put(completo, "completo");
		campos.put("Parcial", "parcial");
		tiempo = new CampoFormularioSelector<String, String>("Tiempo", campos);

		Map<String, tTurno> camposEmpParcial = new TreeMap<String, tTurno>();
		camposEmpParcial.put("Mañana", tTurno.mañana);
		camposEmpParcial.put("Tarde", tTurno.tarde);
		turno = new CampoFormularioSelector<String, tTurno>("Turno", camposEmpParcial);
		
		Map<String, String> camposEmpCompleto = new TreeMap<String, String>();
		camposEmpCompleto.put("Sí", "sí");
		camposEmpCompleto.put("No", "no");
		esFijo = new CampoFormularioSelector<String, String>("¿Es fijo?", camposEmpCompleto);
		
		
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
		formulario.addCampo(turno);
		formulario.addCampo(esFijo);
		formulario.addCampo(departamento);
		formulario.addCampo(telefono);
		
		tiempo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				turno.setModificable(tiempo.getResultado().equalsIgnoreCase("parcial"));
				esFijo.setModificable(tiempo.getResultado().equalsIgnoreCase("completo"));
			}
		});
		
		tiempo.setValue(completo);

		this.add(formulario, BorderLayout.CENTER);

		//###############
		// Botones
		//###############		

		JPanel botonera = new JPanel(new GridLayout(1, 2));

		JButton enviar, cancelar;
		enviar = new JButton("Enviar");
		enviar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				formulario.marcaErrores();
				if (formulario.esCorrecto()) {
					Empleado emp = (tiempo.getResultado().equalsIgnoreCase("completo")?new EmpleadoCompleto():new EmpleadoParcial());
					emp.setNombre(nombre.getResultado());
					emp.setApellido1(apellido1.getResultado());
					emp.setApellido2(apellido2.getResultado());
					emp.setDni(dni.getResultado());
					emp.setCiudad(ciudad.getResultado());
					emp.setDireccion(direccion.getResultado());
					emp.setCp(cp.getResultado());
					emp.setDepartamento(departamento.getResultado());
					emp.setTelefono(telefono.getResultado());
					emp.setActivo(1);
					
					if(tiempo.getResultado().equalsIgnoreCase("parcial")){
						((EmpleadoParcial) emp).setTurno(turno.getResultado());
					}else{
						((EmpleadoCompleto) emp).setEsFijo(esFijo.getResultado().equalsIgnoreCase("sí"));
					}
					
					ControladorAplicacion.getInstancia().accion(
							Acciones.empleadosCrear, emp);
				}
			}
		});
		botonera.add(enviar);

		cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CrearEmpleadoGUI.this.dispose();
			}
		});
		botonera.add(cancelar);
		this.add(botonera, BorderLayout.SOUTH);

		ControladorAplicacion.getInstancia().accion(Acciones.departamentosListado,
				null);

		this.setSize(400, 350);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void actualiza(Acciones evento, Retorno datos) {
		if (evento == Acciones.empleadosCrear)
			if (datos.tieneErrores()) {
				StringBuilder errores = new StringBuilder(
						"Se produjeron los siguientes errores: \n");
				for (TError error : datos.getErrores().getLista()) {
					errores.append("  - ");
					switch (error.getErrorId()) {
					case Errores.empleadoDNIrepetido: {
						dni.setIndicador(false);
						errores.append("Dni repetido, encontrado en el empleado: "
								+ error.getDatos());
					}
						break;
					default:
						errores.append(error.getDatos().toString());
						break;
					}
					errores.append("\n");
				}
				JOptionPane.showMessageDialog(this, errores.toString(),
						"Error", JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this,
						"Empleado dado de alta con el id " + datos.getDatos(),
						"Correcto", JOptionPane.INFORMATION_MESSAGE);
				this.dispose();
			}
		if (evento == Acciones.departamentosListado)
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
				for (Departamento dep : (Collection<Departamento>) datos
						.getDatos())
					camposDep.put(dep.toString(), dep);
				departamento.setMap(camposDep);
			}
	}

	@Override
	public void alVolver(GUI who) {
		// TODO Apéndice de método generado automáticamente

	}

}
