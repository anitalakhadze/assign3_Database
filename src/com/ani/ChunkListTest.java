package com.ani;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.*;

public class ChunkListTest {
    ChunkList<DBBinding> bindings;
    ChunkList<DBRecord> records;
    ChunkList.Chunk chunk1, chunk2;
    List<String> ls_small;
    List<String> ls_med;
    ChunkList chunkList;


    @Before
    public void setUp(){
        ls_small = Arrays.asList("a","b","c","d","e");
        ls_med = Stream.concat(ls_small.stream(), ls_small.stream()).collect(Collectors.toList());
        chunkList = new ChunkList();

        bindings = new ChunkList<>();
        records = new ChunkList<>();
        chunk1 = bindings.new Chunk();
        chunk2 = bindings.new Chunk();
    }

    @Test
    public void testThatConstructorWorksProperly(){
        ChunkList<DBBinding> b = new ChunkList<>();
        assertNull(b.head);
        assertNull(b.tail);
        assertEquals(0, b.size);
    }

    @Test
    public void testThatChunkConstructorWorksProperly(){
        for (int i = 0; i < 7; i++) {
            chunk1.add(new DBBinding("name", "Kuna"));
        }
        assertEquals(7, chunk1.elements);
        chunk1.add(new DBBinding("name", "Kuna"));
        assertTrue(chunk1.isFull());
    }

    @Test
    public void testChunkNext(){
        assertNull(chunk1.next);
        chunk1.next = chunk2;
        assertEquals(chunk2, chunk1.next);
    }


    @Test
    public void testAddInChunkList(){
        bindings.add(new DBBinding("name: Ani"));
        bindings.add(new DBBinding("name: Kuntsula"));
        bindings.add(new DBBinding("name: Kuna"));
        assertEquals(3, bindings.size());
    }

    //// Chunk test

    @Test
    public void testChunkIteratorAdd() {
        ChunkList.Chunk chunk = new ChunkList<>().new Chunk();
        ls_med.stream().limit(3).forEach(chunk::add);
        assertEquals(3, chunk.size());
    }

//    @Test
//    public void testChunkIteratorSeveralTimes() {
//        ChunkList.Chunk ch = new ChunkList<>().new Chunk(ls_med.size());
//        ls_med.forEach(ch::add);
//        Iterator it_first = ch.iterator();
//        ls_med.forEach(e -> assertEquals(e, it_first.next()));
//        assertFalse(it_first.hasNext());
//        ch.elements = 0;
//
//        ls_med.forEach(ch::add);
//        Iterator  it_second = ch.iterator();
//        ls_med.forEach(e -> assertEquals(e, it_second.next()));
//        assertFalse(it_second.hasNext());
//    }

//    @Test
//    public void testChunkIteratorWithRemove() {
//        ChunkList.Chunk ch =  new ChunkList<>().new Chunk(1000);
//        ch.add(1);
//        ch.add(2);
//        assertEquals(2, ch.size());
//
//        ch.remove(0);
//        assertEquals(1, ch.size());
//        assertEquals(2,ch.iterator().next());
//
//        ch.iterator().remove();
//        assertEquals(0, ch.size());
//
//        for (int i = 0; i < 1000; i++) {
//            ch.add(i);
//        }
//        Iterator chunkIterator = ch.iterator();
//        for (int i = 0; i < 1000; i++) {
//            assertEquals(i,chunkIterator.next());
//        }
//        chunkIterator = ch.iterator();
//        for (int i = 0; i < 1000; i++) {
//            chunkIterator.remove();
//        }
//        assertFalse(chunkIterator.hasNext());
//    }

//    @Test
//    public void otherChunkTests() {
//        ChunkList.Chunk chunk = new ChunkList<>().new Chunk(2);
//
//    }

    //// ChunkList tests

    @Test
    public void testAdd() {
        chunkList.addAll(ls_small);
        assertEquals(5, chunkList.size);
    }

    @Test
    public void testIterator() {
        //Fill initial collection
        chunkList.addAll(ls_small);

        // Size must match now
        assertEquals(ls_small.size(), chunkList.size());
        Iterator it_small = chunkList.iterator();
        //Every element is present
        ls_small.forEach(e -> assertEquals(e,it_small.next()));
        //Iterator must finish now
        assertFalse(it_small.hasNext());

        // Now add more values
        chunkList.addAll(ls_small);

        List<String> must_contain_now = Stream
                .concat(ls_small.stream(), ls_small.stream())
                .collect(Collectors.toList());
        assertEquals(must_contain_now.size(), chunkList.size());
        Iterator it_large = chunkList.iterator();
        //Every element is present
        must_contain_now.forEach(e -> assertEquals(e,it_large.next()));
        //Iterator must finish now
        assertFalse(it_large.hasNext());
    }

    @Test
    public void testFirstAddThenRemove() {
//        assertEquals(0, chunkList.size());
//        ls_med.forEach(e -> chunkList.add(e));
//        Iterator removal_iterator_first = chunkList.iterator();
//        while (removal_iterator_first.hasNext()) {
//            removal_iterator_first.remove();
//        }
////        ls_med.forEach(e -> removal_iterator_first.remove());
//        assertFalse(removal_iterator_first.hasNext());
//        assertEquals(0, chunkList.size());
////
////        chunkList.addAll(ls_med);
////        Iterator removal_iterator_second = chunkList.iterator();
////        ls_med.forEach(e -> {
////            assertTrue(removal_iterator_first.hasNext());
////            removal_iterator_second.remove();
////            }
////        );
////        assertFalse(removal_iterator_first.hasNext());
////        assertEquals(0, chunkList.size());
    }

}
