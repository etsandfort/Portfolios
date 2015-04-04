package packagePortfolio;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Comparator;


public class PortfolioList<T> {

	private static final int SIZE = 10;

	private T arr[];
	private int size;
	private Comparator<T> comparator;

	@SuppressWarnings("unchecked")
	public PortfolioList(Comparator c) {
		this.arr = (T[]) new Object[SIZE]; 
		this.size = 0;
		this.comparator= c;
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

	public boolean hasWastedSpace(){
		if((arr.length-size)>SIZE){
			return true;
		}
		return false;
	}
	public void contract(){
		@SuppressWarnings("unchecked")
		T tmp[] = (T[]) new Object[this.arr.length - SIZE];
		for(int i=0; i<size; i++) {
			tmp[i] = this.arr[i];
		}
		this.arr = tmp;

	}


	private void removeElementAtIndex(int index) {

		if(index < 0 || index >= size) 
			throw new IllegalArgumentException("index = "+index+" is out of bounds");

		for(int i=index; i<size-1; i++) {
			this.arr[i] = this.arr[i+1];
		}
		this.size--;
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

		if(size==0){
			addElementAtIndex(0, element);
		}else{
			if(this.isFull()) {
				this.expand();
			} 

			int currentIndex = 0;
			while(currentIndex <= this.size && this.comparator.compare(element, this.arr[currentIndex])<0){
				addElementAtIndex(currentIndex, element);
				currentIndex++;
			}
			this.size++;
		}
	}

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
