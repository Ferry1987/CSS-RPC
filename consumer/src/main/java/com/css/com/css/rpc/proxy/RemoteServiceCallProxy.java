package com.css.com.css.rpc.proxy;

import com.css.com.css.rpc.RemoteCallProxy;
import com.css.com.css.rpc.RemoteCallProxyHandler;
import com.css.com.css.rpc.proxy.protocol.HttpInvocationHandler;

import java.util.logging.*;
import java.lang.reflect.Proxy;
import java.util.Iterator;
import java.util.ServiceLoader;

public class RemoteServiceCallProxy<T> implements RemoteCallProxy<T> {
    final Logger logger = Logger.getLogger(RemoteServiceCallProxy.class.getName());

    /**
     * 生成远程调用代理类
     *
     * @param clazz 远程调用接口类
     * @return
     */
    @Override
    public Object callRemoteService(Class<T> clazz) {
        logger.info("开始通过JDK-SPI获取代理处理器！");
        ServiceLoader<RemoteCallProxyHandler> serviceLoader = ServiceLoader.load(RemoteCallProxyHandler.class);
        Iterator<RemoteCallProxyHandler> iterator = serviceLoader.iterator();
        if (iterator.hasNext()) {
            final RemoteCallProxyHandler proxyObj = iterator.next();
            proxyObj.setServiceName(clazz.getSimpleName());
            logger.info("获取代理处理器成功，处理器名称为：" + proxyObj.getClass().getName());
            return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, proxyObj);
        }
        logger.info("获取代理处理器失败，默认处理器名称为：HttpInvocationHandler：");
        final RemoteCallProxyHandler defaultHandler = new  HttpInvocationHandler();
        defaultHandler.setServiceName(clazz.getSimpleName());
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},defaultHandler );

    }
}
