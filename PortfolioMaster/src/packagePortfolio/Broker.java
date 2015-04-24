package packagePortfolio;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import java.util.ArrayList;
import java.util.Set;
/**
 * Broker.java
 * RAIK 184H
 * This class represents brokers in the financial database
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 3.0
 */

public class Broker extends Person{

//	private static final long serialVersionUID = -6370419024780423414L;
	//Data members
	private String secIdentifier;
	private char type;
	
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
	public Broker(String code, char type, String sec, String lastName, String firstName,
			Address address, Set<Email> email){
		super(code, null, null, lastName, firstName, address, email);
		this.secIdentifier =sec;
		this.type = type;
	}
	
	/**
	 * Obtains the secIdentifier
	 * @return secIdentifier
	 */
	public String getSecIdentifier(){
		return secIdentifier;
	}
	
	/**
	 * Sets the new secIdentifier
	 * @param secIdentifier
	 */
	public void setSecIdentifier(String secIdentifier){
		this.secIdentifier = secIdentifier;
	}
	
	/**
	 * Obtains the type
	 * @return type
	 */
	public char getType(){
		return type;
	}
	
	/**
	 * Sets the new type
	 * @param type
	 */
	public void setType(char type){
		this.type = type;
	}
}