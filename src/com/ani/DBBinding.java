package com.ani;

public class DBBinding {
    String key;
    String value;

    public DBBinding(String key, String val) {
        this.key = key;
        this.value = val;
    }

    public DBBinding(String elem) {
        String[] parts = elem.split(":");
        this.key = parts[0].trim();
        this.value = parts[1].trim();
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return key + ": " + value;
    }

    public boolean contains (DBBinding other){
        return (this.key.equals(other.key)
                && (this.value.toLowerCase().contains(other.value.toLowerCase())));
    }

}
