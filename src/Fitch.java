
/*
 * The following code implements the ten Fitch Rules of Inference as described by the Stanford Logic 
 * Course.  Each method assumes the inputs are formatted exactly as described by the method specifications, 
 * and returns the appropriate output for each logic rule.
 * 
 * I would like to thank the Stanford Logic Course for teaching me about the Fitch rules:
 * http://intrologic.stanford.edu/sections/section_04_04.html
 * 
 * I would also like to thank Mr. Kuszmaul for teaching us propositional logic concepts and helping
 * me out on this assignment.
*/
import java.util.ArrayList;

public class Fitch {

	public static void main(String[] args) {

		// demo of methods
		ArrayList<String> phi = new ArrayList<String>();
		phi.add("B");
		phi.add("A");
		phi.add("C");
		String conjunction = andIntro(phi);
		System.out.println(conjunction); //prints B&A&C
		System.out.println(andElim(conjunction).toString()); //prints [B, A, C]
		
		System.out.println(orIntro("D", phi)); //prints D|B|A|C
		ArrayList<String> phi2 = new ArrayList<String>();
		phi2.add("A|B|C");
		phi.add("B=>D");
		phi.add("A=>D");
		phi.add("C=>D");
		System.out.println(orElim(phi2, "D")); //prints D
		
		System.out.println(negationIntro("A=>~B", "A=>B", "A")); //prints ~A
		System.out.println(negationElim("~~A")); //prints A
		
		System.out.println(implicationIntro("A", "B")); //prints A=>B
		System.out.println(implicationElim("A=>B", "A")); // prints B
		
		System.out.println(biconditionalIntro("A=>B", "B=>A", "A", "B")); //prints A<=>B
		System.out.println(biconditionalElim("A<=>B").toString()); //prints [A=>B, B=>A]
	}

	/**
	 * 
	 * @param phi
	 *            an ArrayList of true logical expressions
	 * @return a string representing the conjunction of all the logical
	 *         expressions
	 */
	public static String andIntro(ArrayList<String> phi) {
		String ret = "";
		for (int i = 0; i < phi.size() - 1; i++) {
			ret += phi.get(i) + "&";
		}
		ret += phi.get(phi.size() - 1);
		return ret;
	}

	/**
	 * 
	 * @param conjunction
	 *            a conjunction of many logical expressions (e.g. A&B&C)
	 * @return an ArrayList containing all the separate logical expressions
	 *         found in the conjunction
	 */
	public static ArrayList<String> andElim(String conjunction) {
		ArrayList<String> ret = new ArrayList<String>();
		String[] phis = conjunction.split("&");
		for (String phi : phis)
			ret.add(phi);
		return ret;

	}

	/**
	 * 
	 * @param phi
	 *            a logical expression which we know to be true (e.g. C)
	 * @param disjuncts
	 *            an ArrayList of disjuncts (e.g. {D, E, F}) to add to phi
	 * @return a disjunction containing the true expression and all the added
	 *         disjuncts (e.g. C|D|E|F )
	 */
	public static String orIntro(String phi, ArrayList<String> disjuncts) {
		String ret = phi;
		for (String d : disjuncts)
			ret += "|" + d;
		return ret;
	}

	/**
	 * 
	 * @param phi
	 *            an ArrayList containing a disjunction (e.g. A|B|C) and a
	 *            series of implications with each logical expression in the
	 *            disjunction implying a logical expression, phi (e.g. A=>phi,
	 *            B=>phi, etc.)
	 * @param psi
	 *            the logical expression implied by each of the disjuncts
	 * @return the logical expression phi, which is known to be true
	 */
	public static String orElim(ArrayList<String> phi, String psi) {
		return psi;
	}

	/**
	 * 
	 * @param imp1
	 *            an implication of the form phi=>psi
	 * @param imp2
	 *            an implication of the form phi=>~psi
	 * @param psi
	 *            the logical expression being implicated
	 * @return a String representing the negation of phi, since it must be false
	 */
	public static String negationIntro(String imp1, String imp2, String psi) {
		return "~" + psi;
	}

	/**
	 * 
	 * @param phi
	 *            A logical expression with a double negative
	 * @return the logical expression after eliminating the double negative
	 */
	public static String negationElim(String phi) {
		if (phi.substring(0, 2).equals("~~")) {
			return phi.substring(2);
		}
		return phi;
	}

	/**
	 * 
	 * @param phi
	 *            a logical expression
	 * @param psi
	 *            a logical expression entailed by phi
	 * @return a String representing the implication phi => psi
	 */
	public static String implicationIntro(String phi, String psi) {
		return phi + "=>" + psi;
	}

	/**
	 * 
	 * @param implication
	 *            an implication of the form phi=>psi
	 * @param phi
	 *            a string representing the left side of the implication, which
	 *            is true
	 * @return psi, the logical expression being implied, which we now know to
	 *         be true
	 */
	public static String implicationElim(String implication, String phi) {
		String[] s = implication.split("=>");
		return s[1];
	}

	/**
	 * 
	 * @param imp1
	 *            an implication of the form phi=>psi
	 * @param imp2
	 *            an implication of the form psi=>phi
	 * @param phi
	 *            one of the logical expressions in the implications
	 * @param psi
	 *            the other logical expression in the implications
	 * @return a biconditional of the form phi<=>psi
	 */
	public static String biconditionalIntro(String imp1, String imp2, String phi, String psi) {
		return phi + "<=>" + psi;
	}

	/**
	 * 
	 * @param bi
	 *            a biconditional of the form phi<=>psi
	 * @return an ArrayList containing two implications: one of the form
	 *         phi=>psi, and the other of the form psi=>phi
	 */
	public static ArrayList<String> biconditionalElim(String bi) {
		ArrayList<String> ret = new ArrayList<String>();
		String[] s = bi.split("<=>");
		String phi = s[0];
		String psi = s[1];
		ret.add(phi + "=>" + psi);
		ret.add(psi + "=>" + phi);
		return ret;
	}

}
