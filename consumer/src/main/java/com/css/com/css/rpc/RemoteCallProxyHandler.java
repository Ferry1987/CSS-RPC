package com.css.com.css.rpc;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.net.Socket;

public interface RemoteCallProxyHandler extends InvocationHandler {
    public void closeConnection(OutputStream output, InputStream input, Socket socket);
    public void setServiceName(String serviceName);
}
