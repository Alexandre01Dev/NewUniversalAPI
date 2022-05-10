package be.alexandre01.eloriamc.server.packets;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.WorldSettings.EnumGamemode;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class NPC extends Reflections {

    @Getter
    private int entityID;
    @Getter
    private Player player;
    @Getter
    private String name;
    @Getter
    private Location location;

    @Getter
    private GameProfile gameProfile;

    public NPC(Player player, String name, Location location) {
        this.player = player;
        this.name = name;
        this.location = location;
        entityID = (int) Math.ceil(Math.random() * 1000) + 2000;
        gameProfile = new GameProfile(UUID.randomUUID(), name);
    }

    public void spawn() {
        PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn();
        setValue(packet, "a", entityID);
        setValue(packet, "b", gameProfile.getId());
        this.setValue(packet, "c", this.getFixLocation(this.location.getX()));
        this.setValue(packet, "d", this.getFixLocation(this.location.getY()));
        this.setValue(packet, "e", this.getFixLocation(this.location.getZ()));
        this.setValue(packet, "f", this.getFixRotation(this.location.getYaw()));
        this.setValue(packet, "g", this.getFixRotation(this.location.getPitch()));
        this.setValue(packet, "h", 0);
        DataWatcher w = new DataWatcher((Entity)null);
        w.a(6, 20.0F);
        w.a(10, (byte)127);
        this.setValue(packet, "i", w);
        addToTablist();
        System.out.println(packet.toString());
        this.sendPacket(packet, player);
        headRotation(location.getYaw(), location.getPitch());
    }

    public void addToTablist(){
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
        PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(gameProfile, 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(gameProfile.getName())[0]);
        @SuppressWarnings("unchecked")
        List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(packet, "b");
        players.add(data);

        setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER);
        setValue(packet, "b", players);

        sendPacket(player,packet);
    }

    public void rmvFromTablist(){
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
        PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(gameProfile, 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(gameProfile.getName())[0]);
        @SuppressWarnings("unchecked")
        List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(packet, "b");
        players.add(data);

        setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER);
        setValue(packet, "b", players);

        sendPacket(player,packet);
    }

    public void destroy(){
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] {entityID});
        rmvFromTablist();
        sendPacket(player,packet);
    }

    public void headRotation(float yaw,float pitch){
        PacketPlayOutEntity.PacketPlayOutEntityLook packet = new PacketPlayOutEntity.PacketPlayOutEntityLook(entityID, getFixRotation(yaw),getFixRotation(pitch) , true);
        PacketPlayOutEntityHeadRotation packetHead = new PacketPlayOutEntityHeadRotation();
        setValue(packetHead, "a", entityID);
        setValue(packetHead, "b", getFixRotation(yaw));


        sendPacket(player,packet);
        sendPacket(player,packetHead);
    }

    public void teleport(Location location){

        PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport();
        setValue(packet, "a", entityID);
        setValue(packet, "b", getFixLocation(location.getX()));
        setValue(packet, "c", getFixLocation(location.getY()));
        setValue(packet, "d", getFixLocation(location.getZ()));
        setValue(packet, "e", getFixRotation(location.getYaw()));
        setValue(packet, "f", getFixRotation(location.getPitch()));

        sendPacket(player,packet);
        headRotation(location.getYaw(), location.getPitch());
        this.location = location.clone();

    }

    public byte getFixRotation(float yawpitch) {
        return (byte)((int)(yawpitch * 256.0F / 360.0F));
    }

    public int getFixLocation(double pos) {
        return MathHelper.floor(pos * 32.0);
    }

    public void animation(int animation) {
        PacketPlayOutAnimation packet = new PacketPlayOutAnimation();
        this.setValue(packet, "a", this.entityID);
        this.setValue(packet, "b", (byte)animation);
        this.sendPacket(this.player, packet);
    }

    public void status(int status) {
        PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus();
        this.setValue(packet, "a", this.entityID);
        this.setValue(packet, "b", (byte)status);
        this.sendPacket(this.player, packet);
    }

    public void equip(int slot, ItemStack itemstack) {
        PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment();
        this.setValue(packet, "a", this.entityID);
        this.setValue(packet, "b", slot);
        this.setValue(packet, "c", itemstack);
        this.sendPacket(this.player, packet);
    }


    public void changeSkin(String texture, String signature){
        gameProfile.getProperties().put("textures", new Property("textures", texture, signature));
    }


    public void setInvisible() {
        DataWatcher w = new DataWatcher((Entity)null);
        w.a(0, (byte)32);
        PacketPlayOutEntityMetadata metadataPacket = new PacketPlayOutEntityMetadata(this.entityID, w, true);
        this.sendPacket(this.player, metadataPacket);
    }

    public void setStanding() {
        DataWatcher w = new DataWatcher((Entity)null);
        w.a(0, (byte)0);
        PacketPlayOutEntityMetadata metadataPacket = new PacketPlayOutEntityMetadata(this.entityID, w, true);
        this.sendPacket(this.player, metadataPacket);
    }

    public void setOnFire() {
        DataWatcher w = new DataWatcher((Entity)null);
        w.a(0, (byte)1);
        PacketPlayOutEntityMetadata metadataPacket = new PacketPlayOutEntityMetadata(this.entityID, w, true);
        this.sendPacket(this.player, metadataPacket);
    }

    public void setCrouch() {
        DataWatcher w = new DataWatcher((Entity)null);
        w.a(0, (byte)2);
        PacketPlayOutEntityMetadata metadataPacket = new PacketPlayOutEntityMetadata(this.entityID, w, true);
        this.sendPacket(this.player, metadataPacket);
    }

    public void sleep(boolean state) {
        if (state) {
            Location bedLocation = new Location(this.player.getWorld(), this.location.getX(), 2.0, this.location.getZ());
            PacketPlayOutBed packet = new PacketPlayOutBed();
            this.setValue(packet, "a", this.entityID);
            this.setValue(packet, "b", new BlockPosition(bedLocation.getX(), bedLocation.getY(), bedLocation.getZ()));
            Iterator var4 = Bukkit.getOnlinePlayers().iterator();

            while(var4.hasNext()) {
                Player pl = (Player)var4.next();
                pl.sendBlockChange(bedLocation, Material.BED_BLOCK, (byte)0);
            }

            this.sendPacket(this.player, packet);
            this.teleport(this.location.clone().add(0.0, 0.3, 0.0));
        } else {
            this.animation(2);
            this.teleport(this.location.clone().subtract(0.0, 0.3, 0.0));
        }

    }


}
