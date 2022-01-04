/*
Mohammad Aman Zargar
Nasid : Maz423
Student no : 11265940
 */

package lib280.list;


import lib280.base.BilinearIterator280;
import lib280.base.CursorPosition280;
import lib280.base.Pair280;
import lib280.exception.*;

/**	This list class incorporates the functions of an iterated 
	dictionary such as has, obtain, search, goFirst, goForth, 
	deleteItem, etc.  It also has the capabilities to iterate backwards 
	in the list, goLast and goBack. */
public class BilinkedList280<I> extends LinkedList280<I> implements BilinearIterator280<I>
{
	/* 	Note that because firstRemainder() and remainder() should not cut links of the original list,
		the previous node reference of firstNode is not always correct.
		Also, the instance variable prev is generally kept up to date, but may not always be correct.  
		Use previousNode() instead! */

	/**	Construct an empty list.
		Analysis: Time = O(1) */
	public BilinkedList280()
	{
		super();
	}

	/**
	 * Create a BilinkedNode280 this Bilinked list.  This routine should be
	 * overridden for classes that extend this class that need a specialized node.
	 * @param item - element to store in the new node
	 * @return a new node containing item
	 */
	protected BilinkedNode280<I> createNewNode(I item) { return new BilinkedNode280<I>(item);}

	/**
	 * Insert element at the beginning of the list
	 * @param x item to be inserted at the beginning of the list 
	 */
	public void insertFirst(I x) 
	{   BilinkedNode280<I>	new_node = createNewNode(x); //new node to be inserted.

		if(this.isEmpty()){  //if list is empty update the head and tail to be the new node.
            this.head = new_node;
            this.tail = new_node;
		}
		else{ //if the list had 1 or more items items set the next node to be the first node.
			new_node.setNextNode(this.head);
			((BilinkedNode280<I>)this.head).setPreviousNode(new_node);
			this.head = new_node;
		}
	}

	/**
	 * Insert element at the beginning of the list
	 * @param x item to be inserted at the beginning of the list 
	 */
	public void insert(I x) 
	{
		this.insertFirst(x);
	}

	/**
	 * Insert an item before the current position.
	 * @param x - The item to be inserted.
	 */
	public void insertBefore(I x) throws InvalidState280Exception {
		if( this.before() ) throw new InvalidState280Exception("Cannot insertBefore() when the cursor is already before the first element.");
		
		// If the item goes at the beginning or the end, handle those special cases.
		if( this.head == position ) {
			insertFirst(x);  // special case - inserting before first element
		}
		else if( this.after() ) {
			insertLast(x);   // special case - inserting at the end
		}
		else {
			// Otherwise, insert the node between the current position and the previous position.
			BilinkedNode280<I> newNode = createNewNode(x);
			newNode.setNextNode(position);
			newNode.setPreviousNode((BilinkedNode280<I>)this.prevPosition);
			prevPosition.setNextNode(newNode);
			((BilinkedNode280<I>)this.position).setPreviousNode(newNode);
			
			// since position didn't change, but we changed it's predecessor, prevPosition needs to be updated to be the new previous node.
			prevPosition = newNode;			
		}
	}
	
	
	/**	Insert x before the current position and make it current item. <br>
		Analysis: Time = O(1)
		@param x item to be inserted before the current position */
	public void insertPriorGo(I x) 
	{
		this.insertBefore(x);
		this.goBack();
	}

	/**	Insert x after the current item. <br>
		Analysis: Time = O(1) 
		@param x item to be inserted after the current position */
	public void insertNext(I x) 
	{
		if (isEmpty() || before())
			insertFirst(x); 
		else if (this.position==lastNode())
			insertLast(x); 
		else if (after()) // if after then have to deal with previous node  
		{
			insertLast(x); 
			this.position = this.prevPosition.nextNode();
		}
		else // in the list, so create a node and set the pointers to the new node 
		{
			BilinkedNode280<I> temp = createNewNode(x);
			temp.setNextNode(this.position.nextNode());
			temp.setPreviousNode((BilinkedNode280<I>)this.position);
			((BilinkedNode280<I>) this.position.nextNode()).setPreviousNode(temp);
			this.position.setNextNode(temp);
		}
	}

	/**
	 * Insert a new element at the end of the list
	 * @param x item to be inserted at the end of the list 
	 */
	public void insertLast(I x)
	{   BilinkedNode280<I> new_node = createNewNode(x);  //new node to be inserted.

		if(this.isEmpty()){ //if list is empty update the head and tail to be the new node.
		   this.head = new_node;
		   this.tail = new_node;}

        else{ //if the list has 1 or more items set the next node of the tail to be the Last node.
        	this.tail.setNextNode(new_node);
        	new_node.setPreviousNode((BilinkedNode280<I>)this.tail);
	     	this.tail = new_node;}
		

	}

	/**
	 * Delete the item at which the cursor is positioned
	 * @precond itemExists() must be true (the cursor must be positioned at some element)
	 */
	public void deleteItem() throws NoCurrentItem280Exception
	{   if(this.position == null){     //exception if cursor is before or after the list or the list is empty.
		   throw new NoCurrentItem280Exception("Cursor must be at an element");
	}

		else if (this.position.nextNode == null && ((BilinkedNode280<I>)this.position).previousNode() == null){
		   this.deleteFirst();} //if there is only one item in the list and the cursor is at that item , deleteFirst item
		else if(this.position.nextNode == null && ((BilinkedNode280<I>)this.position).previousNode() != null){
			this.deleteLast();} //if the cursor is at the last item deleteLast item.

		else{ //if the cursor is somewhere in the list and the list has more than 1 item.
		    BilinkedNode280<I> prev = (BilinkedNode280<I>)this.prevPosition;
	        this.goForth();
	        BilinkedNode280<I> next = (BilinkedNode280<I>)this.position;
	        prev.setNextNode(next);  //Set the previous node of the current node to the next node of the current node.
	        next.setPreviousNode(prev);}


	}

	
	@Override
	public void delete(I x) throws ItemNotFound280Exception {
		if( this.isEmpty() ) throw new ContainerEmpty280Exception("Cannot delete from an empty list.");

		// Save cursor position
		LinkedIterator280<I> savePos = this.currentPosition();
		
		// Find the item to be deleted.
		search(x);
		if( !this.itemExists() ) throw new ItemNotFound280Exception("Item to be deleted wasn't in the list.");

		// If we are about to delete the item that the cursor was pointing at,
		// advance the cursor in the saved position, but leave the predecessor where
		// it is because it will remain the predecessor.
		if( this.position == savePos.cur ) savePos.cur = savePos.cur.nextNode();
		
		// If we are about to delete the predecessor to the cursor, the predecessor 
		// must be moved back one item.
		if( this.position == savePos.prev ) {
			
			// If savePos.prev is the first node, then the first node is being deleted
			// and savePos.prev has to be null.
			if( savePos.prev == this.head ) savePos.prev = null;
			else {
				// Otherwise, Find the node preceding savePos.prev
				LinkedNode280<I> tmp = this.head;
				while(tmp.nextNode() != savePos.prev) tmp = tmp.nextNode();
				
				// Update the cursor position to be restored.
				savePos.prev = tmp;
			}
		}
				
		// Unlink the node to be deleted.
		if( this.prevPosition != null)
			// Set previous node to point to next node.
			// Only do this if the node we are deleting is not the first one.
			this.prevPosition.setNextNode(this.position.nextNode());
		
		if( this.position.nextNode() != null )
			// Set next node to point to previous node 
			// But only do this if we are not deleting the last node.
			((BilinkedNode280<I>)this.position.nextNode()).setPreviousNode(((BilinkedNode280<I>)this.position).previousNode());
		
		// If we deleted the first or last node (or both, in the case
		// that the list only contained one element), update head/tail.
		if( this.position == this.head ) this.head = this.head.nextNode();
		if( this.position == this.tail ) this.tail = this.prevPosition;
		
		// Clean up references in the node being deleted.
		this.position.setNextNode(null);
		((BilinkedNode280<I>)this.position).setPreviousNode(null);
		
		// Restore the old, possibly modified cursor.
		this.goPosition(savePos);
		
	}
	/**
	 * Remove the first item from the list.
	 * @precond !isEmpty() - the list cannot be empty
	 */
	public void deleteFirst() throws ContainerEmpty280Exception{
        if(this.isEmpty()){  //throw ContainerEmpty280Exception if the list is empty.
        	throw new ContainerEmpty280Exception("Cannot delete from an empty list");
		}
        if(this.head.nextNode == null){ //if there is only one item in the List.
        	this.head = null;           //delete the item by setting head and tail to null
        	this.tail = null;
		}

		else // if list has more than 1 item , unlink the first node.
			{this.head = this.head.nextNode;}

	}

	/**
	 * Remove the last item from the list.
	 * @precond !isEmpty() - the list cannot be empty
	 */
	public void deleteLast() throws ContainerEmpty280Exception
	{
		if(this.isEmpty()){ //throw ContainerEmpty280Exception if the list is empty.
			throw new ContainerEmpty280Exception("Cannot delete from an empty list");}
		if(this.head.nextNode == null){ //if there is only one item in the List.
		  this.head = null;             //delete the item by setting head and tail to null.
		  this.tail = null;
	}
	    else{ //if the list has more than 1 item . unlink the last node.
		((BilinkedNode280<I>)this.tail).previousNode().setNextNode(null);}


	}

	
	/**
	 * Move the cursor to the last item in the list.
	 * @precond The list is not empty.
	 */
	public void goLast() throws ContainerEmpty280Exception
	{ if(this.isEmpty()){ //throw ContainerEmpty280Exception if the list is empty.
		throw new ContainerEmpty280Exception("Container cannot br empty for iteration");}

      else{ //if the list has one or more items .
	    this.position = this.tail;   //sets the current node = tail
        this.prevPosition = ((BilinkedNode280<I>)this.tail).previousNode();  //sets previous node = previous node of the tail
	    this.position.nextNode = null;}   //sets next node = null
	}

	/**	Move back one item in the list. 
		Analysis: Time = O(1)
		@precond !before() 
	 */
	public void goBack() throws BeforeTheStart280Exception
	{   if(this.position == null && this.prevPosition == null)  //throws before the start exception
	           {throw new BeforeTheStart280Exception("Cannot go further back from before the start");}
	    else if (this.position != null && this.prevPosition == null) { //if cursor is at the first element.
	     	   this.position = null;
	     	   this.prevPosition = null;
	}
	    else{ //if the cursor is somewhere in the list except the first item.
		this.position = this.prevPosition;  //set the current position = previous position
		this.prevPosition = ((BilinkedNode280<I>)this.position).previousNode();} //set the previous position = the previous position of itself.

	}

	/**	Iterator for list initialized to first item. 
		Analysis: Time = O(1) 
	*/
	public BilinkedIterator280<I> iterator()
	{
		return new BilinkedIterator280<I>(this);
	}

	/**	Go to the position in the list specified by c. <br>
		Analysis: Time = O(1) 
		@param c position to which to go */
	@SuppressWarnings("unchecked")
	public void goPosition(CursorPosition280 c)
	{
		if (!(c instanceof BilinkedIterator280))
			throw new InvalidArgument280Exception("The cursor position parameter" 
					    + " must be a BilinkedIterator280<I>");
		BilinkedIterator280<I> lc = (BilinkedIterator280<I>) c;
		this.position = lc.cur;
		this.prevPosition = lc.prev;
	}

	/**	The current position in this list. 
		Analysis: Time = O(1) */
	public BilinkedIterator280<I> currentPosition()
	{
		return  new BilinkedIterator280<I>(this, this.prevPosition, this.position);
	}

	
  
	/**	A shallow clone of this object. 
		Analysis: Time = O(1) */
	public BilinkedList280<I> clone() throws CloneNotSupportedException
	{
		return (BilinkedList280<I>) super.clone();
	}


//* Regression test. */
	public static void main(String[] args) {
		BilinkedList280<Integer> list = new BilinkedList280<Integer>();


//Testing method insertFirst(I x).

	//case 1: test on an empty list and for any unexpected exception thrown
	  try{
	  list.insertFirst(1); //calling method on empty list.
	  if(list.head.item != 1 && list.tail.item != 1){
	  	 System.out.println("Error in Method insertFirst(I x) on empty list : Expected val of head and tail : 1, val head :" + list.head.item + ",val tail :"+ list.tail.item);}}
	  catch(Exception e){System.out.println("Unexpected exception thrown in insertFirst(I x): on empty list");}

	//case 2: test with one element in the List and checking for any unexpected exception thrown.
	  try{
	  list.insertFirst(2);  //adding the only one element and calling the method.
		if(list.head.item != 2){
			System.out.println("Error in Method insertFirst(I x): list with one element: Expected val : 2 , val :" + list.head.item);}}
	  catch(Exception e){System.out.println("Unexpected exception thrown in insertFirst(I x): on list with one item");}
	//case 3: test with multiple element in the List and checking for any unexpected exception thrown
		try{ //adding muliple items to the list and checking if the latest item added is the head.
		list.insertFirst(5);
		list.insertFirst(7);
		list.insertFirst(8);
		if(list.head.item != 8){
			System.out.println("Error in Method insertFirst(I x) with multiple elements: Expected val : 8 , val :" +list.head.item);}}
		catch(Exception e){System.out.println("Unexpected exception thrown in insertFirst(I x): on  list with multiple items");}

//Testing method insertLast(I x)
    //case1: test on an empty List and checking for any unexpected exception thrown
		BilinkedList280<Integer> list1 = new BilinkedList280<Integer>();
		try{ //adding one element at last of the list.
		list1.insertLast(1);
		if(list1.head.item != 1 && list1.tail.item != 1){
			System.out.println("Error in Method insertLast(I x) empty list: Expected val for head and tail : 1 , val head:" + list1.head.item +"val tail :"+ list1.tail.item);}}
		catch(Exception e){System.out.println("Unexpected exception thrown in insertLast(I x): on empty list");}
    //case2: a list with two items in it and checking for any unexpected exception thrown
		try{
        list1.insertLast(2); //adding the second element to the list.
		if(list1.tail.item != 2){
			System.out.println("Error in Method insertLast(I x) list with two items : Expected val : 2 , val : " + list1.tail.item);}}
		catch(Exception e){System.out.println("Unexpected exception thrown in insertLast(I x): on  list with two items");}
	//case 3: with multiple element in the List and checking for any unexpected exception thrown
		try{ //adding multiple items in the list.
		list1.insertLast(3);
		list1.insertLast(5);
		list1.insertLast(8);
		if(list1.tail.item != 8){
			System.out.println("Error in Method insertLast(I x) Multiple items in list : Expected val : 2 , val :  " +list1.tail.item);}}
		catch(Exception e){System.out.println("Unexpected exception thrown in insertLast(I x): on  list with multiple items");}
//Testing deleteFirst()
		BilinkedList280<Integer> list3 = new BilinkedList280<Integer>();
		//Testing for expected exception.
		try{
			list3.deleteFirst();
			System.out.println("ContainerEmpty280Exceptiion expected but did not occur");}
		catch(ContainerEmpty280Exception e){}
	//Case 1:  test with one item in the List and checking for any unexpected exception thrown
	  list3.insertFirst(1);  //adding an item in the list .
	  try{
	  list3.deleteFirst();
	  if(list3.has(1)){
	  	System.out.println("Error: in method deleteFirst: with one item in the List: item: 1: still in the List" ); }}
	  catch(Exception e){System.out.println("Unexpected exception thrown in deleteFirst(): on  list with one item");}

    //Case 2: Multiple items in the List and checking for any unexpected exception thrown
		list3.insertFirst(3);
	    list3.insertFirst(1);
	    list3.insertLast(5);
	    try{
	    list3.deleteFirst();
	    if(list3.has(1)){
	    	System.out.println("Error: in method deleteFirst: with  Multiple items in the List: item 1: still in the List");} }
	    catch(Exception e){System.out.println("Unexpected exception thrown in deleteFirst(): on  list with multiple items");}
//Testing deleteLast() method .

		BilinkedList280<Integer> list4 = new BilinkedList280<Integer>();
	    //Testing for expected exception
	    try{
	    list4.deleteLast();
	    System.out.println("ContainerEmpty280Exceptiion expected but did not occur");}
	    catch(ContainerEmpty280Exception e){}
	//Case1: list with one item and checking for any unexpected exception thrown
	    list4.insertLast(5);
	    try{
	    list4.deleteLast();
	    if(list4.has(5)){
			System.out.println("Error: in method deleteLast: with  one item in the List: item: 5 still in the List");} }
		catch(Exception e){System.out.println("Unexpected exception thrown in deleteLast(): on  list with one item");}
    //Case2: list with two items and checking for any unexpected exception thrown
        list4.insertLast(6);
	    try{
	    list4.deleteLast();
		if(list4.has(6)){
			System.out.println("Error: in method deleteLast: with  Multiple items in the List: item : 6 ,still in the List");}}
		catch(Exception e){System.out.println("Unexpected exception thrown in deleteLast(): on  list with two items");}
	//Case3: list with multiple items and checking for any unexpected exception thrown
        list4.insertLast(7);
		list4.insertLast(8);
		list4.insertLast(9);
		list4.insertLast(10);
		try{
		list4.deleteLast();
		if(list4.has(10)){
			System.out.println("Error: in method deleteLast: with  Multiple items in the List: item : 10. still in the List");}}
		catch(Exception e){System.out.println("Unexpected exception thrown in deleteLast(): on  list with multiple items");}

//Testing for deleteItem().
		BilinkedList280<Integer> list5 = new BilinkedList280<Integer>();
	//Test for expected exception thrown
		try{
		list5.deleteItem();
		System.out.println("Expected Exception not thrown in deleteItem() ");}
		catch (NoCurrentItem280Exception e){}

	//Test with cursor at the first item and checking for any unexpected exception thrown
	  list5.insertFirst(2);
	  list5.goFirst();	//to bring the cursor at item first.
	  try{
	  list5.deleteItem();
	  if(list5.head != null){
	  	System.out.println("Error: in method deleteItem():   cursor at first item : item: 2 , still in List");
	  }}
	  catch(Exception e){ System.out.println("unexpected exception thrown in Test:with one item in the list");}

	//Test with cursor at last item and checking for any unexpected exception thrown
	  list5.insertFirst(4);
	  list5.insertFirst(5);
	  list5.insertFirst(6);
	  list5.insertFirst(7);
	  list5.goFirst();       // bring the cursor to the last element.
	  while(list5.position.item != 4){
	  	list5.goForth(); }

	  try {
		  list5.deleteItem();
		  if (list5.has(4)) {
			  System.out.println("Error in method deleteItem():cursor at last item: item: 4 still in the list ");
		  }
	  }
      catch(Exception e){System.out.println("Unexpected exception thrown : in deleteItem(): test with cursor at last item");}

	//Test with cursor somewhere in the list and checking for any unexpected exception thrown
		list5.insertFirst(8);
	    list5.insertFirst(9);
	    list5.insertFirst(10);
		list5.goFirst();
	    while(list5.position.item != 9){ list5.goForth();} //Takes the cursor in the middle of the list approx.

	    try{
	    list5.deleteItem();
	    if(list5.has(9)){ System.out.println("Error in method deleteItem(): cursor at somewhere in the list  : item: 8 still in List");}}
	    catch (Exception e){ System.out.println("Unexpected exception thrown : in deleteItem(): cursor at somewhere in the list");}

//Testing goLast().
		BilinkedList280<Integer> list6 = new BilinkedList280<Integer>();
	//Testing for expected exception .
	try{
		list6.goLast();
		System.out.println("Error : exception expected but none thrown in goLast() , empty List"); }
	catch (ContainerEmpty280Exception e){}

	//Test when the cursor is at the first and last item , ie a list with 1 item
		list6.insertFirst(5);
	    try{
	    list6.goLast();
	    if(list6.tail.item !=5){
	    	System.out.println("Error: in method goLast: test with cursor at first & last item : Expected val : 5 , val :" + list6.tail.item );} }
		catch (Exception e){ System.out.println("Unexpected exception thrown : goLast(): cursor at first & Last item");}
	//Test when cursor is at the begining of the List
		list6.insertFirst(6);
		list6.insertFirst(7);
		list6.insertFirst(8);
		list6.insertFirst(9);
		list.goFirst();
		try{
		list.goLast();
		if(list6.tail.item != 5) {
			System.out.println("Error: in goLast : cursor at the begining : Expected val : 5 , val:"+ list6.tail.item);
		}}
		catch (Exception e){ System.out.println("Unexpected exception thrown : goLast(): cursor at first item");}
	//Test when cursor is somewhere middle of the List
		list6.goFirst();
        while(list6.position.item != 7){
        	list6.goForth();
		}
        list6.goLast();
		try{
			list.goLast();
			if(list6.tail.item != 5) {
				System.out.println("Error: in goLast() : cursor at the begining : Expexted val : 5, val :" + list6.tail.item );
			}}
		catch (Exception e){ System.out.println("Unexpected exception thrown : goLast(): cursor at somewhere in the List");}

//Test for goBack()	.
	//Test to catch expected exception.
		BilinkedList280<Integer> list7 = new BilinkedList280<Integer>();
		list7.insertLast(8);
		try{
		list7.goBack();
		System.out.println("Error exception expected in goBack() none thrown");}
		catch(BeforeTheStart280Exception e){ }
    //Test when the cursor is on the first item.
		list7.goForth(); //cursor is now at element 1
		list7.goBack();  //test
		try{
		if(list7.position != null){
			System.out.println("Error: in goBack(): cursor at first item: position not null.");} }
		catch(Exception e){System.out.println("Unexpected exception in goBack() : cursor at first item of the list");}


	//Test when the cursor is at the last item
	    list7.insertLast(9);
		list7.insertLast(11);
		list7.insertLast(22);
        list7.goLast();


        try{

		list7.goBack();
		if(list7.position .item != 11){
			System.out.println( "Error in goBack() : cursor at last item: unexpected item found : " + list7.position.item);}}

		catch(Exception e){System.out.println("Unexpected exception in goBack() : cursor at last item of the list ");}
    //Test when cursor is somewhere in between the List
		list7.goFirst();
		list7.goForth();
		list7.goForth();
		try{
			list7.goBack();
			if(list7.position.item != 9){ System.out.println("Error in goBack: when cursor is somwhere in between List: Expected val :9 Val obtained :" + list7.position.item);} }
        catch(Exception e){ System.out.println("Error: unexpected exception thrown");}

//Test Iterator method goLast();
		BilinkedList280<Integer> list8 = new BilinkedList280<Integer>();
        BilinkedIterator280<Integer> itr = new BilinkedIterator280<Integer>(list8);
    //Test for expected exception.
		try{
		itr.goLast();
		System.out.println("Error: exception not thrown");}
		catch(ContainerEmpty280Exception e){}

    //Test when iterator is at first item and only item .
		list8.insertFirst(8);
		itr.goForth();
		try{
		itr.goLast();
		if(itr.cur.item != 8){System.out.println("Error in iterator method goLast: expected val: 8 , val obtained : "+ itr.cur.item);}}
		catch(Exception e){System.out.println("Error unexpected exception in: itr.goLast() : when iterator is at first item and only item");}

	//Test when the iterator is somehere in the list.
		list8.insertFirst(10);
		list8.insertFirst(11);
		list8.insertFirst(12);
		list8.insertFirst(13);
		itr.goFirst();
		itr.goForth();
		itr.goForth();
		try{
			itr.goLast();
			if(itr.cur.item != 8){
				System.out.println("Error in itr goBack():  when the iterator is somehere in the list. Expected val :8 , val obtained "+itr.cur.item); } }
		catch(Exception e){System.out.println("Error unexpected exception in: itr.goLast() : when iterator is somehere in the list");}
//Test iterator method goBack()
		//Test to catch expected exception.
		BilinkedList280<Integer> list9 = new BilinkedList280<Integer>();
		BilinkedIterator280<Integer> itr1 = new BilinkedIterator280<Integer>(list9);

		list9.insertLast(8);
		try{
			list9.goBack();
			System.out.println("Error exception expected in itr1 goBack() none thrown");}
		catch(BeforeTheStart280Exception e){ }

        //Test when the cursor is on the first item.
		itr1.goForth(); //cursor is now at element 1
		itr1.goBack();  //test
		try{
			if(itr1.cur != null){
				System.out.println("Error: in itr goBack(): cursor at first item: position not null ");} }
		catch(Exception e){System.out.println("Unexpected exception in itr1 goBack() : cursor at first item of the list");}

		//Test when the cursor is at the last item
		list9.insertLast(9);
		list9.insertLast(11);
		list9.insertLast(22);
		itr1.goLast();
		try{

			itr1.goBack();
			if(itr1.cur .item != 11){
				System.out.println( "Error in itr goBack() : cursor at last item: unexpected item found : " + itr1.cur.item);}}
		catch(Exception e){System.out.println("Unexpected exception in itr goBack() : cursor at last item of the list ");}

	}


}
