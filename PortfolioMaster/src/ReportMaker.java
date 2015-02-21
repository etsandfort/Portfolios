import java.util.ArrayList;

public class ReportMaker {
	
	private ReportCalculation reportCalc;
	private ArrayList<Portfolio> portfolios;
	
	public ReportMaker(ArrayList<Portfolio> portfolios) {
		reportCalc = new ReportCalculation(portfolios);
		this.portfolios = portfolios;
	}
	
	/**
	 * This method prints the Portfolio Summary Report.
	 */
	public void printSummaryReport(){
		System.out.println("Portfolio Summary Report");
		printThickLine();
		
		String nameOwner = "";
		String nameManager = "";
	
		// Prints code, owner, manager, fees, commissions, weighted risk, return, total
		System.out.printf("%-12s %-25s %-25s %-15s %-16s %-20s %-15s %-15s\n", "Portfolio",
				"Owner", "Manager", "Fees", "Commissions", "Weighted Risk", "Return", "Total");
		for(int i = 0; i < portfolios.size(); i++){
		
			nameOwner = portfolios.get(i).getOwner().getLastName() 
						+ ", " + portfolios.get(i).getOwner().getFirstName();
			nameManager = portfolios.get(i).getManager().getLastName()
						  + ", " + portfolios.get(i).getManager().getFirstName();
			
			//TODO add a $ to broker fees
			System.out.printf("%-12s %-25s %-25s $%-15.2f %-15.2f %-20.2f %-15.2f %-15.2f\n", 
					portfolios.get(i).getCode(), nameOwner, nameManager,
					portfolios.get(i).getBrokerFees(), 
					portfolios.get(i).getCommissionFees(), 
					portfolios.get(i).getTotalRisks(),
					portfolios.get(i).getTotalAnnualReturns(),
					portfolios.get(i).getTotalValue());
		}
		
		// prints line under numerical values, acting as a divider between individual values and totals
		printThinLine(150);
		// print totals for fees, commissions, return, total
		System.out.printf("%52s %-11s $%-15.2f %-15.2f %20s %-15.2f %-15.2f\n", "", 
				"Totals:", reportCalc.getTotalFees(), reportCalc.getTotalCommission(), 
				"", reportCalc.getTotalReturn(), reportCalc.getTotalValue());
	}
	
	/**
	 * This method prints the Detailed Report for each portfolio, in order alphabetically by Owner's Last Name
	 */
	public void printDetailedReport(){
		System.out.println("Portfolio Details");
		printThickLine();
		
 		// iterating through each portfolio
		for(int i = 0; i < portfolios.size(); i++) {
			
			setUpDetailedReport(i);
			
			for(Asset asset : portfolios.get(i).getAssetList().keySet()) {

				double assetVals[] = new double[3];
				for(int j = 0; j < 3; j++) { // gets risk, annual return and value of each asset
					assetVals[j] = portfolios.get(i).getAssetList().get(asset)[j]; 
				}
				
				// TODO add % to Base Rate
				System.out.printf("%-10s %-48s %-15.2f %-5s $%15.2f $%15.2f\n", 
						asset.getCode(), asset.getLabel(), asset.getBaseRate(), assetVals[0], assetVals[1], assetVals[2]);
			}
			
			// prints line under numerical values, acting as a divider between individual values and totals
			printThinLine(150);
			
			System.out.printf("%59s %20.2f  $%15.2f $%15.2f\n\n", "Totals:",
								portfolios.get(i).getTotalRisks(),
								portfolios.get(i).getTotalAnnualReturns(), 
								portfolios.get(i).getTotalValue());
		}
	}
	
	public void setUpDetailedReport(int portfolioNum) {
		String nameOwner = "";
		String nameManager = "";
 		String nameBeneficiary = "none";
		
		// set the name of the portfolio's owner
		nameOwner = portfolios.get(portfolioNum).getOwner().getLastName()
				    + ", " + portfolios.get(portfolioNum).getOwner().getFirstName();
		
		// set the name of the portfolio's manager
		nameManager = portfolios.get(portfolioNum).getManager().getLastName()
					  + ", " + portfolios.get(portfolioNum).getManager().getFirstName();
		
		// set the name of the portfolio's beneficiary, if there is one
		if(portfolios.get(portfolioNum).getBeneficiary() != null) {
			nameBeneficiary = portfolios.get(portfolioNum).getBeneficiary().getLastName()
							  + ", " + portfolios.get(portfolioNum).getBeneficiary().getFirstName();
		}
		
		System.out.println("Portfolio " + portfolios.get(portfolioNum).getCode());
		printThinLine(40);
		
		System.out.printf("%-15s %-25s\n", "Owner:", nameOwner);
		System.out.printf("%-15s %-25s\n", "Manager:", nameManager);
		System.out.printf("%-15s %-25s\n", "Beneficiary:", nameBeneficiary);
		System.out.println("Assets");
		System.out.printf("%-10s %-48s %-15s %-7s %-15s %-5s\n", "Code", "Asset", 
						  "Return Rate", "Risk", "Annual Return", "Value");
	}
	
	/**
	 * Prints a thick line; used to better format portfolio reports
	 */
	public static void printThickLine() {
		for(int i = 0; i < 150; i++){
			if(i != 149) {
				System.out.print('=');				
			}
			else {
				System.out.println('=');
			}
		}
	}
	
	/**
	 * Prints a hyphenated line; used to better format portfolio reports
	 */
	public static void printThinLine(int lineLength) {
		for(int i = 0; i < lineLength; i++) {
			if(i < lineLength - 90) { // print blank spaces because can't sum codes, owners or managers
				System.out.print(" ");
			}
			else if(i != (lineLength - 1)) {
				System.out.print('-');				
			}
			else { // last char to be printed, so start new line after printing
				System.out.println('-');
			}
		}
	}
}