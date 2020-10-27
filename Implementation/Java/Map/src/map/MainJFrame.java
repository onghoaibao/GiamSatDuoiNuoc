/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javax.swing.JTextArea;
import java.lang.Math;

/**
 *
 * @author ohbao
 */
public class MainJFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainJFrame
     */
    int width;
    int height;
    int width_screen;
    int heigh_screen;
    int x;
    int y;
    int x_old;
    int y_old;
    private Point points[] = new Point[2];
    int width_screen_map;
    int heigh_screen_map;

    private JLabel jLabelTitle;
    private JLabel jLabel_A;
    private JLabel jLabel_B;
    private JLabel jLabel_C;
    private JLabel jLabel_D;

    private JButton jbtConnect;
    private JButton jbtCheckRF;
    private JButton jbtExecute;
    private JTextField jTFArea;
    private JTextArea jtaInFo;
    PaintPanel paintPanel;

    private int iArea;
    private int X_1, X2;
    private int Y_1, Y2;

    private ScheduledExecutorService executorServiceCheckModule;
    private ScheduledExecutorService executorServiceExecution;
    private String rxData = "";
    private static SerialPort comPortMain;
    private boolean isOpenPort = false;

    // Configuation Fram
    private JLabel jLabelPort;
    private JComboBox<String> jComboBoxPort;
    private int iIndexPort = 0;

    void setGuiTemp() {

        jLabelTitle = new JLabel("Chương Trình Giám Sát Đuối Nước");
        jLabelTitle.setBounds(heigh_screen_map + 250, 40, 700, 50);
        //jLabelTitle.setBorder(new javax.swing.border.LineBorder(Color.RED, 1, true));
        jLabelTitle.setFont(new Font("Times New Roman", Font.BOLD, 34));
        jLabelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelTitle.setVerticalAlignment(SwingConstants.CENTER);

        jLabel_A = new JLabel("A");
        jLabel_B = new JLabel("B");
        jLabel_C = new JLabel("C");
        jLabel_D = new JLabel("D");

        jLabel_A.setFont(new Font("Times New Roman", Font.BOLD, 34));
        jLabel_B.setFont(new Font("Times New Roman", Font.BOLD, 34));
        jLabel_C.setFont(new Font("Times New Roman", Font.BOLD, 34));
        jLabel_D.setFont(new Font("Times New Roman", Font.BOLD, 34));

        jLabel_D.setBounds(10, 25, 50, 50);
        jLabel_C.setBounds(width_screen_map + 60, 25, 50, 50);
        jLabel_B.setBounds(width_screen_map + 60, heigh_screen_map, 50, 50);
        jLabel_A.setBounds(10, heigh_screen_map, 50, 50);

        int X_jLabelPort = width_screen_map + 100;
        int Y_jLabelPort = 150;
        jLabelPort = new JLabel("Port");
        jLabelPort.setBounds(X_jLabelPort, Y_jLabelPort, 100, 50);
        jLabelPort.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelPort.setHorizontalAlignment(SwingConstants.LEFT);

        jComboBoxPort = new JComboBox<>();
        jComboBoxPort.setBounds(X_jLabelPort + 120, Y_jLabelPort + 10, 150, 30);;
        jComboBoxPort.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N       

        for (SerialPort com : SerialPort.getCommPorts()) {
            String s = com.getSystemPortName();
            jComboBoxPort.addItem(s);
        }

        JLabel jLabelPort1 = new JLabel("Connect");
        jLabelPort1.setBounds(X_jLabelPort, Y_jLabelPort + 50, 150, 50);
        jLabelPort1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelPort1.setHorizontalAlignment(SwingConstants.LEFT);

        jbtConnect = new JButton("Open");
        jbtConnect.setBounds(X_jLabelPort + 120, Y_jLabelPort + 60, 150, 30);
        jbtConnect.setFont(new Font("Times New Roman", Font.BOLD, 18));

        JLabel jLabelPort2 = new JLabel("Check RF");
        jLabelPort2.setBounds(X_jLabelPort, Y_jLabelPort + 50 + 50, 150, 50);
        jLabelPort2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelPort2.setHorizontalAlignment(SwingConstants.LEFT);

        jbtCheckRF = new JButton("Test");
        jbtCheckRF.setBounds(X_jLabelPort + 120, Y_jLabelPort + 60 + 50, 150, 30);
        jbtCheckRF.setFont(new Font("Times New Roman", Font.BOLD, 18));

        JLabel jLabelPort3 = new JLabel("Execute");
        jLabelPort3.setBounds(X_jLabelPort, Y_jLabelPort + 50 + 50 + 50, 150, 50);
        jLabelPort3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelPort3.setHorizontalAlignment(SwingConstants.LEFT);

        jbtExecute = new JButton("Run");
        jbtExecute.setBounds(X_jLabelPort + 120, Y_jLabelPort + 60 + 50 + 50, 150, 30);
        jbtExecute.setFont(new Font("Times New Roman", Font.BOLD, 18));

        JLabel jLabelPort4 = new JLabel("Area: 20m-50m");
        jLabelPort4.setBounds(X_jLabelPort, Y_jLabelPort + 50 + 50 + 50 + 50, 170, 50);
        jLabelPort4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelPort4.setHorizontalAlignment(SwingConstants.LEFT);

        jTFArea = new JTextField("20");
        jTFArea.setBounds(X_jLabelPort + 170, Y_jLabelPort + 50 + 50 + 50 + 55, 50, 33);
        jTFArea.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTFArea.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel jLabelPort5 = new JLabel("Information");
        jLabelPort5.setBounds(X_jLabelPort, Y_jLabelPort + 50 + 50 + 50 + 50 + 50, 120, 30);
        jLabelPort5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelPort5.setHorizontalAlignment(SwingConstants.LEFT);

        jtaInFo = new JTextArea("The system has not started !!!");
        jtaInFo.setBounds(X_jLabelPort, Y_jLabelPort + 50 + 50 + 50 + 50 + 50 + 40, width_screen - width_screen_map - 150, 300);
        jtaInFo.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jtaInFo.setBorder(new javax.swing.border.LineBorder(Color.BLACK, 1, true));

        add(jLabel_A);
        add(jLabel_B);
        add(jLabel_C);
        add(jLabel_D);
        add(jLabelTitle);
        add(jLabelPort1);
        add(jLabelPort2);
        add(jLabelPort3);
        add(jLabelPort4);
        add(jLabelPort5);
        add(jtaInFo);
        add(jTFArea);
        add(jbtConnect);
        add(jbtCheckRF);
        add(jbtExecute);
        add(jComboBoxPort);
        add(jLabelPort);

        onClickConnect();
        onClickCheck();
        onClickExecution();
    }

    private void onClickConnect() {
        jbtConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isOpenPort == false) {
                    comPortMain = SerialPort.getCommPort(jComboBoxPort.getItemAt(iIndexPort));
                    isOpenPort = comPortMain.openPort();
                    //comPortMain.setComPortTimeouts(SerialPort.LISTENING_EVENT_DATA_RECEIVED, 200, 100);
                    if (isOpenPort) {
                        jbtConnect.setText("Disconnect");
                    } else {
                        JOptionPane.showMessageDialog(MainJFrame.this, "Không Thể Kết Nối Vui Lòng Kiểm Tra Lại !!!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    comPortMain.closePort();
                    jbtConnect.setText("Connect");
                    isOpenPort = false;
                }
            }
        });
    }

    private void onClickCheck() {
        jbtCheckRF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isOpenPort) {
                    SingleThreadReceiveDataCheckModule();
                    comPortMain.writeBytes("TEST".getBytes(), 4);
                } else {
                    JOptionPane.showMessageDialog(MainJFrame.this, "Vui Long Ket Noi Truoc Khi Kiem Tra Module !!!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void onClickExecution() {
        jbtExecute.addActionListener(new ActionListener() {
            private int isConnect = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isConnect == 0) {
                    if (isOpenPort) {
                        isConnect = 1;
                        iArea = convertStringToInt();
                        jbtExecute.setText("Running");
                        SingleThreadReceiveDataExecution();
                        System.out.println("Is start running ");
                    } else {
                        jbtExecute.setText("Run");
                        isConnect = 0;
                        JOptionPane.showMessageDialog(MainJFrame.this, "Vui Long Ket Noi Truoc Khi Kiem Tra Module !!!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    isConnect = 0;
                    jbtExecute.setText("Run");
                    executorServiceExecution.shutdown();
                    System.out.println("Is stop running ");
                }

            }
        });
    }

    public MainJFrame() {

        heigh_screen = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        width_screen = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        System.out.println("width_screen: " + width_screen);
        System.out.println("heigh_screen: " + heigh_screen);
        //distanceBLE(-24.56);
        //calibDistance(-10, -13, -14, -15);

        initComponents();
        getContentPane().setBackground(Color.WHITE);
        width_screen_map = heigh_screen;
        heigh_screen_map = heigh_screen - 150;
        setGuiTemp();

        System.out.println("width_screen_map: " + width_screen_map);
        System.out.println("heigh_screen_map: " + heigh_screen_map);
        paintPanel = new PaintPanel();
        paintPanel.setBounds(50, 40, width_screen_map, heigh_screen_map);
        paintPanel.setBackground(Color.WHITE);
        paintPanel.setBorder(new javax.swing.border.LineBorder(Color.RED, 2, true));
        add(paintPanel);

        X_1 = width_screen_map;
        Y_1 = heigh_screen_map;
        System.out.println("X_1: " + X_1);
        System.out.println("Y_1: " + Y_1);
    }

    private double distanceBLE(int RSSI) {
        double d = 0;
        double A = -12;
        double n = 2.0755;
        double rs_a = RSSI - A;
        double _10n = -10.0 * n;
        double soMu = rs_a / _10n;
        d = Math.round(Math.pow(10, soMu) * 100.0) / 100.0;
        System.out.println("Distance: " + d);
        return d;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(254, 254, 254));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1567, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 673, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    public class PaintPanel extends JPanel {

        private int x_point1;
        private int y_point1;
        private int x_point2;
        private int y_point2;

        public int getX_point(int x1) {
            return x_point1;
        }

        public void setX_point(int x_point1, int x_point2) {
            this.x_point1 = x_point1;
            this.x_point2 = x_point2;
        }

        public int getY_point() {
            return y_point1;
        }

        public void setY_point(int y_point1, int y_point2) {
            this.y_point1 = y_point1;
            this.y_point2 = y_point2;
        }

        public PaintPanel() {
            super();
            this.setBackground(Color.LIGHT_GRAY);
            points[0] = new Point(x_point1, y_point1);
            repaint();
        }

        public void paint(Graphics g) {
            super.paint(g); // clears drawing area
            iArea = convertStringToInt();

            g.fillOval(x_point1, y_point1, 20, 20);
            g.fillOval(x_point2, y_point2, 20, 20);

            g.setColor(Color.red);
            g.setFont(new Font("Courier", Font.BOLD, 13));
            int aT = iArea / (int) (iArea / 10 < 1 ? 1 : iArea / 10);
            int line = aT > 10 ? 10 : aT;
            System.out.println("line = " + line);

            int rowHt = (width_screen_map) / line;
            for (int i = 0; i < line; i++) {
                g.drawLine(i * rowHt, 0, i * rowHt, heigh_screen_map);
                double b = (double) (i * iArea / 10.0);
                if (i == 0) {
                    g.drawString(String.valueOf((double) (i * iArea / 10.0)), i * rowHt + 3, heigh_screen_map - 5);
                } else if (i * iArea / 10 < 10) {
                    g.drawString(String.valueOf((double) (i * iArea / 10.0)), i * rowHt - 10, heigh_screen_map - 5);
                } else if (i * iArea / 10 >= 10) {
                    g.drawString(String.valueOf((double) (i * iArea / 10.0)), i * rowHt - 18, heigh_screen_map - 5);
                }
            }
            g.drawString(String.valueOf((double) (line * iArea / 10.0)), line * rowHt - 20, heigh_screen_map - 5);

            int rowWid = heigh_screen_map / line;
            for (int i = 0; i < line; i++) {
                g.drawLine(0, i * rowWid, width_screen_map, i * rowWid);
                if (i != 0) {
                    g.drawString(String.valueOf((double) ((line - i) * iArea / 10.0)), 3, i * rowWid + 12);
                }
            }
            g.drawString(String.valueOf((double) (iArea / 10.0 * line)), 3, 12);
        }
    }

    private int convertStringToInt() {
        int a = 20;
        try {
            a = Integer.parseInt(jTFArea.getText());
        } catch (Exception e) {

        }
        return a <= 50 ? (a >= 20 ? a : 20) : 20;
    }

    private void SingleThreadReceiveDataCheckModule() {
        ReceiveData();
        executorServiceCheckModule = Executors.newSingleThreadScheduledExecutor();
        executorServiceCheckModule.scheduleAtFixedRate(new Runnable() {
            private int iCheckModule = 0;

            @Override
            public void run() {
                System.out.println("---------------------------------- " + rxData);
                if (iCheckModule == 0) {
                    jtaInFo.setText(jtaInFo.getText() + "\n"
                            + "---- The Modules Are Checking ----");
                }
                if (!rxData.isEmpty()) {
                    jtaInFo.setText(jtaInFo.getText() + "\n" + rxData);
                }
                rxData = "";
                iCheckModule++;
                if (iCheckModule > 8) {
                    jtaInFo.setText(jtaInFo.getText() + "\n"
                            + "---- Finished ----");
                    iCheckModule = 0;
                    executorServiceCheckModule.shutdown();
                }
            }
        }, 0, 300, TimeUnit.MILLISECONDS);
    }

    private void SingleThreadReceiveDataExecution() {
        ReceiveData();
        executorServiceExecution = Executors.newSingleThreadScheduledExecutor();
        executorServiceExecution.scheduleAtFixedRate(new Runnable() {
            private int sttModule = 1;
            int rssi_A = 0;
            int rssi_B = 0;
            int rssi_C = 0;
            int rssi_D = 0;
            Random rand = new Random(50);

            @Override
            public void run() {
                System.out.println("---------------------------------- " + rxData);
                if (!rxData.isEmpty() && sttModule == 0 || true) {
                    try {
                        rssi_A = Integer.parseInt("01");
                        rssi_B = Integer.parseInt("01");
                        rssi_C = Integer.parseInt("01");
                        rssi_D = Integer.parseInt("01");

                        double toado_x = rand.nextInt(iArea);
                        double toado_y = rand.nextInt(iArea);

                        //toado_x = 10;
                        //toado_y = 10;
                        //iArea = convertStringToInt();
                        int ratio_X = X_1 / iArea;
                        int ratio_Y = Y_1 / iArea;

                        int x_p1 = ((int) toado_x * ratio_X - 3) > X_1 ? X_1 - 20 : ((int) toado_x * ratio_X - 3);
                        int y_p1 = (((int) toado_y * ratio_Y - 10) > Y_1 ? 0 : Y_1 - ((int) toado_y * ratio_Y));

                        int x_p2 = (int) toado_x * ratio_X - 3;
                        int y_p2 = Y_1 - (int) toado_y * ratio_Y - 10;

                        System.out.print("X - Y: " + X_1 + " - " + Y_1);
                        System.out.print("   Toa do: " + toado_x + "  " + toado_y);
                        System.out.print("   ____x_: " + x_p1);
                        System.out.println("   ____y_: " + y_p1);

                        paintPanel.setX_point(x_p1 - 5, x_p1 - 15);
                        paintPanel.setY_point(y_p1 - 15, y_p1 + 15);
                        

                        String s = jtaInFo.getText() + "Toa Do: X1= "
                                + String.valueOf(toado_x)
                                + " Y1= " + String.valueOf(toado_y)
                                + ", X1= "
                                + String.valueOf(toado_x)
                                + " Y1= " + String.valueOf(toado_y) + "\n";
                        
                        jtaInFo.setText(s);
                        repaint();
                    } catch (Exception e) {
                        rxData = "";
                    }
                    rxData = "";
                }

                if (isOpenPort) {
                    switch (sttModule) {
                        case 0:
                            comPortMain.writeBytes("MA".getBytes(), 3);
                            sttModule = 1;
                            break;
                        case 1:
                            comPortMain.writeBytes("MB".getBytes(), 3);
                            sttModule = 2;
                            break;
                        case 2:
                            comPortMain.writeBytes("MC".getBytes(), 3);
                            sttModule = 3;
                            break;
                        case 3:
                            comPortMain.writeBytes("MD".getBytes(), 3);
                            sttModule = 0;
                            break;
                    }
                }
            }
        }, 0, (long) (3000), TimeUnit.MILLISECONDS);
    }

    private void ReceiveData() {
        comPortMain.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                byte[] newData = event.getReceivedData();
                for (int i = 0; i < newData.length; ++i) {
                    rxData += (char) newData[i];
                }
            }
        });
    }
}
