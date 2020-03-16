package com.css.com.css.protocol.socket;

import com.css.com.css.AbstractRemoteInvoker;
import com.css.com.css.rpc.vo.RpcTranMessage;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Rpc调用监听器
 */
public class SocketRemoteInvoker extends AbstractRemoteInvoker implements Runnable{
    private Socket socket;
    private Logger loger = Logger.getLogger(SocketRemoteInvoker.class.getName());
    SocketRemoteInvoker(Socket socket) {
        this.socket = socket;
    }
    public void invoke() {
        ObjectInputStream input = null;
        ObjectOutputStream resStream = null;

        try {
            /***1、接收请求数据 ******/
            loger.info("开始启动任务处理线程！");
            input = new ObjectInputStream(socket.getInputStream());
            loger.info("开始读取请求信息！");
            final RpcTranMessage tranMsg = (RpcTranMessage) input.readObject();
            /***2、反射实现方法调用*****/
            final Object res =  invokeLocalMethod(tranMsg);
            /****3、将返回结果写回到流******/
            loger.info("调用结束，返回服务信息！");
            resStream  = new ObjectOutputStream(socket.getOutputStream());
            resStream.writeObject(res);
            resStream.flush();
        } catch (
                Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //(resStream, input);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void run() {
        invoke();
    }
}
