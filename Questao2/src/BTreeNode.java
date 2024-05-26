import java.io.Serializable;

public class BTreeNode<T extends Comparable<T>> {
    int t; // grau minimo
    int n; // numero de elementos no nó
    T[] key; // vetor com os valores armazenados no nó
    BTreeNode<T>[] child; // filhos do nó
    boolean leaf; // booleano q indica se o nó é folha ou não

    public BTreeNode(int t, boolean leaf){
        this.key = (T[]) new Comparable[2 * t - 1];
        this.t = t;
        this.child = (BTreeNode<T>[]) new BTreeNode[2 * t];
        this.n = 0;
        this.leaf = leaf;

    }

    public void InsertNonFull(T k) {
        int i = n - 1;
        if (leaf) {
            while (i >= 0 && key[i].compareTo(k) > 0) {
                key[i + 1] = key[i];
                i--;
            }
            key[i + 1] = k;
            n++;
        } else {
            while (i >= 0 && key[i].compareTo(k) > 0) {
                i--;
            }
            if (child[i + 1].n == 2 * t - 1) {
                splitChild(i + 1, child[i + 1]);
                if (key[i + 1].compareTo(k) < 0) {
                    i++;
                }
            }
            child[i + 1].InsertNonFull(k);
        }
    }

    void splitChild(int i, BTreeNode<T> nodeFull) {
        // nodeFull: nó filho que precisa ser dividido
        BTreeNode<T> newNode = new BTreeNode<>(nodeFull.t, nodeFull.leaf);
        newNode.n = t - 1;

        // Transferindo chaves da segunda metade de nodeFull para newNode
        for (int j = 0; j < t - 1; j++) {
            newNode.key[j] = nodeFull.key[j + t];
        }

        // Se nodeFull não é uma folha, transfira também seus filhos
        if (!nodeFull.leaf) {
            for (int j = 0; j < t; j++) {
                newNode.child[j] = nodeFull.child[j + t];
            }
        }

        // Ajustando o número de chaves de nodeFull
        nodeFull.n = t - 1;

        // Movendo filhos no nó pai para fazer espaço para o novo nó
        for (int j = n; j > i; j--) {
            child[j + 1] = child[j];
        }
        child[i + 1] = newNode;

        // Movendo as chaves no nó pai para inserir a chave mediana de nodeFull
        for (int j = n - 1; j >= i; j--) {
            key[j + 1] = key[j];
        }

        // Inserindo a chave mediana de nodeFull no nó pai
        key[i] = nodeFull.key[t - 1];
        n++;
    }

    BTreeNode<T> bTreeSearch(BTreeNode<T> node, T k) {
        int i = 0;
        while (i < node.n && k.compareTo(node.key[i]) > 0) {
            i++;
        }
        if (i < node.n && k.compareTo(node.key[i]) == 0) {
            return node; // Chave encontrada
        }
        if (node.leaf) {
            return null; // Chave não encontrada e nó é folha
        }
        return bTreeSearch(node.child[i], k); // Busca recursiva no filho adequado
    }

}
