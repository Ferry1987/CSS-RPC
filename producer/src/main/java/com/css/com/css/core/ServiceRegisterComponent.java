package com.css.com.css.core;

import com.css.com.css.core.annotation.Component;
import com.css.com.css.registry.RegistryCenter;
import com.css.com.css.rpc.annotation.ServiceContainer;
import com.css.com.css.utils.ServerContainerUtils;

import javax.management.MalformedObjectNameException;
import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.*;
import java.util.logging.*;

@Component
public class ServiceRegisterComponent {
    private Map<String, Object> beanMap = new HashMap<String, Object>();
    private Logger logger = Logger.getLogger("logger");
    private int serverPort = 1000;
    public boolean init(int port) {
        serverPort = port;
        try {
            logger.info("开始扫描接口类：");
            final List<File> fileList = new ArrayList<File>();
            final String servicePkg = "com.css.com.css.service";
            //1、扫描包下的所有类
            scanAllClassFromPackage(fileList, servicePkg);
            logger.info("扫描结束，扫描到：" + fileList.size() + "个服务。");
            //2、获取所有的服务ServiceContainer
            logger.info("开始将服务注册到远程服务中心！");
            int result = registerServiceContainer(fileList);
            logger.info("成功注册了：" + result + "个远程服务！");
            logger.info("成功注册了：" + RegistryCenter.getLocalSPINumber() + "个本地服务！");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    /**
     * 扫描包下边所有的类
     *
     * @param fileList 文件列表
     * @param pkg      基础包
     */
    private void scanAllClassFromPackage(final List<File> fileList, final String pkg) {
        final String pkgDir = pkg.replaceAll("\\.", "/");
        final URL url = getClass().getClassLoader().getResource(pkgDir);
        final File file = new File(url.getFile());
        /***过滤class文件****/
        final File fs[] = file.listFiles(new FileFilter() {
            public boolean accept(File file) {
                String fName = file.getName();
                if (file.isDirectory()) {
                    scanAllClassFromPackage(fileList, pkg + "." + fName);
                } else {
                    //判断文件后缀是否为.
                    if (fName.endsWith(".class")) {
                        return true;
                    }
                }
                return false;
            }
        });
        fileList.addAll(Arrays.asList(fs));
    }

    private int registerServiceContainer(List<File> classFileList) throws MalformedObjectNameException {
        final List<Map<String, Object>> localServerList = getLocalServerList();
        final Map<String, List<Map<String, Object>>> serviceProducer = new HashMap<String, List<Map<String, Object>>>();
        for (File f : classFileList) {
            String fName = f.getName();
            //去除.class以后的文件名
            fName = fName.substring(0, fName.lastIndexOf("."));
            //将名字的第一个字母转为小写(用它作为key存储map)
            String key = String.valueOf(fName.charAt(0)).toLowerCase() + fName.substring(1);
            //构建一个类全名(包名.类名)
            final int pkgFullDirLength = ServiceRegisterComponent.class.getResource("/").getPath().length();
            final String pkgFullDir = f.getParentFile().getPath();
            final String pkgDir = pkgFullDir.substring(pkgFullDirLength - 1);
            String pkg = pkgDir.replaceAll("\\\\", ".");
            String pkgCls = pkg + "." + fName;
            logger.info("full class path is :" + pkgCls);
            try {
                //反射构建对象
                Class<?> c = Class.forName(pkgCls);
                //判定类上是否有注解isAnnotationPresent()
                if (c.isAnnotationPresent(ServiceContainer.class)) {
                    final Class<?>[] interClass = c.getInterfaces();
                    for (Class inter : interClass) {
                        final Map<String, Class<?>> localSPI = new HashMap<String, Class<?>>();
                        localSPI.put(inter.getSimpleName(), c);
                        RegistryCenter.addLocalSPI(localSPI);
                        serviceProducer.put(inter.getSimpleName(), localServerList);
                    }
                    //Object obj = c.newInstance();
                    //将对象放到map容器
                    //beanMap.put(key, obj);
                }

            } catch (Exception e) {
                e.printStackTrace();
                continue;

            }
        }
        RegistryCenter.registerService(serviceProducer);
        return serviceProducer.size();
    }

    /***
     * 获取Bean对象
     * @param key
     * @return
     */
    public Object getBean(String key) {
        return beanMap.get(key);
    }

    public List<Map<String, Object>> getLocalServerList() throws MalformedObjectNameException {
        final String host = ServerContainerUtils.getLocalIP();
        final List<Map<String, Object>> serverList = new ArrayList<Map<String, Object>>();
        final Map<String, Object> serverMap = new HashMap<String, Object>();
        serverMap.put("ip", host);
        serverMap.put("port", serverPort);
        serverList.add(serverMap);
        return serverList;
    }

    /***
     * 关闭
     */
    public void close() {
        beanMap.clear();
        beanMap = null;
    }
}