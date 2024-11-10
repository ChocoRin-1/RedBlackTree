package com.example;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

class Tree {
    private Node root;

    
    public Tree() {
        this.root = null;
    }

    
    public boolean add(char key) {
        if (root == null) {
            root = new Node(key);
            System.out.println("root dengan angka" + key + "telah ditambahkan");
            return true;
        }
        System.out.println("anakan dengan angka" + key + "telah ditambahkan");
        return addRec(root, key);
    }

    private boolean addRec(Node current, char key) {
        if (key < current.getKey()) {
            if (current.getLeft() == null) {
                current.setLeft(new Node(key));
                return true;
            } else {
                return addRec(current.getLeft(), key);
            }
        } else if (key > current.getKey()) {
            if (current.getRight() == null) {
                current.setRight(new Node(key));
                return true;
            } else {
                return addRec(current.getRight(), key);
            }
        }
        return false; 
    }

    
    public boolean remove(char key) {
        if (!isExist(key)) {
            System.out.println("Node dengan key '" + key + "' tidak ditemukan di tree.");
            return false; 
        }
        root = removeRec(root, key);
        System.out.println("Node dengan key '" + key + "' berhasil dihapus.");
        return true;
    }

    private Node removeRec(Node root, char key) {
        if (root == null) return null;

        if (key < root.getKey()) {
            root.setLeft(removeRec(root.getLeft(), key));
        } else if (key > root.getKey()) {
            root.setRight(removeRec(root.getRight(), key));
        } else {
            
            if (root.getLeft() == null) return root.getRight();
            else if (root.getRight() == null) return root.getLeft();

            
            Node minRightNode = minValue(root.getRight());
            root.setKey(minRightNode.getKey());
            root.setRight(removeRec(root.getRight(), minRightNode.getKey()));
        }
        return root;
    }

    private Node minValue(Node node) {
        while (node.getLeft() != null) node = node.getLeft();
        return node;
    }

   
    public boolean isExist(char key) {
        return isExistRec(root, key);
    }

    private boolean isExistRec(Node current, char key) {
        if (current == null) return false;
        if (key == current.getKey()) return true;
        return key < current.getKey() ? isExistRec(current.getLeft(), key) : isExistRec(current.getRight(), key);
    }

    
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
        int firstSpaces = (int) Math.pow(2, floor) - 1;
        int betweenSpaces = (int) Math.pow(2, floor + 1) - 1;

        printWhitespaces(firstSpaces);

        List<Node> newNodes = new ArrayList<>();
        for (Node node : nodes) {
            if (node != null) {
                System.out.print(node.getKey());
                newNodes.add(node.getLeft());
                newNodes.add(node.getRight());
            } else {
                System.out.print(" ");
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
    
    
    

