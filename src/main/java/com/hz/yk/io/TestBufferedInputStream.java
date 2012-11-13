package com.hz.yk.io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author ykdsg
 *         Date: 12-5-27
 *         Time: ����12:07
 */
public class TestBufferedInputStream {

    public static void main(String[] args) {
            testBufferedStream();
        }


        /**
         * ������ֽ�������
         */
        public static void testBufferedStream() {
    		BufferedInputStream bis=null;
            try {
    			FileInputStream fis = new FileInputStream("F:\\My Document\\dropbox\\����\\java io\\����\\x.txt");
                bis = new BufferedInputStream(fis);
                int bench = 0;
                byte tempBuf[] = new byte[8];      //�����ֽ������棬��СΪ1024���ֽ�
                while ((bis.read(tempBuf)) != -1) {
    				String copyStr = new String(tempBuf);
                    System.out.println(copyStr);
                    bench++;
                }
    			System.out.println("�ļ�����");
                System.out.println("bench=" + bench);
            } catch (FileNotFoundException e) {
                System.out.println("�Ҳ���ָ�����ļ���");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("�ļ���ȡʱ����IO�쳣��");
                e.printStackTrace();
            } finally {
    			try{
    				bis.close();
    			}catch(IOException e){
    				e.printStackTrace();
    			}
    		}
        }
}
