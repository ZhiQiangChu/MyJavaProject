package com.hz.yk.concurrent.exception;

/**
 * @author wuzheng.yk
 *         Date: 14-5-7
 *         Time: ����10:34
 */
public class Main {

    public static void main(String[] args) {
        ThreadA a  = new ThreadA();
        ErrHandler handle = new ErrHandler();
        //��������ȫ�ֵ� UncaughtExceptionHandler
        Thread.setDefaultUncaughtExceptionHandler(handle);
//        a.setUncaughtExceptionHandler(handle);// ���붨���ErrHandler
        a.start();

    }
}
