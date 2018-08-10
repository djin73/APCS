/*Exercises 15.2 and 15.3
 * 
 * The following code provides solutions to exercises 15.2 and 15.3 by transforming the abs()
 * methods of the original class from a static method into an object method as shown.  It also
 * transforms the equals() method from an object method into a class method.
 * 
 * Thanks to Allen B. Downey's book Think AP Java.
 */
public class Complex {

	public double real, imag;

	public Complex(double r, double i) {
		real = r;
		imag = i;
	}
	
/*original method
 * public static double abs(Complex c) {
	    return Math.sqrt(c.real * c.real + c.imag * c.imag);
	}*/
	
	//object method
	public double abs(){
		return Math.sqrt(this.real * this.real + this.imag * this.imag);
	}
	
/*	public boolean equals(Complex b) {
	    return(real == b.real && imag == b.imag);
	} */
	
	//class method
	public static boolean equals(Complex z1, Complex z2) {
	    return (z1.real == z2.real && z1.imag == z2.imag);
	}
}
