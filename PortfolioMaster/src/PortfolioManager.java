import org.apache.log4j.Logger;

import packagePortfolio.Asset;
import packagePortfolio.Email;
import packagePortfolio.Person;
import packagePortfolio.Portfolio;
import packagePortfolio.PortfolioAsset;
import packagePortfolio.ReportMaker;
import packagePortfolio.PortfolioList;
import packagePortfolio.PortfolioComparator;

import com.sdb.PortfolioData;

import packagePortfolio.Address;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * PortfolioManager.java
 * RAIK 184H
 * This is the application class for the database, it contains the main method
 * which runs the financial management system.
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 3.0
 */
public class PortfolioManager{
	//Data members
	private PortfolioData input = new PortfolioData();
	static org.apache.log4j.Logger log = Logger.getLogger(PortfolioManager.class.getName());

	/**
	 * Obtains the input
	 * @return input, a DataReader object
	 */
	public PortfolioData getInput(){
		return input;
	}
	
	/**
	 * Runs the summary report to list all portfolios by owner's last name.
	 */
	public void listByOwner(){
		PortfolioList<Portfolio> allPortfolios = getInput().getPortfolios(PortfolioComparator.ownerComparator());
		log.info("Portfolios retrieved");
		
		ReportMaker report = new ReportMaker(allPortfolios);
		System.out.println("Sorting by Owner, A-Z:\n");
		report.printSummaryReport();
		
		allPortfolios.clear();
	}
	
	/**
	 * Runs the summary report to list all portfolios by total value, ascending.
	 */
	public void listByValue(){
		PortfolioList<Portfolio> allPortfolios = getInput().getPortfolios(PortfolioComparator.valueComparator());
		log.info("Portfolios retrieved");
		
		ReportMaker report = new ReportMaker(allPortfolios);
		System.out.println("Sorting by Total Value, in descending order:\n");
		report.printSummaryReport();
		
		allPortfolios.clear();
	}
	
	/**
	 * Runs the summary report to list all portfolios by their managers, 
	 * first by the broker type of manager (Junior or Expert) then by 
	 * last name/first name.
	 */
	public void listByBrokerType(){
		PortfolioList<Portfolio> allPortfolios = getInput().getPortfolios(PortfolioComparator.managerComparator());
		log.info("Portfolios retrieved");
		
		ReportMaker report = new ReportMaker(allPortfolios);
		System.out.println("Sorting by Manager: Junior/Expert, then ordering by manager last name, first name:\n");
		report.printSummaryReport();
		
		allPortfolios.clear();
	}
	
	/**
	 * This is the main method; it runs the program
	 * @param args the set of arguments provided at runtime
	 */
	public static void main(String args[]) {
//		log.info("Getting portfolios");
//		
		PortfolioManager manager = new PortfolioManager();
//		manager.listByOwner();
//		manager.listByValue();
//		manager.listByBrokerType();
//
//		log.info("Program finished.");
		
//		manager.getAddresses();
//		manager.getEmails();
//		manager.getPersons();
//		manager.getAssets();
//		manager.getPortfolios();
//		manager.getPortfolioAssets();
	}
	
	public void getAddresses() {
		EntityManagerFactory emf = null; 
		EntityManager em = null;
		List<Address> addresses = null;
		
		try {
			emf = Persistence.createEntityManagerFactory("jmelcher_database");
			em = emf.createEntityManager();

			em.getTransaction().begin();
			String query = "FROM Address";
			
			try {
				addresses = (List<Address>) em.createQuery(query).getResultList();
				 		 
			} catch(Exception e) {
				System.out.println("Error loading Address");
				e.printStackTrace();
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				throw new RuntimeException("Error loading Address-2", e);
			}
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

		for(Address addr : addresses) {
			System.out.println("Person with code: " + addr.getPerson().getCode() + " has\nAddress: "+addr.getStreet() + ", " + addr.getCity() + ", " + addr.getState() + ", " + addr.getZipcode() + ", " + addr.getCountry());
		}
	}
	
	public void getEmails() {
		EntityManagerFactory emf = null; 
		EntityManager em = null;
		List<Email> emails = null;
		
		
		try {
			emf = Persistence.createEntityManagerFactory("jmelcher_database");
			em = emf.createEntityManager();

			em.getTransaction().begin();
			String query = "FROM Email";
			
			try {
				emails = (List<Email>) em.createQuery(query).getResultList();
				 		 
			} catch(Exception e) {
				System.out.println("Error loading Email");
				e.printStackTrace();
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				throw new RuntimeException("Error loading Email-2", e);
			}
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

		for(Email email : emails) {
			System.out.println("Person with code " + email.getPerson().getCode() + " has email: "+ email.getEmailAddress());
		}
	}
	
	public void getPersons() {
		EntityManagerFactory emf = null; 
		EntityManager em = null;
		List<Person> persons = null;
		
		
		try {
			emf = Persistence.createEntityManagerFactory("jmelcher_database");
			em = emf.createEntityManager();

			em.getTransaction().begin();
			String query = "FROM Person";
			
			try {
				persons = (List<Person>) em.createQuery(query).getResultList();
				 		 
			} catch(Exception e) {
				System.out.println("Error loading Person");
				e.printStackTrace();
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				throw new RuntimeException("Error loading Person-2", e);
			}
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

		for(Person person : persons) {
			System.out.println("Person: Code: " + person.getCode() + ", Last Name: " + person.getLastName() + ", First Name: " + person.getFirstName() + ", Broker Type: " + person.getBrokerType() + ", SEC ID: " + person.getSecIdentifier());
		}
	}
	
	public void getAssets() {
		EntityManagerFactory emf = null; 
		EntityManager em = null;
		List<Asset> assets = null;
		
		try {
			emf = Persistence.createEntityManagerFactory("jmelcher_database");
			em = emf.createEntityManager();

			em.getTransaction().begin();
			String query = "FROM Asset";
			
			try {
				assets = (List<Asset>) em.createQuery(query).getResultList();
		 
			} catch(Exception e) {
				System.out.println("Error loading Asset");
				e.printStackTrace();
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				throw new RuntimeException("Error loading Asset-2", e);
			}
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

		for(Asset asset : assets) {
			System.out.println("Asset: Code: " + asset.getCode() + " Label: " + asset.getLabel() + " Return Rate: " + asset.getReturnRate() + " Risk: " + asset.getRiskValue() + " Base Rate: " + asset.getBaseRate() + " Class: " + asset.getClass());
		}
	}
	
	public void getPortfolios() {
		EntityManagerFactory emf = null; 
		EntityManager em = null;
		List<Portfolio> portfolios = null;
		
		try {
			emf = Persistence.createEntityManagerFactory("jmelcher_database");
			em = emf.createEntityManager();

			em.getTransaction().begin();
			String query = "FROM Portfolio";
			
			try {
				portfolios = (List<Portfolio>) em.createQuery(query).getResultList();
					 		 
			} catch(Exception e) {
				System.out.println("Error loading Portfolio");
				e.printStackTrace();
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				throw new RuntimeException("Error loading Portfolio-2", e);
			}
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

		for(Portfolio port : portfolios) {
			if(port.getBeneficiaryId() != null) { 
				System.out.println("Portfolio: Code: " + port.getCode() + " Owner ID: " + port.getOwnerId().getCode() + " Manager ID: " + port.getManagerId().getCode() + " Beneficiary ID: " + port.getBeneficiaryId().getCode() + " Broker Fees: " + port.getBrokerFees());
			}
			else {
				System.out.println("Portfolio: Code: " + port.getCode() + " Owner ID: " + port.getOwnerId().getCode() + " Manager ID: " + port.getManagerId().getCode() +  " Broker Fees: " + port.getBrokerFees());
			}
		}
	}
	
	public void getPortfolioAssets() {
		EntityManagerFactory emf = null; 
		EntityManager em = null;
//		List<PortfolioAsset> portfolioAssets = null;
		List<PortfolioAsset> portAssets = null;
		try {
			emf = Persistence.createEntityManagerFactory("jmelcher_database");
			em = emf.createEntityManager();

			em.getTransaction().begin();
			String queryPA = "FROM PortfolioAsset";
			
			try {
				portAssets = (List<PortfolioAsset>) em.createQuery(queryPA).getResultList();
				 		 
			} catch(Exception e) {
				System.out.println("Error loading PortfolioAsset");
				e.printStackTrace();
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				throw new RuntimeException("Error loading PortfolioAsset-2", e);
			}
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

		for(PortfolioAsset portAsset : portAssets) {
			System.out.println("PortfolioAsset: Portfolio ID: " + portAsset.getAssetId());
//			//TODO realizing that the below in its current state would/should break 
//			//+ " Asset ID: " + portAsset.getAssetId() + " Given Value: " + portAsset.getGivenValue());
		}
	}
}