package be.alexandre01.universal.server.packets.npc;

import be.alexandre01.universal.server.SpigotPlugin;
import be.alexandre01.universal.server.packets.npc.type.NPCHuman;
import be.alexandre01.universal.server.player.BasePlayer;
import be.alexandre01.universal.server.utils.locations.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class MirrorNPC {

    private Cuboid centerMirror;
    private Vector rotation;
    private Cuboid zone;

    private Location mirrorMin;
    private Location mirrorMax;
    private HashMap<Player,Boolean> isInMirror = new HashMap<>();

    public MirrorNPC(Location mirrorMin,Location mirrorMax, Cuboid zone) {
        this.zone = zone;
        this.mirrorMin = mirrorMin;
        this.mirrorMax = mirrorMax;
        this.rotation = mirrorMin.toVector().subtract(mirrorMax.toVector()).normalize();

    }

    public void run(Player player){

        BukkitTask refresh = Bukkit.getScheduler().runTaskTimerAsynchronously(SpigotPlugin.getInstance(), () -> {
            if(!isInMirror.containsKey(player)){
                Location location = player.getLocation();
                if(zone.contains(location)){
                   isInMirror.put(player,true);
                   updateNPC(player);
                }
            }
        }, 20,20);
    }

    public void updateNPC(Player player){
        if(!isInMirror.containsKey(player)) return;
        NPC npc = new NPC(new StringBuilder(player.getDisplayName()).reverse().toString(),player.getLocation());
        BasePlayer basePlayer = SpigotPlugin.getInstance().getBasePlayer(player);

        npc.setSkin(basePlayer.getSkinData());

        NPCHuman npcHuman = (NPCHuman) npc.initAndShow(player);

        BukkitTask b = Bukkit.getScheduler().runTaskTimerAsynchronously(SpigotPlugin.getInstance(), () -> {
            if(!isInMirror.containsKey(player)){
                npc.get(player).hide();
                isInMirror.remove(player);
            }

            double xDif = zone.getMinX() - player.getLocation().getX();
            double zDif = zone.getMinZ() - player.getLocation().getZ();
            Location locationWithVector = mirrorMin.clone().add(rotation.clone().normalize().multiply(new Vector(xDif,0,zDif)));

            System.out.println(locationWithVector.distance(player.getLocation()));
          //  Vector v = player.getLocation().toVector().subtract(centerMirror.toVector()).multiply(2);

            /*locationWithVector.
            Location location = v.toLocation(centerMirror.getWorld());
            location.setDirection(player.getLocation().subtract(location).toVector());
            npcHuman.teleport(location);*/
        }, 1,1);
    }



}
