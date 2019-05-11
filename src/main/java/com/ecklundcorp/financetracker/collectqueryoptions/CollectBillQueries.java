/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecklundcorp.financetracker.collectqueryoptions;

import com.ecklundcorp.financetracker.mycosts.Bills;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
//import mycosts.Bills;

/**
 *
 * @author Dillon
 */
public class CollectBillQueries {
    
    ArrayList<String> searchOptions;
    
    public CollectBillQueries(){
        searchOptions = new ArrayList<>();
    }
    
    public ArrayList<String> setUpCategoryQueryOptions(ArrayList<Bills> bills){
        
        // Store all data temporarily in searchOptions Arraylist
        for(int i = 0; i < bills.size(); i++){
            searchOptions.add(bills.get(i).getCategory());
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
    
    public ArrayList<String> setUpCategoryAndMonthYearQueryOptions(ArrayList<Bills> bills){
        
        // Store all data temporarily in searchOptions Arraylist
        for(int i = 0; i < bills.size(); i++){
            searchOptions.add(bills.get(i).getCategory() + " " + bills.get(i).getDate().split("/")[0] + "/" + bills.get(i).getDate().split("/")[2]);
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
    
    public ArrayList<String> setUpDateDayQueryOptions(ArrayList<Bills> bills){
        
        // Store all data temporarily in searchOptions Arraylist
        for(int i = 0; i < bills.size(); i++){
            searchOptions.add(bills.get(i).getDate());
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
    
    public ArrayList<String> setUpDateMonthYearQueryOptions(ArrayList<Bills> bills){
        
        // Store all data temporarily in searchOptions Arraylist
        for(int i = 0; i < bills.size(); i++){
            searchOptions.add(bills.get(i).getDate().split("/")[0] + "/" + bills.get(i).getDate().split("/")[2]);
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
    
    public ArrayList<String> setUpDateYearQueryOptions(ArrayList<Bills> bills){
        
        // Store all data temporarily in searchOptions Arraylist
        for(int i = 0; i < bills.size(); i++){
            searchOptions.add(bills.get(i).getDate().split("/")[2]);
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
