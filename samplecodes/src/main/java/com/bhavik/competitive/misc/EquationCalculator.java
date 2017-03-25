package com.bhavik.competitive.misc;

import java.util.Objects;
import java.util.Stack;

public class EquationCalculator {

	public static void main(String[] args) {

	}

	public static int solveEquation(String equation) throws Exception {

		if (Objects.isNull(equation)) {
			throw new NullPointerException("Equation is null");
		}

		if (equation.length() == 1)
			throw new Exception("Equation is not valid");

		int result = 0;
		StringBuilder numberBuilder = new StringBuilder();
		Stack<String> equationStack = new Stack<>();

		char[] equationChars = equation.toCharArray();

		for (int i = 0; i < equationChars.length; i++) {

		}

		return result;
	}
}
