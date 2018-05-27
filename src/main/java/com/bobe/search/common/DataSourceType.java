package com.bobe.search.common;

import com.sun.org.apache.xerces.internal.dom.DeferredElementNSImpl;

import java.lang.annotation.*;

/**数据源动态切换注解
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
public @interface DataSourceType {
	String value() default "Slave";
}
