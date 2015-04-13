package com.hz.yk.yk.classload;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * �����ռ������
 * ����Ľӿں�loader����ͬһ�������ռ��У����Բ��ɼ����׳��쳣
 * Ҫ�ж�����Class �Ƿ���ȣ�����Ҫ����������������Ҫ�������ռ���������
 * @author wuzheng.yk
 *         Date: 15/1/29
 *         Time: 11:17
 */
public class Case5 {
    public static void main(String[] args) {
        MyClassLoader loader1 = new MyClassLoader(null, "loader1");// ʹ�ø�������������࣬�������ռ����
        try {
            System.out.println(loader1.getParent());
            RfqService rfqService = (RfqService) Proxy.newProxyInstance(loader1, new Class[]{RfqService.class}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    return method.getName();
                }
            });
            System.out.println(rfqService.getClass().getClassLoader());
            System.out.println(rfqService.getRfqTestName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
