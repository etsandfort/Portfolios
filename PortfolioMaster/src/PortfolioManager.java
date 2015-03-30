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
	private PortfolioData input = new PortfolioData();

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
		long cTime = System.currentTimeMillis();
		PortfolioManager manager = new PortfolioManager();
		//PortfolioData pd = new PortfolioData();
		//pd.removeAllAssets();
		//PortfolioData.removeAsset("MHIA32");
		ArrayList<Portfolio> allPortfolios = manager.getInput().getPortfolios();
		ReportMaker report = new ReportMaker(allPortfolios);
		report.printSummaryReport();
		report.printDetailedReport();
		
//		pd.addDepositAccount("TEST!", "THISTEST", 2.52);
//		System.out.println("Inserted Deposit Account!!");
//		pd.addStock("LAM", "TESTTEST", 125.0, 5.0, 6.0, "AS", 45.0);
//		System.out.println("Inserted stock !!");
//		pd.addPrivateInvestment("AAAS", "te", 65.5, 45.5, 78.5, 5550.0);
//		System.out.println("Inserted private Investment");
		long eTime = System.currentTimeMillis();
		long time = eTime - cTime;
		System.out.println("It took  " +time  + " milliseconds");
	}
}