import java.util.ArrayList;
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
		//calculateRisks();
		//calculateValues();
		condenseHashMap(assetNumeric);
		calculateTotalValue();
		calculateTotalRisks();
		//calculateAnnualReturns();
		calculateTotalAnnualReturns();
		calculateBrokerFees();
		calculateCommissionFee();
		calculateReturnRates();
		//condenseHashMap(risks,values,annualReturns);
		
	}
	
	private void condenseHashMap(HashMap<Asset,Double> assetN) {
		assetList = new HashMap<Asset,double[]>();
		for(Asset asset: assetN.keySet()){
			//assetList.put(asset, new double[]{risksGiven.get(asset),annualReturnsGiven.get(asset),valuesGiven.get(asset)});
			assetList.put(asset, new double[]{calculateRisks(asset), calculateAnnualReturns(assetN,asset),calculateValues(assetN,asset)});
		}
	}
	
	
	private void calculateReturnRates() {
		for(Asset asset: assetNumeric.keySet()){
			asset.computeReturnRate(assetNumeric.get(asset));
		}
		
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
	
	private void condenseHashMap(HashMap<Asset, Double> risksGiven,
			HashMap<Asset, Double> valuesGiven,
			HashMap<Asset, Double> annualReturnsGiven) {
		assetList = new HashMap<Asset,double[]>();
		for(Asset asset: risksGiven.keySet()){
			assetList.put(asset, new double[]{risksGiven.get(asset),annualReturnsGiven.get(asset),valuesGiven.get(asset)});
		}
	}

	/**
	 * @return the assetList
	 */
	public HashMap<Asset, double[]> getAssetList() {
		return assetList;
	}

	/**
	 * @param assetList the assetList to set
	 */
	public void setAssetList(HashMap<Asset, double[]> assetList) {
		this.assetList = assetList;
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
		//risks = new HashMap<Asset,Double>
		/*for(Asset asset: this.assetNumeric.keySet()){ //for each asset in the asset numeric
			this.risks.put(asset, asset.getRiskValue()); //gets asset risk value
		}*/
		return asset.getRiskValue();
	}
	
	 /**
	  * calculates the values of the asset
	  */
	private double calculateValues(HashMap<Asset,Double>assetNumerics,Asset asset){
		//values = new HashMap<Asset,Double>();
		/*for(Asset asset: this.assetNumeric.keySet()){ //for each asset
			this.values.put(asset, asset.computeValueOfAsset(assetNumeric.get(asset)));
		}*/
		return asset.computeValueOfAsset(assetNumerics.get(asset));
	}
	
	/**
	 * calculates the commission fees for the portfolio
	 */
	private void calculateCommissionFee() {
		double cFee = 0.0;
		for(Asset asset : assetList.keySet()){
			if(this.manager.getType().equalsIgnoreCase("E")){ //if manager of portfolio is an expert
				cFee += (.05 * assetList.get(asset)[1]);// 5% commission
			}
			else{
				cFee += (.02 * assetList.get(asset)[1]);//2% commission
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
				 bFees = (10 * assetList.size()); //10 times 
			}
			else{
				 bFees = (50 * assetList.size());
			}
			this.brokerFees = bFees;
	}

	/**
	 * calculates the annual returns for the portfolios
	 * @return 
	 */
	private double calculateAnnualReturns(HashMap<Asset,Double>assetNumerics,Asset asset) {
		//annualReturns = new HashMap<Asset,Double>();
		/*for(Asset asset: assetNumeric.keySet()){
			annualReturns.put(asset, asset.computeAnnualReturns(assetNumeric.get(asset)));
		}	*/
		return asset.computeAnnualReturns(assetNumeric.get(asset));
	}
	
	/**
	 * 
	 * @return totalValue
	 */
	public double getTotalValue() {
		return totalValue;
	}
	
	/**
	 * calculates the total aggregate risk for the portfolio
	 */
	public void calculateTotalRisks(){
		double tRisks = 0;
		for(Asset asset : assetList.keySet()){
			//tRisks += (risks.get(asset) * values.get(asset))/this.totalValue;
			tRisks += ((assetList.get(asset)[0])*(assetList.get(asset)[2]))/this.totalValue;
		}
		this.totalRisks = tRisks;
	}
	
	/**
	 * @return the assetNumeric
	 */
	public HashMap<Asset, Double> getAssetNumeric() {
		return assetNumeric;
	}

	/**
	 * @param assetNumeric the assetNumeric to set
	 */
	public void setAssetNumeric(HashMap<Asset, Double> assetNumeric) {
		this.assetNumeric = assetNumeric;
	}

	

	

	/**
	 * @return the totalRisks
	 */
	public double getTotalRisks() {
		return totalRisks;
	}

	/**
	 * @param totalRisks the totalRisks to set
	 */
	public void setTotalRisks(double totalRisks) {
		this.totalRisks = totalRisks;
	}

	

	

	

	/**
	 * @return the brokerFees
	 */
	public double getBrokerFees() {
		return brokerFees;
	}

	/**
	 * @param brokerFees the brokerFees to set
	 */
	public void setBrokerFees(double brokerFees) {
		this.brokerFees = brokerFees;
	}

	/**
	 * @return the commissionFees
	 */
	public double getCommissionFees() {
		return commissionFees;
	}

	/**
	 * @param commissionFees the commissionFees to set
	 */
	public void setCommissionFees(double commissionFees) {
		this.commissionFees = commissionFees;
	}

	/**
	 * @param totalValue the totalValue to set
	 */
	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}



	public void calculateTotalValue() {
		double totalVal = 0;
		for(Asset asset: assetList.keySet()){
			totalVal += assetList.get(asset)[2];
		}
		
		this.totalValue = totalVal;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the owner
	 */
	public Person getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(Person owner) {
		this.owner = owner;
	}

	/**
	 * @return the manager
	 */
	public Broker getManager() {
		return manager;
	}

	/**
	 * @param manager the manager to set
	 */
	public void setManager(Broker manager) {
		this.manager = manager;
	}

	/**
	 * @return the beneficiary
	 */
	public Person getBeneficiary() {
		return beneficiary;
	}

	/**
	 * @param beneficiary the beneficiary to set
	 */
	public void setBeneficiary(Person beneficiary) {
		this.beneficiary = beneficiary;
	}


	/**
	 * @return the totalAnnualReturns
	 */
	public double getTotalAnnualReturns() {
		return totalAnnualReturns;
	}


	/**
	 * @param totalAnnualReturns the totalAnnualReturns to set
	 */
	public void setTotalAnnualReturns(double totalAnnualReturns) {
		this.totalAnnualReturns = totalAnnualReturns;
	}
}