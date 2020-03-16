package com.css.com.css.registry;

import java.io.*;
import java.util.*;
import java.util.logging.*;

public class RegistryCenter extends Observable {
    private static Logger logger = Logger.getLogger("");
    private static String registerCenterDictory = "\\register";
    private static String registerCenterFileName = "register.txt";
    private static String registFileDictory = registerCenterDictory.concat("\\").concat(registerCenterFileName);
    /**
     * 远程服务注册重新，保存服务与生产者对照关系
     */
    static Map<String, List<Map<String, Object>>> remoteServiceRegistCenter = new HashMap<String, List<Map<String, Object>>>();

    /**
     * 获取实现类名称
     *
     * @param serviceName 服务名称
     * @return
     */
    public static List<Map<String, Object>> getServiceProviderContext(String serviceName) {
        try {
            if (remoteServiceRegistCenter.isEmpty()) {
                loadRemoteServiceProviderContext();
            }
            List<Map<String, Object>> providerList = remoteServiceRegistCenter.get(serviceName);
            if (null == providerList || providerList.isEmpty()) {
                loadRemoteServiceProviderContext();
                return remoteServiceRegistCenter.get(serviceName);
            } else {
                return providerList;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void loadRemoteServiceProviderContext() throws IOException, ClassNotFoundException {
        logger.info("首次获取服务提供者消息，从本地存储文件中加载！");
        /***
         * 生产者生产本地文件路径
         */
        final String absoluteReigsterPath = "G:\\WORKSPACE\\MCRO_WORDSPACE\\SWORD_SJFWPT\\rpc\\producer\\target\\classes" + registFileDictory;
        logger.info("注册中心文件路径为：" + absoluteReigsterPath);
        final File registerFile = new File(absoluteReigsterPath);
        /**
         * 检查文件是否存在，不存在这创建文件，写入内容，存在则追加后重新写入
         */
        if (registerFile.exists()) {
            final InputStream inputStream = new FileInputStream(registerFile);
            final ObjectInputStream objectintStream = new ObjectInputStream(inputStream);
            remoteServiceRegistCenter = (Map<String, List<Map<String, Object>>>) objectintStream.readObject();
        }
        logger.info("服务生产者列表加载完成，共加载" + remoteServiceRegistCenter.size() + "条服务注册记录！");

    }
}
