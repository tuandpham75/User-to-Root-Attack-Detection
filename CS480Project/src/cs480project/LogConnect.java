/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs480project;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**
 *
 * @author Ricky
 */
class LogConnect {
    Connection conn = null;
    PreparedStatement pst = null;
        public static Connection ConnectDB(){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\bhoang\\Documents\\User-to-Root-Attack-Detection\\CS480Project\\src\\LinuxLogs.sqlite");
            System.out.println("Connected Succesfully!");
            //readFile();
            return conn;
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,e);
            return null;
        }
    }
}
