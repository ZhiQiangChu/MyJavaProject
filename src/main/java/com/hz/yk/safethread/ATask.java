package com.hz.yk.safethread;

import org.jetbrains.annotations.Nullable;

/**
 * Created by IntelliJ IDEA.
 * User: yangke
 * Date: 11-8-21
 * Time: ����6:04
 * To change this template use File | Settings | File Templates.
 */
public class ATask extends Thread {
    /**
     * A�������޸��Լ���dead��־��dead��һ������������������ֻҪAû������
     * ��ôdead�϶������ڱ仯�ģ������������ࣩ
     */
    public boolean dead = false;
    Stakeout m;

    ATask(Stakeout m) {
        m = m;
        start();
    }

    public String getUserName(){
        return null;
    }


    public void run() {
        try {
            for (int i = -3; i <= 5; i++) {
                int j = 1 / i;   // ��Ϊ���ù���������
                dead = !dead;  // �״̬
                System.out.println("i=" + i + ": status=" + dead);
                try {
                    sleep(2000);
                } catch (InterruptedException ie) {
                    System.out.println("A is Interrupted!");
                }
            }
            m.Keepchecking = false;  //A ����������رռ���߳�
            System.out.println("A is Ending M");
        } catch (Exception e) {
            System.out.println("A become Exception!");
        }
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("�̣߳�"+Thread.currentThread()+" is over by gc~~~~~~~~~~~~~");
    }
}