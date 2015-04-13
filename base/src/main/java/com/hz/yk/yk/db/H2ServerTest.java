package com.hz.yk.yk.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.h2.tools.Server;
import org.junit.Test;

/**
 * ���ֶ�start/stop H2 ���ݿ�
 * @author wuzheng.yk
 *         Date: 15/2/11
 *         Time: 18:28
 */
public class H2ServerTest {
    @Test
    public void h2Test() {
        start();
        crudTest();
        stop();
    }

    private Server server;

    public void start() {
        try {
            System.out.println("��������h2...");
            server = Server.createTcpServer(new String[]{"-tcp", "-tcpAllowOthers", "-tcpPort", "8043"}).start();
            System.out.println("�����ɹ���" + server.getStatus());
        } catch (SQLException e) {
            System.out.println("����h2����" + e.toString());

            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        if (server != null) {
            System.out.println("���ڹر�h2...");
            server.stop();
            System.out.println("�رճɹ�.");
        }
    }

    public void crudTest() {
        try {
            Class.forName("org.h2.Driver");

            // connect to h2
            Connection conn = DriverManager.getConnection("jdbc:h2:./h2db/sxaz42b4", "sa", "sa");

            Statement stat = conn.createStatement();

            // create table
            stat.execute("CREATE TABLE TEST(NAME VARCHAR)");

            // insert table
            stat.execute("INSERT INTO TEST VALUES('�������µ����')");
            stat.execute("INSERT INTO TEST VALUES('http://yjmyzz.cnblogs.com/')");

            // retrive data
            ResultSet result = stat.executeQuery("select name from test ");
            int i = 1;
            while (result.next()) {
                System.out.println(i++ + ":" + result.getString("name"));
            }

            // drop table
            stat.execute("DROP TABLE TEST");

            result.close();
            stat.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
