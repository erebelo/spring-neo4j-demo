package com.erebelo.springneo4jdemo.repository;

import com.erebelo.springneo4jdemo.domain.node.UserNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends Neo4jRepository<UserNode, String> {

}
