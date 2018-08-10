/*
* The following code implements a binary search tree, with the specifications detailed at
* http://paleyontology.com/AP_CS/BST.html.
* Thanks to https://en.wikipedia.org/wiki/Binary_search_tree for helpful info about binary search
* trees in general and their specific methods.
* 
* Questions (note that n is equal to the number of elements in the tree):
* What is the worst case order of growth for finding a datum in a binary search tree: O(n)
* 
* What is the best case: O(1)
* 
* What do you think the average case order of growth is: O(log n)

* What is the order of growth to insert an element in the tree: same as searching, O(log n)
* 
* What is the order of growth to print the tree: O(n)
* 
* EXTRA CREDIT: What is the order of growth to delete an element: O(log n)
 */
import java.util.Random;

public class BST<T extends Comparable<T>> {
	private T datum;
	private BST<T> left;
	private BST<T> right;

	/**
	 * Constructs a new BST.
	 * 
	 * @param datum
	 *            the datum to be placed in the root node
	 */
	public BST(T datum) {
		this.datum = datum;
		left = null;
		right = null;
	}

	/**
	 * Returns the datum in the root of this tree.
	 */
	public T getDatum() {
		return datum;
	}

	/**
	 * Returns the left subtree.
	 */
	public BST<T> getLeft() {
		return left;
	}

	/**
	 * Returns the right subtree.
	 */
	public BST<T> getRight() {
		return right;
	}

	/**
	 * Sets the root of this tree to a specified datum.
	 * 
	 * @param datum
	 *            the specified datum to replace the old value
	 */
	public void setRoot(T datum) {
		this.datum = datum;
	}

	/**
	 * Recursively prints this tree in infix (in-order) notation.
	 */
	public void printTree() {
		if (left != null) {
			left.printTree();
		}
		System.out.print(this.datum + " ");
		if (right != null)
			right.printTree();
	}

	/**
	 * Returns a String representation of the tree.
	 */
	public String toString() {
		return (datum == null) ? "Empty" : "Tree with root " + datum.toString();
	}

	/**
	 * Calculates the depth of the tree, where one node counts as a depth of 1.
	 * 
	 * @return the depth of the tree
	 */
	public int depth() {
		if (left == null && right == null)
			return 1;

		if (left == null)
			return right.depth() + 1;
		if (right == null)
			return left.depth() + 1;

		return Math.max(left.depth(), right.depth()) + 1;

	}

	/**
	 * Inserts a datum into the tree, determining the proper location of
	 * insertion by recursively traversing through the tree and
	 * 
	 * @param datum
	 *            the datum to be inserted
	 */
	public void insert(T datum) {
		if (this.datum == null)
			this.datum = datum;
		else if (datum.compareTo(this.datum) < 0) { // argument < this datum
			if (left == null)
				left = new BST<T>(datum);
			else
				this.left.insert(datum);
		} else { // argument >= this datum
			if (right == null)
				right = new BST<T>(datum);
			else
				this.right.insert(datum);
		}

	}

	/*
	 * Errors
	 * 
	 * 
	 * public void delete(T datum) { if (datum.equals(this.datum)) {
	 * removeRoot(this); } else { if (datum.compareTo(this.datum) < 0) { //
	 * argument < this datum if (left == null) return; else
	 * this.left.delete(datum); } else { // argument >= this datum if (right ==
	 * null) return; else this.right.delete(datum); } } }
	 * 
	 * 
	 * 
	 * private void removeRoot(BST<T> subtree) { if (subtree.left == null){ //
	 * System.out.println(1); if (subtree.right == null){ // leaf subtree =
	 * null; } else // only right branch subtree = subtree.getRight();} else if
	 * (subtree.right == null) // only left branch subtree = subtree.getLeft();
	 * else {// root has two subtrees // find min-valued datum of the right
	 * subtree BST<T> minTree = subtree.getRight(); while (minTree.getLeft() !=
	 * null) minTree = minTree.getLeft();
	 * 
	 * // replace root with the min-valued datum of the right subtree
	 * subtree.setRoot(minTree.getDatum()); // delete min-valued datum minTree =
	 * null; System.out.println(minTree);
	 * 
	 * }
	 * 
	 * }
	 */

	/**
	 * Used to help with inserting 10000 values into a BST in random order in
	 * the main() method.
	 * 
	 * Randomly shuffles an array of integers.
	 * 
	 * @param a
	 *            the array of integers to be shuffled
	 */
	private static void shuffle(int[] a) {
		Random rand = new Random();
		int temp;

		for (int i = a.length - 1; i >= 1; i--) {
			int j = rand.nextInt(i + 1); // random index from 0 to i
			temp = a[i]; // swap a[j] and a[i]
			a[i] = a[j];
			a[j] = temp;
		}
	}

	public static void main(String[] args) {
		// test code
		BST<Integer> tree = new BST<Integer>(null);
		// insert 10000 nums into tree in random order
		int[] nums = new int[10000];
		for (int i = 1; i <= 10000; i++)
			nums[i - 1] = i;
		shuffle(nums);
		for (int i = 0; i < 10000; i++)
			tree.insert(nums[i]);
		// test depth of tree
		System.out.println(tree.depth());

		BST<Integer> tree2 = new BST<Integer>(null);
		tree2.insert(2);
		tree2.insert(7);
		tree2.insert(1);
		tree2.insert(5);
		tree2.printTree();

	}
}
