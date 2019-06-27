package com.bro.adlib.statisticsAndLogsTypeTwo;

import com.blankj.utilcode.util.LogUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class AdSALInvocationHandler implements InvocationHandler {
    private Object proxyObject;

    public AdSALInvocationHandler(Object proxyObject) {
        this.proxyObject = proxyObject;
    }

    /**
     * 动态代理拦截
     * @param o
     * @param method
     * @param arguments
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object o, Method method, Object[] arguments) throws Throwable {
        System.out.println("invoke -> " + method.getName());
        System.out.println("invoke -> " + method.getDeclaringClass().getSimpleName());
        String key = method.getDeclaringClass().getSimpleName() + "->" + method.getName();
        String event = StatisticsEventTable.getsInstance().getEventByKey(key);
        if (event != null) {
            System.out.println(event);
            //  TODO 统计
        }
//        LogUtils.iTag("AD", key);
        //  TODO 日志 这里写的时候在 ExampleUnitTest 写了 Test 所以使用了简单的 Java 原始打印
        System.out.println("AD -> " + key);
        return method.invoke(proxyObject, arguments);
    }
}
