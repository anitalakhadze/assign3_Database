package com.ani;

import org.junit.Test;

import static org.junit.Assert.*;

public class DBBindingTest {

    @Test
    public void testThatConstructorParsesLineCorrectly(){
        DBBinding b = new DBBinding("name", "Kuna");
        assertEquals("name", b.getKey());
        assertEquals("Kuna", b.getValue());
        assertEquals("name: Kuna", b.toString());
    }

    @Test
    public void testContains(){
        DBBinding b1 = new DBBinding("name: lalala haha");
        DBBinding b2 = new DBBinding("name: HAHA");
        DBBinding b3 = new DBBinding("name: LaLAla");
        DBBinding b4 = new DBBinding("stars: haha");
        assertTrue(b1.contains(b2));
        assertTrue(b1.contains(b3));
        assertFalse(b2.contains(b3));
        assertFalse(b1.contains(b4));
    }
}