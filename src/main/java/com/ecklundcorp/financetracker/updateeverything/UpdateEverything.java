/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecklundcorp.financetracker.updateeverything;

import com.ecklundcorp.financetracker.mycosts.Bills;
import com.ecklundcorp.financetracker.mycosts.Income;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
//import mycosts.Bills;
//import mycosts.Income;

/**
 *
 * @author Dillon
 */
public class UpdateEverything {
    
    public void update(JLabel totalBillsLBL, JLabel totalMoneyMadeLBL, JLabel gainLossLBL, JLabel billQueryTotalExpenseLBL, JTable billSummaryTable, JTable billQueryTable, JList billsList, JList billQueryList, JComboBox billCategoryCB, JComboBox billQueryCB, ArrayList<Income> income, ArrayList<String> billQueries, ArrayList<Bills> billQueryResults, JTable incomeSummaryTable, JTable paymentQueryTable, JList incomeList, JList paymentQueryList, JComboBox paymentCategoryCB, JComboBox paymentQueryCB, JLabel paymentQueryTotalIncomeLBL, ArrayList<String> incomeQueries, ArrayList<Bills> bills){
        
        UpdateEverythingBills updateBills = new UpdateEverythingBills();
        updateBills.updateEverything(totalBillsLBL, totalMoneyMadeLBL, gainLossLBL, billQueryTotalExpenseLBL, billSummaryTable, billQueryTable, billsList, billQueryList, billCategoryCB, billQueryCB, income, bills, billQueries, billQueryResults);
        
        UpdateEverythingIncome updateIncome = new UpdateEverythingIncome();
        updateIncome.updateEverything(incomeSummaryTable, paymentQueryTable, incomeList, paymentQueryList, paymentCategoryCB, paymentQueryCB, paymentQueryTotalIncomeLBL, gainLossLBL, totalBillsLBL, totalMoneyMadeLBL, incomeQueries, income, billQueryResults);
        
    }
    
}
