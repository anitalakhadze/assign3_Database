package com.ani;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DBRecordTest {

    @Test
    public void testThatRecordIsCreated() {
        DBRecord r = new DBRecord("name: Ani, stars: kuntsula, stars: kuna");
        String expected = "name: Ani, stars: kuntsula, stars: kuna";
        assertEquals(expected, r.toString());
    }

    @Test
    public void testSelectedToString(){
        DBRecord r = new DBRecord("name: Ani, stars: kuntsula, stars: kuna");
        r.setSelected(true);
        String expected = "*name: Ani, stars: kuntsula, stars: kuna";
        assertEquals(expected, r.toString());
    }

    @Test
    public void testGetBindings(){
        DBRecord r = new DBRecord("name: Ani, stars: kuntsula, stars: kuna");
        List<DBBinding> bindings = new ArrayList<>(r.getBindings());
        DBBinding b = new DBBinding("name: Ani");
        assertEquals(b.toString(), bindings.toArray()[0].toString());
    }

    @Test
    public void testContains(){
        DBRecord r = new DBRecord("name: Ani, stars: kuntsula, stars: kuna");
        DBBinding b = new DBBinding("name: Ani");
        DBBinding b2 = new DBBinding("star: Ani");
        assertTrue(r.contains(b));
        assertFalse(r.contains(b2));
    }
}