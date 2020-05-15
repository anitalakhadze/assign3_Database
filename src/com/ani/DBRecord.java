package com.ani;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DBRecord {
    List<DBBinding> bindings;
    boolean selected;

    public DBRecord(String line){
        bindings = Arrays.stream(line.split(","))
                .map(DBBinding::new)
                .collect(Collectors.toList());
        selected = false;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public List<DBBinding> getBindings(){
        return bindings;
    }

    @Override
    public String toString() {
        String result =  bindings.stream()
                .map(b -> b.toString() + ", ")
                .reduce("", (a, b) -> a + b);
        result = result.substring(0, result.length() - 2);
        if(isSelected()){
            return "*" + result;
        }
        return result;
    }

    public boolean contains (DBBinding criteria){
        for (DBBinding binding : bindings){
            if(binding.contains(criteria)) return true;
        }
        return false;
    }
}
