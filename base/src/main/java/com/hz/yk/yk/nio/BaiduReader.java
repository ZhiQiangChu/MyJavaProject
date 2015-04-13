package com.hz.yk.yk.nio;

import com.hz.yk.yk.doclet.annotation.DocAPIResource;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @author yangke
 *         Date: 11-12-15
 *         Time: ����9:40
 */
@DocAPIResource
public class BaiduReader {
    private Charset charset = Charset.forName("GBK");// ����GBK�ַ���
    private SocketChannel channel;

    /**
     * readHTMLContent
     */
    public void readHTMLContent() {
        try {
            InetSocketAddress socketAddress = new InetSocketAddress(
                    "www.baidu.com", 80);
//step1:������
            channel = SocketChannel.open(socketAddress);
            //step2:��������ʹ��GBK����
            channel.write(charset.encode("GET " + "/ HTTP/1.1" + "\r\n\r\n"));
            //step3:��ȡ����
            ByteBuffer buffer = ByteBuffer.allocate(1024);// ����1024�ֽڵĻ���
            while (channel.read(buffer) != -1) {
                buffer.flip();// flip�����ڶ��������ֽڲ���֮ǰ���á�
                System.out.println(charset.decode(buffer));
                // ʹ��Charset.decode�������ֽ�ת��Ϊ�ַ���
                buffer.clear();// ��ջ���
            }
        } catch (IOException e) {
            System.err.println(e.toString());
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static void main(String[] args) {
        new BaiduReader().readHTMLContent();
    }

}
