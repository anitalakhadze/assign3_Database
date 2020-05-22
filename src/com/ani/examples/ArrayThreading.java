package com.ani.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ArrayThreading {
    private int[] array;
    private Semaphore allDone;

    public ArrayThreading(int len) {
        array = new int[len];
        for (int i = 0; i < len; i++) {
            array[i] = i % 10;
        }
    }

    private class Worker extends Thread{
        int start, end;
        long sum;

        Worker (int start, int end){
            this.start = start;
            this.end = end;
            sum = 0;
        }

        public void run(){
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            allDone.release();
        }

        public long getSum(){
            return sum;
        }
    }

    public void runParallel(){
        int numWorkers = 10;
        allDone = new Semaphore(0);
        List<Worker> workers = new ArrayList<>();
        int lenOneWorker = array.length / numWorkers;

        for (int i = 0; i < numWorkers; i++) {
            int start = i * lenOneWorker;
            int end = (i + 1) * lenOneWorker;
            if (i == numWorkers - 1) end = array.length;
            Worker worker = new Worker(start, end);
            workers.add(worker);
            worker.start();
        }

        try { allDone.acquire(numWorkers); }
        catch (InterruptedException ignored) {}

        int sum = 0;
        for (Worker w : workers) sum += w.getSum();
        System.out.println("workers: " + numWorkers + "sum: " + sum);
    }

    public static void main (String[] args){
        int len = 100;
        ArrayThreading at = new ArrayThreading(len);
        at.runParallel();
    }
}