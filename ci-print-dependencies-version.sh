#!/bin/sh

# Make the script to abort if any command fails
set -e

# Print the commands as it is executed. Useful for debugging
set -x

/usr/bin/chromium-browser --version
/usr/lib/chromium-browser/chromedriver --version