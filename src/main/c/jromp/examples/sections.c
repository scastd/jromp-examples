#include <stdio.h>
#include <omp.h>

int main(int argc, char *argv[]) {
    #pragma omp parallel sections
    {
		#pragma omp section
		{
			printf("Task 1. Running thread %d\n", omp_get_thread_num());
		}

		#pragma omp section
		{
			printf("Task 2. Running thread %d\n", omp_get_thread_num());
		}

		#pragma omp section
		{
			printf("Task 3. Running thread %d\n", omp_get_thread_num());
		}

        #pragma omp section
		{
			printf("Task 4. Running thread %d\n", omp_get_thread_num());
		}
	}
}
