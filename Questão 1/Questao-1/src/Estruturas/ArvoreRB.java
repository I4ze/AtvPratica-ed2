package Estruturas;

public class ArvoreRB<T extends Comparable<T>> {
    public RBNode<T> raiz;
    public static final RBNode<?> nil = new RBNode<>(null, false);
    public Integer cont;
    public Integer passos;

    public ArvoreRB() {
        this.raiz = (RBNode<T>) ArvoreRB.nil;
    }

    public ArvoreRB(T v) {
        this.raiz = new RBNode<>(v, false);
    }

    private void rotacao_esq(RBNode<T> x) {
        RBNode<T> y = x.dir;
        x.dir = y.esq;
        if (!y.esq.equals(ArvoreRB.nil))
            y.esq.p = x;
        y.p = x.p;
        if (x.p.equals(ArvoreRB.nil))
            this.raiz = y;
        else if (x.equals(x.p.esq))
            x.p.esq = y;
        else
            x.p.dir = y;
        y.esq = x;
        x.p = y;
    }

    private void rotacao_dir(RBNode<T> x) {
        RBNode<T> y = x.esq;
        x.esq = y.dir;
        if (!y.dir.equals(ArvoreRB.nil))
            y.dir.p = x;
        y.p = x.p;
        if (x.p.equals(ArvoreRB.nil))
            this.raiz = y;
        else if (x.equals(x.p.esq))
            x.p.esq = y;
        else
            x.p.dir = y;
        y.dir = x;
        x.p = y;
    }

    public void adiciona(T n) {
        if (this.raiz.equals(ArvoreRB.nil)) {
            this.raiz = new RBNode<>(n, false);
        } else {
            RBNode<T> a = this.encontra(n);
            if (n.compareTo(a.v) < 0) {
                a.esq = new RBNode<>(n, true);
                a.esq.p = a;
                this.fixaadicao(a.esq);
            } else if (n.compareTo(a.v) > 0) {
                a.dir = new RBNode<>(n, true);
                a.dir.p = a;
                this.fixaadicao(a.dir);
            }
        }
    }

    // Realiza a troca de nós
    public void transplant(RBNode<T> x, RBNode<T> y) {
        if (x.p.equals(ArvoreRB.nil))
            this.raiz = y;
        else if (x.equals(x.p.esq))
            x.p.esq = y;
        else
            x.p.dir = y;
        y.p = x.p;
    }

    private void fixaadicao(RBNode<T> z) {
        RBNode<T> y;
        while (z.p.cor) {
            if (z.p.equals(z.p.p.esq)) {
                y = z.p.p.dir;
                if (y.cor) {
                    z.p.cor = false;
                    y.cor = false;
                    z.p.p.cor = true;
                    z = z.p.p;
                } else {
                    if (z.equals(z.p.dir)) {
                        z = z.p;
                        this.rotacao_esq(z);
                    }
                    z.p.cor = false;
                    z.p.p.cor = true;
                    this.rotacao_dir(z.p.p);
                }
            } else {
                y = z.p.p.esq;
                if (y.cor) {
                    y.cor = z.p.cor = false;
                    z.p.p.cor = true;
                    z = z.p.p;
                } else {
                    if (z.equals(z.p.esq)) {
                        z = z.p;
                        this.rotacao_dir(z);
                    }
                    z.p.cor = false;
                    z.p.p.cor = true;
                    this.rotacao_esq(z.p.p);
                }
            }
        }
        this.raiz.cor = false;
    }

    public void remove(T n) {
        RBNode<T> z = this.encontra(n);
        RBNode<T> x, y = z;
        boolean cordey = y.cor;

        if (z.v.equals(n)) {
            if (z.esq.equals(ArvoreRB.nil)) {
                x = z.dir;
                this.transplant(z, z.dir);
            } else if (z.dir.equals(ArvoreRB.nil)) {
                x = z.esq;
                this.transplant(z, z.esq);
            } else {
                y = z.sucessor();
                cordey = y.cor;
                x = y.dir;

                if (y.p.equals(z))
                    x.p = y;
                else {
                    this.transplant(y, y.dir);
                    y.dir = z.dir;
                    y.dir.p = y;
                }
                this.transplant(z, y);
                y.esq = z.esq;
                y.esq.p = y;
                y.cor = z.cor;
            }

            if (!cordey)
                this.fixaremocao(x);
        }
    }

    private void fixaremocao(RBNode<T> n) {
        RBNode<T> x;

        while (!n.equals(this.raiz) && !n.cor) {
            if (n.equals(n.p.esq)) {
                x = n.p.dir;

                if (x.cor) { // caso 1
                    x.cor = false;
                    n.p.cor = true;
                    this.rotacao_esq(n.p);
                    x = n.p.dir;
                }
                if (!x.esq.cor && !x.dir.cor) { // caso 2
                    x.cor = true;
                    n = n.p;
                } else {
                    if (!x.dir.cor) { // caso 3
                        x.esq.cor = false;
                        x.cor = true;
                        this.rotacao_dir(x);
                        x = n.p.dir;
                    }
                    // caso 4
                    x.cor = n.p.cor;
                    n.p.cor = false;
                    x.dir.cor = false;
                    this.rotacao_esq(n.p);
                    n = this.raiz;
                }
            } else {
                x = n.p.esq;

                if (x.cor) { // caso 1
                    x.cor = false;
                    n.p.cor = true;
                    this.rotacao_dir(n.p);
                    x = n.p.esq;
                }
                if (!x.esq.cor && !x.dir.cor) { // caso 2
                    x.cor = true;
                    n = n.p;
                } else {
                    if (!x.esq.cor) { // caso 3
                        x.dir.cor = false;
                        x.cor = true;
                        this.rotacao_esq(x);
                        x = n.p.esq;
                    }
                    // caso 4
                    x.cor = n.p.cor;
                    n.p.cor = false;
                    x.esq.cor = false;
                    this.rotacao_dir(n.p);
                    n = this.raiz;
                }
            }
        }
        n.cor = false;
    }

    private RBNode<T> encontra(T n) {
        ContaPassos contador = new ContaPassos();
        RBNode<T> retorno = this.raiz.encontra(n, contador);
        this.passos = contador.getPassos();
        return retorno;
    }

    public T busca(T n){
        return busca(this.raiz, n);
    }

    private T busca(RBNode<T> node, T key){
        if(node == null || node.v == null) return null;
        else if(node.v.equals(key)) return node.v;
        else if(node.v.compareTo(key)<0) return busca(node.dir, key);
        else return busca(node.esq, key);
    }

    String color(boolean flag) {
        return (flag) ? "RED" : "BLACK";
    }

}
