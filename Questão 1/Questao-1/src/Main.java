import Estruturas.*;

public class Main {
    public static void main(String[] args) {
        Palavra p1 = new Palavra("AWAAAAAAAA", 1, 12);
        Palavra p2 = new Palavra("awa2", 1, 13);
        Palavra p3 = new Palavra("awa3", 2, 14);
        Palavra p4 = new Palavra("awa4", 3, 15);
        Palavra p5 = new Palavra("awa5", 3, 17);
        Palavra p6 = new Palavra("awa6", 3, 18);
        Palavra p7 = new Palavra("awa7", 3, 20);

    }

    public static void exibirEmOrdemAVL(ArvoreAVL<Palavra> arvore) {
        exibirEmOrdemAVL(arvore.getRoot());
    }
    private static void exibirEmOrdemAVL(AVLNode<Palavra> node) {
        if(node != null){
            exibirEmOrdemAVL(node.getLeft());
            if(node.getKey() != null){
                System.out.println(node.getKey().toString());
            }
            exibirEmOrdemAVL(node.getRight());
        }
    }

    public static void exibirEmOrdemRB(ArvoreRB<Palavra> arvore) {
        exibirEmOrdem(arvore.raiz);
    }

    private static void exibirEmOrdem(RBNode<Palavra> node) {
        if (node != null && !node.equals(ArvoreRB.nil)) {
            exibirEmOrdem(node.esq);
            if (node.v != null) {
                System.out.println(node.v.toString());
            }
            exibirEmOrdem(node.dir);
        }
    }
}
