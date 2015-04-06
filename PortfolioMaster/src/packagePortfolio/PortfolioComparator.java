package packagePortfolio;

import java.util.Comparator;

/**
 * PortfolioComparator.java
 * RAIK 184H
 * This class holds the three assigned comparators by which to sort portfolios.
 * 
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 1.2
 */
public class PortfolioComparator {

	/**
	 * This comparator method compares portfolios by owner's last name. If the 
	 * last name is the same, owner's first name is compared.
	 * @return integer value of the comparison
	 */
	public static Comparator<Portfolio> ownerComparator(){
		return new Comparator<Portfolio>(){

			public int compare(Portfolio a, Portfolio b){
				int compareLastName = b.getOwner().getLastName().compareTo(a.getOwner().getLastName());
				if(compareLastName==0){
					return b.getOwner().getFirstName().compareTo(a.getOwner().getFirstName());
				} 
				return compareLastName;
			}
		};
	}

	/**
	 * This comparator method compares portfolios by total value.
	 * @return integer value of the comparison
	 */
	public static Comparator<Portfolio> valueComparator() {
		return new Comparator<Portfolio>() {

			public int compare(Portfolio a, Portfolio b){
				double valueDifference = b.getTotalValue()-a.getTotalValue();
				if (valueDifference > 0){
					return 1;
				} else if(valueDifference < 0){
					return -1;
				} else {
					return 0;
				}
			}
		};
	}

	/**
	 * This comparator method compares portfolios by manager's broker
	 * type (Expert/Junior). Then, those two groups are both compared
	 * by manager's last name, followed by manager's first name.
	 * @return integer value of the comparison
	 */
	public static Comparator<Portfolio> managerComparator(){
		return new Comparator<Portfolio>(){

			public int compareManagerNames(Portfolio a, Portfolio b){
				int compareLastName = b.getManager().getLastName().compareTo(a.getManager().getLastName());
				if(compareLastName==0){
					return b.getManager().getFirstName().compareTo(a.getManager().getFirstName());
				} 
				return compareLastName;
			}
			public int compare(Portfolio a, Portfolio b){
				int compareBrokerType =  (a.getManager().getType())-(b.getManager().getType());
				if(compareBrokerType == 0){
					return compareManagerNames(a,b);
				}
				return compareBrokerType;
			}
		};
	}
}