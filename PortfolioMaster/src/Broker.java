import java.util.ArrayList;
/**
 * Broker.java
 * RAIK 184H
 * This class represents brokers in the financial database
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 1.0
 */

public class Broker extends Person{
	//Data members
	private String secIdentifier;
	private String type;
	
	/**
	 * Constructor of a broker
	 * @param code broker code
	 * @param type type of broker
	 * @param sec sec identifier
	 * @param lastName last name
	 * @param firstName first name
	 * @param address broker's address
	 * @param email broker's email(s)
	 */
	public Broker(String code, String type, String sec, String lastName, String firstName,
			Address address, ArrayList<String> email) {
		super(code, lastName, firstName, address, email);
		this.secIdentifier =sec;
		this.type = type;
	}
	
	/**
	 * Obtains the secIdentifier
	 * @return the secIdentifier
	 */
	public String getSecIdentifier() {
		return secIdentifier;
	}
	
	/**Sets the new secIdentifier
	 * @param secIdentifier the secIdentifier to set
	 */
	public void setSecIdentifier(String secIdentifier) {
		this.secIdentifier = secIdentifier;
	}
	
	/**
	 * Obtains the type
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	/**Sets the new type
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
}