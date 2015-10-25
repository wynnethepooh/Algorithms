package project3;//******************************************************************************
// AUTHOR: Wynne Tran
// PROFESSOR: Gilberto Perez
// PROJECT: Matrix Multiplication
// LAST UPDATED: December 10, 2014
//******************************************************************************

import java.util.Random;
import java.util.Scanner;

public class MatrixMultiplication
{
	private static Scanner sc = new Scanner(System.in);
	final int THRESHOLD = 32;
	
	public static void main(String[] args)
	{
		MatrixMultiplication project = new MatrixMultiplication();
		
		int size = 64;
		
		for (int ndx = 0; ndx < 6; ndx++)
		{
			System.out.println();
			System.out.println("========= Size: " + size + " =========");
			System.out.println();
			//System.out.println("Please enter the size for the matrices: ");
			//int size = sc.nextInt();
			//sc.nextLine();
			
			int[][] A = project.createMatrix(size);
			int[][] B = project.createMatrix(size);
			
			project.getRunTimes(A, B);
			size *= 2;
		}
	}
	
	public void getRunTimes(int[][] A, int[][] B)
	{
		long[] iterative = new long[5];
		long[] strassens = new long[5];
		long iterativeAverage = 0;
		long strassensAverage = 0;
		
		System.out.println("Iterative runtimes (ns): ");
		for (int ndx = 0; ndx < 5; ndx++)
		{
			long initTime = System.nanoTime();
			//project.printMatrix(A, B, project.multiply(A, B));
			multiply(A, B);
			long finalTime = System.nanoTime();
			
			iterative[ndx] = finalTime - initTime;
			System.out.println(iterative[ndx]);
		}
		for (int ndx = 0; ndx < 5; ndx++)
		{
			iterativeAverage += iterative[ndx]; 
		}
		System.out.println(iterativeAverage / 5);
		
		System.out.println();
		System.out.println("Strassen's runtimes (ns): ");
		for (int ndx = 0; ndx < 5; ndx++)
		{
			long initTime = System.nanoTime();
			//project.printMatrix(A, B, project.strassen(A, B));
			strassen(A, B);
			long finalTime = System.nanoTime();
			
			strassens[ndx] = finalTime - initTime;
			System.out.println(strassens[ndx]);
		}
		for (int ndx = 0; ndx < 5; ndx++)
		{
			strassensAverage += strassens[ndx]; 
		}
		System.out.println(strassensAverage / 5);
	}
	
	public int[][] createMatrix(int n)
	{
		int[][] matrix = new int[n][n];
		Random rand = new Random();
		
		for (int row = 0; row < n; row++)
		{
			for (int col = 0; col < n; col++)
			{
				matrix[row][col] = rand.nextInt(9) + 1;
			}
		}
		
		return matrix;
	}
	
	private void printMatrix(int[][] A, int[][] B, int[][] C)
	{
		// Print indices
		System.out.println();
		System.out.print("\t\t");
		for (int ndx = 0; ndx < A.length; ndx++)
			System.out.print(ndx + "\t");
		System.out.print("\t\t\t\t\t");
		for (int ndx = 0; ndx < B.length; ndx++)
			System.out.print(ndx + "\t");
		System.out.print("\t\t\t\t\t");
		for (int ndx = 0; ndx < C.length; ndx++)
			System.out.print(" " + ndx + "\t");
		
		System.out.println();
		
		// Print border
		System.out.print("\t -");
		for (int ndx = 0; ndx < A.length; ndx++)
			System.out.print("----");
		System.out.print("\t\t\t\t");
		System.out.print("\t -");
		for (int ndx = 0; ndx < A.length; ndx++)
			System.out.print("----");
		System.out.print("\t\t\t\t");
		System.out.print("\t  -");
		for (int ndx = 0; ndx < A.length; ndx++)
			System.out.print("----");
		
		System.out.println();
		
		for (int row = 0; row < A.length; row++)
		{
			if (row >= 10)
				System.out.print(" " + row + " |\t");
			else
				System.out.print("  " + row + " |\t" );
			
			for (int col = 0; col < A.length; col++)
			{
				System.out.print(A[row][col] + "\t");
			}
			
			if (row == (A.length / 2) - 1)
				System.out.print("\tx\t\t");
			else
				System.out.print("\t\t\t");
			
			if (row >= 10)
				System.out.print(" " + row + " |\t");
			else
				System.out.print("  " + row + " |\t" );
			
			for (int col = 0; col < B.length; col++)
			{
				System.out.print(B[row][col] + "\t");
			}
			
			if (row == (A.length / 2) - 1)
				System.out.print("\t=\t\t");
			else
				System.out.print("\t\t\t");
			
			if (row >= 10)
				System.out.print("  " + row + " |\t");
			else
				System.out.print("   " + row + " |\t" );
			
			for (int col = 0; col < C.length; col++)
			{
				System.out.print(C[row][col] + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public int[][] multiply(int[][] A, int[][] B)
	{
		int[][] result = new int[A.length][A.length];
		
		for (int row = 0; row < A.length; row++)
		{
			for (int col = 0; col < A.length; col++)
			{
				for (int ndx = 0; ndx < A.length; ndx++)
				{
					result[row][col] += A[row][ndx] * B[ndx][col];
				} 
			}
		}
		
		return result;
	}
	
	public int[][] add(int[][] A, int[][] B)
	{
		int[][] result = new int[A.length][A.length];
		
		for (int row = 0; row < A.length; row++)
		{
			for (int col = 0; col < A.length; col++)
			{
				result[row][col] = A[row][col] + B[row][col];
			}
		}
		
		return result;
	}
	
	public int[][] sub(int[][] A, int[][] B)
	{
		int[][] result = new int[A.length][A.length];
		
		for (int row = 0; row < A.length; row++)
		{
			for (int col = 0; col < A.length; col++)
			{
				result[row][col] = A[row][col] - B[row][col];
			}
		}
		
		return result;
	}
	
	public int[][] strassen(int[][] A, int[][] B) 
	{
		int m = nextPowerOfTwo(A.length);
		int[][] APrep = new int[m][m];
		int[][] BPrep = new int[m][m];
		
		for (int row = 0; row < A.length; row++) 
		{
			for (int col = 0; col < A.length; col++)
			{
				APrep[row][col] = A[row][col];
				BPrep[row][col] = B[row][col];
			}
		}
		
		int[][] CPrep = strassenHelper(APrep, BPrep);
		int[][] C = new int[A.length][A.length];
		
		for (int row = 0; row < A.length; row++) 
		{
			for (int col = 0; col < A.length; col++)
			{
				C[row][col] = CPrep[row][col];
			}
		}
		return C;
	}
	
	public int[][] strassenHelper(int[][] A, int[][] B)
	{
		int n = A.length;
		int newSize = n / 2;
		int[][] result = new int[n][n];
		
		if (n <= 1)
		{
			result = multiply(A, B);
		}
		else
		{
			int A11[][] = new int[newSize][newSize];
			int A12[][] = new int[newSize][newSize];
			int A21[][] = new int[newSize][newSize];
			int A22[][] = new int[newSize][newSize];
			
			int B11[][] = new int[newSize][newSize]; 
			int B12[][] = new int[newSize][newSize];
			int B21[][] = new int[newSize][newSize];
			int B22[][] = new int[newSize][newSize];
			
			// Divide the matrices in 4 sub-matrices:
			for (int row = 0; row < newSize; row++) 
			{
				for (int col = 0; col < newSize; col++)
				{
					A11[row][col] = A[row][col]; // top left
					A12[row][col] = A[row][col + newSize]; // top right
					A21[row][col] = A[row + newSize][col]; // bottom left
					A22[row][col] = A[row + newSize][col + newSize]; // bottom right

					B11[row][col] = B[row][col]; // top left
					B12[row][col] = B[row][col + newSize]; // top right
					B21[row][col] = B[row + newSize][col]; // bottom left
					B22[row][col] = B[row + newSize][col + newSize]; // bottom right
				}
      }

      // Calculate m1 to m7:
			int[][] M1 = strassenHelper(add(A11, A22), add(B11, B22));
			// m1 = (a11+a22) * (b11+b22)

			int[][] M2 = strassenHelper(add(A21, A22), B11);
			// m2 = (a21+a22) * (b11)

			int[][] M3 = strassenHelper(A11, sub(B12, B22));
			// m3 = (a11) * (b12 - b22)

			int[][] M4 = strassenHelper(A22, sub(B21, B11));
			// m4 = (a22) * (b21 - b11)

			int[][] M5 = strassenHelper(add(A11, A12), B22);
			// m5 = (a11+a12) * (b22)

			int[][] M6 = strassenHelper(sub(A21, A11), add(B11, B12));
			// m6 = (a21-a11) * (b11+b12)
			
			int[][] M7 = strassenHelper(sub(A12, A22), add(B21, B22));
			// m7 = (a12-a22) * (b21+b22)
			
      // Calculate c21, c21, c11 and c22:
			int[][] C12 = add(M3, M5); 
			// c12 = m3 + m5
			
			int[][] C21 = add(M2, M4); 
			// c21 = m2 + m4

			int[][] C11 = sub(add(add(M1, M4), M7), M5);
			// c11 = m1 + m4 - m5 + m7

			int[][] C22 = sub(add(add(M1, M3), M6), M2);
			// c22 = m1 + m3 - m2 + m6

			// Grouping the results obtained in a single matrix:
			int[][] C = new int[n][n];
			for (int row = 0; row < newSize; row++) 
			{
				for (int col = 0; col < newSize; col++)
				{
					C[row][col] = C11[row][col];
					C[row][col + newSize] = C12[row][col];
					C[row + newSize][col] = C21[row][col];
					C[row + newSize][col + newSize] = C22[row][col];
				}
			}
			result = C;
		}
		return result;
	}
	
	private int nextPowerOfTwo(int n) 
	{
		int log2 = (int) Math.ceil(Math.log(n) / Math.log(2));
		return (int) Math.pow(2, log2);
	}
}
