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
		String deletePA= "DELETE FROM PortfolioAsset WHERE id IN (SELECT id FROM (SELECT * FROM PortfolioAsset) AS pa" 
				+ " WHERE portfolio_id IN ((SELECT Portfolio.id FROM Portfolio WHERE (owner_id IN (" 
				+ " SELECT Person.id FROM Person WHERE code = ?) OR manager_id IN ( " 
				+ " SELECT Person.id FROM Person WHERE code = ?)))))";
		String deletePort= "DELETE FROM Portfolio WHERE (owner_id = (SELECT id FROM Person WHERE code = ?) OR " 
				+ "manager_id = (SELECT id FROM Person WHERE code = ?))";
		String deleteEmail="DELETE FROM Email WHERE person_id = (SELECT Person.id FROM Person WHERE code = ?)";
		String deleteAddress="DELETE FROM Address WHERE person_id = (SELECT Person.id FROM Person WHERE code = ?)";
		String deletePerson="DELETE FROM Person WHERE id = (SELECT id FROM (SELECT * FROM Person) AS p WHERE code = ?)";

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
		String addStateQuery = "INSERT IGNORE INTO State(name) VALUES(?)";
		String addCountryQuery = "INSERT IGNORE INTO Country(name) VALUES(?)";
		String addAddressQuery = "INSERT INTO Address(street,city,state_id,zipcode,country_id,person_id) VALUES " 
				+ "(?, ?,(SELECT id FROM State WHERE (name = ?)),?," 
				+ "(SELECT id FROM Country WHERE (name = ?)),"
				+ "(SELECT id FROM Person WHERE (code = ?)))";

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

			ps = conn.prepareStatement(addStateQuery);
			ps.setString(1, state);
			ps.executeUpdate();
			ps.close();

			ps = conn.prepareStatement(addCountryQuery);
			ps.setString(1, country);
			ps.executeUpdate();
			ps.close();

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

		String query = "INSERT INTO Email(emailAddress,person_id) VALUES (?,(SELECT id FROM Person WHERE (code = ?)))";

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

			String query = "DELETE FROM PortfolioAsset WHERE asset_id = (SELECT id FROM Asset WHERE code = ?)";
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

			String query = "DELETE FROM PortfolioAsset WHERE id IN (SELECT id FROM (SELECT * FROM PortfolioAsset) AS portAsset WHERE portfolio_id IN ((SELECT id FROM Portfolio WHERE code = ?)))";
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

			String query = "INSERT INTO Portfolio(code, owner_id, manager_id, beneficiary_id) VALUES (?, (SELECT id FROM Person WHERE (code = ?)), (SELECT id from Person WHERE (code = ?)), (select id FROM Person WHERE (code = ?)))";
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
			Factory.closeResources( ps, conn);
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

			String queryCheck = "SELECT code FROM Asset WHERE id IN (SELECT asset_id FROM PortfolioAsset WHERE portfolio_id = (SELECT id FROM Portfolio WHERE code = ?))";
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
				String query = "INSERT INTO PortfolioAsset(asset_id, portfolio_id, givenValue) VALUES ((SELECT id FROM Asset WHERE (code = ?)), (SELECT id FROM Portfolio where (code = ?)), ?)";
				ps = conn.prepareStatement(query);
				ps.setString(1, assetCode);
				ps.setString(2, portfolioCode);
				ps.setDouble(3,  value);

				ps.executeUpdate();
				ps.close();
			} else {
				String updateValue = "UPDATE PortfolioAsset SET givenValue = (givenValue + ?) WHERE asset_id = (SELECT id FROM Asset WHERE (code = ?))";
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
	 * into a sorted list ADT using the input comparator
	 * @param c, the comparator the portfolios will be sorted by
	 * @return a sorted list of portfolios
	 */
	//	public GenericList<Portfolio> getPortfolios(Comparator<Portfolio> c){
	public List<Portfolio> getPortfolios(Comparator<Portfolio> c){

		//		GenericList<Portfolio> portfolios = new GenericList<Portfolio>(c);
		EntityManagerFactory emf = null; 
		EntityManager em = null;
		List<Portfolio> portfolios = null;
		List<Asset> assets = getAssets();
		List<Person> persons = getPersons(); // ?
		List<PortfolioAsset> portAssets = getPortfolioAssets();
		//		HashMap<String,Double> assetIDList = new HashMap<String,Double>();

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


				
				for(Person person : persons) {
					for(Portfolio port : portfolios) {
//						HashMap<Asset,double[]> assetList = new HashMap<Asset,double[]>();
//						port.setCommissionFees(2000);
//						port.setTotalValue(5000);
//						port.setTotalAnnualReturns(4000);
//						port.setTotalRisks(3000);
//						port.setBrokerFees(1000);
						for(Asset asset : assets) {
							for(PortfolioAsset portAsset : portAssets) {
								if(person.getPersonId() == port.getOwner().getPersonId() 
										&& port.getPortfolioId() == portAsset.getPortfolio().getPortfolioId() 
										&& asset.getCode() == portAsset.getAsset().getCode()) {
									
									
//									System.out.println(person.getLastName() + ", " + person.getFirstName() 
//											+ " owns Asset " + asset.getCode() + " in Portfolio " + port.getCode() 
//											+ " has given value: $" + portAsset.getGivenValue());
									
									
//									//Most likely have to do something like the following?
//									assetList.put(asset, new double[]{calculateRisks(asset), calculateAnnualReturns(assetNumeric,asset),calculateValues(assetNumeric,asset)});
									
									//port.getAssetList().put(asset, new double[]{});// TODO how does?
									
									
									
//									System.out.println("Compute Annual Returns: " + asset.computeAnnualReturns(portAsset.getGivenValue()));
//									asset.computeReturnRate(portAsset.getGivenValue());
//									System.out.println("Get Return Rate: " + asset.getReturnRate());
//									System.out.println("Compute Value of Asset: " + asset.computeValueOfAsset(portAsset.getGivenValue()));
//									System.out.println("Get Risk Value: " + asset.getRiskValue());
//									port.calculateTotalRisks();
//									port.calculateTotalValue();
//									System.out.println("Port Get Total Annual Returns: " + port.getTotalAnnualReturns());
//									System.out.println("Port Get Total Risks: " + port.getTotalRisks());
//									System.out.println("Portfolio Total Value: " + port.getTotalValue());
								}
							}	
						}
					}
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
		//		Factory.getDriver();
		//		Connection conn = Factory.getConnection();
		//		PreparedStatement ps = null;
		//		ResultSet rs = null;
		//		try{
		//			ArrayList<Asset> assets = getAssets();
		//			ArrayList<Person> persons = getPersons();
		//			
		//			String query = "SELECT * from Portfolio left JOIN PortfolioAsset ON Portfolio.id = PortfolioAsset.portfolio_id";
		//			ps = conn.prepareStatement(query);
		//			rs = ps.executeQuery();
		//
		//			while(rs.next()){
		//
		//				PreparedStatement psAssets = null;
		//				ResultSet rsAssets = null;
		//
		//				String queryAsset = "Select asset_id, givenValue from PortfolioAsset where PortfolioAsset.portfolio_id = ?";
		//				psAssets = conn.prepareStatement(queryAsset);
		//				psAssets.setInt(1, rs.getInt("Portfolio.id"));
		//
		//				rsAssets = psAssets.executeQuery();
		//
		//				if(checkIfIDIsUnique(rs.getString("code"),portfolios)){ //checks if portfolio with same code exists yet
		//
		//					HashMap<String,Double> assetIDList = new HashMap<String,Double>();
		//					while(rsAssets.next()){
		//						PreparedStatement psAssetCodes = null;
		//						ResultSet rsAssetCodes = null;
		//			
		//						String queryAssetCode = "Select code from Asset where Asset.id = ?";
		//						psAssetCodes = conn.prepareStatement(queryAssetCode);
		//						psAssetCodes.setInt(1, rsAssets.getInt("asset_id"));
		//
		//						rsAssetCodes = psAssetCodes.executeQuery();
		//						rsAssetCodes.next();
		//						
		//						assetIDList.put(rsAssetCodes.getString("code"),rsAssets.getDouble("givenValue"));
		//						rsAssetCodes.close();
		//						psAssetCodes.close();
		//					}
		//
		//					rsAssets.close();
		//					psAssets.close();
		//
		//					if(rs.getString("beneficiary_id") != null){
		//						
		//						PreparedStatement psOwner = null;
		//						ResultSet rsOwner = null;
		//						
		//						String queryOwner = "Select code from Person where Person.id = ?";
		//						psOwner = conn.prepareStatement(queryOwner);
		//						psOwner.setInt(1, rs.getInt("owner_id"));
		//						rsOwner = psOwner.executeQuery();
		//						rsOwner.next();
		//						String ownerCode = rsOwner.getString("code");
		//						
		//						rsOwner.close();
		//						psOwner.close();
		//
		//						PreparedStatement psManager = null;
		//						ResultSet rsManager = null;
		//						
		//						String queryManager = "Select code from Person where Person.id = ?";
		//						psManager = conn.prepareStatement(queryManager);
		//						psManager.setInt(1, rs.getInt("manager_id"));
		//						rsManager = psManager.executeQuery();
		//						rsManager.next();
		//						String managerCode = rsManager.getString("code");
		//						
		//						rsManager.close();
		//						psManager.close();
		//
		//						PreparedStatement psBeneficiary = null;
		//						ResultSet rsBeneficiary = null;
		//						
		//						String queryB = "Select code from Person where Person.id = ?";
		//						psBeneficiary = conn.prepareStatement(queryB);
		//						psBeneficiary.setInt(1, rs.getInt("beneficiary_id"));
		//						rsBeneficiary = psBeneficiary.executeQuery();
		//						rsBeneficiary.next();
		//						String beneficiaryCode = rsBeneficiary.getString("code");
		//						
		//						rsBeneficiary.close();
		//						psBeneficiary.close();
		////						portfolios.add(new Portfolio(rs.getString("code"), searchPerson(ownerCode,persons), (Broker) searchPerson(managerCode,persons), searchPerson(beneficiaryCode,persons), searchAssets(assetIDList,assets)));
		//						portfolios.add(new Portfolio(rs.getString("code"), searchPerson(ownerCode,persons), searchPerson(managerCode,persons), searchPerson(beneficiaryCode,persons), searchAssets(assetIDList,assets)));
		//
		//					}
		//					else{
		//						PreparedStatement psOwner = null;
		//
		//						String queryOwner = "Select code from Person where Person.id = ?";
		//						psOwner = conn.prepareStatement(queryOwner);
		//						psOwner.setInt(1, rs.getInt("owner_id"));
		//						
		//						ResultSet rsOwner = psOwner.executeQuery();
		//						rsOwner.next();
		//						String ownerCode = rsOwner.getString("code");
		//						
		//						rsOwner.close();
		//						psOwner.close();
		//
		//						PreparedStatement psManager = null;
		//						ResultSet rsManager = null;
		//						
		//						String queryM = "Select code from Person where Person.id = ?";
		//						psManager = conn.prepareStatement(queryM);
		//						psManager.setInt(1, rs.getInt("manager_id"));
		//						rsManager = psManager.executeQuery();
		//						rsManager.next();
		//						String managerCode = rsManager.getString("code");
		//						
		//						rsManager.close();
		//						psManager.close();
		//						portfolios.add(new Portfolio(rs.getString("code"), searchPerson(ownerCode,persons), (Broker) searchPerson(managerCode,persons),  searchAssets(assetIDList,assets)));
		//					}
		//				}
		//			}
		//			return portfolios;
		//			
		//		} catch(Exception e){ 
		//			log.error("SQLException: " + e);
		//			throw new RuntimeException(e);	
		//		}
		//		finally {
		//			Factory.closeResources(rs, ps, conn);
		//		}
	}

	/**
	 * This method searches a given list of persons to find the specified person
	 * @param id, the person's id
	 * @param persons, the ArrayList of persons
	 * @return the person matching the id, if there is one. Else, returns null
	 */
	public Person searchPerson(String id, ArrayList<Person> persons){
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
	 * Searches for specific assets in a given ArrayList of assets
	 * @param idList, list of ids to be searched for
	 * @param assets, list of assets to search through
	 * @return assetList, a HashMap of assets to values given
	 */
	public HashMap<Asset, Double> searchAssets(HashMap<String,Double> idList, List<Asset> assets){//ArrayList<Asset> assets){
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

		//		Factory.getDriver();
		//		Connection conn = null;
		//		PreparedStatement ps = null;
		//		ResultSet rs = null;
		//		
		//		ArrayList<Asset> allAssets = new ArrayList<Asset>();
		//
		//		try {
		//			conn = Factory.getConnection();
		//
		//			String query = "Select * from Asset";
		//			ps = conn.prepareStatement(query);
		//			rs = ps.executeQuery();
		//
		//			while(rs.next()){
		//				if(rs.getString("type").equalsIgnoreCase("D")){ 
		//					Deposit dep = new Deposit(rs.getString("code"), rs.getString("label"), rs.getString("type"), rs.getDouble("baseRate") * 100);
		//					allAssets.add(dep);
		//				} 
		//				else if(rs.getString("type").equalsIgnoreCase("S")){
		//					Stock sto = new Stock(rs.getString("code"), rs.getString("label"), rs.getString("type"),rs.getDouble("quarterlyDividend"), rs.getDouble("baseRate")*100, rs.getDouble("sharePrice"),rs.getString("symbol"),rs.getDouble("beta"));
		//					allAssets.add(sto);
		//				}
		//				else{
		//					Investment inv = new Investment(rs.getString("code"), rs.getString("label"), rs.getString("type"),rs.getDouble("quarterlyDividend"), rs.getDouble("baseRate")*100,rs.getDouble("omega"),rs.getDouble("investmentValue"));
		//					allAssets.add(inv);
		//				}
		//			}
		//
		//			rs.close();
		//			ps.close();
		//			conn.close();
		//			
		//			return allAssets;
		//		
		//		} catch(Exception e) {
		//			log.error("SQL Exception : " + e);
		//			throw new RuntimeException(e);
		//		} finally {
		//			Factory.closeResources(rs, ps, conn);
		//		}
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
		//	}
		//		Factory.getDriver();
		//		Connection conn = null;
		//		PreparedStatement ps = null;
		//		ResultSet rs = null;
		//
		//		ArrayList<Person> allPersons = new ArrayList<Person>();
		//
		//		try {
		//			conn = Factory.getConnection();
		//
		//			String query = "Select * from Person";
		//			ps = conn.prepareStatement(query);
		//			rs = ps.executeQuery();
		//		
		//			while(rs.next()){
		//				if(rs.getString("brokerType") != null){
		//					Set<Email> email = new HashSet<Email>();
		//					Address ad = null;
		//					ResultSet rsEmail = null;
		//					String emailQuery = "SELECT * from Email WHERE 'Email.person_id' = ?";
		//					PreparedStatement psEmail = conn.prepareStatement(emailQuery);
		//					psEmail.setString(1, rs.getString("id"));
		//
		//					rsEmail = psEmail.executeQuery();
		//					while(rsEmail.next()){
		//						email.add(new Email(rsEmail.getString("emailAddress")));
		//					}
		//					rsEmail.close();
		//					psEmail.close();
		//
		//					ResultSet rsAddress = null;
		//					String addressQuery = "Select * from Address left join State on Address.state_id = State.id "
		//							+ " left join Country on Address.country_id = Country.id Where Address.person_id = ?";
		//					PreparedStatement psAddress = conn.prepareStatement(addressQuery);
		//					psAddress.setString(1, rs.getString("id"));
		//
		//					rsAddress = psAddress.executeQuery();
		//					if(rsAddress.next()){
		//						ad = new Address(rsAddress.getString("street"), rsAddress.getString("city"), 
		//								rsAddress.getString("State.name"),rsAddress.getString("zipcode"), 
		//								rsAddress.getString("Country.name"));
		//					}
		//					
		//					rsAddress.close();
		//					psAddress.close();
		//					
		//					Broker b = new Broker(rs.getString("code"),rs.getString("brokerType").charAt(0),
		//							rs.getString("secID"),rs.getString("lastName"),rs.getString("firstName"),ad,email);
		//					
		//					allPersons.add(b);
		//				}
		//				else {
		////					ArrayList<String> email = new ArrayList<String>();
		//					Set<Email> email = new HashSet<Email>();
		//					Address ad = null;
		//					ResultSet rsEmail = null;
		//					String emailQuery = "SELECT * from Email WHERE 'Email.person_id' = ?";
		//					PreparedStatement psEmail = conn.prepareStatement(emailQuery);
		//					psEmail.setString(1, rs.getString("id"));
		//
		//					rsEmail = psEmail.executeQuery();
		//					while(rsEmail.next()){
		//						email.add(new Email(rsEmail.getString("emailAddress")));
		//					}
		//					
		//					rsEmail.close();
		//					psEmail.close();
		//
		//					ResultSet rsAddress = null;
		//					String addressQuery = "Select * from Address left join State on Address.state_id = State.id join Country on Address.country_id = Country.id Where Address.person_id = ?";
		//					PreparedStatement psAddress = conn.prepareStatement(addressQuery);
		//					psAddress.setString(1, rs.getString("id"));
		//
		//					rsAddress = psAddress.executeQuery();
		//					if(rsAddress.next()){
		//						ad = new Address(rsAddress.getString("street"), rsAddress.getString("city"), rsAddress.getString("State.name"),rsAddress.getString("zipcode"), rsAddress.getString("Country.name"));
		//					}
		//
		//					rsAddress.close();
		//					psAddress.close();
		//
		//					Person p = new Person(rs.getString("code"),null, null, rs.getString("lastName"),rs.getString("firstName"),ad,email);
		//					allPersons.add(p);
		//				}
		//			}
		//
		//			rs.close();
		//			ps.close();
		//
		//			conn.close();
		//			return allPersons;
		//
		//		} catch(Exception e) {
		//			log.error("Error : " + e);
		//			throw new RuntimeException(e);
		//
		//		} finally {
		//			Factory.closeResources(rs, ps, conn);
		//		}
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

		for(Email email : emails) {
			System.out.println("Person with code " + email.getPerson().getCode() + " has email: "+ email.getEmailAddress());
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

		for(Address addr : addresses) {
			System.out.println("Person with code: " + addr.getPerson().getCode() + " has\nAddress: "+ addr.getStreet() + ", " + addr.getCity() + ", " + addr.getState() + ", " + addr.getZipcode() + ", " + addr.getCountry());
		}
	}
}