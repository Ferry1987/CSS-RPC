package com.css.com.css.rpc;

import com.css.com.css.protocol.http.HttpProtocolServer;

import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.logging.*;

public class ServerFactory {
    final static Logger logger = Logger.getLogger(ServerFactory.class.getName());

    /**
     * 获取服务端服务器
     *
     * @return
     */
    public static ProducerStandardServer getStandardServer() {
        //return new SocketProtocolServer();
        /****开始加载SPI实现类****/
        logger.info("开始加载SPI实现类");
        ServiceLoader<ProducerStandardServer> loader = ServiceLoader.load(ProducerStandardServer.class);
        final Iterator<ProducerStandardServer> iterator = loader.iterator();
        if (iterator.hasNext()) {
            ProducerStandardServer server = iterator.next();
            logger.info("找到SPI配置实现类，实现类:" + server.getClass().getName());
            return server;
        }
        logger.info("未找到SPI配置实现类，加载默认实现类：HttpProtocolServer");
        return new HttpProtocolServer();
    }
}
