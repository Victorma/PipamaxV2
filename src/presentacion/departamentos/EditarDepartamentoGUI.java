package presentacion.departamentos;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import constantes.Acciones;
import constantes.Errores;
import negocio.Retorno;
import negocio.TError;
import negocio.departamentos.Departamento;
import presentacion.GUI;
import presentacion.controlador.ControladorFrontal;
import presentacion.formulario.CampoFormularioNumeroEntero;
import presentacion.formulario.CampoFormularioTexto;
import presentacion.formulario.Formulario;

public class EditarDepartamentoGUI extends GUI {

	private static final long serialVersionUID = 2484398935313130207L;
	private CampoFormularioTexto nombre;
	private CampoFormularioNumeroEntero codigo;
	private CampoFormularioTexto sueldo;
	private Formulario formulario;

	private CampoFormularioNumeroEntero id;
	private Formulario formularioId;
	private Departamento departamento;

	public EditarDepartamentoGUI(GUI father, Departamento dep) {
		super(father);
		this.setLayout(new BorderLayout());
		this.setTitle("Editar departamento");
		//###############
		// Formulario id
		//###############
		JPanel panelFormuId = new JPanel(new BorderLayout());
		formularioId = new Formulario();
		id = new CampoFormularioNumeroEntero("Id", (dep != null) ? dep.getId()
				: 0, 0, null);
		formularioId.addCampo(id);
		panelFormuId.add(formularioId, BorderLayout.CENTER);
		JButton consultar = new JButton("Consultar");

		consultar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				formularioId.marcaErrores();
				if (formularioId.esCorrecto()) {
					Departamento dep = new Departamento();
					dep.setId(id.getResultado());
					ControladorFrontal.getInstancia().accion(
							Acciones.departamentosConsultar, dep);
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
		codigo = new CampoFormularioNumeroEntero("Codigo", 0, 0, 999);
		sueldo = new CampoFormularioTexto("Sueldo", "");

		formulario.addCampo(nombre);
		formulario.addCampo(codigo);
		formulario.addCampo(sueldo);
		
		this.add(formulario, BorderLayout.CENTER);

		//###############
		// Botones
		//###############		

		JPanel botonera = new JPanel(new GridLayout());
		JButton cancelar, enviar;
		enviar = new JButton("Enviar");
		enviar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (departamento == null) {
					JOptionPane
							.showMessageDialog(EditarDepartamentoGUI.this,
									"El departamento no es válido, para poder enviar, realice una consulta válida.");
				} else {
					formulario.marcaErrores();
					try{
						Double s = Double.parseDouble(sueldo.getResultado());
						if (formulario.esCorrecto()) {
							departamento.setNombre(nombre.getResultado());
							departamento.setCodigo(codigo.getResultado());
							departamento.setSueldo(s);
							ControladorFrontal.getInstancia().accion(
									Acciones.departamentosEditar, departamento);
						}
					}catch (IllegalArgumentException ile){
						sueldo.setIndicador(false);
					}
				}
			}
		});
		botonera.add(enviar);

		cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EditarDepartamentoGUI.this.dispose();
			}
		});
		botonera.add(cancelar);
		this.add(botonera, BorderLayout.SOUTH);

		this.setSize(400, 150);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		if (dep != null && dep.getId() > 0)
			ControladorFrontal.getInstancia().accion(
					Acciones.departamentosConsultar, dep);
	}

	@Override
	public void actualiza(Integer evento, Retorno datos) {
		if (evento == Acciones.departamentosConsultar) {
			if (datos.tieneErrores()) {
				id.setIndicador(false);
				departamento = null;
				nombre.setValue("");
				codigo.setValue("");
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
				departamento = (Departamento) datos.getDatos();
				nombre.setValue(departamento.getNombre());
				codigo.setValue(departamento.getCodigo());
				sueldo.setValue(departamento.getSueldo().toString());
			}
		} else if (evento == Acciones.departamentosEditar)
			if (datos.tieneErrores()) {
				StringBuilder errores = new StringBuilder(
						"Se produjeron los siguientes errores: \n");
				for (TError error : datos.getErrores().getLista()) {
					errores.append("  - ");
					switch (error.getErrorId()) {
					case Errores.empleadoDNIrepetido: {
						codigo.setIndicador(false);
						errores.append("Codigo repetido, encontrado en el departamento: "
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
						"Departamento editado correctamente", "Correcto",
						JOptionPane.INFORMATION_MESSAGE);
				this.dispose();
			}
	}

	@Override
	public void alVolver(GUI who) {
	}

}
