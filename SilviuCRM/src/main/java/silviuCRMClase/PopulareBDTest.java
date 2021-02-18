package silviuCRMClase;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PopulareBDTest {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

   EntityManagerFactory emf= Persistence.createEntityManagerFactory("SilviuCRM");
   EntityManager em= emf.createEntityManager();
   
   List<Lead>lstLeads= new ArrayList<>();
   
   //adaugare Leads
   
   
	em.getTransaction().begin();
	em.createQuery("DELETE FROM Lead l").executeUpdate();
	em.getTransaction().commit();
	
   lstLeads= em.createQuery("Select l from Lead l").getResultList();
   if(!lstLeads.isEmpty()) {
	   em.getTransaction().begin();
	   for(Lead l:lstLeads) {
		   em.remove(l);
	   }
	   em.getTransaction().commit();
   }
   
  
   
   em.getTransaction().begin();
   for(Lead l:lstLeads) {
	   em.persist(l);
   }
   em.getTransaction().commit();
   
   lstLeads.clear();
   
   lstLeads= em.createQuery("Select l from Lead l").getResultList();
   for(Lead l:lstLeads) {
	   System.out.println("ID: "+ l.getIdLead() + "Nume:" + l.getNumeLead());
   }
		
	}
	
	

}
