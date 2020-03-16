package com.css.com.css.rpc;

public interface RemoteCallProxy<T> {
    /**
     * 调用远程服务
     * @param clazz 远程服务接口类
     * @return 远程服务返回对象
     */
    public Object callRemoteService(Class<T> clazz);
}
