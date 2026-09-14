#include <stdio.h>

__global__ void add(int *a, int *b, int  *c) {
    *c = *a + *b;
}

int main() {
    int *d_a, *d_b, *d_c;
    int size = sizeof(int);

    cudaMallocManaged(&d_a, size);
    cudaMallocManaged(&d_b, size);
    cudaMallocManaged(&d_c, size);

    *d_a = 2;
    *d_b = 7;

    add<<<1,1>>>(d_a, d_b, d_c);
    cudaDeviceSynchronize();
    printf("Resultado: %d", *d_c);

    cudaFree(d_a); cudaFree(d_b); cudaFree(d_c);
    return 0;
}