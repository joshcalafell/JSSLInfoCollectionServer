#!/bin/bash
# Filname: SSLKeysttoreGenerator.sh
# Author: Joshua Michael Waggoner
# Git-Hub: github.com/rabbitfighter81/SSLSockets
# Purpose: keytool to create a simple JKS keystore suitable for use with JSSE. 

# Get the username to use with keytool
echo "Welcome to keystore generator..."

echo "Type the name you wish to use with keytool and press [ENTER]:"

read username

echo "You chose '$username'"

echo="Please enter your choice: "

options=(
	"Create new keystore" 
	"Examine the keystore" 
	"Export and examine the self-signed certificate" 
	"Import the certificate into a new truststore" 
	"Examine the truststore" 
	"Add to your local JVM's trust store"
	"Run your application's server with the appropriate key stores" 
	"Run your application's client with the appropriate key stores" 
	"Run your application's server in debug mode" 
	"Run your application's client in debug mode" 
	"Quit"
	)

select opt in "${options[@]}"

do
    case $opt in

    	###########################################################################################
        ##                                     KEY TOOLS                                         ##
        ###########################################################################################

    	# 1) Create a new keystore and self-signed certificate with corresponding 
		# public/private keys.
        "Create new keystore")

            echo "you chose \"Create new keystore\""

			keytool -genkeypair -alias $username -keyalg RSA \
			-validity 7 -keystore keystore
        ;;

        # 2) Examine the keystore
        "Examine the keystore")

            echo "You chose \"Examine the keystore\""

			keytool -list -v -keystore keystore
        ;;

        # 3) Export and examine the self-signed certificate.
        "Export and examine the self-signed certificate")

            echo "You chose \"Export and examine the self-signed certificate\""

			keytool -export -alias $username -keystore keystore -rfc \
			-file $username.cer
        ;;

        # 4) Import the certificate into a new truststore.
        "Import the certificate into a new truststore")

	        echo "You chose \"Import the certificate into a new truststore\""

			keytool -import -alias $username -file $username.cer \
			-keystore truststore
        ;;

        # 5) Examine the truststore.
        "Examine the truststore")

	        echo "You chose \"Examine the truststore\""

			keytool -list -v -keystore truststore
        ;;

        "Add to your local JVM's trust store")

			echo "You chose \"Add to your local JVM's trust store\""

			echo "Please enter the path to your computer's \"cacerts\" file"
				 #"This is usually found in /usr/lib/jvm/<java version folder>
				 #/jre/lib/security\" or something similar" -> /etc/ssl/certs/java/cacerts

			read path

			sudo keytool -import -file $username.cer -alias "${username}" -keystore "${path}"

		;;

        ###########################################################################################
        ##                                   REGULAR RUN MODES                                   ##
        ###########################################################################################

        # 6) Now run your applications with the appropriate key stores.
        "Run your application's server with the appropriate key stores")

	        echo "You chose \"Run your application's server with the appropriate key stores\""
	        echo "Please enter the name of your compiled server class w/o extension and press [ENTER]"
	        read server
	        echo "Please enter the password for the keystore and press [ENTER]"
	        read password_keystore
	        echo "Please enter the port number to use and press [ENTER]"
	        read portNumber

			java -Djavax.net.ssl.keyStore=keystore \
			-Djavax.net.ssl.keyStorePassword="${password_keystore}" "${server}" "${portNumber}"
        ;;

        # 7) Now run your application's clients with the appropriate key stores. 
        "Run your application's client with the appropriate key stores")

	        echo "You chose \"Run your application's client with the appropriate key stores\""
	        echo "Please enter the name of your compiled client class w/o extension and press [ENTER]"
	        read client
	    	echo "Please enter the password for the keystore and press [ENTER]"
	        read password_keystore
	        echo "Please enter the port number to use and press [ENTER]"
	        read portNumber
	        echo "Please enter the hostname to use and press [ENTER]"
	        read hotName

			java -Djavax.net.ssl.keyStore=keystore \
			-Djavax.net.ssl.keyStorePassword="${password_keystore}" "${client}" "${hostName}" "${portNumber}"
        ;;

        ###########################################################################################
        ##                                       DEBUG MODES                                     ##
        ###########################################################################################

        # Run your applications with the appropriate key stores IN DEBUG MODE.
        "Run your application's server in debug mode")

	        echo "You chose \"Run your application's server in debug mode\""
	        echo "Please enter the name of your compiled server class w/o extension and press [ENTER]"
	        read server
	        echo "Please enter the password for the keystore and press [ENTER]"
	        read password_keystore
	        echo "Please enter the port number tto use and press [ENTER]"
	        read portNumber

			java -Djavax.net.ssl.keyStore=keystore \
			-Djavax.net.ssl.keyStorePassword="${password_keystore}" \
			-Djavax.net.debug=all "${server}" "${portNumber}"
		;;

		# Run your application's clients with the appropriate key stores IN DEBUG MODE. 
        "Run your application's client in debug mode")

	        echo "You chose \"Run your application's client in debug mode\""
	        echo "Please enter the name of your compiled client class w/o extension and press [ENTER]"
	        read client
	    	echo "Please enter the password for the keystore and press [ENTER]"
	        read password_keystore
	        echo "Please enter the port number to use and press [ENTER]"
	        read portNumber
	        echo "Please enter the hostname to use and press [ENTER]"
	        read hotName

			java -Djavax.net.ssl.keyStore=keystore \
			-Djavax.net.ssl.keyStorePassword="${password_keystore}" \
			-Djavax.net.debug=all "${client}" "${hostName}" "${portNumber}"

        ;;

		# Quit
        "Quit")
            break
            ;;
        *) echo invalid option;;
    esac
done











