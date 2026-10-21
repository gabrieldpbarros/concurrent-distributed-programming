#include <stdio.h>
#include <omp.h>
#include <stdbool.h>

// volatile indica ao sistema para confiar apenas
// na informação extraída da memória principal,
// pois a variável em cache pode conter um valor
// que não corresponde ao valor real
volatile bool flag[2] = {false, false}; // Flags de intenção
int shared_resource = 0;

void process(int id) {
    int other = 1 - id; // ID do outro thread
    for (int i = 0; i < 5; i++) {
        flag[id] = true;  // Indica que quer entrar
        printf("Thread %d: flag = true\n", id);
        while (flag[other]) { // Espera ocupada
            /*if (id == 0) {
                // Thread 0 nunca desiste de tentar entrar, causando starvation
                continue;
            }*/
        }
        // Seção crítica
        shared_resource++;
        printf("Thread %d incrementou: %d\n", id, shared_resource);
        // Fim da seção crítica
        flag[id] = false;  // Libera o acesso
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