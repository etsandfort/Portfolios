
import com.sdb.*;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

import packagePortfolio.Portfolio;
import packagePortfolio.ReportMaker;
import packagePortfolio.PortfolioList;
import packagePortfolio.PortfolioComparator;
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
	static org.apache.log4j.Logger log = Logger.getLogger(PortfolioManager.class.getName());

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
		log.info("Getting portfolios");
		
		PortfolioManager manager = new PortfolioManager();
		PortfolioList<Portfolio> allPortfolios = manager.getInput().getPortfolios(PortfolioComparator.ownerComparator());
		System.out.println(allPortfolios.toString());
		log.info("Portfolios retrieved");
		
		ReportMaker report = new ReportMaker(allPortfolios);
		report.printSummaryReport();
		//report.printDetailedReport();
		log.info("Program finished.");
	}
}