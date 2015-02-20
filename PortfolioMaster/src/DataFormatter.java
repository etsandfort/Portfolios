import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Output.java
 * This class is responsible for outputting xml and json files of assets
 * and people, as well as a text file containing a portfolio summary report 
 * and a detailed report of each portfolio.
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 1.0
 */
public class DataFormatter {
	// Data members
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	XStream xmlOut = new XStream();
	ArrayList<Portfolio> portfolio;
//	ReportCalculation reportCalc = new ReportCalculation(portfolio);
	
	/**
	 * This method prints the Portfolio Summary Report.
	 */
	public void printSummaryReport(){
		System.out.println("Portfolio Summary Report");
		printThickLine();
		
		String nameOwner = "";
		String nameManager = "";
	
		// Prints code, owner, manager, fees, commissions, weighted risk, return, total
		System.out.printf("%-12s %-25s %-25s %-15s %-15s %-20s %-15s %-15s\n", "Portfolio",
				"Owner", "Manager", "Fees", "Commissions", "Weighted Risk", "Return", "Total");
//		for(int i = 0; i < portfolio.size(); i++){
		
//			nameOwner = portfolio.get(i).getOwner().getLastName() + ", " + portfolio.get(i).getOwner().getFirstName();
//			nameManager = portfolio.get(i).getManager().getLastName() + ", " + portfolio.get(i).getManager().getFirstName();
//			
//			System.out.printf("%-12s %-25s %-25d %-15.2d %-15.2d %-20.2d %-15.2d %-15.2d\n", 
//					portfolio.get(i).getCode(), nameOwner, nameManager, '$' + portfolio.get(i).getTotalFees(), 
//					portfolio.get(i).getCommission(), portfolio.get(i).getWeightedRisk(),
//					portfolio.get(i).getReturns(), portfolio.get(i).getTotalValue());
//					)
//		}
		
		// prints line under numerical values, acting as a divider between individual values and totals
		printThinLine(150);
		// print totals for fees, commissions, return, total
//		System.out.printf("%40s %-11s %-15d %-15d %20s %-15d %-15d\n", "", "Totals:", reportCalc.getTotalFees(), reportCalc.getTotalCommission(), "", reportCalc.getTotalReturn(), reportCalc.getTotalValue());
	}
	
	/**
	 * This method prints the Detailed Report for each portfolio, in order alphabetically by Owner's Last Name
	 */
	public void printDetailedReport(){
		System.out.println("Portfolio Details");
		printThickLine();
		
		String nameOwner = "";
		String nameManager = "";
 		String nameBeneficiary = "none";
 		
		//for(int i = 0; i < portfolio.size(); i++){
			// nameOwner = portfolio.get(i).getOwner().getLastName() + ", " + portfolio.get(i).getOwner().getFirstName();
			// nameManager = portfolio.get(i).getManager().getLastName() + ", " + portfolio.get(i).getManager().getFirstName();
			// if(portfolio.get(i).getBeneficiary() != null){
			// 	nameBeneficiary = portfolio.get(i).getBeneficiary().getLastName() + ", " + portfolio.get(i).getBeneficiary().getFirstName();
			// }
			//System.out.println("Portfolio" + portfolio.get(i).getCode());
			printThinLine(40);
			
			System.out.printf("%-15s %-25s\n", "Owner:", nameOwner);
			System.out.printf("%-15s %-25s\n", "Manager:", nameManager);
			System.out.printf("%-15s %-25s\n", "Beneficiary:", nameBeneficiary);
			System.out.println("Assets");
			System.out.printf("%-10s %-43s %20s %-5s %17s %15s\n", "Code", "Asset", "Return Rate", "Risk", "Annual Return", "Value");
			
//			int numAssets = portfolio.get(i).getAssets().size();
//			
//			for(int j = 0; j < numAssets; j++)
//			System.out.printf("%-10s %-43s %20.2d %-5.2d %-1s %16.2d %-1s %14.2d\n", portfolio.get(i).getCode(), 
//					portfolio.get(i).getAssets().get(j).getLabel(), portfolio.get(i).getAssets().get(j).getRate() + "%", 
//					portfolio.get(i).getAssets().get(j).getRisk(), "$", portfolio.get(i).getAssets().get(j).getAnnualReturn(), 
//					"$", portfolio.get(i).getAssets().get(j).getValue());
//			}
//			System.out.printf("%48s %-7s %-15d %-15d %20d\n", "", "Totals:", portfolio.get(i).getWeightedRisk(), portfolio.get(i).getTotalAnnualReturn(), portfolio.get(i).getTotalValue());
	}
	
	/**
	 * Prints a thick line; used to better format portfolio reports
	 */
	public static void printThickLine(){
		for(int i = 0; i < 150; i++){
			if(i != 149){
				System.out.print('=');				
			}
			else{
				System.out.println('=');
			}
		}
	}
	
	/**
	 * Prints a hyphenated line; used to better format portfolio reports
	 */
	public static void printThinLine(int lineLength){
		for(int i = 0; i < lineLength; i++){
			if(i < lineLength - 90){ // print blank spaces because can't sum codes, owners or managers
				System.out.print(" ");
			}
			else if(i != (lineLength - 1)){
				System.out.print('-');				
			}
			else{
				System.out.println('-');
			}
		}
	}
	
	/**
	 * Outputs a json file of people from a list of people
	 * @param fileName the output file name
	 * @param people the ArrayList of people
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
	 * @param assets the ArrayList of assets
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
	 * @param people the ArrayList of people
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
	 * @param assets the ArrayList of assets
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