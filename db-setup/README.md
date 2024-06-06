## Neo4j Server Setup

1. Download and extract [Graph Database Self-Managed](https://neo4j.com/deployment-center/) for Windows by choosing the community option **Windows Executable
   Neo4j 5.20.0 (zip)**

2. Navigate to _C:\<NEO4J_PATH>\bin_ directory using Git Bash terminal

3. Start Neo4j Server (**KEEP THIS TERMINAL OPPENED**):

   **NOTE:** to stop the Neo4j server press CTRL + C.

   `$ ./neo4j.bat console`

4. Open the Neo4j Browser by accessing **http://localhost:7474/** using the desired local browser:

   - Fill in the _username_ and _password_ fields as **neo4j**
   - Follow the instructions to create a new password

## Batch File

**NOTE:** change the _<NEO4J_PATH>_ reference.

1. Start Neo4j:

   **start_neo4j.bat**

   ```
   @echo off
   cd "C:\<NEO4J_PATH>\bin"
   neo4j.bat console
   ```
