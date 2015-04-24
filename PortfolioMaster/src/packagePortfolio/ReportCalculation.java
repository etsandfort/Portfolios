package packagePortfolio;

import java.util.List;

/**
 * ReportCalculation.java
 * RAIK 184H
 * Performs all of the calculations necessary for portfolio reporting
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 3.0
 */

public class ReportCalculation{
	// data members
	private double totalCommission;
	private double totalFees;
	private double totalReturn;
	private double totalValue;
	
	/**
	 * This is the constructor for the ReportCalculation class
	 * @param portfoliosGiven
	 */
	public ReportCalculation(List<Portfolio> portfoliosGiven){//GenericList<Portfolio> portfoliosGiven) {
		this.totalCommission = this.calculateTotalCommissions(portfoliosGiven); //calculates total commission
		this.totalFees = this.calculateTotalFees(portfoliosGiven); //calculates total broker fees
		this.totalReturn = this.calculateTotalReturns(portfoliosGiven); //calculates total annual returns
		this.totalValue = this.calculateTotalValue(portfoliosGiven); //calculates total value
	}
	
	/**
	 * Calculates the total commissions  for all the portfolios
	 * @param portfolios, an ArrayList
	 * @return tComm - total commissions, a double
	 */
	private double calculateTotalCommissions(List<Portfolio> portfolios){//GenericList<Portfolio> portfolios){
		double totalCommission = 0.0; 
		for(Portfolio portfolio : portfolios){ //for each portfolio 
			totalCommission += portfolio.getCommissionFees(); //add the total commissions fee
		}
		return totalCommission;
	}
	
	/**
	 * calculates the total broker fees for all the portfolios
	 * @param portfolios, an ArrayList
	 * @return tFees - total broker fees, a double
	 */
	private double calculateTotalFees(List<Portfolio> portfolios){//GenericList<Portfolio>portfolios){
		double totalFees = 0.0; // total broker fees
		for(Portfolio portfolio : portfolios){ //for each portfolio
		totalFees += portfolio.getBrokerFees(); //add its broker fees to the total
		}
		return totalFees;
	}
	
	/**
	 * Calculates the total values for all the portfolios
	 * @param portfolios, an ArrayList
	 * @return tValue - total value, a double
	 */
	private double calculateTotalValue(List<Portfolio> portfolios){//GenericList<Portfolio>portfolios){
		double totalValue = 0.0;
		for(Portfolio portfolio : portfolios){ //for each portfolio
		totalValue += portfolio.getTotalValue(); //add the total value of the portfolio to the rest
		}
		return totalValue;
	}
	
	/**
	 * Calculates the Total annual returns for all the portfolios
	 * @param portfolios, an ArrayList
	 * @return tReturns- total annual returns, a double
	 */
	private double calculateTotalReturns(List<Portfolio> portfolios){//GenericList<Portfolio>portfolios){
		double totalReturns = 0.0; // total annual returns
		for(Portfolio portfolio : portfolios){ //for each portfolio
		totalReturns += portfolio.getTotalAnnualReturns(); //add the annual return of the portfolio to the overall total returns
		}
		return totalReturns;
	}
	
	/**
	 * Obtains the total Commissions of all of the portfolios
	 * @return totalCommission, a double
	 */
	public double getTotalCommission(){
		return totalCommission;
	}
	
	/**
	 * Obtains the total fees of all of the portfolios
	 * @return totalFees, a double
	 */
	public double getTotalFees(){
		return totalFees;
	}
	
	/**
	 * Obtains the total returns of all of the portfolios
	 * @return totalReturn, a double
	 */
	public double getTotalReturn(){
		return totalReturn;
	}
	
	/**
	 * Obtains the total value of all of the portfolios
	 * @return totalValue, a double
	 */
	public double getTotalValue(){
		return totalValue;
	}
}