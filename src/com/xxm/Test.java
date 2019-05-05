package com.xxm;

import java.util.concurrent.TimeUnit;

/**
 * @author CodingXXM
 * @desc test
 * @date 2019/5/4 1:22
 **/
public class Test {


    public static void main(String[] args) {
        SynchronizedContainer<String> container = new SynchronizedContainer<>();

        for (int i=0;i<10;i++){
            new Thread(()->{
                for(int j=0;j<5;j++){
                    System.out.println(container.get());
                }
            },"consumer" + i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i=0;i<2;i++){
            new Thread(()->{
                for (int j=0;j<25;j++){
                    container.put(Thread.currentThread().getName()+" "+j);
                }
            },"producer:" + i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LockContainer<String> container1 = new LockContainer<>();
        for (int i=0;i<10;i++){
            new Thread(()->{
                for(int j=0;j<5;j++){
                    System.out.println(container1.get());
                }
            },"consumer1" + i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i=0;i<2;i++){
            new Thread(()->{
                for (int j=0;j<25;j++){
                    container1.put(Thread.currentThread().getName()+" "+j);
                }
            },"producer1:" + i).start();
        }
    }

}
