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
	private HashMap<Asset, Double> assets;
	
	/**Portfolio constructor without beneficiary
	 * @param code
	 * @param owner
	 * @param manager
	 * @param assets
	 */
	public Portfolio(String code, Person owner, Broker manager,
			HashMap<Asset, Double> assets) {
		super();
		this.code = code;
		this.owner = owner;
		this.manager = manager;
		this.assets = assets;
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
		super();
		this.code = code;
		this.owner = owner;
		this.manager = manager;
		this.beneficiary = beneficiary;
		this.assets = assets;
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