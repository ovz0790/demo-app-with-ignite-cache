package net.demo.ignite;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.apache.ignite.IgniteSystemProperties.IGNITE_NO_ASCII;
import static org.apache.ignite.IgniteSystemProperties.IGNITE_QUIET;

@Slf4j
@SpringBootApplication
@EnableAutoConfiguration
public class AppRunner {

    public static void main(String[] args) {
        System.setProperty(IGNITE_NO_ASCII, "true");
        System.setProperty(IGNITE_QUIET, "false");
        log.info("Version: {}", AppRunner.class.getPackage().getImplementationVersion());
        SpringApplication.run(AppRunner.class, args);
    }
}
