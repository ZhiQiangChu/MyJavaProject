package com.hz.yk.yk.classload;

/**
 * ʹ���Լ����������
 * @author wuzheng.yk
 *         Date: 15/1/29
 *         Time: 11:00
 */
public class Case1 {

    public static void main(String[] args) {
        MyClassLoader loader1 = new MyClassLoader(null,"loader1");//�������Ϊnull��������ʹ��Ӧ��������������������Ĭ��ʹ��Ӧ���������
        try {
            Class clazz = loader1.loadClass("Animal");
            clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
