import java.util.ArrayList;
import java.util.Collections;
import java.util.*;

/**
 * 
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
	
	public ReportCalculation(ArrayList<Portfolio>portfoliosGiven) {
		this.portfolioSet = portfoliosGiven;
		sortList(portfoliosGiven);
		this.totalCommission = this.calculateTotalCommissions(portfoliosGiven);
		this.totalFees = this.calculateTotalFees(portfoliosGiven);
		this.totalReturn = this.calculateTotalReturns(portfoliosGiven);
		this.totalValue = this.calculateTotalValue(portfoliosGiven);
	}
	
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
	
	public double getTotalCommission() {
		return totalCommission;
	}

	public double getTotalFees() {
		return totalFees;
	}

	public double getTotalReturn() {
		return totalReturn;
	}

	public double getTotalValue() {
		return totalValue;
	}

	private double calculateTotalCommissions(ArrayList<Portfolio> portfolios){
		double tComm = 0.0;
		for(Portfolio portfolio : portfolios){
			tComm += portfolio.getCommissionFees();
		}
		
		return tComm;
	}
	private double calculateTotalFees(ArrayList<Portfolio>portfolios){
		double tFees = 0.0;
		
		for(Portfolio portfolio : portfolios){
			tFees += portfolio.getBrokerFees();
		}
		return tFees;
	}
	private double calculateTotalValue(ArrayList<Portfolio>portfolios){
		double tValue = 0.0;
		for(Portfolio portfolio : portfolios){
			tValue += portfolio.getTotalValue();
		}
		return tValue;
	}
	private double calculateTotalReturns(ArrayList<Portfolio>portfolios){
		double tReturns = 0.0;
		for(Portfolio portfolio : portfolios){
			for(Double annualReturns: portfolio.getAnnualReturns().values()){
				tReturns += annualReturns;
			}
		
		}
		
		return tReturns;
		
	}

}
