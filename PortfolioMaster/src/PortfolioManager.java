import org.apache.log4j.Logger;

import packagePortfolio.Portfolio;
import packagePortfolio.PortfolioAsset;
import packagePortfolio.ReportMaker;
import packagePortfolio.PortfolioComparator;

import com.sdb.PortfolioData;

import java.util.List;

/**
 * PortfolioManager.java
 * RAIK 184H
 * This is the application class for the database, it contains the main method
 * which runs the financial management system.
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 5.0
 */
public class PortfolioManager{
	//Data members
	private PortfolioData input;
	static org.apache.log4j.Logger log = Logger.getLogger(PortfolioManager.class.getName());
	
	/**
	 * Constructor for PortfolioManager
	 */
	public PortfolioManager(){
		input = new PortfolioData();
	}
	
	/**
	 * Obtains the input
	 * @return input, a DataReader object
	 */
	public PortfolioData getInput(){
		return input;
	}
	
	/**
	 * Prints the Summary Report of all portfolios
	 */
	public void printSummary(){
		List<Portfolio> allPortfolios = getInput().getPortfolios();
		log.info("Portfolios retrieved");
		for(Portfolio p : allPortfolios){
			p.compileData();
		}
		ReportMaker report = new ReportMaker(allPortfolios);
		report.printSummaryReport();
		allPortfolios.clear();
	}

	/**
	 * Prints the detailed report of each portfolio
	 */
	public void printDetailed(){
		List<Portfolio> allPortfolios = getInput().getPortfolios();
		log.info("Portfolios retrieved");
		
		for(Portfolio p : allPortfolios){
			p.compileData();
		}
		ReportMaker report = new ReportMaker(allPortfolios);
		report.printDetailedReport();
		allPortfolios.clear();
	}
	
	/**
	 * This is the main method; it runs the program
	 * @param args the set of arguments provided at runtime
	 */
	public static void main(String args[]) {
		log.info("Getting portfolios");
		PortfolioManager manager = new PortfolioManager();
		manager.printSummary();
		manager.printDetailed();
		log.info("Program finished");		
	}
}