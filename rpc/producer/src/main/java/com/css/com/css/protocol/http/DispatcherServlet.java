package com.css.com.css.protocol.http;

import com.css.com.css.rpc.RemoteInvoker;
import com.css.com.css.rpc.invoker.RpcCallHandler;
import com.css.com.css.rpc.vo.RpcTranMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatcherServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final RemoteInvoker invoker = new HttpRemoteInvoker(request, response);
        invoker.invoke();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
