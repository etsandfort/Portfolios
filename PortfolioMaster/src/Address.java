/**Address.java
 * RAIK 184H
 * This class represents the address of a person in the financial database
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 1.0
 */
public class Address {
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
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**Sets the new street
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Obtains the city
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**Sets the new city
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Obtains the state
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**Sets the new state
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Obtains the zipcode
	 * @return the zipcode
	 */
	public String getZipcode() {
		return zipcode;
	}

	/**Sets the new zipcode
	 * @param zipcode the zipcode to set
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	/**
	 * Obtains the country
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**Sets the new country
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
}