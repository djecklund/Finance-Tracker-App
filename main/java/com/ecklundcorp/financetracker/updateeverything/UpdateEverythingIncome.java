/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecklundcorp.financetracker.updateeverything;

import com.ecklundcorp.financetracker.collectqueryoptions.CollectIncomeQueries;
import com.ecklundcorp.financetracker.mycosts.Bills;
import com.ecklundcorp.financetracker.mycosts.Income;
import com.ecklundcorp.financetracker.querycalculations.IncomeQueryCalculator;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dillon
 */
public class UpdateEverythingIncome {
    
    DecimalFormat moneyFormat;
    
    public UpdateEverythingIncome(){
        moneyFormat = new DecimalFormat("###,##0.00");
    }
    
    public void updateEverything(JTable incomeSummaryTable, JTable paymentQueryTable, JList incomeList, JList paymentQueryList, JComboBox paymentCategoryCB, JComboBox paymentQueryCB, JLabel paymentQueryTotalIncomeLBL, JLabel gainLossLBL, JLabel totalBillsLBL, JLabel totalMoneyMadeLBL, ArrayList<String> incomeQueries, ArrayList<Income> income, ArrayList<Bills> bills){
        
        updateIncomeSummaryTable(incomeSummaryTable, income);
        updateIncomeList(incomeList, income);
        updateIncomeCategoryCB(paymentCategoryCB, incomeQueries, income);
        
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
    
    private void updateIncomeSummaryTable(JTable incomeSummaryTable, ArrayList<Income> income){
        
        DefaultTableModel model = (DefaultTableModel) incomeSummaryTable.getModel();
        
        for(int i = model.getRowCount() - 1; i >= 0; i--){
            model.removeRow(i);
        }
        
        for(int i = 0; i < income.size(); i++){
            
            Object[] incomeRow = {income.get(i).getDate(), income.get(i).getCategory(), moneyFormat.format(income.get(i).getIncome())};
            model.addRow(incomeRow);
            
        }
        
    }
    
    private void updateIncomeList(JList incomeList, ArrayList<Income> income){
        
        DefaultListModel model = (DefaultListModel) incomeList.getModel();
        
        model.removeAllElements();
        
        for(int i = 0; i < income.size(); i++){
            
            model.addElement(income.get(i).getDate() + " " + income.get(i).getCategory() + " " + moneyFormat.format(income.get(i).getIncome()));
            
        }
        
    }
    
    private void updateIncomeCategoryCB(JComboBox paymentCategoryCB, ArrayList<String> incomeQueries, ArrayList<Income> income){
        
        paymentCategoryCB.removeAllItems();
        
        CollectIncomeQueries incomeCategories = new CollectIncomeQueries();
        
        incomeQueries = incomeCategories.setUpCategoryQueryOptions(income);
        
        for(int i = 0; i < incomeQueries.size(); i++){
            paymentCategoryCB.addItem(incomeQueries.get(i));
        }
                
    }
    
    public void updateIncomeQueryList(JList paymentQueryList, JComboBox paymentQueryCB, ArrayList<String> incomeQueries, ArrayList<Income> incomeQueryResults, ArrayList<Income> income){
        
        CollectIncomeQueries incomeCategories = new CollectIncomeQueries();
        
        DefaultListModel model = (DefaultListModel) paymentQueryList.getModel();
        
        model.removeAllElements();
        
        switch (paymentQueryCB.getSelectedIndex()) {
            case 0:
                incomeQueries = incomeCategories.setUpCategoryQueryOptions(income);
                break;
            case 1:
                incomeQueries = incomeCategories.setUpCategoryAndMonthYearQueryOptions(income);
                break;
            case 2:
                incomeQueries = incomeCategories.setUpDateDayQueryOptions(income);
                break;
            case 3:
                incomeQueries = incomeCategories.setUpDateMonthYearQueryOptions(income);
                break;
            case 4:
                incomeQueries = incomeCategories.setUpDateYearQueryOptions(income);
                break;
            default:
                break;
        }
        
        for(int i = 0; i < incomeQueries.size(); i++){
            
            model.addElement(incomeQueries.get(i));
            
        }
        
    }
    
    public void updateIncomeQueryTable(ArrayList<Income> incomeQueryResults, JTable paymentQueryTable, ArrayList<Income> income, JComboBox paymentQueryCB, JList paymentQueryList, JLabel paymentQueryTotalIncomeLBL){
        
        incomeQueryResults = new ArrayList<>();
        
        DefaultTableModel model = (DefaultTableModel) paymentQueryTable.getModel();
        
        for(int i = model.getRowCount() - 1; i >= 0; i--){

            model.removeRow(i);

        }

        for(int i = 0; i < income.size(); i++){
                        
            if(paymentQueryCB.getSelectedIndex() == 0){

                if(income.get(i).getCategory().equals(paymentQueryList.getSelectedValue())){
                    Object[] incomeQueryRow = {income.get(i).getDate(), income.get(i).getCategory(), moneyFormat.format(income.get(i).getIncome())};
                    model.addRow(incomeQueryRow);
                    incomeQueryResults.add(income.get(i));
                }

            }
            else if(paymentQueryCB.getSelectedIndex() == 1){

                if((income.get(i).getCategory() + " " + income.get(i).getDate().split("/")[0] + "/" + income.get(i).getDate().split("/")[2]).equals(paymentQueryList.getSelectedValue())){
                    Object[] incomeQueryRow = {income.get(i).getDate(), income.get(i).getCategory(), moneyFormat.format(income.get(i).getIncome())};
                    model.addRow(incomeQueryRow);
                    incomeQueryResults.add(income.get(i));
                }

            }
            else if(paymentQueryCB.getSelectedIndex() == 2){

                if(income.get(i).getDate().equals(paymentQueryList.getSelectedValue())){
                    Object[] incomeQueryRow = {income.get(i).getDate(), income.get(i).getCategory(), moneyFormat.format(income.get(i).getIncome())};
                    model.addRow(incomeQueryRow);
                    incomeQueryResults.add(income.get(i));
                }

            }
            else if(paymentQueryCB.getSelectedIndex() == 3){

                if((income.get(i).getDate().split("/")[0] + "/" + income.get(i).getDate().split("/")[2]).equals(paymentQueryList.getSelectedValue())){
                    Object[] incomeQueryRow = {income.get(i).getDate(), income.get(i).getCategory(), moneyFormat.format(income.get(i).getIncome())};
                    model.addRow(incomeQueryRow);
                    incomeQueryResults.add(income.get(i));
                }

            }
            else if(paymentQueryCB.getSelectedIndex() == 4){

                if(income.get(i).getDate().split("/")[2].equals(paymentQueryList.getSelectedValue())){
                    Object[] incomeQueryRow = {income.get(i).getDate(), income.get(i).getCategory(), moneyFormat.format(income.get(i).getIncome())};
                    model.addRow(incomeQueryRow);
                    incomeQueryResults.add(income.get(i));
                }

            }

        }
        
        IncomeQueryCalculator queryCalc = new IncomeQueryCalculator();
        paymentQueryTotalIncomeLBL.setText("$" + moneyFormat.format(queryCalc.getTotalIncome(incomeQueryResults)));
        
    }
    
}
