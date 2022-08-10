package com.nourish1709.deadlock;

public class Main {

    private static final Object monitor1 = new Object();
    private static final Object monitor2 = new Object();

    public static void main(String[] args) {

        final Thread thread1 = new Thread(() -> {
            synchronized (monitor1) {

                System.out.println("Inside the thread 1: monitor 1");
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (monitor2) {
                    System.out.println("Inside the thread 1: monitor 2");
                }
            }
        });

        final Thread thread2 = new Thread(() -> {
            synchronized (monitor2) {
                System.out.println("Inside the thread 2: monitor 2");
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (monitor1) {
                    System.out.println("Inside the thread 2: monitor 1");
                }
            }
        });

        final Thread stateMonitoringThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Thread 1: " + thread1.getState());
                System.out.println("Thread 2: " + thread2.getState());
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        stateMonitoringThread.start();
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        thread1.start();
        thread2.start();
    }
}
