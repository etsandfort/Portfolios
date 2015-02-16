/**
 * Deposit.java
 * RAIK 184H
 * This class represents a deposit account asset in the financial database
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 1.0
 */
public class Deposit extends Asset{
	//Data members
	private double apr;
	
	/**
	 * The constructor for a deposit object
	 * @param code deposit code
	 * @param label deposit label
	 * @param type deposit type
	 * @param apr deposit APR
	 */
	public Deposit(String code, String label, String type, double apr){
		setCode(code);
		setLabel(label);
		setType(type);
		setApr(apr);
	}

	/**
	 * Obtains the apr
	 * @return the apr
	 */
	public double getApr() {
		return apr;
	}

	/**Sets the new apr
	 * @param apr the apr to set
	 */
	public void setApr(double apr) {
		this.apr = apr;
	}
}