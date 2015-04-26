package packagePortfolio;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.InheritanceType;

/**
 * Asset.java
 * RAIK 184H
 * An class representing an asset in the financial database
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 3.0
 */

@Entity
@Table(name="Asset")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type")
public class Asset implements Serializable{

	private static final long serialVersionUID = 3718960773196533638L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	//@ManyToOne(fetch=FetchType.EAGER)
	//@JoinColumn(name="asset_id")
	@Column(name="asset_id", nullable=false)
	private Integer assetId;
	
	@Column(name="code", nullable=false)
	private String code;
	
	@Column(name="label", nullable=false)
	private String label;
	
//	@Column(name="type", nullable=false)
//	private String type;
	
//	@ManyToMany(cascade = CascadeType.ALL)
//	@JoinTable(
//			name = "PortfolioAsset",
//			joinColumns = @JoinColumn(name = "asset_id"),
//			inverseJoinColumns = @JoinColumn(name = "portfolio_id"))
//	private Set<PortfolioAsset> portAssets;
	
	@OneToMany//(mappedBy = "Asset")
	private Set<PortfolioAsset> portfolios;
	
	@Transient
	protected double riskValue; // TODO check this 
	
	@Column(name="baseRate", nullable=false)
	protected double baseRate;
	
	@Transient
	protected double returnRate; // TODO check this
	
	public Asset() {}
	
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
	
//	/**
//	 * Obtains the type
//	 * @return the type
//	 */
//	public String getType(){
//		return type;
//	}
//	
//	/**
//	 * Sets the new type
//	 * @param type
//	 */
//	public void setType(String type){
//		this.type = type;
//	}
	
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
	
	/**
	 * Gets the primary key, an Integer
	 * @return
	 */
	public Integer getAssetId() {
		return assetId;
	}

	/**
	 * Sets the primary key
	 * @param assetId
	 */
	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}
}