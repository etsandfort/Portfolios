package packagePortfolio;

import java.io.Serializable;
//import java.util.HashSet;
//import java.util.Set;
//import java.util.ArrayList;



import javax.persistence.Column;
//import javax.persistence.DiscriminatorColumn;
//import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
//import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
//import javax.persistence.Inheritance;
//import javax.persistence.InheritanceType;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Address.java
 * RAIK 184H
 * This class represents the address of a person in the financial database
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 3.0
 */
@Entity
@Table(name="Address")
public class Address implements Serializable{
	
	private static final long serialVersionUID = 4461398335909762579L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="address_id", nullable=false)
	private Integer addressId;	
	
	@Column(name="street", nullable=false)
	private String street;
	
	@Column(name="city", nullable=false)
	private String city;
	
	@Column(name="state")
	private String state;
	
	@Column(name="zipcode", nullable=false)
	private String zipcode;
	
	@Column(name="country", nullable=false)
	private String country;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="person_id", nullable=false)
	private Person person;

	public Address() {}
	
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
	 * Gets the primary key, an Integer
	 * @return addressId
	 */
	public Integer getAddressId() {
		return addressId;
	}

	/**
	 * Sets the primary key
	 * @param addressId
	 */
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
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
	
	/**
	 * Gets the person associated with an address
	 * @return person
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * Sets the person associated with an address
	 * @param person
	 */
	public void setPerson(Person person) {
		this.person = person;
	}
}