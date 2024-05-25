package Estruturas;

public class ArvoreRB <T extends Comparable<T>>{
    private static final boolean VERMELHO = true;
    private static final boolean PRETO = false;
    RBNode<T> raiz;

    private RBNode<T> buscaNaArvore(RBNode<T> raiz, T receivedElm){
        if(raiz != null){
            if (raiz.elm.equals(receivedElm)) {
                return raiz;
            } else if (raiz.elm.compareTo(receivedElm) < 0) {
                return buscaNaArvore(raiz.right, receivedElm);
            } else {
                return buscaNaArvore(raiz.left, receivedElm);
            }
        }
        return null;
    }

    private RBNode<T> insereNaArvore(RBNode<T> raiz, T receivedElm){
        if(raiz == null){
            return new RBNode<T>(null, null, null, receivedElm);
        }

        if(raiz.elm.compareTo(receivedElm) < 0){
            raiz.right = insereNaArvore(raiz.right, receivedElm);
        }else if(raiz.elm.compareTo(receivedElm) > 0){
            raiz.left = insereNaArvore(raiz.left, receivedElm);
        }

        if(ehPRETO(raiz.left) && ehVERMELHO(raiz.right)){
            raiz = rotacaoEsquerda(raiz);
        }
        if(ehVERMELHO(raiz.left) && ehVERMELHO(raiz.right.right)){
            raiz = rotacaoDireita(raiz);
        }
        if(ehVERMELHO(raiz.left) && ehVERMELHO(raiz.right)){
            inverteCores(raiz);
        }

        return raiz;
    }



    private void inverteCores(RBNode<T> node){
        node.cor = !node.cor;
        node.left.cor = !node.left.cor;
        node.right.cor = !node.right.cor;
    }

    private RBNode<T> rotacaoEsquerda(RBNode<T> node){
        RBNode<T> nodeDir = node.right;
        node.right = nodeDir.left;
        nodeDir.left = node;
        nodeDir.cor = node.cor;
        node.cor = VERMELHO;
        return nodeDir;
    }

    private RBNode<T> rotacaoDireita(RBNode<T> node){
        RBNode<T> nodeEsq = node.left;
        node.left = nodeEsq.right;
        nodeEsq.right = node;
        nodeEsq.cor = node.cor;
        node.cor = VERMELHO;
        return nodeEsq;
    }

    public boolean ehVERMELHO(RBNode<?> node) {
        if (node == null) return false;
        return node.cor == VERMELHO;
    }

    public boolean ehPRETO(RBNode<?> node){
        if(node == null) return true;
        return node.cor == PRETO;
    }


}
