package datastructures;

public class BST<T extends Comparable<T>> {
    public static class Node<T> {
        public T data;
        public Node<T> left;
        public Node<T> right;

        public Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private Node<T> root = null;

    public void insert(T data) {
        root = insertRec(root, data);
    }

    private Node<T> insertRec(Node<T> root, T data) {
        if (root == null) {
            return new Node<>(data);
        }
        if (data.compareTo(root.data) <= 0) {
            root.left = insertRec(root.left, data);
        } else {
            root.right = insertRec(root.right, data);
        }
        return root;
    }

    public Node<T> getRoot() {
        return root;
    }

    public DynamicArray<T> getDescendingOrder() {
        DynamicArray<T> list = new DynamicArray<>();
        inOrderDesc(root, list);
        return list;
    }

    private void inOrderDesc(Node<T> node, DynamicArray<T> list) {
        if (node != null) {
            inOrderDesc(node.right, list);
            list.add(node.data);
            inOrderDesc(node.left, list);
        }
    }

    public void printTree() {
        printTreeRec(root, "", false);
    }

    private void printTreeRec(Node<T> node, String prefix, boolean isLeft) {
        if (node != null) {
            System.out.println(prefix + (isLeft ? "├── " : "└── ") + node.data);
            printTreeRec(node.right, prefix + (isLeft ? "│   " : "    "), true);
            printTreeRec(node.left, prefix + (isLeft ? "│   " : "    "), false);
        }
    }
}
