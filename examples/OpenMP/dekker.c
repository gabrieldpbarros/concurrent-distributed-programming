#include <stdio.h>
#include <omp.h>
#include <stdbool.h>

volatile bool flag[2] = {false, false}; // Flags de intenção
volatile int turn = 0;                 // Variável de controle
int shared_resource = 0;

void process(int id) {
    int other = 1 - id; // ID do outro thread
    for (int i = 0; i < 5; i++) {
        flag[id] = true;
        while (flag[other]) { // Espera ocupada
            if (turn != id) {
                flag[id] = false;
                while (turn != id); // Aguarda a sua vez
                flag[id] = true;
            }
        }
        // Seção crítica
        shared_resource++;
        printf("Thread %d incrementou: %d\n", id, shared_resource);
        // Fim da seção crítica
        turn = other;
        flag[id] = false;
    }
}

int main() {
    #pragma omp parallel num_threads(2)
    {
        int id = omp_get_thread_num();
        process(id);
    }
    printf("Valor final do recurso compartilhado: %d\n", shared_resource);
    return 0;
}