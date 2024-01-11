package com.example.webfluxdemo.nioDemo;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class niotest {
    public static void main(String[] args) throws IOException {
        TransferTo2();
    }

    public static void test1() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        ReadableByteChannel readableByteChannel = Channels.newChannel(System.in);
        while (true) {
            try {
                readableByteChannel.read(buffer);
                buffer.flip();
                System.out.println("收到数据:"+new String(buffer.array(),0,buffer.remaining()));
                buffer.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void test2() throws IOException {
        FileChannel fileChannel = new FileInputStream("D:\\javaPro\\WebFluxDemo\\src\\test\\java\\com\\example\\webfluxdemo\\nioDemo\\text.txt").getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(128);
        fileChannel.read(buffer);
        buffer.flip();
        System.out.println("读取到的数据:"+new String(buffer.array(),0,buffer.remaining()));
    }

    public static void test3() throws IOException {
        FileOutputStream fileInputStream = new FileOutputStream("D:\\javaPro\\WebFluxDemo\\src\\test\\java\\com\\example\\webfluxdemo\\nioDemo\\text.txt");
        FileChannel outChannel = fileInputStream.getChannel();
        outChannel.write(ByteBuffer.wrap("Test!".getBytes()));
    }
    public static void test4() throws IOException {
        try {
            String fileName = "D:\\javaPro\\WebFluxDemo\\src\\test\\java\\com\\example\\webfluxdemo\\nioDemo\\text.txt";
            RandomAccessFile file = new RandomAccessFile(fileName,"rw");
            FileChannel channel = file.getChannel();
            ReadFile("D:\\javaPro\\WebFluxDemo\\src\\test\\java\\com\\example\\webfluxdemo\\nioDemo\\text.txt");
            channel.write(ByteBuffer.wrap("Test NIO!".getBytes()));
            ReadFile("D:\\javaPro\\WebFluxDemo\\src\\test\\java\\com\\example\\webfluxdemo\\nioDemo\\text.txt");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void ReadFile(String fileName) throws IOException {
        RandomAccessFile file = new RandomAccessFile(fileName,"rw");
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        FileChannel channel = file.getChannel();
        channel.read(readBuffer);
        readBuffer.flip();
        System.out.println("读取到的数据:"+new String(readBuffer.array(),0, readBuffer.remaining()));
    }

    public static void TransferTo() throws IOException {
        RandomAccessFile inputStream = new RandomAccessFile("D:\\javaPro\\WebFluxDemo\\src\\test\\java\\com\\example\\webfluxdemo\\nioDemo\\text.txt", "rw");
        RandomAccessFile outputStream = new RandomAccessFile("D:\\javaPro\\WebFluxDemo\\src\\test\\java\\com\\example\\webfluxdemo\\nioDemo\\text2.txt", "rw");
        FileChannel inputChannel = inputStream.getChannel();
        inputChannel.transferTo(0, inputChannel.size(), outputStream.getChannel());
    }

    public static void TransferTo2() throws IOException {
        RandomAccessFile inputStream = new RandomAccessFile("D:\\javaPro\\WebFluxDemo\\src\\test\\java\\com\\example\\webfluxdemo\\nioDemo\\text.txt", "rw");
        FileChannel fileChannel = inputStream.getChannel();
        MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.READ_WRITE, 4, 4);
        map.put("yyds".getBytes());
        map.force();
    }
}
