package jromp.examples;

import jromp.Constants;
import jromp.parallel.Parallel;

public class BasicUsage {
	public static void main(String[] args) {
		Parallel.defaultConfig()
		        .begin((id, variables) -> System.out.printf("Hello World from thread %d of %d%n", id,
		                                                    variables.<Integer>get(Constants.NUM_THREADS).get()))
		        .join();
	}
}
