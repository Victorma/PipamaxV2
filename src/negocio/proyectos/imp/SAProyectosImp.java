/**
 * 
 */
package negocio.proyectos.imp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import constantes.Errores;
import negocio.Retorno;
import negocio.empleados.Empleado;
import negocio.proyectos.Proyecto;
import negocio.proyectos.SAProyectos;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Victorma
 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
public class SAProyectosImp implements SAProyectos {
	private EntityManagerFactory emf;
	private EntityManager em;
	
	/** 
	 * (sin Javadoc)
	 * @see SAProyectos#altaProyecto()
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Retorno altaProyecto(Proyecto pry) {
		Retorno retorno = new Retorno();
		boolean rollback = false;
		try {
			emf = Persistence.createEntityManagerFactory("Implementacion Pipamax JPA");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			Set<Empleado> set = pry.getEmpleado();
			pry.setEmpleado(null);
			
			em.persist(pry);
			em.flush();
			
			Empleado aux;
			for(Empleado e: set)
			{
				TypedQuery<Empleado> qEmp = em.createNamedQuery(
						"negocio.empleados.Empleado.findByid", Empleado.class);
				
				qEmp.setParameter("id", e.getId());
				qEmp.setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT);
				
				if(qEmp.getResultList().size() != 1)
					aux = null;
				else
					aux = qEmp.getResultList().get(0);
				
				if(aux==null){
					retorno.addError(Errores.empleadoNoEncontrado, e.getDni());
					rollback = true;
				} else {
					pry.getEmpleado().add(aux);
					aux.getProyecto().add(pry);
				}
			}
			
			if(!rollback){
				em.getTransaction().commit();
				retorno.setDatos(pry.getId());
			}else{
				retorno.addError(Errores.proyectoNoCreado, pry.getId());
				em.getTransaction().rollback();
			}
			
		}catch (OptimisticLockException ole){
			retorno.addError(Errores.errorDeAccesoConcurrente, ole.getMessage());
			em.getTransaction().rollback();
		}catch (PersistenceException pe) {
			retorno.addError(Errores.proyectoNoCreado, pry.getId());
			em.getTransaction().rollback();
		}catch (IllegalStateException e) {
			retorno.addError(Errores.proyectoNoCreado, pry.getId());
			em.getTransaction().rollback();
		}finally{
			em.close();
			emf.close();
		}
		return retorno;
	}

	/** 
	 * (sin Javadoc)
	 * @see SAProyectos#bajaProyecto()
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Retorno bajaProyecto(Proyecto pry) {
		Retorno retorno = new Retorno();
		try {
			emf = Persistence.createEntityManagerFactory("Implementacion Pipamax JPA");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			TypedQuery<Proyecto> q = em.createNamedQuery(
					"negocio.proyectos.Proyecto.findByid", Proyecto.class);
			q.setParameter("id", pry.getId());
			q.setLockMode(LockModeType.OPTIMISTIC);
			
			Proyecto aux = null;
			if(q.getResultList().size() == 1)
				aux = q.getResultList().get(0);
			
			if(aux == null){
				retorno.addError(Errores.proyectoNoEncontrado, pry.getId());
			}else{
				for(Empleado e: aux.getEmpleado()){
					em.lock(e, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
					e.getProyecto().remove(aux);	
				}
				//TypedQuery<Proyecto> qRP = em.createNamedQuery("negocio.proyectos.Proyecto.removeProyecto", Proyecto.class);
				
				//qRP.setParameter("id", aux.getId());
				//qRP.setLockMode(LockModeType.OPTIMISTIC);
				//if (qRP.executeUpdate() == 1) 
						
				aux.setActivo(0);
				aux.getEmpleado().clear();
				em.getTransaction().commit();
				//else{
				//	retorno.addError(Errores.proyectoNoBorrado, pry.getId());
				//	em.getTransaction().rollback();
				//}
			}
		
		}catch (OptimisticLockException ole){
			retorno.addError(Errores.errorDeAccesoConcurrente, ole.getMessage());
			em.getTransaction().rollback();
		}catch (PersistenceException pe) {
			retorno.addError(Errores.proyectoNoBorrado, pry.getId());
			em.getTransaction().rollback();
		}catch (IllegalStateException e) {
			retorno.addError(Errores.proyectoNoBorrado, pry.getId());
			em.getTransaction().rollback();
		}finally{
			em.close();
			emf.close();
		}
		return retorno;
	}

	/** 
	 * (sin Javadoc)
	 * @see SAProyectos#consultaProyecto()
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Retorno consultaProyecto(Proyecto pry) {
		Retorno retorno = new Retorno();
		try {
			emf = Persistence.createEntityManagerFactory("Implementacion Pipamax JPA");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			TypedQuery<Proyecto> q = em.createNamedQuery(
					"negocio.proyectos.Proyecto.findByid", Proyecto.class);
			q.setParameter("id", pry.getId());
			q.setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			
			Proyecto aux = null;
			if(q.getResultList().size() == 1)
				aux = q.getResultList().get(0);
			
			if(aux == null){
				retorno.addError(Errores.proyectoNoEncontrado, pry.getId());
			}else{
				em.detach(aux);
				em.getTransaction().commit();
				retorno.setDatos(aux);
			}
		}catch (OptimisticLockException ole){
			retorno.addError(Errores.errorDeAccesoConcurrente, ole.getMessage());
			em.getTransaction().rollback();
		}catch (PersistenceException pe) {
			retorno.addError(Errores.proyectoNoEncontrado, pry.getId());
			em.getTransaction().rollback();
		}catch (IllegalStateException e) {
			retorno.addError(Errores.proyectoNoEncontrado, pry.getId());
			em.getTransaction().rollback();
		}finally{
			em.close();
			emf.close();
		}
		return retorno;
	}

	/** 
	 * (sin Javadoc)
	 * @see SAProyectos#editaProyecto()
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Retorno editaProyecto(Proyecto pry) {
		Retorno retorno = new Retorno();
		boolean rollback = false;
		try {
			emf = Persistence.createEntityManagerFactory("Implementacion Pipamax JPA");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			Set<Empleado> set = pry.getEmpleado();
			pry.setEmpleado(null);
			
			TypedQuery<Proyecto> q = em.createNamedQuery(
					"negocio.proyectos.Proyecto.findByid", Proyecto.class);
			q.setParameter("id", pry.getId());
			q.setLockMode(LockModeType.OPTIMISTIC);
			
			Proyecto aux = null;
			if(q.getResultList().size() == 1)
				aux = q.getResultList().get(0);
			
			if(aux == null){
				retorno.addError(Errores.proyectoNoEncontrado, pry.getId());
			}else{		
				Set<Empleado> setAntiguo = new HashSet<Empleado>();
				for(Empleado e:aux.getEmpleado())
					setAntiguo.add(e);
				
				Proyecto proyecto = em.merge(pry);
				
				Empleado eaux;
				for(Empleado e: set)
				{
					
					TypedQuery<Empleado> qE = em.createNamedQuery(
							"negocio.empleados.Empleado.findByid", Empleado.class);
					qE.setParameter("id", e.getId());
					qE.setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT);
					
					eaux = null;
					if(qE.getResultList().size() == 1)
						eaux = qE.getResultList().get(0);
					
					
					if(aux==null){
						retorno.addError(Errores.empleadoNoEncontrado, e.getDni());
						rollback = true;
					} else {
						if(setAntiguo.contains(eaux))setAntiguo.remove(eaux);
						//Toco ambas partes de la relacion "simbolicamente" porque en realidad, solo afecta lo que se toque en empleados, que es quien gestiona la relacion.
						proyecto.getEmpleado().add(eaux);
						eaux.getProyecto().add(proyecto);//Aqui es donde realmente vinculamos los nuevos
					}
				}
				
				for(Empleado e:setAntiguo){
					proyecto.getEmpleado().remove(e); 
					e.getProyecto().remove(proyecto);// Aqui es donde realmente desvinculamos
				}	
				
				if(!rollback){
					em.getTransaction().commit();
				}else{
					retorno.addError(Errores.proyectoNoModificado, pry.getId());
					em.getTransaction().rollback();
				}
			}
		}catch (OptimisticLockException ole){
			retorno.addError(Errores.errorDeAccesoConcurrente, ole.getMessage());
			em.getTransaction().rollback();
		}catch (PersistenceException pe) {
			retorno.addError(Errores.proyectoNoModificado, pry.getId());
			em.getTransaction().rollback();
		}catch (IllegalStateException e) {
			retorno.addError(Errores.proyectoNoModificado, pry.getId());
			em.getTransaction().rollback();
		}finally{
			em.close();
			emf.close();
		}
		return retorno;
	}

	@Override
	public Retorno listaProyectos() {
		Retorno retorno = new Retorno();
		try {
			emf = Persistence.createEntityManagerFactory("Implementacion Pipamax JPA");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Proyecto> q = em.createQuery("select obj from Proyecto obj where obj.activo = 1",Proyecto.class);
			q.setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			List<Proyecto> proys = q.getResultList();
			for(Proyecto p: proys){
				em.lock(p, LockModeType.OPTIMISTIC);
				em.detach(p);
			}
			retorno.setDatos(proys);
			
		} catch (NoResultException nre) {
			retorno.addError(Errores.proyectoNoEncontrado, null);
		}catch (OptimisticLockException ole){
			retorno.addError(Errores.errorDeAccesoConcurrente, ole.getMessage());
			em.getTransaction().rollback();
		}catch (PersistenceException pe) {
			retorno.addError(Errores.proyectoNoEncontrado, null);
			em.getTransaction().rollback();
		}catch (IllegalStateException e) {
			retorno.addError(Errores.proyectoNoEncontrado, null);
			em.getTransaction().rollback();
		}finally{
			em.close();
			emf.close();
		}
		
		return retorno;
	}
}