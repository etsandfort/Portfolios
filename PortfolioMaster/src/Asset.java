/**
 * Asset.java
 * An class representing an asset in the financial database
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 3.8
 */
public class Asset{
	// data members
	private String code;
	private String label;
	private String type;
	protected double riskValue;
	protected double baseRate;
	protected double returnRate;
	
	/**
	 * Computes the return rate of the given asset
	 * @param inputValue
	 */
	public void computeReturnRate(double inputValue){}
	
	/**
	 * Computes the value of the asset
	 * @param inputValue
	 * @return double - returns the double of the annual returns
	 */
	public double computeAnnualReturns(double inputValue){
		return 0.0;
	}
	
	/**
	 * computes the value of the asset
	 * @param inputValue
	 * @return double- returns the double of the value of the asset
	 */
	 public double computeValueOfAsset(double inputValue){
		return 0.0;
	}
	
	/**
	 * Obtains the risk value
	 * @return the riskValue
	 */
	public double getRiskValue(){
		return riskValue;
	}
	
	/**
	 * Sets the risk value
	 * @param riskValue
	 */
	public void setRiskValue(double riskValue){
		this.riskValue = riskValue;
	}
	
	/**
	 * Obtains the code
	 * @return the code
	 */
	public String getCode(){
		return code;
	}
	
	/**
	 * Sets the new code
	 * @param code
	 */
	public void setCode(String code){
		this.code = code;
	}
	
	/**
	 * Obtains the label
	 * @return label
	 */
	public String getLabel(){
		return label;
	}
	
	/**
	 * Sets the new label
	 * @param label
	 */
	public void setLabel(String label){
		this.label = label;
	}
	
	/**
	 * Obtains the type
	 * @return the type
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * Sets the new type
	 * @param type
	 */
	public void setType(String type){
		this.type = type;
	}
	
	/**
	 * Obtains the base rate of return
	 * @return the baseRate
	 */
	public double getBaseRate(){
		return baseRate;
	}
	
	/**
	 * Sets the base rate of return
	 * @param baseRate
	 */
	public void setBaseRate(double baseRate){
		this.baseRate = baseRate;
	}
	
	/**
	 * Obtains the returnRate
	 * @return the returnRate
	 */
	public double getReturnRate(){
		return returnRate;
	}
	
	/**
	 * Sets the returnRate
	 * @param returnRate
	 */
	public void setReturnRate(double returnRate){
		this.returnRate = returnRate;
	}
}