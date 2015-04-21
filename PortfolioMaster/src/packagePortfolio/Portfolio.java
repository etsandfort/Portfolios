package packagePortfolio;

import java.util.HashMap;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Portfolio.java
 * RAIK 184H
 * Template for portfolio objects in the financial management system.
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 3.0
 */
@Entity
@Table(name="Portfolio")
public class Portfolio implements Serializable {

	private static final long serialVersionUID = 4092377723123588407L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	//@ManyToOne(fetch=FetchType.EAGER)
	//@JoinColumn(name="portfolio_id", nullable=false)
	@Column(name="portfolio_id", nullable=false)
	private Integer portfolioId;
	
	@Column(name="code", nullable=false)
	private String code;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="owner_id", nullable=false)
	//private String ownerId;
	private Person owner;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="manager_id", nullable=false)
	//private String managerId;
	private Person manager; //TODO Broker.....
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="beneficiary_id")
//	private String beneficiaryId;
	private Person beneficiary;
	
	@Transient
	private HashMap<Asset, Double> assetNumeric;
	
	@Transient
	private HashMap<Asset,double[]> assetList;
	
	@Transient
	private double totalRisks;
	
	@Transient
	private double totalValue;
	
	@Transient
	private double totalAnnualReturns;
	
	@Transient
	private double brokerFees;
	
	

	@Transient
	private double commissionFees;
	
	public Portfolio() {}
	
	/**
	 * Portfolio constructor without beneficiary
	 * @param code
	 * @param owner
	 * @param manager
	 * @param assets
	 */
	public Portfolio(String code, Person owner, Broker manager,
			HashMap<Asset, Double> assets){
		this.code = code;
		//this.owner = owner;
		//this.manager = manager;
		this.assetNumeric = assets;
		condenseHashMap(assetNumeric);
		calculateTotalValue();
		calculateTotalRisks();
		calculateTotalAnnualReturns();
		calculateBrokerFees();
		calculateCommissionFee();
		calculateReturnRates();
	}
	
	/** 
	 * Portfolio Constructor, overloaded to take in a beneficiary
	 * @param code
	 * @param owner
	 * @param manager
	 * @param beneficiary
	 * @param assets
	 */
	public Portfolio(String code, Person owner, Broker manager,
			Person beneficiary, HashMap<Asset, Double> assets){
		this(code,owner,manager,assets);
		//this.beneficiary = beneficiary;
	}
	
	public Portfolio(String code, Person owner, Broker manager){
		this.code = code;
		//this.owner = owner;
		//this.manager = manager;
		this.setBrokerFees(0);
		this.setCommissionFees(0);
		this.setTotalValue(0);
		this.setTotalAnnualReturns(0);
		this.setTotalRisks(0);	
	}
	
	public Portfolio(String code, Person owner, Broker manager, Person beneficiary){
		this(code, owner, manager);
		//this.beneficiary = beneficiary;
	}
	
	/**
	 * This method condenses the hash map so that it is one hash map of 3 different things
	 * @param assetNumeric - Hash Map of assets and their given numeric values from Portfolios.dat
	 */
	private void condenseHashMap(HashMap<Asset,Double> assetNumeric){
		assetList = new HashMap<Asset,double[]>(); //new hashmap of asset, double array
		for(Asset asset: assetNumeric.keySet()){ //for each asset in the HashMap keySet
			assetList.put(asset, new double[]{calculateRisks(asset), calculateAnnualReturns(assetNumeric,asset),calculateValues(assetNumeric,asset)});
			//calls the calculate methods for each asset to get the Risk,AnnualReturns,Values
		}
	}
	
	/**
	 * Calculates the return rates and stores them for each Asset
	 */
	private void calculateReturnRates(){
		for(Asset asset: assetNumeric.keySet()){
			asset.computeReturnRate(assetNumeric.get(asset));
		}
	}
	
	/**
	 * Calculates the total annual returns of a portfolio
	 */
	private void calculateTotalAnnualReturns(){
		double totalAR = 0.0; // total annual returns
		for(Asset asset : this.assetList.keySet()){
			totalAR += assetList.get(asset)[1];
		}
		setTotalAnnualReturns(totalAR);
	}
	
	/**
	 * Calculates the individual risks of input asset
	 */
	 double calculateRisks(Asset asset){
		return asset.getRiskValue();
	}
	
	/**
	 * Calculates the values of the asset
	 * @return the value of the asset
	 */
	private double calculateValues(HashMap<Asset,Double>assetNumerics,Asset asset){
		return asset.computeValueOfAsset(assetNumerics.get(asset)); // gets the asset from the hashmap and computes its value
	}
	
	//TODO fix implementation
	/**
	 * Calculates the commission fees for a portfolio
	 */
	private void calculateCommissionFee(){
		double commissionFee = 0.0;
//		for(Asset asset : assetList.keySet()){
//			if(this.manager.getType()=='E'){ //if manager of portfolio is an expert
//				commissionFee += (.05 * assetList.get(asset)[1]);// 5% commission on annual returns
//			}
//			else{
//				commissionFee += (.02 * assetList.get(asset)[1]);//2% commission on annual returns
//			}
//		}
//		this.commissionFees = commissionFee;
	}

	
	//TODO fix implementation
	/**
	 * Calculates the broker fees for a portfolio
	 */
	void calculateBrokerFees(){ // package level visibility
			double brokerFees;
//			
//			if(this.manager.getType()=='E'){ //if the manager is an expert
//				 brokerFees = (10 * assetList.size()); //$10 fee per asset
//			}
//			
//			else{ //if manager is junior
//				
//				brokerFees = (50 * assetList.size()); //$50 fee per asset
//			}
//			this.brokerFees = brokerFees;
	}
	
	/**
	 * Calculates the annual returns for the portfolios
	 * @return annualReturns
	 */
	private double calculateAnnualReturns(HashMap<Asset,Double>assetNumerics,Asset asset){
		return asset.computeAnnualReturns(assetNumeric.get(asset));
	}
	
	/**
	 * Calculates the total value of the portfolio
	 */
	public void calculateTotalValue(){
		double totalVal = 0;
		for(Asset asset: assetList.keySet()){ //for each asset in the portfolio
			totalVal += assetList.get(asset)[2]; //add the value of the asset
		}
		this.totalValue = totalVal;
	}
	
	/**
	 * Calculates the total aggregate risk.
	 */
	public void calculateTotalRisks(){
		double totalRisks = 0;
		for(Asset asset : assetList.keySet()){
			totalRisks += ((assetList.get(asset)[0])*(assetList.get(asset)[2]))/this.totalValue;
		}
		this.totalRisks = totalRisks;
	}
	
	/**
	 * Obtains the HashMap assetList
	 * @return the assetList
	 */
	public HashMap<Asset, double[]> getAssetList(){
		return assetList;
	}
	
	/**
	 * Obtains the total value of the portfolio
	 * @return totalValue
	 */
	public double getTotalValue(){
		return totalValue;
	}
	
	/**
	 * Obtains the HashMap of assets to numeric values
	 * @return the assetNumeric 
	 */
	public HashMap<Asset, Double> getAssetNumeric(){
		return assetNumeric;
	}
	
	/**
	 * Sets the asset Numeric HashMap
	 * @param assetNumeric
	 */
	public void setAssetNumeric(HashMap<Asset, Double> assetNumeric){
		this.assetNumeric = assetNumeric;
	}
	
	/**
	 * Obtains the Total Risks
	 * @return totalRisks, a double
	 */
	public double getTotalRisks(){
		return totalRisks;
	}
	
	/**
	 * Sets the Total Risks
	 * @param totalRisks
	 */
	public void setTotalRisks(double totalRisks){
		this.totalRisks = totalRisks;
	}
	
	/**
	 * Obtains the broker fees
	 * @return brokerFees, a double
	 */
	public double getBrokerFees(){
		return brokerFees;
	}
	
	/**
	 * sets the Broker Fees
	 * @param brokerFees the brokerFees to set
	 */
	public void setBrokerFees(double brokerFees){
		this.brokerFees = brokerFees;
	}
	
	/**
	 * Obtains the total Commission Fees
	 * @return commissionFees, a double
	 */
	public double getCommissionFees(){
		return commissionFees;
	}
	
	/**
	 * Sets the total Commission Fees of the portfolio
	 * @param commissionFees, a double
	 */
	public void setCommissionFees(double commissionFees){
		this.commissionFees = commissionFees;
	}
	
	/**
	 * Sets the Total Value of the portfolio
	 * @param totalValue, a double
	 */
	public void setTotalValue(double totalValue){
		this.totalValue = totalValue;
	}
	
	/**
	 * Obtains the portfolio code
	 * @return code, a String
	 */
	public String getCode(){
		return code;
	}
	
	/**
	 * Sets the portfolio code
	 * @param code, a String
	 */
	public void setCode(String code){
		this.code = code;
	}
	
//	/**
//	 * Obtains the portfolio's owner
//	 * @return owner, a Person object
//	 */
//	public Person getOwner(){
//		return owner;
//	}
//	
//	/**
//	 * Sets the portfolio's owner
//	 * @param owner, a Person object
//	 */
//	public void setOwner(Person owner){
//		this.owner = owner;
//	}
//	
//	/**
//	 * Obtains the portfolio's manager
//	 * @return manager, a Broker object
//	 */
//	public Broker getManager(){
//		return manager;
//	}
//	
//	/**
//	 * Sets the portfolio's manager
//	 * @param manager, a Broker object
//	 */
//	public void setManager(Broker manager){
//		this.manager = manager;
//	}
//	
//	/**
//	 * Obtains the portfolio's beneficiary
//	 * @return beneficiary, a Person object
//	 */
//	public Person getBeneficiary(){
//		return beneficiary;
//	}
//	
//	/**
//	 * Sets the portfolio's beneficiary
//	 * @param beneficiary
//	 */
//	public void setBeneficiary(Person beneficiary){
//		this.beneficiary = beneficiary;
//	}
	
	/**
	 * Obtains the Total Annual Returns
	 * @return totalAnnualReturns, a double
	 */
	public double getTotalAnnualReturns(){
		return totalAnnualReturns;
	}
	
	/**
	 * Sets the Total Annual Returns
	 * @param totalAnnualReturns the totalAnnualReturns to set
	 */
	public void setTotalAnnualReturns(double totalAnnualReturns){
		this.totalAnnualReturns = totalAnnualReturns;
	}
	
	/**
	 * Gets the primary key of the portfolio
	 * @return portfolioId, an Integer
	 */
	public Integer getPortfolioId() {
		return portfolioId;
	}

	/**
	 * Sets the primary key of the portfolio
	 * @param portfolioId, an Integer
	 */
	public void setPortfolioId(Integer portfolioId) {
		this.portfolioId = portfolioId;
	}
	
	/**
	 * Gets the primary key of the owner
	 * of the portfolio.
	 * @return ownerId
	 */
	public Person getOwnerId() {
		return owner;
	}
	
	/**
	 * Gets the primary key of the manager
	 * of the portfolio.
	 * @return managerId
	 */
	public Person getManagerId() {
		return manager;
	}

	/**
	 * Gets the primary key of the beneficiary
	 * of the portfolio. Can be null.
	 * @return beneficiaryId
	 */
	public Person getBeneficiaryId() {
		return beneficiary;
	}
	
}