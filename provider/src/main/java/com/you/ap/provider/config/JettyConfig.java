package com.you.ap.provider.config;

import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 对jetty精细化配置
 *
 * @version $Id$
 */
@Configuration
public class JettyConfig {

    private static final Logger logger = LoggerFactory.getLogger(JettyConfig.class);

    @Bean
    public EmbeddedServletContainerFactory servletContainerFactory(
            @Value("${jetty.threadPool.maxThreads}") final String maxThreads,
            @Value("${jetty.threadPool.minThreads}") final String minThreads,
            @Value("${jetty.threadPool.idleTimeout}") final String idleTimeout) {
        JettyEmbeddedServletContainerFactory jettyEmbeddedServletContainerFactory = new JettyEmbeddedServletContainerFactory();
        jettyEmbeddedServletContainerFactory.addServerCustomizers(server -> {
            final QueuedThreadPool threadPool = server.getBean(QueuedThreadPool.class);
            threadPool.setMaxThreads(Integer.valueOf(maxThreads));
            threadPool.setMinThreads(Integer.valueOf(minThreads));
            threadPool.setIdleTimeout(Integer.valueOf(idleTimeout));
            server.setAttribute("org.eclipse.jetty.server.Request.maxFormContentSize",-1);
        });
        jettyEmbeddedServletContainerFactory.setPort(9090);

        return jettyEmbeddedServletContainerFactory;
    }

}
