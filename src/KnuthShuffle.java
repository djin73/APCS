
// https://en.wikipedia.org/wiki/Fisher–Yates_shuffle
import java.util.Random;

public class KnuthShuffle {
	private static void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	public static void shuffle(int[] a) {
		Random rand = new Random();
		/*-- To shuffle an array a of n elements (indices 0..n-1):
		for i from n−1 downto 1 do
		j ← random integer such that 0 ≤ j ≤ i
		exchange a[j] and a[i]*/
		for (int i = a.length - 1; i >= 1; i--) {
			int j = rand.nextInt(i + 1);
			swap(a, j, i);
		}
	}
	public static void shuffle2(int[] a) {
		Random rand = new Random();
		int temp;

		for (int i = a.length - 1; i >= 1; i--) {
			int j = rand.nextInt(i + 1);
			temp = a[i]; //swap a[j] and a[i]
			a[i] = a[j];
			a[j] = temp;
		}
	}

	public static void main(String[] args) {
		int[] a = { 1, 2, 3, 4, 5 };
		shuffle2(a);
		for (int i : a)
			System.out.println(i + " ");
	}
}
