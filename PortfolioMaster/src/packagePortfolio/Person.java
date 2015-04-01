package packagePortfolio;

import java.util.ArrayList;

/**
 * Person.java
 * RAIK 184H
 * This class represents a person using the financial database
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 3.0
 */
public class Person{
	// data members
	private String code;
	private String lastName;
	private String firstName;
	private Address address;
	private ArrayList<String> emails;
	
	/**
	 * Constructor of a Person object
	 * @param code personal code
	 * @param lastName last name
	 * @param firstName first name
	 * @param address address
	 * @param email email
	 */
	public Person(String code, String lastName, String firstName,
			Address address, ArrayList<String> email){
		this.code = code;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.emails = email;
	}
	
	/**
	 * Obtains the code
	 * @return the code
	 */
	public String getCode(){
		return code;
	}
	
	/**Sets the new code
	 * @param code the code to set
	 */
	public void setCode(String code){
		this.code = code;
	}
	
	/**
	 * Obtains the lastName
	 * @return the lastName
	 */
	public String getLastName(){
		return lastName;
	}
	
	/**Sets the new lastName
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	
	/**
	 * Obtains the firstName
	 * @return the firstName
	 */
	public String getFirstName(){
		return firstName;
	}
	
	/**Sets the new firstName
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	
	/**
	 * Obtains the address
	 * @return the address
	 */
	public Address getAddress(){
		return address;
	}
	
	/**Sets the new address
	 * @param address the address to set
	 */
	public void setAddress(Address address){
		this.address = address;
	}
	
	/**
	 * Obtains the emails
	 * @return the emails
	 */
	public ArrayList<String> getEmails(){
		return emails;
	}
	
	/**Sets the new emails
	 * @param emails the emails to set
	 */
	public void setEmails(ArrayList<String> emails){
		this.emails = emails;
	}
}