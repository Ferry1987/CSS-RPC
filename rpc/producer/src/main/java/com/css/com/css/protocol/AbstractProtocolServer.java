package com.css.com.css.protocol;

import com.css.com.css.core.ServiceRegisterComponent;
import com.css.com.css.rpc.ProducerStandardServer;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.*;
public abstract class AbstractProtocolServer implements ProducerStandardServer {
    final Logger logger = Logger.getLogger(AbstractProtocolServer.class.getName());
    /***
     *
     * @param socket  输入流
     * @throws IOException
     */
    public void destroy(Socket socket) throws IOException {
        if (null != socket) {
            socket.close();
        }
    }

    /***
     * 初始化系统组件
     */
    public void initServerComponents(int port) {
        logger.info("开始初始化系统组件");
        ServiceRegisterComponent component = new ServiceRegisterComponent();
        component.init(port);
    }
}
