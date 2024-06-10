package com.erebelo.springneo4jdemo.service.impl;

import com.erebelo.springneo4jdemo.domain.relationship.FollowRelationship;
import com.erebelo.springneo4jdemo.domain.request.UserRequest;
import com.erebelo.springneo4jdemo.domain.response.UserLazyResponse;
import com.erebelo.springneo4jdemo.domain.response.UserResponse;
import com.erebelo.springneo4jdemo.mapper.UserMapper;
import com.erebelo.springneo4jdemo.repository.UserRepository;
import com.erebelo.springneo4jdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
//    @Transactional(readOnly = true)
    public List<UserLazyResponse> findAll() {
        var nodeList = repository.findAll();
        return mapper.lazyNodeListToResponseList(nodeList);
    }

    @Override
//    @Transactional(readOnly = true)
    public UserResponse findById(String id) {
        var node = repository.findById(id).orElse(null);
        return mapper.nodeToResponse(node);
    }

    @Override
//    @Transactional
    public UserLazyResponse insert(UserRequest request) {
        var node = mapper.requestToNode(request);
        node = repository.save(node);

        return mapper.lazyNodeToResponse(node);
    }

    @Override
//    @Transactional
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
//    @Transactional
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
//    @Transactional
    public void followUser(String id1, String id2) {
        var user1 = repository.findById(id1).orElse(null);
        var user2 = repository.findById(id2).orElse(null);

        if (user1 != null && user2 != null) {
            var followingRelationship = new FollowRelationship();
            followingRelationship.setSinceAt(LocalDateTime.now());
            followingRelationship.setUser(user2);
            user1.getFollowing().add(followingRelationship);

            var followerRelationship = new FollowRelationship();
            followerRelationship.setSinceAt(LocalDateTime.now());
            followerRelationship.setUser(user1);
            user2.getFollowers().add(followerRelationship);

            repository.save(user1);
            repository.save(user2);
        }
    }

    @Override
//    @Transactional
    public void unfollowUser(String id1, String id2) {
        var user1 = repository.findById(id1).orElse(null);
        var user2 = repository.findById(id2).orElse(null);

        if (user1 != null && user2 != null) {
            user1.getFollowing().removeIf(rel -> rel.getUser().getId().equals(id2));
            user2.getFollowers().removeIf(rel -> rel.getUser().getId().equals(id1));

            repository.save(user1);
            repository.save(user2);
        }
    }
}
