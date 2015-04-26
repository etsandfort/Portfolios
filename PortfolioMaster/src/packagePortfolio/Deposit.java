package packagePortfolio;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Deposit.java
 * RAIK 184H
 * This class represents a deposit account asset in the financial database
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 3.0
 */

@Entity(name="Deposit")
@DiscriminatorValue(value="D")
public class Deposit extends Asset{
	
	private static final long serialVersionUID = -3775861038675860090L;

	public Deposit() {}	
	
	/**
	 * The constructor for a deposit object
	 * @param code, the deposit code
	 * @param label, the deposit label
	 * @param type, the deposit type
	 * @param apr, the deposit APR
	 */
	public Deposit(String code, String label, String type, double apr){
		setCode(code);
		setLabel(label);
//		setType(type);
		setBaseRate(apr);
		setRiskValue(0);
	}
	
	/**
	 * Computes the Annual Returns, using APY formula 
	 * @param anReturns, the annual returns 
	 */
	public double computeAnnualReturns(double deposit){
		double anReturns = 0.0;
		double aPY;
		aPY = (Math.pow(Math.E, this.baseRate/100) - 1); //apy = e^(APR) - 1
		anReturns = aPY * deposit;
		
		//TODO checking 
//		anReturns = anReturns * 100;
		return anReturns;
	}
	
	/**
	 * Computes asset's value, which is equal to the intitialDeposit
	 * @param value, the value of the asset
	 */
	public double computeValueOfAsset(double initialDeposit){
		double value = initialDeposit;
		return value;
	}
	
	/**
	 * Computes the Deposit's Return Rate
	 * @param initialDeposit - the current amount
	 */
	public void computeReturnRate(double initialDeposit){
//		System.out.println("In return rate - deposit");
		double returnRate = (computeAnnualReturns(initialDeposit)/initialDeposit) * 100.0;
		this.setReturnRate(returnRate);
//		System.out.println("Return rate: " + returnRate);
//		System.out.println("Return rate with get: " + getReturnRate());
	}

	/**
	 * Obtains the Return rate
	 * @return the returnRate
	 */
	public double getReturnRate(){
		return returnRate;
	}

	/**
	 * Sets the Return rate
	 * @param returnRate
	 */
	public void setReturnRate(double returnRate){
		this.returnRate = returnRate;
	}
	
	/**
	 * Obtains the baseRate
	 * @return the baseRate, a double
	 */
	public double getBaseRate(){
		return baseRate;
	}
	
	/**
	 * Sets the baseRate
	 * @param baseRate 
	 */
	public void setBaseRate(double baseRate){
//		this.baseRate = baseRate/100.0; //turns it from percent to decimal
		this.baseRate = baseRate*100.0;
	}
}