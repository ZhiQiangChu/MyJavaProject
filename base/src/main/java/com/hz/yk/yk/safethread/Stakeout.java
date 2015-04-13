package com.hz.yk.yk.safethread;

/**
 * ����߳�
 * Created by IntelliJ IDEA.
 * User: yangke
 * Date: 11-8-21
 * Time: ����6:06
 * To change this template use File | Settings | File Templates.
 */
public class Stakeout extends Thread {
    public static boolean keepchecking = true;  // ������ر�־
    boolean laststatus;     //�����ϴμ��״̬
    int maydeadtimes = 0;  //����߳̿��������ļ�����
    int maydeadtimeout = 3;//�����ж��߳������ı߽�����
    int deadtimes = 0;     //����߳����������ļ�����
    int deadtimeout = 3;   //�����ж��̲߳������ı߽�����
    ATask a;

    Stakeout() {
        start();
    }

    public void run() {
        schedule();
        while (keepchecking) {
            laststatus = a.sign;
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("M is Interrupted!");
            }
            System.out.println("M read A status = " + a.sign);
            //�������һ��ʱ���¼��״̬��a�е�״̬��ͬ��˵�����ܴ����߳����������
            if (laststatus == a.sign) {
                // �߳̿��������Ĵ��������趨ֵ
                if (++maydeadtimes >= maydeadtimeout) {
                    //ʵ���߳������Ĵ���
                    if (++deadtimes >= deadtimeout) {
                        System.out.println("Alert! A is unstable, M will stop it");
                        a = null;
                        break;
                    } else {
                        System.out.println("A is deaded!");
                        schedule();
                        System.out.println("M is restarting A!\n____________________________\n");
                    }
                }
            } else {
                maydeadtimes = 0;
            }
        }
    }

    /**
     * ��ʼ������
     */
    private void schedule() {
       ATask a = new ATask(this);
    }
}
