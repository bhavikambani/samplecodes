package com.bhavik.competitive.hacker_rank.algorithms.implementations.MatrixLayerRotation;

import java.util.Scanner;

/**
 * The Class Solution implementation of Matrix Layer Rotation problem of hacker
 * rank.
 * 
 * Ref# https://www.hackerrank.com/challenges/matrix-rotation-algo
 * 
 * @author Bhavik Aniruddh Ambani
 */
public class Solution {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int m = in.nextInt();
		int n = in.nextInt();
		int r = in.nextInt();
		int[][] a = new int[m][n];
		for (int i = 0; i < m; ++i) {
			for (int j = 0; j < n; j++) {
				a[i][j] = in.nextInt();
			}
		}
		in.close();

		int totalLayers = Math.min(m, n) / 2;
		for (int layer = 0; layer < totalLayers; ++layer) {
			for (int x = 0; x < r % (2 * (m + n - 2 - 4 * layer)); x++) {
				int i = layer, j = layer;
				int temp = a[layer][layer];
				while (i < m - 1 - layer) {
					int temp2 = a[i + 1][j];
					a[i + 1][j] = temp;
					temp = temp2;
					i++;
				}
				while (j < n - 1 - layer) {
					int temp2 = a[i][j + 1];
					a[i][j + 1] = temp;
					temp = temp2;
					j++;
				}
				while (i > layer) {
					int temp2 = a[i - 1][j];
					a[i - 1][j] = temp;
					temp = temp2;
					i--;
				}
				while (j > layer) {
					int temp2 = a[i][j - 1];
					a[i][j - 1] = temp;
					temp = temp2;
					j--;
				}
			}
		}

		for (int x = 0; x < a.length; x++) {
			for (int y = 0; y < a[x].length; y++) {
				System.out.print(a[x][y] + " ");
			}
			System.out.println();
		}
	}
}