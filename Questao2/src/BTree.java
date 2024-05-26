import java.io.*;
import java.util.concurrent.ConcurrentMap;

public class BTree<T extends Comparable<T>> {
    int t;
    BTreeNode<T> root;

    public BTree(int t) {
        this.root = null;
        this.t = t;
    }
    public void saveTreeToFile(String filename) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            saveNode(out, root);
        } catch (IOException e) {
            System.err.println("Erro ao salvar a árvore B: " + e.getMessage());
        }
    }

    private void saveNode(PrintWriter out, BTreeNode node) {
        if (node == null) {
            return;
        }

        out.print(node.n);  // Escreve o número de chaves
        for (Comparable<T> key : node.key) {
            out.print(" " + key);  // Escreve cada chave
        }

        out.print(" |");
        if (!node.leaf) {
            for (BTreeNode child : node.child) {
                if (child != null) {
                    out.print(" " + child);
                }
            }
        }
        out.println();  // Nova linha após cada nó

        if (!node.leaf) {
            for (BTreeNode child : node.child) {
                saveNode(out, child);  // Recursivamente salva cada filho
            }
        }
    }



    // search

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
//    public BTreeNode<Usuario> BTreeSearch(BTreeNode<Usuario> node, int k) {
//        if (node == null) {
//            return null;
//        }
//        int i = 0;
//        while (i < node.n && node.key[i].id_usuario < k) {
//            i++;
//        }
//        if (i < node.n && node.key[i].id_usuario == k) {
//            return node;
//        }
//        if (node.leaf) {
//            return null;
//        }
//        return BTreeSearch(node.child[i], k);
//    }

    // insert
    public void insert(T k) {
        if (root == null) { // Se não houver nenhum elemento na raiz
            root = new BTreeNode<>(t, true);
            root.key[0] = k; // Assumindo que key é um array de T
            root.n = 1;
        } else {
            if (root.n == 2 * t - 1) { // Se a raiz estiver cheia
                BTreeNode<T> s = new BTreeNode<>(t, false);
                s.child[0] = root;
                s.splitChild(0, root);
                int i = 0;
                if (s.key[0].compareTo(k) < 0) {
                    i++;
                }
                s.child[i].InsertNonFull(k);
                root = s;
            } else {
                root.InsertNonFull(k);
            }
        }
    }
    // delete
    public void remove(BTreeNode<T> x, T key) {
        int pos = findKey(x, key); // A função que encontra a posição da chave

        if (pos != -1) { // Chave encontrada no nó x
            if (x.leaf) { // Se é um nó folha, simplesmente remove a chave
                for (int i = pos; i < x.n - 1; i++) {
                    x.key[i] = x.key[i + 1]; // Desloca todas as chaves após 'pos' uma posição para trás
                }
                x.n--; // Decrementa a contagem de chaves no nó
            } else { // Nó interno
                BTreeNode<T> pred = x.child[pos]; // Predecessor
                T predKey = getPredKey(pred); // Encontra a maior chave no subárvore do predecessor
                if (pred.n >= t) {
                    remove(pred, predKey); // Remove recursivamente
                    x.key[pos] = predKey; // Substitui a chave removida pela chave predecessor
                } else {
                    BTreeNode<T> succ = x.child[pos + 1]; // Sucessor
                    T succKey = getSuccKey(succ); // Encontra a menor chave no subárvore do sucessor
                    if (succ.n >= t) {
                        remove(succ, succKey); // Remove recursivamente
                        x.key[pos] = succKey; // Substitui pela chave sucessor
                    } else {
                        merge(x, pos); // Se ambos os filhos têm t-1 chaves, mescla-os
                        remove(pred, key); // Remove a chave do nó mesclado
                    }
                }
            }
        } else { // Chave não encontrada, procura no filho apropriado
            pos = findChild(x, key); // Encontra o filho correto onde a chave pode existir
            BTreeNode<T> child = x.child[pos];
            if (child.n == t - 1) { // Se o filho tem apenas t-1 chaves, ajusta antes da recursão
                fill(x, pos); // Preenche o nó filho para ter pelo menos 't' chaves
            }
            remove(child, key); // Chama recursivamente para remover a chave
        }
    }
    int findChild(BTreeNode<T> x, T key) {
        // Assumindo que as chaves são mantidas em ordem crescente
        for (int i = 0; i < x.n; i++) {
            if (x.key[i].compareTo(key) > 0) {
                return i;  // Retorna o índice do filho antes do qual a chave deve estar
            }
        }
        return x.n;  // A chave é maior que todas as chaves no nó, siga o último filho
    }
    int findKey(BTreeNode<T> x, T key) {
        int pos = 0;
        while (pos < x.n && x.key[pos].compareTo(key) < 0) {
            pos++;
        }
        return pos;
    }
    T getPredKey(BTreeNode<T> node) {
        BTreeNode<T> current = node;
        while (!current.leaf) {
            current = current.child[current.n]; // Vai para o filho mais à direita
        }
        return current.key[current.n - 1]; // Retorna a última chave do nó folha mais à direita
    }
    T getSuccKey(BTreeNode<T> node) {
        BTreeNode<T> current = node;
        while (!current.leaf) {
            current = current.child[0]; // Vai para o filho mais à esquerda
        }
        return current.key[0]; // Retorna a primeira chave do nó folha mais à esquerda
    }
    void fill(BTreeNode<T> x, int pos) {
        if (pos > 0 && x.child[pos - 1].n >= t) {
            borrowFromPrev(x, pos);
        } else if (pos < x.n && x.child[pos + 1].n >= t) {
            borrowFromNext(x, pos);
        } else {
            if (pos < x.n) {
                merge(x, pos);
            } else {
                merge(x, pos - 1);
            }
        }
    }
    void merge(BTreeNode<T> x, int pos) {
        BTreeNode<T> child = x.child[pos];
        BTreeNode<T> sibling = x.child[pos + 1];

        // Puxa a chave do nó pai e insere no final do filho à esquerda
        child.key[child.n++] = x.key[pos];

        // Copia as chaves e filhos do sibling para child
        for (int i = 0; i < sibling.n; ++i) {
            child.key[child.n + i] = sibling.key[i];
        }
        if (!child.leaf) {
            for (int i = 0; i <= sibling.n; ++i) {
                child.child[child.n + i] = sibling.child[i];
            }
        }

        // Move as chaves e filhos do pai para preencher o espaço deixado pela chave movida
        for (int i = pos; i < x.n - 1; ++i) {
            x.key[i] = x.key[i + 1];
            x.child[i + 1] = x.child[i + 2];
        }
        x.n--;

        child.n += sibling.n; // Atualiza o número de chaves em child
    }
    void borrowFromPrev(BTreeNode<T> x, int pos) {
        BTreeNode<T> child = x.child[pos];
        BTreeNode<T> sibling = x.child[pos - 1];

        // Move todas as chaves e filhos de child uma posição à frente
        for (int i = child.n - 1; i >= 0; --i) {
            child.key[i + 1] = child.key[i];
        }
        if (!child.leaf) {
            for (int i = child.n; i >= 0; --i) {
                child.child[i + 1] = child.child[i];
            }
        }
        // Empresta a última chave de sibling para child
        child.key[0] = x.key[pos - 1];
        if (!sibling.leaf) {
            child.child[0] = sibling.child[sibling.n];
        }

        // A última chave de sibling é movida para o pai
        x.key[pos - 1] = sibling.key[sibling.n - 1];

        child.n += 1;
        sibling.n -= 1;
    }

    void borrowFromNext(BTreeNode<T> x, int pos) {
        BTreeNode<T> child = x.child[pos];
        BTreeNode<T> sibling = x.child[pos + 1];

        // A primeira chave do sibling é movida para a última chave de child
        child.key[child.n] = x.key[pos]; // A chave do pai é inserida como última chave de child

        // O primeiro filho do sibling é movido para ser o último filho de child, se não for uma folha
        if (!child.leaf) {
            child.child[child.n + 1] = sibling.child[0];
        }

        // A primeira chave do sibling é promovida ao pai
        x.key[pos] = sibling.key[0];

        // Mover todas as chaves do sibling uma posição para trás
        for (int i = 1; i < sibling.n; ++i) {
            sibling.key[i - 1] = sibling.key[i];
        }

        // Mover os filhos do sibling uma posição para trás, se não for uma folha
        if (!sibling.leaf) {
            for (int i = 1; i <= sibling.n; ++i) {
                sibling.child[i - 1] = sibling.child[i];
            }
        }

        // Atualizar o número de chaves de child e sibling
        child.n += 1;
        sibling.n -= 1;
    }


    public void Show(BTreeNode<T> x) {
        if (x == null) {
            throw new IllegalArgumentException("Node passed to Show cannot be null");
        }

        // Inicia um espaço em branco para visualização clara
        System.out.println(" ");

        // Exibe todas as chaves no nó atual
        for (int i = 0; i < x.n; i++) {
            System.out.print(x.key[i] + " ");
        }

        // Exibe o caractere | ao final das chaves do nó para indicar transição para os filhos
        if (!x.leaf) {
            System.out.print(" | ");

            // Recursivamente exibe cada filho do nó
            for (int i = 0; i <= x.n; i++) {
                Show(x.child[i]);

                // Adiciona o caractere | entre os filhos para melhor distinção visual
                if (i < x.n) {  // Isso assegura que o caractere | não seja impresso após o último filho
                    System.out.print(" | ");
                }
            }
        }
    }




}
