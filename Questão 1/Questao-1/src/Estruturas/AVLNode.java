package Estruturas;

public class AVLNode<T extends Comparable<T>> {
    private T key;
    private Integer height;
    private AVLNode<T> left;
    private AVLNode<T> right;

    public AVLNode(T key) {
        this.key = key;
    }

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public int getHeight() {
        this.height = 1 + Math.max(getLeftHeight(), getRightHeight());
        return height;
    }

    public int getLeftHeight() {
        return left == null ? -1 : left.getHeight();
    }

    public int getRightHeight() {
        return right == null ? -1 : right.getHeight();
    }

    public AVLNode<T> getLeft() {
        return left;
    }

    public void setLeft(AVLNode<T> left) {
        this.left = left;
    }

    public AVLNode<T> getRight() {
        return right;
    }

    public void setRight(AVLNode<T> right) {
        this.right = right;
    }

    public void updateHeight() {
        this.height = 1 + Math.max(getLeftHeight(), getRightHeight());
    }

}
