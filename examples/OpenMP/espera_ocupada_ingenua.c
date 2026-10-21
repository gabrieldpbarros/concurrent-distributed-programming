#include <stdio.h>
#include <omp.h>

volatile int turn = 0; // Controla a vez
int shared_counter = 0;

void critical_section(int id) {
    for (int i = 0; i < 5; i++) {
        while (turn != id); // Espera ocupada
        // Seção crítica
        shared_counter++;
        printf("Thread %d incrementou: %d\n", id, shared_counter);
        // Fim da seção crítica
        turn = 1 - id; // Troca a vez
        //Seção não crítica que termina
        if(id ==0 && i ==3) break;
    }
}

int main() {
    #pragma omp parallel num_threads(2)
    {
        int id = omp_get_thread_num();
        critical_section(id);
    }
    printf("Valor final do contador: %d\n", shared_counter);
    return 0;
}
