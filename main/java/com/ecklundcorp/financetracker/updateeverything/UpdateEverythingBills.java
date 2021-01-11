/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecklundcorp.financetracker.updateeverything;

//import collectqueryoptions.CollectBillQueries;
import com.ecklundcorp.financetracker.collectqueryoptions.CollectBillQueries;
import com.ecklundcorp.financetracker.mycosts.Bills;
import com.ecklundcorp.financetracker.mycosts.Income;
import com.ecklundcorp.financetracker.querycalculations.BillQueryCalculator;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
//import mycosts.Bills;
//import mycosts.Income;
//import querycalculations.BillQueryCalculator;

/**
 *
 * @author Dillon
 */
public class UpdateEverythingBills {
    
    DecimalFormat moneyFormat;
    
    public UpdateEverythingBills(){
        moneyFormat = new DecimalFormat("###,##0.00");
    }
    
    public void updateEverything(JLabel totalBillsLBL, JLabel totalMoneyMadeLBL, JLabel gainLossLBL, JLabel billQueryTotalExpenseLBL, JTable billSummaryTable, JTable billQueryTable, JList billsList, JList billQueryList, JComboBox billCategoryCB, JComboBox billQueryCB, ArrayList<Income> income, ArrayList<Bills> bills, ArrayList<String> billQueries, ArrayList<Bills> billQueryResults){
        
        updateBillSummaryTable(billSummaryTable, bills);
        updateBillList(billsList, bills);
        updateBillCategoryCB(billCategoryCB, billQueries, bills);
        
    }
    
    public void updateSummaryLabels(JLabel totalBillsLBL, JLabel totalMoneyMadeLBL, JLabel gainLossLBL, ArrayList<Income> income, ArrayList<Bills> bills){
        
        double totalIncome = 0.00;
        double totalExpenses = 0.00;
        
        for(int i = 0; i < income.size(); i++){
            if(!(income.get(i).getCategory().equals("Medical"))){
                totalIncome += income.get(i).getIncome();
            }
        }
        
        for(int i = 0; i < bills.size(); i++){
            if(!(bills.get(i).getCategory().equals("Medical"))){
                totalExpenses += bills.get(i).getExpense();
            }
        }
        
        totalBillsLBL.setText("$" + moneyFormat.format(totalExpenses));
        totalMoneyMadeLBL.setText("$" + moneyFormat.format(totalIncome));
        
        double gainLoss = totalIncome - totalExpenses;
        
        gainLossLBL.setText("$" + moneyFormat.format(gainLoss));
        
    }
    
    private void updateBillSummaryTable(JTable billSummaryTable, ArrayList<Bills> bills){
        
        DefaultTableModel model = (DefaultTableModel) billSummaryTable.getModel();
        
        for(int i = model.getRowCount() - 1; i >= 0; i--){
            model.removeRow(i);
        }
        
        for(int i = 0; i < bills.size(); i++){
            
            Object[] billRow = {bills.get(i).getDate(), bills.get(i).getCategory(), moneyFormat.format(bills.get(i).getExpense())};
            model.addRow(billRow);
            
        }
        
    }
    
    private void updateBillList(JList billsList, ArrayList<Bills> bills){
        
        DefaultListModel model = (DefaultListModel) billsList.getModel();
        
        model.removeAllElements();
        
        for(int i = 0; i < bills.size(); i++){
            
            model.addElement(bills.get(i).getDate() + " " + bills.get(i).getCategory() + " " + moneyFormat.format(bills.get(i).getExpense()));
            
        }
        
    }
    
    private void updateBillCategoryCB(JComboBox billCategoryCB, ArrayList<String> billQueries, ArrayList<Bills> bills){
        
        billCategoryCB.removeAllItems();
        
        CollectBillQueries billCategories = new CollectBillQueries();
        
        billQueries = billCategories.setUpCategoryQueryOptions(bills);
        
        for(int i = 0; i < billQueries.size(); i++){
            billCategoryCB.addItem(billQueries.get(i));
        }
                
    }
    
    public void updateBillQueryList(JList billQueryList, JComboBox billQueryCB, ArrayList<String> billQueries, ArrayList<Bills> bills){
        
        CollectBillQueries billCategories = new CollectBillQueries();
        
        DefaultListModel model = (DefaultListModel) billQueryList.getModel();
        
        billQueryList.setSelectedIndex(-1);
        model.removeAllElements();
        
        switch (billQueryCB.getSelectedIndex()) {
            case 0:
                billQueries = billCategories.setUpCategoryQueryOptions(bills);
                break;
            case 1:
                billQueries = billCategories.setUpCategoryAndMonthYearQueryOptions(bills);
                break;
            case 2:
                billQueries = billCategories.setUpDateDayQueryOptions(bills);
                break;
            case 3:
                billQueries = billCategories.setUpDateMonthYearQueryOptions(bills);
                break;
            case 4:
                billQueries = billCategories.setUpDateYearQueryOptions(bills);
                break;
            default:
                break;
        }
        
        for(int i = 0; i < billQueries.size(); i++){
            
            model.addElement(billQueries.get(i));
            
        }
        
    }
    
    public void updateBillQueryTable(ArrayList<Bills> billQueryResults, JTable billQueryTable, ArrayList<Bills> bills, JComboBox billQueryCB, JList billQueryList, JLabel billQueryTotalExpenseLBL){
        
        billQueryResults = new ArrayList<>();
        
        DefaultTableModel model = (DefaultTableModel) billQueryTable.getModel();
        
        for(int i = model.getRowCount() - 1; i >= 0; i--){

            model.removeRow(i);

        }

        for(int i = 0; i < bills.size(); i++){

            if(billQueryCB.getSelectedIndex() == 0){

                if(bills.get(i).getCategory().equals(billQueryList.getSelectedValue())){
                    Object[] billQueryRow = {bills.get(i).getDate(), bills.get(i).getCategory(), moneyFormat.format(bills.get(i).getExpense())};
                    model.addRow(billQueryRow);
                    billQueryResults.add(bills.get(i));
                }

            }
            else if(billQueryCB.getSelectedIndex() == 1){

                if((bills.get(i).getCategory() + " " + bills.get(i).getDate().split("/")[0] + "/" + bills.get(i).getDate().split("/")[2]).equals(billQueryList.getSelectedValue())){
                    Object[] billQueryRow = {bills.get(i).getDate(), bills.get(i).getCategory(), moneyFormat.format(bills.get(i).getExpense())};
                    model.addRow(billQueryRow);
                    billQueryResults.add(bills.get(i));
                }

            }
            else if(billQueryCB.getSelectedIndex() == 2){

                if(bills.get(i).getDate().equals(billQueryList.getSelectedValue())){
                    Object[] billQueryRow = {bills.get(i).getDate(), bills.get(i).getCategory(), moneyFormat.format(bills.get(i).getExpense())};
                    model.addRow(billQueryRow);
                    billQueryResults.add(bills.get(i));
                }

            }
            else if(billQueryCB.getSelectedIndex() == 3){

                if((bills.get(i).getDate().split("/")[0] + "/" + bills.get(i).getDate().split("/")[2]).equals(billQueryList.getSelectedValue())){
                    Object[] billQueryRow = {bills.get(i).getDate(), bills.get(i).getCategory(), moneyFormat.format(bills.get(i).getExpense())};
                    model.addRow(billQueryRow);
                    billQueryResults.add(bills.get(i));
                }

            }
            else if(billQueryCB.getSelectedIndex() == 4){

                if(bills.get(i).getDate().split("/")[2].equals(billQueryList.getSelectedValue())){
                    Object[] billQueryRow = {bills.get(i).getDate(), bills.get(i).getCategory(), moneyFormat.format(bills.get(i).getExpense())};
                    model.addRow(billQueryRow);
                    billQueryResults.add(bills.get(i));
                }

            }

        }
        
        BillQueryCalculator queryCalc = new BillQueryCalculator();
        billQueryTotalExpenseLBL.setText("$" + moneyFormat.format(queryCalc.getTotalExpense(billQueryResults)));
        
    }
    
}
