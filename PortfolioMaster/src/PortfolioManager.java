
import com.sdb.*;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

import packagePortfolio.Portfolio;
import packagePortfolio.ReportMaker;

import com.sdb.PortfolioData;

import org.apache.log4j.*;


/**
 * PortfolioManager.java
 * RAIK 184H
 * This is the application class for the database, it contains the main method
 * which runs the financial management system.
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 3.0
 */
public class PortfolioManager{
	//Data member
	private PortfolioData input = new PortfolioData();
	 //static org.apache.log4j.Logger log = Logger.getLogger(PortfolioManager.class.getName());

	/**
	 * Obtains the input
	 * @return input, a DataReader object
	 */
	public PortfolioData getInput(){
		return input;
	}
	
	/**
	 * This is the main method, it runs the program
	 * @param args the set of arguments provided at runtime
	 */
	public static void main(String args[]){
		//log.info("Getting portfolios");
		
		
		PortfolioManager manager = new PortfolioManager();
		//PortfolioData pd = new PortfolioData();
		//pd.removeAllAssets();
		//PortfolioData.removeAsset("MHIA32");
		//PortfolioData.removePerson("the1");
		//PortfolioData.addPerson("H12", "J", "A", "the", "city", "state", "zip", "country", "E", "SS1");
		//PortfolioData.removePerson("H12");
		//PortfolioData.addEmail("H12", "HelloWorld@");
		//PortfolioData.removeAllAssets();
		//PortfolioData.addDepositAccount("TEST!", "THISTEST", 2.52);
		//PortfolioData.addAsset("AA", "TEST!", 5654);
		//PortfolioData.removeAllPortfolios();
		//PortfolioData.removePortfolio("AA");
		//PortfolioData.addPortfolio("AZ", "the1", "h0h0",null);
//		manager.getInput().selectFromPortfolios();
//		manager.getInput().selectFromPeople();
		//manager.getInput().selectFromAsset();
//		manager.getInput().selectFromPortfolioAsset();
		ArrayList<Portfolio> allPortfolios = manager.getInput().getPortfolios();
		//log.info("Portfolios retrieved");
		ReportMaker report = new ReportMaker(allPortfolios);
		report.printSummaryReport();
		report.printDetailedReport();
		
		
//		System.out.println("Inserted Deposit Account!!");
		//PortfolioData.addStock("LAM", "TESTTEST", 125.0, 5.0, 6.0, "AS", 45.0);
//		System.out.println("Inserted stock !!");
		//PortfolioData.addPrivateInvestment("AAAS", "te", 65.5, 45.5, 78.5, 5550.0);
//		System.out.println("Inserted private Investment");
		
		
		
	}
}