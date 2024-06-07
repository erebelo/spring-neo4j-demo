-----

-- Sets username as unique constraint
CREATE CONSTRAINT FOR (u:User) REQUIRE u.username IS UNIQUE;

-- Create User nodes
CREATE (e:User {username: "@eduardo", name: "Eduardo"}),
       (j:User {username: "@jose", name: "José"})
RETURN e, j;

-----

-- Creates a Tweet node and associates it to a User node (@eduardo)
CREATE (t:Tweet {title: "Tweet 1", content: "This is my first tweet!", likes: 0})
WITH t
MATCH (u:User {username: "@eduardo"})
CREATE (u)-[:POSTED {createdAt: datetime()}]->(t)
RETURN u, t

-- Creates a Tweet node and associates it to a User node (@eduardo)
CREATE (t:Tweet {title: "Tweet 2", content: "This is my second tweet!", likes: 0})
WITH t
MATCH (u:User {username: "@eduardo"})
CREATE (u)-[:POSTED {createdAt: datetime()}]->(t)
RETURN u, t

-- Creates a Tweet node and associates it to a User node (@jose)
CREATE (t:Tweet {title: "Tweet 1", content: "This is my first tweet!", likes: 0})
WITH t
MATCH (u:User {username: "@jose"})
CREATE (u)-[:POSTED {createdAt: datetime()}]->(t)
RETURN u, t

-----

-- Associates a User (@eduardo) to another User (@jose) and vice versa
MATCH (e:User {username: "@eduardo"})
MATCH (j:User {username: "@jose"})
CREATE (e)-[:FOLLOWS {sinceAt: datetime()}]->(j),
	   (j)-[:FOLLOWS {sinceAt: datetime()}]->(e)
RETURN e, j

-----

-- Delete all
MATCH (n)
DETACH DELETE n;

-- Delete all User nodes
MATCH (u:User)
DETACH DELETE u;

-- Deletes a User node by username
MATCH (u:User {username: "@eduardo"})
DETACH DELETE u

-- Deletes one of the associations between two Users (@jose -> @eduardo)
MATCH (j:User {username: "@jose"})-[f:FOLLOWS]->(e:User {username: "@eduardo"})
DELETE f
RETURN j, e;

-----