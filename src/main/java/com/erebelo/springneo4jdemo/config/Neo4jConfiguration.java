package com.erebelo.springneo4jdemo.config;

import lombok.RequiredArgsConstructor;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Config;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
@Conditional(ProfilesConfiguration.class)
public class Neo4jConfiguration {

    private static final String CONNECTION_STRING_TEMPLATE = "bolt://%s:%s/%s";

    private final Environment env;

    @Value("${database.host:localhost}")
    private String dbHost;

    @Value("${database.port:7687}")
    private String dbPort;

    @Value("${database.name:}")
    private String dbName;

    @Value("${database.username:}")
    private String dbUsername;

    @Value("${database.ssl.cert.path:}") // cert.pem file path
    private String sslCertPath;

    // Heap memory security breach: do not use @Value annotation to get passwords
    private String getDbPassword() {
        return env.getProperty("database.password");
    }

    @Bean
    public Driver driver() {
        var uri = String.format(CONNECTION_STRING_TEMPLATE, dbHost, dbPort, dbName);
        return GraphDatabase.driver(uri, AuthTokens.basic(dbUsername, getDbPassword()), configureEncryptionOptions());
    }

    protected Config configureEncryptionOptions() {
        Config.ConfigBuilder configBuilder = Config.builder()
                .withConnectionTimeout(30, TimeUnit.SECONDS)
                .withTrustStrategy(Config.TrustStrategy.trustSystemCertificates());

        if (!ObjectUtils.isEmpty(sslCertPath)) {
            configBuilder.withEncryption()
                    .withTrustStrategy(Config.TrustStrategy.trustCustomCertificateSignedBy(new File(sslCertPath)));
        }

        return configBuilder.build();
    }
}
