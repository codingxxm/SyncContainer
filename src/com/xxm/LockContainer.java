package com.xxm;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author CodingXXM
 * @desc 同步容器设计-最大容量10-lock实现
 * @date 2019/5/4 1:46
 **/
public class LockContainer<T> {
    private final LinkedList<T> list = new LinkedList<>();
    private static final int MAX_LENGTH = 10;
    private int size = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition consumer = lock.newCondition();
    private final Condition producer = lock.newCondition();

    public void put(T t){
        try {
            lock.lock();
            while (list.size() == MAX_LENGTH){
                producer.await();
            }
            list.add(t);
            ++ size;
            consumer.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public T get(){
        T t = null;
        try {
            lock.lock();
            while (list.size() == 0){
                consumer.await();
            }
            t = list.removeFirst();
            size --;
            producer.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return t;
    }

    public int getSize(){
        return size;
    }
}
