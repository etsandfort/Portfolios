import java.util.HashMap;
/**
 * 
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 1.0
 */
public class Portfolio {
	//Data Members
	private String code;
	private Person owner;
	private Broker manager;
	private Person beneficiary;
	private HashMap<Asset, Double> assetNumeric;
	private HashMap<Asset,double[]> assetList;
	private double totalRisks;
	private double totalValue;
	private double totalAnnualReturns;
	private double brokerFees;
	private double commissionFees;
	
	/**Portfolio constructor without beneficiary
	 * @param code
	 * @param owner
	 * @param manager
	 * @param assets
	 */
	public Portfolio(String code, Person owner, Broker manager,
			HashMap<Asset, Double> assets) {
		this.code = code;
		this.owner = owner;
		this.manager = manager;
		this.assetNumeric = assets;
		condenseHashMap(assetNumeric);
		calculateTotalValue();
		calculateTotalRisks();
		calculateTotalAnnualReturns();
		calculateBrokerFees();
		calculateCommissionFee();
		calculateReturnRates();
	}
	
	/** Portfolio Constructor, overloaded to take in a beneficiary
	 * @param code
	 * @param owner
	 * @param manager
	 * @param beneficiary
	 * @param assets
	 */
	public Portfolio(String code, Person owner, Broker manager,
			Person beneficiary, HashMap<Asset, Double> assets) {
		this(code,owner,manager,assets);
		this.beneficiary = beneficiary;
	}
	
	/**
	 * condenses the hash map so it is one hash map of 3 different things
	 * @param assetN - Hash Map of assets and their given numeric values from portfolio.dat
	 */
	private void condenseHashMap(HashMap<Asset,Double> assetN) {
		assetList = new HashMap<Asset,double[]>(); //new hashmap of asset, double array
		for(Asset asset: assetN.keySet()){ //for each asset in the HashMap keySet
			assetList.put(asset, new double[]{calculateRisks(asset), calculateAnnualReturns(assetN,asset),calculateValues(assetN,asset)});
			//calls the calculate methods for each asset to get the Risk,AnnualReturns,Values
		}
	}
	
	/**
	 * calculates the return rates, stores them for each Asset
	 */
	private void calculateReturnRates() {
		for(Asset asset: assetNumeric.keySet()){
			asset.computeReturnRate(assetNumeric.get(asset));
		}
	}
	/**
	 * calculates the total annual returns for the portfolio
	 */
	private void calculateTotalAnnualReturns() {
		double tAR = 0.0;
		for(Asset asset : this.assetList.keySet()){
			tAR += assetList.get(asset)[1];
		}
		setTotalAnnualReturns(tAR);
	}
	
	/**
	 * calculates the individual risks
	 */
	 double calculateRisks(Asset asset){
		return asset.getRiskValue();
	}
	
	 /**
	  * calculates the values of the asset
	  */
	private double calculateValues(HashMap<Asset,Double>assetNumerics,Asset asset){
		return asset.computeValueOfAsset(assetNumerics.get(asset));
	}
	
	/**
	 * calculates the commission fees for the portfolio
	 */
	private void calculateCommissionFee() {
		double cFee = 0.0;
		for(Asset asset : assetList.keySet()){
			if(this.manager.getType().equalsIgnoreCase("E")){ //if manager of portfolio is an expert
				cFee += (.05 * assetList.get(asset)[1]);// 5% commission on annual returns
			}
			else{
				cFee += (.02 * assetList.get(asset)[1]);//2% commission on annual returns
			}
		}
		this.commissionFees = cFee;
	}
	/**
	 * calculates the broker fees for portfolio
	 */
	void calculateBrokerFees() {
			double bFees;
			if(this.manager.getType().equalsIgnoreCase("E")){ //if the manager is an expert
				 bFees = (10 * assetList.size()); //$10 fee per asset
			}
			else{ //if manager is junior
				 bFees = (50 * assetList.size()); //$50 fee per asset
			}
			this.brokerFees = bFees;
	}
	/**
	 * calculates the annual returns for the portfolios
	 * @return annualReturns
	 */
	private double calculateAnnualReturns(HashMap<Asset,Double>assetNumerics,Asset asset) {
		return asset.computeAnnualReturns(assetNumeric.get(asset));
	}
	
	/**
	 * calculates the total value of the portfolio
	 */
	public void calculateTotalValue() {
		double totalVal = 0;
		for(Asset asset: assetList.keySet()){ //for each asset in the portfolio
			totalVal += assetList.get(asset)[2]; //add the value of the asset
		}
		this.totalValue = totalVal;
	}
	
	/**
	 * calculates the total aggregate risk for the portfolio
	 */
	public void calculateTotalRisks(){
		double tRisks = 0;
		for(Asset asset : assetList.keySet()){
			tRisks += ((assetList.get(asset)[0])*(assetList.get(asset)[2]))/this.totalValue;
		}
		this.totalRisks = tRisks;
	}
	
	/**
	 * @return the assetList
	 */
	public HashMap<Asset, double[]> getAssetList() {
		return assetList;
	}
	
	/**
	 * obtains the total value of the portfolio
	 * @return totalValue
	 */
	public double getTotalValue() {
		return totalValue;
	}
	
	/**
	 * obtains the asset Numeric
	 * @return the assetNumeric
	 */
	public HashMap<Asset, Double> getAssetNumeric() {
		return assetNumeric;
	}
	/**
	 * sets the asset Numeric
	 * @param assetNumeric the assetNumeric to set
	 */
	public void setAssetNumeric(HashMap<Asset, Double> assetNumeric) {
		this.assetNumeric = assetNumeric;
	}
	/**
	 * obtains the Total Risks
	 * @return the totalRisks
	 */
	public double getTotalRisks() {
		return totalRisks;
	}
	/**
	 * sets the Total Risks
	 * @param totalRisks the totalRisks to set
	 */
	public void setTotalRisks(double totalRisks) {
		this.totalRisks = totalRisks;
	}
	/**
	 * obtains the broker fees
	 * @return the brokerFees
	 */
	public double getBrokerFees() {
		return brokerFees;
	}
	/**
	 * sets the Broker Fees
	 * @param brokerFees the brokerFees to set
	 */
	public void setBrokerFees(double brokerFees) {
		this.brokerFees = brokerFees;
	}
	/**
	 * obtains the total Commission Fees
	 * @return the commissionFees
	 */
	public double getCommissionFees() {
		return commissionFees;
	}
	/**
	 * sets the total Commission Fees of the portfolio
	 * @param commissionFees the commissionFees to set
	 */
	public void setCommissionFees(double commissionFees) {
		this.commissionFees = commissionFees;
	}
	/**
	 * sets the Total Value of the portfolio
	 * @param totalValue the totalValue to set
	 */
	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}
	/**
	 * obtains the code
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * sets the code
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * obtains the owner
	 * @return the owner
	 */
	public Person getOwner() {
		return owner;
	}
	/**
	 * sets the owner
	 * @param owner the owner to set
	 */
	public void setOwner(Person owner) {
		this.owner = owner;
	}
	/**
	 * obtains the manager
	 * @return the manager
	 */
	public Broker getManager() {
		return manager;
	}
	/**
	 * sets the manager
	 * @param manager the manager to set
	 */
	public void setManager(Broker manager) {
		this.manager = manager;
	}
	/**
	 * obtains the beneficiary
	 * @return the beneficiary
	 */
	public Person getBeneficiary() {
		return beneficiary;
	}
	/**
	 * sets the Beneficiary
	 * @param beneficiary the beneficiary to set
	 */
	public void setBeneficiary(Person beneficiary) {
		this.beneficiary = beneficiary;
	}
	/**
	 * obtains the total annual Returns
	 * @return the totalAnnualReturns
	 */
	public double getTotalAnnualReturns() {
		return totalAnnualReturns;
	}
	/**
	 * sets the total Annual Returns
	 * @param totalAnnualReturns the totalAnnualReturns to set
	 */
	public void setTotalAnnualReturns(double totalAnnualReturns) {
		this.totalAnnualReturns = totalAnnualReturns;
	}
}