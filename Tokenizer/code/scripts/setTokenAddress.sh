#!/bin/bash

# ask the user for the token address
read -p "Enter the Token42 contract address: " TOKEN_ADDRESS

# append in the .env file a new line with the token address
echo "TOKEN_ADDRESS=$TOKEN_ADDRESS" >> .env