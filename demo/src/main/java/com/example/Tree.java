    package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tree {
    private Node root;

    public Tree() {
        root = null;
    }

    public boolean add(char key) {
        Node newNode = new Node(key);
        if (root == null) {
            root = newNode;
            root.setRed(false); // Root selalu berwarna hitam
            return true;
        }

        Node current = root;
        Node parent = null;

        while (current != null) {
            parent = current;
            if (key < current.getKey()) {
                current = current.getLeft();
            } else if (key > current.getKey()) {
                current = current.getRight();
            } else {
                return false; // Key sudah ada, tidak dapat ditambahkan
            }
        }

        newNode.setParent(parent);
        if (key < parent.getKey()) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }

        fixInsert(newNode);
        return true;
    }

    private void fixInsert(Node node) {
        while (node != root && node.getParent().isRed()) {
            if (node.getParent() == node.getParent().getParent().getLeft()) {
                Node uncle = node.getParent().getParent().getRight();
                if (uncle != null && uncle.isRed()) {
                    node.getParent().setRed(false);
                    uncle.setRed(false);
                    node.getParent().getParent().setRed(true);
                    node = node.getParent().getParent();
                } else {
                    if (node == node.getParent().getRight()) {
                        node = node.getParent();
                        rotateToLeft(node);
                    }
                    node.getParent().setRed(false);
                    node.getParent().getParent().setRed(true);
                    rotateToRight(node.getParent().getParent());
                }
            } else {
                Node uncle = node.getParent().getParent().getLeft();
                if (uncle != null && uncle.isRed()) {
                    node.getParent().setRed(false);
                    uncle.setRed(false);
                    node.getParent().getParent().setRed(true);
                    node = node.getParent().getParent();
                } else {
                    if (node == node.getParent().getLeft()) {
                        node = node.getParent();
                        rotateToRight(node);
                    }
                    node.getParent().setRed(false);
                    node.getParent().getParent().setRed(true);
                    rotateToLeft(node.getParent().getParent());
                }
            }
        }
        root.setRed(false);
    }

    private void rotateToLeft(Node current) {
        Node rightChild = current.getRight();
        current.setRight(rightChild.getLeft());
        if (rightChild.getLeft() != null) {
            rightChild.getLeft().setParent(current);
        }
        rightChild.setParent(current.getParent());
        if (current.getParent() == null) {
            root = rightChild;
        } else if (current == current.getParent().getLeft()) {
            current.getParent().setLeft(rightChild);
        } else {
            current.getParent().setRight(rightChild);
        }
        rightChild.setLeft(current);
        current.setParent(rightChild);
    }

    private void rotateToRight(Node current) {
        Node leftChild = current.getLeft();
        current.setLeft(leftChild.getRight());
        if (leftChild.getRight() != null) {
            leftChild.getRight().setParent(current);
        }
        leftChild.setParent(current.getParent());
        if (current.getParent() == null) {
            root = leftChild;
        } else if (current == current.getParent().getRight()) {
            current.getParent().setRight(leftChild);
        } else {
            current.getParent().setLeft(leftChild);
        }
        leftChild.setRight(current);
        current.setParent(leftChild);
    }

    public boolean isExist(char key) {
        Node current = root;
        while (current != null) {
            if (key < current.getKey()) {
                current = current.getLeft();
            } else if (key > current.getKey()) {
                current = current.getRight();
            } else {
                return true; // Ditemukan
            }
        }
        return false;
    }

    public boolean remove(char key) {
        Node nodeToRemove = searchNode(root, key);
        if (nodeToRemove == null) {
            System.out.println("Key " + key + " tidak ditemukan di tree");
            return false;
        }
    
        deleteNode(nodeToRemove);
        System.out.println("Key " + key + " telah dihapus dari tree");
        return true;
    }
    
    private void deleteNode(Node node) {
        Node replacement = node;
        Node child;
        boolean originalColor = replacement.isRed();
    
        if (node.getLeft() == null) { // Case 1: Node has only a right child or no children
            child = node.getRight();
            transplant(node, node.getRight());
        } else if (node.getRight() == null) { // Case 2: Node has only a left child
            child = node.getLeft();
            transplant(node, node.getLeft());
        } else { // Case 3: Node has two children
            replacement = minValue(node.getRight());
            originalColor = replacement.isRed();
            child = replacement.getRight();
    
            if (replacement.getParent() != node) {
                transplant(replacement, replacement.getRight());
                replacement.setRight(node.getRight());
                replacement.getRight().setParent(replacement);
            }
    
            transplant(node, replacement);
            replacement.setLeft(node.getLeft());
            replacement.getLeft().setParent(replacement);
            replacement.setRed(node.isRed());
        }
    
        if (!originalColor && child != null) {
            fixDelete(child);
        }
    }
    
    private void transplant(Node u, Node v) {
        if (u.getParent() == null) {
            root = v;
        } else if (u == u.getParent().getLeft()) {
            u.getParent().setLeft(v);
        } else {
            u.getParent().setRight(v);
        }
        if (v != null) {
            v.setParent(u.getParent());
        }
    }

    private void fixDelete(Node node) {
        while (node != root && (node == null || !node.isRed())) {
            if (node == node.getParent().getLeft()) {
                Node sibling = node.getParent().getRight();
    
                if (sibling.isRed()) {
                    sibling.setRed(false);
                    node.getParent().setRed(true);
                    rotateToLeft(node.getParent());
                    sibling = node.getParent().getRight();
                }
    
                if ((sibling.getLeft() == null || !sibling.getLeft().isRed()) &&
                    (sibling.getRight() == null || !sibling.getRight().isRed())) {
                    sibling.setRed(true);
                    node = node.getParent();
                } else {
                    if (sibling.getRight() == null || !sibling.getRight().isRed()) {
                        if (sibling.getLeft() != null) sibling.getLeft().setRed(false);
                        sibling.setRed(true);
                        rotateToRight(sibling);
                        sibling = node.getParent().getRight();
                    }
    
                    sibling.setRed(node.getParent().isRed());
                    node.getParent().setRed(false);
                    if (sibling.getRight() != null) sibling.getRight().setRed(false);
                    rotateToLeft(node.getParent());
                    node = root;
                }
            } else {
                Node sibling = node.getParent().getLeft();
    
                if (sibling.isRed()) {
                    sibling.setRed(false);
                    node.getParent().setRed(true);
                    rotateToRight(node.getParent());
                    sibling = node.getParent().getLeft();
                }
    
                if ((sibling.getLeft() == null || !sibling.getLeft().isRed()) &&
                    (sibling.getRight() == null || !sibling.getRight().isRed())) {
                    sibling.setRed(true);
                    node = node.getParent();
                } else {
                    if (sibling.getLeft() == null || !sibling.getLeft().isRed()) {
                        if (sibling.getRight() != null) sibling.getRight().setRed(false);
                        sibling.setRed(true);
                        rotateToLeft(sibling);
                        sibling = node.getParent().getLeft();
                    }
    
                    sibling.setRed(node.getParent().isRed());
                    node.getParent().setRed(false);
                    if (sibling.getLeft() != null) sibling.getLeft().setRed(false);
                    rotateToRight(node.getParent());
                    node = root;
                }
            }
        }
        if (node != null) node.setRed(false);
    }
    
    private Node searchNode(Node root, char key) {
        while (root != null) {
            if (key < root.getKey()) {
                root = root.getLeft();
            } else if (key > root.getKey()) {
                root = root.getRight();
            } else {
                return root;
            }
        }
        return null;
    }

    private Node minValue(Node node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    

    // public void printTree() {
    //     printTree(root, "", true);
    // }

    // private void printTree(Node node, String indent, boolean last) {
    //     if (node != null) {
    //         System.out.print(indent);
    //         if (last) {
    //             System.out.print("R----");
    //             indent += "   ";
    //         } else {
    //             System.out.print("L----");
    //             indent += "|  ";
    //         }
    //         String color = node.isRed() ? "RED" : "BLACK";
    //         System.out.println(node.getKey() + "(" + color + ")");
    //         printTree(node.getLeft(), indent, false);
    //         printTree(node.getRight(), indent, true);
    //     }
    // }

    public void inorder() {
        System.out.print("Inorder traversal: ");
        inorderRec(root);
        System.out.println();
    }

    private void inorderRec(Node node) {
        if (node != null) {
            inorderRec(node.getLeft());
            System.out.print(node.getKey() + " ");
            inorderRec(node.getRight());
        }
    }

    public void preorder() {
        System.out.print("Preorder traversal: ");
        preorderRec(root);
        System.out.println();
    }

    private void preorderRec(Node node) {
        if (node != null) {
            System.out.print(node.getKey() + " ");
            preorderRec(node.getLeft());
            preorderRec(node.getRight());
        }
    }

    public void postorder() {
        System.out.print("Postorder traversal: ");
        postorderRec(root);
        System.out.println();
    }

    private void postorderRec(Node node) {
        if (node != null) {
            postorderRec(node.getLeft());
            postorderRec(node.getRight());
            System.out.print(node.getKey() + " ");
        }
    }
    
    public void printTreePretty() {
        int maxLevel = maxDepth(root); 
        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private void printNodeInternal(List<Node> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || isAllElementsNull(nodes)) return;

        int floor = maxLevel - level;
        int edgeLines = (int) Math.pow(2, Math.max(floor - 1, 0));
        int firstSpaces = (int) Math.pow(2, floor + 1) - 1;
        int betweenSpaces = (int) Math.pow(2, floor + 1) - 1;

        printWhitespaces(firstSpaces);

        List<Node> newNodes = new ArrayList<>();
        for (Node node : nodes) {
            if (node != null) {
                System.out.print(node.getKey() + "(" + (node.isRed() ? "R" : "B") + ")");
                newNodes.add(node.getLeft());
                newNodes.add(node.getRight());
            } else {
                System.out.print("   ");
                newNodes.add(null);
                newNodes.add(null);
            }
            printWhitespaces(betweenSpaces);
        }
        System.out.println();

        for (int i = 1; i <= edgeLines; i++) {
            for (Node node : nodes) {
                printWhitespaces(firstSpaces - i);
                if (node == null) {
                    printWhitespaces(edgeLines + edgeLines + i + 1);
                    continue;
                }

                if (node.getLeft() != null) System.out.print("/");
                else printWhitespaces(1);

                printWhitespaces(i + i - 1);

                if (node.getRight() != null) System.out.print("\\");
                else printWhitespaces(1);

                printWhitespaces(edgeLines + edgeLines - i);
            }
            System.out.println();
        }
        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private void printWhitespaces(int count) {
        for (int i = 0; i < count; i++) System.out.print(" ");
    }

    private int maxDepth(Node node) {
        if (node == null) return 0;
        return Math.max(maxDepth(node.getLeft()), maxDepth(node.getRight())) + 1;
    }

    private boolean isAllElementsNull(List<Node> list) {
        for (Node node : list) {
            if (node != null) return false;
        }
        return true;
    }
}
