import java.sql.Connection;
import java.sql.DriverManager;

import com.mysql.jdbc.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */
public class PortfolioData {

	/**
 * Method that removes every person record from the database
 */
/*public static void removeAllPersons() {
getDriver();
getConnection();
String query1 = "DELETE * FROM PortfolioAsset";
String query2 = "DELETE * FROM Portfolio";
String query3 = "DELETE * FROM Email";	
String query4 = "DELETE * FROM Address";
String query5 = "DELETE * FROM Person";	
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query1);
			ps.executeUpdate();
			ps = conn.prepareStatement(query2);
			ps.executeUpdate();
			ps = conn.prepareStatement(query3);
			ps.executeUpdate();
			ps = conn.prepareStatement(query4);
			ps.executeUpdate();
			ps = conn.prepareStatement(query5);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {

			closeConnection();
		}
}

*//**
	 * Removes the person record from the database corresponding to the
	 * provided <code>personCode</code>
	 * @param personCode
	 *//*
	public static void removePerson(String personCode) {
getDriver();
getConnection();

String query1= "DELETE FROM PortfolioAsset WHERE portfolioAssetID IN 
(SELECT portfolioAssetID FROM (SELECT * FROM PortfolioAsset) AS p WHERE portfolioID IN 
	((SELECT portfolioID FROM Portfolio WHERE ownerID IN (
    SELECT personID FROM Person WHERE personCode = ?) OR
     managerID IN (
    SELECT personID FROM Person WHERE personCode = ?))))";
String query2= "DELETE FROM Portfolio WHERE 
	(ownerID = (SELECT personID FROM Person WHERE personCode = ?) OR managerID = (SELECT personID FROM Person WHERE personCode = ?))";
String query3="DELETE FROM Email WHERE personID = (SELECT personID FROM Person WHERE personCode = ?)";
String query4="DELETE FROM Address WHERE personID = (SELECT personID FROM Person WHERE personCode = ?)";
String query5="DELETE FROM Person WHERE personID = (SELECT personID FROM (SELECT * FROM Person) AS p WHERE personCode = ?)";

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query1);
			ps.setString(1, personCode);
			ps.executeUpdate();

			ps = conn.prepareStatement(query2);
			ps.setString(1, personCode);
			ps.executeUpdate();

			ps = conn.prepareStatement(query3);
			ps.setString(1, personCode);
			ps.executeUpdate();

			ps = conn.prepareStatement(query4);
			ps.setString(1, personCode);
			ps.executeUpdate();

			ps = conn.prepareStatement(query5);
			ps.setString(1, personCode);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {

	closeConnection();
		}

	}

*//**
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
	 *//*
	public static void addPerson(String personCode, String firstName, String lastName, String street, 
		String city, String state, String zip, String country, String brokerType, String secBrokerId) {
getDriver();
getConnection();
String addPersonQuery= "INSERT INTO Person(personCode, lastName, firstName, secID, brokerType) VALUES (?, ?, ?, ?, ?);"
String addStateQuery = "INSERT IGNORE INTO State(stateName) VALUES(?)";
String addCountryQuery = "INSERT IGNORE INTO Country(countryName) VALUES(?)";
String addAddressQuery = "INSERT INTO Address(street,city,stateID,zipcode,countryID,personID) VALUES
(?, ?,(SELECT stateID FROM State WHERE (stateName = ?)),?,
	(SELECT countryID FROM Country WHERE (countryName = ?)),(SELECT personID FROM Person WHERE (personCode = ?)))";

PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(addPersonQuery);
			ps.setString(1, personCode);
			ps.setString(2, lastName);
			ps.setString(3, firstName);
			ps.setString(4, secBrokerId);
			ps.setString(5, brokerType);
			ps.executeUpdate();

			ps = conn.prepareStatement(addStateQuery);
			ps.setString(1, state);
			ps.executeUpdate();

			ps = conn.prepareStatement(addCountryQuery);
			ps.setString(1, country);
			ps.executeUpdate();

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

	closeConnection();
		}
	}
	
	*//**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * @param personCode
	 * @param email
	 *//*
	public static void addEmail(String personCode, String email) {
getDriver();
getConnection();
String query = "INSERT INTO Email(emailAddress,personID) VALUES (?,(SELECT personID FROM Person WHERE (personCode = ?)))";
PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(addPersonQuery);
			ps.setString(1, email);
			ps.setString(2, personCode);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {

	closeConnection();
		}
	}

	*//**
	 * Removes all asset records from the database
	 *//*
	public static void removeAllAssets() {Factory.getDriver();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			//Step 2: instantiate the database connection
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);

			//Step 3-4: formulate, prepare, and execute a query
			String query = "SET SQL_SAFE_UPDATES=0";

			ps = conn.prepareStatement(query);
			
			rs = ps.executeQuery();
			//TODO I don't think anything has to be done with the ResultSet. Confirm? ***********************
			
			//close resources when no longer needed
			rs.close();
			ps.close();
			
			query = "DELETE FROM Asset";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			rs.close();
			ps.close();
			

			//close the connection
			conn.close();
		} catch(Exception e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			//Step 5: Close your resources!
			Factory.closeResources(rs, ps, conn);
		}
	}
*/
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
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);

			//Step 3-4: formulate, prepare, and execute a query
			String query = "SET SQL_SAFE_UPDATES=0";

			ps = conn.prepareStatement(query);
			
			ps.executeUpdate();
			//TODO I don't think anything has to be done with the ResultSet. Confirm? ***********************
			
			//close resources when no longer needed
			
			ps.close();
			
			query = "DELETE FROM Asset WHERE assetCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, assetCode);
			 ps.executeUpdate();
			
			ps.close();
			
			//TODO delete from portfolioAsset and Portfolio if need be
			

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
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);

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
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);

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
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);

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
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);

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
		ResultSet rs = null;
		
		try {
			//Step 2: instantiate the database connection
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);

			//Step 3-4: formulate, prepare, and execute a query
			String query = "SET SQL_SAFE_UPDATES=0";

			ps = conn.prepareStatement(query);
			rs = ps.executeQuery(); // TODO do nothing?
			
			//close resources TODO should this be here as well as below? Seems weird.
			rs.close();
			ps.close();
			
			query = "DELETE FROM PortfolioAsset WHERE portfolioAssetID IN (SELECT portfolioAssetID FROM (SELECT * FROM PortfolioAsset) AS portAsset WHERE portfolioID IN ((SELECT portfolioID FROM Portfolio WHERE portfolioCode = ?)))";
			ps = conn.prepareStatement(query);
			ps.setString(1, portfolioCode);
			
			rs = ps.executeQuery();
			
			// TODO ditto above closing comment
			rs.close();
			ps.close();
			
			query = "DELETE FROM Portfolio WHERE portfolioCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, portfolioCode);
			
			rs = ps.executeQuery();
		
			rs.close();
			ps.close();

			//close the connection
			conn.close();
		} catch(Exception e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			//Step 5: Close your resources!
			Factory.closeResources(rs, ps, conn);
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
		ResultSet rs = null;
		
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
			
			rs = ps.executeQuery();

			//close resources when no longer needed
			rs.close();
			ps.close();

			//close the connection
			conn.close();
		} catch(Exception e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			//Step 5: Close your resources!
			Factory.closeResources(rs, ps, conn);
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
		ResultSet rs = null;
		
		try {
			//Step 2: instantiate the database connection
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);

			//Step 3-4: formulate, prepare, and execute a query
			String query = "INSERT INTO PortfolioAsset(assetID, portfolioID, givenValue) VALUES ((SELECT assetID FROM Asset WHERE (assetCode = ?)), (SELECT portfolioID FROM Portfolio where (portfolioCode = ?)), ?)";

			ps = conn.prepareStatement(query);
			ps.setString(1, assetCode);
			ps.setString(2, portfolioCode);
			ps.setDouble(3,  value);
			rs = ps.executeQuery();
			//TODO use if here? how to do this part?
//			if(rs.next()) {
//				b.setBandId(bandId);
//				b.setName(rs.getString("BandName"));
//			}
			
			//close resources when no longer needed
			rs.close();
			ps.close();

			//close the connection
			conn.close();
		} catch(Exception e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			//Step 5: Close your resources!
			Factory.closeResources(rs, ps, conn);
		}
	}
	
	//TODO Make sure these are corrrect, the getters for things
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
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);

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
			
//			if(rs.next()){
//				dep = new Deposit(rs.getString("assetCode"), rs.getString("label"), rs.getString("assetType"), rs.getDouble("baseRate"));
//				System.out.println("Here is the information for the given deposit\n");
//				System.out.println("the asset code is " + dep.getCode() + "\n");
//				System.out.println("the label is " + dep.getLabel() + "\n");
//				System.out.println("the assetType is " + dep.getType() + "\n");
//				System.out.println("The apr is " + dep.getRiskValue() + "\n");
//			}
			//TODO use if here? how to do this part?
//			if(rs.next()) {
//				b.setBandId(bandId);
//				b.setName(rs.getString("BandName"));
//			}
			
			
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
		
		public ArrayList<Person> getPersons(){
			Factory.getDriver();
			
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<Person> allPersons = new ArrayList<Person>();
			
			try {
				//Step 2: instantiate the database connection
				conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);

				//Step 3-4: formulate, prepare, and execute a query
				String query = "Select * from Person";

				ps = conn.prepareStatement(query);
				
				rs = ps.executeQuery();
				//TODO figure out how to get the address and emails
				while(rs.next()){
					if(rs.getString("brokerType") != null){
						ArrayList<String> email = new ArrayList<String>();
						Address ad = null;
						Broker bPerson = new Broker(rs.getString("personCode"),rs.getString("brokerType").charAt(0),rs.getString("secID"),rs.getString("lastName"),rs.getString("firstName"),ad,email);
						allPersons.add(bPerson);
					}
					else{
						ArrayList<String> email = new ArrayList<String>();
						Address ad = null;
						Person p = new Person(rs.getString("personCode"),rs.getString("lastName"),rs.getString("firstName"),ad,email);
						allPersons.add(p);
					}
						
				}
				
//				
				
				
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
