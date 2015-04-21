package packagePortfolio;

import java.util.List;
//import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestMain {
	
	public static void main(String args[]) {

		EntityManagerFactory emf = null; 
		EntityManager em = null;
		List<Address> se = null;
		
		try {
			emf = Persistence.createEntityManagerFactory("jmelcher_database");
			em = emf.createEntityManager();

			em.getTransaction().begin();
			//Other usage:
			//em.persist(...);
			//em.detach(...);
			//em.flush(); -synchs up EM with database
			//em.merge(...);
			//em.remove(...);
			String query = "FROM Address";
			
			try {
				
				se = (List<Address>) em.createQuery(query).getResultList();
				 
				//parameterized alternative: 
				//em.find(StudentEntity.class, 1);
//				Address s = se.get(0);
//				 
//				 EmailEntity email = new EmailEntity();
//				 email.setStudent(s);
//				 email.setAddress("boom@unl.edu");
//				 em.persist(email);
//				 em.refresh(s);
				 
			} catch(Exception e) {
				System.out.println("Error loading Address");
				e.printStackTrace();
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				throw new RuntimeException("Error loading Address-2", e);
			}

			//em.getTransaction().commit();
			em.getTransaction().rollback();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
			if (emf != null && emf.isOpen()) {
				emf.close();
			}
		}

		//Entity is detached at this point

		for(Address s : se) {
			System.out.println("Address: "+s.getStreet() + ", " + s.getCity() + ", " + s.getState() + ", " + s.getZipcode() + ", " + s.getCountry());
//			System.out.println("My courses: ");
//			for(CourseEntity ce : s.getCourses()) {
//				System.out.println("\t" + ce);
//			}
		}
	}
}