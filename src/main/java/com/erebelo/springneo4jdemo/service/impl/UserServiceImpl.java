package com.erebelo.springneo4jdemo.service.impl;


import com.erebelo.springneo4jdemo.domain.relationship.FollowRelationship;
import com.erebelo.springneo4jdemo.domain.request.UserRequest;
import com.erebelo.springneo4jdemo.domain.response.UserLazyResponse;
import com.erebelo.springneo4jdemo.domain.response.UserResponse;
import com.erebelo.springneo4jdemo.mapper.UserMapper;
import com.erebelo.springneo4jdemo.repository.FollowRepository;
import com.erebelo.springneo4jdemo.repository.UserRepository;
import com.erebelo.springneo4jdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final UserMapper mapper;

    @Override
//    @Transactional(readOnly = true)
    public List<UserLazyResponse> findAll() {
        var nodeList = userRepository.findAll();
        return mapper.lazyNodeListToResponseList(nodeList);
    }

    @Override
//    @Transactional(readOnly = true)
    public UserResponse findById(String id) {
        var node = userRepository.findById(id).orElse(null);
        return mapper.nodeToResponse(node);
    }

    @Override
//    @Transactional
    public UserLazyResponse insert(UserRequest request) {
        var node = mapper.requestToNode(request);
        node = userRepository.save(node);

        return mapper.lazyNodeToResponse(node);
    }

    @Override
//    @Transactional
    public UserLazyResponse update(String id, UserRequest request) {
        var node = userRepository.findById(id).orElse(null);

        if (node != null) {
            node.setUsername(request.getUsername());
            node.setName(request.getName());

            node = userRepository.save(node);
        }

        return mapper.lazyNodeToResponse(node);
    }

    @Override
//    @Transactional
    public void delete(String id) {
        userRepository.deleteById(id);
    }

    @Override
//    @Transactional
    public void followUser(String id1, String id2) {
        var user1 = userRepository.findById(id1).orElse(null);
        var user2 = userRepository.findById(id2).orElse(null);

        if (user1 != null && user2 != null) {
            var relationship = new FollowRelationship();
            relationship.setUser(user2);
            relationship.setSinceAt(LocalDateTime.now());

            user1.getFollowing().add(relationship);
            userRepository.save(user1);

            // this is the only way possible to create the bidirectional relationship, however, it created a new separated node named
            // FollowRelationship
            followRepository.save(relationship);


//        var user1 = userRepository.findById(id1).orElse(null);
//        var user2 = userRepository.findById(id2).orElse(null);
//
//        if (user1 != null && user2 != null) {
//            var followRelationship = new FollowRelationship();
//            followRelationship.setSinceAt(LocalDateTime.now());
//
//            followRelationship.setUser(user2);
//            user1.getFollowing().add(followRelationship);
//
//            followRelationship = new FollowRelationship();
//            followRelationship.setSinceAt(LocalDateTime.now());
//
//            followRelationship.setUser(user1);
//            user2.getFollowers().add(followRelationship);
//
//            userRepository.save(user1);
//            userRepository.save(user2);
        }
    }

    @Override
//    @Transactional
    public void unfollowUser(String id1, String id2) {
        var user1 = userRepository.findById(id1).orElse(null);
        if (user1 != null) {
            user1.getFollowing().removeIf(rel -> rel.getUser().getId().equals(id2));
            userRepository.save(user1);
        }
    }
}
