package com.css.com.css.protocol.http;

import com.css.com.css.AbstractRemoteInvoker;
import com.css.com.css.rpc.invoker.RpcCallHandler;
import com.css.com.css.rpc.vo.RpcTranMessage;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class HttpRemoteInvoker extends AbstractRemoteInvoker {
    final Logger logger = Logger.getLogger(HttpRemoteInvoker.class.getName());
    HttpServletRequest req;
    HttpServletResponse resp;

    public HttpRemoteInvoker(HttpServletRequest req, HttpServletResponse resp) {
        this.req = req;
        this.resp = resp;
    }

    public void invoke() {
        ObjectInputStream inputStream = null;
        ObjectOutputStream resStream = null;

        try {
            /***1、接收请求数据 ******/
            logger.info("开始启动任务处理线程！");
            inputStream = new ObjectInputStream(req.getInputStream());
            logger.info("开始读取请求信息！");
            final RpcTranMessage tranMsg = (RpcTranMessage) inputStream.readObject();
            /***2、反射实现方法调用*****/
            final Object res = invokeLocalMethod(tranMsg);
            /****3、将返回结果写回到流******/
            logger.info("调用结束，返回服务信息！");
            resStream = new ObjectOutputStream(resp.getOutputStream());
            resStream.writeObject(res);
            resStream.flush();
        } catch (
                Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //closeConnection(resStream, inputStream);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}
