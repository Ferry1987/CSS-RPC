package com.css.com.css.rpc.proxy.protocol;

import com.css.com.css.registry.RegistryCenter;
import com.css.com.css.rpc.proxy.AbstractInvocationHandler;
import com.css.com.css.rpc.vo.RpcTranMessage;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

public class HttpInvocationHandler extends AbstractInvocationHandler {
    Logger logger = Logger.getLogger(HttpInvocationHandler.class.getName());
    private String ip = "localhost";
    private int port = 1000;

    public HttpInvocationHandler() {

    }

 /*   public HttpInvocationHandler(String ip, int port, String interClassName) {
        this.ip = ip;
        this.port = port;
        this.interClassName = interClassName;
    }*/

    /**
     * 代理类处理器
     *
     * @param proxy  代理对象
     * @param method 代理方法
     * @param args   方法参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("成功进入代理类");
        logger.info("开始初始化建立远程链接！");
        /****从远端注册中心，获取可以生产者的IP和端口****/
        logger.info("开始从远程注册中心获取服务者列表,服务名称" + serviceName);
        final Map<String, Object> serviceMap = getRemoteService(serviceName);
        if (null != serviceMap && !serviceMap.isEmpty()) {
            ip = String.valueOf(serviceMap.get("ip"));
            port = Integer.parseInt(String.valueOf(serviceMap.get("port")));
            logger.info("随机获取的服务提供者信息为：" + ip + port);
        } else {
            throw new RuntimeException("从服务中心获取服务信息为空，未找到" + serviceName + "服务注册信息");
        }
        final URL url = new URL("HTTP", ip, port, "/");
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        final OutputStream out = connection.getOutputStream();
        final ObjectOutputStream outputStream = new ObjectOutputStream(out);
        /***准备请求数据****/
        final RpcTranMessage tranMessage = new RpcTranMessage();
        tranMessage.setClassName(serviceName);
        tranMessage.setMethodTypes(method.getParameterTypes());
        tranMessage.setMethodName(method.getName());
        tranMessage.setMethodParams(args);
        //封装消息
        logger.info("开始远程调用！");
        outputStream.writeObject(tranMessage);
        outputStream.flush();
        logger.info("开始获取返回结果！");
        final ObjectInputStream inputStream = new ObjectInputStream(connection.getInputStream());
        final Object obj = inputStream.readObject();
        closeConnection(outputStream, inputStream, null);
        return obj;
    }
}
