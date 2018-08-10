import java.util.ArrayList;

public class EncapMe {

	// Create three methods that encapsulate the three commented
	// actions of the code below
	// name the methods: intoArray, intoList, and evaluate
	// None of the methods should produce side effects
	public static void main(String[] args) {
		
		double xValue = .1;
		int cutoff = 2;
		String polytest1 = "1*x^0+1*x^1+3*x^2+0*x^3+1*x^4+4*x^5";
		String polytest2 = "1*x^0+1*x^1+3*x^2+0*x^3";
		String polytest3 = "3*x^0+2*x^1+1*x^2";

		// intoArray
		int[] a1 = intoArray(polytest1);
		int[] a2 = intoArray(polytest2);
		int[] a3 = intoArray(polytest3);

		//intoList
		ArrayList<Integer> list1 = intoList(a1);
		ArrayList<Integer> list2 = intoList(a2);
		ArrayList<Integer> list3 = intoList(a3);

		// evaluate test cases
		double total1 = evaluate(list1, xValue, cutoff);
		double total2 = evaluate(list2, xValue, cutoff);
		double total3 = evaluate(list3, xValue, cutoff);

		System.out.println(total1);
		System.out.println(total2);

		System.out.println(total3);


	}

	/**
	 * Takes a String representing a polynomial and stores its coefficients into
	 * an array, which is returned.
	 * 
	 * @param poly
	 *            a string represeting a polynomial. The polynomial must have
	 *            coefficients and exponents which are integers in the range
	 *            0-9. The terms must be sorted in increasing order of
	 *            exponents, and any terms with a coefficient of 0 must still be
	 *            represented. Finally there must be no whitespace.
	 * @return an array representing the coefficients of the polynomial
	 */
	public static int[] intoArray(String poly) {
		int[] coeffs = new int[(poly.length() + 1) / 6];
		for (int k = 0; k < coeffs.length; k++) {
			coeffs[k] = poly.charAt(k * 6);
		}
		return coeffs;
	}

	/**
	 * Takes an array of integers resulting from the previous method, intoArray,
	 * and transfers it to an ArrayList with the proper integer values. Returns
	 * this ArrayList.
	 * 
	 * @param a
	 *            an array of integers resulting from applying intoArray, thus
	 *            containing integers that are the results of automatically
	 *            casting chars to ints
	 * @return an ArrayList of integers representing the input array with its
	 *         values changed to the correct integer values
	 */
	public static ArrayList<Integer> intoList(int[] a) {
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for (int k : a) {
			if (k == '0')
				ret.add(0);
			if (k == '1')
				ret.add(1);
			if (k == '2')
				ret.add(2);
			if (k == '3')
				ret.add(3);
			if (k == '4')
				ret.add(4);
			if (k == '5')
				ret.add(5);
			if (k == '6')
				ret.add(6);
			if (k == '7')
				ret.add(7);
			if (k == '8')
				ret.add(8);
			if (k == '9')
				ret.add(9);

		}
		return ret;
	}

	/**
	 * Evaluates a polynomial, whose coefficients are represented as an
	 * ArrayList as defined by the procedure above, for terms whose exponents
	 * are less than or equal to an input cutoff.
	 * 
	 * @param coeffs
	 *            an ArrayList of integers representing the coefficients of the
	 *            polynomial, which should have been constructed through the
	 *            intoList method above
	 * @param xValue
	 *            a double representing the specified value of x that will be
	 *            substituted into the polynomial
	 * @param cutoff
	 *            an integer representing the cutoff as specified above
	 * @return a double representing the result of evaluating the polynomial for
	 *         the specified value of x with the specified cutoff
	 */
	public static double evaluate(ArrayList<Integer> coeffs, double xValue, int cutoff) {
		double total = 0;
	
		for (int k = 0; k <= Math.min(cutoff, coeffs.size()); k++) {
			total += coeffs.get(k) * Math.pow(xValue, k);
		}
		return total;

	}

}