/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecklundcorp.financetracker.gettingbillsandpayments;

import com.ecklundcorp.financetracker.mycosts.Bills;
import com.ecklundcorp.financetracker.mycosts.Income;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Dillon
 */
public class ReadFromExcel {
    
    ArrayList<Bills> bills;
    ArrayList<Income> income;
    SimpleDateFormat dateFormat;
        
    public ReadFromExcel(){
        bills = new ArrayList<>();
        income = new ArrayList<>();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    }
    
    public void read(){
        
        try{
            
            File file = new File("Input File/My Bills and Income.xlsx");
            
            FileInputStream input = new FileInputStream(file);
            
            Workbook wb = new XSSFWorkbook(input);
            
            Sheet billSheet = wb.getSheetAt(0);
            Sheet incomeSheet = wb.getSheetAt(1);
                        
            Row row;
            
            // Get the bill and income information and populate the corresponding arraylist
            for(int rowIndex = 1; rowIndex <= billSheet.getLastRowNum(); rowIndex++){
                                
                Bills info = new Bills();
                row = billSheet.getRow(rowIndex);
                Cell idCell = row.getCell(0);
                Cell dateCell = row.getCell(1);
                Cell categoryCell = row.getCell(2);
                Cell expenseCell = row.getCell(3);
                Cell descriptionCell = row.getCell(4);
                
                info.setId(rowIndex);
                info.setDate(dateCell.getStringCellValue());
                info.setCategory(categoryCell.getStringCellValue());
                info.setExpense(expenseCell.getNumericCellValue());
                info.setDescription(descriptionCell.getStringCellValue());
                
                bills.add(info);
                                
            }
            
            for(int rowIndex = 1; rowIndex <= incomeSheet.getLastRowNum(); rowIndex++){
                                
                Income info = new Income();
                row = incomeSheet.getRow(rowIndex);
                
                Cell idCell = row.getCell(0);
                Cell dateCell = row.getCell(1);
                Cell categoryCell = row.getCell(2);
                Cell incomeCell = row.getCell(3);
                Cell descriptionCell = row.getCell(4);
                Cell userIdCell = row.getCell(5);
                
                info.setId(rowIndex);
                info.setDate(dateCell.getStringCellValue());
                info.setCategory(categoryCell.getStringCellValue());
                info.setIncome(incomeCell.getNumericCellValue());
                info.setDescription(descriptionCell.getStringCellValue());
                
                income.add(info);
                                                
            }
            
            // Close the Workbook and inputfilestream
            wb.close();
            input.close();
            
        }catch(IOException io){
            
        }
        
    }
    
    public ArrayList<Bills> getBills(){
        return bills;
    }
    
    public ArrayList<Income> getIncome(){
        return income;
    }
    
}
