package net.demo.ignite.config;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.cache.spring.SpringCacheManager;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@EnableCaching
@RequiredArgsConstructor
@Order(1)
@Slf4j
@EnableConfigurationProperties
public class IgniteCacheConfig {

    private final IgniteConfProperties igniteProperties;

    @SneakyThrows
    @Bean("igniteCacheMagager")
    public CacheManager cacheManager() {
        SpringCacheManager manager = new SpringCacheManager();
        manager.setConfiguration(getIgniteConfiguration());
        manager.setDynamicCacheConfiguration(getDefaultCacheConfiguration());
        return manager;
    }

    @SneakyThrows
    private IgniteConfiguration getIgniteConfiguration() {
        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setIgniteInstanceName(igniteProperties.getInstanceName());
        cfg.setPeerClassLoadingEnabled(igniteProperties.getPeerClassLoadingEnabled());
        cfg.setDiscoverySpi(getTcpDiscoverySpi());
        cfg.setClientMode(false);
        cfg.setNetworkTimeout(igniteProperties.getNetworkTimeout());
        cfg.setFailureDetectionTimeout(igniteProperties.getFailureTimeout());
        return cfg;
    }

    private CacheConfiguration getDefaultCacheConfiguration() {
        CacheConfiguration conf = new CacheConfiguration();
        conf.setName("defaultCache");
        return conf;
    }

    private TcpDiscoverySpi getTcpDiscoverySpi() {
        log.info("Use {}:{} to enter cluster", igniteProperties.getAddresses(), igniteProperties.getLocalPort());
        TcpDiscoverySpi tsp = new TcpDiscoverySpi();
        tsp.setLocalPort(igniteProperties.getLocalPort());
        TcpDiscoveryVmIpFinder ipf = new TcpDiscoveryVmIpFinder(true);
        ipf.setAddresses(igniteProperties.getAddresses());
        tsp.setIpFinder(ipf);
        return tsp;
    }

}
