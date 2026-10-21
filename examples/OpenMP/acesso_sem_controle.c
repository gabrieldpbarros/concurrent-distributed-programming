# include <stdio.h>
# include <omp.h>

int shared_counter = 0;

int main() {
    #pragma omp parallel num_threads(3)
    {
        for (int i = 0; i < 1000; i++) {
            shared_counter++; // Seção crítica sem controle
        }
    }
    printf("Valor final do contador: %d\n", shared_counter);
    return 0;
}