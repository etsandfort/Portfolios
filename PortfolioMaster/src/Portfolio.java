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
	private HashMap<Asset,Double> risks;
	private double totalRisks;
	private HashMap<Asset,Double> values;
	private double totalValue;
	private HashMap<Asset,Double>annualReturns;
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
		calculateRisks();
		calculateValues();
		calculateTotalValue();
		calculateTotalRisks();
		calculateAnnualReturns();
		calculateBrokerFees();
		calculateCommissionFee();
	}
	
	private void calculateCommissionFee() {
		double cFee = 0.0;
		for(Double value : annualReturns.values()){
			if(this.manager.getType().equalsIgnoreCase("E")){
				cFee += (.05 * value);
			}
			else{
				cFee += (.02 * value);
			}
		}
		this.commissionFees = cFee;
	}

	void calculateBrokerFees() {
			double bFees;
			if(this.manager.getType().equalsIgnoreCase("E")){
				 bFees = (10 * annualReturns.size());
			}
			else{
				 bFees = (50 * annualReturns.size());
			}
			this.brokerFees = bFees;
	}

	private void calculateAnnualReturns() {
		annualReturns = new HashMap<Asset,Double>();
		for(Asset asset: assetNumeric.keySet()){
			annualReturns.put(asset, asset.computeAnnualReturns(assetNumeric.get(asset)));
		}
	}

	public double getTotalValue() {
		return totalValue;
	}

	public void calculateTotalRisks(){
		double tRisks = 0;
		for(Asset asset : risks.keySet()){
			tRisks += (risks.get(asset) * values.get(asset))/this.totalValue;
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
	 * @return the risks
	 */
	public HashMap<Asset, Double> getRisks() {
		return risks;
	}

	/**
	 * @param risks the risks to set
	 */
	public void setRisks(HashMap<Asset, Double> risks) {
		this.risks = risks;
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
	 * @return the values
	 */
	public HashMap<Asset, Double> getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(HashMap<Asset, Double> values) {
		this.values = values;
	}

	/**
	 * @return the annualReturns
	 */
	public HashMap<Asset, Double> getAnnualReturns() {
		return annualReturns;
	}

	/**
	 * @param annualReturns the annualReturns to set
	 */
	public void setAnnualReturns(HashMap<Asset, Double> annualReturns) {
		this.annualReturns = annualReturns;
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
		for(Double value: values.values()){
			totalVal += value;
		}
		this.totalValue = totalVal;
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
	
	 void calculateRisks(){
		risks = new HashMap<Asset,Double>();
		
		for(Asset asset: this.assetNumeric.keySet()){

			this.risks.put(asset, asset.getRiskValue());
		}
	}
	
	private void calculateValues(){
		values = new HashMap<Asset,Double>();
		for(Asset asset: this.assetNumeric.keySet()){
			this.values.put(asset, asset.computeValueOfAsset(assetNumeric.get(asset)));
		}
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
}