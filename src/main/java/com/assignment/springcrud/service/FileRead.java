package com.assignment.springcrud.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

class FileRead implements Callable<String> {
    Integer line;

    public FileRead(/*int sta,int sto*/Integer line) throws IOException {
        this.line = line;
    }

    @Override
    public String call() throws IOException {
        String details = Files.readAllLines(Paths.get("checkcsv.csv")).get(line);
        return details;
    }
}
//
//import java.io.*;
//import static java.lang.Math.toIntExact;
//import java.nio.*;
//import java.nio.channels.*;
//import java.nio.charset.Charset;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.concurrent.*;
//
//public class FileRead implements Callable<String> {
////    BufferedReader reader = Files.newBufferedReader(Paths.get("checkcsv.csv"));
////    String line = "";
////    Integer start,stop;
//    Integer li;
//    public FileRead(/*int sta,int sto*/Integer l) throws IOException {
////        start = sta;
////        stop=sto;
//        li = l;
//    }
//    @Override public String call() throws IOException {
//        String line = Files.readAllLines(Paths.get("checkcsv.csv")).get(li);
//        return line;
//    }
//
////    @Override public String call() throws IOException {
////        try {
////            for(int i =start;i<stop;i++) {
////                String line = Files.readAllLines(Paths.get("file.txt")).get(i);
////                System.out.println(line);
////            }
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
////    }
//
//    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        Future<String>[] results = new Future[5];
//        FileRead fileRead;
//        Future<String> future;
//        for (int i=0;i<14999;i++){
//            fileRead = new FileRead(i);
////            results[i]=executorService.submit(fileRead);
//            future = executorService.submit(fileRead);
//            System.out.println(future.get());
////            System.out.println(results[i].get());
//        }
//        executorService.shutdown();
////        String line = Files.readAllLines(Paths.get("test.csv")).get(2);
////        System.out.println(line);
//    }
//}

//public class FileRead implements Runnable
//{
//
//    private FileChannel channel;
//    private long startLocation;
//    private int size;
//    int sequenceNumber;
//
//    public FileRead(long loc, int size, FileChannel chnl, int sequence)
//    {
//        startLocation = loc;
//        this.size = size;
//        channel = chnl;
//        sequenceNumber = sequence;
//    }
//
//    @Override
//    public void run()
//    {
//        try
//        {
//            System.out.println("Reading the channel: " + startLocation + ":" + size);
//
//            //allocate memory
//            ByteBuffer buff = ByteBuffer.allocate(size);
//
//            //Read file chunk to RAM
//            channel.read(buff, startLocation);
//
//            //chunk to String
//            String string_chunk = new String(buff.array(), Charset.forName("UTF-8"));
//
//            System.out.println("Done Reading the channel: " + startLocation + ":" + size);
//
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    //args[0] is path to read file
////args[1] is the size of thread pool; Need to try different values to fing sweet spot
//    public static void main(String[] args) throws Exception
//    {
//        FileInputStream fileInputStream = new FileInputStream("test.csv");
//        FileChannel channel = fileInputStream.getChannel();
//        long remaining_size = channel.size(); //get the total number of bytes in the file
//        long chunk_size = remaining_size / 10; //file_size/threads
//
//        //Max allocation size allowed is ~2GB
//        if (chunk_size > (Integer.MAX_VALUE - 5))
//        {
//            chunk_size = (Integer.MAX_VALUE - 5);
//        }
//
//        //thread pool
//        ExecutorService executor = Executors.newFixedThreadPool(2);
//
//        long start_loc = 0;//file pointer
//        int i = 0; //loop counter
//        while (remaining_size >= chunk_size)
//        {
//            //launches a new thread
//            executor.execute(new FileRead(start_loc, toIntExact(chunk_size), channel, i));
//            remaining_size = remaining_size - chunk_size;
//            start_loc = start_loc + chunk_size;
//            i++;
//        }
//
//        //load the last remaining piece
//        executor.execute(new FileRead(start_loc, toIntExact(remaining_size), channel, i));
//
//        //Tear Down
//        executor.shutdown();
//
//        //Wait for all threads to finish
//        while (!executor.isTerminated())
//        {
//            //wait for infinity time
//        }
//        System.out.println("Finished all threads");
//        fileInputStream.close();
//    }
//
//}








//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.*;
//
//class SearchTask implements Callable<Integer> {
//    private int localCounter = 0;
//    private int start; // start index of search
//    private int end;
//    private List<String> words;
//    private String token;
//
//    public SearchTask(int start, int end, List<String> words, String token) {
//        this.start = start;
//        this.end = end;
//        this.words = words;
//        this.token = token;
//    }
//
//    public Integer call() {
//        for(int i = start; i < end; i++) {
//            if(words.get(i).equals(token)) localCounter++;
//        }
//        return localCounter;
//    }
//}
//
//// meanwhile in main :)
//public class FileRead {
//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//
//        List<String> words = new ArrayList<String>();
//        // populate words
//        // let's assume you have 30000 words
//
//        // create tasks
//        List<Callable> tasks = new ArrayList<Callable>();
//        tasks.add(new
//
//        SearchTask(0,10000,words, "John"));
//            tasks.add(new
//
//        SearchTask(10000,20000,words, "John"));
//            tasks.add(new
//
//        SearchTask(20000,30000,words, "John"));
//
//        // create thread pool and start tasks
//        ExecutorService exec = Executors.newFixedThreadPool(3);
//        List<Future> results = exec.invokeAll(tasks);
//
//        // wait for tasks to finish and collect results
//        int counter = 0;
//            for(
//        Future f:results)
//
//        {
//            counter += f.get();
//        }
//    }
//}
//
////import java.io.BufferedReader;
////import java.io.IOException;
////import java.nio.file.Files;
////import java.nio.file.Paths;
////
////public class check {
////
////}
////
////
////class Adder{
////    public void doAdd(){
////        String line = null;
////        try(BufferedReader br = Files.newBufferedReader(Paths.get("test.csv"))){
////            while ((line = br.readLine())!=null){
////
////            }
////
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
////}}