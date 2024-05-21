package secuencial;

import java.util.*;

class Nodo {
    private int id;
    private List<Arista> aristas;

    public Nodo(int id) {
        this.id = id;
        this.aristas = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public List<Arista> getAristas() {
        return aristas;
    }

    public void agregarArista(Arista arista) {
        aristas.add(arista);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(": ");
        for (Arista arista : aristas) {
            sb.append("(").append(arista.getDestino().getId()).append(", ").append(arista.getPeso()).append(") ");
        }
        return sb.toString();
    }
}