/**
 * Asset.java
 * An class representing an asset in the financial database
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 1.1
 */
public class Asset {
	private String code;
	private String label;
	private String type;

	/**
	 * Obtains the code
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**Sets the new code
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Obtains the label
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**Sets the new label
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
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