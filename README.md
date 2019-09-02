### implemented commands
* `/tp_list` shows the tp ID of players 
* `/tp_id <id>` teleport to a player by id (/tp_list)
* `/tp_name <name>` teleport to a player by name
* `/loc` your current location in block units
* `/tp_co <x> <y>` teleport to a location in block units

### todo
* [ ] add a cooldown
* [ ] teamonly during pvp
* [ ] tp to closest core

### Building a Jar *(without dependencies)*

`gradlew jar` / `./gradlew jar`

Output jar should be in `build/libs`.


### Installing

Simply place the output jar from the step above in your server's `config/plugins` directory and restart the server.
List your currently installed plugins by running the `plugins` command.
