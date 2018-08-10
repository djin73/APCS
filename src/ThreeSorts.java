/*
 * Implements three sorting algorithms: selection sort, insertion sort, and bubble sort.
 * 
 * Thanks to:
 * https://en.wikipedia.org/wiki/Selection_sort
 * https://en.wikipedia.org/wiki/Insertion_sort
 * https://en.wikipedia.org/wiki/Bubble_sort
 * for information about the three types of sorts.
 */
public class ThreeSorts {

	/**
	 * Uses selection sort to sort an array of integers from least to greatest.
	 * Sorts original array and returns the newly sorted array.
	 * 
	 * @param a
	 *            the array to be sorted
	 * @return the newly sorted array, sorted in order from least to greatest
	 */
	public static int[] selectionSort(int[] a) {
		int minPos, min;

		for (int i = 0; i < a.length - 1; i++) {
			min = a[i];
			minPos = i;

			for (int j = i + 1; j < a.length; j++)
				if (a[j] < min) { // find the min of the remaining elements
					min = a[j];
					minPos = j;
				}
			swap(a, i, minPos); // put min at the front of remaining elements
		}

		return a;
	}

	/**
	 * Uses insertion sort to sort an array of integers from least to greatest.
	 * Sorts original array and returns the newly sorted array.
	 * 
	 * @param a
	 *            the array to be sorted
	 * @return the newly sorted array, sorted in order from least to greatest
	 */
	public static int[] insertionSort(int[] a) {

		for (int i = 0; i < a.length; i++) {
			int count = 0;
			while (a[i] >= a[count] && count < i) {
				// find index of first element before a[i] that is greater than
				// a[i]
				count++;
			}
			int temp = a[i];
			for (int j = i; j > count; j--)
				a[j] = a[j - 1]; // shift elements following specified insertion
									// point to the right
			a[count] = temp; // insert a[i] into insertion point

		}
		return a;
	}

	/**
	 * Uses bubble sort to sort an array of integers from least to greatest.
	 * Sorts original array and returns the newly sorted array.
	 * 
	 * @param a
	 *            the array to be sorted
	 * @return the newly sorted array, sorted in order from least to greatest
	 */
	public static int[] bubbleSort(int[] a) {
		boolean completed = false;
		while (!completed) {
			completed = true; // set true for now, stays true if no swaps made
			for (int i = 0; i < a.length - 1; i++) { // pass through
				// check pairs
				if (a[i + 1] < a[i]) {
					swap(a, i, i + 1);
					completed = false; // was not completed yet
				}
			}
		}
		return a;
	}

	/**
	 * Takes an array of integers and swaps the elements at the specified
	 * inputs.
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

	/**
	 * Used for convenience when testing cases. Prints the elements of an array
	 * of integers in the format below:
	 * 
	 * [ A1, A2, A3, ... , An ]
	 * 
	 * Where A1 is the first element of the array, A2 is the second, etc.
	 * 
	 * @param a
	 *            the array to be printed
	 */
	public static void printArray(int[] a) {
		System.out.print("[ ");
		for (int i = 0; i < a.length - 1; i++)
			System.out.print(a[i] + ", ");
		System.out.print(a[a.length - 1] + " ]");
		System.out.println();
	}

	public static void main(String[] args) {
		// test cases

		int[] unsortedArray1 = { 3, 4, 6, 2, 5, 1, 5 };
		printArray(selectionSort(unsortedArray1));

		int[] unsortedArray2 = { 3, 4, 6, 2, 5, 1, 5 };
		printArray(insertionSort(unsortedArray2));

		int[] unsortedArray3 = { 3, 4, 6, 2, 5, 1, 5 };
		printArray(bubbleSort(unsortedArray3));

	}

}
