package packagePortfolio;
import java.util.Comparator;

public class PortfolioComparator {

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
