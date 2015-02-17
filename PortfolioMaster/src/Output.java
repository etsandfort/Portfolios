import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Output.java
 * This class is responsible for putting out xml and json files of assets and people
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 1.0
 */
public class Output {
	//Data members
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	XStream xmlOut = new XStream();
	
	public void printSummaryReport(){
	//TODO	
	}
	
	public void printDetailedReport(){
	//TODO	
	}
	/**
	 * Outputs a json file of people from a list of people
	 * @param fileName the output file name
	 * @param people the arraylist of people
	 */
	public void outputJsonPersons(String fileName, ArrayList<Person> people ){
		File fileOut = new File(fileName);
		try {
			FileWriter fw = new FileWriter(fileOut);
			fw.write("{\n\"Persons\": \n");
			fw.write(gson.toJson(people));
			fw.write("}");
			fw.close();
		} catch (IOException e) {

		System.out.println("Unable to output Json for Persons");
		}
	}
	
	/**
	 * Outputs a json file of assets from a list of assets
	 * @param fileName the output file name
	 * @param assets the arraylist of assets
	 */
	public void outputJsonAssets(String fileName, ArrayList<Asset> assets ){
		File fileOut = new File(fileName);
		try {
			FileWriter fw = new FileWriter(fileOut);
			fw.write("{\n\"Assets\": \n");
			fw.write(gson.toJson(assets));
			fw.write("}");
			fw.close();
		} catch (IOException e) {
		System.out.println("Unable to output JSON for Assets");
		}
	}
	
	/**
	 * Outputs an xml file of people from a list of people
	 * @param fileName the output file name
	 * @param people the arraylist of people
	 */
	public void outputXmlPersons(String fileName, ArrayList<Person> people ){
		File fileOut = new File(fileName);
		try {
			FileWriter fw = new FileWriter(fileOut);
			xmlOut.alias("person", Broker.class);
			xmlOut.alias("person", Person.class);
			
			fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");	
			fw.write(xmlOut.toXML(people).replace("list", "Persons"));
			fw.close();
		} catch (IOException e) {
		System.out.println("Unable to output xml for Persons");
		}
	}
	
	/**
	 * Outputs an xml file of assets from a list of assets
	 * @param fileName the output file name
	 * @param assets the arraylist of assets
	 */
	public void outputXmlAssets(String fileName, ArrayList<Asset> assets ){
		File fileOut = new File(fileName);
		try {
			FileWriter fw = new FileWriter(fileOut);
			xmlOut.alias("asset", Deposit.class);
			xmlOut.alias("asset", Investment.class);
			xmlOut.alias("asset", Stock.class);
		
			fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");	
			fw.write(xmlOut.toXML(assets).replace("list", "Assets"));
			fw.close();
		} catch (IOException e) {
		System.out.println("Unable to output xml for  Assets");
		}
	}

}