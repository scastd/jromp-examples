package jromp.examples;

import jromp.Constants;
import jromp.parallel.Parallel;
import jromp.parallel.var.PrivateVariable;
import jromp.parallel.var.SharedVariable;
import jromp.parallel.var.Variables;

import java.util.Random;

public class ParallelForBase {
	// A simple random number generator.
	private static final Random random = new Random();

	public static int rand(int bound) {
		return random.nextInt(bound);
	}

	public static void main(String[] args) {
		final int N = 4;
		final int M = 4;

		int i;
		int j = 0;
		int n;
		int m;
		int sum = 0;
		int[] a = new int[M];
		int[] c = new int[N];
		int[][] b = new int[M][N];

		m = M;
		n = N;

		// Initialize the arrays.
		for (i = 0; i < M; i++) {
			for (j = 0; j < N; j++) {
				b[i][j] = rand(100);
			}
		}

		for (i = 0; i < N; i++) {
			c[i] = rand(100);
		}

		// Declare the variables
		Variables vars = Variables.create();
		vars.add("a", new SharedVariable<>(a))
		    .add("b", new SharedVariable<>(b))
		    .add("c", new SharedVariable<>(c))
		    .add("m", new SharedVariable<>(m))
		    .add("n", new SharedVariable<>(n))
		    // Private variables
		    .add("i", new PrivateVariable<>(i))
		    .add("j", new PrivateVariable<>(j))
		    .add("sum", new PrivateVariable<>(sum));

		// Parallel for loop
		Parallel.withThreads(4)
		        .parallelFor(0, m, vars, (id, start, end, variables) -> {
			        variables.get("sum").set(0);
			        int nThreads = variables.<Integer>get(Constants.NUM_THREADS).get();

			        for (int j1 = 0; j1 < n; j1++) {
				        int finalJ = j1;
				        variables.get("sum")
				                 .update(old -> variables.<int[][]>get("b").get()[id][finalJ] *
						                 variables.<int[]>get("c").get()[finalJ]);
			        }

			        variables.<int[]>get("a").get()[id] = variables.<Integer>get("sum").get();
			        System.out.printf("Thread %d, of %d, calculates the iteration i=%d%n", id, nThreads, id);
		        })
		        .join();

		for (i = 0; i < m; i++) {
			System.out.printf("a[%d] = %d\n", i, a[i]);
		}
	}
}
