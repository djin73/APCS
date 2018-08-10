import java.util.*;

/*
The following code contains:

 - A constructor that takes a String input that represents a propositional logic's proposition constants, 
and a Set of Strings that each represent a sentence in propositional logic.

 - A method, Evaluate, that evaluates each sentence for each truth assignment and returns a data structure 
of your choice that serves to record the results of the evaluations.

 - A method, Entails, that takes a String representing a logical sentence and returns a Map 
that maps each logical expression in the collection to a boolean that is true if the input 
entails the expression in the collection.

As specified by the requirements.  It also has three private helper methods: innerParen() returning
the string within the innermost parentheses of the original string; addZeroes, which adds zeroes to
the beginning of binary numbers to make them a specified length (used for representing truth assignments);
and evaluateForTruthAssignment (a static method which takes a sentence, an integer representing a truth 
assignment, and an array of the propositional constants in the sentence; it evaluates the sentence
for the given truth assignment and returns the value true or false).

While innerParen() and addZeroes() are relatively short and simple, the bulk of the code lies in the
evaluateForTruthAssignment() method.  This method takes the integer parameter, converts it to binary
with the proper number of digits, and uses it as a truth assignment.  It substitutes the propositional
constants in the sentence with T's or F's as necessary.  Then, it evaluates the entire sentence by 
slowly eliminating parentheses: it finds the expression in the current innermost parentheses, evaluates
it with the proper order of operations and logic rules, and substitutes the whole parenthetical expression
with the evaluated value T or F.  It repeats this process until there are no more parentheses, in which case
the sentence can simply be evaluated using the correct order of operations.

Finally, the main() method contains code demonstrating the class at work.
 */
public class LogicalExpressionCollection {

	public String[] constants; // array representing proposition constants
								// e.g. [a,b,c]
	public HashSet<String> sentences; // set of logical sentences to be

	/**
	 * A constructor that takes a String input that represents a propositional
	 * logic's proposition constants, and a Set of Strings that each represent a
	 * sentence in propositional logic.
	 * 
	 * @param pc
	 *            the propositional constants with no spaces (e.g. "abc")
	 * @param sent
	 *            the set of propositional logic sentences
	 */
	public LogicalExpressionCollection(String pc, HashSet<String> sent) {
		constants = new String[pc.length()];
		for (int i = 0; i < pc.length(); i++)
			constants[i] = pc.substring(i, i + 1);
		sentences = sent;
	}

	/**
	 * Returns the expression inside the innermost parentheses of a String.
	 * 
	 * @param s
	 *            the parameter String
	 * @return the String in the first innermost parentheses, NOT including the
	 *         parentheses
	 */
	private static String innerParen(String s) {
		int beginIndex = -1;
		int endIndex = s.length();
		for (int i = 0; i < s.length(); i++) {
			if (s.substring(i, i + 1).equals("("))
				beginIndex = i;
			if (s.substring(i, i + 1).equals(")")) {
				endIndex = i;
				break;
			}
		}
		return s.substring(beginIndex + 1, endIndex);
	}

	/**
	 * Adds zeroes to the left of a binary number to make it a specified length
	 * (e.g. 10 -> 0010)
	 * 
	 * @param b
	 *            A string representing a number in binary
	 * @param length
	 *            The desired length of the number
	 * @return
	 */
	private static String addZeroes(String b, int length) {
		String binary = b;
		while (binary.length() < length)
			binary = "0" + binary;
		return binary;
	}

	/**
	 * 
	 * @param assignment
	 *            A number from 0 to 2^(# of constants) - 1, representing a
	 *            current truth assignment. For example, if the number were 5,
	 *            and our constants were a, b, and c, we would convert 5 to
	 *            binary, in which it becomes 101. This represents a being true,
	 *            b being false, and c being true.
	 * @param sent
	 *            The logical sentence being evaluated.
	 * @param pc
	 *            The array of propositional constants.
	 * @return
	 */
	private static boolean evaluateForTruthAssignment(int assignment, String sent, String[] pc) {

		String binary = addZeroes(Integer.toBinaryString(assignment), pc.length);

		HashMap<Integer, Boolean> assignMap = new HashMap(); // truth assignments
		for (int i = 0; i < binary.length(); i++) {
			if (binary.substring(i, i + 1).equals("0"))
				assignMap.put(i, false);
			else
				assignMap.put(i, true);
		}

		String sentence = sent;
		for (int i = 0; i < sentence.length(); i++) {
			String cur = sentence.substring(i, i + 1); // current character of
														// sentence
			for (int j = 0; j < pc.length; j++) {
				if (pc[j].equals(cur)) { // found a propositional constant
					if (assignMap.get(j)) // true
						sentence = sentence.substring(0, i) + "T" + sentence.substring(i + 1);
					else // false
						sentence = sentence.substring(0, i) + "F" + sentence.substring(i + 1);
				}
			}
		}

		// eliminate parentheses step by step
		while (sentence.length() > 1) {
			// extract innermost parenthesized substring
			String inner = innerParen(sentence);
			int beginIndex = sentence.indexOf("(" + inner + ")");
			int endIndex = beginIndex + inner.length() + 2;
			if (beginIndex == -1) { // if there are no parentheses left
				beginIndex = 0;
				endIndex = sentence.length();
			}

			// evaluate the expression within the parentheses
			// process ~
			for (int i = 0; i < inner.length(); i++) {
				String curChar = inner.substring(i, i + 1);
				if (curChar.equals("~")) {
					if (inner.substring(i + 1, i + 2).equals("T")) // ~T
						inner = inner.substring(0, i) + "F" + inner.substring(i + 2);
					else // ~F
						inner = inner.substring(0, i) + "T" + inner.substring(i + 2);
				}
			}

			// process &
			for (int i = 0; i < inner.length(); i++) {
				String curChar = inner.substring(i, i + 1);
				if (curChar.equals("&")) {
					if (inner.substring(i - 1, i).equals("F") || inner.substring(i + 1, i + 2).equals("F"))
						inner = inner.substring(0, i - 1) + "F" + inner.substring(i + 2);
					else // T&T
						inner = inner.substring(0, i - 1) + "T" + inner.substring(i + 2);
				}
			}

			// process |
			for (int i = 0; i < inner.length(); i++) {
				String curChar = inner.substring(i, i + 1);
				if (curChar.equals("|")) {
					if (inner.substring(i - 1, i).equals("F") && inner.substring(i + 1, i + 2).equals("F"))
						inner = inner.substring(0, i - 1) + "F" + inner.substring(i + 2);
					else // T|F, F|T, T|T
						inner = inner.substring(0, i - 1) + "T" + inner.substring(i + 2);
				}
			}

			// process =>
			for (int i = 0; i < inner.length() - 1; i++) {
				String curSymbol = inner.substring(i, i + 2);
				if (curSymbol.equals("=>")) {

					if (inner.substring(i - 1, i).equals("T") && inner.substring(i + 2, i + 3).equals("F"))
						inner = inner.substring(0, i - 1) + "F" + inner.substring(i + 3); // T=>F
					else // all others
						inner = inner.substring(0, i - 1) + "T" + inner.substring(i + 3);
				}
			}

			// modify original sentence
			sentence = sentence.substring(0, beginIndex) + inner + sentence.substring(endIndex);
		}

		if (sentence.equals("F"))
			return false;
		return true;
	}

	// evaluates each sentence for each truth assignment and returns a data
	// structure
	// of your choice that serves to record the results of the evaluations.
	/**
	 * Evaluates each sentence for each possible truth assignment.
	 * 
	 * @return A HashMap<String, Boolean[]> which maps each sentence,
	 *         represented as Strings, to an array of Booleans where the entry
	 *         at index 0 corresponds to the value of the sentence evaluated for
	 *         the truth assignment 000 (in the case of there being 3
	 *         propositional constants; there could be more or less), and so on
	 *         (see evaluateForTruthAssignment() for more details).
	 */
	public HashMap<String, Boolean[]> evaluate() {
		HashMap<String, Boolean[]> hm = new HashMap();

		for (String s : sentences) {
			Boolean[] evaluations = new Boolean[(int) Math.pow(2, constants.length)];
			for (int i = 0; i < evaluations.length; i++)
				evaluations[i] = evaluateForTruthAssignment(i, s, constants);
			hm.put(s, evaluations);
		}

		return hm;
	}

	/**
	 * 
	 * @param sent
	 *            a logical sentence
	 * @return HashMap mapping each logical expression in the collection to true
	 *         if parameter sent entails the expression, false otherwise
	 */
	public HashMap<String, Boolean> entails(String sent) {
		HashMap<String, Boolean> hm = new HashMap();
		for (String s : sentences)
			hm.put(s, true);

		for (int i = 0; i < (int) Math.pow(2, constants.length); i++) { // iterate
																		// through
																		// truth
			// assignments
			if (evaluateForTruthAssignment(i, sent, constants))
				for (String s : sentences) {
					if (!evaluateForTruthAssignment(i, s, constants))
						hm.put(s, false);
				}

		}

		return hm;
	}

	public static void main(String[] args) {

		HashSet<String> set = new HashSet(); // set of sentences
		set.add("a&b=>c");
		set.add("a&~b&c");
		LogicalExpressionCollection collection = new LogicalExpressionCollection("abc", set);

		// since 5 base 10 = 101 base 2, this corresponds to a = true, b = false
		// c = true, which makes a&b=>c true, so this should print true
		System.out.println(collection.evaluate().get("a&b=>c")[5]);

		// since ~B does not entail a&~b&c, this should print false
		System.out.println(collection.entails("~b").get("a&~b&c"));

	}

}
