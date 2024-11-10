package com.example;


    class Node {
        private char key;
        private Node right;
        private Node left;
    
        
        public Node(char key) {
            this.key = key;
            this.right = null;
            this.left = null;
        }
    
        
        public void setKey(char key) {
            this.key = key;
        }
    
        
        public void setRight(Node right) {
            this.right = right;
        }
    
       
        public void setLeft(Node left) {
            this.left = left;
        }
    
        
        public char getKey() {
            return this.key;
        }
    
        
        public Node getRight() {
            return this.right;
        }
    
        
        public Node getLeft() {
            return this.left;
        }
    }

