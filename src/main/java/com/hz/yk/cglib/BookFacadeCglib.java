package com.hz.yk.cglib;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @author wuzheng.yk
 *         Date: 14-7-11
 *         Time: ����5:32
 */
public class BookFacadeCglib implements MethodInterceptor {

    /**
     * �����������
     *
     * @param target
     * @return
     */
    public static Object getInstance(Object target) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        // �ص�����
        enhancer.setCallback(new BookFacadeCglib());
        // �����������
        return enhancer.create();
    }


    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("���￪ʼ");
        methodProxy.invokeSuper(obj, args);
        System.out.println("�������");
        return null;
    }
}

