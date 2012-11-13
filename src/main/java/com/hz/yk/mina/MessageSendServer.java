package com.hz.yk.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * @author yangke
 *         Date: 12-8-30
 *         Time: ����4:48
 */
public class MessageSendServer {
    private static final int port = 11111;
    public static void main(String[]args) throws IOException {
        //����˼����˿���
        IoAcceptor acceptor = new NioSocketAcceptor();
        //��־filter
        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        //�������л�������������java�������л��ɶ�������
        acceptor.getFilterChain().addLast(
                "Objectcodec",
                new ProtocolCodecFilter(
                        (ProtocolCodecFactory) new ObjectSerializationCodecFactory()));
        //ҵ����handler
        acceptor.setHandler(new ServerHandler());
        //���ü����˿�
        acceptor.setDefaultLocalAddress(new InetSocketAddress(port));
        //�����˼���accptor
        acceptor.bind();

        System.out.println("Server starts to listen to PORT :"+port);

    }

}
