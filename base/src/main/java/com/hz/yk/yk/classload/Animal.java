package com.hz.yk.yk.classload;

/**
 * @author wuzheng.yk
 *         Date: 15/1/29
 *         Time: 10:22
 */
public class Animal {
    public int a = 2;

    public Animal() {
        System.out.println("����animal,�ұ���" + this.getClass().getClassLoader() + "   �����ˣ�");
    }
}
