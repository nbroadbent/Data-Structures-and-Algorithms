/*** 
 * This is class implements a trie that holds a set of strings.
 * MyTrie stores stores nodes using class TreeNode
 * 
 * Name:   		Nicholas Broadbent
 * Student Number: 	8709720
 * Uottawa Email: 	nbroa025@uottawa.ca
 * 
 *
 */

public class MyTrie {
	
	private TreeNode root = null;

	private int numNodes;

    	// Constructor. Note that an empty trie (no strings added) contains the root node 
	public MyTrie() {
		root = new TreeNode(null, null, null,false); 
		numNodes=1;
	}

	// Method to be implemented by you. See handout part 1A
	public boolean insert(String s) {
		TreeNode curr = root;
		TreeNode child;
		int len = s.length();
		
		for (int i = 0; i < len; i++){
			if (s.charAt(i) == '0'){
				child = curr.getLeftChild();
				// Create left child if it does not exist.
				if (child == null){
					child = new TreeNode(curr, null, null, false);
					curr.setLeftChild(child);
					numNodes++;
				}
			}
			else{
				child = curr.getRightChild();
				// Create right child if it does not exist.
				if (child == null){
					child = new TreeNode(curr, null, null, false);
					curr.setRightChild(child);
					numNodes++;
				}
			}
			curr = child;
		}
		// If string already exists.
		if (curr.getIsUsed())
			return false;
		
		curr.setIsUsed(true);
		return true;
	}
	
	// Method to be implemented by you. See handout part 1A
	public boolean search(String s) {
		TreeNode parent = root;
		TreeNode child;
		
		for (int i = 0; i < s.length(); i++){
			child = (s.charAt(i) == '0') ? parent.getLeftChild() : parent.getRightChild();
			
			if (child == null) 
				return false;
			parent = child;
		}
		return parent.getIsUsed();
	}

	// Method to be implemented by you. See handout part 1A	
	public void printStringsInLexicoOrder() {
		printStringsInLexicoOrder(root, "");
	}
	private void printStringsInLexicoOrder(TreeNode N, String s) {
		if (N != null) {
			if (N.getIsUsed())
				System.out.print(s + ",");
			
			printStringsInLexicoOrder(N.getLeftChild(), s + "0");
			printStringsInLexicoOrder(N.getRightChild(), s + "1");
		}
	}
	
	
	// the following method that calls its recursive counterpart
	// prints the tree and its boolean values at nodes in 
	// in-order traversal order
	
	public void printInOrder() { // not to be changed
		printInOrder(root);
	}
	private void printInOrder(TreeNode N) { // not to be changed
		System.out.print("(");
		if (N!=null) {
			printInOrder(N.getLeftChild());
			System.out.print(N.getIsUsed());
			printInOrder(N.getRightChild());

		}
		System.out.print(")");
	}
	
	public TreeNode root() { // not to be changed
		return root;
	}
	
	public int numNodes() { // not to be changed
		return numNodes;
	}
}
