package com.hz.yk.concurrent.aqs;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * @author wuzheng.yk
 *         Date: 13-1-13
 *         Time: ����8:49
 */
public class FIFOMutex {
    private AtomicBoolean locked = new AtomicBoolean(false);
    private Queue<Thread> waiters = new ConcurrentLinkedQueue<Thread>();


    public void lock() {
        boolean wasInterrupted = false;
        Thread current = Thread.currentThread();
        waiters.add(current);

        //���waiters�ĵ�һ���ȴ��߲�Ϊ��ǰ�̣߳����ߵ�ǰlocked��״̬Ϊ��ռ��(true)
        //��ôparkס��ǰ�߳�
        System.out.println(Thread.currentThread() + ",locked=" + locked.get());
        while (waiters.peek() != current || !locked.compareAndSet(false, true)) {
            LockSupport.park();

            //���̱߳�unparkʱ����һʱ���鵱ǰ�߳��Ƿ�interrupted
            if (Thread.interrupted()) {
                wasInterrupted = true;
            }
        }

        //�õ����󣬴ӵȴ������Ƴ���ǰ�̣߳���������������ǰ�߳��Ѿ���interrupted��
        //��ô��interruptһ���Ա㹩�ⲿ��Ӧ��
        waiters.remove();
        if (wasInterrupted) {
            current.interrupt();
        }
    }

    //unlock�߼���Լ򵥣��趨��ǰ��Ϊ����״̬�����ҽ��ȴ�������
    //�ĵ�һ���ȴ��̻߳���
    public void unlock() {
        locked.set(false);
        LockSupport.unpark(waiters.peek());
    }

    public static void main(String[] args) throws InterruptedException {
        final FIFOMutex fifoMutex = new FIFOMutex();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread1 running");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "thread1 lock..........");
                fifoMutex.lock();
                System.out.println(Thread.currentThread() + "thread1 unlock..........");
            }
        });


        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread2 running");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "thread2 lock..........");
                fifoMutex.lock();
                System.out.println(Thread.currentThread() + "thread2 unlock..........");
            }
        });

        thread1.start();
        thread2.start();
        Thread.sleep(800);

    }
}
