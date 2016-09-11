package com.foo;

import javax.inject.Singleton;

import org.apache.camel.util.InetAddressUtil;

@Singleton
public class HelloBean {

    public String sayHello() throws Exception {
        return "Swarm says hello from "
                + InetAddressUtil.getLocalHostName();
    }
}
