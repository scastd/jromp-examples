package jromp.examples;

import jromp.parallel.Parallel;
import jromp.parallel.var.ReductionVariable;
import jromp.parallel.var.Variables;
import jromp.parallel.var.reduction.Sum;

public class PiCalculationWithReduction {
	public static void main(String[] args) {
		int n = 100000;
		double h = 1.0 / n;
		ReductionVariable<Double> result = new ReductionVariable<>(new Sum<>(), 0d);
		Variables vars = Variables.create().add("sum_internal", result);

		double initialTime = System.currentTimeMillis();

		Parallel.defaultConfig()
		        .parallelFor(0, n, vars, (id, start, end, variables) -> {
			        double x;
			        double sum = 0.0;

			        for (int i = start; i < end; i++) {
				        x = h * (i - 0.5);
				        sum += calc(x);
			        }

			        final double finalSum = sum;
			        variables.<Double>get("sum_internal").update(old -> old + finalSum);
		        })
		        .join();

		double finalTime = System.currentTimeMillis();
		System.out.println("Time: " + (finalTime - initialTime) + "ms");

		double finalResult = h * result.get();
		System.out.printf("PI is approximately: %.16f, Error is: %.16f%n", finalResult,
		                  Math.abs(finalResult - Math.PI));
	}

	private static double calc(double a) {
		return 4.0 / (1.0 + a * a);
	}
}
