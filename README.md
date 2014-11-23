JSSLInfoCollectionServer
========================
A simple JSSL secure clint/server program that allows multithreading sockets, allowing multiple clients to log on simultaniously, and fill out user information. Both client and server display various properties of each sockeets session upon creation of new connections. Once the user information is collected, for each user, the collected information is stored in a text file named after the username, i.e. <username>.txt. Each client has the ability to keep creating usernames until they choose not to. For security, this program uses SSL, keystores, truststores, and trusted certificates, along with corrosponding passwords and naturally encrypts messages going to and from both client and server. 

Navigation
-----------
[JSSLInfoCollectionServer](#jsslinfocollectionserver) |
[Purpose](#purpose) |
[Screenshot](#screenshot) |
[Author](#author) |
[Usage](#usage) | 
[Copyright](#copyright) | 


Purpose
-------
This was written for my final project in CS3750-Computer/Network Secutiry class at MSU Denver

Screenshot
----------
![Picture]


Author
------
Joshua Michael Waggoner (rabbitfighter81@gmail.com)</li>

Usage
-----
To run the client you must first use keytool to create a simple JKS keystore suitable for use with JSSE. Make a keyEntry (with public/private keys) in your keystore, then make corrosponding trustedCertEnttry (public keys only) in your truststore. keystore goes in server directory and the truststore goes in the client directory. 
<ul>
<li> To run the server w/o debuggin, you must add the following parameters: \n
java -Djavax.net.ssl.keyStore=<path to keystore> -Djavax.net.ssl.keyStorePassword=<password> Server <port>
</li>
<li> To run the client w/o debuggin, you must add the following parameters: \n
java -Djavax.net.ssl.keyStore=<path to keystore> -Djavax.net.ssl.keyStorePassword=<password> Client <host name> <port>
</li>

Copyright
---------
(c) rabbitfighter.net 2014





