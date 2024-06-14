package com.erebelo.springneo4jdemo.config;

import org.neo4j.driver.Config;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import java.util.concurrent.TimeUnit;

@Configuration
@Profile("local")
public class Neo4jLocalConfiguration extends Neo4jConfiguration {

    public Neo4jLocalConfiguration(Environment env) {
        super(env);
    }

    @Override
    protected Config configureEncryptionOptions() {
        return Config.builder()
                .withConnectionTimeout(30, TimeUnit.SECONDS)
                .withTrustStrategy(Config.TrustStrategy.trustSystemCertificates())
                .build();
    }
}
