package com.erebelo.springneo4jdemo.repository;

import com.erebelo.springneo4jdemo.domain.node.TweetNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends Neo4jRepository<TweetNode, String> {

}
