package paralelo;

import java.util.*;
import java.util.concurrent.*;

public class Grafo {
    private List<Nodo> nodos;

    public Grafo() {
        nodos = new LinkedList<>();
    }

    public void agregarVertice(int id) {
        nodos.add(new Nodo(id));
    }
    
    public List<Nodo> getNodos() {
        return nodos;
    }

    public void agregarArista(int id1, int id2, int peso) {
        Nodo nodo1 = obtenerNodoPorId(id1);
        Nodo nodo2 = obtenerNodoPorId(id2);

        if (nodo1 != null && nodo2 != null) {
            nodo1.agregarArista(new Arista(nodo2, peso));
            nodo2.agregarArista(new Arista(nodo1, peso)); // Si el grafo es no dirigido
        }
    }

    private Nodo obtenerNodoPorId(int id) {
        for (Nodo nodo : nodos) {
            if (nodo.getId() == id) {
                return nodo;
            }
        }
        return null;
    }
    
    public boolean existeArista(int idOrigen, int idDestino) {
        Nodo nodoOrigen = obtenerNodoPorId(idOrigen);
        Nodo nodoDestino = obtenerNodoPorId(idDestino);

        if (nodoOrigen == null || nodoDestino == null) {
            return false;
        }

        for (Arista arista : nodoOrigen.getAristas()) {
            if (arista.getDestino().equals(nodoDestino)) {
                return true; // Se encontró la arista
            }
        }

        return false;
    }

    public void imprimirGrafo() {
        for (Nodo nodo : nodos) {
            System.out.println(nodo);
        }
    }

    public Map<Integer, Integer> dijkstra(int idInicial, int numHilos) {
        Nodo inicio = obtenerNodoPorId(idInicial);
        if (inicio == null) {
            throw new IllegalArgumentException("El nodo inicial no existe en el grafo");
        }

        Map<Integer, Integer> distancias = new HashMap<>();
        PriorityQueue<NodoDistancia> colaPrioridad = new PriorityQueue<>(Comparator.comparingInt(nd -> nd.distancia));
        Set<Nodo> visitados = new HashSet<>();

        for (Nodo nodo : nodos) {
            distancias.put(nodo.getId(), Integer.MAX_VALUE);
        }
        distancias.put(idInicial, 0);
        colaPrioridad.add(new NodoDistancia(inicio, 0));

        ExecutorService executor = Executors.newFixedThreadPool(numHilos);

        while (!colaPrioridad.isEmpty()) {
            NodoDistancia actual = colaPrioridad.poll();
            Nodo nodoActual = actual.nodo;

            if (!visitados.add(nodoActual)) {
                continue;
            }

            List<Future<Void>> futures = new ArrayList<>();

            for (Arista arista : nodoActual.getAristas()) {
                Nodo vecino = arista.getDestino();
                if (visitados.contains(vecino)) {
                    continue;
                }

                int nuevaDistancia = distancias.get(nodoActual.getId()) + arista.getPeso();
                Future<Void> future = (Future<Void>) executor.submit(() -> {
                    if (nuevaDistancia < distancias.get(vecino.getId())) {
                        distancias.put(vecino.getId(), nuevaDistancia);
                        colaPrioridad.add(new NodoDistancia(vecino, nuevaDistancia));
                    }
                });

                futures.add(future);
            }

            // Esperar a que finalicen todas las tareas antes de continuar
            for (Future<Void> future : futures) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        executor.shutdown();

        return distancias;
    }

}
