package yk.concurrent.frame;

/**
 * Created by IntelliJ IDEA.
 * User: yangke
 * Date: 11-8-21
 * Time: ����2:55
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    public static void main(String[] args) {
        /*
        �����߼�����ǰ������sayHello����ĵ��÷���
         */
//        Service s = new ServiceImp();
//        Client c = new Client(s);
//        c.requestService();

        /*
        �����߼����Ӻ󣬶���sayHello����ĵ��÷���
         */
        Service s = new ServiceProxy();
        Client c = new Client(s);
        c.requestService();
    }
}
