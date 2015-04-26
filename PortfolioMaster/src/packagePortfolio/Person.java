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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Person.java
 * RAIK 184H
 * This class represents a person using the financial database
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 3.0
 */
@Entity
@Table(name="Person")
public class Person implements Serializable {

	private static final long serialVersionUID = -814095199153603476L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="person_id", nullable=false)
	private Integer personId;
	
	@Column(name="code", nullable=false)
	private String code;
	
	@Column(name="lastName", nullable=false)
	private String lastName;
	
	@Column(name="firstName", nullable=false)
	private String firstName;
	
	@Column(name="secID", nullable=true)
	private String secIdentifier;
	
	@Column(name="brokerType", nullable=true)
	private String brokerType; 
	
	@OneToOne
	@JoinColumn(name="person_id", nullable=false)
	private Address address;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="person")
	private Set<Email> emails;
	
	public Person() {}
	
	/**
	 * Constructor of a Person object
	 * @param code personal code
	 * @param lastName last name
	 * @param firstName first name
	 * @param address address
	 * @param emails emails
	 */
	public Person(String code, String brokerType, String sec, String lastName, String firstName,
			Address address, Set<Email> emails){
		this.code = code;
		this.firstName = firstName;
		this.lastName = lastName;
		this.brokerType = brokerType;
		this.secIdentifier = sec;
		this.address = address;
		this.emails = emails;
	}
	
	/**
	 * Gets the primary key, an Integer
	 * @return personId
	 */
	public Integer getPersonId() {
		return personId;
	}

	/**
	 * Sets the primary key
	 * @param personId
	 */
	public void setPersonId(Integer personId) {
		this.personId = personId;
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
	public Set<Email> getEmails(){
		return emails;
	}
	
	/**Sets the new emails
	 * @param emails the emails to set
	 */
	public void setEmails(Set<Email> emails){
		this.emails = emails;
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
	 * Obtains the broker type
	 * @return type
	 */
	public String getBrokerType(){
		return brokerType;
	}
	
	/**
	 * Sets the new broker type
	 * @param brokerType
	 */
	public void setType(String brokerType){
		this.brokerType = brokerType;
	}
}