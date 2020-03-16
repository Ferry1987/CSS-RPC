package com.css.com.css.registry;

import java.io.*;
import java.util.*;
import java.util.logging.*;

public class RegistryCenter extends Observable {
    private static Logger logger = Logger.getLogger("");
    private static String registerCenterDictory = "register";
    private static String registerCenterFileName = "register.txt";
    private static String registFileDictory = registerCenterDictory.concat("//").concat(registerCenterFileName);
    /**
     * 本地服务中心，保存本地服务与接口对照关系
     */
    final static Map<String, Class<?>> localSPICenter = new HashMap<String, Class<?>>();
    /**
     * 远程服务注册重新，保存服务与生产者对照关系
     */
    static Map<String, List<Map<String, Object>>> remoteServiceRegistCenter = new HashMap<String, List<Map<String, Object>>>();

    /****增加服务映射到服务中心****/
    public static void addLocalSPI(Map<String, Class<?>> mapping) {
        localSPICenter.putAll(mapping);
    }

    /**
     * 获取实现类名称
     *
     * @param inter
     * @return
     */
    public static Class<?> getImplementsClassName(String inter) {
        return localSPICenter.get(inter);
    }

    /**
     * 服务远程注册
     *
     * @param service 服务名称
     */
    public static void registerService(Map<String, List<Map<String, Object>>> service) {
        remoteServiceRegistCenter = service;
        persistenceServiceProviderContext(remoteServiceRegistCenter);

    }

    /**
     * 服务远程注册
     *
     * @param service 服务名称
     * @param ip      服务者IP
     * @param port    端口
     * @throws IOException
     */
    public static void registerService(String service, String ip, int port) throws IOException {
        final List<Map<String, Object>> serviceProducerList = new ArrayList<Map<String, Object>>();
        final Map<String, Object> producerMap = new HashMap<String, Object>();
        producerMap.put("ip", ip);
        producerMap.put("port", port);
        serviceProducerList.add(producerMap);
        final String absoluteReigsterPath = RegistryCenter.class.getResource("/") + registFileDictory;
        final File registerFile = new File(absoluteReigsterPath);
        /**
         * 检查文件是否存在，不存在这创建文件，写入内容，存在则追加后重新写入
         */
        if (!registerFile.exists()) {
            remoteServiceRegistCenter.put(service, serviceProducerList);
        } else {
            if (remoteServiceRegistCenter.containsKey(service)) {
                remoteServiceRegistCenter.get(service).add(producerMap);
            } else {
                remoteServiceRegistCenter.put(service, serviceProducerList);
            }
        }
        persistenceServiceProviderContext(remoteServiceRegistCenter);

    }

    /**
     * 将服务注册信息持久化到本地文件
     *
     * @param obj 需要持久化的对象
     */
    public static void persistenceServiceProviderContext(Object obj) {
        logger.info("系统开始持久化服务列表");
        String absoluteReigsterPath = RegistryCenter.class.getResource("/").getPath() + registFileDictory;
        logger.info("absoluteReigsterPath:" + absoluteReigsterPath);
        final File registerFile = new File(absoluteReigsterPath);
        try {
            if (!registerFile.exists()) {
                final File parentFile = registerFile.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                registerFile.createNewFile();
            }
            final FileOutputStream outputStream = new FileOutputStream(registerFile);
            final ObjectOutputStream objectOutStream = new ObjectOutputStream(outputStream);
            objectOutStream.writeObject(obj);
            objectOutStream.flush();
            if (outputStream != null) {
                outputStream.close();
            }
            if (objectOutStream != null) {
                objectOutStream.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static int getLocalSPINumber() {
        return localSPICenter.size();
    }
}
