package com.erebelo.springneo4jdemo.domain.relationship;

import com.erebelo.springneo4jdemo.domain.node.TweetNode;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RelationshipProperties
public class PostRelationship {

    @RelationshipId
    private Long id;

    @TargetNode
    private TweetNode tweet;

    private LocalDateTime createdAt;

}
