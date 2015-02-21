import java.util.ArrayList;

/**
 * DataOutputter.java
 * RAIK 184H
 * This is the application class for the database, it contains the main method
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 1.0
 */
public class DataOutputter {
	//Data members
	private DataReader input = new DataReader();
//	private ArrayList<Portfolio> allPortfolios;

	/**
	 * Obtains the input
	 * @return the input
	 */
	public DataReader getInput() {
		return input;
	}

	/**Sets the new input
	 * @param input the input to set
	 */
	public void setInput(DataReader input) {
		this.input = input;
	}

//	/**
//	 * Obtains the ArrayList allPortfolios
//	 * @return allPortfolios, an ArrayList of Portfolios
//	 */
//	public ArrayList<Portfolio> getAllPortfolios() {
//		return allPortfolios;
//	}
//
//	/**
//	 * Sets the values of the allPortfolios Portfolio ArrayList
//	 * @param allPortfolios
//	 */
//	public void setAllPortfolios(ArrayList<Portfolio> allPortfolios) {
//		this.allPortfolios = allPortfolios;
//	}
	
	/**
	 * This is the main method, it runs the program
	 * @param args the set of arguments provided at runtime
	 */
	public static void main(String args[]){
		DataOutputter dataOut = new DataOutputter();
		ArrayList<Portfolio> allPortfolios = dataOut.getInput().readPortfolios();
//		dataOut.setAllPortfolios(dataOut.getInput().readPortfolios());
		ReportCalculation reportCalc = new ReportCalculation(allPortfolios);
		reportCalc.printReports();
	}
}