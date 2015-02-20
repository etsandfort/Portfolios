/**
 * Stock.java
 * RAIK 184H
 * This class represents a stock asset in the financial database
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 1.0
 */
public class Stock extends Asset {
	//Data members
	private double baseRateOfReturn;
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
	public Stock(String code, String label, String type, double rate, double quart, double share,String symbol, double beta){
		setCode(code);
		setLabel(label);
		setType(type);
		//setBaseRateOfReturn(rate);
		//setQuarterlyDividend(quart);//quick fix bad 
		setBaseRateOfReturn(quart);
		setQuarterlyDividend(rate);
		setSharePrice(share);
		setSymbol(symbol);
		setBeta(beta);
		setRiskValue(beta);
		setValueValue(share);
	}
	public void setRiskValue(double risk){
		this.riskValue = risk;
	}
	public double getRiskValue(){
		return riskValue;
	}
	
	public double computeAnnualReturns(double given){
		double anReturns = 0.0;
		//System.out.println("Here is the share price " + this.sharePrice);
		//System.out.println("Here is the quartDiv " + this.quarterlyDividend);
		//System.out.println("Here is the baseR " + this.baseRateOfReturn);
		//System.out.println("Here is what is given " + given);
		anReturns = this.quarterlyDividend * 4 + this.baseRateOfReturn/100 * given * this.sharePrice ; //look into this for the decimals
		return anReturns;
	}
	public double computeValueOfAsset(double given){
		double value = given * sharePrice;
		return value;
	}
	
	/**
	 * Obtains the baseRateOfReturn
	 * @return the baseRateOfReturn
	 */
	public double getBaseRateOfReturn() {
		return baseRateOfReturn;
	}

	/**Sets the new baseRateOfReturn
	 * @param baseRateOfReturn the baseRateOfReturn to set
	 */
	public void setBaseRateOfReturn(double baseRateOfReturn) {
		this.baseRateOfReturn = baseRateOfReturn;
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