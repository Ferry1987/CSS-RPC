package com.css.com.css.rpc.invoker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/***
 * 通过socat协议实现
 */
public abstract class RpcCallServer {
    /****
     * 定义任务线程池
     */
    public final ExecutorService service = Executors.newFixedThreadPool(100);

    /***
     * 启动server方法
     * @param port 端口
     */
    public abstract void start(int port);
}
