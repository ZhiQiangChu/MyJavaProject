package com.hz.yk.yk.guava.collections;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * ��ֵ�Ե�˫��ӳ��
 * @author wuzheng.yk
 *         Date: 14-7-18
 *         Time: ����3:57
 */
public class BiMapMain {
    public static void main(String[] args) {
        BiMap<String,Long> biMap = HashBiMap.create();
        biMap.put("test", 10001L);
        System.out.println(biMap.get("test"));
        System.out.println(biMap.inverse().get(10001L));


    }
}
