package com.erebelo.springneo4jdemo.repository;

import com.erebelo.springneo4jdemo.domain.relationship.FollowRelationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends Neo4jRepository<FollowRelationship, Long> {

}
