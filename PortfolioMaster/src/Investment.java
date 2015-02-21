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

	
	public double computeAnnualReturns(double PercentageOwned){
	double anReturns = (this.baseRate * this.value + this.quarterlyDividend * 4) * PercentageOwned / 100;
	return anReturns;
	}
	public void setRiskValue(double risk){
		this.riskValue = risk;
	}
	public double getRiskValue(){
		return riskValue;
	}
	public double computeValueOfAsset(double given){
		double value = this.value * given / 100;
		return value;
	}
	/**
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
		this.baseRate = baseRate;
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