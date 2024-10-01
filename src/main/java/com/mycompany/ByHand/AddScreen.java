package com.mycompany.ByHand;

import javax.swing.JFrame;

public class AddScreen extends JFrame {
    public AddScreen() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Adicionar Produto");
        setResizable(false);
        setSize(new java.awt.Dimension(800, 800));
        getContentPane().setLayout(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new AddScreen();
    }

}
