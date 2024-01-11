package org.example;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Main myMain = new Main();
//        myMain.thread(1,2);
        Future<Integer> integerFuture = myMain.thread2(1, 2);
        Integer result = integerFuture.get();
        Thread thread = Thread.currentThread();
        System.out.println("thread name = " + thread.getName());
        System.out.println("Main result: a+b = " + result);
    }
    public void thread(int a, int b) throws InterruptedException {
        Thread thread = new Thread(new MyTask(a, b));
        thread.setName("myTask");
        thread.start();
        String name = thread.getName();
        System.out.println("name = " + name);
        Thread.State state = thread.getState();
        System.out.println("state = " + state);
        Thread.sleep(1000);
        if (thread.isAlive()) {
            thread.interrupt();
        }
        System.out.println("Now = " + thread.getState());
    }

    public Future<Integer> thread2(int a, int b) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> res = executorService.submit(new MyTask2(a, b));
        try {
            System.out.println("thread result: a+b = " + res.get());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        executorService.shutdown();
        return res;
    }

    public static class MyTask implements Runnable {
        private final int a;
        private final int b;

        public MyTask(int a, int b) {
            this.a = a;
            this.b = b;
        }
        @Override
        public void run() {
            Thread thread = Thread.currentThread();
            System.out.println("thread name = " + thread.getName());
            System.out.println("a+b = " + (a+b));
        }
    }

    public static class MyTask2 implements Callable<Integer> {
        private final int a;
        private final int b;
        public MyTask2(int a, int b) {
            this.a = a;
            this.b = b;
        }
        @Override
        public Integer call() {
            Thread thread = Thread.currentThread();
            System.out.println("thread name = " + thread.getName());
            System.out.println("a+b = " + (a+b));
            return a+b;
        }
    }
}