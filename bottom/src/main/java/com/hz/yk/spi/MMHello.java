package com.hz.yk.spi;

/**
 * �����ʵ�ֿ��Ժ�META-INF �е�com.hz.yk.spi.HelloInterface ���÷ŵ��ⲿ��jar��
 * @author wuzheng.yk
 *         Date: 15/4/13
 *         Time: 20:10
 */
public class MMHello implements HelloInterface{
    @Override
    public void sayHi() {
        System.out.println("mm say hi");
    }
}
