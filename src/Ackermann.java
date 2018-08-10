
public class Ackermann {

	public static void main(String[] args) {
		System.out.println(A(4, 1));
	}

	public static int A(int m, int n) { // Ackermann function
		if (m == 0)
			return n + 1;

		if (m > 0) {
			if (n == 0)
				return A(m - 1, 1);
			else if (n > 0)
				return A(m - 1, A(m, n - 1));
			else {
				System.out.println("Cannot have negative arguments.");
				return 0;
			}

		} else {
			return 0;
		}
	}

}
