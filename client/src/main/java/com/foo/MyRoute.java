package com.foo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // onException(Exception.class).maximumRedeliveries(5).redeliveryDelay(1000);

        from("timer:foo?period=2000")
                .to("netty4-http:http://{{service:hello}}/hello?keepAlive=false&disconnect=true")
                .log("${body}");
    }
}



