package com.erebelo.springneo4jdemo.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BusinessConstant {

    public static final String HEALTH_CHECK_PATH = "/health-check";
    public static final String USERS_PATH = "/users";
    public static final String USERS_FOLLOW_PATH = "/{fromId}/follow/{toId}";
    public static final String USERS_UNFOLLOW_PATH = "/{fromId}/unfollow/{toId}";

}
