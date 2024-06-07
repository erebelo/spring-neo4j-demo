package com.erebelo.springneo4jdemo.domain.node;

import com.erebelo.springneo4jdemo.domain.relationship.FollowsRelationship;
import com.erebelo.springneo4jdemo.domain.relationship.PostedRelationship;
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

import java.util.List;
import java.util.Set;
import java.util.UUID;

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
    private UUID id;

    private String username;
    private String name;

    @Relationship(type = "FOLLOWS", direction = OUTGOING)
    private Set<FollowsRelationship> following;

    @Relationship(type = "FOLLOWS", direction = INCOMING)
    private Set<FollowsRelationship> followers;

    @Relationship(type = "POSTED", direction = OUTGOING)
    private List<PostedRelationship> posts;

}
