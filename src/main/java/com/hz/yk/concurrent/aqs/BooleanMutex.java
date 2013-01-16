package com.hz.yk.concurrent.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author wuzheng.yk
 *         Date: 13-1-8
 *         Time: ����3:41
 */
public class BooleanMutex {
    private Sync sync;

    public BooleanMutex() {
        sync = new Sync();
        set(false);
    }

    public BooleanMutex(Boolean mutex) {
        sync = new Sync();
        set(mutex);
    }

    /**
     * �����ȴ�BooleanΪtrue
     *
     * @throws InterruptedException
     */
    public void get() throws InterruptedException {
        sync.innerGet();
    }

    /**
     * �����ȴ�BooleanΪtrue,�������ó�ʱʱ��
     *
     * @param timeout
     * @param unit
     * @throws InterruptedException
     * @throws TimeoutException
     */
    public void get(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
        sync.innerGet(unit.toNanos(timeout));
    }

    /**
     * �������ö�Ӧ��Boolean mutex
     *
     * @param mutex
     */
    public void set(Boolean mutex) {
        if (mutex) {
            sync.innerSetTrue();
        } else {
            sync.innerSetFalse();
        }
    }

    public boolean state() {
        return sync.innerState();
    }

    /**
     * Synchronization control for BooleanMutex. Uses AQS sync state to
     * represent run status
     */
    private final class Sync extends AbstractQueuedSynchronizer {

        /** State value representing that TRUE */
        private static final int  TRUE             = 1;
        /** State value representing that FALSE */
        private static final int  FALSE            = 2;
        private static final long serialVersionUID = 1L;

        private boolean isTrue(int state) {
            return (state & TRUE) != 0;
        }

        /**
         * ʵ��AQS�Ľӿڣ���ȡ���������ж�
         */
        @Override
        protected int tryAcquireShared(int state) {
            // ���Ϊtrue��ֱ�������ȡ������
            // ���Ϊfalse�������������У��ȴ�������
            return isTrue(getState()) ? 1 : -1;
        }

        /**
         * ʵ��AQS�Ľӿڣ��ͷŹ��������ж�
         */
        @Override
        protected boolean tryReleaseShared(int ignore) {
            //ʼ�շ���true���������release
            return true;
        }

        boolean innerState() {
            return isTrue(getState());
        }

        void innerGet() throws InterruptedException {
            acquireSharedInterruptibly(0);
        }

        void innerGet(long nanosTimeout) throws InterruptedException, TimeoutException {
            if (!tryAcquireSharedNanos(0, nanosTimeout))
                throw new TimeoutException();
        }

        void innerSetTrue() {
            for (;;) {
                int s = getState();
                if (s == TRUE) {
                    return; //ֱ���˳�
                }
                if (compareAndSetState(s, TRUE)) {// cas����״̬�����Ⲣ������true����
                    releaseShared(0);//�ͷ�һ�������󣬻���һ��������Thread
                }
            }
        }

        void innerSetFalse() {
            for (;;) {
                int s = getState();
                if (s == FALSE) {
                    return; //ֱ���˳�
                }
                if (compareAndSetState(s, FALSE)) {//cas����״̬�����Ⲣ������false����
                    setState(FALSE);
                }
            }
        }

    }

}
