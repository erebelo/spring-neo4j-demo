-----

-- Sets username as unique constraint
CREATE CONSTRAINT FOR (u:User) REQUIRE u.username IS UNIQUE;

-- Create User nodes
CREATE (e:User {id: "abc45678-abcd-90ef-ghij-1234abcd5678", username: "@eduardo", name: "Eduardo"}),
       (j:User {id: "def45678-abcd-90ef-ghij-1234abcd5678", username: "@jose", name: "José"})
RETURN e, j;

-----

-- Creates a Tweet node and associates it to a User node (@eduardo) through the POSTED relationship
CREATE (t:Tweet {id: "ghi45678-abcd-90ef-ghij-1234abcd5670", title: "Tweet 1", content: "This is my first tweet!", likes: 0})
WITH t
MATCH (u:User {username: "@eduardo"})
CREATE (u)-[:POSTED {createdAt: datetime()}]->(t)
RETURN u, t;

-- Creates a Tweet node and associates it to a User node (@eduardo) through the POSTED relationship
CREATE (t:Tweet {id: "jkl45678-abcd-90ef-ghij-1234abcd5670", title: "Tweet 2", content: "This is my second tweet!", likes: 0})
WITH t
MATCH (u:User {username: "@eduardo"})
CREATE (u)-[:POSTED {createdAt: datetime()}]->(t)
RETURN u, t;

-- Creates a Tweet node and associates it to a User node (@jose) through the POSTED relationship
CREATE (t:Tweet {id: "mno45678-abcd-90ef-ghij-1234abcd5670", title: "Tweet 1", content: "This is my first tweet!", likes: 0})
WITH t
MATCH (u:User {username: "@jose"})
CREATE (u)-[:POSTED {createdAt: datetime()}]->(t)
RETURN u, t;

-----

-- Associates a User (@eduardo) to another User (@jose) and vice versa through the FOLLOW relationship
MATCH (e:User {username: "@eduardo"})
MATCH (j:User {username: "@jose"})
CREATE (e)-[:FOLLOW {sinceAt: datetime()}]->(j),
	   (j)-[:FOLLOW {sinceAt: datetime()}]->(e)
RETURN e, j;

-----

-- Delete all
MATCH (n)
DETACH DELETE n;

-- Delete all User nodes
MATCH (u:User)
DETACH DELETE u;

-- Deletes a User node by username
MATCH (u:User {username: "@eduardo"})
DETACH DELETE u;

-- Deletes one of the FOLLOW relationships between two Users (@jose -> @eduardo)
MATCH (j:User {username: "@jose"})-[f:FOLLOW]->(e:User {username: "@eduardo"})
DELETE f
RETURN j, e;

-- Fetch by <elementId>
MATCH (u)
WHERE elementId(u) = "4:7bd1681f-0d48-4c9c-a7c4-f2d18ea4ba6e:4"
RETURN u;

-----