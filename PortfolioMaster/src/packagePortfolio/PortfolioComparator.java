package packagePortfolio;
import java.util.Comparator;

public class PortfolioComparator {

	public Comparator<Portfolio> ownerComparator(){
		return new Comparator<Portfolio>(){
	
		public int compare(Portfolio a, Portfolio b){
			int compareLastName = a.getOwner().getLastName().compareTo(b.getOwner().getLastName());
			if(compareLastName==0){
				return a.getOwner().getFirstName().compareTo(b.getOwner().getFirstName());
			} 
			return compareLastName;
		}
		};
	}
	
	public Comparator<Portfolio> valueComparator(){
		return new Comparator<Portfolio>(){
	
		public int compare(Portfolio a, Portfolio b){
			double valueDifference = a.getTotalValue()-b.getTotalValue();
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
	
	public Comparator<Portfolio> managerComparator(){
		return new Comparator<Portfolio>(){
	
			public int compareManagerNames(Portfolio a, Portfolio b){
				int compareLastName = a.getManager().getLastName().compareTo(b.getManager().getLastName());
				if(compareLastName==0){
					return a.getManager().getFirstName().compareTo(b.getManager().getFirstName());
				} 
				return compareLastName;
			}
			public int compare(Portfolio a, Portfolio b){
				int compareBrokerType =  (b.getManager().getType())-(a.getManager().getType());
				if(compareBrokerType == 0){
					return compareManagerNames(a,b);
				}
				return compareBrokerType;
				
			}
		};
	}
	
}
