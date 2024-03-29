package be.alexandre01.universal.server.player;

import be.alexandre01.universal.API;
import be.alexandre01.universal.chat.ChatConfiguration;
import be.alexandre01.universal.chat.ChatOptions;
import be.alexandre01.universal.data.PlayerData;
import be.alexandre01.universal.server.SpigotPlugin;
import be.alexandre01.universal.server.packets.injector.PacketInjector;
import be.alexandre01.universal.server.packets.injector.compatibility.ProtocolInjector;
import be.alexandre01.universal.server.packets.skin.SkinData;
import be.alexandre01.universal.server.packets.ui.scoreboard.PersonalScoreboard;
import be.alexandre01.universal.server.session.players.PlayerDamager;
import be.alexandre01.universal.utils.ClassUtils;
import com.mojang.authlib.properties.Property;
import lombok.Getter;
import net.md_5.bungee.api.chat.BaseComponent;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.*;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class BasePlayer extends CraftPlayer implements TitleImpl, NameTagImpl {
    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    @Getter
    PacketInjector packetInjector;
    @Getter PlayerDamager damagerUtils = new PlayerDamager();
    SpigotPlugin spigotPlugin;
    API api;
    Player player;
    @Getter SkinData skinData;

    PlayerData playerData;

    @Getter
    PersonalScoreboard personalScoreboard;

    public BasePlayer(Player player) {
        super(((CraftServer) player.getServer()), ((CraftPlayer) player).getHandle());
        this.player = player;
        this.spigotPlugin = SpigotPlugin.getInstance();
        this.api = API.getInstance();
        if(ClassUtils.classExist("com.comphenix.protocol.ProtocolManager")) {
            System.out.println("ProtocolInjector");
            this.packetInjector = new ProtocolInjector(player);
        }else {
            //this.packetInjector = new PacketInjector(player);
        }
        try {
            Property property = getProfile().getProperties().get("textures").iterator().next();
            skinData = new SkinData(property.getValue(), property.getSignature());
        }catch (Exception e){
            skinData = null;
        }



    }



    public void setPersonalScoreboard(PersonalScoreboard personalScoreboard) {
        if(this.personalScoreboard != null) {
            this.personalScoreboard.onLogout();
        }
        this.personalScoreboard = personalScoreboard;
    }

    @Override
    public Player getPlayer() {
       return this.player;
    }

    public PlayerData getData(boolean request){
        if(request || playerData == null) {
            this.playerData = api.getPlayerDataManager().getPlayerData(this.player.getName());
        }
        return playerData;
    }

    public void refreshData(){
        this.playerData = api.getPlayerDataManager().getPlayerData(this.player.getName());
    }


    /**
     * Send packet to Player
     * @param packet
     */
    public void sendPacket(Packet<?> packet){
        getHandle().playerConnection.sendPacket(packet);
    }

    /**
     * Send Message To Player
     * @param chatOptions
     * @param message
     * */
    public void sendMessage(ChatOptions chatOptions, String message){
        sendMessage(api.getChatConfiguration().getChatTextBuilder(chatOptions.getName()),message);
    }
    public void sendMessage(ChatOptions chatOptions, BaseComponent... components){
        sendMessage(api.getChatConfiguration().getChatTextBuilder(chatOptions.getName()),components);
    }
    public void sendMessage(String name, String message){
        sendMessage(api.getChatConfiguration().getChatTextBuilder(name),message);
    }
    public void sendMessage(String name, BaseComponent... components){
        sendMessage(api.getChatConfiguration().getChatTextBuilder(name),components);
    }
    public void sendMessage(ChatConfiguration.ChatTextBuilder chatTextBuilder, String message){
        spigot().sendMessage(chatTextBuilder.build(this,message));
    }
    public void sendMessage(ChatConfiguration.ChatTextBuilder chatTextBuilder, BaseComponent... components){
        spigot().sendMessage(chatTextBuilder.build(this,components));
    }
    public void sendMessage(BaseComponent... baseComponent){
        this.spigot().sendMessage(baseComponent);
    }
    public void sendMessage(Collection<String> message){
        for(String s : message){
            sendMessage(s);
        }
    }
    public void sendMessage(ArrayList<String> message){
        for(String s : message){
            sendMessage(s);
        }
    }
    public void sendMessage(LinkedList<String> message){
        for(String s : message){
            sendMessage(s);
        }
    }
    public void sendMessage(List<String> message){
        for(String s : message){
            sendMessage(s);
        }
    }
    public void sendMessage(String[] message){
        for(String s : message){
            sendMessage(s);
        }
    }

    /**
     * Send Title and ActionBar to player
     * @param fadeIn
     * @param stay
     * @param fadeOut
     * @param message
     */
    @Override
    public void sendTitle(Integer fadeIn, Integer stay, Integer fadeOut, String message) {
        TitleImpl.super.sendTitle(this,fadeIn,stay,fadeOut,message);
    }

    @Override
    public void sendSubtitle(Integer fadeIn, Integer stay, Integer fadeOut, String message) {
        TitleImpl.super.sendSubtitle(this,fadeIn,stay,fadeOut,message);
    }

    @Override
    public void sendFullTitle(Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
        TitleImpl.super.sendFullTitle(this,fadeIn,stay,fadeOut,title,subtitle);
    }

    @Override
    public void sendTitle(Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
        TitleImpl.super.sendTitle(this,fadeIn,stay,fadeOut,title,subtitle);
    }

    @Override
    public void sendTabTitle(String header, String footer) {
        TitleImpl.super.sendTabTitle(this,header,footer);
    }

    @Override
    public void sendActionBar(String message) {
        TitleImpl.super.sendActionBar(this,message);
    }



    //UNUSED METHODS

    @Override
    public void setNameTag(Player player, String teamName, String prefix, String suffix) {
        NameTagImpl.super.setNameTag(player,teamName,prefix,suffix);
    }

    @Override
    public void removeNameTag(Player player, String teamName) {
        NameTagImpl.super.removeNameTag(player, teamName);
    }

    public BasePlayer(CraftServer server, EntityPlayer entity) {
        super(server, entity);
    }

}
