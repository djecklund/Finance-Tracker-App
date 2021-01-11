/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecklundcorp.financetracker.import_export;

import com.ecklundcorp.financetracker.mycosts.Expense;
import com.ecklundcorp.financetracker.exceptions.IssueTracker;
import com.ecklundcorp.financetracker.mycosts.Bills;
import com.ecklundcorp.financetracker.mycosts.Income;
import com.ecklundcorp.financetracker.updateeverything.UpdateExcel;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
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
    public void exportFile(ArrayList<Bills> bills, ArrayList<Income> income, JProgressBar progressBar, JLabel exportProgressLBL, String exportType) throws IOException{
        
        switch(exportType){
            case "Basic Export":
                exportBasicReport(bills, income, progressBar, exportProgressLBL);
                break;
            case "Income/Bills Breakdown Export":
                exportIncomeBillsBreakdownReport(bills, income, progressBar, exportProgressLBL);
                break;
        }
    }
    
    private void exportBasicReport(ArrayList<Bills> bills, ArrayList<Income> income, JProgressBar progressBar, JLabel exportProgressLBL){
        
        Thread thread = new Thread(new Runnable() {

        @Override
        public void run() {
            
            int value = 0;
        
            progressBar.setFont(new Font("MV Boli", Font.BOLD,25));
            
            //progressBar.setString(value + "%");
            progressBar.setValue(value);
            exportProgressLBL.setText("Starting up...");
            progressBar.setStringPainted(true);
            //progressBar.setVisible(true);
            
            int totalSize = bills.size() + income.size();
            int current = 0;
            String pathway = "Export Files/Export of My Bills and Income.xlsx";
        
            File f = new File(pathway);

            XSSFWorkbook wb = null;
            FileOutputStream resultsOutputFile = null;
            
            value += 25;
            //progressBar.setString(value + "%");
            progressBar.setValue(value);
            
            try{
                
                value += 25;
                //progressBar.setString(value + "%");
                progressBar.setValue(value);
                exportProgressLBL.setText("Creating Bill Sheet...");
            
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

                    // Place the data in each cell of each row
                    idCell.setCellValue(bills.get(i).getId());
                    dateCell.setCellValue(bills.get(i).getDate());
                    categoryCell.setCellValue(bills.get(i).getCategory());

                    expenseCell.setCellType(CellType.NUMERIC);
                    expenseCell.setCellValue(bills.get(i).getExpense());
                    descriptionCell.setCellValue(bills.get(i).getDescription());
                    
                    current++;
                    progressBar.setValue(current);
                    
                }
                
                value += 25;
                //progressBar.setString(value + "%");
                progressBar.setValue(value);
                exportProgressLBL.setText("Creating Income Sheet...");
                
                for(int i = 0; i < income.size(); i++){

                    // Create each row that the data will go into
                    XSSFRow row = incomeSheet.createRow(i + 1);

                    // Create the cells for each row
                    XSSFCell idCell = row.createCell(0);
                    XSSFCell dateCell = row.createCell(1);
                    XSSFCell categoryCell = row.createCell(2);
                    XSSFCell incomeCell = row.createCell(3);
                    XSSFCell descriptionCell = row.createCell(4);

                    // Place the data in each cell of each row
                    idCell.setCellValue(income.get(i).getId());
                    dateCell.setCellValue(income.get(i).getDate());

                    categoryCell.setCellValue(income.get(i).getCategory());

                    incomeCell.setCellType(CellType.NUMERIC);
                    incomeCell.setCellValue(income.get(i).getIncome());
                    descriptionCell.setCellValue(income.get(i).getDescription());

                }
                
                value += 10;
                //progressBar.setString(value + "%");
                progressBar.setValue(value);
                exportProgressLBL.setText("Finishing up report creation...");
                
                // Create the header row
                XSSFRow billHeaders = billSheet.createRow(0);
                XSSFRow incomeHeaders = incomeSheet.createRow(0);

                // Create the cells of the header row
                XSSFCell billIdHeaderCell = billHeaders.createCell(0);
                XSSFCell billDateHeaderCell = billHeaders.createCell(1);
                XSSFCell billCategoryHeaderCell = billHeaders.createCell(2);
                XSSFCell billExpenseHeaderCell = billHeaders.createCell(3);
                XSSFCell billDescriptionHeaderCell = billHeaders.createCell(4);

                XSSFCell incomeIdHeaderCell = incomeHeaders.createCell(0);
                XSSFCell incomeDateHeaderCell = incomeHeaders.createCell(1);
                XSSFCell incomeCategoryHeaderCell = incomeHeaders.createCell(2);
                XSSFCell incomeHeaderCell = incomeHeaders.createCell(3);
                XSSFCell incomeDescriptionHeaderCell = incomeHeaders.createCell(4);

                // Populate the cells of the header row
                billIdHeaderCell.setCellValue("ID");
                billDateHeaderCell.setCellValue("Date");
                billCategoryHeaderCell.setCellValue("Expense Category");
                billExpenseHeaderCell.setCellValue("Expense");
                billDescriptionHeaderCell.setCellValue("Expense Description");
                billDateHeaderCell.setCellValue("Date");

                incomeIdHeaderCell.setCellValue("ID");
                incomeDateHeaderCell.setCellValue("Date");
                incomeCategoryHeaderCell.setCellValue("Income Category");
                incomeHeaderCell.setCellValue("Income");
                incomeDescriptionHeaderCell.setCellValue("Income Description");

                resultsOutputFile = new FileOutputStream(f);

                // Resize each column to fit the data
                billSheet.autoSizeColumn(0);
                billSheet.autoSizeColumn(1);
                billSheet.autoSizeColumn(2);
                billSheet.autoSizeColumn(3);
                billSheet.autoSizeColumn(4);

                incomeSheet.autoSizeColumn(0);
                incomeSheet.autoSizeColumn(1);
                incomeSheet.autoSizeColumn(2);
                incomeSheet.autoSizeColumn(3);
                incomeSheet.autoSizeColumn(4);

                wb.write(resultsOutputFile);
                
                value += 15;
                progressBar.setValue(value);
                exportProgressLBL.setText("Report created.");
                
                JOptionPane.showMessageDialog(null, "The information has been exported.");

            }catch(IOException io){
                issue.generateIssueReport("ExportIOException", io);
            }
            finally{
                if(resultsOutputFile != null){
                    try {
                        resultsOutputFile.flush();
                    } catch (IOException ex) {
                        Logger.getLogger(Import_Export.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        resultsOutputFile.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Import_Export.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                if(wb != null){
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
    
    private void exportIncomeBillsBreakdownReport(ArrayList<Bills> bills, ArrayList<Income> income, JProgressBar exportProgressBar, JLabel exportProgressLBL){
        
        ArrayList<Expense> categories = getCategories(bills);
        
        ExportBillsBreakdown ebb = new ExportBillsBreakdown();
        ebb.exportIncomeBillsBreakdownReport(categories, exportProgressBar, exportProgressLBL);
        
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
                                    System.out.println("Working on getting new Bills...");
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
                                            bills.add(bill);

                                        }
                                    }
                                        
                                else{
                                	
	                                    System.out.println("Possible new Income...");
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
        
        income = updateImportedIncomeInfo(oldIncome);
        updateImportedIncome();
        
    }
    
    private ArrayList<Bills> updateImportedBillInfo(ArrayList<Bills> oldBills) throws SQLException{
        
        
        for(int i = 0; i < oldBills.size(); i++){
            for(int j = 0; j < bills.size(); j++){
                if(oldBills.get(i).getDate().equals(bills.get(j).getDate()) 
                        && oldBills.get(i).getDescription().equals(bills.get(j).getDescription())
                        && oldBills.get(i).getExpense() == bills.get(j).getExpense()){
                    
                    bills.remove(j);
                    j--;
                }
            }
        }
        
        for(int i = 0; i < bills.size(); i++){
            oldBills.add(i,bills.get(i));
        }
        
        for(int i = 0; i < oldBills.size(); i++){
            oldBills.get(i).setId((i + 1));
        }
        
        return oldBills;
    }
    
    private ArrayList<Income> updateImportedIncomeInfo(ArrayList<Income> oldIncome) throws SQLException{
        
        for(int i = 0; i < oldIncome.size(); i++){
            for(int j = 0; j < income.size(); j++){
                if(oldIncome.get(i).getDate().equals(income.get(j).getDate()) 
                        && oldIncome.get(i).getDescription().equals(income.get(j).getDescription())
                        && oldIncome.get(i).getIncome() == income.get(j).getIncome()){
                    
                    income.remove(j);
                    j--;
                }
            }
        }
        
        for(int i = 0; i < income.size(); i++){
            oldIncome.add(i,income.get(i));
        }
        
        for(int i = 0; i < oldIncome.size(); i++){
            oldIncome.get(i).setId((i + 1));
        }
        
        return oldIncome;
        
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
                                
                row = companySheet.getRow(rowIndex + 1);
                Cell newCategory = row.getCell(0);
                Cell oldCategory = row.getCell(1);
                
                importBillUpdates.add(newCategory + "someDelimeter" + oldCategory);
                
            }
            
            // Close the Workbook and inputfilestream
            wb.close();
            input.close();
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        System.out.println("Import bills");
        for(int i = 0; i < importBillUpdates.size(); i++){
            
            for(int j = 0; j < bills.size(); j++){
                
                if(bills.get(j).getCategory().equals("No Category Found") || bills.get(j).getCategory().equals("")){
                    
                    if(bills.get(j).getDescription().contains(importBillUpdates.get(i).split("someDelimeter")[1])){
                    	System.out.println("Test of import bill update...");
                        bills.get(j).setCategory(importBillUpdates.get(i).split("someDelimeter")[0]);
                    }
                    else {
                    	bills.get(j).setCategory("No Category Found");
                    }
                    
                }
                
            }
        }
                
    }
    
    private void updateImportedIncome(){
        
        ArrayList<String> importIncomeUpdates = new ArrayList<>();
        
        try{
            
            File file = new File("Input File/ExampleIncome.xlsx");
            
            FileInputStream input = new FileInputStream(file);
            
            Workbook wb = new XSSFWorkbook(input);
            
            Sheet companySheet = wb.getSheetAt(0);
                        
            Row row;
            
            // Get the bill and income information and populate the corresponding arraylist
            for(int rowIndex = 0; rowIndex <= companySheet.getLastRowNum(); rowIndex++){
                                
                row = companySheet.getRow(rowIndex + 1);
                Cell newCategory = row.getCell(0);
                Cell oldCategory = row.getCell(1);
                
                importIncomeUpdates.add(newCategory + "someDelimeter" + oldCategory);
                
            }
            
            // Close the Workbook and inputfilestream
            wb.close();
            input.close();
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        System.out.println("Import Income");
        for(int i = 0; i < importIncomeUpdates.size(); i++){
            
            for(int j = 0; j < income.size(); j++){
                
                if(income.get(j).getCategory().equals("No Category Found") || income.get(j).getCategory().equals("")){
                    
                    if(income.get(j).getDescription().contains(importIncomeUpdates.get(i).split("someDelimeter")[1])){
                    	System.out.println("Test of import income update...");
                        income.get(j).setCategory(importIncomeUpdates.get(i).split("someDelimeter")[0]);
                    }
                    else {
                    	income.get(j).setCategory("No Category Found");
                    }
                    
                }
                
            }
        }
                
    }
    // End of Imports
    
    // Other functions
    private ArrayList<Expense> getCategories(ArrayList<Bills> bills){
        
        ArrayList<Expense> categories = new ArrayList<>();
        ArrayList<String> cat = new ArrayList<>();
        
        for(int i = 0; i < bills.size(); i++){
            cat.add(bills.get(i).getCategory() + " someDelimeter " + bills.get(i).getDate().split("/")[0] + "/" + bills.get(i).getDate().split("/")[2]);
        }
        
        Set<String> temp = new HashSet<>();
        temp.addAll(cat);
        cat.removeAll(cat);
        cat.addAll(temp);
        
        for(int i = 0; i < cat.size(); i++){
            Expense ex = new Expense();
            ex.setCategory(cat.get(i).split(" someDelimeter ")[0]);
            ex.setDate(cat.get(i).split(" someDelimeter ")[1]);
            categories.add(ex);
        }
        
        for(int i = 0; i < bills.size(); i++){
            for(int j = 0; j < categories.size(); j++){
                
                String billDate = bills.get(i).getDate().split("/")[0] + "/" + bills.get(i).getDate().split("/")[2];
                if(categories.get(j).getCategory().equals(bills.get(i).getCategory()) && categories.get(j).getDate().equals(billDate)){
                    categories.get(j).setExpense(categories.get(j).getExpense() + bills.get(i).getExpense());
                }
            
            }
        }
        
        return categories;
        
    }
    
    // End of other functions
    
    public ArrayList<Bills> getBills(){
        return bills;
    }
    
    public ArrayList<Income> getIncome(){
        return income;
    }
    
}
