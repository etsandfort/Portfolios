import java.util.ArrayList;

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
	private DataReader input = new DataReader();

	/**
	 * Obtains the input
	 * @return input, a DataReader object
	 */
	public DataReader getInput(){
		return input;
	}
	
	/**
	 * This is the main method, it runs the program
	 * @param args the set of arguments provided at runtime
	 */
	public static void main(String args[]){
		PortfolioManager manager = new PortfolioManager();
		ArrayList<Portfolio> allPortfolios = manager.getInput().readPortfolios();
		ReportMaker report = new ReportMaker(allPortfolios);
		report.printSummaryReport();
		report.printDetailedReport();
	}
}