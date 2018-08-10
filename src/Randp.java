/*
 * The following code implements the Randp command, which produces a random integer 
 * between 1 and a specified number, keeping track of which numbers have already been chosen
 * and only returning a number which has not yet been chosen.  Upon choosing all the possible numbers, 
 * it will return 0.
 * 
 */
public class Randp {
	private int[] nums;
	private int numsLeft;

	/**
	 * Constructor
	 * 
	 * @param n
	 *            the upper bound of the integers to be chosen
	 */
	public Randp(int n) {
		initNums(n);
		numsLeft = n;
	}

	/**
	 * Initializes the nums array and fills it with the integers from 1 to n, in
	 * order.
	 * 
	 * @param n
	 */
	private void initNums(int n) {
		nums = new int[n];
		for (int i = 1; i <= n; i++)
			nums[i - 1] = i;
	}

	/**
	 * Finds and returns a random integer from 1 to n that has not yet been
	 * chosen.
	 * 
	 * @return random integer from 1 to n that has not been chosen yet
	 */
	public int nextInt() {
		if (numsLeft == 0)
			return 0;

		int retval;
		int next = (int) (Math.random() * numsLeft); // random index from 0 to
														// numsLeft-1
		retval = nums[next];

		swap(nums, next, numsLeft - 1);
		// swap nums[next] with nums[numsLeft - 1], moving the used value to the
		// end of the array

		numsLeft--;
		return retval;
	}

	/**
	 * Swaps two elements of an array at specified indices.
	 * 
	 * @param a
	 *            the array to be modified
	 * @param i
	 *            the index of the first element to be swapped
	 * @param j
	 *            the index of the second element to be swapped
	 */
	private static void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	public static void main(String[] args) {
		// test code
		Randp rand = new Randp(5);
		System.out.println(rand.nextInt());
		System.out.println(rand.nextInt());
		System.out.println(rand.nextInt());
		System.out.println(rand.nextInt());
		System.out.println(rand.nextInt());
		System.out.println(rand.nextInt()); // should print 0
		System.out.println(rand.nextInt()); // should print 0

	}
}