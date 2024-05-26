package Estruturas;

public class ArvoreAVL<T extends Comparable<T>> {
    private AVLNode<T> root;
    private Integer fb = 2;

    public T search(T key) {
        AVLNode<T> node = root;
        node = search(node, key);
        if (node == null) return null;
        else return node.getKey();
    }

    private AVLNode<T> search(AVLNode<T> node, T key) {
        if (node == null) return null;
        else if (node.getKey().equals(key)) return node;
        else if (node.getKey().compareTo(key) < 0) return search(node.getRight(), key);
        else return search(node.getLeft(), key);
    }

    public AVLNode<T> getRoot() {
        return root;
    }

    public void insert(T key) {
        root = insert(root, key);
    }

    private AVLNode<T> insert(AVLNode<T> node, T nodeKey) {
        if (node == null) {
            return new AVLNode<>(nodeKey); // Cria um nó como folha
        } else if (node.getKey().compareTo(nodeKey) > 0) {
            node.setLeft(insert(node.getLeft(), nodeKey));
        } else if (node.getKey().compareTo(nodeKey) < 0) {
            node.setRight(insert(node.getRight(), nodeKey));
        } else {
            return node;
        }
        // Retorna nó realizando o balanceamento da subarvore se necessario
        return balancedAVLNode(node);
    }

    public void remove(T key) {
        root = remove(root, key);
    }

    private AVLNode<T> remove(AVLNode<T> node, T key) {
        if (node == null) {
            return node;
        }
        if (node.getKey().compareTo(key) < 0) {
            node.setRight(remove(node.getRight(), key));
        } else if (node.getKey().compareTo(key) > 0) {
            node.setLeft(remove(node.getLeft(), key));
        } else {
            node = removeRoot(node);
        }
        // Retorna nó realizando o balanceamento da subarvore se necessario
        return balancedAVLNode(node);
    }

    private AVLNode<T> removeRoot(AVLNode<T> node) {
        // Se o nó nao tem filhos
        if (node.getHeight() == 0) return null;

        AVLNode<T> newNode;
        // Busca um substituto na ramificacao de maior altura para nao desbalancear a arvore
        if (node.getLeft() != null && (node.getRight() == null || node.getLeftHeight() > node.getRightHeight())) {
            newNode = new AVLNode<>(getHighestKey(node.getLeft()));
            newNode.setLeft(remove(node.getLeft(), newNode.getKey()));
            newNode.setRight(node.getRight());
        } else {
            newNode = new AVLNode<>(getLowestKey(node.getRight()));
            newNode.setLeft(node.getLeft());
            newNode.setRight(remove(node.getRight(), newNode.getKey()));
        }
        return balancedAVLNode(newNode); // Balanceia antes de retornar
    }

    private AVLNode<T> balancedAVLNode(AVLNode<T> node) {
        if (node == null) return node; // Nao ha necessidade de balanceamento

        // Atualiza a altura do node e suas ramificacoes
        node.updateHeight();
        // Fator de balanceamento
        int balance = node.getLeftHeight() - node.getRightHeight();

        // Identifica qual tipo de rotacao deve ser feita para balancear a arvore
        if (balance <= (fb * -1)) {
            if (node.getRight() != null && node.getRight().getRightHeight() > node.getRight().getLeftHeight()) {
                node = rotateLeft(node);
            } else {
                if (node.getRight() != null) {
                    node.setRight(rotateRight(node.getRight()));
                }
                node = rotateLeft(node);
            }
        } else if (balance >= fb) {
            if (node.getLeft() != null && node.getLeft().getLeftHeight() > node.getLeft().getRightHeight()) {
                node = rotateRight(node);
            } else {
                if (node.getLeft() != null) {
                    node.setLeft(rotateLeft(node.getLeft()));
                }
                node = rotateRight(node);
            }
        }
        return node;
    }

    private AVLNode<T> rotateLeft(AVLNode<T> node) {
        AVLNode<T> newRoot = node.getRight();
        if (newRoot == null) return node; // Evita null pointer exception
        AVLNode<T> newRight = newRoot.getLeft();
        newRoot.setLeft(node);
        node.setRight(newRight);
        node.updateHeight();
        newRoot.updateHeight();
        return newRoot;
    }

    private AVLNode<T> rotateRight(AVLNode<T> node) {
        AVLNode<T> newRoot = node.getLeft();
        if (newRoot == null) return node; // Evita null pointer exception
        AVLNode<T> newLeft = newRoot.getRight();
        newRoot.setRight(node);
        node.setLeft(newLeft);
        node.updateHeight();
        newRoot.updateHeight();
        return newRoot;
    }

    private T getLowestKey(AVLNode<T> node) {
        if (node == null || node.getLeft() == null) return node != null ? node.getKey() : null;
        return getLowestKey(node.getLeft());
    }

    private T getHighestKey(AVLNode<T> node) {
        if (node == null || node.getRight() == null) return node != null ? node.getKey() : null;
        return getHighestKey(node.getRight());
    }

    public Integer getFb() {
        return fb;
    }

    public void setFb(Integer fb) {
        this.fb = fb;
    }
}
