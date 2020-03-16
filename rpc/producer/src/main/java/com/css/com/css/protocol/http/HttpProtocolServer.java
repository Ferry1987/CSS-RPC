package com.css.com.css.protocol.http;

import com.css.com.css.protocol.AbstractProtocolServer;
import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;
import java.util.logging.*;
public class HttpProtocolServer extends AbstractProtocolServer {
    final Logger logger = Logger.getLogger("");
    public void export(int port) {
        String defaltHost = "localhost";
        /***创建tomcat对象 ***/
        Tomcat tomcat = new Tomcat();
        /***获取服务器****/
        Server server = tomcat.getServer();
        /***获取服务****/
        Service service = server.findService("Tomcat");
        Connector connector = new Connector();
        connector.setPort(port);

        /***创建引擎****/
        Engine engine = new StandardEngine();
        engine.setDefaultHost(defaltHost);

        Host host = new StandardHost();
        host.setName(defaltHost);

        String contextPath = "";
        Context context = new StandardContext();
        context.setPath(contextPath);
        context.addLifecycleListener(new Tomcat.FixContextListener());

        host.addChild(context);
        engine.addChild(host);
        service.setContainer(engine);
        service.addConnector(connector);

        /****添加接收器*****/
        tomcat.addServlet(contextPath,"dispatcher",new DispatcherServlet());
        context.addServletMappingDecoded("/*","dispatcher");
        /***
         * 启动Tomcat
         */
        try{
            logger.info("开始启动tomcat服务！");
            tomcat.start();
            logger.info("tomcat启动成功！");
            logger.info("开始加载系统组件");
            initServerComponents(port);
            tomcat.getServer().await();
            logger.info("tomcat关闭！");
        }catch (Exception ex){
            ex.toString();

        }
    }
}
