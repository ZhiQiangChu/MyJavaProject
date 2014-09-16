package com.hz.yk.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

/**
 * @author wuzheng.yk
 *         Date: 14-9-9
 *         Time: ����10:00
 */
public class NettyClient {
    final static String host = "127.0.0.1";
    final static int port = 8088;

    public static void main(String[] args) {
        Client client = new Client();
        client.config(host, port).start();
    }
}

class Client {
    ClientBootstrap bootstrap;
    ChannelHandler myHandler = new MyClientHandler();
    StringClientHandler stringHandler = new StringClientHandler();
    String host;
    int port;
    public Client() {
        bootstrap = new ClientBootstrap(
                new NioClientSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(stringHandler,myHandler);
            }
        });
    }

    Client config(String host, int port) {
        this.host = host;
        this.port = port;
        bootstrap.setOption("remoteAddress", new InetSocketAddress(this.host, this.port));
        return this;
    }

    void start() {
        bootstrap.connect();
    }

    class MyClientHandler extends SimpleChannelUpstreamHandler {
        @Override
        public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
                throws Exception {
            System.out.println("Client Channel closed " + e);
        }

        @Override
        public void channelConnected(ChannelHandlerContext ctx,
                                     ChannelStateEvent e) throws Exception {
            System.out.println("Client Channel connected " + e);
        }

        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
                throws Exception {
            try {
                String buf = (String) e.getMessage();
                System.out.println("Client reseived message : " + buf);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw ex;
            }
        }
    }
}

class StringClientHandler extends SimpleChannelUpstreamHandler {
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
            throws Exception {
        try {
            ChannelBuffer buf = (ChannelBuffer) e.getMessage();
            byte[] bytes = buf.array();
            System.out
                    .println("Client reseived message and convert it to a String!");
            Channels.fireMessageReceived(ctx, new String(bytes));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * ���󶨵�����˵�ʱ�򴥷���������˷���Ϣ��
     *
     * @alia OneCoder
     * @author lihzh
     */
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
        // �ֶη�����Ϣ
        sendMessageByFrame(e);
    }

    /**
     * ��<b>"Hello, I'm client."</b>�ֳ����ݷ��͡� <br>
     * Hello, <br>
     * I'm<br>
     * client.<br>
     *
     * @param e
     *            Netty�¼�
     * @author lihzh
     */
    private void sendMessageByFrame(ChannelStateEvent e) {
        String msgOne = "Hello, ";
        String msgTwo = "I'm ";
        String msgThree = "client.";
        e.getChannel().write(tranStr2Buffer(msgOne));
        e.getChannel().write(tranStr2Buffer(msgTwo));
        e.getChannel().write(tranStr2Buffer(msgThree));
    }

    /**
     * ���ַ���ת����{@link ChannelBuffer}��˽�з����������ַ����ķǿ��жϡ�
     *
     * @param str
     *            ��ת���ַ�����Ҫ���null
     * @return ת�����ChannelBuffer
     * @author lihzh
     */
    private ChannelBuffer tranStr2Buffer(String str) {
        ChannelBuffer buffer = ChannelBuffers.buffer(str.length());
        buffer.writeBytes(str.getBytes());
        return buffer;
    }
}