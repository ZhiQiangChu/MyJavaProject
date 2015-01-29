package com.hz.yk.classload;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * ͨ����̬����������web app�����ռ�Ĵ�����������ռ�һ�£��Ͳ���������������ռ���������
 * PS: ������Դ�͸�����ռ䣬���Կ���ֱ��ʹ�÷��������һ�������ռ��Class���в�������HSFTest���淴�����RfqRequestDTO��
 * @author wuzheng.yk
 *         Date: 15/1/29
 *         Time: 11:25
 */
public class HSFTest {
    public HSFTest() {
        System.out.println("����HSFTest,�ұ���" + this.getClass().getClassLoader() + "   �����ˣ�");
    }

    public Object getObject(Class<?> interfaceClass, final Class<?> returnClass) {
        return Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object o = returnClass.newInstance();
                returnClass.getDeclaredMethod("setName", String.class).invoke(o, "invoke " + method.getName());
                return o;
            }
        });
    }
}
