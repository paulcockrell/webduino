#!/bin/bash

# Create a PTY
PTY=$(socat -d -d PTY,raw,echo=0 TCP:host.docker.internal:54321 &>/tmp/socat.log &)

# Sleep to ensure socat is running and PTY is ready
sleep 2

# Extract the PTY path (e.g., /dev/pts/0)
VIRTUAL_TTY=$(grep -o '/dev/pts/[0-9]*' /tmp/socat.log | head -n1)

echo "Using virtual serial port: $VIRTUAL_TTY, please wait while we connect..."

# Run your Clojure app, passing the port as an env var or CLI arg
export SERIAL_PORT=$VIRTUAL_TTY
clojure -M -m myapp.backend.main
