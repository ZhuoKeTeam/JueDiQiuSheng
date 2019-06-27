package com.bro.adlib.statisticsAndLogsTypeTwo;

import java.lang.reflect.Proxy;

public class ProxyFactory {
    private static ProxyFactory sInstance = new ProxyFactory();

    private ProxyFactory() {}

    public static ProxyFactory getsInstance() {
        return sInstance;
    }

    /**
     * 创建动态代理类
     * @param serviceClass
     * @param impl
     * @param <T>
     * @return
     */
    public <T> T createProxyObj(Class<T> serviceClass, Object impl) {

        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new AdSALInvocationHandler(impl)
        );
    }
}
