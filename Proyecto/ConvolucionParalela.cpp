#include <iostream>
#include <vector>
#include <random>
#include <chrono>
#include <thread> // Necesario para threads

using namespace std;

// Variables globales para acceso compartido
vector<vector<double>> imagen;
vector<vector<double>> resultado;
vector<vector<double>> kernel;
int N;

// --- 1. GENERADOR (Igual al secuencial) ---
void generarDatos(int n, int op) {
    N = n;
    // Inicializamos vectores con tamaño N
    imagen.assign(N, vector<double>(N));
    resultado.assign(N, vector<double>(N));
    
    random_device rd;
    mt19937 gen(rd());
    uniform_real_distribution<> dis(0.0, 255.0);
    
    // Llenar matriz
    for(int i=0; i<N; ++i) 
        for(int j=0; j<N; ++j) 
            imagen[i][j] = dis(gen);

    // Seleccionar filtro
    switch(op) {
        case 1: kernel = {{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}}; break;
        case 2: kernel = {{0.11, 0.11, 0.11}, {0.11, 0.11, 0.11}, {0.11, 0.11, 0.11}}; break;
        case 3: kernel = {{0, 0, 0}, {-1, 1, 0}, {0, 0, 0}}; break;
        case 4: kernel = {{-2, -1, 0}, {-1, 1, 1}, {0, 1, 2}}; break;
        case 5: kernel = {{0, 1, 0}, {1, -4, 1}, {0, 1, 0}}; break;
        case 6: kernel = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}}; break;
        case 7: kernel = {{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}}; break;
        case 8: kernel = {{1, 1, 1}, {1, -2, 1}, {-1, -1, -1}}; break;
        case 9: kernel = {{-1, 1, 1}, {-1, -2, 1}, {-1, 1, 1}}; break;
        case 10: kernel = {{0.06, 0.12, 0.06}, {0.12, 0.25, 0.12}, {0.06, 0.12, 0.06}}; break;
        default: kernel = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
    }
}

// --- 2. TAREA DEL HILO ---
void worker(int inicioFila, int finFila) {
    int kAlto = kernel.size();
    int kAncho = kernel[0].size();
    int padH = kAlto / 2;
    int padW = kAncho / 2;

    for (int i = inicioFila; i < finFila; ++i) {
        for (int j = 0; j < N; ++j) {
            double suma = 0.0;
            for (int ki = 0; ki < kAlto; ++ki) {
                for (int kj = 0; kj < kAncho; ++kj) {
                    int imgI = i - padH + ki;
                    int imgJ = j - padW + kj;
                    if (imgI >= 0 && imgI < N && imgJ >= 0 && imgJ < N) {
                        suma += imagen[imgI][imgJ] * kernel[ki][kj];
                    }
                }
            }
            resultado[i][j] = suma;
        }
    }
}

// --- 3. MAIN ---
int main() {
    int nInput, op, numHilos;

    cout << "--- CONVOLUCION PARALELA C++ ---" << endl;
    cout << "Tamano de la matriz (N): "; cin >> nInput;
    cout << "Numero de hilos: "; cin >> numHilos;
    cout << "Filtro (1-10): "; cin >> op;

    cout << "Generando datos..." << endl;
    generarDatos(nInput, op);

    cout << "Iniciando..." << endl;
    auto inicio = chrono::high_resolution_clock::now();

    // A. Gestión de hilos
    vector<thread> hilos;
    int filasPorHilo = N / numHilos;

    for (int i = 0; i < numHilos; ++i) {
        int inicio = i * filasPorHilo;
        int fin = (i == numHilos - 1) ? N : (inicio + filasPorHilo);
        
        // Creamos el hilo y lo añadimos al vector
        hilos.emplace_back(worker, inicio, fin);
    }

    // B. Join (Esperar a todos)
    for (auto& t : hilos) {
        t.join();
    }

    auto fin = chrono::high_resolution_clock::now();
    chrono::duration<double, milli> duracion = fin - inicio;

    cout << "Tiempo Total (Paralelo): " << duracion.count() << " ms" << endl;

    return 0;
}