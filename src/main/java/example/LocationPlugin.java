package location;

import io.anuke.arc.*;
import io.anuke.arc.util.*;
import io.anuke.mindustry.*;
import io.anuke.mindustry.content.*;
import io.anuke.mindustry.entities.type.*;
import io.anuke.mindustry.game.EventType.*;
import io.anuke.mindustry.gen.*;
import io.anuke.mindustry.plugin.Plugin;
import java.lang.Math;

//testing java
import static java.lang.System.out;
import java.lang.reflect.Method;

public class LocationPlugin extends Plugin{

    //register event handlers and create variables in the constructor
    public LocationPlugin(){}

    //register commands that player can invoke in-game
    @Override
    public void registerClientCommands(CommandHandler handler){

        handler.<Player>register("tp_list", "", "Tp to this player by ID.", (args, player)->{
            int ID = 1;

            for (Player p: Vars.playerGroup.all()){

                player.sendMessage("[accent]"+ID+":[] "+p.name);
                ID++;
            }
            player.sendMessage("You can use [accent]/tp_id <id>[] to teleport to a player using the number next to their name.");
        });

        handler.<Player>register("tp_id", "<ID>", "Tp to this player by ID.", (args, player)->{
           try{
               int ID = Integer.parseInt(args[0]);
               Player other = Vars.playerGroup.all().get(ID-1);
               player.sendMessage("teleported to " + other.name);
               //strict on
               player.setNet(other.x-50, other.y);
               player.set(other.x-50, other.y);
               //strict off
               Call.onPositionSet(player.con.id, other.x-50, other.y);
           } catch (Exception e){
               player.sendMessage("[scarlet] invalid ID");
           }
        });

        handler.<Player>register("tp_name", "<player>", "Tp to this player.", (args, player)->{
            Player other = Vars.playerGroup.find(p->p.name.equalsIgnoreCase(args[0]));

            //give error message with scarlet-colored text if player isn't found
            if(other == null) {
                player.sendMessage("[scarlet]No player by that name found![]\nMaybe you could try tp_id and tp_list");
                return;
            }
            //strict off
            Call.onPositionSet(player.con.id, other.x-50, other.y);
            //strict on
            player.setNet(other.x - 50, other.y);
            player.set(other.x - 50, other.y);

            player.sendMessage("teleported to " + other.name);

        });

        handler.<Player>register("tp_co", "<x> <y>", "Tp to this location, (x,y).", (args, player)->{
            try{
                int x = Integer.parseInt(args[0])*8;
                int y = Integer.parseInt(args[1])*8;
                //strict off
                Call.onPositionSet(player.con.id, (float)x, (float)y);
                //strict on + local
                player.setNet((float)x, (float)y);
                player.set((float)x, (float)y);

            } catch (Exception e){
                player.sendMessage("[scarlet] x and y are integers!");
            }
        });

        handler.<Player>register("loc", "", "My location", (args, player)->{
            player.sendMessage("[green]Your location is:[] x = " + Math.round(player.x/8) + "; y = " + Math.round(player.y/8));
        });

        //for testing
        handler.<Player>register("test", "", "For testing only", (args, player)->{
            if (!player.isAdmin){
                return;
            }
            Class c = Player.class;
            // get list of methods
            Method[] methods = c.getMethods();

            // get the name of every method present in the list
            for (Method method : methods) {
                String MethodName = method.getName();
                System.out.println(MethodName);
            };
        });
    }
}
