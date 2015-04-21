package packagePortfolio;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="PortfolioAsset")
public class PortfolioAsset implements Serializable {

	private static final long serialVersionUID = -1117015480297833348L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="portAsset_id", nullable=false)
	private Integer portAssetId;
	
//  @Transient	
//	@OneToMany(fetch=FetchType.EAGER)
//	@JoinColumn(name="asset_id")
//	private Set<Integer> assetId;

	@OneToMany(targetEntity = Asset.class)
	private Set<Asset> assetId;

//  @Transient
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="portfolio_id", nullable=false)
	private Set<Portfolio> portfolioId;
	
	//@OneToMany(fetch=FetchType.EAGER)
	//, targetEntity = Portfolio.class)
	//private Set<Portfolio> portfolioId;
	
	@Column(name="givenValue")
	private double givenValue;

	public PortfolioAsset() {}
	
	/**
	 * Gets the primary key value of the PortfolioAsset
	 * @return portAssetId, an Integer
	 */
	public Integer getPortAssetId() {
		return portAssetId;
	}

	/**
	 * Gets the primary key of asset associated with
	 * PortfolioAsset
	 * @return assetId, an Integer
	 */
	public Set<Asset> getAssetId() {
		return assetId;
	}

	/**
	 * Gets the primary key of portfolio associated with
	 * PortfolioAsset
	 * @return portfolioId, an integer
	 */
	public Set<Portfolio> getPortfolioId() {
		return portfolioId;
	}

	/**
	 * Gets the givenValue of the PortfolioAsset
	 * @return givenValue, a double
	 */
	public double getGivenValue() {
		return givenValue;
	}
	
	/**
	 * Sets the value of the portfolioAsset
	 * @param givenValue, a double
	 */
	public void setGivenValue(double givenValue) {
		this.givenValue = givenValue;
	}
}