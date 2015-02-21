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
//	protected final ReportCalculation reportCalc = new ReportCalculation();
	
	
	/**
	 * This method prints the Portfolio Summary Report.
	 */
	public void printSummaryReport(ArrayList<Portfolio> portfolios){
		System.out.println("Portfolio Summary Report");
		printThickLine();
		
		String nameOwner = "";
		String nameManager = "";
	
		// Prints code, owner, manager, fees, commissions, weighted risk, return, total
		System.out.printf("%-12s %-25s %-25s %-15s %-15s %-20s %-15s %-15s\n", "Portfolio",
				"Owner", "Manager", "Fees", "Commissions", "Weighted Risk", "Return", "Total");
//		for(int i = 0; i < portfolios.size(); i++){
		
//			nameOwner = portfolios.get(i).getOwner().getLastName() + ", " + portfolios.get(i).getOwner().getFirstName();
//			nameManager = portfolios.get(i).getManager().getLastName() + ", " + portfolios.get(i).getManager().getFirstName();
//			
//			System.out.printf("%-12s %-25s %-25d %-15.2d %-15.2d %-20.2d %-15.2d %-15.2d\n", 
//					portfolios.get(i).getCode(), nameOwner, nameManager, '$' + portfolios.get(i).getBrokerFees(), 
//					portfolios.get(i).getCommission(), portfolios.get(i).getWeightedRisk(),
//					portfolios.get(i).getReturns(), portfolios.get(i).getTotalValue());
//					)
//		}
		
		// prints line under numerical values, acting as a divider between individual values and totals
		printThinLine(150);
		// print totals for fees, commissions, return, total
//		System.out.printf("%40s %-11s %-15d %-15d %20s %-15d %-15d\n", "", "Totals:", this.getTotalFees(), this.getTotalCommission(), "", this.getTotalReturn(), this.getTotalValue());
	}
	
	/**
	 * This method prints the Detailed Report for each portfolio, in order alphabetically by Owner's Last Name
	 */
	public void printDetailedReport(ArrayList<Portfolio> portfolios){
		System.out.println("Portfolio Details");
		printThickLine();
		
		String nameOwner = "";
		String nameManager = "";
 		String nameBeneficiary = "none";
 		
		//for(int i = 0; i < portfolios.size(); i++){
			// nameOwner = portfolios.get(i).getOwner().getLastName() + ", " + portfolios.get(i).getOwner().getFirstName();
			// nameManager = portfolios.get(i).getManager().getLastName() + ", " + portfolios.get(i).getManager().getFirstName();
			// if(portfolios.get(i).getBeneficiary() != null){
			// 	nameBeneficiary = portfolios.get(i).getBeneficiary().getLastName() + ", " + portfolios.get(i).getBeneficiary().getFirstName();
			// }
			//System.out.println("Portfolio" + portfolios.get(i).getCode());
			printThinLine(40);
			
			System.out.printf("%-15s %-25s\n", "Owner:", nameOwner);
			System.out.printf("%-15s %-25s\n", "Manager:", nameManager);
			System.out.printf("%-15s %-25s\n", "Beneficiary:", nameBeneficiary);
			System.out.println("Assets");
			System.out.printf("%-10s %-43s %20s %-5s %17s %15s\n", "Code", "Asset", "Return Rate", "Risk", "Annual Return", "Value");
			
//			int numAssets = portfolios.get(i).getAssets().size();
//			
//			for(int j = 0; j < numAssets; j++)
//			System.out.printf("%-10s %-43s %20.2d %-5.2d %-1s %16.2d %-1s %14.2d\n", portfolios.get(i).getCode(), 
//					portfolios.get(i).getAssets().get(j).getLabel(), portfolios.get(i).getAssets().get(j).getRate() + "%", 
//					portfolios.get(i).getAssets().get(j).getRisk(), "$", portfolios.get(i).getAssets().get(j).getAnnualReturn(), 
//					"$", portfolios.get(i).getAssets().get(j).getValue());
//			}
//			System.out.printf("%48s %-7s %-15d %-15d %20d\n", "", "Totals:", portfolios.get(i).getWeightedRisk(), portfolios.get(i).getTotalAnnualReturn(), portfolios.get(i).getTotalValue());
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