package paralelo;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        
//        for(int i = 0; i < 10; i++)
            ejecutar();
        
    }
    
    public static void ejecutar() {
        int i, j, valorArista, numAristas, nodoVecino;
        final int maxAristasPorNodo = 5;
        final int numeroNodos = 100;
        Random rm = new Random();
        
        Grafo grafo = new Grafo();

        grafo.agregarVertice(1);
        grafo.agregarVertice(2);
        grafo.agregarVertice(3);
        grafo.agregarVertice(4);
        grafo.agregarVertice(5);
        grafo.agregarVertice(6);
        grafo.agregarVertice(7);
        grafo.agregarVertice(8);

        grafo.agregarArista(1, 2, 5);
        grafo.agregarArista(1, 3, 10);
        grafo.agregarArista(2, 4, 2);
        grafo.agregarArista(3, 5, 1);
        grafo.agregarArista(4, 5, 3);
        grafo.agregarArista(6, 3, 1);
        grafo.agregarArista(6, 8, 1);
        grafo.agregarArista(6, 1, 13);
        grafo.agregarArista(7, 6, 1);
        grafo.agregarArista(8, 5, 11);     

/*        for(i = 1; i <= numeroNodos; i++) {
            grafo.agregarVertice(i);
        }
        
        for(Nodo nodo : grafo.getNodos()){
            numAristas = rm.nextInt(maxAristasPorNodo)+1;
            for(j = 0; j < numAristas ; j++){
                nodoVecino = rm.nextInt(grafo.getNodos().size())+1;
                if(nodoVecino != nodo.getId() && ! grafo.existeArista(nodo.getId(), nodoVecino)) {
                    valorArista = rm.nextInt(100)+1;
                    grafo.agregarArista(nodo.getId(), nodoVecino, valorArista);
                }
            }
        }   */


        grafo.imprimirGrafo();

        int idInicial = 1;
        int numHilos = 10;
        
        long tiempoInicial = System.nanoTime();
        
        Map<Integer, Integer> distancias = grafo.dijkstra(idInicial, numHilos);
        
        long tiempoFinal = System.nanoTime();
        long tiempoEjecucion = tiempoFinal - tiempoInicial;
        double tiempoEjecucionEnSegundos = tiempoEjecucion / 1000000000.0;

        System.out.println("Distancias desde el nodo " + idInicial + ":");
        for (Map.Entry<Integer, Integer> entrada : distancias.entrySet()) {
            System.out.println("Nodo " + entrada.getKey() + " - Distancia: " + entrada.getValue());
        }   
        
        System.out.println("Algoritmo paralelo:");
        System.out.println("El método tardó " + tiempoEjecucion + " nanosegundos en ejecutarse.");
        System.out.println("El método tardó " + tiempoEjecucionEnSegundos + " segundos en ejecutarse.");
    }
}
