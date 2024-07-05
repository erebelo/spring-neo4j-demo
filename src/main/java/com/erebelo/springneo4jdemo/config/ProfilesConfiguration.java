package com.erebelo.springneo4jdemo.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.lang.NonNull;

public class ProfilesConfiguration implements Condition {

    @Override
    public boolean matches(ConditionContext context, @NonNull AnnotatedTypeMetadata metadata) {
        String[] activeProfiles = context.getEnvironment().getActiveProfiles();

        for (String profile : activeProfiles) {
            if ("local".equals(profile) || "test".equals(profile)) {
                return false;
            }
        }

        return true;
    }
}
