package com.bhavik.competitive.hacker_rank.crackingCodingInterview.SortingComparator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Ref### https://www.hackerrank.com/challenges/ctci-comparator-sorting
 * 
 * @author Bhavikkumar Ambani
 * 
 *
 */
class Solution {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		Player[] player = new Player[n];
		Checker checker = new Checker();
		for (int i = 0; i < n; i++) {
			player[i] = new Player(scan.next(), scan.nextInt());
		}
		scan.close();
		Arrays.sort(player, checker);
		for (int i = 0; i < player.length; i++) {
			System.out.printf("%s %s\n", player[i].name, player[i].score);
		}
	}
}

class Checker implements Comparator<Player> {

	@Override
	public int compare(Player o1, Player o2) {
		int result = o2.score - o1.score;
		if (result != 0)
			return result;
		else
			return o1.name.compareTo(o2.name);
	}

}

class Player {
	String name;
	int score;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Player(String name, int score) {
		super();
		this.name = name;
		this.score = score;
	}

}