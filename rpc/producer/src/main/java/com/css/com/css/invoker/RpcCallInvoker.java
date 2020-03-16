package com.css.com.css.invoker;

import com.css.com.css.registry.RegistryCenter;
import com.css.com.css.rpc.vo.RpcTranMessage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public class RpcCallInvoker {
    private  Logger loger = Logger.getLogger(RpcCallInvoker.class.getName());
    /***反射调用实现类***/
    public  Object invoke(RpcTranMessage tranMsg) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        final String interClassName = tranMsg.getClassName();
        /***2、获取服务注册中心，实现类*****/
        loger.info("开始调用服务！");
        final Class clazz = RegistryCenter.getImplementsClassName(interClassName);
        if (null == clazz) {
            throw new RuntimeException("服务端没有改服务");
        }
        /****3、反射调用方法*****/
        final Method method = clazz.getMethod(tranMsg.getMethodName(), tranMsg.getMethodTypes());
        final Object res = method.invoke(clazz.newInstance(), tranMsg.getMethodParams());
        return res;
    }
}
