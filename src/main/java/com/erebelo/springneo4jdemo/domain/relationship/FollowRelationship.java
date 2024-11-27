package com.erebelo.springneo4jdemo.domain.relationship;

import com.erebelo.springneo4jdemo.domain.node.UserNode;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 * Avoid using `@Data` here to prevent circular references leading to
 * `StackOverflowError`.
 * <p>
 * `@ToString` is manually * handled to avoid recursion.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RelationshipProperties
public class FollowRelationship {

    @RelationshipId
    private Long id;

    @TargetNode
    private UserNode user;

    private LocalDateTime sinceAt;

    /**
     * Override `toString()` to avoid circular reference causing
     * `StackOverflowError`.
     * <p>
     * Print only the username of the `UserNode`.
     */
    @Override
    public String toString() {
        return "FollowRelationship{" + "id=" + id + ", user=" + (user != null ? user.getUsername() : "null")
                + ", sinceAt=" + sinceAt + '}';
    }
}
