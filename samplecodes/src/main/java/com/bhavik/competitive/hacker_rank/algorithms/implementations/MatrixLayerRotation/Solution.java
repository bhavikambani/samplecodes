package com.bhavik.competitive.hacker_rank.algorithms.implementations.MatrixLayerRotation;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Bhavik Aniruddh Ambani
 *
 */

public class Solution {
	public static void main(String[] args) {
		Scanner buf = new Scanner(System.in);

		String[] line = new String[3];
		line = buf.nextLine().split(" ");
		int m = Integer.parseInt(line[0]);
		int n = Integer.parseInt(line[1]);
		int num_rotations = Integer.parseInt(line[2]);

		int[][] matrix = new int[m][n];

		for (int i = 0; i < m; ++i) {
			line = new String[n];
			line = buf.nextLine().split(" ");
			for (int j = 0; j < n; ++j) {
				matrix[i][j] = Integer.parseInt(line[j]);
			}
		}
		buf.close();
		int[][] orgMatrix = deepCopyIntMatrix(matrix);
		int rotateCount = 0;
		do {
			rotatematrix(matrix);
			rotateCount++;
		} while (!Arrays.deepEquals(matrix, orgMatrix));
		System.out.println(rotateCount);
		printMatrix(matrix);
		printMatrix(orgMatrix);
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

	private static void printMatrix(int[][] matrix) {
		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[row].length; col++) {
				System.out.printf("%3d", matrix[row][col]);
			}
			System.out.println();
		}
	}
}