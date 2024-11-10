package com.example;

public class Main {
    public static void main(String[] args) {
        Tree rbt = new Tree();
           
        System.out.println("Angka-angka yang dimasukkan ke dalam Tree:");
        rbt.add('5'); 
        rbt.add('3');
        rbt.add('7');
        rbt.add('2');
        rbt.add('4');
        rbt.add('6');
        rbt.add('8');
        rbt.add('9');
        rbt.add('1');
        rbt.add('0');
        System.out.println("\n");
        rbt.printTree();

        // rbt.remove('2');
        // rbt.remove('2');

        // rbt.printTree();

        // rbt.add('2');
       

        // bst.printTree();

        // bst.add('2');

        // bst.printTree();
        
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
