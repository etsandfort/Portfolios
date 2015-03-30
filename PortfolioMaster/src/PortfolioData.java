import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;

import com.mysql.jdbc.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */
public class PortfolioData {

	/**
 * Method that removes every person record from the database
 */
	
public static void removeAllPersons() {
Factory.getDriver();
String query1 = "DELETE  FROM PortfolioAsset";
String query2 = "DELETE  FROM Portfolio";
String query3 = "DELETE  FROM Email";	
String query4 = "DELETE  FROM Address";
String query5 = "DELETE  FROM Person";	
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = Factory.getConnection();
			ps = conn.prepareStatement(query1);
			ps.executeUpdate();
			ps.close();
			ps = conn.prepareStatement(query2);
			ps.executeUpdate();
			ps.close();
			ps = conn.prepareStatement(query3);
			ps.executeUpdate();
			ps.close();
			ps = conn.prepareStatement(query4);
			ps.executeUpdate();
			ps.close();
			ps = conn.prepareStatement(query5);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {

			Factory.closeResources(ps, conn);
		}
}

/**
	 * Removes the person record from the database corresponding to the
	 * provided <code>personCode</code>
	 * @param personCode
	 */
	public static void removePerson(String personCode) {
Factory.getDriver();


String query1= "DELETE FROM PortfolioAsset WHERE portfolioAssetID IN "+
"(SELECT portfolioAssetID FROM (SELECT * FROM PortfolioAsset) AS p WHERE portfolioID IN "+
	"((SELECT portfolioID FROM Portfolio WHERE ownerID IN (" +
   " SELECT personID FROM Person WHERE personCode = ?) OR" +
    " managerID IN ( " +
   " SELECT personID FROM Person WHERE personCode = ?))))";
String query2= "DELETE FROM Portfolio WHERE " +
	"(ownerID = (SELECT personID FROM Person WHERE personCode = ?) OR managerID = (SELECT personID FROM Person WHERE personCode = ?))";
String query3="DELETE FROM Email WHERE personID = (SELECT personID FROM Person WHERE personCode = ?)";
String query4="DELETE FROM Address WHERE personID = (SELECT personID FROM Person WHERE personCode = ?)";
String query5="DELETE FROM Person WHERE personID = (SELECT personID FROM (SELECT * FROM Person) AS p WHERE personCode = ?)";

		PreparedStatement ps = null;
		Connection conn = Factory.getConnection();
		try {
			
			ps = conn.prepareStatement(query1);
			ps.setString(1, personCode);
			ps.executeUpdate();
			ps.close();

			ps = conn.prepareStatement(query2);
			ps.setString(1, personCode);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(query3);
			ps.setString(1, personCode);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(query4);
			ps.setString(1, personCode);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(query5);
			ps.setString(1, personCode);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {

			Factory.closeResources(ps, conn);
		}

	}

/**
	 * Method to add a person record to the database with the provided data. The
	 * <code>brokerType</code> will either be "E" or "J" (Expert or Junior) or 
	 * <code>null</code> if the person is not a broker.
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
	public static void addPerson(String personCode, String firstName, String lastName, String street, 
		String city, String state, String zip, String country, String brokerType, String secBrokerId) {
Factory.getDriver();
Connection conn = Factory.getConnection();
String addPersonQuery= "INSERT INTO Person(personCode, lastName, firstName, secID, brokerType) VALUES (?, ?, ?, ?, ?)";
String addStateQuery = "INSERT IGNORE INTO State(stateName) VALUES(?)";
String addCountryQuery = "INSERT IGNORE INTO Country(countryName) VALUES(?)";
String addAddressQuery = "INSERT INTO Address(street,city,stateID,zipcode,countryID,personID) VALUES " +
"(?, ?,(SELECT stateID FROM State WHERE (stateName = ?)),?," +
	"(SELECT countryID FROM Country WHERE (countryName = ?)),(SELECT personID FROM Person WHERE (personCode = ?)))";

PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(addPersonQuery);
			ps.setString(1, personCode);
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
			ps.setString(6, personCode);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {

	Factory.closeResources(ps, conn);
		}
	}
	
	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		Factory.getDriver();
		Connection conn = Factory.getConnection();
String query = "INSERT INTO Email(emailAddress,personID) VALUES (?,(SELECT personID FROM Person WHERE (personCode = ?)))";
PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, personCode);
			ps.executeUpdate();
			ps.close();

			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
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
			//Step 2: instantiate the database connection
			conn = Factory.getConnection();

			//Step 3-4: formulate, prepare, and execute a query
			String query = "SET SQL_SAFE_UPDATES=0";

			ps = conn.prepareStatement(query);
			
			 ps.executeUpdate();
			
			
			ps.close();
			query = "DELETE  FROM PortfolioAsset";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();
			
			query = "DELETE FROM Asset";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();
			
			
			//TODO delete from portfolios
			
			

			//close the connection
			conn.close();
		} catch(Exception e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			//Step 5: Close your resources!
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
		//TODO remove from portfolio asset and portfolio also 
		Connection conn = null;
		PreparedStatement ps = null;
		
		
		try {
			//Step 2: instantiate the database connection
			conn = Factory.getConnection();

			//Step 3-4: formulate, prepare, and execute a query
			String query = "SET SQL_SAFE_UPDATES=0";

			ps = conn.prepareStatement(query);
			
			ps.executeUpdate();
			
			//close resources when no longer needed
			
			ps.close();
			//TODO see if this can be done better
			
			
			query = "DELETE FROM PortfolioAsset WHERE assetID = (SELECT assetID FROM Asset WHERE assetCode = ?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, assetCode);
			ps.executeUpdate();
			ps.close();
			
			
			query = "DELETE FROM Asset WHERE assetCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, assetCode);
			 ps.executeUpdate();
			
			ps.close();
			
			
			//close the connection
			conn.close();
		} catch(Exception e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			//Step 5: Close your resources!
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
		
		//create new deposit here?
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		
		try {
			//Step 2: instantiate the database connection
			conn = Factory.getConnection();
			//Step 3-4: formulate, prepare, and execute a query
			String query =  "INSERT INTO Asset(assetCode,label,assetType,baseRate) values (?,?,?,?)";
			
			ps = conn.prepareStatement(query);
			ps.setString(1, assetCode);
			ps.setString(2, label);
			ps.setString(3, "D");
			ps.setDouble(4, apr);
			
			ps.executeUpdate();

			
			
			//close resources when no longer needed
		
			ps.close();

			//close the connection
			conn.close();
		} catch(Exception e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			//Step 5: Close your resources!
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
		
		//create new investment here?
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			//Step 2: instantiate the database connection
			conn = Factory.getConnection();

			//Step 3-4: formulate, prepare, and execute a query
			String query =  "INSERT INTO Asset(assetCode,label,assetType,baseRate,quarterlyDividend, omega, investmentValue) values (?,?,?,?,?,?,?)";
			
			ps = conn.prepareStatement(query);
			ps.setString(1, assetCode);
			ps.setString(2, label);
			ps.setString(3, "P");
			ps.setDouble(4, baseRateOfReturn);
			ps.setDouble(5,quarterlyDividend);
			ps.setDouble(6,baseOmega);
			ps.setDouble(7,totalValue);
			
			ps.executeUpdate();

			
			
			//close resources when no longer needed
			
			ps.close();

			//close the connection
			conn.close();
		} catch(Exception e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			//Step 5: Close your resources!
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
		
		//create new stock here?
		//Stock st = new  Stock(params)
		
		Connection conn = null;
		PreparedStatement ps = null;
	
		
		try {
			//Step 2: instantiate the database connection
			conn = Factory.getConnection();

			//Step 3-4: formulate, prepare, and execute a query
			String query =  "INSERT INTO Asset(assetCode,label,assetType,baseRate,quarterlyDividend, beta, symbol,sharePrice) values (?,?,?,?,?,?,?,?)";
			
			ps = conn.prepareStatement(query);
			ps.setString(1, assetCode);
			ps.setString(2, label);
			ps.setString(3, "S");
			ps.setDouble(4, baseRateOfReturn);
			ps.setDouble(5,quarterlyDividend);
			ps.setDouble(6,beta);
			ps.setString(7,stockSymbol);
			ps.setDouble(8,sharePrice);
			
			 ps.executeUpdate();

			
			
			//close resources when no longer needed
			//rs.close();
			ps.close();

			//close the connection
			conn.close();
		} catch(Exception e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			//Step 5: Close your resources!
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
			//Step 2: instantiate the database connection
			conn = Factory.getConnection();

			//Step 3-4: formulate, prepare, and execute a query
			String query = "SET SQL_SAFE_UPDATES=0";

			ps = conn.prepareStatement(query);
			
			ps.executeUpdate();
			//TODO I don't think anything has to be done with the ResultSet. Confirm? ***********************
			
			//close resources when no longer needed
			
			ps.close();
			
			query = "DELETE FROM PortfolioAsset";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();
			
			query = "DELETE FROM Portfolio";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();

			//close the connection
			conn.close();
		} catch(Exception e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			//Step 5: Close your resources!
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

			String query = "SET SQL_SAFE_UPDATES=0";

			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();
			
			query = "DELETE FROM PortfolioAsset WHERE portfolioAssetID IN (SELECT portfolioAssetID FROM (SELECT * FROM PortfolioAsset) AS portAsset WHERE portfolioID IN ((SELECT portfolioID FROM Portfolio WHERE portfolioCode = ?)))";
			ps = conn.prepareStatement(query);
			ps.setString(1, portfolioCode);
			ps.executeUpdate();
			ps.close();
			
			query = "DELETE FROM Portfolio WHERE portfolioCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, portfolioCode);
			ps.executeUpdate();
			ps.close();
			
			conn.close();
			
		
		} catch(Exception e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			//Step 5: Close your resources!
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
			//Step 2: instantiate the database connection
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);

			//Step 3-4: formulate, prepare, and execute a query
			String query = "INSERT INTO Portfolio(portfolioCode, ownerID, managerID, beneficiaryID) VALUES (?, SELECT personID FROM Person WHERE (personCode = ?)), (SELECT personID WHERE (personCode = ?)), (select personID FROM Person WHERE (personCode = ?)))";

			ps = conn.prepareStatement(query);
			ps.setString(1, portfolioCode);
			ps.setString(2, ownerCode);
			ps.setString(3, managerCode);
			ps.setString(4, beneficiaryCode);
			
			ps.executeUpdate();

			//close resources when no longer needed
			
			ps.close();

			//close the connection
			conn.close();
		} catch(Exception e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			//Step 5: Close your resources!
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
		
		//PortfolioAsset pa = new PortfolioAsset();  ???
		Factory.getDriver();
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			//Step 2: instantiate the database connection
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);

			//Step 3-4: formulate, prepare, and execute a query
			String query = "INSERT INTO PortfolioAsset(assetID, portfolioID, givenValue) VALUES ((SELECT assetID FROM Asset WHERE (assetCode = ?)), (SELECT portfolioID FROM Portfolio where (portfolioCode = ?)), ?)";

			ps = conn.prepareStatement(query);
			ps.setString(1, assetCode);
			ps.setString(2, portfolioCode);
			ps.setDouble(3,  value);
			ps.executeUpdate();
			//TODO use if here? how to do this part?
//			if(rs.next()) {
//				b.setBandId(bandId);
//				b.setName(rs.getString("BandName"));
//			}
			
			//close resources when no longer needed
			
			ps.close();

			//close the connection
			conn.close();
		} catch(Exception e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			//Step 5: Close your resources!
			Factory.closeResources( ps, conn);
		}
	}
	
	
	public ArrayList<Portfolio> getPortfolios(){
		ArrayList<Portfolio> portfolios = new ArrayList<Portfolio>();
		try{
			Factory.getDriver();
			
			Connection conn = Factory.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;

			
			ArrayList<Asset> assets = getAssets();
			ArrayList<Person> persons = getPersons();
			//Iterate through the file parsing Portfolio objects from the lines
			String query = "SELECT * from Portfolio join PortfolioAsset on Portfolio.portfolioID = PortfolioAsset.portfolioID";
			
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			//TODO make sure this only happens once
			while(rs.next()){
				
				//TODO do the 3 queries for owner ID,  manager ID, beneficiary ID
				PreparedStatement psAssets = null;
				ResultSet rsAssets = null;
				
				String queryAsset = "Select assetID, givenValue from PortfolioAsset where portfolioID = ?";
				psAssets = conn.prepareStatement(queryAsset);
				psAssets.setString(1, rs.getString("portfolioID"));
				
				rsAssets = psAssets.executeQuery();
				
				HashMap<String,Double> assetIDList = new HashMap<String,Double>();
				while(rsAssets.next()){
					assetIDList.put(rsAssets.getString("assetID"),rsAssets.getDouble("givenValue"));
				}
				rsAssets.close();
				psAssets.close();
				
				if(rs.getString("beneficiaryID") != null){
					PreparedStatement psO = null;
					ResultSet rsO = null;
					String queryO = "Select personCode from Person where Person.personID = ?";
					psO = conn.prepareStatement(queryO);
					psO.setInt(1, rs.getInt("ownerID"));
					rsO = psO.executeQuery();
					String ownerCode = rsO.getString("personCode");
					rsO.close();
					psO.close();
					
					PreparedStatement psM = null;
					ResultSet rsM = null;
					String queryM = "Select personCode from Person where Person.personID = ?";
					psM = conn.prepareStatement(queryM);
					psM.setInt(1, rs.getInt("managerID"));
					rsM = psM.executeQuery();
					String managerCode = rsM.getString("personCode");
					rsM.close();
					psM.close();
					
					PreparedStatement psB = null;
					ResultSet rsB = null;
					String queryB = "Select personCode from Person where Person.personID = ?";
					psB = conn.prepareStatement(queryB);
					psB.setInt(1, rs.getInt("beneficiaryID"));
					rsB = psB.executeQuery();
					String beneficiaryCode = rsB.getString("personCode");
					rsB.close();
					psB.close();
					
					
					 portfolios.add(new Portfolio(rs.getString("portfolioCode"), searchPerson(ownerCode,persons), (Broker) searchPerson(managerCode,persons), searchPerson(beneficiaryCode,persons), searchAssets(assetIDList,assets )));
				}
				else{
					PreparedStatement psO = null;
					
					String queryO = "Select personCode from Person where Person.personID = ?";
					psO = conn.prepareStatement(queryO);
					psO.setInt(1, rs.getInt("ownerID"));
					ResultSet rsO = psO.executeQuery();
					String ownerCode = rsO.getString("personCode");
					rsO.close();
					psO.close();
					
					PreparedStatement psM = null;
					ResultSet rsM = null;
					String queryM = "Select personCode from Person where Person.personID = ?";
					psM = conn.prepareStatement(queryM);
					psM.setInt(1, rs.getInt("managerID"));
					rsM = psM.executeQuery();
					String managerCode = rsM.getString("personCode");
					rsM.close();
					psM.close();
					 portfolios.add(new Portfolio(rs.getString("portfolioCode"), searchPerson(ownerCode,persons), (Broker) searchPerson(managerCode,persons),  searchAssets(assetIDList,assets )));
				}
				}
			
			
			return portfolios;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * This method parses a portfolio object from a line of text
	 * @param line
	 * @return
	 */
//	public Portfolio parsePortfolio(String line, ArrayList<Asset> assets, ArrayList<Person> persons){
//		Portfolio p;
//
//		
//	}
	
	/**
	 * This method searches a given list of persons to find the specified person
	 * @param id, the person's id
	 * @param persons, the ArrayList of persons
	 * @return the person matching the id, if there is one. Else, returns null
	 */
	public Person searchPerson(String id, ArrayList<Person> persons){
		Person subject;
		System.out.println("person wanted " + id);
		for(Person p: persons){
			System.out.println("person code " + p.getCode());
			if(p.getCode().equalsIgnoreCase(id)){
				System.out.println("person code" + p.getCode());
				subject=p;
				return subject;
			}
		}
		System.out.println("Something broker");
		return null;
	}
	
	/**
	 * Searches for specific assets in a given ArrayList of assets
	 * @param idList, list of ids to be searched for
	 * @param assets, arraylist of assets to search through
	 * @return assetList, a HashMap of assets to values given
	 */
	public HashMap<Asset, Double> searchAssets(HashMap<String,Double> idList, ArrayList<Asset> assets){
		HashMap<Asset,Double> assetList = new HashMap<Asset,Double>();
		for(String id: idList.keySet()){
			if(!id.equalsIgnoreCase("")) {
				double value = idList.get(id);
				id = id.split(":")[0];
			
				for(Asset a: assets){
					if(a.getCode().equalsIgnoreCase(id)){
						assetList.put(a,value);
					}
				}
			}
		}
		return assetList;
	}
	
	//TODO Make sure these are correct, the getters for things
	/**
	 * gets all the Assets currently in the database
	 * @return ArrayList<Asset> 
	 */
	public ArrayList<Asset> getAssets(){
		Factory.getDriver();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Asset> allAssets = new ArrayList<Asset>();
		
		try {
			//Step 2: instantiate the database connection
			conn = Factory.getConnection();

			//Step 3-4: formulate, prepare, and execute a query
			String query = "Select * from Asset";

			ps = conn.prepareStatement(query);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				if(rs.getString("assetType").equalsIgnoreCase("D")){
					Deposit dep = new Deposit(rs.getString("assetCode"), rs.getString("label"), rs.getString("assetType"), rs.getDouble("baseRate"));
					allAssets.add(dep);
				}
				else if(rs.getString("assetType").equalsIgnoreCase("S")){
					Stock sto = new Stock(rs.getString("assetCode"), rs.getString("label"), rs.getString("assetType"),rs.getDouble("quarterlyDividend"), rs.getDouble("baseRate"), rs.getDouble("sharePrice"),rs.getString("symbol"),rs.getDouble("beta"));
					allAssets.add(sto);
				}
				else{
					Investment inv = new Investment(rs.getString("assetCode"), rs.getString("label"), rs.getString("assetType"),rs.getDouble("quarterlyDividend"), rs.getDouble("baseRate"),rs.getDouble("omega"),rs.getDouble("investmentValue"));
					allAssets.add(inv);
				}
			}
			

			
			
			//close resources when no longer needed
			rs.close();
			ps.close();

			//close the connection
			conn.close();
			return allAssets;
		} catch(Exception e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			
			throw new RuntimeException(e);
			
		} finally {
			//Step 5: Close your resources!
			Factory.closeResources(rs, ps, conn);
			
		}
	}
		//TODO Fix variable names
		//TODO join addresses, email, person
		public ArrayList<Person> getPersons(){
			Factory.getDriver();
			
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<Person> allPersons = new ArrayList<Person>();
			
			try {
				//Step 2: instantiate the database connection
				conn = Factory.getConnection();
				//Step 3-4: formulate, prepare, and execute a query
				
				String query = "Select * from Person";

				ps = conn.prepareStatement(query);
				
				rs = ps.executeQuery();
				//TODO figure out how to get the address and emails
				while(rs.next()){
					if(rs.getString("brokerType") != null){
						ArrayList<String> email = new ArrayList<String>();
						Address ad = null;
						ResultSet rsEmail = null;
						String emailQuery = "SELECT * from Email WHERE 'personID' = ?";
						PreparedStatement psE = conn.prepareStatement(emailQuery);
						psE.setString(1, rs.getString("personID"));
						
						rsEmail = psE.executeQuery();
						while(rsEmail.next()){
							email.add(rsEmail.getString("emailAddress"));
						}
						rsEmail.close();
						psE.close();
						
						
						ResultSet rsAddress = null;
						String addressQuery = "Select * from Address  join State on Address.stateID = State.stateID join Country on Address.countryID = Country.countryID Where Address.personID = ?";
						PreparedStatement psA = conn.prepareStatement(addressQuery);
						psA.setString(1, rs.getString("personID"));
						
						rsAddress = psA.executeQuery();
						if(rsAddress.next()){
							 ad = new Address(rsAddress.getString("street"), rsAddress.getString("city"), rsAddress.getString("stateName"),rsAddress.getString("zipcode"), rsAddress.getString("countryName"));
						}
						rsAddress.close();
						psA.close();
						Broker b = new Broker(rs.getString("personCode"),rs.getString("brokerType").charAt(0),rs.getString("secID"),rs.getString("lastName"),rs.getString("firstName"),ad,email);
						allPersons.add(b);
					}
					else{
						ArrayList<String> email = new ArrayList<String>();
						Address ad = null;
						ResultSet rsEmail = null;
						String emailQuery = "SELECT * from Email WHERE 'personID' = ?";
						PreparedStatement psE = conn.prepareStatement(emailQuery);
						psE.setString(1, rs.getString("personID"));
						
						rsEmail = psE.executeQuery();
						while(rsEmail.next()){
							email.add(rsEmail.getString("emailAddress"));
						}
						rsEmail.close();
						psE.close();
						
						
						ResultSet rsAddress = null;
						String addressQuery = "Select * from Address  join State on Address.stateID = State.stateID join Country on Address.countryID = Country.countryID Where Address.personID = ?";
						PreparedStatement psA = conn.prepareStatement(addressQuery);
						psA.setString(1, rs.getString("personID"));
						
						rsAddress = psA.executeQuery();
						if(rsAddress.next()){
							 ad = new Address(rsAddress.getString("street"), rsAddress.getString("city"), rsAddress.getString("stateName"),rsAddress.getString("zipcode"), rsAddress.getString("countryName"));
						}
						rsAddress.close();
						psA.close();
						
						Person p = new Person(rs.getString("personCode"),rs.getString("lastName"),rs.getString("firstName"),ad,email);
						allPersons.add(p);
					}
						
				}
								
				//close resources when no longer needed
				rs.close();
				ps.close();

				//close the connection
				conn.close();
				return allPersons;
			} catch(Exception e) {
				System.out.println("SQLException: ");
				e.printStackTrace();
				
				throw new RuntimeException(e);
				
			} finally {
				//Step 5: Close your resources!
				Factory.closeResources(rs, ps, conn);
				
			}
		}
	}
