package com.bhavik.competitive.hacker_rank.algorithms.search.ConnectedCellsinaGrid;

import java.util.Scanner;

/**
 * The Class Solution of Connected Cells in a Grid problem of hacker rank.
 * 
 * Ref# https://www.hackerrank.com/challenges/connected-cell-in-a-grid
 * 
 * @author Bhavik Aniruddh Ambani
 */
public class Solution {

	/** The visited cells in grid. */
	public static boolean[][] visitedCellsInGrid;

	/**
	 * Find zone helper.
	 *
	 * @param grid
	 *            the grid
	 * @param i
	 *            the i
	 * @param j
	 *            the j
	 * @param counter
	 *            the counter
	 * @param m
	 *            the m
	 * @param n
	 *            the n
	 * @return the int
	 */
	public static int findConnectedZone(int[][] grid, int i, int j, int counter, int m, int n) {
		if (i < 0 || j < 0 || i >= m || j >= n)
			return 0;
		if (visitedCellsInGrid[i][j])
			return 0;
		visitedCellsInGrid[i][j] = true;
		if (grid[i][j] == 0)
			return 0;
		else
			return 1 + findConnectedZone(grid, i - 1, j - 1, counter, m, n)
					+ findConnectedZone(grid, i - 1, j, counter, m, n)
					+ findConnectedZone(grid, i - 1, j + 1, counter, m, n)
					+ findConnectedZone(grid, i, j - 1, counter, m, n) + findConnectedZone(grid, i, j, counter, m, n)
					+ findConnectedZone(grid, i, j + 1, counter, m, n)
					+ findConnectedZone(grid, i + 1, j - 1, counter, m, n)
					+ findConnectedZone(grid, i + 1, j, counter, m, n)
					+ findConnectedZone(grid, i + 1, j + 1, counter, m, n);
	}

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
		int[][] grid = new int[m][n];
		visitedCellsInGrid = new boolean[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				grid[i][j] = in.nextInt();
				// First we set visited nodes to false
				visitedCellsInGrid[i][j] = false;
			}
		}
		in.close();
		int max = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (!visitedCellsInGrid[i][j])
					max = Math.max(max, findConnectedZone(grid, i, j, 0, m, n));
			}
		}
		System.out.println(max);
	}
}