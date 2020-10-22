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
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.border.Border;

public class Map extends JApplet {

    private int pointCount = 0; // count number of points

    // array of 2 java.awt.Point references
    private Point points[] = new Point[2];

    private boolean p0Exists = false;
    private boolean p1Exists = false;
    private int r = 8;
    JLabel label1;
    JLabel label2;
    int width_screen;
    int heigh_screen;
    public void init() {

        JPanel p = new JPanel();
        p.setVisible(true);
        
        heigh_screen = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        width_screen = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        label1 = new JLabel("Click several times to create a polygon. Finish by clicking on Point 0.");
        label1.setBackground(Color.CYAN);
        label1.setOpaque(true);
        label1.setVisible(true);
        label1.setPreferredSize(new Dimension(1000, 400));
        label1.setSize(new Dimension(1000, 400));
        label1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        p.setLayout(new FlowLayout());
        p.add(label1);
        Container content = getContentPane();
        content.setLayout(new BorderLayout()); // Used to center the panel
        content.add(p, BorderLayout.SOUTH);

        PaintPanel paintPanel = new PaintPanel(30, 30);
        paintPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        paintPanel.setBounds(5, 5, 500, 500);
        content.add(paintPanel, BorderLayout.CENTER);
        repaint();

        Random rand = new Random(100);
        ScheduledExecutorService service__ = Executors.newSingleThreadScheduledExecutor();
        service__.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                int xx = rand.nextInt(200);
                int yy = rand.nextInt(200);
                paintPanel.setX_point(xx);
                paintPanel.setY_point(yy);
                repaint();
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);

    }

    public class PaintPanel extends JPanel {

        private int x_point;
        private int y_point;

        public void setX_point(int x_point) {
            this.x_point = x_point;
        }

        public void setY_point(int y_point) {
            this.y_point = y_point;
        }

        public PaintPanel(int x, int y) {
            super();
            x_point = x;
            y_point = y;
            this.setBackground(Color.LIGHT_GRAY);
            points[0] = new Point(x_point, y_point);
            repaint();
        }

        public void paint(Graphics g) {
            super.paint(g); // clears drawing area
            g.fillOval(x_point, y_point, 8, 8);

            int line = 10;
            int rowHt = 200 / line;
            
            for (int i = 0; i < line; i++) {
                g.drawLine(0, i * rowHt, 200, i * rowHt);
            }

            int rowWid = 200 / line;
            for (int i = 0; i < line; i++) {
                g.drawLine(i * rowWid, 0, i * rowWid, 200);
            }

        }
    }

}
