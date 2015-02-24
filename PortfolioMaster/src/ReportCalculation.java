import java.util.ArrayList;
import java.util.Collections;
import java.util.*;

/**
 * class used to sum up all the report values 
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 1.0
 *
 */
//make sure to sort the arraylist by owners last name
public class ReportCalculation {
	private double totalCommission;
	private double totalFees;
	private double totalReturn;
	private double totalValue;
	private  ArrayList<Portfolio> portfolioSet;
	private FileOutputter format;
	
	/**
	 * constructor for ReportCalculation class
	 * @param portfoliosGiven
	 */
	public ReportCalculation(ArrayList<Portfolio> portfoliosGiven) {
		this.portfolioSet = portfoliosGiven;
		sortList(portfoliosGiven); //sorts the list by last name of owner
		this.totalCommission = this.calculateTotalCommissions(portfoliosGiven); //calculates total commission
		this.totalFees = this.calculateTotalFees(portfoliosGiven); //calculates total broker fees
		this.totalReturn = this.calculateTotalReturns(portfoliosGiven); //calculates total annual returns
		this.totalValue = this.calculateTotalValue(portfoliosGiven); //calculates total value
		format = new FileOutputter();
	}
	
	/**
	 * sorts the list
	 * @param list
	 * @return list- the sorted list of all portfolios
	 */
	public ArrayList<Portfolio> sortList(ArrayList<Portfolio> list){
		
		class PortfolioSorter implements Comparator<Portfolio> {
			/**
			 * this is the method used to sort
			 */
			public int compare(Portfolio portfolio1, Portfolio portfolio2) {
				return portfolio1.getOwner().getLastName().compareTo(portfolio2.getOwner().getLastName());
			}
		}
		Collections.sort(list, new PortfolioSorter());
		return list;
	}
	
	/**
	 * calculates the total commissions  for all the portfolios
	 * @param portfolios
	 * @return tComm - total commissions
	 */
	private double calculateTotalCommissions(ArrayList<Portfolio> portfolios){
		double tComm = 0.0;
		for(Portfolio portfolio : portfolios){ //for each portfolio 
			tComm += portfolio.getCommissionFees(); //add the total commissions fee
		}
		return tComm;
	}
	
	/**
	 * calculates the total broker fees for all the portfolios
	 * @param portfolios
	 * @return tFees - total broker fees
	 */
	private double calculateTotalFees(ArrayList<Portfolio>portfolios){
		double tFees = 0.0;
		
		for(Portfolio portfolio : portfolios){ //for each portfolio
			tFees += portfolio.getBrokerFees(); //add its broker fees to the total
		}
		return tFees;
	}
	
	/**
	 * calculates the total values for all the portfolios
	 * @param portfolios
	 * @return tValue - total value
	 */
	private double calculateTotalValue(ArrayList<Portfolio>portfolios){
		double tValue = 0.0;
		for(Portfolio portfolio : portfolios){ //for each portfolio
			tValue += portfolio.getTotalValue(); //add the total value of the portfolio to the rest
		}
		return tValue;
	}
	
	/**
	 * calculates the Total annual returns for all the portfolios
	 * @param portfolios
	 * @return tReturns- total annual returns
	 */
	private double calculateTotalReturns(ArrayList<Portfolio>portfolios){
		double tReturns = 0.0;
		for(Portfolio portfolio : portfolios){ //for each portfolio
			tReturns += portfolio.getTotalAnnualReturns(); //add the annual return of the portfolio to the rest
		}
		
		return tReturns;
		
	}

	/**
	 * @return the totalCommission
	 */
	public double getTotalCommission() {
		return totalCommission;
	}

	/**
	 * @return the totalFees
	 */
	public double getTotalFees() {
		return totalFees;
	}

	/**
	 * @return the totalReturn
	 */
	public double getTotalReturn() {
		return totalReturn;
	}

	/**
	 * @return the totalValue
	 */
	public double getTotalValue() {
		return totalValue;
	}
	
}