package com.hz.yk.yk.classload;

/**
 * app classloader�޷���ȡloader1�е�Animal�࣬��Ϊ�����ռ��Ǹ����
 * @author wuzheng.yk
 *         Date: 15/1/29
 *         Time: 11:01
 */
public class Case2 {
    public static void main(String[] args) {
        MyClassLoader loader1 = new MyClassLoader(null, "loader1");//ʹ�ø�������������࣬�������ռ����
        try {
            System.out.println("loader1 parent="+loader1.getParent());
            Class<?> clazz = loader1.loadClass("Animal");
            System.out.println("class loader = "+clazz.getClassLoader());
            Object object = clazz.newInstance();
            Animal animal = (Animal) object;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
