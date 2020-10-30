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
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

class GridsCanvas {

    private static int iArea = 80;
    private static int sttModule = 0;
    private static int X_1 = 1000;
    private static int Y_1 = 1000;

    public static void main(String[] a) throws InterruptedException {
//        distanceBLE1(-41.16 );
//        distanceBLE1(-41.16 );
//        distanceBLE1(-41.16 );
//        distanceBLE1(-41.16 );
//        getRSSIByAdd("A");
//        getRSSIByAdd("B");
//        getRSSIByAdd("C");
//        getRSSIByAdd("D");
        test1();

    }

    private static String[] getRSSIByAdd(String add, String sData) {
        String key = add + "1";
        String sTemp = sData.substring(sData.indexOf(key), sData.indexOf(key, sData.indexOf(key) + 3) + 2);
        //System.out.println("sTemp: " + sTemp + "   sKey: " + key);
        String[] d = sTemp.split(":");
        //System.out.println("\n----------");
        return d;
    }

    private static double distanceBLE1(int RSSI) {
        double d = 0;
        int A = -67;
        double n = 2.0755;
        double rs_a = RSSI - A;
        double _10n = -10.0 * n;
        double soMu = rs_a / _10n;
        d = Math.round(Math.pow(10, soMu) * 100.0) / 100.0;
        //System.out.println("Distance: " + d);
        return d;
    }

    private static double calculator_X_AB(double da, double db) {
        double x___ = 0.0;
        double AB = iArea;
        x___ = ((da * da) - (db * db) + (AB * AB)) / (2.0 * AB);
        x___ = Math.round(x___ * 100) / 100;
        return x___;
    }

    private static double calculator_Y_AB(double da, double db) {
        double AB = iArea;
        double x1 = calculator_X_AB(da, db);
        double yy = (da * da) - (x1 * x1);
        yy = (db * db) - Math.pow(AB - x1, 2);
        System.out.println("yy: " + yy);
        return yy <= 0 ? 0 : Math.round(Math.sqrt(yy) * 100) / 100.0;
    }

    private static double calculator_X_CD(double dc, double dd) {
        double x___ = 0.0;
        double AB = iArea * 2;
        x___ = ((dc * dc) - (dd * dd) - (AB * AB)) / (-AB);
        x___ = Math.round(x___ * 100) / 100;
        return x___;
    }

    private static double calculator_Y_CD(double dc, double dd) {
        double x1 = calculator_X_CD(dc, dd);
        double yy = (dc * dc) - (x1 * x1);
        //System.out.println("yy: " + yy);
        return yy <= 0 ? 0 : Math.round(Math.sqrt(yy) * 100) / 100.0;
    }

    private static void test1() throws InterruptedException {
        boolean isOpenPort = true;
        String sData = "";
        int i = 0;
        //System.out.println("i:" + i);       
        while (true) {
            int iA = sData.indexOf("A1") + sData.indexOf("A1", sData.indexOf("A1") + 3);
            int iB = sData.indexOf("B1") + sData.indexOf("B1", sData.indexOf("B1") + 3);
            int iC = sData.indexOf("C1") + sData.indexOf("C1", sData.indexOf("C1") + 3);
            int iD = sData.indexOf("D1") + sData.indexOf("D1", sData.indexOf("D1") + 3);

            if (sttModule == 0 && iA > sData.indexOf("A1") && iA > 0) {
                //System.out.println("Exe 1");
                //comPortMain.writeBytes("MA".getBytes(), 2);
                sttModule = 1;
            } else if (sttModule == 1 && iB > sData.indexOf("B1") && iB > 0) {
                //System.out.println("Exe 2");
                //comPortMain.writeBytes("MA".getBytes(), 2);
                sttModule = 2;
            } else if (sttModule == 2 && iC > sData.indexOf("C1") && iC > 0) {
                //System.out.println("Exe 3");
                //comPortMain.writeBytes("MA".getBytes(), 2);
                sttModule = 3;
            } else if (sttModule == 3 && iD > sData.indexOf("D1") && iD > 0) {
                // System.out.println("Exe 4");
                //comPortMain.writeBytes("MA".getBytes(), 2);
                try {
                    String lA[] = getRSSIByAdd("A", sData);
                    String lB[] = getRSSIByAdd("B", sData);
                    String lC[] = getRSSIByAdd("C", sData);
                    String lD[] = getRSSIByAdd("D", sData);

                    //double xA1 = distanceBLE1(Integer.parseInt(lA[1]));
                    //double xA2 = distanceBLE1(Integer.parseInt(lA[2]));
//
//                    double xB1 = distanceBLE1(Integer.parseInt(lB[1]));
//                    double xB2 = distanceBLE1(Integer.parseInt(lB[2]));
//
//                    double xC1 = distanceBLE1(Integer.parseInt(lC[1]));
//                    double xC2 = distanceBLE1(Integer.parseInt(lC[2]));
//
//                    double xD1 = distanceBLE1(Integer.parseInt(lD[1]));
//                    double xD2 = distanceBLE1(Integer.parseInt(lD[2]));
                    double xA1 = 56.56;
                    double xB1 = 56.56;
                    TinhToaDoXY_CD(xA1, xB1);
                } catch (Exception e) {
                    System.out.println("Error data !!!");
                }

                //break;
            }
            else{
                System.out.println("Exe 4");
            }

            if (i == 2) {
                sData = "A1:-67:-68:B1:-77:-78:B1C1:-77:-78:C1D1:-97:-98:D1";
            } else if (i == 4) {
                sData = "A1:-67:-68:A1B1:-77:-78:BC1:-77:-78:C1D1:-97:-98:D1";
            } else if (i == 6) {
                sData = "A1:-67:-68:A1B1:-77:-78:B1C1:-77:-78:CD1:-97:-98:D1";
            } else if (i == 7) {
                sData = "A1:-67:-68:A1B1:-77:-78:B1C1:-77:-78:C1D1:-97:-98:D";
            } else if (i == 9) {
                sData = "A1:-67:-68:A1B1:-77:-78:B1C1:-77:-78:C1D1:-97:-98:D1";
                i = 0;
            }

            i++;
            Thread.sleep(2000);
        }
    }

    private static double[] TinhToaDoXY_AB(double dA, double dB) {
        double[] td = {0.0, 0.0};
        double toado_x = calculator_X_AB(dA, dB);
        double toado_y = calculator_Y_AB(dA, dB);
        System.out.println("X: " + toado_x + "  Y: " + toado_y);

        int ratio_X = X_1 / iArea;
        int ratio_Y = Y_1 / iArea;

        int x_p1 = ((int) (toado_x * ratio_X - 3.0)) > X_1 ? X_1 - 20 : ((int) (toado_x * ratio_X - 3.0));
        int y_p1 = (((int) (toado_y * ratio_Y - 10.0)) > Y_1 ? 0 : Y_1 - ((int) (toado_y * ratio_Y)));

        int x_p2 = (int) (toado_x * ratio_X - 3);
        int y_p2 = Y_1 - (int) toado_y * ratio_Y - 10;

        td[0] = (x_p1 - 5.0);
        td[1] = (x_p1 - 5.0);

//        paintPanel.setX_point(x_p1 - 5, x_p1 - 15);
//        paintPanel.setY_point(y_p1 - 15, y_p2 - 15);
        return td;
    }

    private static double[] TinhToaDoXY_CD(double dC, double dD) {
        double[] td = {0.0, 0.0};
        double toado_x = calculator_X_CD(dC, dD);
        double toado_y = calculator_Y_CD(dC, dD);
        System.out.println("X: " + toado_x + "  Y: " + toado_y);

        int ratio_X = X_1 / iArea;
        int ratio_Y = Y_1 / iArea;

        int x_p1 = ((int) (toado_x * ratio_X - 3.0)) > X_1 ? X_1 - 20 : ((int) (toado_x * ratio_X - 3.0));
        int y_p1 = (((int) (toado_y * ratio_Y - 10.0)) > Y_1 ? 0 : Y_1 - ((int) (toado_y * ratio_Y)));

        int x_p2 = (int) (toado_x * ratio_X - 3);
        int y_p2 = Y_1 - (int) toado_y * ratio_Y - 10;

        td[0] = (x_p1 - 5.0);
        td[1] = (x_p1 - 5.0);

//        paintPanel.setX_point(x_p1 - 5, x_p1 - 15);
//        paintPanel.setY_point(y_p1 - 15, y_p2 - 15);
        return td;
    }
}
