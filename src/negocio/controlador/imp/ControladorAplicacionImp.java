package negocio.controlador.imp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import presentacion.dispacher.DispatcherView;
import constantes.Acciones;
import negocio.Retorno;
import negocio.controlador.Command;
import negocio.controlador.ControladorAplicacion;
import negocio.controlador.clientes.CommandClienteBorrar;
import negocio.controlador.clientes.CommandClienteConsultar;
import negocio.controlador.clientes.CommandClienteCrear;
import negocio.controlador.clientes.CommandClienteEditar;
import negocio.controlador.clientes.CommandClienteListado;
import negocio.controlador.clientes.CommandClienteReactivar;
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
import negocio.controlador.marcas.CommandMarcaBorrar;
import negocio.controlador.marcas.CommandMarcaConsultar;
import negocio.controlador.marcas.CommandMarcaCrear;
import negocio.controlador.marcas.CommandMarcaEditar;
import negocio.controlador.marcas.CommandMarcaListado;
import negocio.controlador.marcas.CommandMarcaListadoProductos;
import negocio.controlador.marcas.CommandMarcaReactivar;
import negocio.controlador.pedidos.CommandPedidoAnular;
import negocio.controlador.pedidos.CommandPedidoBorrar;
import negocio.controlador.pedidos.CommandPedidoCompletar;
import negocio.controlador.pedidos.CommandPedidoConsProdProv;
import negocio.controlador.pedidos.CommandPedidoConsultar;
import negocio.controlador.pedidos.CommandPedidoCrear;
import negocio.controlador.pedidos.CommandPedidoLista;
import negocio.controlador.productos.CommandProductoBorrar;
import negocio.controlador.productos.CommandProductoBorrarSuministro;
import negocio.controlador.productos.CommandProductoConsultar;
import negocio.controlador.productos.CommandProductoCrear;
import negocio.controlador.productos.CommandProductoCrearSuministro;
import negocio.controlador.productos.CommandProductoEditar;
import negocio.controlador.productos.CommandProductoListaSuministros;
import negocio.controlador.productos.CommandProductoListado;
import negocio.controlador.proveedores.CommandProveedorBorrar;
import negocio.controlador.proveedores.CommandProveedorConsultar;
import negocio.controlador.proveedores.CommandProveedorCrear;
import negocio.controlador.proveedores.CommandProveedorEditar;
import negocio.controlador.proveedores.CommandProveedorListado;
import negocio.controlador.proveedores.CommandProveedorReactivar;
import negocio.controlador.proyectos.CommandProyectoBorrar;
import negocio.controlador.proyectos.CommandProyectoConsultar;
import negocio.controlador.proyectos.CommandProyectoConsultarLista;
import negocio.controlador.proyectos.CommandProyectoCrear;
import negocio.controlador.proyectos.CommandProyectoEditar;
import negocio.controlador.ventas.CommandVentaBorrar;
import negocio.controlador.ventas.CommandVentaConsultar;
import negocio.controlador.ventas.CommandVentaCrear;
import negocio.controlador.ventas.CommandVentaDevolucion;
import negocio.controlador.ventas.CommandVentaListado;

public class ControladorAplicacionImp extends ControladorAplicacion {

	private final String commandFileName = "command.config";
	
	@Override
	public Retorno ejecuta(Command c) {
		return c.execute();
	}
	
	private HashMap<String,Class<? extends Command>> classMap = null;
	
	private Command parseCommand(Acciones evento){
		
		Command com = null;
		
		if(classMap == null){
			try{
				HashMap<String,Class<? extends Command>> temporalClassMap = new HashMap<String,Class<? extends Command>>();
				Scanner fileReader = new Scanner(new File(commandFileName));
				String line;
				while(fileReader.hasNext()){
					line = fileReader.nextLine();
					line = line.trim();
					if(line.charAt(0) != '#' && line.length() != 0){
						String [] tokens = line.split(" "); 
						temporalClassMap.put(tokens[0], (Class<? extends Command>) Class.forName(tokens[tokens.length-1]));
					}
				}
				classMap = temporalClassMap;
			}catch(FileNotFoundException fnfe){
				System.err.println("No encontrado "+commandFileName+"!!!");
			} catch (ClassNotFoundException e) {
				System.err.println("Clase no encontrada: " + e.getMessage());
			}
		}
		
		if(classMap != null){
			try {
				com = classMap.get(evento.toString()).newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		
		return com;
	}

	@Override
	public void accion(Acciones evento, Object datos) {
		Retorno retorno = new Retorno();
		Command comando = parseCommand(evento);
		comando.setContext(datos);
		retorno = ejecuta(comando);
		DispatcherView.getInstancia().actualiza(evento, retorno);
	}

}
