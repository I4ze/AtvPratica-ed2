package Estruturas;

public class RBNode<T extends Comparable<T>> {
    public T v;
    public RBNode<T> p, esq, dir;
    public boolean cor;

    public RBNode(T n, boolean cor) {
        this.v = n;
        this.cor = cor;
        this.p = this.esq = this.dir = (RBNode<T>) ArvoreRB.nil;
    }

    // Busca o nó que será o pai do proximo nó a ser inserido
    public RBNode<T> encontra(T n, ContaPassos contador) {
        if ((n.compareTo(this.v) < 0) && !this.esq.equals(ArvoreRB.nil)) {
            contador.incremento(2);//incrementa dois passos: o da comparação e a movimentação para o proximo nó
            return this.esq.encontra(n, contador);
        } else if ((n.compareTo(this.v) > 0) && !this.dir.equals(ArvoreRB.nil)) {
            contador.incremento(3);//incrementa três passos: o da comparação anterior, o da atual e a movimentação para o proximo nó
            return this.dir.encontra(n, contador);
        }
        else {
            contador.incremento(2);//
            return this;
        }
    }

    // Busca o menor valor da árvore
    public RBNode<T> minimo() {
        if (!this.esq.equals(ArvoreRB.nil)) return esq.minimo();
        else return this;
    }

    public RBNode<T> sucessor() {
        if (!this.dir.equals(ArvoreRB.nil)) return this.dir.minimo();
        else return this;
    }
}
