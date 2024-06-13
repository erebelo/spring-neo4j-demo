package com.erebelo.springneo4jdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@Configuration
@Profile("local")
public class Neo4jLocalConfiguration extends Neo4jConfiguration {

    public Neo4jLocalConfiguration(Environment env) {
        super(env);
    }
}
