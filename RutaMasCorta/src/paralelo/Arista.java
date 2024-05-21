package paralelo;

public class Arista {
    private Nodo destino;
    private int peso;

    public Arista(Nodo destino, int peso) {
        this.destino = destino;
        this.peso = peso;
    }

    public Nodo getDestino() {
        return destino;
    }

    public int getPeso() {
        return peso;
    }
}
