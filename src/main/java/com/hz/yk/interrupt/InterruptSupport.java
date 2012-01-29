package com.hz.yk.interrupt;

import sun.nio.ch.Interruptible;

/**
 * @author ykdsg
 *         Date: 11-11-4
 *         Time: ����10:17
 *         To change this template use File | Settings | File Templates.
 */
abstract class InterruptSupport implements InterruptAble {

    private volatile boolean interrupted = false;
    private Interruptible interruptor = new Interruptible() {
        public void interrupt() {
            interrupted = true;
            //������Interruptible�¼����ӵĴ����������ص�InterruptSupport.this.interrupt()������
            // ������Լ���ʵ���Լ���ҵ���߼�������sock���رյȵȡ�
            InterruptSupport.this.interrupt(); // λ��3
        }
    };

    public final boolean execute() throws InterruptedException {
        try {
            //����sun�ṩ��blockedOn�������󶨶�Ӧ��Interruptible�¼��������ӵ�ָ����Thread�ϡ�
            blockedOn(interruptor); // λ��1
            if (Thread.currentThread().isInterrupted()) { // ������interrupted
                interruptor.interrupt();
            }
            // ִ��ҵ�����
            bussiness();
        } finally {
            //ִ����������չ��ӡ�����ʹ�����ӳ�ʱ������һ��Thread�����¼���Ӱ�졣
            blockedOn(null);   // λ��2
        }

        return interrupted;
    }

    public abstract void bussiness();

    public abstract void interrupt();

    // -- sun.misc.SharedSecrets --
    static void blockedOn(Interruptible intr) { // package-private
        sun.misc.SharedSecrets.getJavaLangAccess().blockedOn(Thread.currentThread(), intr);
    }
}