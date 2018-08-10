
/*
 * This code implements code demonstrating the vocabulary terms and concepts for Vocab 11 in the context
 * of a class representing a 2D matrix of numbers with functions such as matrix multiplication.
 * 
 * try - In the multiply() method, on line 64, there is a try block which "tries" the process of 
 * matrix multiplication on the two inputs.
 * 
 * catch - Following the try block, on line 74 there is a catch block which takes over if the try block
 * fails (this happens when the input matrices do not satisfy the constraint for matrix multiplication,
 * where the number of columns of the first matrix must equal the number of rows in the second).
 * 
 * finally - After the catch block, on line 78 there is a finally block which always executes no matter
 * how the try/catch terminated.
 * 
 * error - The main error in this code appears when trying to multiply Matrices m1 and m2 on line 106.
 *
 * compile time error - Although it is commented out to assure the program still runs, line 100 provides 
 * an example of a compile time error.  Because it is syntatically incorrect to initialize a Matrix with 
 * a number of rows equal to 0.5, the program will detect the error while compiling the code (BEFORE 
 * running it), making it a compile-time error.
 * 
 * run time error - Line 106 provides an example of a runtime error, as it tries to multiply two matrices
 * whose dimensions do not match (the number of columns in the first matrix is not equal to the number 
 * of rows in the second).  This is a runtime error because it is not detected by the compiler, but only
 * causes problems while the program is running.  In this case, it is detected and handled by the catch 
 * block in the multiply() method.
 * 
 * error correction code - Though not explicitly implemented in this code, because this program stores
 * and transmits data it will automatically detect any errors and correct them through the use of error
 * correction codes and parity bits.
 */

import java.util.*;

public class Matrix {
	public int numRows;
	public int numCols;
	public double[][] A; // 2d array representing the values of the matrix

	// constructor
	public Matrix(int rows, int cols) {
		numRows = rows;
		numCols = cols;
		A = new double[rows][cols];
	}

	// constructor
	public Matrix(double[][] m) {
		numRows = m.length;
		numCols = m[0].length;
		A = Arrays.copyOf(m, m.length);
	}

	// set a particular value in the matrix
	public void set(int row, int col, double value) {
		A[row - 1][col - 1] = value;
	}

	// Multiplies two matrices with m1 on the left and m2 on the right; returns
	// the resulting matrix.
	public static Matrix multiply(Matrix m1, Matrix m2) {
		double[][] result = new double[m1.numRows][m2.numCols];
		try {
			for (int r = 0; r < m1.numRows; r++) {
				for (int c = 0; c < m2.numCols; c++) {
					double sum = 0;
					for (int i = 0; i < m1.numCols; i++) {
						sum += m1.A[r][i] * m2.A[i][c];
					}
					result[r][c] = sum;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// matrix dimensions don't match for valid multiplication
			System.out.println("Error: Matrix dimensions do not match.");
			return null;
		} finally {
			System.out.println("This statement is always printed.");
		}
		return new Matrix(result);

	}


	//prints this matrix
	public void printMatrix() {
		for (int r = 0; r < A.length; r++) {
			System.out.print(A[r][0]);
			for (int c = 1; c < A[0].length; c++)
				System.out.print(" " + A[r][c]);
			System.out.println();
		}
	}

	public static void main(String[] args) {
		double[][] a1 = new double[][] { { 1.5, 0, 2 }, { 0, 0, 2 } }; // 2x3
		double[][] a2 = new double[][] { { 1.5, 0 }, { 0, 1 } };// 2x2

		// Matrix example = new Matrix(0.5, 10); //compile time error
		Matrix m1 = new Matrix(a1);// 2x3 matrix
		Matrix m2 = new Matrix(a2);// 2x2 matrix
		m1.printMatrix();
		m2.printMatrix();

		Matrix m3 = multiply(m1, m2); //run time error

	}

}
