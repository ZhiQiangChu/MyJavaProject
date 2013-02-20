package com.hz.yk.oom;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * �������Ǵ�������������������Ϣ�����ࡢ��̬�����ͳ�����
 * ��С��-XX:PermSize��-XX:MaxPermSize�����ڣ���̫���п��ܳű����ô�
 *
 * ����JVM����-verbose:gc -Xms10M -Xmx10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 * -XX:+HeapDumpOnOutOfMemoryError�����ܺܿ챨��OOM
 * @author wuzheng.yk
 *         Date: 13-2-19
 *         Time: ����10:20
 */
public class MethodAreaOOM {

    static class OOMOjbect {}


    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        while (true) {
            Enhancer eh = new Enhancer();
            eh.setSuperclass(OOMOjbect.class);
            eh.setUseCache(false);
            eh.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invokeSuper(obj, args);
                }
            });
            eh.create();
        }
    }
}
