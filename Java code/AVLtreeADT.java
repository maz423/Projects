/* Name :Mohammad Aman Zargar
        NSID: Maz423
        Student ID :11265940
*/



package lib280.tree;

import lib280.base.Searchable280;
import lib280.exception.ItemNotFound280Exception;
import lib280.exception.NoCurrentItem280Exception;

public class AVLtreeADT<I extends Comparable<? super I>> extends OrderedSimpleTree280<I> implements Searchable280<I> {

    /***
     * inherits attributes from OrderedSimpleTree280<I>.
     */
    public AVLtreeADT(){
        super();
    }


    /**
     * insert item in an AVL tree while mentaining the AVL property.(ie |max imbalance| <=1) at every node.
     * @precond:
     * @param x: the item to be inserted.
     * @postcond: intem x is inserted in the tree while mentaining the AVL property of the tree.
     * @timing: worst case time complexity is O(log(n)).
     */
    public void insert(I x){
        if (isEmpty())    //if tree is empty make x the root node.
            rootNode = (AVLnode<I>)createNewNode(x);

        else if (x.compareTo(rootItem()) < 0)  //if x < root , traverse the left subtree.
        {   ((AVLnode<I>)this.rootNode).LHeight ++;  //add one to LHeight since our node will be inserted in the left subtree.
            AVLtreeADT<I> leftTree = (AVLtreeADT<I>)rootLeftSubtree(); //left subtree of the current tree
            leftTree.insert(x);    //recursivly insert x in left subtree.
            setRootLeftSubtree(leftTree);  //set root subtree to leftree . since root has been removed.
            ((AVLnode<I>) this.rootNode).LHeight = 1 + Math.max(((AVLnode<I>)this.rootNode.leftNode).LHeight ,((AVLnode<I>)this.rootNode.leftNode).RHeight);
        }
        else  //if x > root , traverse the right subtree.
        {   ((AVLnode<I>)this.rootNode).RHeight ++;    //add one to RHeight since our node will be inserted in the right subtree.
            AVLtreeADT<I>  rightTree = (AVLtreeADT<I>)rootRightSubtree(); //right subtree of the current tree
            rightTree.insert(x);     //recursivly insert x in right subtree.
            setRootRightSubtree(rightTree); //set root subtree to right . since root has been removed.

            ((AVLnode<I>) this.rootNode).RHeight = 1 + Math.max(((AVLnode<I>)this.rootNode.rightNode).LHeight ,((AVLnode<I>)this.rootNode.rightNode).RHeight);
        }
        //Restore AVL property. ie (|max imbalance| <=1)
        this.RestoreAVL();  // check if AVL property holds , do nothing if it holds , else restore AVL property.ie (|max imbalance| <=1)



    }

    /***
     * find signed imbalance at root node of current tree.
     * @precond: the node must have LHeight and RHeight attributes.
     * @postcond: None.
     * @return: int indicating imbalance at the root node.
     * @timing: O(1) .
     */
    public int signed_imbalace()
    { return ((AVLnode<I>)this.rootNode()).get_LHeight() - ((AVLnode<I>)this.rootNode()).get_RHeight();   //imablance = left subtree height - right subtree height.
    }

    /***
     * Restores the Avl property of the tree such that max imbalance at every node is not greater than 1 or less than -1
     * @precond:  None
     * @postcond: restores AVL tree where |max imbalance| <= 1.
     * @timing: O(1) .
     *
     */
    public void RestoreAVL(){

      if (Math.abs(this.signed_imbalace()) <= 1 ) {return;}   //no imbalace do nothing.

      else if (this.signed_imbalace() == 2 ){         //R is left heavy. two cases arise . LL imbalance and LR imabalce .
         if (((AVLtreeADT<I>)this.rootLeftSubtree()).signed_imbalace() >= 0){  //LL imbalace .
             this.rightRotate(); //fix LL imbalance bt right rotation.
         }
         else{     // LR imbalance! Do double right rotation.
             AVLtreeADT<I> ltree = (AVLtreeADT<I>)this.rootLeftSubtree();  //variable to store leftsubtree
             ltree.LeftRotate(); //LeftRotate left subtree.
             this.setRootLeftSubtree(ltree); // attach leftsubree .

             this.rightRotate();//RightRotate current tree.
         }
      }
      else { //imablace is <=0 . two cases arise. RR imblance and RL imbalance.
          if (((AVLtreeADT<I>)this.rootRightSubtree()).signed_imbalace() <= 0) { //if tree is right heavy.

              this.LeftRotate();  //left rotate
          }
          else{ // the tree has an RL imbalace .
              AVLtreeADT<I> rtree = (AVLtreeADT<I>)this.rootRightSubtree(); //variable to store right subtree.
              rtree.rightRotate(); //rightrotate right subtree.
              this.setRootRightSubtree(rtree);   // attach rightsubree .

              this.LeftRotate(); //leftrotate current tree.
          }
      }
    }

    /***
     * create a new AVL node.
      * @param x: item in the AVL node
     * @return: the AVL node created.
     */
   public AVLnode<I> createNewNode(I x){
       return  new AVLnode<I>(x);
   }

    /***
     * left rotate to fix RR imbalance.
     * @precond: the node must have an RR imbalance .
     * @postcond:  RR imbalace is fixed by right rotation
     * @timing: O(1) .
     *
     */
   public void LeftRotate(){
       AVLnode<I> critical_node = (AVLnode<I>)this.rootNode();  //variables to refer to critical node , rightnode, rightnode -> leftnode
       AVLnode<I> right_son = critical_node.rightNode();
       AVLnode<I> right_son_leftchild = right_son.leftNode();
       if(right_son_leftchild == null){ //if right son has no left children , make height of critical node 0
           critical_node.RHeight = 0;   //because this node will be the critical nodes right node /right child.
       }
       else{ //if left node is a node and has subnodes attached. set right height of critical node to max height of the new subtree + 1.
           critical_node.RHeight = 1 + Math.max(right_son_leftchild.LHeight,right_son_leftchild.RHeight);
       }
       critical_node.setRightNode(right_son_leftchild); //set critical nodes left node = its right node -> left node.

       right_son.setLeftNode(critical_node); //set right sons left Node = critical node .
       right_son.LHeight = 1 + Math.max(critical_node.LHeight,critical_node.RHeight); // recompute height of right son.
       this.setRootNode(right_son); //make right son the new root node
   }

    /***
     * right rotate to fix LL imbalace .
     * @precond: the node must have an LL imbalance .
     * @postcond: LL imbalance is fixed by RR rotation.
     * @timing: O(1) .
     *
     */
   public void rightRotate(){
       AVLnode<I> critical_node = (AVLnode<I>)this.rootNode(); //variables to refer to critical node , leftnode, leftnode ->rightnode
       AVLnode<I> left_son = critical_node.leftNode();
       AVLnode<I> left_son_rightchild = left_son.rightNode();

       if(left_son_rightchild == null){ //if left son has no left children , make height of critical node 0
           critical_node.LHeight = 0;   //because this node will be the critical nodes left node /left child.
       }
       else{ //if right node is a node and has subnodes attached. set left height of critical node to max height of the new subtree + 1.
           critical_node.LHeight = 1 + Math.max(left_son_rightchild.LHeight,left_son_rightchild.RHeight);
       }
       critical_node.setLeftNode(left_son_rightchild); //set critical nodes right node = its left node -> right node.


       left_son.setRightNode(critical_node); //set left sons right Node = critical node .
       left_son.RHeight = 1+ Math.max(critical_node.LHeight,critical_node.RHeight); // recompute height of right son.

       this.setRootNode(left_son);  //make left son the new root node.
   }

    /***
     * to delete a given node from the AVL tree while mentaining the AVL property.
     * @precond: the item should exist in the tree.
     * @param x: the node to be deleted
     * @postcond:  the item will be deleted and any nessecary rotations applied to restore AVL property.
     * @timing: worst case time complexity is O(log(n)).
     */
   public void delete(I x){
      if(this.rootNode == null){ throw new ItemNotFound280Exception("item to be deleted not found");} //if item does not exist throw exception.

      if(x.compareTo(this.rootNode.item()) == 0){ //if item is at the root. we have three cases , item has zero ,one, or two children.
          if(((AVLnode<I>)this.rootNode).LHeight == 0 && ((AVLnode<I>)this.rootNode).RHeight == 0){ //if node has zero children set it to null.
              this.rootNode = null;
          }
          else if(((AVLnode<I>)this.rootNode).LHeight == 1 && ((AVLnode<I>)this.rootNode).RHeight == 0 ){ //if node has a left child set it to parent . and delete its original node.
              AVLnode<I> left_Node = (AVLnode<I>)this.rootNode.leftNode;
              AVLnode<I> root = (AVLnode<I>)this.rootNode;
              this.rootNode = left_Node; //set root node to left node.
              root.leftNode = null;   //set left node to null.
          }
          else if (((AVLnode<I>)this.rootNode).RHeight == 1 && ((AVLnode<I>)this.rootNode).LHeight == 0){  //if node has a rightchild set it to parent . and delete its original node.
              AVLnode<I> right_Node = (AVLnode<I>)this.rootNode.rightNode;
              AVLnode<I> root = (AVLnode<I>)this.rootNode;
              this.rootNode = right_Node; //set root node to right node.
              root.rightNode = null;  //set rightnode to null.
          }
          else{ // node had two children  replace the node with the inorder successor.
              AVLnode<I> lnode = (AVLnode<I>)this.rootNode().leftNode;
              AVLnode<I> target = (AVLnode<I>)this.rootNode().rightNode;
              AVLnode<I> prev = (AVLnode<I>)this.rootNode;

              boolean found = false;

              while (target.leftNode() != null){ //find inorder successor. loop untill left child of right subtree is null.
                  prev = target; //prev node to succesor
                  target = (AVLnode<I>)target.leftNode; //the inorder succesor
                  found = true;
              }

              AVLtreeADT<I> rtree = (AVLtreeADT<I>)this.rootRightSubtree();
              AVLtreeADT<I> ltree = (AVLtreeADT<I>)this.rootLeftSubtree();
              if(found){ //if while loop was executed ie rightsubtree -> leftnode was not null.
              prev.leftNode = null;  //set node prev to target = null
              this.setRootNode(target); //set target as the root node.
              this.setRootRightSubtree(rtree); //attach right subtree after changing nodes
              ((AVLnode<I>) this.rootNode).RHeight = 1 + Math.max(((AVLnode<I>)rtree.rootNode).LHeight,((AVLnode<I>)rtree.rootNode).RHeight); //recompute Rheight
              this.setRootLeftSubtree(ltree); //attach left subtree after changing nodes.
              ((AVLnode<I>) this.rootNode).LHeight = 1 + Math.max(((AVLnode<I>)ltree.rootNode).LHeight,((AVLnode<I>)rtree.rootNode).RHeight); //recompute LHeight
              target = null;}
              else{//if while loop was not executed and right subtree root was the inorder successor.
                this.setRootNode(target); //set right subtree node to root
                this.rootNode.setLeftNode(lnode); //attach leftnode/left subtree
                ((AVLnode<I>) this.rootNode).LHeight = lnode.LHeight + 1; //recompute height after changes.
              }
          }
      }
      else{ //if item was not at the root.
          if(x.compareTo( this.rootNode.item()) <= 0 ){ //if item is less than or equal to the item at root .
              AVLtreeADT<I> leftTree = (AVLtreeADT<I>)rootLeftSubtree(); // ltree refers to left subtree.
              leftTree.delete(x); //traverse left
              this.setRootLeftSubtree(leftTree); //attach left subtree after item has been deleted.
              if(this.rootLeftSubtree().rootNode != null){ //if rootleftsubtree node is not null
              ((AVLnode<I>) this.rootNode).LHeight = 1 + Math.max(((AVLnode<I>)leftTree.rootNode).LHeight,((AVLnode<I>)leftTree.rootNode).RHeight);} // recompute height
              else{((AVLnode<I>)this.rootNode).LHeight --;} //if rootleftsubtree node is  null. subtract height by 1.


          }
          else {//if item is greater than the item at root .
              AVLtreeADT<I> rightTree = (AVLtreeADT<I>)rootRightSubtree(); //right subtree .
              rightTree.delete(x); //traverse tight
              this.setRootRightSubtree(rightTree);  //attach right subtree after item has been deleted.
              if(this.rootRightSubtree().rootNode != null){ //if rootrightsubtree node is not null
              ((AVLnode<I>) this.rootNode).RHeight = 1 + Math.max(((AVLnode<I>)rightTree.rootNode).LHeight,((AVLnode<I>)rightTree.rootNode).RHeight);} //recompute height.
              else{((AVLnode<I>)this.rootNode).RHeight --;} //if rootrightsubtree node is  null. subtract height by 1.
          }
          RestoreAVL(); // check if AVL property holds , do nothing if it holds , else restore AVL property.ie (|max imbalance| <=1)
      }
   }

    protected String toStringByLevel(int i)
    {
        StringBuffer blanks = new StringBuffer((i - 1) * 5);
        for (int j = 0; j < i - 1; j++)
            blanks.append("                     ");

        String result = new String();
        if (!isEmpty() && (!rootLeftSubtree().isEmpty() || !rootRightSubtree().isEmpty()))
            result += rootRightSubtree().toStringByLevel(i+1);
        result += "\n" + blanks + i + ": " ;
        if(this.rootNode != null){
        result += "\n" + blanks + "(L:" + ((AVLnode<I>)this.rootNode).LHeight + "," + "R:" + ((AVLnode<I>)this.rootNode).RHeight +")" + "-->" + ": LVL"+ i + ": "  ;}
        else{result += "";}
        if (isEmpty())
            result += "-";
        else
        {
            result += rootItem();
            if (!rootLeftSubtree().isEmpty() || !rootRightSubtree().isEmpty())
                result += rootLeftSubtree().toStringByLevel(i+1);
        }
        return result;
    }




    public static void main(String[] args) {
//testing .
        //creating a tree for testing purposes.
        AVLtreeADT<BinaryNode280<Integer>> tree1 = new AVLtreeADT<BinaryNode280<Integer>>();
        AVLtreeADT<BinaryNode280<Integer>> tree2 = new AVLtreeADT<BinaryNode280<Integer>>();
        AVLtreeADT<BinaryNode280<Integer>> tree3 = new AVLtreeADT<BinaryNode280<Integer>>();

//testing insert method:

// testing insert on an empty tree.
        AVLnode<Integer> x = new AVLnode<>(60);
        tree1.insert(x);
        //create node to check if node exists.

        if(!(tree1.has(x))){
            System.out.println("Error in insertion on empty tree item 60 not in list ");
        }
        //insert item after root node.
        AVLnode<Integer> y = new AVLnode<>(50);
        tree1.insert(y);
        if(!(tree1.has(y))){
            System.out.println("Error in insertion on tree, item 50 not in tree ");
        }
        //testing insertion with 2 nodes in the tree.
        AVLnode<Integer> z = new AVLnode<>(70);
        tree1.insert(z);
        if(!(tree1.has(z))){
            System.out.println("Error in insertion on tree, item 70 not in tree ");}


//testing for rotaions after insertion.

// case for LL rotation
        System.out.println("Case 1 : Check for LL rotation");
        AVLnode<Integer> a = new AVLnode<>(45);
        tree1.insert(a);

        System.out.println("Tree before insertion of element 40 -----------------------------> LL rotation expected");
        System.out.println(tree1.toStringByLevel() );
        AVLnode<Integer> d = new AVLnode<>(40);
        tree1.insert(d);
        System.out.println("tree after inertion of element 40 -------------------------------> LL performed succesfully");

//case for RR

        System.out.println(tree1.toStringByLevel() );

        System.out.println("Case 2 : Check for RR rotation");
        tree2.insert(new AVLnode<>(90));
        tree2.insert(new AVLnode<>(100));
        tree2.insert(new AVLnode<>(95));
        tree2.insert(new AVLnode<>(110));
        System.out.println("Tree before insertion of element 115 -----------------------------> RR rotation expected");
        System.out.println(tree2.toStringByLevel() );
        tree2.insert(new AVLnode<>(115));
        System.out.println("Tree after insertion of element 115 -----------------------------> RR performed succesfully");
        System.out.println(tree2.toStringByLevel() );

//case for LR rotation.
        System.out.println("Case 3 : Check for LR rotation");
        System.out.println("Tree before insertion of element 48 -----------------------------> insertion causes LR imbalace");
        System.out.println(tree1.toStringByLevel() );
        tree1.insert(new AVLnode<>(48));
        System.out.println("Tree after insertion of element 48 -----------------------------> LR imbalance fixed.");
        System.out.println(tree1.toStringByLevel() );

//case for RL rotation.
        System.out.println("Case 4 : Check for RL rotation");
        System.out.println("Tree before insertion of element 98 -----------------------------> insertion causes RL imbalace");
        System.out.println(tree2.toStringByLevel() );
        tree2.insert(new AVLnode<>(98));
        System.out.println("Tree after insertion of element 98 -----------------------------> RL imbalance fixed");
        System.out.println(tree2.toStringByLevel() );


//Tsting deletion method.

        AVLtreeADT<BinaryNode280<Integer>> tree4 = new AVLtreeADT<BinaryNode280<Integer>>();
        try{
            tree4.delete(x);
            System.out.println("Expected item not found exception");
        }
        catch (ItemNotFound280Exception e){}

//case1 causes LL imbalace
          System.out.println("Case 1 : Check for LL imbalance after deletion");
          tree1.delete(new AVLnode<>(70));
          System.out.println("Tree before deletion of element 60 -----------------------------> LL rotation expected");
          System.out.println(tree1.toStringByLevel() );
          tree1.delete(new AVLnode<>(60));
          System.out.println("Tree after deletion of element 60 -----------------------------> LL rotation completed succesfully");
          System.out.println(tree1.toStringByLevel() );

//case2 deletion causes RR imbalance.
        System.out.println("Case 2 : Check for RR imbalance after deletion");
        tree2.delete(new AVLnode<>(90));
        tree2.delete(new AVLnode<>(98));
        System.out.println("Tree before deletion of element 95 -----------------------------> RR rotation expected");
        System.out.println(tree2.toStringByLevel() );
        tree2.delete(new AVLnode<>(95));
        System.out.println("Tree after deletion of element 95 -----------------------------> RR rotation completed successfully");
        System.out.println(tree2.toStringByLevel() );

//case3 : deletion causes LR imbalace .
        System.out.println("Case 3 : Check for RL imbalance after deletion");
        tree2.insert(new AVLnode<>(90));
        tree2.insert(new AVLnode<>(95));
        tree2.delete(new AVLnode<>(90));


        System.out.println("Tree before deletion of element 115 -----------------------------> deletion causes LR imbalance");
        System.out.println(tree2.toStringByLevel() );
        tree2.delete(new AVLnode<>(115));

        System.out.println("Tree after deletion of element 115 -----------------------------> LR imbalance fixed.");
        System.out.println(tree2.toStringByLevel() );

//cas4 deletion causes RL imbalance .
        System.out.println("Case 4 : Check for LR imbalance after deletion");
        tree2.insert(new AVLnode<>(105));
        System.out.println("Tree before deletion of element 95 -----------------------------> deletion causes RL imbalance");
        System.out.println(tree2.toStringByLevel() );
        tree2.delete(new AVLnode<>(95));
        System.out.println("Tree after deletion of element 95 -----------------------------> RL imbalace fixed.");
        System.out.println(tree2.toStringByLevel() );





    }
}
