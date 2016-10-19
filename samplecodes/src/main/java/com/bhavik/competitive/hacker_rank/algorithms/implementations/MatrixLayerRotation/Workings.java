package com.bhavik.competitive.hacker_rank.algorithms.implementations.MatrixLayerRotation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Workings {
	public static void main(String[] args) throws FileNotFoundException {
		PrintWriter fileOutput = new PrintWriter(new File("/home/bhavik/Desktop/output.txt"));

		for (int m = 2; m < 50; m++) {
			for (int n = 2; n < 50; n++) {
				int[][] matrix = new int[m][n];
				int value = 1;
				for (int i = 0; i < m; ++i) {
					for (int j = 0; j < n; ++j) {
						matrix[i][j] = value++;
					}
				}
				int[][] orgMatrix = deepCopyIntMatrix(matrix);
				int rotateCount = 0;
				do {
					rotatematrix(matrix);
					rotateCount++;
				} while (!Arrays.deepEquals(matrix, orgMatrix));
				fileOutput.write(" M  = " + m + ", N = " + n + ", Total Rotations required = " + rotateCount + "\n");
			}
		}
		fileOutput.close();

	}

	public static int[][] deepCopyIntMatrix(int[][] input) {
		if (input == null)
			return null;
		int[][] result = new int[input.length][];
		for (int r = 0; r < input.length; r++) {
			result[r] = input[r].clone();
		}
		return result;
	}

	static int[][] rotatematrix(int mat[][]) {
		int m = mat.length;
		int n = mat[0].length;
		int row = 0, col = 0;
		int prev, curr;
		/*
		 * row - Staring row index m - ending row index col - starting column
		 * index n - ending column index i - iterator
		 */
		while (row < m && col < n) {
			if (row + 1 == m || col + 1 == n)
				break;
			// Store the first element of next row, this
			// element will replace first element of current
			// row
			prev = mat[row + 1][col];
			/* Move elements of first row from the remaining rows */
			for (int i = col; i < n; i++) {
				curr = mat[row][i];
				mat[row][i] = prev;
				prev = curr;
			}
			row++;
			/* Move elements of last column from the remaining columns */
			for (int i = row; i < m; i++) {
				curr = mat[i][n - 1];
				mat[i][n - 1] = prev;
				prev = curr;
			}
			n--;
			/* Move elements of last row from the remaining rows */
			if (row < m) {
				for (int i = n - 1; i >= col; i--) {
					curr = mat[m - 1][i];
					mat[m - 1][i] = prev;
					prev = curr;
				}
			}
			m--;
			/* Move elements of first column from the remaining rows */
			if (col < n) {
				for (int i = m - 1; i >= row; i--) {
					curr = mat[i][col];
					mat[i][col] = prev;
					prev = curr;
				}
			}
			col++;
		}
		return mat;
	}

	static void printMatrix(int[][] matrix) {
		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[row].length; col++) {
				System.out.printf("%3d", matrix[row][col]);
			}
			System.out.println();
		}
	}
}
