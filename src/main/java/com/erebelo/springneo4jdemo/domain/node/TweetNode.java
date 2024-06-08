package com.erebelo.springneo4jdemo.domain.node;

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

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Node("Tweet")
public class TweetNode {

    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;

    private String title;
    private String content;
    private Integer likes;

    @Relationship(type = "POST", direction = INCOMING)
    private UserNode user;

}
