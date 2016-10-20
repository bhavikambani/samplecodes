package com.bhavik.competitive.hacker_rank.algorithms.search.CountLuck;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/**
 * The Class Solution of Count Luck algorithm problem of Hacker Rank.
 * 
 * Ref# https://www.hackerrank.com/challenges/count-luck
 * 
 * @author Bhavik Aniruddh Ambani
 */
public class Solution {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		StringBuilder sb = new StringBuilder();
		Scanner in = new Scanner(System.in);

		for (byte T = Byte.parseByte(in.next().trim()); T > 0; --T) {
			in.nextLine(); // Temperory line read for white space characters
			// System.out.println(tmp);
			// System.out.println(Arrays.deepToString(tmp.split(" ")));
			String[] inputLine = in.nextLine().split(" ");
			final byte n = Byte.parseByte(inputLine[0]);
			final byte m = Byte.parseByte(inputLine[1]);
			final char[][] forest = new char[n][];
			for (byte i = 0; i < n; forest[i++] = in.next().toCharArray()) {
			}
			final short k = Short.parseShort(in.next());
			byte startY = 0;
			byte startX = 0;
			for (byte y = 0; y < n; ++y) {
				for (byte x = 0; x < m; ++x) {
					if (forest[y][x] == 'M') {
						startY = y;
						startX = x;
						y = n;
						break;
					}
				}
			}
			sb.append(countWaves(forest, n, m, startY, startX) == k ? "Impressed\n" : "Oops!\n");
		}
		in.close();
		System.out.print(sb);
	}

	/**
	 * Count waves.
	 *
	 * @param forest
	 *            the forest
	 * @param Y
	 *            the y
	 * @param X
	 *            the x
	 * @param startY
	 *            the start Y
	 * @param startX
	 *            the start X
	 * @return the short
	 */
	private static short countWaves(char[][] forest, byte Y, byte X, byte startY, byte startX) {
		final short[][] minWaves = new short[Y][X];

		for (short y = 0; y < Y; ++y) {
			for (short x = 0; x < X; minWaves[y][x++] = -1) {
			}
		}
		Deque<Point> stack = new ArrayDeque<Point>();
		stack.push(new Point(startY, startX));
		minWaves[startY][startX] = 0;

		while (!stack.isEmpty()) {
			Point pointBean = stack.pop();
			short waves = minWaves[pointBean.y][pointBean.x];
			if (forest[pointBean.y][pointBean.x] == '*') {
				return waves;
			}
			waves++;
			byte childCount = 0;
			if (pointBean.y > 0 && forest[pointBean.y - 1][pointBean.x] != 'X'
					&& minWaves[pointBean.y - 1][pointBean.x] < 0) {
				++childCount;
				minWaves[pointBean.y - 1][pointBean.x] = waves;
				stack.push(new Point((byte) (pointBean.y - 1), pointBean.x));
			}
			if (pointBean.x > 0 && forest[pointBean.y][pointBean.x - 1] != 'X'
					&& minWaves[pointBean.y][pointBean.x - 1] < 0) {
				++childCount;
				minWaves[pointBean.y][pointBean.x - 1] = waves;
				stack.push(new Point(pointBean.y, (byte) (pointBean.x - 1)));
			}
			if (pointBean.y + 1 < Y && forest[pointBean.y + 1][pointBean.x] != 'X'
					&& minWaves[pointBean.y + 1][pointBean.x] < 0) {
				++childCount;
				minWaves[pointBean.y + 1][pointBean.x] = waves;
				stack.push(new Point((byte) (pointBean.y + 1), pointBean.x));
			}
			if (pointBean.x + 1 < X && forest[pointBean.y][pointBean.x + 1] != 'X'
					&& minWaves[pointBean.y][pointBean.x + 1] < 0) {
				++childCount;
				minWaves[pointBean.y][pointBean.x + 1] = waves;
				stack.push(new Point(pointBean.y, (byte) (pointBean.x + 1)));
			}
			if (childCount == 1) {
				pointBean = stack.peek();
				--minWaves[pointBean.y][pointBean.x];
			}
		}
		return (short) -1;
	}

	/**
	 * The Class Point that holds the value of x and y.
	 */
	private static class Point {

		/** The y. */
		public final byte y;

		/** The x. */
		public final byte x;

		/**
		 * Instantiates a new point.
		 *
		 * @param y
		 *            the y
		 * @param x
		 *            the x
		 */
		public Point(final byte y, final byte x) {
			this.y = y;
			this.x = x;
		}
	}
}