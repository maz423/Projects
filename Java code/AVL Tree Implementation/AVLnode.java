/* Name :Mohammad Aman Zargar
        NSID: Maz423
        Student ID :11265940
*/

package lib280.tree;

public class AVLnode <I extends Comparable<? super I>> extends BinaryNode280<I>  {


    protected int LHeight;
    protected int  RHeight;
    /**
     * Construct a new node with item x.
     *
     * @param x the item placed in the new node
     * @timing Time = O(1)
     */
    public AVLnode(I x) {
        super(x);
        LHeight = 0;
        RHeight = 0;
    }

    /***
     * get the leftNode of the current node
     * @return: leftNode
     */
    public AVLnode<I> leftNode(){
        return (AVLnode<I>) this.leftNode;
    }

    /***
     *get the rightNode of the current node
     * @return: rightNode
     */
    public AVLnode<I> rightNode(){
        return (AVLnode<I>) this.rightNode;}

    /**
     *to get left subtree height of an AVL node.
     * @return: left subtree height of an AVL node.
     */
    public int get_LHeight(){
        return LHeight;
    }

    /**
     *to get right subtree height of an AVL node.
     * @return: right subtree height of an AVL node.
     */
    public int get_RHeight(){
        return RHeight;
    }
}
