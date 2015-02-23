/**
 * Deposit.java
 * RAIK 184H
 * This class represents a deposit account asset in the financial database
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 1.0
 */
public class Deposit extends Asset{
	//Data members
	
	/**
	 * The constructor for a deposit object
	 * @param code deposit code
	 * @param label deposit label
	 * @param type deposit type
	 * @param apr deposit APR
	 */
	public Deposit(String code, String label, String type, double apr){
		setCode(code);
		setLabel(label);
		setType(type);
		setBaseRate(apr);
		setRiskValue(0);
	}
	
	/**
	 * computes the annual returns 
	 */
	public double computeAnnualReturns(double deposit){
		double anReturns = 0.0;
		double aPY;
		aPY = (Math.pow(Math.E, this.baseRate) - 1); //apy = 
		anReturns = aPY * deposit  ;
		return anReturns;
	}
	
	/**
	 * computes value of asset, equal to what the intitialDeposit is
	 */
	public double computeValueOfAsset(double initialDeposit){
		double value = initialDeposit;
		return value;
	}
	
	/**
	 * 
	 */
	public double getBaseRate(){
		return baseRate;
	}
	
	public void setBaseRate(double baseRate){
		this.baseRate = baseRate/100;
	}
	public void computeReturnRate(double initialDeposit){
		
		double returnRate = computeAnnualReturns(initialDeposit)/initialDeposit * 100;
		this.setReturnRate(returnRate);
		
	}
	public double getReturnRate(){
		return returnRate;
	}
	public void setReturnRate(double returnRate){
		this.returnRate = returnRate;
	}
	
}