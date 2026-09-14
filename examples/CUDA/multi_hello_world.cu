#include <stdio.h>

__global__ void hello(void) {
    printf("Hello Wold! De: thread %d - bloco: %d\n", threadIdx.x, blockIdx.x);
}

int main(void) {
    int num_threads = 2;
    int num_blocks = 2;
    hello<<<num_blocks,num_threads>>>();
    cudaDeviceSynchronize();
    
    return 0;
}