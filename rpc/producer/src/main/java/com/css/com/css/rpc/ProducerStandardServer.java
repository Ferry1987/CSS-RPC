package com.css.com.css.rpc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author liwei
 * @name RPC代理服务器
 * @desc 代理服务器支持多种协议
 * @time 2020年03月12日
 */
public interface ProducerStandardServer {
    /***启动服务器****/
    public void export(int port);
     /***销毁服务器****/
    public void destroy(Socket socket) throws IOException;
}
