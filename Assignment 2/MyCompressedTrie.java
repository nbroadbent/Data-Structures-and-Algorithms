
/*** 
 * This is class implements a compressed trie that holds a set of strings.
 * MyCompressedTrie stores nodes using class TreeNodeWithData
 * 
 * Name:   
 * Student Number: 
 * Uottawa Email: 
 * 
 *
 */
public class MyCompressedTrie {
	
	private TreeNodeWithData root;
	
	private int numNodes;
	
	public MyCompressedTrie() { // simple constructor (empty trie consisting of root only)
		root = new TreeNodeWithData(null, null, null,false,null);
		numNodes=1;
	}
	
	// to be implemented by you see handout Part 2A
	// Constructor that receives a regular trie and creates this
	// instance that is a compressed trie
	// 
	public MyCompressedTrie(MyTrie trie) { 
		this();
		createCompressedTrie(trie.root(), root, -1, "");
		//removeRedundant(trie.root(), root, -1, "");
	}
	
	private void createCompressedTrie(TreeNode N, TreeNodeWithData C, int dir, String s){	
		if (N == null)
			return;
		
		boolean add = false;
		TreeNodeWithData t = C;
		TreeNode left = N.getLeftChild();
		TreeNode right = N.getRightChild();

		// Check if node is redundant.
		if (!N.getIsUsed() && dir != -1){
			// If both children are used, Node is safe.
			if (left != null && right != null){
				//if (left.getIsUsed() && right.getIsUsed())
					add = true;	
			}
			if (!add){
				if (left != null){
					if (dir == 0)
						N.getParent().setLeftChild(left);
					else
						N.getParent().setRightChild(left);
				}
				if (right != null){
					if (dir == 0)
						N.getParent().setLeftChild(right);
					else
						N.getParent().setRightChild(right);
				}
			}
		}
		else if (dir != -1){
			// Node is not redundant.
			add = true;
		}

		if (add){
			t = new TreeNodeWithData(C, null, null, N.getIsUsed(), s);
			if (dir == 0)
				C.setLeftChild(t); // error here
			else
				C.setRightChild(t);
			numNodes++;
		}
		
		createCompressedTrie(left, t, 0, s + "0");
		createCompressedTrie(right, t, 1, s + "1");
	}

	// Method to be implemented by you. See handout part 2A	
	public void printStringsInLexicoOrder() {
		printStringsInLexicoOrder(root);
	}
	private void printStringsInLexicoOrder(TreeNode N) {
		if (N != null) {
			if (N.getIsUsed())
				System.out.print(((TreeNodeWithData)N).getData() + ",");
			
			printStringsInLexicoOrder(N.getLeftChild());
			printStringsInLexicoOrder(N.getRightChild());

		}
	}

	// the following method that calls its recursive counterpart
	// prints the tree and its data values at nodes in 
	// in-order traversal order
	
	public void printInOrder() { // not to be changed
		printInOrder(root);
	}
	
	private void printInOrder(TreeNode N) { // not to be changed
		System.out.print("(");
		if (N!=null) {
			printInOrder(N.getLeftChild());
			System.out.print(((TreeNodeWithData)N).getData());
			printInOrder(N.getRightChild());

		}
		System.out.print(")");
	}
	

	
	public int numNodes() {
		return numNodes;	
	}
}
