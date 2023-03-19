package ADTPackage;
import java.util.EmptyStackException;
/**
   A class of stacks whose entries are stored in a chain of nodes.
   
   @author Frank M. Carrano
   @author Timothy M. Henry
   @version 5.0
*/
public final class LinkedStack<T> implements StackInterface<T>
{
	private Node topNode; // References the first node in the chain
	
	public LinkedStack()
	{
		topNode = null;
	} // end default constructor
	
	public void push(T newEntry)
	{
		Node newNode = new Node(newEntry, topNode);
		topNode = newNode;
//    topNode = new Node(newEntry, topNode); // Alternate code
	} // end push

	public T peek()
	{
		if (isEmpty())
			throw new EmptyStackException();
		else
         return topNode.getData();
	} // end peek

	public T pop()
	{
	   T top = peek();  // Might throw EmptyStackException
   // Assertion: topNode != null
      topNode = topNode.getNextNode();

	   return top;
	} // end pop

/*
// Question 1, Chapter 6: Does not call peek 
	public T pop()
	{
      if (isEmpty())
         throw new EmptyStackException();
      else
		{
         assert (topNode != null);
			top = topNode.getData();
			topNode = topNode.getNextNode();
		} // end if
		
		return top;
	} // end pop
*/		

	public boolean isEmpty()
	{
		return topNode == null;
	} // end isEmpty
	
	public void clear()
	{
		topNode = null;  // Causes deallocation of nodes in the chain
	} // end clear

	private class Node
	{
      private T    data; // Entry in stack
      private Node next; // Link to next node

      private Node(T dataPortion)
      {
         this(dataPortion, null);
      } // end constructor

      private Node(T dataPortion, Node linkPortion)
      {
         data = dataPortion;
         next = linkPortion;	
      } // end constructor

      private T getData()
      {
         return data;
      } // end getData

      private void setData(T newData)
      {
         data = newData;
      } // end setData

      private Node getNextNode()
      {
         return next;
      } // end getNextNode

      private void setNextNode(Node nextNode)
      {
         next = nextNode;
      } // end setNextNode
	} // end Node
} // end LinkedStack
