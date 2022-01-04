/*
Mohammad Aman Zargar
Nasid : Maz423
Student no : 11265940
 */

package lib280.list;


import lib280.base.BilinearIterator280;
import lib280.exception.BeforeTheStart280Exception;
import lib280.exception.ContainerEmpty280Exception;

/**	A LinkedIterator which has functions to move forward and back, 
	and to the first and last items of the list.  It keeps track of 
	the current item, and also has functions to determine if it is 
	before the start or after the end of the list. */
public class BilinkedIterator280<I> extends LinkedIterator280<I> implements BilinearIterator280<I>
{

	/**	Constructor creates a new iterator for list 'list'. <br>
		Analysis : Time = O(1) 
		@param list list to be iterated */
	public BilinkedIterator280(BilinkedList280<I> list)
	{
		super(list);
	}

	/**	Create a new iterator at a specific position in the newList. <br>
		Analysis : Time = O(1)
		@param newList list to be iterated
		@param initialPrev the previous node for the initial position
		@param initialCur the current node for the initial position */
	public BilinkedIterator280(BilinkedList280<I> newList, 
			LinkedNode280<I> initialPrev, LinkedNode280<I> initialCur)
	{
		super(newList, initialPrev, initialCur);
	}
    
	/**
	 * Move the cursor to the last element in the list.
	 * @precond The list is not empty.
	 */
	public void  goLast() throws ContainerEmpty280Exception
	{ if (list.isEmpty()){ //if list is empty throw exception.
		throw new ContainerEmpty280Exception("Iterator: goLast(): empty list}");
	}
	  else{ //if the list has one or more items .
      this.cur = list.lastNode(); //sets the current node = tail
	  this.prev = ((BilinkedNode280<I>)cur).previousNode(); //sets previous node = previous node of the tail
	  this.cur.nextNode = null;}   //sets next node = null
	}

	/**
	 * Move the cursor one element closer to the beginning of the list
	 * @precond !before() - the cursor cannot already be before the first element.
	 */
	public void goBack() throws BeforeTheStart280Exception
	{
		if(this.cur == null && this.prev == null) //throws before the start exception
		{throw new BeforeTheStart280Exception("Cannot go further back from before the start");}

		else if (this.cur != null && this.prev == null) { //if cursor is at the first element.
			this.cur = null;
			this.prev = null;
		}
		else{ //if the cursor is somewhere in the list except the first item.
			this.cur = this.prev; //set the cur position = previous position
			this.prev = ((BilinkedNode280<I>)this.prev).previousNode();}  //set the previous position = the previous position of itself.
	 }

	/**	A shallow clone of this object. <br> 
	Analysis: Time = O(1) */
	public BilinkedIterator280<I> clone()
	{
		return (BilinkedIterator280<I>) super.clone();
	}


} 
