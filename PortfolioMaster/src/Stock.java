/**
 * Stock.java
 * RAIK 184H
 * This class represents a stock asset in the financial database
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 1.0
 */
public class Stock extends Asset {
	//Data members
	private double quarterlyDividend;
	private String symbol;
	private double sharePrice;
	private double beta;
	
	/**
	 * The constructor of a stock object
	 * @param code stock code
	 * @param label stock label
	 * @param type stock type
	 * @param rate stock rate of return
	 * @param quart stock quarterly dividend
	 * @param share stock share price
	 * @param symbol stock symbol
	 * @param beta stock beta measure
	 */
	public Stock(String code, String label, String type, double quart, double rate, double share,String symbol, double beta){
		setCode(code);
		setLabel(label);
		setType(type);
		setBaseRate(rate);
		setQuarterlyDividend(quart);
		setSharePrice(share);
		setSymbol(symbol);
		setBeta(beta);
		setRiskValue(beta);
	}
	
	/**
	 * computes the Annual Returns for the Stock asset
	 * @param stocks - the amount of stocks owned
	 */
	public double computeAnnualReturns(double stocks){
		double anReturns = 0.0;
		anReturns = (this.quarterlyDividend * 4 * stocks) + (this.baseRate * stocks * this.sharePrice) ; 
		//annual return is quartly dividend * 4 * stocks owned plus base Rate of return times sharePrice times stocks
		return anReturns;
	}
	
	/**
	 * computes the Value of the Stock
	 * @param stocks - the amount of stocks owned
	 */
	public double computeValueOfAsset(double stocks){
		double value = stocks * sharePrice; //value is stocks * sharePrice
		return value;
	}
	
	/**
	 * computes the returnRate of the Stock
	 * @param stocks - the amount of stocks owned
	 */
	public void computeReturnRate(double stocks){
		
		double returnRate = (computeAnnualReturns(stocks) / computeValueOfAsset(stocks)) * 100;
		this.setReturnRate(returnRate);
	}
	
	/**
	 * sets the Risk Value
	 * @param risk
	 */
	public void setRiskValue(double risk){
		this.riskValue = risk;
	}
	
	/**
	 * obtains the Risk Value
	 * @return riskValue
	 */
	public double getRiskValue(){
		return riskValue;
	}
	
	/**
	 * obtains the returnRate
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
		this.baseRate = (baseRate/100);
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
	 * Obtains the symbol
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**Sets the new symbol
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Obtains the sharePrice
	 * @return the sharePrice
	 */
	public double getSharePrice() {
		return sharePrice;
	}

	/**Sets the new sharePrice
	 * @param sharePrice the sharePrice to set
	 */
	public void setSharePrice(double sharePrice) {
		this.sharePrice = sharePrice;
	}

	/**
	 * Obtains the beta
	 * @return the beta
	 */
	public double getBeta() {
		return beta;
	}

	/**Sets the new beta
	 * @param beta the beta to set
	 */
	public void setBeta(double beta) {
		this.beta = beta;
	}
}