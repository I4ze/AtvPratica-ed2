package Estruturas;

public class RBNode <T> {
    private static final boolean VERMELHO = true;
    private static final boolean PRETO = false;

    RBNode<T> left, right, pai;
    T elm;
    boolean cor;

    public RBNode(RBNode<T> left, RBNode<T> right,RBNode<T> pai, T elm) {
        this.pai = pai;
        this.left = left;
        this.right = right;
        this.elm = elm;
        this.cor = VERMELHO;
    }
}
