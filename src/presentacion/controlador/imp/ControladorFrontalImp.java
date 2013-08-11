package presentacion.controlador.imp;

import presentacion.controlador.*;
import presentacion.dispacher.DispatcherView;
import constantes.Acciones;
import negocio.Retorno;
import negocio.controlador.*;
import negocio.controlador.clientes.*;
import negocio.controlador.departamentos.CommandDepartamentoBorrar;
import negocio.controlador.departamentos.CommandDepartamentoConsultar;
import negocio.controlador.departamentos.CommandDepartamentoConsultarLista;
import negocio.controlador.departamentos.CommandDepartamentoCrear;
import negocio.controlador.departamentos.CommandDepartamentoEditar;
import negocio.controlador.departamentos.CommandDepartamentoSalario;
import negocio.controlador.empleados.CommandEmpleadoBorrar;
import negocio.controlador.empleados.CommandEmpleadoCalcularSueldo;
import negocio.controlador.empleados.CommandEmpleadoConsultar;
import negocio.controlador.empleados.CommandEmpleadoConsultarLista;
import negocio.controlador.empleados.CommandEmpleadoCrear;
import negocio.controlador.empleados.CommandEmpleadoEditar;
import negocio.controlador.marcas.*;
import negocio.controlador.pedidos.*;
import negocio.controlador.productos.*;
import negocio.controlador.proveedores.*;
import negocio.controlador.proyectos.CommandProyectoBorrar;
import negocio.controlador.proyectos.CommandProyectoConsultar;
import negocio.controlador.proyectos.CommandProyectoConsultarLista;
import negocio.controlador.proyectos.CommandProyectoCrear;
import negocio.controlador.proyectos.CommandProyectoEditar;
import negocio.controlador.ventas.*;

public class ControladorFrontalImp extends ControladorFrontal {
	// TODO Actualizar el método actualiza.
	@Override
	public void accion(Acciones evento, Object datos) {
		ControladorAplicacion.getInstancia().accion(evento, datos);
	}

}
