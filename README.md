The plugin contains 2 events, 1 scheduler, 1 command, 1 notifications.json and 1 config.yml,

command

The command is </afk> it toggles afk on and off

events

player quit event: it resets all the data when a player exits the server.
player move event: it removes the data that are used to check if the player is afk or not when he moves

notifications.json

It's were the messages that are being sent to the player are kept, a template can be found here https://github.com/Georgatos/AFKKicker/blob/main/src/main/resources/notifications.json

config.yml

Basic configuration, a template can be found here https://github.com/Georgatos/AFKKicker/blob/main/src/main/resources/config.yml

startingHour and finishingHour are of type String and must have the format "HH:mm"

TPS is a double, it checks at how many TPS should we start checking (with the combination of the startingHour and finishingHour) to kick the players out.

locationLimit How many times should it check if the player is in the same location before he gets kicked out of the server with a combination of tps, startingHour and finishingHour.




