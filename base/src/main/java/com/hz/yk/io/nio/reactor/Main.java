package com.hz.yk.io.nio.reactor;

import java.io.IOException;

/**
 * @author wuzheng.yk
 *         Date: 14-8-10
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Reactor reactor = new Reactor(9999);
        new Thread(reactor).start();
    }
}

