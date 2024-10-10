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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    @Transactional(value = "transactionManager", readOnly = true)
    public List<UserLazyResponse> findAll() {
        var nodeList = repository.findAll();
        return mapper.lazyNodeListToResponseList(nodeList);
    }

    @Override
    @Transactional(value = "transactionManager", readOnly = true)
    public UserResponse findById(String id) {
        var node = repository.findById(id).orElse(null);
        return mapper.nodeToResponse(node);
    }

    @Override
    @Transactional("transactionManager")
    public UserLazyResponse insert(UserRequest request) {
        var node = mapper.requestToNode(request);
        node = repository.save(node);

        return mapper.lazyNodeToResponse(node);
    }

    @Override
    @Transactional("transactionManager")
    public UserLazyResponse update(String id, UserRequest request) {
        var node = repository.findById(id).orElse(null);

        if (node != null) {
            node.setUsername(request.getUsername());
            node.setName(request.getName());

            node = repository.save(node);
        }

        return mapper.lazyNodeToResponse(node);
    }

    @Override
    @Transactional("transactionManager")
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional("transactionManager")
    public void followUser(String id1, String id2) {
        var user1 = repository.findById(id1).orElse(null);
        var user2 = repository.findById(id2).orElse(null);

        if (user1 != null && user2 != null && !isFollowing(user1, id2)) {
            var localDate = LocalDateTime.now();

            var followingRelationship = new FollowRelationship();
            followingRelationship.setSinceAt(localDate);
            followingRelationship.setUser(user2);
            user1.getFollowing().add(followingRelationship);

            var followerRelationship = new FollowRelationship();
            followerRelationship.setSinceAt(localDate);
            followerRelationship.setUser(user1);
            user2.getFollowers().add(followerRelationship);

            repository.save(user1);
            repository.save(user2);

        }
    }

    @Override
    @Transactional("transactionManager")
    public void unfollowUser(String id1, String id2) {
        var user1 = repository.findById(id1).orElse(null);
        var user2 = repository.findById(id2).orElse(null);

        if (user1 != null && user2 != null && isFollowing(user1, id2)) {
            user1.getFollowing().removeIf(rel -> rel.getUser().getId().equals(id2));
            user2.getFollowers().removeIf(rel -> rel.getUser().getId().equals(id1));

            repository.save(user1);
            repository.save(user2);

        }
    }

    private boolean isFollowing(UserNode user, String userId2) {
        return user.getFollowing().stream().anyMatch(rel -> rel.getUser().getId().equals(userId2));
    }
}
