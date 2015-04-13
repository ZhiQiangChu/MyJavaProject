package com.hz.yk.yk.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

/**
 * @author yangke
 *         Date: 11-12-14
 *         Time: ����9:58
 */
public class Main {


    public static void main(String[] args) throws IOException {
        //        ���ȴ���һ��Selector����NIO��SelectorProvider�ṩSelector��
        Selector socketSelector = SelectorProvider.provider().openSelector();
        //        ����һ���������׽���ͨ��,����ͨ�����óɷ�������
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        //        ��iii�����������׽���ͨ���������׽��ְ󶨵�ָ���ĵ�ַ
        InetSocketAddress address = new InetSocketAddress("localhost", 9999);
        serverChannel.socket().bind(address);
        //        ��iv�����������׽���ͨ��ע�ᵽSelector�У�����ָ�����������׽��ִ���ʲô״̬ʱ֪ͨ�������ڷ������׽���ͨ�������ķ������׽���ֻ��һ��״̬����OP_ACCEPT״̬��
        serverChannel.register(socketSelector, SelectionKey.OP_ACCEPT);

        while (true) {
            socketSelector.select();
            Iterator selectedKeys = socketSelector.selectedKeys().iterator();
            while (selectedKeys.hasNext()) {
                SelectionKey key = (SelectionKey) selectedKeys.next();
                selectedKeys.remove();
                if (key.isAcceptable()) {//�������׽����յ��ͻ����󣬴�ʱֻ�����Ƿ������׽���
                    //ȡ��key��Ӧ��Channel��ȡ��Channel��Ӧ�ķ������׽���ͨ����Ӧ�ķ�������
                    //���֣�Ȼ����ô˷������׽��ֵ�accept������ͻ��������ӣ���accept()��
                    //�ص��׽���ע�ᵽSelector�У��Ա��ڸ��׽���������ʱ��ȡʱ����Selector
                    //ͨ��Ӧ�ó���
                } else if (key.isReadable()) {//�׽������������ݶ�ȡ׼��
                    //��key��ȡ����Ӧ��Channel��ʹ��Channel��ȡ���ݣ���ǰ���Ѿ����۹�
                } else if (key.isWritable()) {//�׽�������������д��׼��
                    //��key��ȡ����Ӧ��Channel,ʹ��Channelд�����ݣ���ǰ���Ѿ����۹�
                }
            }
        }


    }


}
