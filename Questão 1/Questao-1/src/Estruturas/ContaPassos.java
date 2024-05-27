package Estruturas;

public class ContaPassos {
    private Integer passos;

    public ContaPassos() {
        this.passos = 0;
    }

    public void incremento(Integer valor) {
        passos += valor;
    }

    public int getPassos() {
        return passos;
    }

    public void reset() {
        passos = 0;
    }
}
