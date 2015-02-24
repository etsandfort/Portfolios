/**
 * Investment.java
 * RAIK 184H
 * This class represents an investment asset in the financial database
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 3.0
 */
public class Investment extends Asset{
	//Data Members
	private double quarterlyDividend;
	private double omega;
	private double value;

	/**
	 * Constructor of investments
	 * @param code, the investment asset's code
	 * @param label, the investment asset's label
	 * @param type, the type of investment
	 * @param rate, the investment's rate of return
	 * @param quart, the investment's quarterly dividend
	 * @param omega, the investment's omega measure
	 * @param value, the investment's total value
	 */
	public Investment(String code, String label, String type, double quart,double rate,  double omega, double value){
		setCode(code);
		setLabel(label);
		setType(type);
		setBaseRate(rate);
		setQuarterlyDividend(quart);
		setOmega(omega);
		setValue(value);
		setRiskValue(omega);

	}
	
	/**
	 * Computes the Annual Returns for the Investment
	 * @param percentageOwned - percentage owned of investment
	 */
	public double computeAnnualReturns(double percentageOwned){
		double anReturns = (this.baseRate * this.value + this.quarterlyDividend * 4) * percentageOwned / 100;
		//annualReturns is base rate of returns * value plus quarterly dividend * 4 times percentageOwned as a percent
		return anReturns;
	}

	/**
	 * Computes the Value of the Investment Asset
	 * @param percentageOwned - percentage owned of investment
	 */
	public double computeValueOfAsset(double percentageOwned){
		double value = this.value * percentageOwned / 100;
		return value;
	}

	/**
	 * Computes the return rate of given investment
	 * @param percentageOwned - percentage owned of investment
	 */
	public void computeReturnRate(double percentageOwned){
		double returnRate = (computeAnnualReturns(percentageOwned) / computeValueOfAsset(percentageOwned))* 100;
		this.setReturnRate(returnRate); 
	}

	/**
	 * Sets the investment's risk value
	 * @param risk, a double
	 */
	public void setRiskValue(double risk){
		this.riskValue = risk + Math.pow(Math.E, (-100000/value));
	}

	/**
	 * Obtains the investment's risk value
	 * @returns riskValue, a double
	 */
	public double getRiskValue(){
		return riskValue;
	}

	/**
	 * Obtains the investment's return rate
	 * @return returnRate, a double
	 */
	public double getReturnRate(){
		return returnRate;
	}

	/**
	 * Sets the investment's return rate
	 * @param returnRate, a double
	 */
	public void setReturnRate(double returnRate){
		this.returnRate = returnRate;
	}

	/**
	 * Obtains the investment's quarterly dividend
	 * @return quarterlyDividend, a double
	 */
	public double getQuarterlyDividend(){
		return quarterlyDividend;
	}
	
	/**Sets the investment's quarterly dividend
	 * @param quarterlyDividend, a double
	 */
	public void setQuarterlyDividend(double quarterlyDividend){
		this.quarterlyDividend = quarterlyDividend;
	}
	
	/**
	 * Obtains the investment's base rate of return
	 * @return baseRate, a double
	 */
	public double getBaseRate(){
		return baseRate;
	}

	/**
	 * Sets the investment's base rate of return
	 * @param baseRate, a double
	 */
	public void setBaseRate(double baseRate ){
		this.baseRate = (baseRate / 100); // converts percentage to its decimal form
	}
	
	/**
	 * Obtains the investment's omega measure
	 * @return omega, a double
	 */
	public double getOmega(){
		return omega;
	}
	
	/**
	 * Sets the investment's omega measure
	 * @param omega, a double
	 */
	public void setOmega(double omega){
		this.omega = omega;
	}
	
	/**
	 * Obtains the stock's value
	 * @return value, a double
	 */
	public double getValue(){
		return value;
	}
	
	/**
	 * Sets the investment's value
	 * @param value, a double
	 */
	public void setValue(double value){
		this.value = value;
	}
}