/**
 * DataConverter.java
 * RAIK 184H
 * This is the application class for the database, it contains the main method
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 1.0
 */
public class DataConverter {
	//Data members
	private Input input = new Input();
	private Output output = new Output();

	/**
	 * Obtains the input
	 * @return the input
	 */
	public Input getInput() {
		return input;
	}

	/**Sets the new input
	 * @param input the input to set
	 */
	public void setInput(Input input) {
		this.input = input;
	}

	/**
	 * Obtains the output
	 * @return the output
	 */
	public Output getOutput() {
		return output;
	}

	/**Sets the new output
	 * @param output the output to set
	 */
	public void setOutput(Output output) {
		this.output = output;
	}

	/**
	 * This is the main method, it runs the program
	 * @param args the set of arguments provided at runtime
	 */
	public static void main(String args[]){
		DataConverter dc = new DataConverter();
		//Converting the input files to .json and .xml output files
		dc.getOutput().outputJsonAssets("data/Assets.json", dc.getInput().readAssets());
		dc.getOutput().outputXmlAssets("data/Assets.xml", dc.getInput().readAssets());
		dc.getOutput().outputJsonPersons("data/Persons.json", dc.getInput().readPersons());
		dc.getOutput().outputXmlPersons("data/Persons.xml", dc.getInput().readPersons());
	}
}
