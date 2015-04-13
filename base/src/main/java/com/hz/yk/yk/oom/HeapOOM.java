package com.hz.yk.yk.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * ���ж����ʵ�����䶼��Java���Ϸ����ڴ棬�Ѵ�С��-Xmx��-Xms������
 *
 * ����JVM����-verbose:gc -Xms10M -Xmx10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+HeapDumpOnOutOfMemoryError�����ܺܿ챨��OOM��
 Exception in thread "main" java.lang.OutOfMemoryError: Java heap space

 �������Զ�����Dump��
 * @author wuzheng.yk
 *         Date: 13-2-19
 *         Time: ����10:19
 */
public class HeapOOM {

    static class OOMObject{}

    /**
     * @param args
     */
    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<OOMObject>();

        while(true){
            list.add(new OOMObject());
        }
    }

}
