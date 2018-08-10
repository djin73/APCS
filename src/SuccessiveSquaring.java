/* The following code contains a method, pow(), that takes a double "a" and an integer "b".  It then returns the 
 * value of a^b.  It does this by recursively calling itself, based on the fact that:
 * 
 * a^b = (a * a)^(b/2) if b is even
 * a^b = a * (a*a)^((b-1)/2) if b is odd
 * 
 * Additionally, if the exponent is negative, it simply runs the algorithm on the reciprocal of the number
 * being exponentiated, with the exponent being positive. Through recursion, the value of b in successive 
 * calls is approximately halved every time.  Thus, this algorithm is much faster than just multiplying "a" 
 * by itself "b" times, as it runs in logarithmic time (log base 2) rather than linear time.
 * 
 */
public class SuccessiveSquaring {

	/**
	 * Returns a^b through successive squaring.
	 * 
	 * @param a
	 *            the number being exponentiated, a
	 * @param b
	 *            the exponent b
	 * @return a to the power of b
	 */
	public static double pow(double a, int b) {
		if (b < 0)
			return pow(1 / a, -b);
		if (b == 0)
			return 1;
		if (b == 1)
			return a;

		if (b % 2 == 0)
			return pow(a * a, b / 2);
		else
			return a * pow(a * a, (b - 1) / 2);

	}

	public static void main(String[] args) {
		System.out.println(pow(2, 10)); // prints 2^10
		System.out.println(pow(2, -1)); // prints 2^(-1)
	}

}
