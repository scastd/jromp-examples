#include <omp.h>
#include <stdio.h>
#include <math.h>

double calc(double a);

int main(int argc, char *argv[]) {
    int n = 100000; // Número de intervalos
    int i;
    double pi25dt = 3.141592653589793238462643;
    double h = 1.0 / n;
    double sum = 0.0; // Suma de intervalos
    double x;

    double start = omp_get_wtime();

    // La variable sum es una variable de reducción (+)
#pragma omp parallel for private(i, x) reduction(+:sum)
    for (i = 1; i <= n; i++) {
        x = h * (i - 0.5);
        sum += calc(x);
    }

    sum = h * sum;
    double end = omp_get_wtime();
    printf("Time: %fms\n", (end - start) * 1000);
    printf("PI is approximately: %.16f. Error: %.16f\n", sum, fabs(sum - pi25dt));
    return 0;
}

double calc(double a) {
    return 4.0 / (1.0 + a * a);
}
