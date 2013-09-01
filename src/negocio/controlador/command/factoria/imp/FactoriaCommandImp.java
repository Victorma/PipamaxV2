package negocio.controlador.command.factoria.imp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import negocio.controlador.command.Command;
import negocio.controlador.command.factoria.FactoriaCommand;

public class FactoriaCommandImp extends FactoriaCommand {

	private final String commandFileName = "command.config";
	
	private HashMap<String,Class<? extends Command>> classMap = null;
	
	private Command parseCommand(String evento){
		
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
	public Command createCommand(String evento) {
		return parseCommand(evento);
	}

}
