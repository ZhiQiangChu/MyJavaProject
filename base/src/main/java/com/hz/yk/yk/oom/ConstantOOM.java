package com.hz.yk.yk.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * ��̬��������Ҳ���п��ܳű�������
 *
 * ͬ������JVM������-XX:PermSize=10M -XX:MaxPermSize=10M
 * @author wuzheng.yk
 *         Date: 13-2-20
 *         Time: ����1:20
 */
public class ConstantOOM {
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        List<String> list = new ArrayList<String>();
        int i=0;
        while(true){
            list.add(String.valueOf(i++).intern());
        }
    }

}
