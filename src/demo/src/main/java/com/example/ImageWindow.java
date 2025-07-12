package com.example;

import javax.swing.JFrame;

public class ImageWindow extends JFrame { // הצגת הפאנל ImagePanel
  public static final int WIDTH = 600;
  public static final int HEIGHT = 600;

  public ImageWindow() {
    this.setSize(WIDTH, HEIGHT);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setLayout(null);
    this.add(new ImagePanel(0, 0, WIDTH, HEIGHT));
    this.setVisible(true);
  }

  // אפשר לממש גם דרך כאן עם מתודת מיין וליצור אובייקט של החלון
}