package com.hz.yk.classload;

/**
 * @author wuzheng.yk
 *         Date: 15/1/29
 *         Time: 11:06
 */
public class Cat {
    Dog dog = null;

    public Cat() {
        System.out.println("����cat������" + this.getClass().getClassLoader() + "   ������");
        dog = new Dog();
    }

    public Dog getDog() {
        return dog;
    }
}
