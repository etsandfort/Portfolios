package packagePortfolio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="PortfolioAsset")
public class PortfolioAsset implements Serializable {

	private static final long serialVersionUID = -1117015480297833348L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="portAsset_id", nullable=false)
	private Integer portAssetId;
	
	@ManyToOne
	@JoinColumn(name="asset_id")
	private Asset asset;

	@ManyToOne
	@JoinColumn(name="portfolio_id", nullable=false)
	private Portfolio portfolio;
	
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
	 * Gets the asset associated with
	 * PortfolioAsset
	 * @return asset, an Asset
	 */
	public Asset getAsset() {
		return asset;
	}

	/**
	 * Gets the portfolio associated with
	 * PortfolioAsset
	 * @return portfolio, a Portfolio
	 */
	public Portfolio getPortfolio() {
		return portfolio;
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