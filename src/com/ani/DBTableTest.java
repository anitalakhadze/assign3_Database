package com.ani;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class DBTableTest {
    DBTable table;
    String filename;
    String crit;

    @Before
    public void setUp() throws IOException {
        filename = "C:\\Users\\Lenovo\\Documents\\dev\\oop\\assign3_part2\\src\\com\\ani\\movies.txt";
        table = new DBTable();
        table.read(filename);
        crit = "stars:kotto, stars:stanton";
    }

    @Test
    public void functionalTestSelectAnd() throws IOException {
        table.selectAnd(crit);
        String expected = "*name: Alien, stars: Yaphet Kotto, stars: Sigourney Weaver, stars: Harry Dean Stanton\n" +
                "name: Repo Man, stars: Emilio Estevez, stars: Harry Dean Stanton\n" +
                "name: The Truth About Cats and Dogs, stars: Janeane Garofalo, stars: Uma Thurman\n" +
                "name: Sense and Sensibility, stars: Emma Thompson, stars: Hugh Grant\n" +
                "name: Midnight Run, stars: Yaphet Kotto, stars: Charles Grodin, stars: Robert Deniro";
        assertEquals(expected, table.toString());
        assertEquals(1, table.getSelectedSize());
    }

    @Test
    public void functionalTestSelectOr() throws IOException {
        table.selectOr(crit);
        String expected = "*name: Alien, stars: Yaphet Kotto, stars: Sigourney Weaver, stars: Harry Dean Stanton\n" +
                "*name: Repo Man, stars: Emilio Estevez, stars: Harry Dean Stanton\n" +
                "name: The Truth About Cats and Dogs, stars: Janeane Garofalo, stars: Uma Thurman\n" +
                "name: Sense and Sensibility, stars: Emma Thompson, stars: Hugh Grant\n" +
                "*name: Midnight Run, stars: Yaphet Kotto, stars: Charles Grodin, stars: Robert Deniro";
        assertEquals(expected, table.toString());
        assertEquals(3, table.getSelectedSize());
    }

    @Test
    public void testThatToStringWorksCorrectly() throws IOException {
        String expected = "name: Alien, stars: Yaphet Kotto, stars: Sigourney Weaver, stars: Harry Dean Stanton\n" +
                "name: Repo Man, stars: Emilio Estevez, stars: Harry Dean Stanton\n" +
                "name: The Truth About Cats and Dogs, stars: Janeane Garofalo, stars: Uma Thurman\n" +
                "name: Sense and Sensibility, stars: Emma Thompson, stars: Hugh Grant\n" +
                "name: Midnight Run, stars: Yaphet Kotto, stars: Charles Grodin, stars: Robert Deniro";
        assertEquals(expected, table.toString());
    }

    @Test
    public void testDelete() throws IOException {
        table.selectOr(crit);
        String expected = "name: The Truth About Cats and Dogs, stars: Janeane Garofalo, stars: Uma Thurman\n" +
                "name: Sense and Sensibility, stars: Emma Thompson, stars: Hugh Grant";
        table.deleteSelected();
        assertEquals(expected, table.toString());
        assertEquals(0, table.getSelectedSize());
    }

    @Test
    public void testDelete1() throws IOException {
        table.selectOr(crit);
        String expected = "*name: Alien, stars: Yaphet Kotto, stars: Sigourney Weaver, stars: Harry Dean Stanton\n" +
                "*name: Repo Man, stars: Emilio Estevez, stars: Harry Dean Stanton\n" +
                "*name: Midnight Run, stars: Yaphet Kotto, stars: Charles Grodin, stars: Robert Deniro";
        table.deleteUnselected();
        assertEquals(expected, table.toString());
    }

    @Test
    public void testDelete2() throws IOException {
        table.selectOr(crit);
        String expected = "*name: Alien, stars: Yaphet Kotto, stars: Sigourney Weaver, stars: Harry Dean Stanton\n" +
                "*name: Repo Man, stars: Emilio Estevez, stars: Harry Dean Stanton\n" +
                "*name: Midnight Run, stars: Yaphet Kotto, stars: Charles Grodin, stars: Robert Deniro";
        table.deleteUnselected();
        table.deleteSelected();
    }

    @Test
    public void testThatReadsCorrectly(){
        assertEquals(5, table.size());
        table.deleteAll();
        assertEquals(0, table.size());
    }

    @Test
    public void testClearSelected(){
        table.selectOr(crit);
        assertEquals(3, table.getSelectedSize());
        table.clearSelected();
        assertEquals(0, table.getSelectedSize());
    }

}