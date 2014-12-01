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
	"Run your application's client with the appropriate key stores" 
	"Run your application's client in debug mode" 
	"Quit"
	)

select opt in "${options[@]}"

do
    case $opt in

        ###########################################################################################
        ##                                   REGULAR RUN MODES                                   ##
        ###########################################################################################

        # Run your application's clients with the appropriate key stores. 
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

			-Djavax.net.ssl.keyStorePassword="${password_keystore}" "${client}" "${hostName}" "${portNumber}"
        ;;

        ###########################################################################################
        ##                                       DEBUG MODES                                     ##
        ###########################################################################################

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

			java -Djavax.net.ssl.keyStore=../server/keystore \
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











