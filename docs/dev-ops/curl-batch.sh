#!/bin/bash

# URL to send the GET requests to
URL="http://localhost:8091/api/exec"

# Number of requests to send
REQUEST_COUNT=10

for (( i=1; i<=$REQUEST_COUNT; i++ ))
do
    echo "Sending request $i to $URL"
    curl -X GET "$URL"
    echo # Add a new line for better readability
done
