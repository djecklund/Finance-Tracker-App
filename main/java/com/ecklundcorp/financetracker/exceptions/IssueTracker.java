/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecklundcorp.financetracker.exceptions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dillon
 */
public class IssueTracker {
    
    public void generateIssueReport(String issue, Exception e){
        
        File f = new File("Issues/" + issue + ".log");
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date();
        
        try(FileWriter fw = new FileWriter(f)){
            
            fw.write(dateFormat.format(date) + "\n" + issue + "\n" + e.getMessage());
            
        }catch(IOException io){
            System.out.println(io.getMessage());
        }
        
        //JOptionPane.showMessageDialog(null, "An issue: " + issue + " has occured, please check the Issues folder for more details.");
        
    }
    
    public void generateIssueReport(String issue, Exception e, JTable table){
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        
        Object rowData[] = {issue, e.getMessage()};
        
        model.addRow(rowData);
        
    }
    
}
