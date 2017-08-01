package com.hz.yk.io.nio.reactor.connection;

/**
 * 
 * <pre><b>connection event handler interface.</b></pre>
 * @author 
 * <pre>seaboat</pre>
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * @version 1.0
 */
public interface ConnectionEventHandler {

	public void event(Connection connection, int event);

}
