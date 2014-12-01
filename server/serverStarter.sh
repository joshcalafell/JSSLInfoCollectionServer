#!/bin/bash
# Filname: ServerStarter.sh
# Author: Joshua Michael Waggoner
# Git-Hub: github.com/rabbitfighter81/JSSLInfoCollectionServer
# Purpose: Starts the server with proper arguments 

# Get the username to use with keytool
echo "Welcome to keystore generator..."

echo "Type the name you wish to use with keytool and press [ENTER]:"

read username

echo "You chose '$username'"

echo="Please enter your choice: "

options=(
	"Run your application's server with the appropriate key stores" 
	"Run your application's server in debug mode" 
	"Quit"
	)

select opt in "${options[@]}"

do
    case $opt in

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

		# Quit
        "Quit")
            break
            ;;
        *) echo invalid option;;
    esac
done











