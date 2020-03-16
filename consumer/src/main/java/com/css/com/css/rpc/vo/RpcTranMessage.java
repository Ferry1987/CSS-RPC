package com.css.com.css.rpc.vo;

import java.io.Serializable;

public class RpcTranMessage implements Serializable {
    private String className;
    private String methodName;

    public Class<?>[] getMethodTypes() {
        return methodTypes;
    }

    public void setMethodTypes(Class<?>[] methodTypes) {
        this.methodTypes = methodTypes;
    }

    private Class<?>[] methodTypes;
    private Object[] methodParams;
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public void setMethodParams(Object[] methodParams) {
        this.methodParams = methodParams;
    }

}
