import org.apache.log4j.Logger;

import packagePortfolio.Portfolio;
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
//		GenericList<Portfolio> allPortfolios = getInput().getPortfolios(PortfolioComparator.ownerComparator());
		List<Portfolio> allPortfolios = getInput().getPortfolios(PortfolioComparator.ownerComparator());

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
//		GenericList<Portfolio> allPortfolios = getInput().getPortfolios(PortfolioComparator.valueComparator());
		List<Portfolio> allPortfolios = getInput().getPortfolios(PortfolioComparator.valueComparator());

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
//		GenericList<Portfolio> allPortfolios = getInput().getPortfolios(PortfolioComparator.managerComparator());
		List<Portfolio> allPortfolios = getInput().getPortfolios(PortfolioComparator.managerComparator());

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
		log.info("Getting portfolios");
		
		PortfolioManager manager = new PortfolioManager();
		manager.listByOwner();
		manager.listByValue();
		manager.listByBrokerType();

		log.info("Program finished.");		
	}
}