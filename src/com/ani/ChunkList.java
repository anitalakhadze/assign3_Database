package com.ani;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ChunkList<E> extends AbstractCollection<E> {
    Chunk head;
    Chunk tail;
    int size;

    class Chunk {
        private static final int ARRAY_SIZE = 8;
        E[] arr;
        int elements;
        Chunk next;

        public int size() {
            return elements;
        }

        public Chunk() {
            arr = (E[]) new Object[ARRAY_SIZE];
            elements = 0;
            next = null;
        }

        public void add(E elem){
            arr[elements++] = elem;
        }

        public void remove(int idx) {
            System.arraycopy(arr, idx +1, arr, idx, elements - idx - 1);
            elements--;
            ChunkList.this.size--;
        }

        public boolean isFull(){
            return elements == arr.length;
        }

        public boolean isEmpty() { return elements == 0; }

        Iterator iterator() {
            return new Iterator() {
                int cursor; //index of next element to return
                int lastRet = -1; // index of last element returned; -1 if no such

                @Override
                public boolean hasNext() {
                   return cursor != elements;
                }

                @Override
                public Object next() {
                    int i = cursor;
                    cursor = i + 1;
                    return arr[lastRet = i];
                }

                @Override
                public void remove() {
                    Chunk.this.remove(lastRet);
                    cursor = lastRet;
                    lastRet = -1;
                }
//                int currPosition = 0;
//
//                @Override
//                public boolean hasNext() {
//                    return currPosition < elements;
//                }
//
//                @Override
//                public Object next() {
//                    return arr[currPosition++];
//                }
//
//                @Override
//                public void remove() {
//                    Chunk.this.remove(--currPosition);
//                }

            };
        }
    }

    /**
     * Constructs a new ChunkList which has 0 elements in it.
     * At first head and tail pointers are null, we allocate
     * chunks only when necessary.
     */
    public ChunkList(){
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Adds elem at the end of the ChunkList - in the tail chunk.
     * If the tail chunk is full, a new chunk should be added
     * and become the new tail.
     * @param elem element that needs to be added.
     * @return returns true if a new element was added.
     */
    @Override
    public boolean add(Object elem) {
        if (tail == null){
            Chunk newOne = new Chunk();
            tail = newOne;
            head = newOne;
        } else if (tail.isFull()) {
            Chunk newOne = new Chunk();
            tail.next = newOne;
            tail = newOne;
        }
        tail.add((E) elem);
        size++;
        return true;
    }

    class MyIterator implements Iterator {
        Chunk chunk;
        Chunk previous;
        Iterator chunkIterator;

        MyIterator(){
            chunk = head;
            previous = null;
            if (chunk != null) chunkIterator = chunk.iterator();
        }

        @Override
        public boolean hasNext() {
            if (chunk == null) return false;
            else return chunkIterator.hasNext() || chunk.next != null;
        }

        @Override
        public Object next() {
            if (chunkIterator.hasNext()){
                return chunkIterator.next();
            } else if (chunk.next != null){
                previous = chunk;
                chunk = chunk.next;
                chunkIterator = chunk.iterator();
                return chunkIterator.next();
            }
            return null;
        }

        @Override
        public void remove() {
            chunkIterator.remove();
            if (chunk.isEmpty()) {
                if (chunk.next != null){
                    if (previous != null){
                        previous.next = chunk.next;
                        chunk = chunk.next;
                    } else {
                        chunk = chunk.next;
                        head = chunk;
                    }
                    chunkIterator = chunk.iterator();
                } else {
                    if (previous != null) {
                        tail = previous;
                        previous.next = null;
                        chunk = null;
                    } else {
                        chunk = null;
                        head = null;
                        tail = null;
                    }
                }
            }
        }
    }

    @Override
    public Iterator iterator() {
        return new MyIterator();
    }

    /**
     * @return returns the number of elements inside the ChunkList.
     */
    @Override
    public int size() {
        return size;
    }

}
