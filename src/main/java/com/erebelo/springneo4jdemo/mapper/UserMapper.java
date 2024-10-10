package com.erebelo.springneo4jdemo.mapper;

import static org.mapstruct.ReportingPolicy.WARN;

import com.erebelo.springneo4jdemo.domain.node.TweetNode;
import com.erebelo.springneo4jdemo.domain.node.UserNode;
import com.erebelo.springneo4jdemo.domain.relationship.FollowRelationship;
import com.erebelo.springneo4jdemo.domain.relationship.PostRelationship;
import com.erebelo.springneo4jdemo.domain.request.UserRequest;
import com.erebelo.springneo4jdemo.domain.response.FollowRelationshipResponse;
import com.erebelo.springneo4jdemo.domain.response.PostRelationshipResponse;
import com.erebelo.springneo4jdemo.domain.response.TweetLazyResponse;
import com.erebelo.springneo4jdemo.domain.response.UserLazyResponse;
import com.erebelo.springneo4jdemo.domain.response.UserResponse;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = WARN)
public interface UserMapper {

    List<UserLazyResponse> lazyNodeListToResponseList(List<UserNode> nodeList);

    UserLazyResponse lazyNodeToResponse(UserNode node);

    UserResponse nodeToResponse(UserNode node);

    FollowRelationshipResponse mapFollowRelationshipToFollowRelationshipResponse(FollowRelationship relationship);

    PostRelationshipResponse mapPostRelationshipToPostRelationshipResponse(PostRelationship relationship);

    TweetLazyResponse mapTweetNodeToTweetLazyResponse(TweetNode node);

    UserNode requestToNode(UserRequest request);

}
