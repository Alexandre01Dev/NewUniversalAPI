package be.alexandre01.eloriamc.server.packets;

import fr.betterlight.Main;
import fr.betterlight.utils.packet.custom_events.PacketDecodeEvent;
import fr.betterlight.utils.packet.custom_events.PacketEncodeEvent;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.util.List;

public class PacketInjector extends Thread{
    boolean isInjected = false;
    final Player player;
    final Channel channel;
    final PluginManager pluginManager;
    final PacketInjectorManager packetInjectorManager;
    public PacketInjector(Player player){
        this.player = player;
        this.pluginManager = Bukkit.getPluginManager();
        channel = ((CraftPlayer)this.player).getHandle().playerConnection.networkManager.channel;
        packetInjectorManager = Main.getInstance().getPacketInjectorManager();
        packetInjectorManager.addInjector(this);

    }
    public void injectAll(){
        injectInputDecoder();
        injectOutputEncoder();
    }

    public synchronized void injectInputDecoder(){
        channel.pipeline().addAfter("decoder","PacketDecoderInjector",new MessageToMessageDecoder<Packet<?>>() {
            @Override
            protected void decode(ChannelHandlerContext channelHandlerContext, Packet<?> packet, List<Object> list) throws Exception {
                list.add(packet);
                for( PacketInterceptor interceptor : packetInjectorManager.getInterceptors()){
                    interceptor.decode(player,packet);
                    if(interceptor.isCancelled()){
                        list.remove(packet);
                        return;
                    }
                }
                PacketDecodeEvent event = new PacketDecodeEvent(packet,player);
                pluginManager.callEvent(event);

                if(event.isCancelled()){
                    list.remove(packet);
                }
            }
        });
        isInjected = true;
    }

    public synchronized void injectOutputEncoder(){
        channel.pipeline().addAfter("encoder","PacketEncoderInjector",new MessageToMessageEncoder<Packet<?>>() {
            @Override
            protected void encode(ChannelHandlerContext channelHandlerContext, Packet<?> packet, List<Object> list) throws Exception {
                list.add(packet);
                for( PacketInterceptor interceptor : packetInjectorManager.getInterceptors()){
                    interceptor.encode(player,packet);
                    if(interceptor.isCancelled()){
                        list.remove(packet);
                        return;
                    }
                }
                PacketEncodeEvent event = new PacketEncodeEvent(packet,player);
                pluginManager.callEvent(event);


                if(event.isCancelled()){
                    list.remove(packet);
                }
            }
        });
        isInjected = true;
    }

    public void uninjectAll(){
        uninjectDecoder();
        uninjectEncoder();
        isInjected = false;
    }

    public void uninjectDecoder(){
        ChannelPipeline pipeline = channel.pipeline();
        if(pipeline.get("PacketDecoderInjector") != null){
            pipeline.remove("PacketDecoderInjector");
        }
        //VERIF IFINJECT
    }
    public void uninjectEncoder(){
        ChannelPipeline pipeline = channel.pipeline();
        if(pipeline.get("PacketEncoderInjector") != null){
            pipeline.remove("PacketEncoderInjector");
        }
    }
}
