package com.ani;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DBTable {
    List<DBRecord> table;

    public DBTable(){
        table = new ArrayList<>();
    }

    public void read(String filename) throws IOException {
        try(BufferedReader in = new BufferedReader(new FileReader(filename))) {
            in.lines().forEach(line -> table.add(new DBRecord(line)));
        }
        table.stream().map(DBRecord::toString).collect(Collectors.toList());
    }

    public int size(){
        return table.size();
    }

    public int getSelectedSize(){
        int selectedSize = 0;
        for (DBRecord record : table){
            if (record.isSelected()) selectedSize++;
        }
        return selectedSize;
    }

    @Override
    public String toString() {
        String result =  table.stream()
                .map(elem -> elem.toString() + "\n")
                .reduce("", (a, b) -> a + b);
        return result.substring(0, result.length() - 1);
    }

    public void deleteAll(){
        table.clear();
    }

    public void deleteSelected(){
        table = table.stream()
                .filter(record -> !record.isSelected())
                .collect(Collectors.toList());
    }

    public void deleteUnselected(){
        table = table.stream()
                .filter(DBRecord::isSelected)
                .collect(Collectors.toList());
    }

    public void selectOr(String input){
        List<DBBinding> conditions = Arrays.stream(input.split(","))
                .map(DBBinding::new)
                .collect(Collectors.toList());
        table.forEach(record -> record.setSelected(
                conditions.stream()
                        .anyMatch(record::contains)));
    }

    public void selectAnd(String input){
        List<DBBinding> conditions = Arrays.stream(input.split(","))
                .map(DBBinding::new)
                .collect(Collectors.toList());
        table.forEach(record -> record.setSelected(
                conditions.stream()
                        .allMatch(record::contains)));
    }

    public void clearSelected(){
        table.forEach(record -> record.setSelected(false));
    }
}
