package com.hz.yk.yk.xml;

import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author wuzheng.yk
 *         Date: 13-3-17
 *         Time: ����8:59
 */
public class JAXPDemo {

    public static void main(String[] args) {
        String filename = "beans.xml";
        URL url = JAXPDemo.class.getClassLoader().getResource(filename);
        if(url==null){
            System.out.println("url is null");
            return;
        }
        try {
            //�õ�DocumentBuilderFactory�������Ĺ���ʵ��
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //��DocumentBuilder�й������ DOM ������
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            //���� XML �ĵ������������õ�һ�� Document
            Document document = documentBuilder.parse(url.getPath());
            Element root = document.getDocumentElement();
            NodeList beans = root.getChildNodes();

            for (int i = 0; i < beans.getLength(); i++) {
                Node bean = beans.item(i);
                if (bean.getNodeType() == Node.ELEMENT_NODE) {
                    //ȡ�ýڵ������ֵ
                    String beanId = bean.getAttributes().getNamedItem("id").getNodeValue();
                    System.out.println("bean id="+beanId);

                    for (Node node = bean.getFirstChild(); node != null; node = node
                            .getNextSibling()) {
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            if (node.getNodeName().equals("property")) {
                                String name = node.getNodeName();
                                String nodeValue = node.getFirstChild().getNodeValue();
                                System.out.println(name);
                                System.out.println(nodeValue);
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {

        }
    }


}
