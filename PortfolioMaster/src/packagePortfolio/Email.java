package packagePortfolio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Email")
public class Email implements Serializable {

	private static final long serialVersionUID = -5607706583489474430L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="email_id", nullable=false)
	private Integer emailId;	
	
	@Column(name="emailAddress", nullable=false)
	private String emailAddress;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="person_id", nullable=false)
	private Person person;

	public Email() {}
	
	/**
	 * Constructor for Email
	 * @param emailAddress
	 */
	public Email(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	/**
	 * Gets the primary key, an Integer
	 * @return
	 */
	public Integer getEmailId() {
		return emailId;
	}

	/**
	 * Sets the primary key
	 * @param emailId
	 */
	public void setEmailId(Integer emailId) {
		this.emailId = emailId;
	}

	/**
	 * Gets email address
	 * @return emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Sets the email address to an input String
	 * @param emailAddress, a String
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	/**
	 * Gets the Person associated with the email
	 * @return person
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * Sets the person associated with the email
	 * @param person
	 */
	public void setPerson(Person person) {
		this.person = person;
	}
}