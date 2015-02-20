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



	private void calculateBrokerFees() {
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
		System.out.println("For portfolio : " + this.code);
		for(Asset asset: assetNumeric.keySet()){
			annualReturns.put(asset, asset.computeAnnualReturns(assetNumeric.get(asset)));
			System.out.println("Here is the annual return for "+ asset.getLabel() + " is " + asset.computeAnnualReturns(assetNumeric.get(asset)));
		}
		
		System.out.println();
		
	}



	public double getTotalValue() {
		return totalValue;
	}

	public void calculateTotalRisks(){
		double tRisks = 0;
		for(Asset asset : risks.keySet()){
			tRisks += (risks.get(asset) * values.get(asset))/this.totalValue;
		}
		//System.out.println(" Here is the portfolio " + this.code + " Here is the total risk " + tRisks);
		this.totalRisks = tRisks;
	}
	public void calculateTotalValue() {
		double totalVal = 0;
		for(Double value: values.values()){
			totalVal += value;
		}
		//System.out.println("Total value for portfolio " + this.code + " is " +totalVal);
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
	
	
	private void calculateRisks(){
		risks = new HashMap<Asset,Double>();
		
		for(Asset asset: this.assetNumeric.keySet()){
//			System.out.println("Here is the asset " + asset.getLabel());
//			System.out.println("Here is the risk value " + asset.getRiskValue());
			this.risks.put(asset, asset.getRiskValue());
		}
		
	}
	
	private void calculateValues(){
		values = new HashMap<Asset,Double>();
		for(Asset asset: this.assetNumeric.keySet()){
			this.values.put(asset, asset.computeValueOfAsset(assetNumeric.get(asset)));
		}
		//System.out.println(values);
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