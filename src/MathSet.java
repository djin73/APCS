/*
 * The following code implements the MathSet class, a special type of Set which contains only integers.
 * It contains union() and intersection() methods which correspond to the union of two MathSets or the
 * intersection of two MathSets.
 */
import java.util.*;

public class MathSet extends HashSet<Integer> implements Set<Integer> {

	/**
	 * Constructor
	 */
	public MathSet() {
		super();
	}

	/**
	 * Returns the union of this set with a specified MathSet.
	 * 
	 * @param s2
	 *            the specified MathSet to be compared to
	 * @return a MathSet representing the union of the two sets
	 */
	public MathSet union(MathSet s2) {
		MathSet ret = new MathSet();
		for (Integer i : this)
			ret.add(i);
		for (Integer i : s2)
			ret.add(i);
		return ret;
	}

	/**
	 * Returns the intersection of this set with a specified MathSet.
	 * 
	 * @param s2
	 *            the specified MathSet to be compared to
	 * @return a MathSet representing the intersection of the two sets
	 */
	public MathSet intersection(MathSet s2) {
		MathSet ret = new MathSet();
		for (Integer i : this)
			if (s2.contains(i))
				ret.add(i);

		return ret;
	}

	public static void main(String[] args) {
		//test code
		MathSet s = new MathSet();
		s.add(1);
		s.add(2);
		s.add(3);

		MathSet s2 = new MathSet();
		s2.add(2);
		s2.add(3);
		s2.add(4);

		System.out.println(s.union(s2));
		System.out.println(s.intersection(s2));

	}

}