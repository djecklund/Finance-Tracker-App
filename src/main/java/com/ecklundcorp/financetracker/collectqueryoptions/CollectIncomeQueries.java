/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecklundcorp.financetracker.collectqueryoptions;

import com.ecklundcorp.financetracker.mycosts.Income;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
//import mycosts.Income;

/**
 *
 * @author Dillon
 */
public class CollectIncomeQueries {
    
    ArrayList<String> searchOptions;
    
    public CollectIncomeQueries(){
        searchOptions = new ArrayList<>();
    }
    
    public ArrayList<String> setUpCategoryQueryOptions(ArrayList<Income> income){
        
        // Store all data temporarily in searchOptions Arraylist
        for(int i = 0; i < income.size(); i++){
            searchOptions.add(income.get(i).getCategory());
        }
        
        // Remove any duplicate values in searchOptions ArrayList by storing its values 
        // in a HashSet, then place the new list in searchOptions
        Set<String> temp = new HashSet<>();
        temp.addAll(searchOptions);
        searchOptions.removeAll(searchOptions);
        searchOptions.addAll(temp);
        
        Collections.sort(searchOptions, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });
        
        return searchOptions;
    }
    
    public ArrayList<String> setUpCategoryAndMonthYearQueryOptions(ArrayList<Income> income){
        
        // Store all data temporarily in searchOptions Arraylist
        for(int i = 0; i < income.size(); i++){
            searchOptions.add(income.get(i).getCategory() + " " + income.get(i).getDate().split("/")[0] + "/" + income.get(i).getDate().split("/")[2]);
        }
        
        // Remove any duplicate values in searchOptions ArrayList by storing its values 
        // in a HashSet, then place the new list in searchOptions
        Set<String> temp = new HashSet<>();
        temp.addAll(searchOptions);
        searchOptions.removeAll(searchOptions);
        searchOptions.addAll(temp);
        
        Collections.sort(searchOptions);
        
        return searchOptions;
    }
    
    public ArrayList<String> setUpDateDayQueryOptions(ArrayList<Income> income){
        
        // Store all data temporarily in searchOptions Arraylist
        for(int i = 0; i < income.size(); i++){
            searchOptions.add(income.get(i).getDate());
        }
        
        // Remove any duplicate values in searchOptions ArrayList by storing its values 
        // in a HashSet, then place the new list in searchOptions
        Set<String> temp = new HashSet<>();
        temp.addAll(searchOptions);
        searchOptions.removeAll(searchOptions);
        searchOptions.addAll(temp);
        
        ArrayList<String> searchOptionsDay = new ArrayList<>();
        
        for(int i = 0; i < searchOptions.size(); i++){
            searchOptionsDay.add(searchOptions.get(i).split("/")[2] + " " + searchOptions.get(i).split("/")[0] + " " + searchOptions.get(i).split("/")[1]);
        }
        
        Collections.sort(searchOptionsDay);
        
        searchOptions.removeAll(searchOptions);
        
        for(int i = 0; i < searchOptionsDay.size(); i++){
            searchOptions.add(searchOptionsDay.get(i).split(" ")[1] + "/" + searchOptionsDay.get(i).split(" ")[2] + "/" + searchOptionsDay.get(i).split(" ")[0]);
        }
        
        return searchOptions;
    }
    
    public ArrayList<String> setUpDateMonthYearQueryOptions(ArrayList<Income> income){
        
        // Store all data temporarily in searchOptions Arraylist
        for(int i = 0; i < income.size(); i++){
            searchOptions.add(income.get(i).getDate().split("/")[0] + "/" + income.get(i).getDate().split("/")[2]);
        }
        
        // Remove any duplicate values in searchOptions ArrayList by storing its values 
        // in a HashSet, then place the new list in searchOptions
        Set<String> temp = new HashSet<>();
        temp.addAll(searchOptions);
        searchOptions.removeAll(searchOptions);
        searchOptions.addAll(temp);
        
        ArrayList<String> searchOptionsMonthYear = new ArrayList<>();
        
        for(int i = 0; i < searchOptions.size(); i++){
            searchOptionsMonthYear.add(searchOptions.get(i).split("/")[1] + " " + searchOptions.get(i).split("/")[0]);
        }
        
        Collections.sort(searchOptionsMonthYear);
        
        searchOptions.removeAll(searchOptions);
        
        for(int i = 0; i < searchOptionsMonthYear.size(); i++){
            searchOptions.add(searchOptionsMonthYear.get(i).split(" ")[1] + "/" + searchOptionsMonthYear.get(i).split(" ")[0]);
        }
                
        return searchOptions;
    }
    
    public ArrayList<String> setUpDateYearQueryOptions(ArrayList<Income> income){
        
        // Store all data temporarily in searchOptions Arraylist
        for(int i = 0; i < income.size(); i++){
            searchOptions.add(income.get(i).getDate().split("/")[2]);
        }
        
        // Remove any duplicate values in searchOptions ArrayList by storing its values 
        // in a HashSet, then place the new list in searchOptions
        Set<String> temp = new HashSet<>();
        temp.addAll(searchOptions);
        searchOptions.removeAll(searchOptions);
        searchOptions.addAll(temp);
        
        Collections.sort(searchOptions);
        
        return searchOptions;
    }
    
}
