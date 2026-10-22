#include <iostream>
#include <vector>
#include <cmath>
#include <fstream>
#include <string>
#include <sstream>
#include <numeric>
#include <algorithm>
#include <iterator>
#include <omp.h>

#define THREADS_USED 8

template <typename T>
void imprimir_vetor(const std::vector<T>& vec) {
    std::cout << "[ ";
    for (const auto& elem : vec) {
        std::cout << elem << " ";
    }
    std::cout << "]" << '\n';
}

void processar_dados(
    const std::vector<double>& dados,
    double media,
    double& variancia,
    double& max_val
) {
    int n = dados.size();
    double soma_quadrados = 0.0;
    double maior = dados[0];

    omp_set_num_threads(THREADS_USED);
    #pragma omp parallel for \
        shared(dados, media, n) \
        reduction(+:soma_quadrados) \
        reduction(max:maior)
    for (int i = 0; i < n; i++) {
        double diff = dados[i] - media;
        soma_quadrados += diff * diff;

        if (dados[i] > maior)
            maior = dados[i];
    }

    variancia = soma_quadrados / n;
    max_val = maior;
}

int main(int argc, char* argv[]) {
    if (argc == 3) {
        std::ifstream inf{ argv[1] };
        std::ofstream outf{ argv[2] };
        if (!inf) {
            std::cerr << "Não foi possível abrir " << argv[1] << ".\n";
            return 1;
        }
        if (!outf) {
            std::cerr << "Não foi possível abrir " << argv[2] << ".\n";
            return 1;
        }

        outf << "Threads usadas: " << THREADS_USED << '\n';

        std::string strLine{};
        while (std::getline(inf, strLine)) { // dados
            if (strLine.empty()) continue;
            std::vector<double> dados{};
            double media{}, variancia{}, max_val{}; 

            std::stringstream ss(strLine);
            double tempDouble{};
            while (ss >> tempDouble) dados.push_back(tempDouble);
            if (dados.empty()) continue;

            double soma = std::accumulate(
                dados.begin(),
                dados.end(),
                0.0
            );
            media = soma / dados.size();

            double inicio{ omp_get_wtime() };
            processar_dados(dados, media, variancia, max_val);
            double fim{ omp_get_wtime() };

            // impressao
            std::cout << "Tamanho do conjunto: " << dados.size() << ".\n"
                << "Tempo de execucao: " << (fim - inicio) * 1000.0 << " ms.\n";
            //imprimir_vetor(dados);
            std::cout << "A variancia e " << variancia 
                      << " e o maior valor e " << max_val << ".\n\n";

            // saida
            std::copy(
                dados.begin(),
                dados.end(),
                std::ostream_iterator<double>(
                    outf,
                    " "
                )
            );
            outf << "\nTempo de execucao: " << (fim - inicio) * 1000.0 << " ms\n"
                << "Variancia: " << variancia << '\n'
                << "Maior valor: " << max_val << "\n\n";
        }

        return 0;
    } else {
        std::cout << "Insira os nomes dos arquivos leitura e saida.\n";
        std::cerr << "USAGE: ./variancia_valor_maximo 'ENTRADA.txt' 'SAIDA.txt'\n";
        return 1;
    }
}