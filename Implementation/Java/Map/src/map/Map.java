/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import java.awt.*;
import java.applet.*;
import javax.swing.JFrame;
import javax.swing.JApplet;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

public class Map extends JApplet {

    private int pointCount = 0; // count number of points

    // array of 2 java.awt.Point references
    private Point points[] = new Point[2];

    private boolean p0Exists = false;
    private boolean p1Exists = false;
    private int r = 8;
    JLabel label1;
    JLabel label2;

    public void init() {

        JPanel p = new JPanel();

        label1 = new JLabel("Click several times to create a polygon. Finish by clicking on Point 0.");
        label1.setBackground(Color.CYAN);
        label1.setOpaque(true);
        label1.setVisible(true);
        label1.setPreferredSize(new Dimension(1000, 400));
        label1.setSize(new Dimension(1000, 400));
        p.setLayout(new FlowLayout());
        p.add(label1);
        Container content = getContentPane();
        content.setLayout(new BorderLayout()); // Used to center the panel
        content.add(p, BorderLayout.SOUTH);

        PaintPanel paintPanel = new PaintPanel();
        content.add(paintPanel, BorderLayout.CENTER);

    }

    public class PaintPanel extends JPanel {

        // set up GUI and register mouse event handler
        public PaintPanel() {
            super();
            this.setBackground(Color.LIGHT_GRAY);

            // handle frame mouse  event
            addMouseListener(
                    new MouseAdapter() // anonymous inner class
            {
                // define a point and repaint
                public void mousePressed(MouseEvent event) {
                    if (!p0Exists) {
                        // create point0
                        points[0] = new Point(event.getX(), event.getY());
                        System.out.println("Point 0 created: " + points[0]);
                        p0Exists = true;
                        repaint();
                    } else if (!p1Exists) {
                        // create point1
                        points[1] = new Point(event.getX(), event.getY());
                        System.out.println("Point 1 created: " + points[1]);
                        p1Exists = true;
                        repaint();
                    }

                } // end method mousePressed

            } // end anonymous inner class
            ); // end call to addMouseMotionListener
        } // end PaintPanel constructor

        public void paint(Graphics g) {
            super.paint(g); // clears drawing area
            if (p0Exists) {
                g.drawOval(points[0].x - r / 2, points[0].y - r / 2, r, r);
                g.drawString("Point0", points[0].x, points[0].y - 3 * r);
                g.drawString("x=" + points[0].x + " y=" + points[0].y,
                        points[0].x, points[0].y - r);
                //g.drawLine(points[ 0 ].x - r/2, points[ 0 ].y - r/2, r, r);
            }
            if (p1Exists) {
                g.drawOval(points[1].x - r / 2, points[1].y - r / 2, r, r);
                g.drawString("Point1", points[1].x, points[1].y - 3 * r);
                g.drawString("x=" + points[1].x + " y=" + points[1].y,
                        points[1].x, points[1].y - r);
            }
            if (p0Exists && p1Exists) {
                g.drawLine(points[0].x, points[0].y, points[1].x, points[1].y);
            }
            
            int rows = 20;
            int cols = 20;
            
//            int rowHt = height / (rows);
//            for (int i = 0; i < rows; i++) {
//                g.drawLine(0, i * rowHt, width, i * rowHt);
//            }
//
//            // draw the columns
//            int rowWid = width / (cols);
//            for (int i = 0; i < cols; i++) {
//                g.drawLine(i * rowWid, 0, i * rowWid, height);
//            }
        } 
    }

}
