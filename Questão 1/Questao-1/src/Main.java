import Estruturas.ArvoreRB;
import Estruturas.Palavra;
import Estruturas.RBNode;

public class Main {
    public static void main(String[] args) {
        Palavra p1 = new Palavra("AWAAAAAAAA", 1);
        Palavra p2 = new Palavra("awa2", 1);
        Palavra p3 = new Palavra("awa3", 2);
        Palavra p4 = new Palavra("awa4", 3);
        ArvoreRB<Palavra> arvore = new ArvoreRB<Palavra>();
        arvore.adiciona(p1);
        exibirEmOrdem(arvore);
        System.out.println();

        arvore.adiciona(p2);
        exibirEmOrdem(arvore);
        System.out.println();

        arvore.adiciona(p3);
        exibirEmOrdem(arvore);
        System.out.println();

        arvore.adiciona(p4);
        exibirEmOrdem(arvore);
        System.out.println();
        System.out.println(arvore.encontra(p2).v.toString()+arvore.passos);
        System.out.println(arvore.encontra(p3).v.toString()+arvore.passos);

    }

    public static void exibirEmOrdem(ArvoreRB<Palavra> arvore) {
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
