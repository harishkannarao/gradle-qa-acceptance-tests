#!/bin/sh

# Make the script to abort if any command fails
set -e

# Print the commands as it is executed. Useful for debugging
set -x

./gradlew clean build -DchromeHeadless=true -DchromeDriverBinary=/usr/lib/chromium-browser/chromedriver -DchromeBinary=/usr/bin/chromium-browser