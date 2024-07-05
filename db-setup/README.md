## Neo4j Server Setup

1. Download and extract [Graph Database Self-Managed](https://neo4j.com/deployment-center/) by choosing the community option **Neo4j 5.20.0 Released 23 May 2024** for the OS wanted

2. Navigate to _C:\\<NEO4J_PATH>\bin_ directory using Git Bash terminal

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

## Generating SSL Certificate

1. Navigate to _C:\\<NEO4J_PATH>\certificates_ directory

2. Create a new folder named _bolt_

3. Open the Git Bash terminal at _C:\\<NEO4J_PATH>\certificates\bolt_ path

4. Generate Private Key:

   `$ openssl genrsa -out key.pem 2048`

5. Generate Certificate Signing Request (CSR):

   **NOTE:** type the domain name (e.g., localhost) when asked to enter the **Common Name (e.g. server FQDN or YOUR name) []**

   `$ openssl req -new -key key.pem -out csr.pem`

6. Generate Self-signed Certificate:

   `$ openssl x509 -req -days 365 -in csr.pem -signkey key.pem -out cert.pem`

7. Navigate to _C:\\<NEO4J_PATH>\conf_ directory

8. Edit the **neo4j.conf** file

9. Locate the properties below and replace their values as follows:

   ```
   # Bolt connector
   server.bolt.enabled=true
   server.bolt.tls_level=REQUIRED
   server.bolt.listen_address=:7687
   server.bolt.advertised_address=:7687

   # Bolt SSL configuration
   dbms.ssl.policy.bolt.enabled=true
   dbms.ssl.policy.bolt.base_directory=certificates/bolt
   dbms.ssl.policy.bolt.private_key=key.pem
   dbms.ssl.policy.bolt.public_certificate=cert.pem
   dbms.ssl.policy.bolt.client_auth=NONE
   ```

10. Use the _cert.pem_ on the client side (e.g., backend service) to ensure secure communication with the database server by encrypting the data transmitted over the network
