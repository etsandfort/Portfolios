package packagePortfolio;

import java.util.*;

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
	public ReportCalculation(PortfolioList<Portfolio> portfoliosGiven) {
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
	private double calculateTotalCommissions(PortfolioList<Portfolio> portfolios){
		double totalCommission = 0.0; 
		Boolean b = portfolios == null;
		for(int i = 0; i < portfolios.size(); i++){
			totalCommission += portfolios.get(i).getCommissionFees(); //add the total commissions fee
		}
		return totalCommission;
	}
	
	/**
	 * calculates the total broker fees for all the portfolios
	 * @param portfolios, an ArrayList
	 * @return tFees - total broker fees, a double
	 */
	private double calculateTotalFees(PortfolioList<Portfolio>portfolios){
		double totalFees = 0.0; // total broker fees
		
		for(int i = 0; i < portfolios.size(); i++){	
		totalFees += portfolios.get(i).getBrokerFees(); //add its broker fees to the total
		}
		return totalFees;
	}
	
	/**
	 * Calculates the total values for all the portfolios
	 * @param portfolios, an ArrayList
	 * @return tValue - total value, a double
	 */
	private double calculateTotalValue(PortfolioList<Portfolio>portfolios){
		double totalValue = 0.0;
		for(int i = 0; i < portfolios.size(); i++){	
		totalValue += portfolios.get(i).getTotalValue(); //add the total value of the portfolio to the rest
		}
		return totalValue;
	}
	
	/**
	 * Calculates the Total annual returns for all the portfolios
	 * @param portfolios, an ArrayList
	 * @return tReturns- total annual returns, a double
	 */
	private double calculateTotalReturns(PortfolioList<Portfolio>portfolios){
		double totalReturns = 0.0; // total annual returns
		for(int i = 0; i < portfolios.size(); i++){	
		totalReturns += portfolios.get(i).getTotalAnnualReturns(); //add the annual return of the portfolio to the overall total returns
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