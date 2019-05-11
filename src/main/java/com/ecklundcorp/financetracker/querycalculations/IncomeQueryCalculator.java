/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecklundcorp.financetracker.querycalculations;

import com.ecklundcorp.financetracker.mycosts.Income;
import java.util.ArrayList;
//import mycosts.Income;

/**
 *
 * @author Dillon
 */
public class IncomeQueryCalculator {
    
    public double getTotalIncome(ArrayList<Income> incomeQueryResults){
        
        double totalIncome = 0.00;
        
        for(int i = 0; i < incomeQueryResults.size(); i++){
            totalIncome += incomeQueryResults.get(i).getIncome();
        }
        
        return totalIncome;
    }
    
}
