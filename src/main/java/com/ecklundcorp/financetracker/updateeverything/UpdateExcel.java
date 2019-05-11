/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecklundcorp.financetracker.updateeverything;

import com.ecklundcorp.financetracker.mycosts.Bills;
import com.ecklundcorp.financetracker.mycosts.Income;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Dillon
 */
public class UpdateExcel {
    
    public void update(ArrayList<Bills> bills, ArrayList<Income> income){
        
        String pathway = "Input File/My Bills and Income.xlsx";
        
        File f = new File(pathway);
        
        try{
                    
            // Create workbook and sheet within workbook
            XSSFWorkbook wb = new XSSFWorkbook();
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
            XSSFCell billUserIdCell = billHeaders.createCell(5);
            
            XSSFCell incomeIdHeaderCell = incomeHeaders.createCell(0);
            XSSFCell incomeDateHeaderCell = incomeHeaders.createCell(1);
            XSSFCell incomeCategoryHeaderCell = incomeHeaders.createCell(2);
            XSSFCell incomeHeaderCell = incomeHeaders.createCell(3);
            XSSFCell incomeDescriptionHeaderCell = incomeHeaders.createCell(4);
            XSSFCell incomeUserIdCell = incomeHeaders.createCell(5);
            
            // Populate the cells of the header row
            billIdHeaderCell.setCellValue("ID");
            billDateHeaderCell.setCellValue("Date");
            billCategoryHeaderCell.setCellValue("Expense Category");
            billExpenseHeaderCell.setCellValue("Expense");
            billDescriptionHeaderCell.setCellValue("Expense Description");
            billUserIdCell.setCellValue("User ID");
            
            incomeIdHeaderCell.setCellValue("ID");
            incomeDateHeaderCell.setCellValue("Date");
            incomeCategoryHeaderCell.setCellValue("Income Category");
            incomeHeaderCell.setCellValue("Income");
            incomeDescriptionHeaderCell.setCellValue("Income Description");
            incomeUserIdCell.setCellValue("User ID");
            
            FileOutputStream resultsOutputFile = new FileOutputStream(pathway);

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
            
            //write this workbook to an OutputStream
            wb.write(resultsOutputFile);
            resultsOutputFile.flush();
            resultsOutputFile.close();
                    
        }catch(IOException io){
                    
        }
        
    }

    
}
