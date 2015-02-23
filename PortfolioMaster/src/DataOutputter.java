import java.util.ArrayList;

/**
 * DataOutputter.java
 * RAIK 184H
 * This is the application class for the database, it contains the main method
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 1.0
 */
public class DataOutputter {
	//Data member
	private DataReader input = new DataReader();

	/**
	 * Obtains the input
	 * @return the input
	 */
	public DataReader getInput() {
		return input;
	}
	
	/**
	 * This is the main method, it runs the program
	 * @param args the set of arguments provided at runtime
	 */
	public static void main(String args[]){
		long startTime = System.nanoTime();
		DataOutputter dataOut = new DataOutputter();
		ArrayList<Portfolio> allPortfolios = dataOut.getInput().readPortfolios();
		ReportMaker report = new ReportMaker(allPortfolios);
		report.printSummaryReport();
		report.printDetailedReport();
		long endTime = System.nanoTime();
		System.out.println("Took "+(endTime - startTime)/1000000 + " ms"); 
	}
}