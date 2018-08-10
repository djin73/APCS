import java.util.ArrayList;

public class Tester {

	public static void main(String[] args) {
		// test cases

	}

	/**
	 * Applies the second step of INDO, which eliminates negatives before
	 * conjunctions and disjunctions, as well as eliminating double negatives.
	 * 
	 * @param sentence
	 *            a sentence in propositional logic resulting from applying
	 *            steps I of the INDO procedure to a propositional expression
	 *            (and thus should not have any implications)
	 * @return the sentence after applying the second step of the INDO procedure
	 */
	public static String negations(String sentence) {
		sentence = sentence.replaceAll(" ", ""); // remove spaces

		int index = 0;
		while (index < sentence.length() - 1) {
			if (sentence.substring(index, index + 1).equals("~")) {
				if (sentence.substring(index + 1, index + 2).equals("~"))
					sentence = sentence.substring(0, index) + sentence.substring(index + 2);

				else if (sentence.substring(index + 1, index + 2).equals("(")) {
					int endIndex = rightParenIndex(sentence, index + 2);
					// find index of the right parentheses of the expression to
					// be demorgan'ed
					sentence = sentence.substring(0, index) + deMorgan(sentence.substring(index + 1, endIndex + 1))
							+ sentence.substring(endIndex + 1);
				} else
					index++;
			} else
				index++;
		}

		return sentence;

	}

	/**
	 * Applies DeMorgan's law, which states that
	 * 
	 * ~(a&b) = ~a|~b and ~(a|b) = ~a&~b,
	 * 
	 * to a propositional sentence. The sentence MUST HAVE HAD the "~" at the
	 * front removed; that is, we presume that the sentence is an expression
	 * such as one inside one of the parentheses in the law above.
	 * 
	 * e.g. a&b -> ~a|~b; inputting ~(a&b) is not permitted.
	 * 
	 * @param sentence
	 *            a sentence in propositional logic with no implications (it
	 *            must be a conjunction of disjunction)
	 * @return the result of applying DeMorgan's law to the input
	 */
	private static String deMorgan(String sentence) {
		sentence = removeOuterParen(sentence);
		int type = typeOfExpression(sentence);
		String ret = "";

		if (type == 0) { // disjunction
			ArrayList<String> disjuncts = splitByRegexOutsideParen(sentence, "|");
			ret = "~(" + disjuncts.get(0) + ")";
			for (int i = 1; i < disjuncts.size(); i++)
				ret += "&~(" + disjuncts.get(i) + ")";
		} else if (type == 1) {// conjunction
			ArrayList<String> conjuncts = splitByRegexOutsideParen(sentence, "&");
			ret = "~(" + conjuncts.get(0) + ")";
			for (int i = 1; i < conjuncts.size(); i++)
				ret += "|~(" + conjuncts.get(i) + ")";
		} else { // literal
			return "~" + sentence;
		}
		return "(" + ret + ")";
	}

	/**
	 * Given a String, an integer representing an index in that String, find the
	 * index of the right parentheses of the INNERMOST set of parentheses which
	 * contains the character at that index, or return the length of the String
	 * if the character is not contained within any parentheses.
	 * 
	 * e.g. (s = "((aa)aa)", index = 5) --> 7 since the index of 5 corresponds
	 * to the 3rd a, which is held only inside the outer set of parentheses.
	 * 
	 * @param s
	 *            a String
	 * @param index
	 *            an integer representing an index in that String
	 * @return the index of the right parentheses of the innermost set of
	 *         parentheses which contains the character at that index
	 */
	private static int rightParenIndex(String s, int index) {
		int count = 0;
		while (index < s.length()) {
			String curChar = s.substring(index, index + 1);
			if (curChar.equals("("))
				count--;
			else if (curChar.equals(")"))
				count++;

			if (count == 1)
				return index;
			index++;
		}
		return index;
	}

	/**
	 * Takes a logical sentence, removes its outer parentheses, and splits the
	 * sentence using the specified regex, similar to the String split() method.
	 * HOWEVER, only considers the regex when it is not within any parentheses
	 * of any sort. Returns an ArrayList representing results.
	 * 
	 * e.g. split("A&B&(C&D)", "&") -> [A, B, (C&D)]
	 * 
	 * @param sentence
	 *            a syntactically valid logical expression
	 * @param regex
	 *            the specified regex to split by
	 * @return an ArrayList representing the results of the split
	 */
	private static ArrayList<String> splitByRegexOutsideParen(String sentence, String regex) {
		sentence = removeOuterParen(sentence);
		ArrayList<String> ret = new ArrayList<String>();
		int index = 0;
		int diff = 0; // (left parentheses so far) - (right parentheses)
		while (index < sentence.length()) {
			if (sentence.substring(index, index + 1).equals(regex) && diff == 0) {
				ret.add(sentence.substring(0, index));
				sentence = sentence.substring(index + 1);
				index = 0;
			} else {
				if (sentence.substring(index, index + 1).equals("("))
					diff++;
				else if (sentence.substring(index, index + 1).equals(")"))
					diff--;
				index++;
			}
		}
		ret.add(sentence);
		return ret;
	}

	/**
	 * Removes all the outer parentheses of a logical expression.
	 * 
	 * @param s
	 *            a syntactically valid logical expression
	 * @return the modified logical expression with its outer parentheses
	 *         removed
	 */
	private static String removeOuterParen(String s) {

		int diff = 0; // (left parentheses so far) - (right parentheses)
		if (s.substring(0, 1).equals("(")) {
			diff = 1;
			for (int i = 1; i < s.length(); i++) {
				if (s.substring(i, i + 1).equals("("))
					diff++;
				else if (s.substring(i, i + 1).equals(")"))
					diff--;

				if (diff == 0)
					if (i == s.length() - 1)
						return removeOuterParen(s.substring(1, s.length() - 1));
					else
						return s;
			}

		}
		return s;
	}

	/**
	 * Returns an integer representing whether the input sentence, which must
	 * not contain an implication, is a disjunction, conjunction, or literal.
	 * (Note that because of operator precedence, a&b|c = (a&b)|c which is a
	 * disjunction).
	 * 
	 * @param sentence
	 *            a sentence in propositional logic with no implications (it
	 *            must be a conjunction of disjunction)
	 * @return 0 if the sentence is a disjunction, 1 if it is a conjunction, 2
	 *         if it is a literal
	 */
	private static int typeOfExpression(String sentence) {
		sentence = removeOuterParen(sentence);
		int index = 0;
		int diff = 0; // (left paren so far) - (right paren so far)
		boolean hasAnd = false;
		while (index < sentence.length()) {
			if (sentence.substring(index, index + 1).equals("("))
				diff++;
			else if (sentence.substring(index, index + 1).equals(")"))
				diff--;
			else if (diff == 0 && sentence.substring(index, index + 1).equals("|"))
				return 0; // disjunction
			else if (diff == 0 && sentence.substring(index, index + 1).equals("&"))
				hasAnd = true;

			index++;
		}
		if (hasAnd) {
			return 1; // conjunction
		} else
			return 2; // literal
	}

}
