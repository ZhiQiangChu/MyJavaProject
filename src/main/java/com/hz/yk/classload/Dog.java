package com.hz.yk.classload;

/**
 * @author wuzheng.yk
 *         Date: 15/1/29
 *         Time: 11:06
 */
public class Dog {
    public Dog(){
        System.out.println("����dog,�ұ���"+this.getClass().getClassLoader()+"   ������");
    }
}
