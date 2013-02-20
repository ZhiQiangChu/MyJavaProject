package com.hz.yk.oom;

/**
 * ����JVM������-Xss128k�������쳣��
 Exception in thread "main" java.lang.StackOverflowError
 * @author wuzheng.yk
 *         Date: 13-2-20
 *         Time: ����2:07
 */
public class StackOOM {
    /**
     * @param args
     */

    private int stackLength = 1;

    public void stackLeak(){
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) throws Throwable{
        // TODO Auto-generated method stub
        StackOOM oom = new StackOOM();
        try{
            oom.stackLeak();
        }catch(Throwable err){
            System.out.println("Stack length:" + oom.stackLength);
            throw err;
        }

    }

}
