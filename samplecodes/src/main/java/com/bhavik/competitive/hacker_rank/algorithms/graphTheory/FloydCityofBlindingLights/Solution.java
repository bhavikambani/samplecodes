package com.bhavik.competitive.hacker_rank.algorithms.graphTheory.FloydCityofBlindingLights;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * The Class Solution of Floyd : City of Blinding Lights problem in Graph
 * theory.
 * 
 * Reference# :
 * https://www.hackerrank.com/challenges/floyd-city-of-blinding-lights
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
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] line = br.readLine().split(" ");
		final short N = Short.parseShort(line[0]);
		final int[][] distances = new int[N + 1][N + 1];

		for (short x = 0; x < N; ++x) {
			for (short y = 0; y < N; distances[x][y++] = -1) {
			}
		}
		for (int M = Integer.parseInt(line[1]); M > 0; --M) {
			line = br.readLine().split(" ");
			final short X = (short) (Short.parseShort(line[0]) - 1);
			final short Y = (short) (Short.parseShort(line[1]) - 1);
			final short R = Short.parseShort(line[2]);
			if (distances[X][Y] < 0) {
				++distances[X][N];
			}
			distances[X][Y] = R;
		}
		final Edge[][] edges = new Edge[N][];
		for (short x = 0; x < N; ++x) {
			final short n = (short) distances[x][N];
			edges[x] = new Edge[n];
			for (short y = 0, i = 0; i < n; ++y) {
				if (distances[x][y] != -1) {
					edges[x][i++] = new Edge(y, distances[x][y]);
				}
			}
		}
		StringBuffer sb = new StringBuffer();
		final boolean[] isCalculated = new boolean[N];
		for (int Q = Integer.parseInt(br.readLine()); Q > 0; --Q) {
			line = br.readLine().split(" ");
			final short A = (short) (Short.parseShort(line[0]) - 1);
			final short B = (short) (Short.parseShort(line[1]) - 1);
			if (!isCalculated[A]) {
				distances[A] = getMinDistances(edges, A, N);
				isCalculated[A] = true;
			}
			sb.append(distances[A][B] + "\n");
		}
		System.out.print(sb);
	}

	/**
	 * Gets the min distances.
	 *
	 * @param edges
	 *            the edges
	 * @param origin
	 *            the origin
	 * @param N
	 *            the n
	 * @return the min distances
	 */
	private static int[] getMinDistances(final Edge[][] edges, final short origin, final short N) {
		final int[] distances = new int[N];
		for (short i = 0; i < N; distances[i++] = -1) {
		}
		distances[origin] = 0;
		final Queue<Edge> q = new PriorityQueue<Edge>(N, new Comparator<Edge>() {
			@Override
			public int compare(final Edge a, final Edge b) {
				return Integer.compare(a.weight, b.weight);
			}
		});
		q.add(new Edge(origin, 0));
		do {
			short nodeId = q.poll().nodeIdentifier;
			final int distance = distances[nodeId];
			for (Edge edge : edges[nodeId]) {
				nodeId = edge.nodeIdentifier;
				final int curMinDistance = distances[nodeId];
				final int newMinDistance = distance + edge.weight;
				if (curMinDistance < 0 || curMinDistance > newMinDistance) {
					distances[nodeId] = newMinDistance;
					q.add(new Edge(nodeId, newMinDistance));
				}
			}
		} while (!q.isEmpty());
		return distances;
	}

	/**
	 * The Class Edge.
	 */
	private static class Edge {
		/** The node id. */
		public final short nodeIdentifier;
		/** The weight. */
		public final int weight;

		/**
		 * Instantiates a new edge.
		 *
		 * @param nodeIdentifier
		 *            the node id
		 * @param weight
		 *            the weight
		 */
		public Edge(final short nodeIdentifier, final int weight) {
			this.nodeIdentifier = nodeIdentifier;
			this.weight = weight;
		}
	}
}