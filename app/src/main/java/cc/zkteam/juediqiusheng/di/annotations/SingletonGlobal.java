package cc.zkteam.juediqiusheng.di.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 全局单例注解，参考：
 * http://blog.csdn.net/xx326664162/article/details/67640509
 * 第五点： 自定义@Scope
 *
 * 不同的@Scope ，定义单例对象的生命周期，也就是使用范围。在写代码时，程序员更加清楚什么时候创建Component，什么时候结束。
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface SingletonGlobal {}