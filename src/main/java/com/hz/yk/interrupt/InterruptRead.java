package com.hz.yk.interrupt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author ykdsg
 *         Date: 11-11-4
 *         Time: ����10:33
 */
class InterruptRead extends InterruptSupport {

    private FileInputStream in;

    @Override
    public void bussiness() {
        File file = new File("E:\\movie\\�ؼ���4��-����-��������Ӣ˫��Ļ.rmvb");
        try {
            in = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            while (in.read(bytes, 0, 1024) > 0) {
                // Thread.sleep(100);
                // if (Thread.interrupted()) {// ��ǰ��Interrupt��鷽ʽ
                // throw new InterruptedException("");
                // }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public FileInputStream getIn() {
        return in;
    }

    @Override
    public void interrupt() {
        try {
            in.getChannel().close();
            System.out.println("interrupt done ....");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
