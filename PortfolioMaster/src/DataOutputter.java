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
	private DataFormatter format = new DataFormatter();

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

	/**
	 * Obtains the data output format
	 * @return the format
	 */
	public DataFormatter getFormat() {
		return format;
	}

	/**Sets the new data output format
	 * @param format the format to set
	 */
	public void setFormat(DataFormatter format) {
		this.format = format;
	}

	/**
	 * This is the main method, it runs the program
	 * @param args the set of arguments provided at runtime
	 */
	public static void main(String args[]){
		DataOutputter dataOut = new DataOutputter();
				
		//Converting the input files to .json and .xml output files
//		do.getFormat().outputJsonAssets("data/Assets.json", dc.getInput().readAssets());
//		do.getFormat().outputXmlAssets("data/Assets.xml", dc.getInput().readAssets());
//		do.getFormat().outputJsonPersons("data/Persons.json", dc.getInput().readPersons());
//		do.getFormat().outputXmlPersons("data/Persons.xml", dc.getInput().readPersons());
		dataOut.getFormat().printSummaryReport();
		dataOut.getFormat().printDetailedReport();
	}
}