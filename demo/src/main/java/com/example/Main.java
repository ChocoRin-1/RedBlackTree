package com.example;

public class Main {
    public static void main(String[] args) {
        Tree rbt = new Tree();
           
        System.out.println("Angka-angka yang dimasukkan ke dalam Tree:");
        rbt.add('A'); 
        rbt.add('B');
        rbt.add('C');
        rbt.add('D');
        rbt.add('E');
        rbt.add('F');
        rbt.add('G');
        rbt.add('H');
        rbt.add('I');
        rbt.add('J');
        System.out.println("\n");
        rbt.printTreePretty();

        rbt.remove('L');
        rbt.remove('A');
        System.out.println("\n");

        rbt.printTreePretty();

        rbt.find('J');

        // rbt.add('C');
        // System.out.println("\n");
        // rbt.printTreePretty();
        
        long startInorder = System.nanoTime();
        rbt.inorder();
        long endInorder = System.nanoTime();
        double timeInorder = (endInorder - startInorder) / 1_000_000_000.0;
        System.out.println("Waktu eksekusi inorder traversal: " + timeInorder + " detik\n");

        
        long startPreorder = System.nanoTime();
        rbt.preorder();
        long endPreorder = System.nanoTime();
        double timePreorder = (endPreorder - startPreorder) / 1_000_000_000.0;
        System.out.println("Waktu eksekusi preorder traversal: " + timePreorder + " detik\n");

        
        long startPostorder = System.nanoTime();
        rbt.postorder();
        long endPostorder = System.nanoTime();
        double timePostorder = (endPostorder - startPostorder) / 1_000_000_000.0;
        System.out.println("Waktu eksekusi postorder traversal: " + timePostorder + " detik");
    }
}
