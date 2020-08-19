/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

/**
 *
 * @author ohbao
 */
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;

class GridsCanvas extends JPanel {

    int width, height;

    int rows;

    int cols;

    GridsCanvas(int w, int h, int r, int c) {
        setSize(width = w, height = h);
        rows = r;
        cols = c;
    }

    @Override
    public void paint(Graphics g) {
        int i;
        width = getSize().width;
        height = getSize().height;

        // draw the rows
        int rowHt = height / (rows);
        for (i = 0; i < rows; i++) {
            g.drawLine(0, i * rowHt, width, i * rowHt);
        }

        // draw the columns
        int rowWid = width / (cols);
        for (i = 0; i < cols; i++) {
            g.drawLine(i * rowWid, 0, i * rowWid, height);
        }
    }
}

public class Map1 extends JFrame {

    private Point points[] = new Point[2];

    private boolean p0Exists = false;
    private boolean p1Exists = false;
    private int r = 8;

    public Map1() {

        GridsCanvas xyz = new GridsCanvas(200, 200, 10, 10);
        add(xyz);

        pack();
        points[0] = new Point(50, 50);
        repaint();
    }

    public static void main(String[] a) {
        new Map1().setVisible(true);
    }

}
