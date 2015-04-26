package com.sdb; //DO NOT CHANGE THIS

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import packagePortfolio.Address;
import packagePortfolio.Asset;
import packagePortfolio.Email;
import packagePortfolio.Person;
import packagePortfolio.Portfolio;
import packagePortfolio.GenericList;
import packagePortfolio.PortfolioAsset;

import com.sdb.PortfolioData;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

/**
 * PortfolioData.java
 * RAIK 184H
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 */
public class PortfolioData {

	static org.apache.log4j.Logger log = Logger.getLogger(PortfolioData.class.getName());

	/**
	 * Method that removes every person record from the database
	 */
	public static void removeAllPersons() {
		Factory.getDriver();
		Connection conn = null;
		PreparedStatement ps = null;

		String deletePA = "DELETE FROM PortfolioAsset";
		String deletePort = "DELETE FROM Portfolio";
		String deleteEmail = "DELETE FROM Email";	
		String deleteAddress = "DELETE FROM Address";
		String deletePerson = "DELETE FROM Person";	

		try {
			conn = Factory.getConnection();

			ps = conn.prepareStatement(deletePA);
			ps.executeUpdate();
			ps.close();

			ps = conn.prepareStatement(deletePort);
			ps.executeUpdate();
			ps.close();

			ps = conn.prepareStatement(deleteEmail);
			ps.executeUpdate();
			ps.close();

			ps = conn.prepareStatement(deleteAddress);
			ps.executeUpdate();
			ps.close();

			ps = conn.prepareStatement(deletePerson);
			ps.executeUpdate();
			ps.close();

			conn.close();

		} catch (SQLException e) {
			log.error("SQLException: " + e);
			throw new RuntimeException(e);
		} finally {
			Factory.closeResources(ps, conn);
		}
	}

	/**
	 * Removes the person record from the database corresponding to the
	 * provided <code>code</code>
	 * @param code
	 */
	public static void removePerson(String code) {
		Factory.getDriver();
		Connection conn = null;
		PreparedStatement ps = null;

		String safeUpdatesOff = "SET SQL_SAFE_UPDATES=0";
		String deletePA= "DELETE FROM PortfolioAsset WHERE portAsset_id IN (SELECT portAsset_id FROM (SELECT * FROM PortfolioAsset) AS pa" 
				+ " WHERE portfolio_id IN ((SELECT Portfolio.portfolio_id FROM Portfolio WHERE (owner_id IN (" 
				+ " SELECT Person.person_id FROM Person WHERE code = ?) OR manager_id IN ( " 
				+ " SELECT Person.person_id FROM Person WHERE code = ?)))))";
		String deletePort= "DELETE FROM Portfolio WHERE (owner_id = (SELECT person_id FROM Person WHERE code = ?) OR " 
				+ "manager_id = (SELECT person_id FROM Person WHERE code = ?))";
		String deleteEmail="DELETE FROM Email WHERE person_id = (SELECT Person.person_id FROM Person WHERE code = ?)";
		String deleteAddress="DELETE FROM Address WHERE person_id = (SELECT Person.person_id FROM Person WHERE code = ?)";
		String deletePerson="DELETE FROM Person WHERE person_id = (SELECT person_id FROM (SELECT * FROM Person) AS p WHERE code = ?)";

		try {
			conn = Factory.getConnection();

			ps = conn.prepareStatement(safeUpdatesOff);
			ps.executeUpdate();
			ps.close();

			ps = conn.prepareStatement(deletePA);
			ps.setString(1, code);
			ps.setString(2, code);
			ps.executeUpdate();
			ps.close();

			ps = conn.prepareStatement(deletePort);
			ps.setString(1, code);
			ps.setString(2, code);
			ps.executeUpdate();
			ps.close();

			ps = conn.prepareStatement(deleteEmail);
			ps.setString(1, code);
			ps.executeUpdate();
			ps.close();

			ps = conn.prepareStatement(deleteAddress);
			ps.setString(1, code);
			ps.executeUpdate();
			ps.close();

			ps = conn.prepareStatement(deletePerson);
			ps.setString(1, code);
			ps.executeUpdate();
			ps.close();

			conn.close();

		} catch (SQLException e) {
			log.error("SQLException: " + e);
			throw new RuntimeException(e);
		} finally {
			Factory.closeResources(ps, conn);
		}
	}

	/**
	 * Method to add a person record to the database with the provided data. The
	 * <code>brokerType</code> will either be "E" or "J" (Expert or Junior) or 
	 * <code>null</code> if the person is not a broker.
	 * @param code
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 * @param brokerType
	 */
	public static void addPerson(String code, String firstName, String lastName, String street, 
			String city, String state, String zip, String country, String brokerType, String secBrokerId) {

		Factory.getDriver();
		Connection conn = null;
		PreparedStatement ps = null;

		String addPersonQuery= "INSERT INTO Person(code, lastName, firstName, secID, brokerType) VALUES (?, ?, ?, ?, ?)";
//		String addStateQuery = "INSERT IGNORE INTO State(name) VALUES(?)";
//		String addCountryQuery = "INSERT IGNORE INTO Country(name) VALUES(?)";
		String addAddressQuery = "INSERT INTO Address(street,city,state,zipcode,country,person_id) VALUES " 
				+ "(?,?,?,?,?,"
				+ "(SELECT person_id FROM Person WHERE (code = ?)))";

		try {
			conn = Factory.getConnection();
			ps = conn.prepareStatement(addPersonQuery);
			ps.setString(1, code);
			ps.setString(2, lastName);
			ps.setString(3, firstName);
			ps.setString(4, secBrokerId);
			ps.setString(5, brokerType);
			ps.executeUpdate();
			ps.close();

//			ps = conn.prepareStatement(addStateQuery);
//			ps.setString(1, state);
//			ps.executeUpdate();
//			ps.close();
//
//			ps = conn.prepareStatement(addCountryQuery);
//			ps.setString(1, country);
//			ps.executeUpdate();
//			ps.close();

			ps = conn.prepareStatement(addAddressQuery);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setString(4, zip);
			ps.setString(5, country);
			ps.setString(6, code);
			ps.executeUpdate();
			ps.close();

			conn.close();

		} catch (SQLException e) {
			log.error("SQLException: " + e);
			throw new RuntimeException(e);
		} finally {
			Factory.closeResources(ps, conn);
		}
	}

	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>code</code>
	 * @param code
	 * @param email
	 */
	public static void addEmail(String code, String email) {
		Factory.getDriver();
		Connection conn = null;
		PreparedStatement ps = null;

		String query = "INSERT INTO Email(emailAddress,person_id) VALUES (?,(SELECT person_id FROM Person WHERE (code = ?)))";

		try {
			conn = Factory.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, code);
			ps.executeUpdate();
			ps.close();

			conn.close();

		} catch (SQLException e) {
			log.error("SQLException: " + e);
			throw new RuntimeException(e);
		} finally {
			Factory.closeResources(ps, conn);
		}
	}

	/**
	 * Removes all asset records from the database
	 */
	public static void removeAllAssets() {
		Factory.getDriver();
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Factory.getConnection();

			String query = "DELETE FROM PortfolioAsset";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();

			query = "DELETE FROM Asset";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();
			conn.close();

		} catch(Exception e) {
			log.error("SQLException: " + e);
			throw new RuntimeException(e);
		} finally {
			Factory.closeResources( ps, conn);
		}
	}

	/**
	 * Removes the asset record from the database corresponding to the
	 * provided <code>assetCode</code>
	 * @param assetCode
	 */
	public static void removeAsset(String assetCode) {
		Factory.getDriver();
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Factory.getConnection();

			String query = "DELETE FROM PortfolioAsset WHERE asset_id = (SELECT asset_id FROM Asset WHERE code = ?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, assetCode);
			ps.executeUpdate();
			ps.close();

			query = "DELETE FROM Asset WHERE code = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, assetCode);
			ps.executeUpdate();
			ps.close();

			conn.close();

		} catch(Exception e) {
			log.error("SQLException: " + e);
			throw new RuntimeException(e);
		} finally {
			Factory.closeResources(ps, conn);
		}
	}

	/**
	 * Adds a deposit account asset record to the database with the
	 * provided data. 
	 * @param assetCode
	 * @param label
	 * @param apr
	 */
	public static void addDepositAccount(String assetCode, String label, double apr) {

		Factory.getDriver();
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Factory.getConnection();

			String query =  "INSERT INTO Asset(code,label,type,baseRate) values (?,?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, assetCode);
			ps.setString(2, label);
			ps.setString(3, "D");
			ps.setDouble(4, apr);
			ps.executeUpdate();
			ps.close();

			conn.close();

		} catch(Exception e) {
			log.error("SQLException: " + e);
			throw new RuntimeException(e);
		} finally {
			Factory.closeResources( ps, conn);
		}
	}

	/**
	 * Adds a private investment asset record to the database with the
	 * provided data. 
	 * @param assetCode
	 * @param label
	 * @param quarterlyDividend
	 * @param baseRateOfReturn
	 * @param baseOmega
	 * @param totalValue
	 */
	public static void addPrivateInvestment(String assetCode, String label, Double quarterlyDividend, 

			Double baseRateOfReturn, Double baseOmega, Double totalValue) {

		Factory.getDriver();
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Factory.getConnection();

			String query = "INSERT INTO Asset(code,label,type,baseRate,quarterlyDividend, omega, investmentValue) values (?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, assetCode);
			ps.setString(2, label);
			ps.setString(3, "P");
			ps.setDouble(4, baseRateOfReturn);
			ps.setDouble(5,quarterlyDividend);
			ps.setDouble(6,baseOmega);
			ps.setDouble(7,totalValue);

			ps.executeUpdate();
			ps.close();

			conn.close();

		} catch(Exception e) {
			log.error("SQLException: " + e);
			throw new RuntimeException(e);
		} finally {
			Factory.closeResources( ps, conn);
		}
	}

	/**
	 * Adds a stock asset record to the database with the
	 * provided data. 
	 * @param assetCode
	 * @param label
	 * @param quarterlyDividend
	 * @param baseRateOfReturn
	 * @param beta
	 * @param stockSymbol
	 * @param sharePrice
	 */
	public static void addStock(String assetCode, String label, Double quarterlyDividend, 
			Double baseRateOfReturn, Double beta, String stockSymbol, Double sharePrice) {

		Factory.getDriver();
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Factory.getConnection();

			String query = "INSERT INTO Asset(code,label,type,quarterlyDividend,baseRate, sharePrice, symbol,beta) values (?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, assetCode);
			ps.setString(2, label);
			ps.setString(3, "S");
			ps.setDouble(4, quarterlyDividend);
			ps.setDouble(5,baseRateOfReturn);
			ps.setDouble(6,sharePrice);
			ps.setString(7,stockSymbol);
			ps.setDouble(8,beta);

			ps.executeUpdate();
			ps.close();

			conn.close();

		} catch(Exception e) {
			log.error("SQLException: " + e);
			throw new RuntimeException(e);
		} finally {
			Factory.closeResources( ps, conn);
		}
	}

	/**
	 * Removes all portfolio records from the database
	 */
	public static void removeAllPortfolios() {
		Factory.getDriver();
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Factory.getConnection();

			String query = "DELETE FROM PortfolioAsset";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();

			query = "DELETE FROM Portfolio";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();

			conn.close();

		} catch(Exception e) {
			log.error("SQLException: " + e);
			throw new RuntimeException(e);
		} finally {
			Factory.closeResources( ps, conn);
		}
	}

	/**
	 * Removes the portfolio record from the database corresponding to the
	 * provided <code>portfolioCode</code>
	 * @param portfolioCode
	 */
	public static void removePortfolio(String portfolioCode) {
		Factory.getDriver();
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);

			String query = "DELETE FROM PortfolioAsset WHERE portAsset_id IN (SELECT portAsset_id FROM (SELECT * FROM PortfolioAsset) AS portAsset WHERE portfolio_id IN ((SELECT portfolio_id FROM Portfolio WHERE code = ?)))";
			ps = conn.prepareStatement(query);
			ps.setString(1, portfolioCode);
			ps.executeUpdate();
			ps.close();

			query = "DELETE FROM Portfolio WHERE code = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, portfolioCode);
			ps.executeUpdate();
			ps.close();

			conn.close();

		} catch(Exception e) {
			log.error("SQLException: " + e);
			throw new RuntimeException(e);
		} finally {
			Factory.closeResources( ps, conn);
		}
	}

	/**
	 * Adds a portfolio records to the database with the given data.  If the portfolio has no
	 * beneficiary, the <code>beneficiaryCode</code> will be <code>null</code>
	 * @param portfolioCode
	 * @param ownerCode
	 * @param managerCode
	 * @param beneficiaryCode
	 */
	public static void addPortfolio(String portfolioCode, String ownerCode, String managerCode, String beneficiaryCode) {
		Factory.getDriver();
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);

			String query = "INSERT INTO Portfolio(code, owner_id, manager_id, beneficiary_id) VALUES (?, (SELECT person_id FROM Person WHERE (code = ?)), (SELECT person_id from Person WHERE (code = ?)), (select person_id FROM Person WHERE (code = ?)))";
			ps = conn.prepareStatement(query);
			ps.setString(1, portfolioCode);
			ps.setString(2, ownerCode);
			ps.setString(3, managerCode);
			ps.setString(4, beneficiaryCode);

			ps.executeUpdate();
			ps.close();

			conn.close();

		} catch(Exception e) {
			log.error("SQLException: " + e);
			throw new RuntimeException(e);
		} finally {
			Factory.closeResources(ps, conn);
		}
	}

	/**
	 * Associates the asset record corresponding to <code>assetCode</code> with the 
	 * portfolio corresponding to the provided <code>portfolioCode</code>.  The third 
	 * parameter, <code>value</code> is interpreted as a <i>balance</i>, <i>number of shares</i>
	 * or <i>stake percentage</i> depending on the type of asset the <code>assetCode</code> is
	 * associated with.
	 * @param portfolioCode
	 * @param assetCode
	 * @param value
	 */
	public static void addAsset(String portfolioCode, String assetCode, double value) {
		Factory.getDriver();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);

			String queryCheck = "SELECT code FROM Asset WHERE asset_id IN (SELECT asset_id FROM PortfolioAsset WHERE portfolio_id = (SELECT portfolio_id FROM Portfolio WHERE code = ?))";
			ps = conn.prepareStatement(queryCheck);
			ps.setString(1, portfolioCode);
			rs = ps.executeQuery();

			boolean codeMatch = false;
			while(rs.next()) {
				String existingCode = rs.getString("code");
				if(existingCode.equals(assetCode)) {
					codeMatch = true;
				}
			}

			rs.close();
			ps.close();

			if(!codeMatch) {
				String query = "INSERT INTO PortfolioAsset(asset_id, portfolio_id, givenValue) VALUES ((SELECT asset_id FROM Asset WHERE (code = ?)), (SELECT portfolio_id FROM Portfolio where (code = ?)), ?)";
				ps = conn.prepareStatement(query);
				ps.setString(1, assetCode);
				ps.setString(2, portfolioCode);
				ps.setDouble(3,  value);

				ps.executeUpdate();
				ps.close();
			} else {
				String updateValue = "UPDATE PortfolioAsset SET givenValue = (givenValue + ?) WHERE asset_id = (SELECT asset_id FROM Asset WHERE (code = ?))";
				ps = conn.prepareStatement(updateValue);
				ps.setDouble(1, value);
				ps.setString(2, assetCode);
				ps.executeUpdate();
				ps.close();
			}

			conn.close();

		} catch(Exception e) {
			log.error("SQLException: " + e);
			throw new RuntimeException(e);
		} finally {
			Factory.closeResources(rs, ps, conn);
		}
	}

	/**
	 * Checks if portfolioID given is already present in ArrayList of given portfolios
	 * @param portfolioID
	 * @param portfoliosGiven
	 * @return boolean; true if unique, false if not
	 */
	public boolean checkIfIDIsUnique(String portfolioID, GenericList<Portfolio> portfoliosGiven){
		for(Portfolio p : portfoliosGiven){

			if(p.getCode().equalsIgnoreCase(portfolioID)){
				return false;
			}
		}
		return true;
	}

	/**
	 * The getPortfolios method retrieves all portfolios in the database. They are inserted
	 * into a sorted list.
	 * @return a sorted list of portfolios
	 */
	public List<Portfolio> getPortfolios(){

		EntityManagerFactory emf = null; 
		EntityManager em = null;
		List<Portfolio> portfolios = null;
		List<Asset> assets = null;
		List<Person> persons = null;
		List<PortfolioAsset> portAssets = null;
		

		try {
			emf = Persistence.createEntityManagerFactory("jmelcher_database");
			em = emf.createEntityManager();

			em.getTransaction().begin();
			String query = "FROM Portfolio";
			String queryPA = "FROM PortfolioAsset";
			String queryA = "FROM Asset";
			String queryPer = "FROM Person";
			try {
				portfolios = (List<Portfolio>) em.createQuery(query).getResultList();
				portAssets = (List<PortfolioAsset>) em.createQuery(queryPA).getResultList();
				assets = (List<Asset>) em.createQuery(queryA).getResultList();
				persons = (List<Person>) em.createQuery(queryPer).getResultList();
				
				for(PortfolioAsset portAsset : portAssets) {
						portAsset.getAsset().computeReturnRate(portAsset.getGivenValue());
				}
				
			} catch(Exception e) {
				System.out.println("Error loading Portfolio or PA or Asset or Person");
				e.printStackTrace();
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				throw new RuntimeException("Error loading Portfolio or PA of Asset - 2", e);
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

		return portfolios;
	}

	/**
	 * This method searches a given list of persons to find the specified person
	 * @param id, the person's id
	 * @param persons, the List of persons
	 * @return the person matching the id, if there is one. Else, returns null
	 */
	public Person searchPerson(String id, List<Person> persons){
		Person subject;

		for(Person person: persons){

			if(person.getCode().equalsIgnoreCase(id)){
				subject=person;
				return subject;
			}
		}
		return null;
	}

	/**
	 * Searches for specific assets in a List of assets
	 * @param idList, list of ids to be searched for
	 * @param assets, list of assets to search through
	 * @return assetList, a HashMap of assets to values given
	 */
	public HashMap<Asset, Double> searchAssets(HashMap<String,Double> idList, List<Asset> assets){
		HashMap<Asset,Double> assetList = new HashMap<Asset,Double>();
		for(String id: idList.keySet()){
			if(!id.equalsIgnoreCase("")) {
				double value = idList.get(id);

				for(Asset a: assets){
					if(a.getCode().equalsIgnoreCase(id)){
						assetList.put(a,value);
					}
				}
			}
		}
		return assetList;
	}

	/**
	 * Gets all the Assets currently in the database
	 * @return List<Asset> 
	 */
	public List<Asset> getAssets(){
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
		
		return assets;
	}

	/**
	 * Gets all of the persons in the database and puts them in a List
	 * @return List of persons
	 */
	public List<Person> getPersons(){

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

		return persons;
	}

	/**
	 * Retrieves all PortfolioAssets from the database.
	 * @return
	 */
	public List<PortfolioAsset> getPortfolioAssets() {
		EntityManagerFactory emf = null; 
		EntityManager em = null;
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

		return portAssets;
	}

	/**
	 * Retrieves all emails from the database.
	 */
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
	}

	/**
	 * Retrieves all addresses from the database.
	 */
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
	}
}