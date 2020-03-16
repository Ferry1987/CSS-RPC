package com.css.com.css.rpc;

import com.css.com.css.rpc.proxy.RemoteServiceCallProxy;

public class BizServiceUtils {

    public static Object callRomote(Class clzz){
        RemoteCallProxy proxy = new RemoteServiceCallProxy();
        return proxy.callRemoteService(clzz);

    }
}
