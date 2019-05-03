package com.xxm;

import java.util.LinkedList;

/**
 * @author CodingXXM
 * @desc 同步容器设计-最大容量10-synchronized实现
 * @date 2019/5/3 23:40
 **/
public class SynchronizedContainer<T> {
    private final LinkedList<T> list = new LinkedList<>();
    private static final int MAX_LENGTH = 10;
    private int size = 0;

    public synchronized void put(T t){
        //使用while而不使用if是因为，如果使用if多个生产者线程同时收到notify的话，那么都会顺序抢锁并执行下去，如果线程1执行后容器满了，线程2又获取到锁顺序执行添加，就会出现错误
        //使用while则多个线程被notify准备获取锁执行时，当线程1执行后，线程2想要执行前while循环会做一次判断，这时候会再次判断容器的容量，也就保证了不会出错
        //实际场景中，while+wait使用率99%
        while (list.size() == MAX_LENGTH){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(t);
        ++ size;
        //执行完添加，通知所有线程此时锁已经被释放，可以获取锁来执行
        this.notifyAll();
    }

    public synchronized T get(){
        T t = null;
        while (list.size() == 0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        t = list.removeFirst();
        size --;
        this.notifyAll();
        return t;
    }

    public int getSize(){
        return size;
    }
}
