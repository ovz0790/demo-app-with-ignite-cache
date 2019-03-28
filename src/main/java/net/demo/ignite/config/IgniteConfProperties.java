package net.demo.ignite.config;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Properties for ignite cache config.
 */
@Component
@ConfigurationProperties("cache.ignite")
@Accessors(chain = true)
@Getter
@Setter
public class IgniteConfProperties {

    private String instanceName;

    private Boolean peerClassLoadingEnabled;

    private List<String> addresses;

    private Integer localPort;

    private Long failureTimeout;

    private Long networkTimeout;
}
