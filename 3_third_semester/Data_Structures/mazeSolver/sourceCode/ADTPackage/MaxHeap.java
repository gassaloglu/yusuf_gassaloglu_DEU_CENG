package ADTPackage;
import java.util.Arrays;
/**
 A class that implements the ADT maxheap by using an array.
 
 @author Frank M. Carrano
 @author Timothy M. Henry
 @version 5.0
 */
public final class MaxHeap<T extends Comparable<? super T>>
implements MaxHeapInterface<T>
{
   private T[] heap;      // Array of heap entries; ignore heap[0]
   private int lastIndex; // Index of last entry and number of entries
   private boolean integrityOK = false;
   private static final int DEFAULT_CAPACITY = 5; // NB: Changed to 5 from 25 for testing convenience
   private static final int MAX_CAPACITY = 10000;
   
   public MaxHeap()
   {
      this(DEFAULT_CAPACITY); // Call next constructor
   } // end default constructor

	public MaxHeap(int initialCapacity)
	{
   // NOTE: This code deviates from the book somewhat in that checkCapacity throws an exception
   //       if initialCapacity is either too small or too large.
      integrityOK = false;
      checkCapacity(initialCapacity);

		// The cast is safe because the new array contains null entries
		@SuppressWarnings("unchecked")
		T[] tempHeap = (T[])new Comparable[initialCapacity + 1]; // First array element is not used
		heap = tempHeap;
		lastIndex = 0;
		integrityOK = true;
	} // end constructor

   public MaxHeap(T[] entries)
   {
      this(entries.length); // Call other constructor
      // Assertion: integrityOK = true
      lastIndex = entries.length;

      // Copy given array to data field
      for (int index = 1; index <= lastIndex; index++)
         heap[index] = entries[index - 1];
      
      // Create heap
      for (int rootIndex = lastIndex / 2; rootIndex > 0; rootIndex--)
         reheap(rootIndex);
   } // end constructor
   
   public void add(T newEntry)
   {
      checkIntegrity();        // Ensure initialization of data fields
      int newIndex = lastIndex + 1;
      int parentIndex = newIndex / 2;
      while ( (parentIndex > 0) && newEntry.compareTo(heap[parentIndex]) > 0)
      {
         heap[newIndex] = heap[parentIndex];
         newIndex = parentIndex;
         parentIndex = newIndex / 2;
      } // end while
      
      heap[newIndex] = newEntry;
      lastIndex++;
      ensureCapacity();
   } // end add

   public T removeMax()
   {
      checkIntegrity();             // Ensure initialization of data fields
      T root = null;
      
      if (!isEmpty())
      {
         root = heap[1];            // Return value
         heap[1] = heap[lastIndex]; // Form a semiheap
         lastIndex--;               // Decrease size
         reheap(1);                 // Transform to a heap
      } // end if
      
      return root;
   } // end removeMax

	public T getMax()
	{
		checkIntegrity();
		T root = null;

		if (!isEmpty())
			root = heap[1];

		return root;
	} // end getMax

	public boolean isEmpty()
	{
		return lastIndex < 1;
	} // end isEmpty

	public int getSize()
	{
		return lastIndex;
	} // end getSize

	public void clear()
	{
		checkIntegrity();
		while (lastIndex > -1)
		{
			heap[lastIndex] = null;
			lastIndex--;
		} // end while

		lastIndex = 0;
	} // end clear

   // Precondition: checkIntegrity has been called.
   private void reheap(int rootIndex)
   {
      boolean done = false;
      T orphan = heap[rootIndex];
      int leftChildIndex = 2 * rootIndex;
      
      while (!done && (leftChildIndex <= lastIndex) )
      {
         int largerChildIndex = leftChildIndex; // Assume larger
         int rightChildIndex = leftChildIndex + 1;
         
         if ( (rightChildIndex <= lastIndex) &&
             heap[rightChildIndex].compareTo(heap[largerChildIndex]) > 0)
         {
            largerChildIndex = rightChildIndex;
         } // end if
         
         if (orphan.compareTo(heap[largerChildIndex]) < 0)
         {
            heap[rootIndex] = heap[largerChildIndex];
            rootIndex = largerChildIndex;
            leftChildIndex = 2 * rootIndex;
         }
         else
            done = true;
      } // end while
      
      heap[rootIndex] = orphan;
   } // end reheap

	// Doubles the capacity of the array heap if it is full.
	// Precondition: checkIntegrity has been called.
	private void ensureCapacity()
	{
      int numberOfEntries = lastIndex;
      int capacity = heap.length - 1;
      if (numberOfEntries >= capacity)
      {
         int newCapacity = 2 * capacity;
         checkCapacity(newCapacity); // Is capacity too big?
         heap = Arrays.copyOf(heap, newCapacity + 1);
      } // end if
   } // end ensureCapacity

	// Throws an exception if this object is corrupt.
	private void checkIntegrity()
	{
		if (!integrityOK)
			throw new SecurityException ("MaxHeap object is corrupt.");
	} // end checkIntegrity

	// Ensures that the client requests a capacity
	// that is not too small or too large.
	private void checkCapacity(int capacity)
	{
		if (capacity < DEFAULT_CAPACITY)
         throw new IllegalStateException("Attempt to create a heap " +
                                         "whose capacity is smaller than " +
                                         DEFAULT_CAPACITY);
		else if (capacity > MAX_CAPACITY)
			throw new IllegalStateException("Attempt to create a heap " +
											        "whose capacity is larger than " +
											        MAX_CAPACITY);
	} // end checkCapacity
} // end MaxHeap

