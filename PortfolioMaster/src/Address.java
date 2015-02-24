/**
 * Address.java
 * RAIK 184H
 * This class represents the address of a person in the financial database
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 3.0
 */
public class Address{
	//Data members
	private String street;
	private String city;
	private String state;
	private String zipcode;
	private String country;

	/**
	 * Constructor of an address
	 * @param street the street
	 * @param city the city
	 * @param state the state
	 * @param zip the zip code
	 * @param country the country
	 */
	public Address(String street, String city, String state, String zip, String country){
		this.street= street;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipcode=zip;
	}

	/**
	 * Obtains the street
	 * @return street, a String
	 */
	public String getStreet(){
		return street;
	}

	/**
	 * Sets the new street
	 * @param street
	 */
	public void setStreet(String street){
		this.street = street;
	}

	/**
	 * Obtains the city
	 * @return city, a String
	 */
	public String getCity(){
		return city;
	}

	/**
	 * Sets the new city
	 * @param city, a String
	 */
	public void setCity(String city){
		this.city = city;
	}

	/**
	 * Obtains the state
	 * @return state, a String
	 */
	public String getState(){
		return state;
	}

	/**Sets the new state
	 * @param state, a String
	 */
	public void setState(String state){
		this.state = state;
	}

	/**
	 * Obtains the zipcode
	 * @return zipcode, a String
	 */
	public String getZipcode(){
		return zipcode;
	}

	/**Sets the new zipcode
	 * @param zipcode, a String
	 */
	public void setZipcode(String zipcode){
		this.zipcode = zipcode;
	}

	/**
	 * Obtains the country
	 * @return country, a String
	 */
	public String getCountry(){
		return country;
	}

	/**
	 * Sets the new country
	 * @param country, a String
	 */
	public void setCountry(String country){
		this.country = country;
	}
}