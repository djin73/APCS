import java.util.*;

public class INDOImplementation {

	public static void main(String[] args) {
		// test cases showing I works
		System.out.println("Implications test: g&(r=>f) ---> " + implications("g&      (r=>f)"));
		System.out.println("Implications test: a=>b=>c ---> " + implications("a=>b=>c"));
		System.out.println("Implications test: p&q<=>r ---> " + implications("p&q<=>r"));
		System.out.println();

		// test cases showing N works
		System.out.println("Negations test: g&(~(r)|(f)) ---> " + negations("g&(~(r)|(f))"));
		System.out.println("Negations test: ~(a)|(~(b)|(c)) ---> " + negations("~(a)|(~(b)|(c))"));
		System.out.println("Negations test: (~(p&q)|(r))&((p&q)|~(r)) ---> " + negations("(~(p&q)|(r))&((p&q)|~(r))"));
		System.out.println();

		// test cases showing D works
		System.out.println("Distribution test: g&(~r|(f)) ---> " + distribution("g&(~r|(f))"));
		System.out.println("Distribution test: ~a|(~b|(c)) ---> " + distribution("~a|(~b|(c))"));
		System.out.println(
				"Distribution test: ((~p|~q)|(r))&((p&q)|~r) ---> " + distribution("((~p|~q)|(r))&((p&q)|~r)"));
		System.out.println();

		// test cases showing O works
		System.out.println("Operators test: g&~r|f ---> " + operators("g&~r|f"));
		System.out.println("Operators test: ~a|~b|c ---> " + operators("~a|~b|c"));
		System.out.println("Operators test: (~p|~q|r)&(~r|p)&(~r|q) ---> " + operators("(~p|~q|r)&(~r|p)&(~r|q)"));
		System.out.println();

		// test cases showing INDO works
		System.out.println("INDO test: g&(r=>f) ---> " + INDO("g&(r=>f)"));
		System.out.println("INDO test: a=>b=>c ---> " + INDO("a=>b=>c")); // from
																			// Grace
		System.out.println("INDO test: p&q<=>r ---> " + INDO("p&q<=>r"));
		System.out.println("INDO test: (a=>b)|a ---> " + INDO("(a=>b)|a"));// from
																			// Robbie
		
		System.out.println(INDO("((~p&~r)|~q)&(~p&r)"));

	}

	/**
	 * Applies the INDO procedure to a propositional logic sentence to convert
	 * it to clausal form.
	 * 
	 * Note: While original specifications required that the sentence have no
	 * parentheses, this method will still work even with parentheses.
	 * 
	 * @param sentence
	 *            a propositional logic sentence with the symbols ~, |, &, =>,
	 *            and <=>
	 * 
	 * @return the sentence converted to clausal form
	 */
	public static String INDO(String sentence) {
		sentence = sentence.replaceAll(" ", ""); //get rid of spaces
		
		sentence = implications(sentence);

		sentence = negations(sentence);

		sentence = distribution(sentence);

		sentence = operators(sentence);

		return sentence;
	}
	
//	public static String remove

	/**
	 * Given a String, an integer representing an index in that String, find the
	 * index of the left parentheses of the INNERMOST set of parentheses which
	 * contains the character at that index, or return 0 if the character is not
	 * contained within any parentheses.
	 * 
	 * e.g. (s = "((aa)aa)", index = 5) --> 0 since the index of 5 corresponds
	 * to the 3rd a, which is held only inside the outer set of parentheses.
	 * 
	 * @param s
	 *            a String
	 * @param index
	 *            an integer representing an index in that String
	 * @return the index of the left parentheses of the innermost set of
	 *         parentheses which contains the character at that index
	 */
	private static int leftParenIndex(String s, int index) {
		int count = 0;
		while (index >= 0) {
			String curChar = s.substring(index, index + 1);
			if (curChar.equals("("))
				count++;
			else if (curChar.equals(")"))
				count--;

			if (count == 1)
				return index;
			index--;
		}
		return index;
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
	 * Returns an integer representing whether the input sentence, which must
	 * not contain an implication, is a disjunction, conjunction, or literal.
	 * (Note that because of operator precedence, a&b|c = (a&b)|c which is a
	 * disjunction).
	 * 
	 * @param sentence
	 *            a sentence in propositional logic containing no implication
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

	/**
	 * The first step of the INDO procedure, which eliminates conditionals and
	 * biconditionals according to the INDO procedure.
	 * 
	 * @param sentence
	 *            a sentence in propositional logic
	 * @return the sentence after applying the first step of the INDO procedure.
	 */
	public static String implications(String sentence) {
		sentence = sentence.replaceAll(" ", ""); // get rid of spaces

		for (int i = 0; i < sentence.length(); i++) {
			if (sentence.substring(i, i + 1).equals("=")) {
				int leftIndex = leftParenIndex(sentence, i) + 1;
				int rightIndex = rightParenIndex(sentence, i) - 1;
				String phi, psi;

				if (sentence.substring(i - 1, i).equals("<")) { // biconditional
					phi = sentence.substring(leftIndex, i - 1);
					psi = sentence.substring(i + 2, rightIndex + 1);
					sentence = sentence.substring(0, leftIndex) + "(~(" + phi + ")|(" + psi + "))&((" + phi + ")|~("
							+ psi + "))" + sentence.substring(rightIndex + 1);
				} else { // one-way conditional
					phi = sentence.substring(leftIndex, i);
					psi = sentence.substring(i + 2, rightIndex + 1);
					sentence = sentence.substring(0, leftIndex) + "~(" + phi + ")|(" + psi + ")"
							+ sentence.substring(rightIndex + 1);

				}
			}

		}
		return sentence;
	}

	/**
	 * Applies the second step of INDO, which eliminates negatives before
	 * conjunctions and disjunctions, as well as eliminating double negatives.
	 * 
	 * @param sentence
	 *            a sentence in propositional logic resulting from applying
	 *            steps I of the INDO procedure to a propositional expression
	 * @return the sentence after applying the second step of the INDO procedure
	 */
	public static String negations(String sentence) {
		sentence = sentence.replaceAll(" ", ""); //remove spaces

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
	 *            a disjunction or conjunction in propositional logic
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
	 * Applies the third step of INDO, which repeatedly uses the distributive
	 * property to simplify the expression.
	 * 
	 * @param sentence
	 *            a sentence in propositional logic resulting from applying
	 *            steps I and N of the INDO procedure to a propositional
	 *            expression
	 * @return the sentence after applying the third step of the INDO procedure
	 */
	public static String distribution(String sentence) {
		sentence = sentence.replaceAll(" ", ""); //get rid of spaces
		
		int index = 1;
		while (index > 0 && index < sentence.length() - 1) {

			if (sentence.substring(index - 1, index + 1).equals(")|")
					|| sentence.substring(index, index + 2).equals("|(")) {

				// express as phi|psi
				// calculate left bound of phi and right bound of psi

				int phiLeftIndex = disjunctLeftBound(sentence, index);
				int psiRightIndex = disjunctRightBound(sentence, index);
				String phi = sentence.substring(phiLeftIndex, index);
				String psi = sentence.substring(index + 1, psiRightIndex);
				int phiType = typeOfExpression(phi);
				int psiType = typeOfExpression(psi);

				String insert = sentence.substring(phiLeftIndex, psiRightIndex);
				if (psiType == 1) { // psi is a conjunction
					ArrayList<String> conjuncts = splitByRegexOutsideParen(psi, "&");
					insert = "(" + phi + "|" + conjuncts.get(0) + ")";
					for (int i = 1; i < conjuncts.size(); i++)
						insert += "&(" + phi + "|" + conjuncts.get(i) + ")";

				} else if (phiType == 1) {// phi is a conjunction
					ArrayList<String> conjuncts = splitByRegexOutsideParen(phi, "&");
					insert = "(" + psi + "|" + conjuncts.get(0) + ")";
					for (int i = 1; i < conjuncts.size(); i++)
						insert += "&(" + psi + "|" + conjuncts.get(i) + ")";

				} else { // both not conjunctions
					insert = removeOuterParen(phi) + "|" + removeOuterParen(psi);

				}
				sentence = sentence.substring(0, phiLeftIndex) + insert + sentence.substring(psiRightIndex);
				index = phiLeftIndex;

			} else if (sentence.substring(index - 1, index + 1).equals(")&")
					|| sentence.substring(index, index + 2).equals("&(")) {

				int phiLeftIndex = conjunctLeftBound(sentence, index);
				int psiRightIndex = conjunctRightBound(sentence, index);
				String phi = sentence.substring(phiLeftIndex, index);
				String psi = sentence.substring(index + 1, psiRightIndex);
				int phiType = typeOfExpression(phi);
				int psiType = typeOfExpression(psi);

				if (!(phiType == 0 && psiType == 0)) { // not both disjunctions
					String insert = removeOuterParen(phi) + "&" + removeOuterParen(psi);
					sentence = sentence.substring(0, phiLeftIndex) + insert + sentence.substring(psiRightIndex);
					index = phiLeftIndex;
				} else
					index++;
			} else
				index++;
		}

		// final sweep: simplify things like (a|b)&((c|d|(x|y))&(e|f))
		index = 1;
		while (index > 0 && index < sentence.length() - 1) {
			if (sentence.substring(index - 1, index + 1).equals(")|")
					|| sentence.substring(index, index + 2).equals("|(")) {

				// express as phi|psi

				int phiLeftIndex = disjunctLeftBound(sentence, index);
				int psiRightIndex = disjunctRightBound(sentence, index);
				String phi = sentence.substring(phiLeftIndex, index);
				String psi = sentence.substring(index + 1, psiRightIndex);

				String insert = removeOuterParen(phi) + "|" + removeOuterParen(psi);

				sentence = sentence.substring(0, phiLeftIndex) + insert + sentence.substring(psiRightIndex);
				index = phiLeftIndex;

			} else if (sentence.substring(index - 1, index + 1).equals(")&")
					|| sentence.substring(index, index + 2).equals("&(")) {

				int phiLeftIndex = conjunctLeftBound(sentence, index);
				int psiRightIndex = conjunctRightBound(sentence, index);
				String phi = sentence.substring(phiLeftIndex, index);
				String psi = sentence.substring(index + 1, psiRightIndex);
				int phiType = typeOfExpression(phi);
				int psiType = typeOfExpression(psi);

				String modifiedPhi = removeOuterParen(phi);
				String modifiedPsi = removeOuterParen(psi);
				if (phiType == 0)
					modifiedPhi = "(" + modifiedPhi + ")";
				if (psiType == 0)
					modifiedPsi = "(" + modifiedPsi + ")";

				if (!(phiType == 0 && psiType == 0)) { // not both disjunctions
					String insert = modifiedPhi + "&" + modifiedPsi;
					sentence = sentence.substring(0, phiLeftIndex) + insert + sentence.substring(psiRightIndex);
					index = phiLeftIndex;
				} else
					index++;
			} else
				index++;
		}
		return sentence;
	}

	/**
	 * Returns the index of the left bound of the left element of the
	 * disjunction specified by the "|" symbol at the inputted index.
	 * 
	 * @param sentence
	 *            a sentence in propositional logic containing at least one
	 *            disjunction
	 * @param index
	 *            an integer, the index of the "|" symbol of the disjunction
	 * @return the index of the left bound of the left element of the
	 *         disjunction (part of the element)
	 */
	private static int disjunctLeftBound(String sentence, int index) {
		int phiLeftIndex = 0;
		int i = index - 1;
		int diff = 0; // (# of left parentheses) - (# of right)
		while (i >= 0) {
			if (sentence.substring(i, i + 1).equals(")"))
				diff--;
			else if (sentence.substring(i, i + 1).equals("(")) {
				diff++;
				if (diff == 1) { // hit left parentheses
					phiLeftIndex = i + 1;
					break;
				}
			} else if (diff == 0 && sentence.substring(i, i + 1).equals("|")) {
				phiLeftIndex = i + 1;
				break;
			}

			i--;
		}
		return phiLeftIndex;
	}

	/**
	 * Returns the index of the right bound of the right element of the
	 * disjunction specified by the "|" symbol at the inputted index.
	 * 
	 * @param sentence
	 *            a sentence in propositional logic containing at least one
	 *            disjunction
	 * @param index
	 *            an integer, the index of the "|" symbol of the disjunction
	 * @return the index of the right bound of the right element of the
	 *         disjunction (part of the element)
	 */
	private static int disjunctRightBound(String sentence, int index) {
		int phiRightIndex = sentence.length();
		int i = index + 1;
		int diff = 0; // (# of right parentheses) - (# of left)
		while (i <= sentence.length() - 1) {
			if (sentence.substring(i, i + 1).equals("("))
				diff--;
			else if (sentence.substring(i, i + 1).equals(")")) {
				diff++;
				if (diff == 1) { // hit right parentheses
					phiRightIndex = i;
					break;
				}
			} else if (diff == 0 && sentence.substring(i, i + 1).equals("|")) {
				phiRightIndex = i;
				break;
			}

			i++;
		}
		return phiRightIndex;
	}

	/**
	 * Returns the index of the left bound of the left element of the
	 * conjunction specified by the "&" symbol at the inputted index.
	 * 
	 * @param sentence
	 *            a sentence in propositional logic containing at least one
	 *            conjunction
	 * @param index
	 *            an integer, the index of the "&" symbol of the conjunction
	 * @return the index of the left bound of the left element of the
	 *         conjunction (not part of the element)
	 */
	private static int conjunctLeftBound(String sentence, int index) {
		int phiLeftIndex = 0;
		int i = index - 1;
		int diff = 0; // (# of left parentheses) - (# of right)
		while (i >= 0) {
			if (sentence.substring(i, i + 1).equals(")"))
				diff--;
			else if (sentence.substring(i, i + 1).equals("(")) {
				diff++;
				if (diff == 1) { // hit left parentheses
					phiLeftIndex = i + 1;
					break;
				}
			} else if (diff == 0
					&& (sentence.substring(i, i + 1).equals("|") || sentence.substring(i, i + 1).equals("&"))) {
				phiLeftIndex = i + 1;
				break;
			}

			i--;
		}
		return phiLeftIndex;
	}

	/**
	 * Returns the index of the right bound of the right element of the
	 * conjunction specified by the "&" symbol at the inputted index.
	 * 
	 * @param sentence
	 *            a sentence in propositional logic containing at least one
	 *            conjunction
	 * @param index
	 *            an integer, the index of the "&" symbol of the conjunction
	 * @return the index of the right bound of the right element of the
	 *         conjunction (not part of the element)
	 */
	private static int conjunctRightBound(String sentence, int index) {
		int phiRightIndex = sentence.length();
		int i = index + 1;
		int diff = 0; // (# of right parentheses) - (# of left)
		while (i <= sentence.length() - 1) {
			if (sentence.substring(i, i + 1).equals("("))
				diff--;
			else if (sentence.substring(i, i + 1).equals(")")) {
				diff++;
				if (diff == 1) { // hit right parentheses
					phiRightIndex = i;
					break;
				}
			} else if (diff == 0
					&& (sentence.substring(i, i + 1).equals("|") || sentence.substring(i, i + 1).equals("&"))) {
				phiRightIndex = i;
				break;
			}

			i++;
		}
		return phiRightIndex;
	}

	/**
	 * Applies the third step of INDO, which repeatedly uses the distributive
	 * property to simplify the expression.
	 * 
	 * @param sentence
	 *            a sentence in propositional logic resulting from applying
	 *            steps I, N, and D of the INDO procedure to a propositional
	 *            expression
	 * @return the sentence after applying the third step of the INDO procedure
	 */
	public static String operators(String sentence) {
		sentence = sentence.replaceAll(" ", ""); //get rid of spaces
		
		// if other steps went correctly, this should be a conjunction of
		// disjunctions
		String[] conjuncts = sentence.split("&");
		String[] clauses = new String[conjuncts.length];
		for (int i = 0; i < clauses.length; i++) {
			String curConjunct = conjuncts[i];

			// get rid of parentheses
			curConjunct = removeOuterParen(curConjunct);

			// replace "|" with commas
			for (int j = 0; j < curConjunct.length(); j++) {
				if (curConjunct.substring(j, j + 1).equals("|"))
					curConjunct = curConjunct.substring(0, j) + "," + curConjunct.substring(j + 1);
			}
			String clause = "{" + curConjunct + "}";
			clauses[i] = clause;
		}
		String ret = clauses[0];
		for (int i = 1; i < clauses.length; i++)
			ret += " " + clauses[i];
		return ret;
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
	 *            the logical sentence
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
	 *            the logical expression
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

}