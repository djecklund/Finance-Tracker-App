/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecklundcorp.financetracker.querycalculations;

import com.ecklundcorp.financetracker.mycosts.Bills;
import java.util.ArrayList;
//import mycosts.Bills;

/**
 *
 * @author Dillon
 */
public class BillQueryCalculator {
    
    public double getTotalExpense(ArrayList<Bills> billQueryResults){
        
        double totalExpenses = 0.00;
        
        for(int i = 0; i < billQueryResults.size(); i++){
            totalExpenses += billQueryResults.get(i).getExpense();
        }
        
        return totalExpenses;
    }
    
}
