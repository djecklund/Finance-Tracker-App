/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecklundcorp.financetracker.import_export;

import com.ecklundcorp.financetracker.mycosts.Expense;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Dillon
 */
public class ExportBillsBreakdown {
    
    public void exportIncomeBillsBreakdownReport(ArrayList<Expense> expenses, JProgressBar exportProgressBar, JLabel exportProgressLBL){
        
        Thread thread = new Thread(new Runnable() {

        @Override
        public void run() {
        
            int value = 0;

            exportProgressBar.setFont(new Font("MV Boli", Font.BOLD,25));
            
            exportProgressBar.setString(value + "%");
            exportProgressBar.setValue(value);
            exportProgressLBL.setText("Starting up...");
            exportProgressBar.setVisible(true);
            exportProgressBar.setStringPainted(true);

            double janTotal = 0.00;
            double febTotal = 0.00;
            double marTotal = 0.00;
            double aprTotal = 0.00;
            double mayTotal = 0.00;
            double junTotal = 0.00;
            double julyTotal = 0.00;
            double augTotal = 0.00;
            double septTotal = 0.00;
            double octTotal = 0.00;
            double novTotal = 0.00;
            double decTotal = 0.00;

            int janCategories = 0;
            int febCategories = 0;
            int marCategories = 0;
            int aprCategories = 0;
            int mayCategories = 0;
            int juneCategories = 0;
            int julyCategories = 0;
            int augCategories = 0;
            int septCategories = 0;
            int octCategories = 0;
            int novCategories = 0;
            int decCategories = 0;

            String year = "2020";

            exportProgressLBL.setText("Gathering Export Data for " + year + "...");

            for(int i = 0; i < expenses.size(); i++){

                if(expenses.get(i).getDate().split("/")[0].contains("01") && expenses.get(i).getDate().split("/")[1].contains(year)){
                    janTotal += expenses.get(i).getExpense();
                }
                else if(expenses.get(i).getDate().split("/")[0].equals("02") && expenses.get(i).getDate().split("/")[1].contains(year)){
                    febTotal += expenses.get(i).getExpense();
                }
                else if(expenses.get(i).getDate().split("/")[0].contains("03") && expenses.get(i).getDate().split("/")[1].contains(year)){
                    marTotal += expenses.get(i).getExpense();
                }
                else if(expenses.get(i).getDate().split("/")[0].contains("04") && expenses.get(i).getDate().split("/")[1].contains(year)){
                    aprTotal += expenses.get(i).getExpense();
                }
                else if(expenses.get(i).getDate().split("/")[0].contains("05") && expenses.get(i).getDate().split("/")[1].contains(year)){
                    mayTotal += expenses.get(i).getExpense();
                }
                else if(expenses.get(i).getDate().split("/")[0].contains("06") && expenses.get(i).getDate().split("/")[1].contains(year)){
                    junTotal += expenses.get(i).getExpense();
                }
                else if(expenses.get(i).getDate().split("/")[0].contains("07") && expenses.get(i).getDate().split("/")[1].contains(year)){
                    julyTotal += expenses.get(i).getExpense();
                }
                else if(expenses.get(i).getDate().split("/")[0].contains("08") && expenses.get(i).getDate().split("/")[1].contains(year)){
                    augTotal += expenses.get(i).getExpense();
                }
                else if(expenses.get(i).getDate().split("/")[0].contains("09") && expenses.get(i).getDate().split("/")[1].contains(year)){
                    septTotal += expenses.get(i).getExpense();
                }
                else if(expenses.get(i).getDate().split("/")[0].contains("10") && expenses.get(i).getDate().split("/")[1].contains(year)){
                    octTotal += expenses.get(i).getExpense();
                }
                else if(expenses.get(i).getDate().split("/")[0].contains("11") && expenses.get(i).getDate().split("/")[1].contains(year)){
                    novTotal += expenses.get(i).getExpense();
                }
                else if(expenses.get(i).getDate().split("/")[0].contains("12") && expenses.get(i).getDate().split("/")[1].contains(year)){
                    decTotal += expenses.get(i).getExpense();
                }

            }

            exportProgressLBL.setText("Finished gathering data for " + year);

            String pathway = "Export Files/Export of Income_Bills Breakdown.xlsx";

                File f = new File(pathway);

                XSSFWorkbook wb = null;
                FileOutputStream resultsOutputFile = null;

                try{

                    // Create workbook and sheet within workbook
                    wb = new XSSFWorkbook();
                    XSSFSheet janSheet = wb.createSheet("January Breakdown");
                    XSSFSheet febSheet = wb.createSheet("Febuary Breakdown");
                    XSSFSheet marchSheet = wb.createSheet("March Breakdown");
                    XSSFSheet aprSheet = wb.createSheet("April Breakdown");
                    XSSFSheet maySheet = wb.createSheet("May Breakdown");
                    XSSFSheet juneSheet = wb.createSheet("June Breakdown");
                    XSSFSheet julySheet = wb.createSheet("July Breakdown");
                    XSSFSheet augSheet = wb.createSheet("August Breakdown");
                    XSSFSheet septSheet = wb.createSheet("September Breakdown");
                    XSSFSheet octSheet = wb.createSheet("October Breakdown");
                    XSSFSheet novSheet = wb.createSheet("November Breakdown");
                    XSSFSheet decSheet = wb.createSheet("December Breakdown");

                    XSSFCellStyle percentStyle = wb.createCellStyle();
                    percentStyle.setDataFormat(wb.createDataFormat().getFormat("0.00%"));

                    DataFormat fmt = wb.createDataFormat();

                    XSSFFont boldFont = wb.createFont();
                    boldFont.setBold(true);

                    XSSFCellStyle moneyStyle = wb.createCellStyle();
                    moneyStyle.setDataFormat(fmt.getFormat("$###,##0.00"));

                    XSSFCellStyle totalMoneyStyle = wb.createCellStyle();
                    totalMoneyStyle.setDataFormat(fmt.getFormat("$###,##0.00"));
                    totalMoneyStyle.setFont(boldFont);

                    int currentRow = 2;

                    exportProgressLBL.setText("Creating report for January " + year);

                    // Write the data into the spreadsheet
                    for(int i = 0; i < expenses.size(); i++){

                        if(expenses.get(i).getDate().split("/")[1].contains(year)){

                            if(expenses.get(i).getDate().split("/")[0].contains("01")){

                                // Create each row that the data will go into
                                XSSFRow row = janSheet.createRow(currentRow);

                                // Create the cells for each row
                                XSSFCell expenseCategoryCell = row.createCell(0);
                                XSSFCell moneySpentCell = row.createCell(1);
                                XSSFCell percentOfOverallExpenseCell = row.createCell(2);

                                // Place the data in each cell of each row
                                expenseCategoryCell.setCellValue(expenses.get(i).getCategory());
                                moneySpentCell.setCellStyle(moneyStyle);
                                moneySpentCell.setCellValue(expenses.get(i).getExpense());
                                percentOfOverallExpenseCell.setCellType(CellType.FORMULA);
                                String formula = "B" + (currentRow + 1) + " / " + janTotal;
                                percentOfOverallExpenseCell.setCellStyle(percentStyle);
                                percentOfOverallExpenseCell.setCellFormula(formula);

                                currentRow++;
                                janCategories++;

                            }

                        }

                    }

                    value += 8;
                    exportProgressBar.setString(value + "%");
                    exportProgressBar.setValue(value);
                    exportProgressLBL.setText("Creating report for Febuary " + year);

                    currentRow = 2;

                    // Febuary
                    for(int i = 0; i < expenses.size(); i++){

                        if(expenses.get(i).getDate().split("/")[1].contains(year)){

                            if(expenses.get(i).getDate().split("/")[0].contains("02")){

                                // Create each row that the data will go into
                                XSSFRow row = febSheet.createRow(currentRow);

                                // Create the cells for each row
                                XSSFCell expenseCategoryCell = row.createCell(0);
                                XSSFCell moneySpentCell = row.createCell(1);
                                XSSFCell percentOfOverallExpenseCell = row.createCell(2);

                                // Place the data in each cell of each row
                                expenseCategoryCell.setCellValue(expenses.get(i).getCategory());
                                moneySpentCell.setCellStyle(moneyStyle);
                                moneySpentCell.setCellValue(expenses.get(i).getExpense());
                                percentOfOverallExpenseCell.setCellType(CellType.FORMULA);
                                String formula = "B" + (currentRow + 1) + " / " + febTotal;
                                percentOfOverallExpenseCell.setCellStyle(percentStyle);
                                percentOfOverallExpenseCell.setCellFormula(formula);

                                currentRow++;
                                febCategories++;
                            }

                        }

                    }


                    // March

                    currentRow = 2;

                    value += 8;
                    exportProgressBar.setString(value + "%");
                    exportProgressBar.setValue(value);
                    exportProgressLBL.setText("Creating report for March " + year);

                    for(int i = 0; i < expenses.size(); i++){

                        if(expenses.get(i).getDate().split("/")[1].contains(year)){

                            if(expenses.get(i).getDate().split("/")[0].contains("03")){

                                // Create each row that the data will go into
                                XSSFRow row = marchSheet.createRow(currentRow);

                                // Create the cells for each row
                                XSSFCell expenseCategoryCell = row.createCell(0);
                                XSSFCell moneySpentCell = row.createCell(1);
                                XSSFCell percentOfOverallExpenseCell = row.createCell(2);

                                // Place the data in each cell of each row
                                expenseCategoryCell.setCellValue(expenses.get(i).getCategory());
                                moneySpentCell.setCellStyle(moneyStyle);
                                moneySpentCell.setCellValue(expenses.get(i).getExpense());
                                percentOfOverallExpenseCell.setCellType(CellType.FORMULA);
                                String formula = "B" + (currentRow + 1) + " / " + marTotal;
                                percentOfOverallExpenseCell.setCellStyle(percentStyle);
                                percentOfOverallExpenseCell.setCellFormula(formula);

                                currentRow++;
                                marCategories++;

                            }

                        }

                    }


                    // April

                    currentRow = 2;

                    value += 8;
                    exportProgressBar.setString(value + "%");
                    exportProgressBar.setValue(value);
                    exportProgressLBL.setText("Creating report for April " + year);

                    for(int i = 0; i < expenses.size(); i++){

                        if(expenses.get(i).getDate().split("/")[1].contains(year)){

                            if(expenses.get(i).getDate().split("/")[0].contains("04")){

                                // Create each row that the data will go into
                                XSSFRow row = aprSheet.createRow(currentRow);

                                // Create the cells for each row
                                XSSFCell expenseCategoryCell = row.createCell(0);
                                XSSFCell moneySpentCell = row.createCell(1);
                                XSSFCell percentOfOverallExpenseCell = row.createCell(2);

                                // Place the data in each cell of each row
                                expenseCategoryCell.setCellValue(expenses.get(i).getCategory());
                                moneySpentCell.setCellStyle(moneyStyle);
                                moneySpentCell.setCellValue(expenses.get(i).getExpense());
                                percentOfOverallExpenseCell.setCellType(CellType.FORMULA);
                                String formula = "B" + (currentRow + 1) + " / " + aprTotal;
                                percentOfOverallExpenseCell.setCellStyle(percentStyle);
                                percentOfOverallExpenseCell.setCellFormula(formula);

                                currentRow++;
                                aprCategories++;

                            }

                        }

                    }


                    // May

                    currentRow = 2;

                    value += 8;
                    exportProgressBar.setString(value + "%");
                    exportProgressBar.setValue(value);
                    exportProgressLBL.setText("Creating report for May " + year);

                    for(int i = 0; i < expenses.size(); i++){

                        if(expenses.get(i).getDate().split("/")[1].contains(year)){

                            if(expenses.get(i).getDate().split("/")[0].contains("05")){

                                // Create each row that the data will go into
                                XSSFRow row = maySheet.createRow(currentRow);

                                // Create the cells for each row
                                XSSFCell expenseCategoryCell = row.createCell(0);
                                XSSFCell moneySpentCell = row.createCell(1);
                                XSSFCell percentOfOverallExpenseCell = row.createCell(2);

                                // Place the data in each cell of each row
                                expenseCategoryCell.setCellValue(expenses.get(i).getCategory());
                                moneySpentCell.setCellStyle(moneyStyle);
                                moneySpentCell.setCellValue(expenses.get(i).getExpense());
                                percentOfOverallExpenseCell.setCellType(CellType.FORMULA);
                                String formula = "B" + (currentRow + 1) + " / " + mayTotal;
                                percentOfOverallExpenseCell.setCellStyle(percentStyle);
                                percentOfOverallExpenseCell.setCellFormula(formula);

                                currentRow++;
                                mayCategories++;

                            }

                        }

                    }


                    // June

                    currentRow = 2;

                    value += 8;
                    exportProgressBar.setString(value + "%");
                    exportProgressBar.setValue(value);
                    exportProgressLBL.setText("Creating report for June " + year);

                    for(int i = 0; i < expenses.size(); i++){

                        if(expenses.get(i).getDate().split("/")[1].contains(year)){

                            if(expenses.get(i).getDate().split("/")[0].contains("06")){

                                // Create each row that the data will go into
                                XSSFRow row = juneSheet.createRow(currentRow);

                                // Create the cells for each row
                                XSSFCell expenseCategoryCell = row.createCell(0);
                                XSSFCell moneySpentCell = row.createCell(1);
                                XSSFCell percentOfOverallExpenseCell = row.createCell(2);

                                // Place the data in each cell of each row
                                expenseCategoryCell.setCellValue(expenses.get(i).getCategory());
                                moneySpentCell.setCellStyle(moneyStyle);
                                moneySpentCell.setCellValue(expenses.get(i).getExpense());
                                percentOfOverallExpenseCell.setCellType(CellType.FORMULA);
                                String formula = "B" + (currentRow + 1) + " / " + junTotal;
                                percentOfOverallExpenseCell.setCellStyle(percentStyle);
                                percentOfOverallExpenseCell.setCellFormula(formula);

                                currentRow++;
                                juneCategories++;

                            }

                        }

                    }


                    // July

                    currentRow = 2;

                    value += 8;
                    exportProgressBar.setString(value + "%");
                    exportProgressBar.setValue(value);
                    exportProgressLBL.setText("Creating report for July " + year);

                    for(int i = 0; i < expenses.size(); i++){

                        if(expenses.get(i).getDate().split("/")[1].contains(year)){

                            if(expenses.get(i).getDate().split("/")[0].contains("07")){

                                // Create each row that the data will go into
                                XSSFRow row = julySheet.createRow(currentRow);

                                // Create the cells for each row
                                XSSFCell expenseCategoryCell = row.createCell(0);
                                XSSFCell moneySpentCell = row.createCell(1);
                                XSSFCell percentOfOverallExpenseCell = row.createCell(2);

                                // Place the data in each cell of each row
                                expenseCategoryCell.setCellValue(expenses.get(i).getCategory());
                                moneySpentCell.setCellStyle(moneyStyle);
                                moneySpentCell.setCellValue(expenses.get(i).getExpense());
                                percentOfOverallExpenseCell.setCellType(CellType.FORMULA);
                                String formula = "B" + (currentRow + 1) + " / " + julyTotal;
                                percentOfOverallExpenseCell.setCellStyle(percentStyle);
                                percentOfOverallExpenseCell.setCellFormula(formula);

                                currentRow++;
                                julyCategories++;

                            }

                        }

                    }


                    // August

                    currentRow = 2;

                    value += 8;
                    exportProgressBar.setString(value + "%");
                    exportProgressBar.setValue(value);
                    exportProgressLBL.setText("Creating report for August " + year);

                    for(int i = 0; i < expenses.size(); i++){

                        if(expenses.get(i).getDate().split("/")[1].contains(year)){

                            if(expenses.get(i).getDate().split("/")[0].contains("08")){

                                // Create each row that the data will go into
                                XSSFRow row = augSheet.createRow(currentRow);

                                // Create the cells for each row
                                XSSFCell expenseCategoryCell = row.createCell(0);
                                XSSFCell moneySpentCell = row.createCell(1);
                                XSSFCell percentOfOverallExpenseCell = row.createCell(2);

                                // Place the data in each cell of each row
                                expenseCategoryCell.setCellValue(expenses.get(i).getCategory());
                                moneySpentCell.setCellStyle(moneyStyle);
                                moneySpentCell.setCellValue(expenses.get(i).getExpense());
                                percentOfOverallExpenseCell.setCellType(CellType.FORMULA);
                                String formula = "B" + (currentRow + 1) + " / " + augTotal;
                                percentOfOverallExpenseCell.setCellStyle(percentStyle);
                                percentOfOverallExpenseCell.setCellFormula(formula);

                                currentRow++;
                                augCategories++;

                            }

                        }

                    }


                    // September

                    currentRow = 2;

                    value += 8;
                    exportProgressBar.setString(value + "%");
                    exportProgressBar.setValue(value);
                    exportProgressLBL.setText("Creating report for September " + year);

                    for(int i = 0; i < expenses.size(); i++){

                        if(expenses.get(i).getDate().split("/")[1].contains(year)){

                            if(expenses.get(i).getDate().split("/")[0].contains("09")){

                                // Create each row that the data will go into
                                XSSFRow row = septSheet.createRow(currentRow);

                                // Create the cells for each row
                                XSSFCell expenseCategoryCell = row.createCell(0);
                                XSSFCell moneySpentCell = row.createCell(1);
                                XSSFCell percentOfOverallExpenseCell = row.createCell(2);

                                // Place the data in each cell of each row
                                expenseCategoryCell.setCellValue(expenses.get(i).getCategory());
                                moneySpentCell.setCellStyle(moneyStyle);
                                moneySpentCell.setCellValue(expenses.get(i).getExpense());
                                percentOfOverallExpenseCell.setCellType(CellType.FORMULA);
                                String formula = "B" + (currentRow + 1) + " / " + septTotal;
                                percentOfOverallExpenseCell.setCellStyle(percentStyle);
                                percentOfOverallExpenseCell.setCellFormula(formula);

                                currentRow++;
                                septCategories++;

                            }

                        }

                    }


                    // October

                    currentRow = 2;

                    value += 8;
                    exportProgressBar.setString(value + "%");
                    exportProgressBar.setValue(value);
                    exportProgressLBL.setText("Creating report for October " + year);

                    for(int i = 0; i < expenses.size(); i++){

                        if(expenses.get(i).getDate().split("/")[1].contains(year)){

                            if(expenses.get(i).getDate().split("/")[0].contains("10")){

                                // Create each row that the data will go into
                                XSSFRow row = octSheet.createRow(currentRow);

                                // Create the cells for each row
                                XSSFCell expenseCategoryCell = row.createCell(0);
                                XSSFCell moneySpentCell = row.createCell(1);
                                XSSFCell percentOfOverallExpenseCell = row.createCell(2);

                                // Place the data in each cell of each row
                                expenseCategoryCell.setCellValue(expenses.get(i).getCategory());
                                moneySpentCell.setCellStyle(moneyStyle);
                                moneySpentCell.setCellValue(expenses.get(i).getExpense());
                                percentOfOverallExpenseCell.setCellType(CellType.FORMULA);
                                String formula = "B" + (currentRow + 1) + " / " + octTotal;
                                percentOfOverallExpenseCell.setCellStyle(percentStyle);
                                percentOfOverallExpenseCell.setCellFormula(formula);

                                currentRow++;
                                octCategories++;

                            }

                        }

                    }


                    // November

                    currentRow = 2;

                    value += 8;
                    exportProgressBar.setString(value + "%");
                    exportProgressBar.setValue(value);
                    exportProgressLBL.setText("Creating report for November " + year);

                    for(int i = 0; i < expenses.size(); i++){

                        if(expenses.get(i).getDate().split("/")[1].contains(year)){

                            if(expenses.get(i).getDate().split("/")[0].contains("11")){

                                // Create each row that the data will go into
                                XSSFRow row = novSheet.createRow(currentRow);

                                // Create the cells for each row
                                XSSFCell expenseCategoryCell = row.createCell(0);
                                XSSFCell moneySpentCell = row.createCell(1);
                                XSSFCell percentOfOverallExpenseCell = row.createCell(2);

                                // Place the data in each cell of each row
                                expenseCategoryCell.setCellValue(expenses.get(i).getCategory());
                                moneySpentCell.setCellStyle(moneyStyle);
                                moneySpentCell.setCellValue(expenses.get(i).getExpense());
                                percentOfOverallExpenseCell.setCellType(CellType.FORMULA);
                                String formula = "B" + (currentRow + 1) + " / " + novTotal;
                                percentOfOverallExpenseCell.setCellStyle(percentStyle);
                                percentOfOverallExpenseCell.setCellFormula(formula);

                                currentRow++;
                                novCategories++;

                            }

                        }

                    }


                    // December

                    currentRow = 2;

                    value += 8;
                    exportProgressBar.setString(value + "%");
                    exportProgressBar.setValue(value);
                    exportProgressLBL.setText("Creating report for December " + year);

                    for(int i = 0; i < expenses.size(); i++){

                        if(expenses.get(i).getDate().split("/")[1].contains(year)){

                            if(expenses.get(i).getDate().split("/")[0].contains("12")){

                                // Create each row that the data will go into
                                XSSFRow row = decSheet.createRow(currentRow);

                                // Create the cells for each row
                                XSSFCell expenseCategoryCell = row.createCell(0);
                                XSSFCell moneySpentCell = row.createCell(1);
                                XSSFCell percentOfOverallExpenseCell = row.createCell(2);

                                // Place the data in each cell of each row
                                expenseCategoryCell.setCellValue(expenses.get(i).getCategory());
                                moneySpentCell.setCellStyle(moneyStyle);
                                moneySpentCell.setCellValue(expenses.get(i).getExpense());
                                percentOfOverallExpenseCell.setCellType(CellType.FORMULA);
                                String formula = "B" + (currentRow + 1) + " / " + decTotal;
                                percentOfOverallExpenseCell.setCellStyle(percentStyle);
                                percentOfOverallExpenseCell.setCellFormula(formula);

                                currentRow++;
                                decCategories++;

                            }

                        }

                    }

                    // Create the header row
                    XSSFRow janYaarBillHeaders = janSheet.createRow(0);
                    XSSFRow febYaarBillHeaders = febSheet.createRow(0);
                    XSSFRow marchYaarBillHeaders = marchSheet.createRow(0);
                    XSSFRow aprYaarBillHeaders = aprSheet.createRow(0);
                    XSSFRow mayYaarBillHeaders = maySheet.createRow(0);
                    XSSFRow juneYaarBillHeaders = juneSheet.createRow(0);
                    XSSFRow julyYaarBillHeaders = julySheet.createRow(0);
                    XSSFRow augYaarBillHeaders = augSheet.createRow(0);
                    XSSFRow septYaarBillHeaders = septSheet.createRow(0);
                    XSSFRow octYaarBillHeaders = octSheet.createRow(0);
                    XSSFRow novYaarBillHeaders = novSheet.createRow(0);
                    XSSFRow decYaarBillHeaders = decSheet.createRow(0);

                    XSSFRow janBillHeaders = janSheet.createRow(1);
                    XSSFRow febBillHeaders = febSheet.createRow(1);
                    XSSFRow marchBillHeaders = marchSheet.createRow(1);
                    XSSFRow aprBillHeaders = aprSheet.createRow(1);
                    XSSFRow mayBillHeaders = maySheet.createRow(1);
                    XSSFRow juneBillHeaders = juneSheet.createRow(1);
                    XSSFRow julyBillHeaders = julySheet.createRow(1);
                    XSSFRow augBillHeaders = augSheet.createRow(1);
                    XSSFRow septBillHeaders = septSheet.createRow(1);
                    XSSFRow octBillHeaders = octSheet.createRow(1);
                    XSSFRow novBillHeaders = novSheet.createRow(1);
                    XSSFRow decBillHeaders = decSheet.createRow(1);

                    XSSFRow janTotalRow = janSheet.createRow((janCategories + 2));
                    XSSFRow febTotalRow = febSheet.createRow(febCategories + 2);
                    XSSFRow marTotalRow = marchSheet.createRow(marCategories + 2);
                    XSSFRow aprTotalRow = aprSheet.createRow(aprCategories + 2);
                    XSSFRow mayTotalRow = maySheet.createRow(mayCategories + 2);
                    XSSFRow juneTotalRow = juneSheet.createRow(juneCategories + 2);
                    XSSFRow julyTotalRow = julySheet.createRow(julyCategories + 2);
                    XSSFRow augTotalRow = augSheet.createRow(augCategories + 2);
                    XSSFRow septTotalRow = septSheet.createRow(septCategories + 2);
                    XSSFRow octTotalRow = octSheet.createRow(octCategories + 2);
                    XSSFRow novTotalRow = novSheet.createRow(novCategories + 2);
                    XSSFRow decTotalRow = decSheet.createRow(decCategories + 2);

                    // Create the cells of the header row
                    XSSFCell janYearHeaderCell = janYaarBillHeaders.createCell(0);
                    XSSFCell expenseCategoryHeaderCell = janBillHeaders.createCell(0);
                    XSSFCell moneySpentHeaderCell = janBillHeaders.createCell(1);
                    XSSFCell percentOfOverallExpenseHeaderCell = janBillHeaders.createCell(2);
                    XSSFCell tmp1 = janTotalRow.createCell(0);
                    XSSFCell janTotalExpense = janTotalRow.createCell(1);

                    XSSFCell febYearHeaderCell = febYaarBillHeaders.createCell(0);
                    XSSFCell expenseCategoryHeaderCell2 = febBillHeaders.createCell(0);
                    XSSFCell moneySpentHeaderCell2 = febBillHeaders.createCell(1);
                    XSSFCell percentOfOverallExpenseHeaderCell2 = febBillHeaders.createCell(2);
                    XSSFCell tmp2 = febTotalRow.createCell(0);
                    XSSFCell febTotalExpense = febTotalRow.createCell(1);

                    XSSFCell marchYearHeaderCell = marchYaarBillHeaders.createCell(0);
                    XSSFCell expenseCategoryHeaderCell3 = marchBillHeaders.createCell(0);
                    XSSFCell moneySpentHeaderCell3 = marchBillHeaders.createCell(1);
                    XSSFCell percentOfOverallExpenseHeaderCell3 = marchBillHeaders.createCell(2);
                    XSSFCell tmp3 = marTotalRow.createCell(0);
                    XSSFCell marTotalExpense = marTotalRow.createCell(1);

                    XSSFCell aprYearHeaderCell = aprYaarBillHeaders.createCell(0);
                    XSSFCell expenseCategoryHeaderCell4 = aprBillHeaders.createCell(0);
                    XSSFCell moneySpentHeaderCell4 = aprBillHeaders.createCell(1);
                    XSSFCell percentOfOverallExpenseHeaderCell4 = aprBillHeaders.createCell(2);
                    XSSFCell tmp4 = aprTotalRow.createCell(0);
                    XSSFCell aprTotalExpense = aprTotalRow.createCell(1);

                    XSSFCell mayYearHeaderCell = mayYaarBillHeaders.createCell(0);
                    XSSFCell expenseCategoryHeaderCell5 = mayBillHeaders.createCell(0);
                    XSSFCell moneySpentHeaderCell5 = mayBillHeaders.createCell(1);
                    XSSFCell percentOfOverallExpenseHeaderCell5 = mayBillHeaders.createCell(2);
                    XSSFCell tmp5 = mayTotalRow.createCell(0);
                    XSSFCell mayTotalExpense = mayTotalRow.createCell(1);

                    XSSFCell juneYearHeaderCell = juneYaarBillHeaders.createCell(0);
                    XSSFCell expenseCategoryHeaderCell6 = juneBillHeaders.createCell(0);
                    XSSFCell moneySpentHeaderCell6 = juneBillHeaders.createCell(1);
                    XSSFCell percentOfOverallExpenseHeaderCell6 = juneBillHeaders.createCell(2);
                    XSSFCell tmp6 = juneTotalRow.createCell(0);
                    XSSFCell juneTotalExpense = juneTotalRow.createCell(1);

                    XSSFCell julyYearHeaderCell = julyYaarBillHeaders.createCell(0);
                    XSSFCell expenseCategoryHeaderCell7 = julyBillHeaders.createCell(0);
                    XSSFCell moneySpentHeaderCell7 = julyBillHeaders.createCell(1);
                    XSSFCell percentOfOverallExpenseHeaderCell7 = julyBillHeaders.createCell(2);
                    XSSFCell tmp7 = julyTotalRow.createCell(0);
                    XSSFCell julyTotalExpense = julyTotalRow.createCell(1);

                    XSSFCell augYearHeaderCell = augYaarBillHeaders.createCell(0);
                    XSSFCell expenseCategoryHeaderCell8 = augBillHeaders.createCell(0);
                    XSSFCell moneySpentHeaderCell8 = augBillHeaders.createCell(1);
                    XSSFCell percentOfOverallExpenseHeaderCell8 = augBillHeaders.createCell(2);
                    XSSFCell tmp8 = augTotalRow.createCell(0);
                    XSSFCell augTotalExpense = augTotalRow.createCell(1);

                    XSSFCell septYearHeaderCell = septYaarBillHeaders.createCell(0);
                    XSSFCell expenseCategoryHeaderCell9 = septBillHeaders.createCell(0);
                    XSSFCell moneySpentHeaderCell9 = septBillHeaders.createCell(1);
                    XSSFCell percentOfOverallExpenseHeaderCell9 = septBillHeaders.createCell(2);
                    XSSFCell tmp9 = septTotalRow.createCell(0);
                    XSSFCell septTotalExpense = septTotalRow.createCell(1);

                    XSSFCell octYearHeaderCell = octYaarBillHeaders.createCell(0);
                    XSSFCell expenseCategoryHeaderCell10 = octBillHeaders.createCell(0);
                    XSSFCell moneySpentHeaderCell10 = octBillHeaders.createCell(1);
                    XSSFCell percentOfOverallExpenseHeaderCell10 = octBillHeaders.createCell(2);
                    XSSFCell tmp10 = octTotalRow.createCell(0);
                    XSSFCell octTotalExpense = octTotalRow.createCell(1);

                    XSSFCell novYearHeaderCell = novYaarBillHeaders.createCell(0);
                    XSSFCell expenseCategoryHeaderCell11 = novBillHeaders.createCell(0);
                    XSSFCell moneySpentHeaderCell11 = novBillHeaders.createCell(1);
                    XSSFCell percentOfOverallExpenseHeaderCell11 = novBillHeaders.createCell(2);
                    XSSFCell tmp11 = novTotalRow.createCell(0);
                    XSSFCell novTotalExpense = novTotalRow.createCell(1);

                    XSSFCell decYearHeaderCell = decYaarBillHeaders.createCell(0);
                    XSSFCell expenseCategoryHeaderCell12 = decBillHeaders.createCell(0);
                    XSSFCell moneySpentHeaderCell12 = decBillHeaders.createCell(1);
                    XSSFCell percentOfOverallExpenseHeaderCell12 = decBillHeaders.createCell(2);
                    XSSFCell tmp12 = decTotalRow.createCell(0);
                    XSSFCell decTotalExpense = decTotalRow.createCell(1);


                    // Populate the cells of the header row
                    janYearHeaderCell.setCellValue(year);
                    expenseCategoryHeaderCell.setCellValue("Expense Category");
                    moneySpentHeaderCell.setCellValue("Money Spent");
                    percentOfOverallExpenseHeaderCell.setCellValue("Percent of overall expenses");
                    tmp1.setCellStyle(totalMoneyStyle);
                    tmp1.setCellValue("Total");
                    janTotalExpense.setCellType(CellType.FORMULA);
                    janTotalExpense.setCellStyle(totalMoneyStyle);
                    String formula = "Sum(B3" + ":B" + (janCategories + 2) + ")";
                    janTotalExpense.setCellFormula(formula);

                    febYearHeaderCell.setCellValue(year);
                    expenseCategoryHeaderCell2.setCellValue("Expense Category");
                    moneySpentHeaderCell2.setCellValue("Money Spent");
                    percentOfOverallExpenseHeaderCell2.setCellValue("Percent of overall expenses");
                    tmp2.setCellStyle(totalMoneyStyle);
                    tmp2.setCellValue("Total");
                    febTotalExpense.setCellType(CellType.FORMULA);
                    febTotalExpense.setCellStyle(totalMoneyStyle);
                    String formula2 = "Sum(B3" + ":B" + (febCategories + 2) + ")";
                    febTotalExpense.setCellFormula(formula2);

                    marchYearHeaderCell.setCellValue(year);
                    expenseCategoryHeaderCell3.setCellValue("Expense Category");
                    moneySpentHeaderCell3.setCellValue("Money Spent");
                    percentOfOverallExpenseHeaderCell3.setCellValue("Percent of overall expenses");
                    tmp3.setCellStyle(totalMoneyStyle);
                    tmp3.setCellValue("Total");
                    marTotalExpense.setCellType(CellType.FORMULA);
                    marTotalExpense.setCellStyle(totalMoneyStyle);
                    String formula3 = "Sum(B3" + ":B" + (marCategories + 2) + ")";
                    marTotalExpense.setCellFormula(formula3);

                    aprYearHeaderCell.setCellValue(year);
                    expenseCategoryHeaderCell4.setCellValue("Expense Category");
                    moneySpentHeaderCell4.setCellValue("Money Spent");
                    percentOfOverallExpenseHeaderCell4.setCellValue("Percent of overall expenses");
                    tmp4.setCellStyle(totalMoneyStyle);
                    tmp4.setCellValue("Total");
                    aprTotalExpense.setCellType(CellType.FORMULA);
                    aprTotalExpense.setCellStyle(totalMoneyStyle);
                    String formula4 = "Sum(B3" + ":B" + (aprCategories + 2) + ")";
                    aprTotalExpense.setCellFormula(formula4);

                    mayYearHeaderCell.setCellValue(year);
                    expenseCategoryHeaderCell5.setCellValue("Expense Category");
                    moneySpentHeaderCell5.setCellValue("Money Spent");
                    percentOfOverallExpenseHeaderCell5.setCellValue("Percent of overall expenses");
                    tmp5.setCellStyle(totalMoneyStyle);
                    tmp5.setCellValue("Total");
                    mayTotalExpense.setCellType(CellType.FORMULA);
                    mayTotalExpense.setCellStyle(totalMoneyStyle);
                    String formula5 = "Sum(B3" + ":B" + (mayCategories + 2) + ")";
                    mayTotalExpense.setCellFormula(formula5);

                    juneYearHeaderCell.setCellValue(year);
                    expenseCategoryHeaderCell6.setCellValue("Expense Category");
                    moneySpentHeaderCell6.setCellValue("Money Spent");
                    percentOfOverallExpenseHeaderCell6.setCellValue("Percent of overall expenses");
                    tmp6.setCellStyle(totalMoneyStyle);
                    tmp6.setCellValue("Total");
                    juneTotalExpense.setCellType(CellType.FORMULA);
                    juneTotalExpense.setCellStyle(totalMoneyStyle);
                    String formula6 = "Sum(B3" + ":B" + (juneCategories + 2) + ")";
                    juneTotalExpense.setCellFormula(formula6);

                    julyYearHeaderCell.setCellValue(year);
                    expenseCategoryHeaderCell7.setCellValue("Expense Category");
                    moneySpentHeaderCell7.setCellValue("Money Spent");
                    percentOfOverallExpenseHeaderCell7.setCellValue("Percent of overall expenses");
                    tmp7.setCellStyle(totalMoneyStyle);
                    tmp7.setCellValue("Total");
                    julyTotalExpense.setCellType(CellType.FORMULA);
                    julyTotalExpense.setCellStyle(totalMoneyStyle);
                    String formula7 = "Sum(B3" + ":B" + (julyCategories + 2) + ")";
                    julyTotalExpense.setCellFormula(formula7);

                    augYearHeaderCell.setCellValue(year);
                    expenseCategoryHeaderCell8.setCellValue("Expense Category");
                    moneySpentHeaderCell8.setCellValue("Money Spent");
                    percentOfOverallExpenseHeaderCell8.setCellValue("Percent of overall expenses");
                    tmp8.setCellStyle(totalMoneyStyle);
                    tmp8.setCellValue("Total");
                    augTotalExpense.setCellType(CellType.FORMULA);
                    augTotalExpense.setCellStyle(totalMoneyStyle);
                    String formula8 = "Sum(B3" + ":B" + (augCategories + 2) + ")";
                    augTotalExpense.setCellFormula(formula8);

                    septYearHeaderCell.setCellValue(year);
                    expenseCategoryHeaderCell9.setCellValue("Expense Category");
                    moneySpentHeaderCell9.setCellValue("Money Spent");
                    percentOfOverallExpenseHeaderCell9.setCellValue("Percent of overall expenses");
                    tmp9.setCellStyle(totalMoneyStyle);
                    tmp9.setCellValue("Total");
                    septTotalExpense.setCellType(CellType.FORMULA);
                    septTotalExpense.setCellStyle(totalMoneyStyle);
                    String formula9 = "Sum(B3" + ":B" + (septCategories + 2) + ")";
                    septTotalExpense.setCellFormula(formula9);

                    octYearHeaderCell.setCellValue(year);
                    expenseCategoryHeaderCell10.setCellValue("Expense Category");
                    moneySpentHeaderCell10.setCellValue("Money Spent");
                    percentOfOverallExpenseHeaderCell10.setCellValue("Percent of overall expenses");
                    tmp10.setCellStyle(totalMoneyStyle);
                    tmp10.setCellValue("Total");
                    octTotalExpense.setCellType(CellType.FORMULA);
                    octTotalExpense.setCellStyle(totalMoneyStyle);
                    String formula10 = "Sum(B3" + ":B" + (octCategories + 2) + ")";
                    octTotalExpense.setCellFormula(formula10);

                    novYearHeaderCell.setCellValue(year);
                    expenseCategoryHeaderCell11.setCellValue("Expense Category");
                    moneySpentHeaderCell11.setCellValue("Money Spent");
                    percentOfOverallExpenseHeaderCell11.setCellValue("Percent of overall expenses");
                    tmp11.setCellStyle(totalMoneyStyle);
                    tmp11.setCellValue("Total");
                    novTotalExpense.setCellType(CellType.FORMULA);
                    novTotalExpense.setCellStyle(totalMoneyStyle);
                    String formula11 = "Sum(B3" + ":B" + (novCategories + 2) + ")";
                    novTotalExpense.setCellFormula(formula11);

                    decYearHeaderCell.setCellValue(year);
                    expenseCategoryHeaderCell12.setCellValue("Expense Category");
                    moneySpentHeaderCell12.setCellValue("Money Spent");
                    percentOfOverallExpenseHeaderCell12.setCellValue("Percent of overall expenses");
                    tmp12.setCellStyle(totalMoneyStyle);
                    tmp12.setCellValue("Total");
                    decTotalExpense.setCellType(CellType.FORMULA);
                    decTotalExpense.setCellStyle(totalMoneyStyle);
                    String formula12 = "Sum(B3" + ":B" + (decCategories + 2) + ")";
                    decTotalExpense.setCellFormula(formula12);


                    resultsOutputFile = new FileOutputStream(f);

                    // Resize each column to fit the data
                    janSheet.autoSizeColumn(0);
                    janSheet.autoSizeColumn(1);
                    janSheet.autoSizeColumn(2);

                    febSheet.autoSizeColumn(0);
                    febSheet.autoSizeColumn(1);
                    febSheet.autoSizeColumn(2);

                    marchSheet.autoSizeColumn(0);
                    marchSheet.autoSizeColumn(1);
                    marchSheet.autoSizeColumn(2);

                    aprSheet.autoSizeColumn(0);
                    aprSheet.autoSizeColumn(1);
                    aprSheet.autoSizeColumn(2);

                    maySheet.autoSizeColumn(0);
                    maySheet.autoSizeColumn(1);
                    maySheet.autoSizeColumn(2);

                    juneSheet.autoSizeColumn(0);
                    juneSheet.autoSizeColumn(1);
                    juneSheet.autoSizeColumn(2);

                    julySheet.autoSizeColumn(0);
                    julySheet.autoSizeColumn(1);
                    julySheet.autoSizeColumn(2);

                    augSheet.autoSizeColumn(0);
                    augSheet.autoSizeColumn(1);
                    augSheet.autoSizeColumn(2);

                    septSheet.autoSizeColumn(0);
                    septSheet.autoSizeColumn(1);
                    septSheet.autoSizeColumn(2);

                    octSheet.autoSizeColumn(0);
                    octSheet.autoSizeColumn(1);
                    octSheet.autoSizeColumn(2);

                    novSheet.autoSizeColumn(0);
                    novSheet.autoSizeColumn(1);
                    novSheet.autoSizeColumn(2);

                    decSheet.autoSizeColumn(0);
                    decSheet.autoSizeColumn(1);
                    decSheet.autoSizeColumn(2);


                    wb.write(resultsOutputFile);
                    exportProgressBar.setValue(100);
                    exportProgressBar.setVisible(true);
                    exportProgressBar.setString("100%");
                    exportProgressLBL.setText("Finished Processing Data, Report has been generated.");
                    JOptionPane.showMessageDialog(null, "The Bill Breakdown Export Report is complete.");

                }catch(IOException io){
                    System.out.println("ExportIOException" + io.getMessage());
                }
                finally{
                    if(resultsOutputFile != null){
                        try {
                            resultsOutputFile.flush();
                        } catch (IOException ex) {
                            Logger.getLogger(ExportBillsBreakdown.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            resultsOutputFile.close();
                        } catch (IOException ex) {
                            Logger.getLogger(ExportBillsBreakdown.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                    if(wb != null){
                        try {
                            wb.close();
                        } catch (IOException ex) {
                            Logger.getLogger(ExportBillsBreakdown.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }
            
            }});
            
            thread.start();
        
    }
    
}
