package com.erebelo.springneo4jdemo.service.impl;

import com.erebelo.springneo4jdemo.domain.node.UserNode;
import com.erebelo.springneo4jdemo.domain.relationship.FollowRelationship;
import com.erebelo.springneo4jdemo.domain.request.UserRequest;
import com.erebelo.springneo4jdemo.domain.response.UserLazyResponse;
import com.erebelo.springneo4jdemo.domain.response.UserResponse;
import com.erebelo.springneo4jdemo.mapper.UserMapper;
import com.erebelo.springneo4jdemo.repository.UserRepository;
import com.erebelo.springneo4jdemo.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    @Transactional(value = "transactionManager", readOnly = true)
    public List<UserLazyResponse> findAll() {
        log.info("Fetching all users");
        var nodeList = repository.findAll();

        log.info("Users successfully retrieved: {}", nodeList);
        return mapper.lazyNodeListToResponseList(nodeList);
    }

    @Override
    @Transactional(value = "transactionManager", readOnly = true)
    public UserResponse findById(String id) {
        log.info("Fetching user by id: {}", id);
        var node = repository.findById(id).orElse(null);

        log.info("User {} successfully retrieved", node);
        return mapper.nodeToResponse(node);
    }

    @Override
    @Transactional("transactionManager")
    public UserLazyResponse insert(UserRequest request) {
        log.info("Creating user");
        var node = mapper.requestToNode(request);
        node = repository.save(node);

        log.info("User created successfully: {}", node);
        return mapper.lazyNodeToResponse(node);
    }

    @Override
    @Transactional("transactionManager")
    public UserLazyResponse update(String id, UserRequest request) {
        log.info("Updating user with id: {}", id);
        var node = repository.findById(id).orElse(null);

        if (node != null) {
            node.setUsername(request.getUsername());
            node.setName(request.getName());

            node = repository.save(node);
        }

        log.info("User updated successfully: {}", node);
        return mapper.lazyNodeToResponse(node);
    }

    @Override
    @Transactional("transactionManager")
    public void delete(String id) {
        log.info("Deleting user with id: {}", id);

        repository.deleteById(id);
        log.info("User deleted successfully");
    }

    @Override
    @Transactional("transactionManager")
    public void followUser(String fromId, String toId) {
        log.info("User id: {} following user id: {}", fromId, toId);

        var fromUser = repository.findById(fromId).orElse(null);
        var toUser = repository.findById(toId).orElse(null);

        if (fromUser != null && toUser != null && !isFollowing(fromUser, toId)) {
            var localDate = LocalDateTime.now();

            var followingRelationship = new FollowRelationship();
            followingRelationship.setSinceAt(localDate);
            followingRelationship.setUser(toUser);
            fromUser.getFollowing().add(followingRelationship);

            var followerRelationship = new FollowRelationship();
            followerRelationship.setSinceAt(localDate);
            followerRelationship.setUser(fromUser);
            toUser.getFollowers().add(followerRelationship);

            repository.save(fromUser);
            repository.save(toUser);
            log.info("User id: {} followed successfully user id: {}", fromId, toId);
        } else {
            log.info("User id: {} has already followed user id: {}", fromId, toId);
        }
    }

    @Override
    @Transactional("transactionManager")
    public void unfollowUser(String fromId, String toId) {
        log.info("User id: {} unfollowing user id: {}", fromId, toId);

        var fromUser = repository.findById(fromId).orElse(null);
        var toUser = repository.findById(toId).orElse(null);

        if (fromUser != null && toUser != null && isFollowing(fromUser, toId)) {
            fromUser.getFollowing().removeIf(rel -> rel.getUser().getId().equals(toId));
            toUser.getFollowers().removeIf(rel -> rel.getUser().getId().equals(fromId));

            repository.save(fromUser);
            repository.save(toUser);
            log.info("User id: {} successfully unfollowed user id: {}", fromId, toId);
        } else {
            log.info("User id: {} has already unfollowed user id: {}", fromId, toId);
        }
    }

    private boolean isFollowing(UserNode fromUser, String toId) {
        return fromUser.getFollowing().stream().anyMatch(rel -> rel.getUser().getId().equals(toId));
    }
}
