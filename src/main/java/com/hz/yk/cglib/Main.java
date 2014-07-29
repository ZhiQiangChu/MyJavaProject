package com.hz.yk.cglib;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * ͨ��cglib ��̬����java��
 * @author wuzheng.yk
 *         Date: 14-7-11
 *         Time: ����5:14
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        // �������Ա����
        HashMap propertyMap = new HashMap();
        propertyMap.put("id", Class.forName("java.lang.Integer"));
        propertyMap.put("name", Class.forName("java.lang.String"));
        propertyMap.put("address", Class.forName("java.lang.String"));

        // ���ɶ�̬ Bean
        CglibBean bean = new CglibBean(propertyMap);
        // �� Bean ����ֵ
        bean.setValue("id", new Integer(123));
        bean.setValue("name", "454");
        bean.setValue("address", "789");
        // �� Bean �л�ȡֵ����Ȼ�˻��ֵ�������� Object
        System.out.println("  >> id      = " + bean.getValue("id"));
        System.out.println("  >> name    = " + bean.getValue("name"));
        System.out.println("  >> address = " + bean.getValue("address"));

        // ���bean��ʵ��
        Object object = bean.getObject();

        // ͨ������鿴���з�����
        Class clazz = object.getClass();
        System.out.println("class=" + clazz);
        Method[] methods = clazz.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            System.out.println(methods[i].getName());
        }

    }

}
