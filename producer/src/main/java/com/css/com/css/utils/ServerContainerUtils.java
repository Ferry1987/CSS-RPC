package com.css.com.css.utils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;
import javax.naming.Context;
import javax.naming.InitialContext;


public class ServerContainerUtils {


    /**
     * @return
     * @throws MalformedObjectNameException 获取当前tomcat机器的端口号
     */
    public static String getLocalPort() throws MalformedObjectNameException {
        MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
        Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"),
                Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
        String port = objectNames.iterator().next().getKeyProperty("port");
        return port;
    }

    /**
     * 获取weblogic 端口号
     *
     * @return
     */
    public static String getWeblogicPort() {
        try {
            Context ctx = new InitialContext();
            MBeanServer tMBeanServer = (MBeanServer) ctx.lookup("java:comp/env/jmx/runtime");
            ObjectName tObjectName = new ObjectName("com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean");
            ObjectName serverrt = (ObjectName) tMBeanServer.getAttribute(tObjectName, "ServerRuntime");
            String port = String.valueOf(tMBeanServer.getAttribute(serverrt, "ListenPort"));
            String listenAddr = (String) tMBeanServer.getAttribute(serverrt, "ListenAddress");
            String[] tempAddr = listenAddr.split("/");
            if (tempAddr.length == 1) {
                listenAddr = tempAddr[0];
            } else if (tempAddr[tempAddr.length - 1].trim().length() != 0) {
                listenAddr = tempAddr[tempAddr.length - 1];
            } else if (tempAddr.length > 2) {
                listenAddr = tempAddr[tempAddr.length - 2];
            }
            return port;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @return 获取当前机器的IP
     */
    public static String getLocalIP() {
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (Exception e) {
            e.printStackTrace();
        }


        byte[] ipAddr = addr.getAddress();
        String ipAddrStr = "";
        for (int i = 0; i < ipAddr.length; i++) {
            if (i > 0) {
                ipAddrStr += ".";
            }
            ipAddrStr += ipAddr[i] & 0xFF;
        }
        return ipAddrStr;
    }
}