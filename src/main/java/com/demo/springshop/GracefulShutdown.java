package com.demo.springshop;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GracefulShutdown implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {

    private static final int TIMEOUT = 30;
    private volatile Connector connector;

    @Override
    public void customize(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        this.connector.pause();
        Executor executor = this.connector.getProtocolHandler().getExecutor();

        if(executor instanceof ThreadPoolExecutor) {
            try {
                ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
                threadPoolExecutor.shutdown();

                if(!threadPoolExecutor.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
                    threadPoolExecutor.shutdownNow();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
