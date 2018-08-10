/*
 * The following program implements 11 methods relating to and dealing with truth columns, 
 * which are individual columns in truth tables.  (They can be thought of as columns of 
 * values, each value equal to either "True" or "False").
 * 
 * The methods can be divided into three classes:
 * 
 * Operators (and(), or(), negate(), imply(), biconditional()) take two arrays and return a new
 * array based on the specified operator in the name of the method.  For example, the and() method 
 * would take two columns and return a new column produced by using the "and" operator on each 
 * of the corresponding entries in the first two columns.  The exception to this is the negate() method,
 * which simply takes a single truth column and negates all of its entries.
 * 
 * Classifiers (valid(), unsatisfiable(), contingent()) take a single array and check if they
 * satisfy certain conditions.  These conditions are specific to each method and are specified 
 * in the javadoc comments for each method.  For example, valid() checks if a truth column is
 * valid - that is, if it contains all true values.
 * 
 * Comparators (equivalent(), entails(), consistent()) take two arrays and test certain relationships
 * between them; these are specified in more detail by the javadoc comments for each method.  For
 * example, equivalent() tests if two truth columns are equivalent (as one might expect).
 * 
 * 
 * Additionally present in the code are test cases to test each method.
*/
public class TruthColumn {

	/**
	 * Represents the "and" operator.
	 * 
	 * @param a
	 *            an array of booleans representing a truth column
	 * @param b
	 *            an array of booleans representing a truth column
	 * @return an array of booleans representing the truth column obtained from
	 *         applying the "and" operator to the entries in the input columns
	 */
	public static boolean[] and(boolean[] a, boolean[] b) {
		if (a.length != b.length) {
			System.out.println("Error: Columns must be same size.");
			return null;
		} else {
			boolean[] retval = new boolean[a.length];
			for (int i = 0; i < a.length; i++)
				retval[i] = a[i] && b[i];
			return retval;
		}
	}

	/**
	 * Represents the "or" operator.
	 * 
	 * @param a
	 *            an array of booleans representing a truth column
	 * @param b
	 *            an array of booleans representing a truth column
	 * @return an array of booleans representing the truth column obtained from
	 *         applying the "or" operator to the entries in the input columns
	 */
	public static boolean[] or(boolean[] a, boolean[] b) {
		if (a.length != b.length) {
			System.out.println("Error: Columns must be same size.");
			return null;
		} else {
			boolean[] retval = new boolean[a.length];
			for (int i = 0; i < a.length; i++)
				retval[i] = a[i] || b[i];
			return retval;
		}
	}

	/**
	 * Represents the negation operator.
	 * 
	 * @param a
	 *            an array of booleans representing a truth column
	 * @return an array of booleans representing the truth column obtained from
	 *         negating the entries in the input column
	 */
	public static boolean[] negate(boolean[] a) {
		boolean[] retval = new boolean[a.length];
		for (int i = 0; i < a.length; i++)
			retval[i] = !a[i];
		return retval;
	}

	/**
	 * Represents the implication operator.
	 * 
	 * @param a
	 *            an array of booleans representing a truth column
	 * @param b
	 *            an array of booleans representing a truth column
	 * @return an array of booleans representing the truth column obtained from
	 *         applying the implication operator to the entries in the input
	 *         columns
	 */
	public static boolean[] imply(boolean[] a, boolean[] b) {
		if (a.length != b.length) {
			System.out.println("Error: Columns must be same size.");
			return null;
		} else {
			boolean[] retval = new boolean[a.length];
			for (int i = 0; i < a.length; i++)
				retval[i] = !a[i] || b[i];
			return retval;
		}
	}

	/**
	 * Represents the biconditional operator.
	 * 
	 * @param a
	 *            an array of booleans representing a truth column
	 * @param b
	 *            an array of booleans representing a truth column
	 * @return an array of booleans representing the truth column obtained from
	 *         applying the biconditional operator to the entries in the input
	 *         columns
	 */
	public static boolean[] biconditional(boolean[] a, boolean[] b) {
		return and(imply(a, b), imply(b, a));
	}

	/**
	 * Determines whether or not a truth column is valid (true for all truth
	 * assignments).
	 * 
	 * @param a
	 *            an array of booleans representing a truth column
	 * @return true if the column is valid, false otherwise
	 */
	public static boolean valid(boolean[] a) {
		for (boolean b : a)
			if (!b)
				return false;
		return true;
	}

	/**
	 * Determines whether or not a truth column is unsatisfiable.
	 * 
	 * @param a
	 *            an array of booleans representing a truth column
	 * @return true if the column is unsatisfiable, false otherwise
	 */
	public static boolean unsatisfiable(boolean[] a) {
		for (boolean b : a)
			if (b)
				return false;
		return true;
	}

	/**
	 * Determines whether or not a truth column is contingent (neither valid nor
	 * unsatisfiable).
	 * 
	 * @param a
	 *            an array of booleans representing a truth column
	 * @return true if the column is unsatisfiable, false otherwise
	 */
	public static boolean contingent(boolean[] a) {
		return !valid(a) && !unsatisfiable(a);
	}

	/**
	 * Determines whether or not two truth columns are equivalent.
	 * 
	 * @param a
	 *            an array of booleans representing a truth column
	 * @param b
	 *            an array of booleans representing a truth column
	 * @return true if the columns are equivalent, false otherwise
	 */
	public static boolean equivalent(boolean[] a, boolean[] b) {
		if (a.length != b.length) {
			System.out.println("Error: Columns must be same size.");
			return false;
		}
		for (int i = 0; i < a.length; i++)
			if (a[i] != b[i])
				return false;

		return true;
	}

	/**
	 * Determines whether or not a truth column entails another.
	 * 
	 * @param a
	 *            an array of booleans representing the first truth column
	 * @param b
	 *            an array of booleans representing the second truth column
	 * @return true if the first column entails the second, false if otherwise
	 */
	public static boolean entails(boolean[] a, boolean[] b) {
		if (a.length != b.length) {
			System.out.println("Error: Columns must be same size.");
			return false;
		}
		for (int i = 0; i < a.length; i++)
			if (a[i] && !b[i])
				return false;
		return true;
	}

	/**
	 * Determines whether or not two truth columns are consistent (if there
	 * exists a truth assignment such that both columns are true).
	 * 
	 * @param a
	 *            an array of booleans representing a truth column
	 * @param b
	 *            an array of booleans representing a truth column
	 * @return true if the columns are consistent, false otherwise
	 */
	public static boolean consistent(boolean[] a, boolean[] b) {
		if (a.length != b.length) {
			System.out.println("Error: Columns must be same size.");
			return false;
		}
		for (int i = 0; i < a.length; i++)
			if (a[i] && b[i])
				return true;
		return false;
	}

	/**
	 * Prints a nonempty array of booleans in one line, surrounding the entries
	 * with brackets.
	 * 
	 * @param b
	 *            the array to be printed
	 */
	private static void printArray(boolean[] b) {
		String s = "[" + b[0];
		for (int i = 1; i < b.length; i++)
			s += ", " + b[i];
		s += "]";
		System.out.println(s);
	}

	// test cases
	public static void main(String[] args) {

		boolean[] a = { true, true, false, false }; // first truth column
		boolean[] b = { true, false, true, false }; // second truth column

		boolean[] test = and(a, b);
		printArray(test); // expected output: prints [true, false, false, false]

		test = or(a, b);
		printArray(test); // expected output: prints [true, true, true, false]

		test = negate(a);
		printArray(test); // expected output: prints [false, false, true, true]

		test = imply(a, b);
		printArray(test); // expected output: prints [true, false, true, true]

		test = biconditional(a, b);
		printArray(test); // expected output: prints [true, false, false, true]

		System.out.println(valid(a)); // expected output: prints false

		System.out.println(unsatisfiable(a)); // expected output: prints false

		System.out.println(contingent(a)); // expected output: prints true

		System.out.println(equivalent(a, b)); // expected output: prints false

		System.out.println(entails(a, b)); // expected output: prints false

		System.out.println(consistent(a, b)); // expected output: prints true

	}

}
