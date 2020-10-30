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
import java.awt.List;
import java.awt.ScrollPane;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JTextArea;
import java.lang.Math;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

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
    private JList jlistInFo;
    private JScrollPane sp;
    DefaultListModel model = new DefaultListModel();
    ArrayList<String> lElement = new ArrayList<>();
    PaintPanel paintPanel;
    private JCheckBox jCheckBox;

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
        jLabelTitle = new JLabel("CHƯƠNG TRÌNH");
        jLabelTitle.setBounds(heigh_screen_map + 250, 40, 400, 50);
        jLabelTitle.setFont(new Font("Times New Roman", Font.BOLD, 22));
        jLabelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelTitle.setVerticalAlignment(SwingConstants.CENTER);
        jLabelTitle.setBorder(new javax.swing.border.LineBorder(Color.BLACK, 1, true));

        JLabel jLabelTitle1 = new JLabel("CẢNH BÁO SỚM TÌNH TRẠNG ĐUỐI NƯỚC");
        jLabelTitle1.setBounds(heigh_screen_map + 250, 40 + 50, 450, 50);
        jLabelTitle1.setFont(new Font("Times New Roman", Font.BOLD, 20));
        jLabelTitle1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelTitle1.setVerticalAlignment(SwingConstants.CENTER);
        jLabelTitle1.setBorder(new javax.swing.border.LineBorder(Color.BLACK, 1, true));

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
        //jLabelPort5.setBorder(new javax.swing.border.LineBorder(Color.BLACK, 1, true));

        jCheckBox = new JCheckBox();
        jCheckBox.setBounds(X_jLabelPort + 130, Y_jLabelPort + 50 + 50 + 50 + 50 + 40, 50, 50);
        jCheckBox.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jCheckBox.setBorderPainted(false);
        jCheckBox.setOpaque(false);

        jtaInFo = new JTextArea("The system has not started !!!\n");
        jtaInFo.setBounds(X_jLabelPort, Y_jLabelPort + 50 + 50 + 50 + 50 + 50 + 40, width_screen - width_screen_map - 300, 300);
        jtaInFo.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jtaInFo.setBorder(new javax.swing.border.LineBorder(Color.BLACK, 1, true));

        jlistInFo = new JList();
        jlistInFo.setBounds(X_jLabelPort, Y_jLabelPort + 50 + 50 + 50 + 50 + 50 + 40, width_screen - width_screen_map - 300, 200);
        jlistInFo.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jlistInFo.setBorder(new javax.swing.border.LineBorder(Color.BLACK, 1, true));
        jlistInFo.setLayoutOrientation(JList.VERTICAL);
        //jlistInFo.setVisibleRowCount(-1);
        // jScrollPane1 = new ScrollPane(MainJFrame.jlistInFo);

        sp = new JScrollPane(this.jlistInFo);
        sp.setBounds(X_jLabelPort, Y_jLabelPort + 50 + 50 + 50 + 50 + 50 + 40, width_screen - width_screen_map - 300, 300);
        sp.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        sp.setBorder(new javax.swing.border.LineBorder(Color.BLACK, 1, true));

        sp.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                boolean b = jCheckBox.isSelected();
                if (b) {
                    e.getAdjustable().setValue(e.getAdjustable().getMaximum());
                }
            }
        });

        sp.setViewportView(jlistInFo);

        String week[] = {"Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday", "Sunday"};

        jlistInFo.setModel(model);

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
        add(sp);
        //add(jtaInFo);
        add(jCheckBox);
        add(jTFArea);
        add(jbtConnect);
        add(jbtCheckRF);
        add(jbtExecute);
        add(jComboBoxPort);
        add(jLabelPort);
        add(jLabelTitle1);

        onClickConnect();
        onClickCheck();
        onClickExecution();
        iArea = convertStringToInt(jTFArea.getText());
        iArea = 80;
    }

    private void onClickConnect() {
        jbtConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isOpenPort == false) {
                    iIndexPort = jComboBoxPort.getSelectedIndex();
                    comPortMain = SerialPort.getCommPort(jComboBoxPort.getItemAt(iIndexPort));
                    System.out.println("comPortMain: " + comPortMain.getSystemPortName());
                    isOpenPort = comPortMain.openPort();
                    comPortMain.setBaudRate(9600);
                    comPortMain.setComPortTimeouts(SerialPort.LISTENING_EVENT_DATA_RECEIVED, 1000, 100);
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
                        iArea = convertStringToInt(jTFArea.getText());
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
        System.out.println("----------------------------------------------------");
        heigh_screen = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        width_screen = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        System.out.println("width_screen: " + width_screen);
        System.out.println("heigh_screen: " + heigh_screen);
        int a = Integer.parseInt("-075");
        int a1 = Integer.parseInt("-072");
        distanceBLE(a);
        distanceBLE(a1);
        distanceBLE((a1 + a) / 2);

        //calibDistance(-10, -13, -14, -15);
        initComponents();
        getContentPane().setBackground(Color.WHITE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        width_screen_map = heigh_screen;
        heigh_screen_map = heigh_screen - 150;
        setGuiTemp();

        System.out.println("iArea:" + iArea);
        System.out.println("Toa do X: " + calculator_X_AB(56.57, 56.57));
        System.out.println("Toa do Y: " + calculator_Y_AB(56.57, 56.57));

        //System.out.println("width_screen_map: " + width_screen_map);
        //System.out.println("heigh_screen_map: " + heigh_screen_map);
        paintPanel = new PaintPanel();
        paintPanel.setBounds(50, 40, width_screen_map, heigh_screen_map);
        paintPanel.setBackground(Color.WHITE);
        paintPanel.setBorder(new javax.swing.border.LineBorder(Color.RED, 2, true));
        add(paintPanel);

        X_1 = width_screen_map;
        Y_1 = heigh_screen_map;
        //System.out.println("X_1: " + X_1);
        //System.out.println("Y_1: " + Y_1);
    }

    private double distanceBLE(int RSSI) {
        double d = 0;
        double A = -80;
        double n = 2;
        double rs_a = RSSI - A;
        double _10n = -10.0 * n;
        double soMu = rs_a / _10n;
        d = Math.round(Math.pow(10, soMu) * 100.0) / 100.0;
        System.out.println("RSSI: " + RSSI + "  Distance: " + d);
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

        private int x_point1_old;
        private int y_point1_old;
        private int x_point2_old;
        private int y_point2_old;

        private int counter_1 = 0;
        private int counter_2 = 0;

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
            iArea = convertStringToInt(jTFArea.getText());
            if (x_point1 != 0 || y_point1 != 0) {
                g.fillOval(x_point1, y_point1, 20, 20);
                x_point1_old = x_point1;
                y_point1_old = y_point1;
            } else {
                counter_1 += 1;
                if (counter_1 == 3) {
                    g.fillOval(x_point1_old, y_point1_old, 20, 20);
                }
            }

            if (x_point2 != 0 || y_point2 != 0) {
                g.fillOval(x_point1, y_point1, 20, 20);
                x_point2_old = x_point2;
                y_point2_old = y_point2;
            } else {
                g.fillOval(x_point2_old, y_point2_old, 20, 20);
            }

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
                    g.drawString(String.valueOf((double) (i * iArea / 10.0)), i * rowHt - 20, heigh_screen_map - 5);
                } else if (i * iArea / 10 >= 10) {
                    g.drawString(String.valueOf((double) (i * iArea / 10.0)), i * rowHt - 25, heigh_screen_map - 5);
                }
            }
            g.drawString(String.valueOf((double) (line * iArea / 10.0)), line * rowHt - 30, heigh_screen_map - 5);

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

    private int convertStringToInt(String s) {
        int a = 20;
        try {
            a = Integer.parseInt(s);
        } catch (Exception e) {
            return 9999;
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
            private int sttModule = 0;
            int rssi_A = 0;
            int rssi_B = 0;
            int rssi_C = 0;
            int rssi_D = 0;
            Random rand = new Random(50);
            int c = 0;
            int timeOut = 0;

            @Override
            public void run() {
                model.addElement("ABCD: " + jCheckBox.isSelected() + "  " + String.valueOf(c));
                if (!rxData.isEmpty() && sttModule == 0 || true) {
                    System.out.println("---------------------------------- " + rxData);
                    try {
                        double toado_x = rand.nextInt(100) / 2.0;
                        double toado_y = rand.nextInt(100) / 2.0;
//                        toado_x = distanceBLE(rssi_A);
//                        toado_y = distanceBLE(rssi_B);

//                        toado_x_BC_1 = distanceBLE(-80);
//                        toado_y_BC_1 = distanceBLE(-79);
//                        toado_x_BC_1 = distanceBLE(-80);
//                        toado_y_BC_1 = distanceBLE(-79);
                        toado_x = calculator_X_AB(28.28, 28.28);
                        toado_y = calculator_Y_AB(28.28, 28.28);

                        int ratio_X = X_1 / iArea;
                        int ratio_Y = Y_1 / iArea;

                        int x_p1 = ((int) (toado_x * ratio_X - 3.0)) > X_1 ? X_1 - 20 : ((int) (toado_x * ratio_X - 3.0));
                        int y_p1 = (((int) (toado_y * ratio_Y - 10.0)) > Y_1 ? 0 : Y_1 - ((int) (toado_y * ratio_Y)));

                        int x_p2 = (int) (toado_x * ratio_X - 3);
                        int y_p2 = Y_1 - (int) toado_y * ratio_Y - 10;

                        paintPanel.setX_point(x_p1 - 5, x_p1 - 15);
                        paintPanel.setY_point(y_p1 - 15, y_p2 - 15);

                        c += 1;
                        repaint();
                        rxData = "";
                    } catch (Exception e) {
                        rxData = "";
                    }
                    rxData = "";
                }

                int iA = rxData.indexOf("A1") + rxData.indexOf("A1", rxData.indexOf("A1") + 3);
                int iB = rxData.indexOf("B1") + rxData.indexOf("B1", rxData.indexOf("B1") + 3);
                int iC = rxData.indexOf("C1") + rxData.indexOf("C1", rxData.indexOf("C1") + 3);
                int iD = rxData.indexOf("D1") + rxData.indexOf("D1", rxData.indexOf("D1") + 3);

                if (isOpenPort) {
                    if (sttModule == 0 && iA > rxData.indexOf("A1")) {
                        System.out.println("Exe 1");
                        comPortMain.writeBytes("MA".getBytes(), 2);
                        sttModule = 1;
                    } else if (sttModule == 1 && iB > rxData.indexOf("B1")) {
                        System.out.println("Exe 2");
                        comPortMain.writeBytes("MA".getBytes(), 2);
                        sttModule = 2;
                    } else if (sttModule == 2 && iC > rxData.indexOf("C1")) {
                        System.out.println("Exe 3");
                        comPortMain.writeBytes("MA".getBytes(), 2);
                        sttModule = 3;
                    } else if (sttModule == 3 && iD > rxData.indexOf("D")) {
                        System.out.println("Exe 4");
                        comPortMain.writeBytes("MA".getBytes(), 2);
                        sttModule = 0;
                    }
                }

                if (iD > rxData.indexOf("D1")) {
                    String lA[] = getRSSIByAdd("A", rxData);
                    String lB[] = getRSSIByAdd("B", rxData);
                    String lC[] = getRSSIByAdd("C", rxData);
                    String lD[] = getRSSIByAdd("D", rxData);

                    double X_BC_AB_1 = distanceBLE(Integer.parseInt(lA[1]));
                    double Y_BC_AB_1 = distanceBLE(Integer.parseInt(lB[1]));
                    double X_BC_CD_1 = distanceBLE(Integer.parseInt(lC[1]));
                    double Y_BC_CD_1 = distanceBLE(Integer.parseInt(lD[1]));

                    double X_BC_AB_2 = distanceBLE(Integer.parseInt(lA[1]));
                    double Y_BC_AB_2 = distanceBLE(Integer.parseInt(lB[1]));
                    double X_BC_CD_2 = distanceBLE(Integer.parseInt(lC[1]));
                    double Y_BC_CD_2 = distanceBLE(Integer.parseInt(lD[1]));

                    double toado_XY_AB_1[] = TinhToaDoXY_AB(X_BC_AB_1, Y_BC_AB_1);
                    double toado_XY_AB_2[] = TinhToaDoXY_AB(X_BC_AB_2, Y_BC_AB_2);
                }
                timeOut++;
            }
        }, 0, (long) (100), TimeUnit.MILLISECONDS);
    }

    private double calculator_X_AB(double da, double db) {
        double x___ = 0.0;
        double AB = iArea;
        x___ = ((da * da) - (db * db) + (AB * AB)) / (2.0 * AB);
        x___ = Math.round(x___ * 100) / 100;
        return x___;
    }

    private double calculator_Y_AB(double da, double db) {
        double AB = iArea;
        double x1 = calculator_X_AB(da, db);
        double yy = (da * da) - (x1 * x1);
        yy = (db * db) - Math.pow(AB - x1, 2);
        System.out.println("yy: " + yy);
        return yy <= 0 ? 0 : Math.round(Math.sqrt(yy) * 100) / 100.0;
    }

    private double calculator_X_CD(double dc, double dd) {
        double x___ = 0.0;
        double AB = iArea * 2;
        x___ = ((dc * dc) - (dd * dd) - (AB * AB)) / (-AB);
        x___ = Math.round(x___ * 100) / 100;
        return x___;
    }

    private double calculator_Y_CD(double dc, double dd) {
        double x1 = calculator_X_CD(dc, dd);
        double yy = (dc * dc) - (x1 * x1);
        //System.out.println("yy: " + yy);
        return yy <= 0 ? 0 : Math.round(Math.sqrt(yy) * 100) / 100.0;
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
                System.out.println("rxData: " + rxData);
            }
        });
    }

    private String[] getRSSIByAdd(String add, String sData) {
        String key = add + "1";
        String sTemp = sData.substring(sData.indexOf(key), sData.indexOf(key, sData.indexOf(key) + 3) + 2);
        System.out.println("sTemp: " + sTemp + "   sKey: " + key);
        String[] d = sTemp.split(":");
        System.out.println("\n----------");
        return d;
    }

    private double[] TinhToaDoXY_AB(double dA, double dB) {
        double[] td = {0.0, 0.0};
        double toado_x = calculator_X_AB(dA, dB);
        double toado_y = calculator_Y_AB(dA, dB);

        int ratio_X = X_1 / iArea;
        int ratio_Y = Y_1 / iArea;

        int x_p1 = ((int) (toado_x * ratio_X - 3.0)) > X_1 ? X_1 - 20 : ((int) (toado_x * ratio_X - 3.0));
        int y_p1 = (((int) (toado_y * ratio_Y - 10.0)) > Y_1 ? 0 : Y_1 - ((int) (toado_y * ratio_Y)));

        int x_p2 = (int) (toado_x * ratio_X - 3);
        int y_p2 = Y_1 - (int) toado_y * ratio_Y - 10;

        td[0] = (x_p1 - 5.0);
        td[1] = (x_p1 - 5.0);
        System.out.println("X: " + td[0] + "  Y: " + td[1]);
//        paintPanel.setX_point(x_p1 - 5, x_p1 - 15);
//        paintPanel.setY_point(y_p1 - 15, y_p2 - 15);
        return td;
    }

}
