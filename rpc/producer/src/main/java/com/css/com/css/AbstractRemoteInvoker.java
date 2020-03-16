package com.css.com.css;

import com.css.com.css.registry.RegistryCenter;
import com.css.com.css.rpc.RemoteInvoker;
import com.css.com.css.rpc.vo.RpcTranMessage;
import java.util.logging.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbstractRemoteInvoker implements RemoteInvoker {
    final Logger logger = Logger.getLogger(AbstractRemoteInvoker.class.getName());
    public Object invokeLocalMethod(RpcTranMessage tranMsg) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        final String interClassName = tranMsg.getClassName();
        /***2、获取服务注册中心，实现类*****/
        logger.info("开始调用服务！");
        final Class clazz = RegistryCenter.getImplementsClassName(interClassName);
        if (null == clazz) {
            throw new RuntimeException("服务端没有该服务");
        }
        /****3、反射调用方法*****/
        final Method method = clazz.getMethod(tranMsg.getMethodName(), tranMsg.getMethodTypes());
        final Object res = method.invoke(clazz.newInstance(), tranMsg.getMethodParams());
        return res;
    }
}
