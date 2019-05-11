/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecklundcorp.financetracker.import_export;

import com.ecklundcorp.financetracker.exceptions.IssueTracker;
import com.ecklundcorp.financetracker.mycosts.Bills;
import com.ecklundcorp.financetracker.mycosts.Income;
import com.ecklundcorp.financetracker.updateeverything.UpdateExcel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Dillon
 */
public class Import_Export {
    
    ArrayList<Bills> bills;
    ArrayList<Income> income;
    IssueTracker issue;
    
    public Import_Export(){
        bills = new ArrayList<>();
        income = new ArrayList<>();
        issue = new IssueTracker();
    }
    
    // Begin exports
    public void exportFile(ArrayList<Bills> bills, ArrayList<Income> income, JProgressBar progressBar) throws IOException{
        
        Thread thread = new Thread(new Runnable() {

        @Override
        public void run() {
            progressBar.setValue(0);
            progressBar.setStringPainted(true);
            progressBar.setString("0%");
            
            int totalSize = bills.size() + income.size();
            int current = 0;
            String pathway = "Export Files/My Bills and Income.xlsx";
        
            File f = new File(pathway);

            XSSFWorkbook wb = null;
            FileOutputStream resultsOutputFile = null;

            try{

                // Create workbook and sheet within workbook
                wb = new XSSFWorkbook();
                XSSFSheet billSheet = wb.createSheet("Bills");
                XSSFSheet incomeSheet = wb.createSheet("Pay");

                // Write the data into the spreadsheet
                for(int i = 0; i < bills.size(); i++){

                    // Create each row that the data will go into
                    XSSFRow row = billSheet.createRow(i + 1);

                    // Create the cells for each row
                    XSSFCell idCell = row.createCell(0);
                    XSSFCell dateCell = row.createCell(1);
                    XSSFCell categoryCell = row.createCell(2);
                    XSSFCell expenseCell = row.createCell(3);
                    XSSFCell descriptionCell = row.createCell(4);
                    XSSFCell userIdCell = row.createCell(5);

                    // Place the data in each cell of each row
                    idCell.setCellValue(bills.get(i).getId());
                    dateCell.setCellValue(bills.get(i).getDate());
                    categoryCell.setCellValue(bills.get(i).getCategory());

                    expenseCell.setCellType(CellType.NUMERIC);
                    expenseCell.setCellValue(bills.get(i).getExpense());
                    descriptionCell.setCellValue(bills.get(i).getDescription());
                    userIdCell.setCellValue(bills.get(i).getUser_id());
                    
                    current++;
                    progressBar.setValue(current);
                    
                }

                for(int i = 0; i < income.size(); i++){

                    // Create each row that the data will go into
                    XSSFRow row = incomeSheet.createRow(i + 1);

                    // Create the cells for each row
                    XSSFCell idCell = row.createCell(0);
                    XSSFCell dateCell = row.createCell(1);
                    XSSFCell categoryCell = row.createCell(2);
                    XSSFCell incomeCell = row.createCell(3);
                    XSSFCell descriptionCell = row.createCell(4);
                    XSSFCell userIdCell = row.createCell(5);

                    // Place the data in each cell of each row
                    idCell.setCellValue(income.get(i).getId());
                    dateCell.setCellValue(income.get(i).getDate());

                    categoryCell.setCellValue(income.get(i).getCategory());

                    incomeCell.setCellType(CellType.NUMERIC);
                    incomeCell.setCellValue(income.get(i).getIncome());
                    descriptionCell.setCellValue(income.get(i).getDescription());
                    userIdCell.setCellValue(income.get(i).getUser_id());

                }

                // Create the header row
                XSSFRow billHeaders = billSheet.createRow(0);
                XSSFRow incomeHeaders = incomeSheet.createRow(0);

                // Create the cells of the header row
                XSSFCell billIdHeaderCell = billHeaders.createCell(0);
                XSSFCell billDateHeaderCell = billHeaders.createCell(1);
                XSSFCell billCategoryHeaderCell = billHeaders.createCell(2);
                XSSFCell billExpenseHeaderCell = billHeaders.createCell(3);
                XSSFCell billDescriptionHeaderCell = billHeaders.createCell(4);
                XSSFCell billUserIdHeaderCell = billHeaders.createCell(5);

                XSSFCell incomeIdHeaderCell = incomeHeaders.createCell(0);
                XSSFCell incomeDateHeaderCell = incomeHeaders.createCell(1);
                XSSFCell incomeCategoryHeaderCell = incomeHeaders.createCell(2);
                XSSFCell incomeHeaderCell = incomeHeaders.createCell(3);
                XSSFCell incomeDescriptionHeaderCell = incomeHeaders.createCell(4);
                XSSFCell incomeUserIdHeaderCell = incomeHeaders.createCell(5);

                // Populate the cells of the header row
                billIdHeaderCell.setCellValue("ID");
                billDateHeaderCell.setCellValue("Date");
                billCategoryHeaderCell.setCellValue("Expense Category");
                billExpenseHeaderCell.setCellValue("Expense");
                billDescriptionHeaderCell.setCellValue("Expense Description");
                billDateHeaderCell.setCellValue("Date");
                billUserIdHeaderCell.setCellValue("User ID");

                incomeIdHeaderCell.setCellValue("ID");
                incomeDateHeaderCell.setCellValue("Date");
                incomeCategoryHeaderCell.setCellValue("Income Category");
                incomeHeaderCell.setCellValue("Income");
                incomeDescriptionHeaderCell.setCellValue("Income Description");
                incomeUserIdHeaderCell.setCellValue("User ID");

                resultsOutputFile = new FileOutputStream(f);

                // Resize each column to fit the data
                billSheet.autoSizeColumn(0);
                billSheet.autoSizeColumn(1);
                billSheet.autoSizeColumn(2);
                billSheet.autoSizeColumn(3);
                billSheet.autoSizeColumn(4);
                billSheet.autoSizeColumn(5);

                incomeSheet.autoSizeColumn(0);
                incomeSheet.autoSizeColumn(1);
                incomeSheet.autoSizeColumn(2);
                incomeSheet.autoSizeColumn(3);
                incomeSheet.autoSizeColumn(4);
                incomeSheet.autoSizeColumn(5);

                wb.write(resultsOutputFile);
                JOptionPane.showMessageDialog(null, "The information has been exported.");

            }catch(IOException io){
                issue.generateIssueReport("ExportIOException", io);
            }
            finally{
                if(resultsOutputFile != null){
                    System.out.println("flush");
                    try {
                        resultsOutputFile.flush();
                    } catch (IOException ex) {
                        Logger.getLogger(Import_Export.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("close again");
                    try {
                        resultsOutputFile.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Import_Export.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                if(wb != null){
                    System.out.println("close");
                    try {
                        wb.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Import_Export.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
            
        }});

        thread.start();
        
    }
    
    // End of Exports
    
    // Begin Imports
    public void importFile(String path, ArrayList<Bills> oldBills, ArrayList<Income> oldIncome, JTable issueTable) throws IOException, SQLException{
        
        File f = new File(path);
        
        try(Scanner scan = new Scanner(f)){
            
            String test = scan.nextLine();
            
            if(test.split(",").length == 8){
                if(test.split(",")[0].equals("Account Number") && test.split(",")[1].equals("Post Date") && 
                        test.split(",")[2].equals("Check") && test.split(",")[3].equals("Description") && 
                        test.split(",")[4].equals("Debit") && test.split(",")[5].equals("Credit") && 
                        test.split(",")[6].equals("Status") && test.split(",")[7].equals("Balance")){
                    
                    while(scan.hasNextLine()){
                        test = scan.nextLine();
                        if(!test.contains(",Pending,")){
                            test = test.substring(11, test.length());
                            
                            if(!test.contains(", ")){
                                String test2[] = test.split(",");
                                if(!test2[3].equals("")){
                                    if(!test2[3].equals("")){

                                        Bills bill = new Bills();

                                        if(test2[0].split("/")[0].length() > 1){

                                            if(test2[0].split("/")[1].length() > 1){
                                                bill.setDate(test2[0]);
                                            }
                                            else{
                                                test2[0] = test2[0].split("/")[0] + "/0" + test2[0].split("/")[1] + "/" + test2[0].split("/")[2];
                                                bill.setDate(test2[0]);
                                            }
                                        }
                                        else{
                                            test2[0] = "0" + test2[0];
                                            if(test2[0].split("/")[1].length() > 1){
                                                bill.setDate(test2[0]);
                                            }
                                            else{
                                                test2[0] = test2[0].split("/")[0] + "/0" + test2[0].split("/")[1] + "/" + test2[0].split("/")[2];
                                                bill.setDate(test2[0]);
                                            }
                                        }
                                        
                                        bill.setDescription(test2[2]);
                                        bill.setExpense(Double.parseDouble(test2[3]));
                                        bill.setUser_id(1);
                                        bills.add(bill);

                                    }
                                }
                                else{
                                    
                                    Income newIncome = new Income();
                                    
                                    if(test2[0].split("/")[0].length() > 1){

                                            if(test2[0].split("/")[1].length() > 1){
                                                newIncome.setDate(test2[0]);
                                            }
                                            else{
                                                test2[0] = test2[0].split("/")[0] + "/0" + test2[0].split("/")[1] + "/" + test2[0].split("/")[2];
                                                newIncome.setDate(test2[0]);
                                            }
                                        }
                                        else{
                                            test2[0] = "0" + test2[0];
                                            if(test2[0].split("/")[1].length() > 1){
                                                newIncome.setDate(test2[0]);
                                            }
                                            else{
                                                test2[0] = test2[0].split("/")[0] + "/0" + test2[0].split("/")[1] + "/" + test2[0].split("/")[2];
                                                newIncome.setDate(test2[0]);
                                            }
                                        }
                                        
                                        newIncome.setDescription(test2[2]);
                                        newIncome.setIncome(Double.parseDouble(test2[4]));
                                        newIncome.setUser_id(1);
                                        income.add(newIncome);
                                    
                                }
                            }
                            else{
                                Income newIncome = new Income();
                                String test2[] = test.split(",");
                                
                                if(test2[0].split("/")[0].length() > 1){
                                    
                                    if(test2[0].split("/")[1].length() > 1){
                                        newIncome.setDate(test2[0]);
                                    }
                                    else{
                                        test2[0] = test2[0].split("/")[0] + "/0" + test2[0].split("/")[1] + "/" + test2[0].split("/")[2];
                                        newIncome.setDate(test2[0]);
                                    }
                                    
                                }
                                else{
                                    test2[0] = "0" + test2[0];
                                    if(test2[0].split("/")[1].length() > 1){
                                        newIncome.setDate(test2[0]);
                                    }
                                    else{
                                        test2[0] = test2[0].split("/")[0] + "/0" + test2[0].split("/")[1] + "/" + test2[0].split("/")[2];
                                        newIncome.setDate(test2[0]);
                                    }
                                }
                                
                                newIncome.setDescription(test2[2].replace("\"", "") + "," + test2[3].replace("\"", ""));
                                newIncome.setIncome(Double.parseDouble(test2[5]));
                                newIncome.setUser_id(1);
                                income.add(newIncome);
                                
                            }
                            
                        }
                    }
                    
                }
                else{
                    System.out.println("Test failed on column headers.");
                }
            }
            else{
                System.out.println("Test failed on whole file.");
            }
            
        }catch(Exception e){
            issue.generateIssueReport("ImportException", e, issueTable);
        }
        
        bills = updateImportedBillInfo(oldBills);
        updateImportedBills();
        
        //income = updateImportedIncomeInfo(oldIncome);
        
    }
    
    private ArrayList<Bills> updateImportedBillInfo(ArrayList<Bills> oldBills) throws SQLException{
        
        if(bills.size() != oldBills.size()){
            
            ArrayList<Integer> ids = new ArrayList<>();

            int billId = 1;
            System.out.println("Bill id start");
            for(int i = bills.size() - 1; i >= 0; i--){
                bills.get(i).setId(billId);
                billId++;
            }
            System.out.println("Bill id end");
            

            for(int i = 0; i < oldBills.size(); i++){
                ids.add(oldBills.get(i).getId());
            }

            System.out.println("Bill Removal start");
            for(int i = 0; i < bills.size(); i++){
                for(int j = 0; j < ids.size(); j++){
                    if(bills.get(i).getId() == ids.get(j)){
                        bills.remove(i);
                        i--;
                    }
                }
            }
            System.out.println("Bill Removal end");
            
            System.out.println("Bill check start");
            for(int i = 0; i < oldBills.size(); i++){
                for(int j = 0; j < bills.size(); j++){

                    if(oldBills.get(i).getDescription().contains(bills.get(j).getDescription().replace("\"", ""))){
                        bills.get(j).setCategory(oldBills.get(i).getCategory());
                    }

                }
            }
            System.out.println("Bill check end");

            for(int i = 0; i < bills.size(); i++){

                if(bills.get(i).getCategory().equals("")){
                    bills.get(i).setCategory("No Category Found");
                }

            }
        
        }
        
        System.out.println("");
        System.out.println("");
        
        UpdateExcel ue = new UpdateExcel();
        ue.update(bills, income);
        
        System.out.println("Bills added");
        
        return bills;
    }
    
    private ArrayList<Income> updateImportedIncomeInfo(ArrayList<Income> oldIncome) throws SQLException{
        
        income.remove(income.size() - 1);
        
        if(income.size() != oldIncome.size()){
        
            ArrayList<Integer> ids = new ArrayList<>();

            int id = 1;

            for(int i = income.size() - 1; i >= 0; i--){
                income.get(i).setId(id);
                id++;
            }

            for(int i = 0; i < oldIncome.size(); i++){
                ids.add(oldIncome.get(i).getId());
            }
            System.out.println("Test");
            for(int i = 0; i < income.size(); i++){
                for(int j = 0; j < ids.size(); j++){
                    if(income.get(i).getId() == ids.get(j)){
                        income.remove(i);
                        i--;
                    }
                }
            }
            System.out.println("Test 2");

            for(int i = 0; i < oldIncome.size(); i++){
                for(int j = 0; j < income.size(); j++){

                    if(oldIncome.get(i).getDescription().contains(income.get(j).getDescription().replace("\"", ""))){
                        income.get(j).setCategory(oldIncome.get(i).getCategory());
                    }

                    if(income.get(j).getDescription().contains("External Deposit STATE OF") || income.get(j).getDescription().contains("EXT DEP STATE OF")){
                        income.get(j).setCategory("State Tax Refund");
                    }
                    else if(income.get(j).getDescription().contains("External Deposit IRS TREAS") || income.get(j).getDescription().contains("EXT DEP IRS")){
                        income.get(j).setCategory("Federal Tax Refund");
                    }

                }
            }

            for(int i = 0; i < income.size(); i++){

                if(income.get(i).getCategory().equals("")){
                    income.get(i).setCategory("No Category found");
                }

            }
            
            System.out.println();
            
            System.out.println("Size of new Income: " + income.size());
        
        }
        else{
            System.out.println("The sizes are the same!");
        }
        
        return income;
    }
    
    private void updateImportedBills(){
        
        ArrayList<String> importBillUpdates = new ArrayList<>();
        
        try{
            
            File file = new File("Input File/ExampleBills.xlsx");
            
            FileInputStream input = new FileInputStream(file);
            
            Workbook wb = new XSSFWorkbook(input);
            
            Sheet companySheet = wb.getSheetAt(0);
                        
            Row row;
            
            // Get the bill and income information and populate the corresponding arraylist
            for(int rowIndex = 0; rowIndex <= companySheet.getLastRowNum(); rowIndex++){
                                
                row = companySheet.getRow(rowIndex);
                Cell newCategory = row.getCell(0);
                Cell oldCategory = row.getCell(1);
                
                importBillUpdates.add(newCategory + "someDelimeter" + oldCategory);
                
            }
            
            // Close the Workbook and inputfilestream
            wb.close();
            input.close();
            
        }catch(IOException io){
            
        }
        
        for(int i = 0; i < importBillUpdates.size(); i++){
            
            for(int j = 0; j < bills.size(); j++){
                
                if(bills.get(j).getCategory().equals("No Category Found")){
                    
                    if(bills.get(j).getDescription().contains(importBillUpdates.get(i).split("someDelimeter")[1])){
                        bills.get(j).setCategory(importBillUpdates.get(i).split("someDelimeter")[0]);
                    }
                    
                }
                
            }
        }
                
    }
    // End of Imports
    
    public ArrayList<Bills> getBills(){
        return bills;
    }
    
    public ArrayList<Income> getIncome(){
        return income;
    }
    
}
