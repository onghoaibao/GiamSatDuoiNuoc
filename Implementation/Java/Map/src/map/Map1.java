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

class GridsCanvas {

    public static void main(String[] a) {
        distanceBLE1(-41.16 );
        distanceBLE1(-41.16 );
        distanceBLE1(-41.16 );
        distanceBLE1(-41.16 );
    }

    private static double distanceBLE1(double RSSI) {
        double d = 0;
        int A = -12;
        double n = 2.0755;
        double rs_a = RSSI - A;
        double _10n = -10.0 * n;
        double soMu = rs_a / _10n;
        d = Math.round(Math.pow(10, soMu)*100.0)/100.0;
        System.out.println("Distance: " + d);
        return d;
    }

}
