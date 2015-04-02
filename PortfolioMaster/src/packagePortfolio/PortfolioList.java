package packagePortfolio;
import java.util.Arrays;
import java.util.Iterator;

public class PortfolioList<T> {

private static final int SIZE = 10;
	
	private T arr[];
	private int size;
	
	@SuppressWarnings("unchecked")
	public PortfolioList() {
		this.arr = (T[]) new Object[SIZE]; 
		this.size = 0;
	}

	public T getElementAtIndex(int index) {
		if(index < 0 || index >= size) 
			throw new IllegalArgumentException("index = "+index+" is out of bounds");
		else
			return this.arr[index];
	}
	
	public void removeElementAtIndex(int index) {

		if(index < 0 || index >= size) 
			throw new IllegalArgumentException("index = "+index+" is out of bounds");
		
		for(int i=index; i<size-1; i++) {
			this.arr[i] = this.arr[i+1];
		}
		this.size--;
		
		if(size < arr.length - SIZE) {
			//resize
			@SuppressWarnings("unchecked")
			T tmp[] = (T[]) new Object[this.arr.length - SIZE];
			for(int i=0; i<size; i++) {
				tmp[i] = this.arr[i];
			}
			this.arr = tmp;
		}
		
	}
	
	public void addElementAtIndex(int index, T element) {
		
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
	
	public void addElementAtEnd(T element) {

		if(size == arr.length) {
			//resize
			@SuppressWarnings("unchecked")
			T tmp[] = (T[]) new Object[arr.length + SIZE];
			for(int i=0; i<arr.length; i++) {
				tmp[i] = arr[i];
			}
			arr = tmp;
		}

		this.arr[size] = element;
		this.size++;
	}
	
	public int getSize() {
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
			}};
	}
	
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
