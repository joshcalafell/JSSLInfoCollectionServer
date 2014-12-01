JSSLInfoCollectionServer
========================
A simple JSSL secure clint/server program that allows multithreading sockets, allowing multiple clients to log on simultaniously, and fill out user information. Both client and server display various properties of each socket's session upon creation and subsequent connections. Once the user's information is collected, for each user, the collected information is stored in a text file named after the username, i.e. 'username'.txt. Each client has the ability to keep creating usernames until they choose not to. For security, this program uses SSL, keystores, truststores, and trusted certificates, along with corrosponding passwords. This process naturally encrypts messages going to and from both client and server. Both programs can be run in either regular or debug mode. Debug mode is especially interesting if you really want to understand how this process works. 

Navigation
-----------
[JSSLInfoCollectionServer](#jsslinfocollectionserver) |
[Author](#author) |
[Purpose](#purpose) |
[Usage](#usage) | 
[Server Screenshot](#serverscreenshot) |
[Client Screenshot](#clientrscreenshot) |
[Contents of User File](#contentsofuserfile)
[Shell Script](#shellscript) |
[Copyright](#copyright)  

Author
------
Joshua Michael Waggoner (rabbitfighter81@gmail.com)</li>

Purpose
-------
This was written for my final project in CS3750-Computer/Network Secutiry class at MSU Denver

Usage
-----
To run the client you must first use keytool to create a simple JKS keystore suitable for use with JSSE. Make a keyEntry (with public/private keys) in your keystore, then make corrosponding trustedCertEnttry (public keys only) in your truststore. Once created, the keystore goes in server directory and the truststore goes in the client directory. 

<ul><strong>To run the server w/o debugging, you must add the following parameters:</strong>
<li>java -Djavax.net.ssl.keyStore='path to keystore' -Djavax.net.ssl.keyStorePassword='password' Server 'port'</li>
</ul>
<ul><strong>To run the client w/o debugging, you must add the following parameters:</strong>
<li>java -Djavax.net.ssl.keyStore='path to keystore' -Djavax.net.ssl.keyStorePassword='password' Client 'host name' 'port'</li> 
</ul>
<ul><strong>To run the server w/ debugging, you must add the following parameters:</strong>
<li>java -Djavax.net.ssl.keyStore='path to keystore' -Djavax.net.ssl.keyStorePassword='password' -Djavax.net.debug=all Server 'port'</li>
</ul>
<ul><strong>To run the client w/ debuggin, you must add the following parameters:</strong>
<li>java -Djavax.net.ssl.keyStore='path to keystore' -Djavax.net.ssl.keyStorePassword='password' -Djavax.net.debug=all Client 'host name' 'port'</li> 
</ul>

Server Screenshot
-----------------
![Picture](http://rabbitfighter.net/wp-content/uploads/2014/11/Server.png)

Client Screenshot
-----------------
![Picture](http://rabbitfighter.net/wp-content/uploads/2014/11/Client.png)

Contents of User File
---------------------
![Picture](http://rabbitfighter.net/wp-content/uploads/2014/11/catOfFooBar.png)

Shell Script
------------
I wrote a shell script to help create the truststore, keystore, and certificates, as well as help users import the certs into keystores. The script can be found here:
<ul>
<li>https://github.com/rabbitfighter81/SSLKeytool</li>
</ul>

Copyright
---------
<strong> &#169; 2014 http://www.rabbitfighter.net</strong>





