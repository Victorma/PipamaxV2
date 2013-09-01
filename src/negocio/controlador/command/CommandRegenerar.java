package negocio.controlador.command;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import negocio.Retorno;

public class CommandRegenerar implements Command {

	@Override
	public Retorno execute() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("Regenerar");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.getTransaction().commit();
		emf.close();		
		
		return new Retorno();
	}

	@Override
	public void setContext(Object datos) {
	}

}
