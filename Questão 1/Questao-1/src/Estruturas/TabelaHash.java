package Estruturas;


public abstract class TabelaHash<T extends Comparable<T>> {
    private Integer tam = 10;
    private Integer numItens = 0;
    public T[] tabela;
    private Integer passos;

    @SuppressWarnings("unchecked")
    public TabelaHash() {
        this.tabela = (T[]) new Comparable[tam];
    }

    public abstract T remove(T key);
    public abstract void insere(T key);
    public abstract T busca(T key);

    @SuppressWarnings("unchecked")
    public void dobraTamanho(){
        int novoTam = tam*2;
        T[] novaTabela = (T[]) new Comparable[novoTam];
        if (tam >= 0) System.arraycopy(tabela, 0, novaTabela, 0, tam);
        this.tabela = novaTabela;
        this.tam = novoTam;
    }

    public Integer getTam() {
        return tam;
    }

    public void setTam(Integer tam) {
        this.tam = tam;
    }

    public int hash(T key) {
        return key.hashCode()%getTam();
    }

    public Integer getNumItens() {
        return numItens;
    }

    public void setNumItens(Integer numItens) {
        this.numItens = numItens;
    }

    public Integer getPassos() {
        return passos;
    }

    public void setPassos(Integer passos) {
        this.passos = passos;
    }
}
