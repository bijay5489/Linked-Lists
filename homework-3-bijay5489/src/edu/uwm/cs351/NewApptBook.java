//Was at tutor with //Nathon tran,//jiahui yng
// Name-bijay panta
// The tutor's name was Dylan, and riley
// Work on somepart (like binarySearch with my tutors/uncle),
// I did write all code here
//
package edu.uwm.cs351;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
//import edu.uwm.cs351.NewApptBook.MyIterator;
//import edu.uwm.cs351.NewApptBook.MyIterator;
//import edu.uwm.cs351.NewApptBook.MyIterator;

//import edu.uwm.cs351.RangeCollection.MyIterator;

/**
 * A variant of the ApptBook ADT that follows the Collection model.
 * In particular, it has no sense of a current element.
 * All access to elements by the client must be through the iterator.
 * The {@link #add(Appointment)} method should add at the correct spot in sorted order in the collection.
 */

public class NewApptBook extends AbstractCollection<Appointment> implements Cloneable {
	/** Static Constants */
	private static final int INITIAL_CAPACITY = 1; 
	//The 1 is just a placeholder. Whenever we use the initial capacity variable we pass it on to the construct of the array. So it has to be at least one since it cannot be negative. no -1 appointments.  This is pretty much the capacity of the array. 

	private Appointment[] data;
	private int manyItems;
	private int version;
	
	
	private static boolean doReport = true; // change only in invariant tests
	private boolean report(String error) {
		if (doReport) {
			System.out.println("Invariant error: " + error);
		}
		return false;
	}
	
	
	/**
	 * Construct an empty NewApptBook with a capacity of INITIAL_CAPACITY
	 */
	public NewApptBook( ) {
		this(INITIAL_CAPACITY);
	//	Calls the second constructor
		assert wellFormed() : "invariant failed at end of constructor";
		//Assert calls for the well form statement which checks if the invariants are true or not. 
		//If there’s error The compiler will print the message.
		}
	/**
	 * Construct an empty NewApptBook with a capacity of initialCapacity
	 * @param initialCapacity the capacity of the NewApptBook
	 */
	public NewApptBook(int initialCapacity)//Gets the capacity from the test
	{
		if (initialCapacity < 0) 
			throw new IllegalArgumentException("Initial Capacity cannot be negative");
		data = new Appointment[initialCapacity];
		//Many items and versions are zero because we haven’t added any appointments.
		manyItems = 0;
		version = 0;
		assert wellFormed() : "invariant failed at end of constructor";
	}
	

	@Override // implementation
	public boolean add(Appointment element) {
		assert wellFormed() : "invariant failed at start of add";
		if (element == null)
			throw new NullPointerException("element cannot be null");
			//throw new IllegalArgumentException("element cannot be null");
		ensureCapacity(this.manyItems+1);
		
	//	this.data[manyItems++] = element;
		int i;
		for(i = manyItems; i >0 && data[i-1].compareTo(element) > 0; --i)
		//It greater than 0 and data i- 1 is simply a Boolean check that’s making sure it’s greater than zero and The element we are about to add is not smaller than our add
		{
			data[i] = data[i - 1]; 
		}
		//We have to add it at the correct index. so we start at the end of the array. we go to the left until we get to the spot where we place element. . 

		data[i] = element;
		++manyItems;
		//We added a element so the number of many items need to increase. 
		//Version needs to change since array has changed.
		++version;
		assert wellFormed() : "invariant failed at end of add";
		return true;
		}
	
	
	@Override // implementation
	public boolean addAll(Collection<? extends Appointment> c) 
	// had help here with <?
	// so it -Implements a function from the parent class. Any sub classes will have a super class.
	//So The c collection can have a type of appointment or any other sub classes.
	//The ? Mark means it can be any type as long as it extends appointments. 
	//		So you could be a dentist appointment and a therapy appointment. Even though there are different types.
	{
		assert wellFormed() : "invariant failed at start of addAll";
		if (c.size() == 0)
			//If the collection that we have to add is empty then don’t add anything. 
			return false;
		ensureCapacity(manyItems + c.size());
		//We need to ensure that capacity is at least items we have + all the new items.
		for (Appointment a : c) 
		//A just the appointment you’re at c, just enhanced for loop. 
			//Add A is adding all the appointments in C.
		{
			add(a);
		}
		++version;
		assert wellFormed() : "invariant failed at end of addAll";
		return true;
	}
	
	

	@Override // required
	public Iterator<Appointment> iterator(){
		assert wellFormed() : "invariant failed at start of interator()";
		return new MyIterator();
	}

	
	/**
	 * Get an iterator that points to the first Appointment greater than or equal to guide. The iterator
	 * hasNext() returns false if no such element exists
	 * @param guide the Appointment that we want to start with
	 * @return Iterator that has the first element equal to or greater than guide
	 */
	public Iterator<Appointment> iterator(Appointment guide) {
		//The appointment which the iterator points too, so like the setcurrent.
		//The iterators starts at any appointment which starts or after x number. 
	//	if (guide == null) throw new NullPointerException("cannot be null");
		assert wellFormed() : "invariant failed at start of interator()";
		return new MyIterator(guide);
	}


	@Override // implementation
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override // implementation
	public void clear() {
		if (manyItems != 0) version++;
		//data = new Appointment[INITIAL_CAPACITY];
		manyItems = 0;
	}
	//Clear just empties the elements in the array. so when clear is called on a non-empty collection it changes the version as we change the data structure.


	//hw2 answers
	/**
	 * Check the invariants
	 * @return true if all invariants pass, false otherwise
	 */
	private boolean wellFormed() {
		if (data == null) return report ("data is null");
		if(data.length < manyItems) return report("data is too short");
		for(int i = 0; i < manyItems; ++i) {
		if(data[i] == null) return report("null found at index" + i);
		if(i > 0 && data[i-1].compareTo(data[i]) > 0) {
			for (int j = 0; j < manyItems; j++) {
				System.out.println(data[j]);
		}
			return report ("Found out of order data");
		}
		} 
		return true;
	}
								


	/**
	 * Generate a copy of this book.
	 * @return
	 *   The return value is a copy of this book. Subsequent changes to the
	 *   copy will not affect the original, nor vice versa.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for creating the clone.
	 **/ 
	public NewApptBook clone( ) { 
		assert wellFormed() : "invariant failed at start of clone";
		NewApptBook answer;
	
		try
		{
			answer = (NewApptBook) super.clone( );
		}
		catch (CloneNotSupportedException e)
		{  // This exception should not occur. But if it does, it would probably
			// indicate a programming error that made super.clone unavailable.
			// The most common error would be forgetting the "Implements Cloneable"
			// clause at the start of this class.
			throw new RuntimeException
			("This class does not implement Cloneable");
		}
	
		// all that is needed is to clone the data array.
		// (Exercise: Why is this needed?)
		this.data = data.clone( );
	
		assert wellFormed() : "invariant failed at end of clone";
		assert this.wellFormed() : "invariant on answer failed at end of clone";
		return answer;
	}
	


	/**
	 * Change the current capacity of this book as needed so that
	 * the capacity is at least as big as the parameter.
	 * This code must work correctly and efficiently if the minimum
	 * capacity is (1) smaller or equal to the current capacity (do nothing)
	 * (2) at most double the current capacity (double the capacity)
	 * or (3) more than double the current capacity (new capacity is the
	 * minimum passed).
	 * 
	 * @param minimumCapacity
	 *                        the new capacity for this book
	 * @postcondition
	 *                This book's capacity has been changed to at least
	 *                minimumCapacity.
	 *                If the capacity was already at or greater than
	 *                minimumCapacity,
	 *                then the capacity is left unchanged.
	 * @exception OutOfMemoryError
	 *                             Indicates insufficient memory for: new array of
	 *                             minimumCapacity elements.
	 **/
	private void ensureCapacity(int minimumCapacity){	
	if (data.length >= minimumCapacity) return;
	int newCap = data.length *2;
	if (newCap < minimumCapacity) newCap = minimumCapacity;
	Appointment[] newData = new Appointment[newCap];
	for (int i=0; i< manyItems; ++i){
		newData[i] = data[i];
	}
	data = newData;
	return;
	}	
	
								
	
	
	
	@Override // required
	public int size() {
		assert wellFormed() : "invariant failed at start of size";
		return manyItems;
	}

	

	// TODO: Add all the contents here.
	// Remember:
	// - All public methods not marked @Override must be fully documented with javadoc
	// - A @Override method must be marked 'required', 'implementation', or 'efficiency'
	// - You need to define and check the data structure invariant
	//   (essentially the same as in Homework #2)
	
	// - You should define a nested iterator class called MyIterator (with its own data structure), 
	//   and then the iterator() method simply returns a new instance.
	// You are permitted to copy in any useful code/comments from the Homework #2 solution.
	// But do not include any of the cursor-related methods, and in particular,
	// make sure you have no "currentIndex" field.
	
	
	
	private class MyIterator implements Iterator<Appointment> // TODO: what should this implement?	
	{
		private int myVersion;
		//My iterator needs its own version which it checks with the version of the class. 
		//To check if it’s stale or not. If my version and version are different. The iterator is stale
		
		private int next;
		private int current;
		
		
		//Same as setcurrent in the previous assignment
		public MyIterator(Appointment a) {
			assert wellFormed() : "invariant failed at the start of iterator constrcutor";
			if (a == null) throw new NullPointerException("cannot be null");
			if (manyItems <= 0) next = 0;
			else next = binarySearch(data, a, manyItems - 1);
			//We use binary search to find the index in which next has to be.
			myVersion = version;
			current = next;
		}
		/**
		 * 
		 * @param data
		 * @param a
		 * @param len
		 * @return if value is less than mid or higher than mid. 
		 */
		private int binarySearch(Appointment[] data, Appointment a, int len) {
			int lo = 0, hi = len;
			while (lo < hi) {
				int mid = lo + (hi - lo) / 2;
				if (data[mid].compareTo(a) >= 0) hi = mid;
				else lo = mid + 1;
				//Pretty self-explanatory, we divide (high-low) to find our medium. 
				//Then we compare a medium value to see if our next is going to be bigger than it or less than it. 
				//This helps narrow down, the search. 
			}
			//Had help with this method from my uncle.
			if (data[lo].compareTo(a) < 0)
				return (lo + 1);
			else
				 return lo;
			//
			
		}
		

		
		
		
		public MyIterator() {
			assert wellFormed() : "invariant failed at the start of iterator constrcutor";
			next = 0;
			myVersion = version;
			current = next;
			
		}
	
		public boolean hasNext() {
			assert wellFormed() : "invariant failed at start of hasNext";
			if (version != myVersion) throw new ConcurrentModificationException();
			return next < manyItems;
			//If next is greater than many items, then there’s no items left to explore.

		}
		
		
		@Override // required
		public Appointment next() {
			assert wellFormed() : "invariant failed at start of next";
			if (!hasNext()) throw new NoSuchElementException();
			////We check if there is a next to call or not.
			if (version != myVersion) throw new ConcurrentModificationException();
			Appointment nextAppointment = data[next];
			//We update current.
			current = next;
			next++;
			assert wellFormed() : "invariant failed at end of next";
			return nextAppointment;
		}

		
		// The nested MyIterator class should use the following
				// invariant checker:
		public boolean wellFormed() {
			if (!NewApptBook.this.wellFormed())
				return false;
			if (version != myVersion)
				return true; // not my fault if invariant broken
			if (current < 0 || current > manyItems)
				return report("current out of range: " + current + " not in range [0," + manyItems + "]");
			if (next < 0 || next > manyItems) 
				return report("next out of range: " + next + " not in range[0," + manyItems + "]");
			if (next != current && next != current + 1)
				return report("next " + next + " isn't current or its successor (current = " + current + ")");
			return true;
		}

		@Override // implementation
		public void remove() {
			assert wellFormed() : "invariant failed at start of hasNext";
			if (version != myVersion) throw new ConcurrentModificationException();
			if (current == next) 
				//If current is equal to next then we know next hasn’t been called yet.
				//You cannot call remove if you have not called next yet.
				throw new IllegalStateException();
			for (int i = current + 1; i < manyItems; i++) {
				data[i - 1] = data[i];
			}
			//Similar to the add method it starts at current but Shifts elements to the left.
			data[manyItems - 1] = null;
			//Since we removed something items minus 1 needs to be null.
			manyItems--;
			next--;
			if (current == 0) {
				next = 0;
			}
			version++;
			myVersion++;
			assert wellFormed() : "invariant failed at end of it.remove";
			//return;
		}
}


}








