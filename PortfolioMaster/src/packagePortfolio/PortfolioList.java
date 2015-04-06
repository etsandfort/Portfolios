package packagePortfolio;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Comparator;

/**
 * PortfolioList<T> is the class that is the mould for the team's sorted list ADT.
 * 
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 2.0
 * @param <T>
 */
public class PortfolioList<T> implements Iterable<T>{

	// data members
	private static final int SIZE = 10;
	private T arr[];
	private int size;
	private Comparator<T> comparator;

	/**
	 * Constructor for the PortfolioList<T> class. Constructs PortfolioList objects.
	 * @param c, the comparator of choice
	 */
	@SuppressWarnings("unchecked")
	public PortfolioList(Comparator<T> c) {
		this.arr = (T[]) new Object[SIZE]; 
		this.size = 0;
		this.comparator= c;
	}

	/**
	 * This method retrieves the array element at the given index
	 * @param index
	 * @return the element at the input index
	 */
	public T get(int index) {
		if(index < 0 || index >= size) 
			throw new IllegalArgumentException("index = "+index+" is out of bounds");
		else
			return this.arr[index];
	}

	/**
	 * The isFull() method checks if the array is full.
	 * @return boolean; true if full, false if not
	 */
	public boolean isFull(){
		if(this.size == arr.length){
			return true;
		} 
		return false;
	}

	/**
	 * The expand() method enlarges the array by SIZE elements
	 */
	public void expand(){
		@SuppressWarnings("unchecked")
		T tmp[] = (T[]) new Object[this.arr.length + SIZE];
		for(int i=0; i<size; i++) {
			tmp[i] = this.arr[i];
		}
		this.arr = tmp;
	}

	/**
	 * The hasWastedSpace() method checks if the array has more than SIZE 
	 * empty (null) spaces in it.
	 * @return boolean; true if more than SIZE elements in array are null
	 */
	public boolean hasWastedSpace(){
		if((arr.length-size)>SIZE){
			return true;
		}
		return false;
	}
	
	/**
	 * The contract() method decreases the number of elements in the array
	 * by SIZE elements.
	 */
	public void contract(){
		@SuppressWarnings("unchecked")
		T tmp[] = (T[]) new Object[this.arr.length - SIZE];
		for(int i=0; i<size; i++) {
			tmp[i] = this.arr[i];
		}
		this.arr = tmp;
	}

	/**
	 * This method removes the array element at the input index. If given 
	 * index is not included in the array's current size, an
	 * IllegalArgumentException is thrown.
	 * @param index, the place at which user wants to remove an element
	 */
	private void removeElementAtIndex(int index) {

		if(index < 0 || index >= size) 
			throw new IllegalArgumentException("index = "+index+" is out of bounds");

		for(int i=index; i<size-1; i++) {
			this.arr[i] = this.arr[i+1];
		}
		this.size--;
	}

	/**
	 * This method adds an element to the array at the given index
	 * @param index
	 * @param element
	 */
	private void addElementAtIndex(int index, T element) {

		if(index < 0 || index > SIZE){
			throw new IllegalArgumentException("Invalid index");
		}
		if(this.size == arr.length){
			this.arr = Arrays.copyOf(this.arr, arr.length + SIZE);
		}
		for(int i=size-1; i>=index; i--){
			arr[i+1] = arr[i];
		}
		arr[index]=element;
		size++;
	}

	/**
	 * This method adds an element to the array, either at the beginning
	 * if the array is currently empty, or at the index the element should
	 * be after being compared with the desired comparator.
	 * @param element
	 */
	public void add(T element) {
		if(size==0){
			addElementAtIndex(0, element);
		}else{
			if(this.isFull()) {
				this.expand();
			} 

			int currentIndex = 0;
			while(currentIndex <= this.size && this.comparator.compare(element, this.arr[currentIndex])>0){
				addElementAtIndex(currentIndex, element);
				currentIndex++;
			}
		}
	}

	/**
	 * The remove method removes the input element from the array 
	 * and contracts the array if necessary.
	 * @param element
	 */
	public void remove(T element){
		int index = -1;
		for(int i = 0; i<this.size; i++){
			if(this.arr[i].equals(element)){
				index = i;
			}
		}
		if(size==0 || index != -1){
			throw new ArrayIndexOutOfBoundsException("Not in the array");
		} else{
			removeElementAtIndex(index);
			if(this.hasWastedSpace()){
				this.contract();
			}
		}
	}
	
	/**
	 * This method clears the list.
	 */
	@SuppressWarnings("unchecked")
	public void clear() {
		this.arr = (T[]) new Object[SIZE]; 
	}
	
	/**
	 * The size() method returns the PortfolioList size.
	 * @return size
	 */
	public int size() {
		return this.size;
	}

	/**
	 * The isEmpty() method checks if the PortfolioList size is 0.
	 * @return boolean; true if PortfolioList size is 0
	 */
	public boolean isEmpty() {
		return (size == 0) ? true : false;
	}

	/**
	 * This is the iterator for PortfolioList.
	 */
	public Iterator<T> iterator() {
		return new Iterator<T>() {

			int currentIndex = 0;
			//checks if there is a next element in the array
			@Override
			public boolean hasNext() {
				T item = arr[currentIndex + 1];
				currentIndex++;
				if(item==null){
					return false;
				}
				return true;
			}
			
			//returns the next element in the array
			@Override
			public T next() {
				if(!hasNext()){
					throw new IndexOutOfBoundsException("No next element");
				}
				currentIndex++;
				return arr[currentIndex];
			}

			//Not implemented. Unneeded for the team's desired iterator tasks
			@Override
			public void remove() {
				throw new UnsupportedOperationException("not implemented");
			}
		};
	}

	/**
	 * The toString() method was overridden to display the array's elements.
	 */
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
