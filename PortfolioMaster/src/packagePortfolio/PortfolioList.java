package packagePortfolio;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Comparator;
import java.util.Enumeration;

public class PortfolioList<T> {

private static final int SIZE = 10;
	
	private T arr[];
	private int size;
	private CurrentSorting sortType;
	
	@SuppressWarnings("unchecked")
	public PortfolioList() {
		this.arr = (T[]) new Object[SIZE]; 
		this.size = 0;
		
	}

	public T get(int index) {
		if(index < 0 || index >= size) 
			throw new IllegalArgumentException("index = "+index+" is out of bounds");
		else
			return this.arr[index];
	}
	
	public boolean isFull(){
		if(this.size == arr.length){
			return true;
		} 
		return false;
	}
	
	public void expand(){
		@SuppressWarnings("unchecked")
		T tmp[] = (T[]) new Object[this.arr.length + SIZE];
		for(int i=0; i<size; i++) {
			tmp[i] = this.arr[i];
		}
		this.arr = tmp;
	}
	
	public void contract(){
		//TODO Implement
	}
	
	
	public void removeElementAtIndex(int index) {

		if(index < 0 || index >= size) 
			throw new IllegalArgumentException("index = "+index+" is out of bounds");
		
		for(int i=index; i<size-1; i++) {
			this.arr[i] = this.arr[i+1];
		}
		this.size--;
		
		if(this.isFull()) {
			this.expand();
		}
		
	}
	
	private void addElementAtIndex(int index, T element) {
		
		if(index<0 || index > SIZE){
			throw new IllegalArgumentException("Invalid index");
		}
		if(size == arr.length){
			this.arr = Arrays.copyOf(this.arr, arr.length + SIZE);
		}
		for(int i=size-1; i>=index; i--){
			arr[i+1] = arr[i];
		}
		arr[index]=element;
		size++;
	}
	
	public void add(T element) {
		if(element instanceof Portfolio){
			if(size==0){
				addElementAtIndex(0, element);
			}
		switch(sortType){
		case OWNER:
			//TODO sort by owner
			break;
		case VALUE:
			//TODO sorty by value
			break;
		case MANAGER:
			//TODO sort by manager
			break;
		default:
			break;
		}
		if(this.isFull()) {
			this.expand();
		}
		
		
		
		this.arr[size] = element;
		this.size++;
		}
		else {
			throw new IllegalArgumentException("Not a portfolio");
		}
	}
	
	private void insertByOwnerName(T element){
		int currentIndex = 0;
		Portfolio port = (Portfolio) element;
		while(currentIndex <= this.size){
			addElementAtIndex(currentIndex, element);
		}
	}
	
	public int size() {
		return this.size;
	}
	
	public boolean isEmpty() {
		return (size == 0) ? true : false;
	}
	
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			
			int currentIndex = 0;
			@Override
			public boolean hasNext() {
				T item = arr[currentIndex];
				currentIndex++;
				if(item==null){
					return false;
				}
				return true;
			}
			@Override
			public T next() {
				T item = null;
				if(arr[currentIndex+1]!=null){
				 item = arr[currentIndex+1];
				}
				return item;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("not implemented");
			}
		};
	}
	
	public enum CurrentSorting{
		OWNER,
		VALUE,
		MANAGER
	}
	
	public Comparator<T> comparator(){
		return new Comparator<T>(){
	
		public int compare(T a, T b){
			return 0;
		}
		};
	}
//	//TODO Insert with owner last name, use String.compareTo()
//	public static <T extends Comparable<T>> T compareOwner(T[] arr) {
//		T first = arr[0];
//		
//		
//		return first;
//		
//	}
//	//TODO Insert with portfolio value
//	public static <T extends Comparable<T>> T comparePortValue() {
//		
//	
//	}
//	//TODO Insert with broker type, alphabetize brokers
//	public static <T extends Comparable<T>> T compareManager() {
//		
//		
//	}
	@Override
	public String toString() {
		if(this.size == 0) {
			return "[empty]";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i=0; i<this.size-1; i++) {
			sb.append(this.arr[i]);
			sb.append(", ");
		}
		sb.append(this.arr[this.size-1]);
		sb.append("]");
		return sb.toString();
	}
}
