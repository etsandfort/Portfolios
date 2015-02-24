/**
 * Investment.java
 * RAIK 184H
 * This class represents an investment asset in the financial database
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 1.0
 */
public class Investment extends Asset{
	//Data Members
	private double quarterlyDividend;
	private double omega;
	private double value;
	
	/**
	 * Constructor of investments
	 * @param code investment asset's code
	 * @param label investment asset's label
	 * @param type type of investment
	 * @param rate rate of return
	 * @param quart quarterly dividend
	 * @param omega omega measure
	 * @param value total value
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
	 * computes the Annual Returns for the Investment
	 * @param percentageOwned - percentage owned of investment
	 */
	public double computeAnnualReturns(double percentageOwned){
		double anReturns = (this.baseRate * this.value + this.quarterlyDividend * 4) * percentageOwned / 100;
		//annualReturns is base rate of returns * value plus quarterly dividend * 4 times percentageOwned as a percent
		return anReturns;
	}
	
	/**
	 * computes the Value of the Asset
	 * @param percentageOwned - percentage owned of investment
	 */
	public double computeValueOfAsset(double percentageOwned){
		double value = this.value * percentageOwned / 100;
		return value;
	}
	
	/**
	 * computes the return rate of given investment
	 * @param percentageOwned - percentage owned of investment
	 */
	public void computeReturnRate(double percentageOwned){
	double returnRate = (computeAnnualReturns(percentageOwned) / computeValueOfAsset(percentageOwned))* 100;
	this.setReturnRate(returnRate); 
	}
	
	/**
	 * sets the risk Value
	 * @param risk
	 */
	public void setRiskValue(double risk){
		this.riskValue = risk;
	}
	
	/**
	 * obtains the risk value
	 * @returns riskValue
	 */
	public double getRiskValue(){
		return riskValue;
	}
	
	/**
	 * obtains the return rate
	 * @return returnRate
	 */
	public double getReturnRate(){
		return returnRate;
	}
	
	/**
	 * sets the Return Rate
	 * @param returnRate
	 */
	public void setReturnRate(double returnRate){
		this.returnRate = returnRate;
	}
	
	/**
	 *
	 * Obtains the quarterlyDividend
	 * @return the quarterlyDividend
	 */
	public double getQuarterlyDividend() {
		return quarterlyDividend;
	}

	/**Sets the new quarterlyDividend
	 * @param quarterlyDividend the quarterlyDividend to set
	 */
	public void setQuarterlyDividend(double quarterlyDividend) {
		this.quarterlyDividend = quarterlyDividend;
	}

	/**
	 * obtains the baseRate
	 * @return the baseRate
	 */
	public double getBaseRate(){
		return baseRate;
	}
	
	/**
	 * sets the baseRate
	 * @param baseRate
	 */
	public void setBaseRate(double baseRate ){
		this.baseRate = (baseRate / 100);
	}

	/**
	 * Obtains the omega
	 * @return the omega
	 */
	public double getOmega() {
		return omega;
	}

	/**Sets the new omega
	 * @param omega the omega to set
	 */
	public void setOmega(double omega) {
		this.omega = omega;
	}

	/**
	 * Obtains the value
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**Sets the new value
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}
}