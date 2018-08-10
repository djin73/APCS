/*Exercise 15.4
 * 
 * The following code provides a solution for exercise 15.4.  The exercise requires one to transform
 * all class methods from the Rational class in Exercise 11.3 into object methods, which is done
 * here as shown.  Additionally, the main() method contains demonstrations of these methods, and 
 * examples of incorrect code trying to treat object methods as class methods or vice versa.
 * 
 * Thanks to Allen B. Downey's book Think AP Java.
 */
public class Rational {
	public int num, denom;

	public static void main(String[] args) {
		Rational r = new Rational(1, 2);
		// Rational.printRational(r); does not compile
		r.printRational(); // prints 1/2
		// Rational.negate(r); does not compile
		r.negate();
		r.printRational(); //prints -1/2

		Rational r2 = new Rational(1, 3);
		Rational r3 = r.add(r2); //-1/2 + 1/3 = -1/6
//		Rational r3 = add(r, r2);  does not compile
		r3.printRational(); // prints -1/6

		((new Rational(4, 6)).reduce()).printRational(); // prints 2/3

	}

	// 2. constructor
	public Rational() {
		num = 0;
		denom = 0;
	}

	// 6. constructor /w parameters
	public Rational(int n, int d) {
		num = n;
		denom = d;
	}

	// 3. prints this rational
	public void printRational() {
		System.out.println(num + "/" + denom);
	}

	// 7. method negate, reverses sign
	public void negate() {
		num *= -1;
	}

	// 8. swaps numerator and denominator
	public static void invert(Rational r) {
		int denom = r.denom;
		r.denom = r.num;
		r.num = denom;
	}

	// 9. converts this rational to a double
	public double toDouble() {
		return ((double) num) / ((double) denom);
	}

	// 10. returns a Rational equal to a reduced form of this Rational
	public Rational reduce() {
		int gcd = GCD(num, denom);
		return new Rational(num / gcd, denom / gcd);
	}

	// 11. adds this rational, returning the rational representing their sum in
	// lowest terms
	public Rational add(Rational r2) {
		int commonDenom = this.denom * r2.denom;
		int sum = this.num * r2.denom + r2.num * this.denom;
		return new Rational(sum, commonDenom).reduce();

	}

	// uses the Euclidean algorithm to find the greatest common divisors of two
	// integers, a and b
	private static int GCD(int a, int b) {
		a = Math.abs(a);
		b = Math.abs(b);
		if (a > b) {
			return GCD(a - b, b);
		} else if (a == b)
			return a;
		else {
			return GCD(a, b - a);
		}
	}

}
