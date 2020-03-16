package com.css.com.css.rpc.proxy;

import com.css.com.css.registry.RegistryCenter;
import com.css.com.css.rpc.RemoteCallProxyHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.*;

public abstract class AbstractInvocationHandler implements RemoteCallProxyHandler {
    public Logger logger = Logger.getLogger("");
    public String serverHost = "localhost";
    public String serviceName = "";
    public int port = 1000;

    /**
     * 关闭连接
     *
     * @param output 输出流
     * @param input  输入流
     * @param socket socket
     * @throws IOException
     */
    public void closeConnection(OutputStream output, InputStream input, Socket socket) {
        try {
            if (null != input) {
                input.close();
            }
            if (null != output) {
                output.close();
            }
            if (null != socket) {
                socket.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setServiceName(String name) {
        this.serviceName = name;
    }

    /**
     * 从远程注册中心获取服务地址
     *
     * @param serviceName 服务名
     */
    public Map<String, Object> getRemoteService(String serviceName) {
        logger.info("开始从远程注册中心获取服务者列表,服务名称" + serviceName);
        final List<Map<String, Object>> providerList = RegistryCenter.getServiceProviderContext(serviceName);
        if (null != providerList && providerList.size() > 0) {
            logger.info("获取到" + providerList.size() + "个服务提供者！");
        } else {
            throw new RuntimeException("未获取到服务提供者：");
        }
        if (null != providerList && providerList.size() > 0) {
            /****
             * 模拟客户端服负载
             * 随机从服务提供者中获取一个服务地址
             */
            logger.info("模拟服务端负载，开始随机获取一个服务提供者信息！");
            final int listSize = providerList.size();
            Random random = new Random();
            int num = random.nextInt() % listSize;
            return providerList.get(num);
        }else {
            return null;
        }

    }
}
