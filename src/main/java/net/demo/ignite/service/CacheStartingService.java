package net.demo.ignite.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;

/**
 * Ignite cluster could be initialized and started with .xml configuration
 */
@Slf4j
//@Service
@DependsOn("igniteCacheMagager")
@RequiredArgsConstructor
public class CacheStartingService {
    @Value("classpath:ignite-config.xml")
    private Resource igniteXml;

    @SneakyThrows
    @PostConstruct
    @Async
    public void initCaches() {
        try (InputStream igniteConfig = igniteXml.getInputStream()) {
            if (igniteConfig == null) {
                log.error("No proper ignite XML config in classpath");
                return;
            }

            Ignition.start(igniteConfig);
        }
    }
}
