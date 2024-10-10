package com.erebelo.springneo4jdemo.service;

import com.erebelo.springneo4jdemo.domain.request.UserRequest;
import com.erebelo.springneo4jdemo.domain.response.UserLazyResponse;
import com.erebelo.springneo4jdemo.domain.response.UserResponse;
import java.util.List;

public interface UserService {

    List<UserLazyResponse> findAll();

    UserResponse findById(String id);

    UserLazyResponse insert(UserRequest request);

    UserLazyResponse update(String id, UserRequest request);

    void delete(String id);

    void followUser(String id1, String id2);

    void unfollowUser(String id1, String id2);

}
