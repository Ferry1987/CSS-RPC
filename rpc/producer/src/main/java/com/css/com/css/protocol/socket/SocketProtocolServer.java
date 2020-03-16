package com.css.com.css.protocol.socket;

import com.css.com.css.protocol.AbstractProtocolServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/***
 * 通过socat协议实现
 */
public class SocketProtocolServer extends AbstractProtocolServer {
    final Logger logger = Logger.getLogger(SocketProtocolServer.class.getName());
    final ExecutorService service = Executors.newFixedThreadPool(100);
    public void export(int port) {
        try {
            logger.info("开始启动Socket服务监听，监控端口为：" + port);
            ServerSocket serverSocket = new ServerSocket(port);
            initServerComponents(port);
            while (true) {
                //阻塞接收教习
                Socket socket = serverSocket.accept();
                socket.setKeepAlive(true);
                /****使用线程池模式执行*****/
                service.execute(new SocketRemoteInvoker(socket));
                /*final RemoteInvoker invoker= new SocketInvoker(socket);
                invoker.invoke();*/
            }
        } catch (IOException e) {
            logger.warning("服务器监听异常：" + e.toString());
        }
    }
}
