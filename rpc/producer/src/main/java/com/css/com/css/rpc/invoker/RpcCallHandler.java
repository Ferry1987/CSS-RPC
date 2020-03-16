package com.css.com.css.rpc.invoker;

import com.css.com.css.invoker.RpcCallInvoker;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Rpc调用监听器
 */
public abstract class RpcCallHandler extends RpcCallInvoker implements Runnable {
    public void closeConnection(OutputStream output, InputStream input, Socket socket) throws IOException {
        if (null != input) {
            input.close();
        }
        if (null != output) {
            output.close();
        }
        if (null != socket) {
            socket.close();
        }
    }
}
