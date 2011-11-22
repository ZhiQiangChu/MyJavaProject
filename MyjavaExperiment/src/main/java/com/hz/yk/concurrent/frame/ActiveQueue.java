package yk.concurrent.frame;

import java.util.Stack;

/**
 * Created by IntelliJ IDEA.
 * User: yangke
 * Date: 11-8-21
 * Time: ����2:34
 * To change this template use File | Settings | File Templates.
 */
public class ActiveQueue {
    public ActiveQueue() {
        _queue = new Stack();
    }

    public synchronized void enqueue(MethodRequest mr) {
        while (_queue.size() > QUEUE_SIZE) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        _queue.push(mr);
        notifyAll();
        System.out.println("Leave Queue");
    }

    public synchronized MethodRequest dequeue() {
        MethodRequest mr;

        while (_queue.empty()) {
            try {
                System.out.println("��ʼ���Ի�ȡ����");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mr = (MethodRequest) _queue.pop();
        notifyAll();

        return mr;
    }

    private Stack _queue;
    private final static int QUEUE_SIZE = 20;

}
