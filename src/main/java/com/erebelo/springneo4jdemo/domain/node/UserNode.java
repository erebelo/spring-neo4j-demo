package com.erebelo.springneo4jdemo.domain.node;

import com.erebelo.springneo4jdemo.domain.relationship.FollowRelationship;
import com.erebelo.springneo4jdemo.domain.relationship.PostRelationship;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;
import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Node("User")
public class UserNode {

    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;

    private String username;
    private String name;

    @Relationship(type = "FOLLOW", direction = OUTGOING)
    private Set<FollowRelationship> following = new HashSet<>();

    @Relationship(type = "FOLLOW", direction = INCOMING)
    private Set<FollowRelationship> followers = new HashSet<>();

    @Relationship(type = "POST", direction = OUTGOING)
    private List<PostRelationship> posts = new ArrayList<>();

}
