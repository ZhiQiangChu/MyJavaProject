package com.hz.yk.yk.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author wuzheng.yk
 *         Date: 15/2/11
 *         Time: 12:03
 */
public class H2DBTest {
    String sourceURL="jdbc:h2:tcp://localhost/mem:testmemdb";//H2DB mem mode
    String user="sa";
    String key="";
    public void runInsertDelete(){
        try{
            try{
                Class.forName("org.h2.Driver");//HSQLDB Driver
            }catch(Exception e){
                e.printStackTrace();
            }
            Connection conn= DriverManager.getConnection(sourceURL, user, key);//��������������
            Statement stmt=conn.createStatement();
            //����һ�� Statement �������� SQL ��䷢�͵����ݿ⡣
            //stmt.executeUpdate("DELETE FROM mytable WHERE name=\'NO.2\'");
            //ִ�з����ҵ�һ���� methodName ����ͬ���ķ���������Ŀ���ϵ��ø÷�����
            //stmt.execute("CREATE TABLE idtable(id INT,name VARCHAR(100));");
            stmt.execute("INSERT INTO idtable VALUES(1,\'MuSoft\')");
            stmt.execute("INSERT INTO idtable VALUES(2,\'StevenStander\')");
            stmt.close();
            conn.close();

        }catch(SQLException sqle){
            System.out.println("SQL ERROR!");
        }

    }
    public void query(String SQL){
        try{
            try{
                Class.forName("org.h2.Driver");
            }catch(Exception e){
                e.printStackTrace();
            }
            Connection conn=DriverManager.getConnection(sourceURL,user,key);//��������������
            Statement stmt=conn.createStatement();//
            ResultSet rset=stmt.executeQuery(SQL);
            //ִ�з����ҵ�һ���� methodName ����ͬ���ķ���������Ŀ���ϵ��ø÷�����
            while(rset.next())
            {
                System.out.println(rset.getInt("id")+"  "+rset.getString("name"));
            }
            rset.close();
            stmt.close();
            conn.close();
        }catch(SQLException sqle){
            System.err.println(sqle);
        }
    }
    public static void main(String args[]){
        H2DBTest mt=new H2DBTest();
        mt.runInsertDelete();
        mt.query("SELECT * FROM idtable");
    }
}
