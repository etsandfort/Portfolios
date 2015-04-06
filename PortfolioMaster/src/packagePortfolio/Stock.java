package packagePortfolio;

/**
 * Stock.java
 * RAIK 184H
 * This class represents a stock asset in the financial database
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 3.0
 */
public class Stock extends Asset{
	//Data members
	private double quarterlyDividend;
	private String symbol;
	private double sharePrice;
	private double beta;
	
	/**
	 * The constructor of a stock object
	 * @param code, the stock's code
	 * @param label, the stock's label
	 * @param type, the stock's type
	 * @param rate, the stock's rate of return
	 * @param quart, the stock's quarterly dividend
	 * @param share, the stock's share price
	 * @param symbol, the stock's symbol
	 * @param beta, the stock's beta measure
	 */
	public Stock(String code, String label, String type, double quart, double rate, double share,String symbol, double beta){
		setCode(code);
		setLabel(label);
		setType(type);
		setQuarterlyDividend(quart);
		setBaseRate(rate);
		setSharePrice(share);
		setSymbol(symbol);
		setBeta(beta);
		setRiskValue(beta);
	}
	
	/**
	 * Computes the Annual Returns for the Stock asset
	 * @param stocks - the amount of stocks owned
	 */
	public double computeAnnualReturns(double stocks){
		double anReturns = 0.0;
		anReturns = (this.quarterlyDividend * 4 * stocks) + (this.baseRate * stocks * this.sharePrice) ; 
		//annual return is quarterly dividend * 4 * stocks owned plus base Rate of return times sharePrice times stocks
		return anReturns;
	}
	
	/**
	 * Computes the Value of the Stock
	 * @param stocks - the amount of stocks owned
	 * @return value, a double
	 */
	public double computeValueOfAsset(double stocks){
		double value = stocks * sharePrice; //value is stocks * sharePrice
		return value;
	}
	
	/**
	 * Computes the returnRate of the Stock
	 * @param stocks - the amount of stocks owned
	 */
	public void computeReturnRate(double stocks){
		
		double returnRate = (computeAnnualReturns(stocks) / computeValueOfAsset(stocks)) * 100.0;
		this.setReturnRate(returnRate);
	}
	
	/**
	 * Sets the Risk Value
	 * @param risk, a double
	 */
	public void setRiskValue(double risk){
		this.riskValue = risk;
	}
	
	/**
	 * Obtains the stock's risk value.
	 * @return riskValue, a double
	 */
	public double getRiskValue(){
		return riskValue;
	}
	
	/**
	 * Obtains the stock's return rate.
	 * @return returnRate, a double
	 */
	public double getReturnRate(){
		return returnRate;
	}
	
	/**
	 * Sets the stock's return rate.
	 * @param returnRate, a double
	 */
	public void setReturnRate(double returnRate){
		this.returnRate = returnRate;
	}
	
	/**
	 * Obtains the stock's base rate of return
	 * @return baseRate, a double
	 */
	public double getBaseRate(){
		return baseRate;
	}
	
	/**
	 * Sets the stock's base rate of return
	 * @param baseRate
	 */
	public void setBaseRate(double baseRate ){
		this.baseRate = (baseRate/100.0); // converting the percentage to its decimal version
	}
	
	/**
	 * Obtains the stock's quarterly dividend
	 * @return quarterlyDividend, a double
	 */
	public double getQuarterlyDividend(){
		return quarterlyDividend;
	}
	
	/**
	 * Sets the stock's quarterly dividend
	 * @param quarterlyDividend, a double
	 */
	public void setQuarterlyDividend(double quarterlyDividend){
		this.quarterlyDividend = quarterlyDividend;
	}
	
	/**
	 * Obtains the stock's symbol
	 * @return symbol, a String
	 */
	public String getSymbol(){
		return symbol;
	}
	
	/**
	 * Sets the stock's symbol
	 * @param symbol, a String
	 */
	public void setSymbol(String symbol){
		this.symbol = symbol;
	}
	
	/**
	 * Obtains the stock's share price
	 * @return sharePrice, a double
	 */
	public double getSharePrice(){
		return sharePrice;
	}
	
	/**Sets the new sharePrice
	 * @param sharePrice the sharePrice to set
	 */
	public void setSharePrice(double sharePrice){
		this.sharePrice = sharePrice;
	}
	
	/**
	 * Obtains the stock's beta measure
	 * @return beta, a double
	 */
	public double getBeta(){
		return beta;
	}
	
	/**
	 * Sets the stock's beta measure
	 * @param beta, a double
	 */
	public void setBeta(double beta){
		this.beta = beta;
	}
}