package com.hz.yk.classload;

import java.lang.reflect.Method;

/**
 * �����ռ�Ĵ�����,ͬһ������Ӧ�õ���һ����ļ���������ͬ��
 * @author wuzheng.yk
 *         Date: 15/1/29
 *         Time: 11:07
 */
public class Case3 {
    public static void main(String[] args) {
        MyClassLoader loader1 = new MyClassLoader(null, "loader1");// ʹ�ø�������������࣬�������ռ����
        try {
            System.out.println(loader1.getParent());
            Class<?> clazz = loader1.loadClass("com.hz.yk.classload.Cat");
            System.out.println(clazz.getClassLoader());
            Object object = clazz.newInstance();
            Method method = clazz.getMethod("getDog");
            Dog dog = (Dog) method.invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
