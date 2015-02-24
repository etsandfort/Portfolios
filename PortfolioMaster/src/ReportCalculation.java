import java.util.ArrayList;
import java.util.Collections;
import java.util.*;

/**
 * ReportCalculation.java
 * Performs all of the calculations necessary for portfolio reporting
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 5.0
 */

public class ReportCalculation{
	// data members
	private double totalCommission;
	private double totalFees;
	private double totalReturn;
	private double totalValue;
	private  ArrayList<Portfolio> portfolioSet;
	
	/**
	 * This is the constructor for the ReportCalculation class
	 * @param portfoliosGiven
	 */
	public ReportCalculation(ArrayList<Portfolio> portfoliosGiven) {
		this.portfolioSet = portfoliosGiven;
		sortList(portfoliosGiven); //sorts the list by last name of owner
		this.totalCommission = this.calculateTotalCommissions(portfoliosGiven); //calculates total commission
		this.totalFees = this.calculateTotalFees(portfoliosGiven); //calculates total broker fees
		this.totalReturn = this.calculateTotalReturns(portfoliosGiven); //calculates total annual returns
		this.totalValue = this.calculateTotalValue(portfoliosGiven); //calculates total value
	}
	
	/**
	 * Sorts the ArrayList of portfolios
	 * @param list, an ArrayList of portfolios
	 * @return list- the sorted ArrayList of all portfolios
	 */
	public ArrayList<Portfolio> sortList(ArrayList<Portfolio> list){
		
		class PortfolioSorter implements Comparator<Portfolio>{
			/**
			 * The compare method sorts the portfolios by owner's last name
			 */
			public int compare(Portfolio portfolio1, Portfolio portfolio2){
				return portfolio1.getOwner().getLastName().compareTo(portfolio2.getOwner().getLastName());
			}
		}
		Collections.sort(list, new PortfolioSorter());
		return list;
	}
	
	/**
	 * Calculates the total commissions  for all the portfolios
	 * @param portfolios, an ArrayList
	 * @return tComm - total commissions, a double
	 */
	private double calculateTotalCommissions(ArrayList<Portfolio> portfolios){
		double tComm = 0.0; // total commissions
		for(Portfolio portfolio : portfolios){ //for each portfolio 
			tComm += portfolio.getCommissionFees(); //add the total commissions fee
		}
		return tComm;
	}
	
	/**
	 * calculates the total broker fees for all the portfolios
	 * @param portfolios, an ArrayList
	 * @return tFees - total broker fees, a double
	 */
	private double calculateTotalFees(ArrayList<Portfolio>portfolios){
		double tFees = 0.0; // total broker fees
		
		for(Portfolio portfolio : portfolios){ //for each portfolio
			tFees += portfolio.getBrokerFees(); //add its broker fees to the total
		}
		return tFees;
	}
	
	/**
	 * Calculates the total values for all the portfolios
	 * @param portfolios, an ArrayList
	 * @return tValue - total value, a double
	 */
	private double calculateTotalValue(ArrayList<Portfolio>portfolios){
		double tValue = 0.0; // total value
		for(Portfolio portfolio : portfolios){ //for each portfolio
			tValue += portfolio.getTotalValue(); //add the total value of the portfolio to the rest
		}
		return tValue;
	}
	
	/**
	 * Calculates the Total annual returns for all the portfolios
	 * @param portfolios, an ArrayList
	 * @return tReturns- total annual returns, a double
	 */
	private double calculateTotalReturns(ArrayList<Portfolio>portfolios){
		double tReturns = 0.0; // total annual returns
		for(Portfolio portfolio : portfolios){ //for each portfolio
			tReturns += portfolio.getTotalAnnualReturns(); //add the annual return of the portfolio to the overall total returns
		}
		
		return tReturns;
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