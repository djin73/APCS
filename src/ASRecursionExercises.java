/*
AS exercises 1.11 and 1.12

The following code implements solutions, in Java, for AS exercises 1.11 and 1.12.  The first exercise, 
which asks for two different solutions to find the terms of a recursively defined sequence f, is
implemented in the methods recursiveF() and iterativeF().  recursiveF() implements f with a purely
recursive process, while iterativeF() uses the helper method iterf() to find f(n) with an iterative
process (note that it is still syntactically a recursive procedure, as it still calls itself).

The second exercise asks for a way to find a specified element of Pascal's Triangle.  This is implemented
in the following code with the Pascal() method which takes two nonnegative integers as its arguments, one
representing the row of the desired element, and one representing the column.  This is assuming that the 
first row is defined as row 0, and that the leftmost column of each row is defined as column 0.  The method
uses the recursive property that each element (besides the first/last element of each row) is equal to the 
sum of the two elements directly above it.

*/
public class ASRecursionExercises {

	public static void main(String[] args) {
		// test code
		System.out.println(Pascal(6, 2));
		System.out.println(recursiveF(5));
		System.out.println(iterativeF(5));
	}

	/**
	 * A recursive process to find f(n), where f is defined as
	 * 
	 * f(n) = n for n = 0, 1, 2
	 * 
	 * f(n) = f(n-1) + 2f(n-2) + 3f(n-3) for n >= 3
	 * 
	 * @param n
	 *            a nonnegative integer
	 * @return f(n)
	 */
	public static int recursiveF(int n) {
		if (n < 3)
			return n;
		return recursiveF(n - 1) + 2 * recursiveF(n - 2) + 3 * recursiveF(n - 3);
	}

	/**
	 * Uses the helper method f-iter to find the nth term of f using an iterative process.
	 * 
	 * @param n
	 *            a nonnegative integer
	 * @return f(n)
	 */
	public static int iterativeF(int n) {
		if (n < 3)
			return n;
		return iterf(2, 1, 0, n - 2);
	}

	/**
	 * Helper method, reduces counter for the next call, "shifts" over a and b
	 * for the next call, and calculates the next first term using the recursive
	 * definition of f. (Iterative process)
	 * 
	 * @param a
	 *            a term of the sequence, e.g. f(t)
	 * @param b
	 *            term preceding a e.g. f(t-1)
	 * @param c
	 *            term preceding b e.g. f(t-2)
	 * @param counter
	 *            a nonnegative integer, the number of iterations to be
	 *            performed
	 * @return
	 */
	private static int iterf(int a, int b, int c, int counter) {
		if (counter == 0)
			return a;
		return iterf(a + 2 * b + 3 * c, a, b, counter - 1);
	}

	/**
	 * Returns the number in Pascal's triangle at the specified row and column,
	 * where we define the first row of Pascal's triangle as row 0, and define
	 * the leftmost number of any row to be in column 0. Note that Pascal(row,
	 * col) should equal C(row, col) where C is the choose function.
	 * 
	 * @param row
	 *            the row of the desired number in Pascal's triangle
	 * @param col
	 *            the column of the desired number in Pascal's triangle
	 * @return the desired number in Pascal's triangle
	 */
	public static int Pascal(int row, int col) {
		if (col == 0 || col == row)
			return 1;
		return Pascal(row - 1, col - 1) + Pascal(row - 1, col); //elements above the current element
	}

}
