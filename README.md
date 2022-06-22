# mc-packet-logger
A new version of the minecraft packet logger to packet log things.

## Use cases

1. You want to find out how a paid client does something
2. You want to learn the exact vanilla game packets for bypasses

If the first reason is why you're using this, I do not take liability for any lost client licenses because of the use of this tool. You are on your own.

## Building

1. Download zip or `git clone https://github.com/Sxmurai/mc-packet-logger/` in a terminal
2. Open the folder that it downloaded or unzip the contents into a folder
3. Open a terminal in that folder
4. Type the following commands in: `gradlew setupDecompWorkspace`, `gradlew clean`, `gradlew build` (Note: if you are on windows, you'll need to add a ./ before the gradlew command.
5. Your build jar will be in build/libs.

Don't want to do that? Open the releases tab and download the pre-compiled jar.

This does require forge and Java 8. Gradle should automatically download for you. If not, open it in IntelliJ or download Gradle yourself.
