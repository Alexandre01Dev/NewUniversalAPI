package be.alexandre01.universal.server.utils.locations;

import be.alexandre01.universal.server.packets.Reflections;
import com.google.common.collect.Lists;
import net.minecraft.server.v1_8_R3.ChunkSection;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunk;
import org.bukkit.Chunk;

import java.util.ArrayList;
import java.util.Iterator;

public class ChunksUtils {
    public static ArrayList<ChunkCoord> around(Chunk origin, int radius) {

        org.bukkit.World world =  origin.getWorld();

        int length = (radius * 2) + 1;
        ArrayList<ChunkCoord> chunks = new ArrayList<>(length * length);

        int cX = origin.getX();
        int cZ = origin.getZ();

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                System.out.println(cX + x+">"+cZ + z);
                chunks.add(new ChunkCoord(cX + x, cZ + z));
            }
        }
        return chunks;

    }

    protected static int a(int i, boolean flag, boolean flag1) {
        int j = i * 2 * 16 * 16 * 16;
        int k = i * 16 * 16 * 16 / 2;
        int l = flag ? i * 16 * 16 * 16 / 2 : 0;
        int i1 = flag1 ? 256 : 0;
        return j + k + l + i1;
    }


    public static PacketPlayOutMapChunk createPacket(net.minecraft.server.v1_8_R3.Chunk chunk, ChunkSection[] sections, boolean flag, int i) {
        Reflections reflections = new Reflections();
        PacketPlayOutMapChunk packet = new PacketPlayOutMapChunk();
        reflections.setValue(packet, "a", chunk.locX);
        reflections.setValue(packet, "b", chunk.locZ);
        reflections.setValue(packet, "d", flag);
        reflections.setValue(packet, "c", a(sections,chunk.getBiomeIndex().clone(), flag, !chunk.getWorld().worldProvider.o(), i));
        return packet;
    }
    public static PacketPlayOutMapChunk.ChunkMap a(ChunkSection[] chunkSections, byte[] biomeIndex,boolean flag, boolean flag1, int i) {
        ChunkSection[] achunksection = chunkSections;
        PacketPlayOutMapChunk.ChunkMap packetplayoutmapchunk_chunkmap = new PacketPlayOutMapChunk.ChunkMap();
        ArrayList arraylist = Lists.newArrayList();

        int j;
        for(j = 0; j < achunksection.length; ++j) {
            ChunkSection chunksection = achunksection[j];
            if (chunksection != null && (!flag || !chunksection.a()) && (i & 1 << j) != 0) {
                packetplayoutmapchunk_chunkmap.b |= 1 << j;
                arraylist.add(chunksection);
            }
        }

        packetplayoutmapchunk_chunkmap.a = new byte[a(Integer.bitCount(packetplayoutmapchunk_chunkmap.b), flag1, flag)];
        j = 0;
        Iterator iterator = arraylist.iterator();

        ChunkSection chunksection1;
        while(iterator.hasNext()) {
            chunksection1 = (ChunkSection)iterator.next();
            char[] achar = chunksection1.getIdArray();
            char[] achar1 = achar;
            int k = achar.length;

            for(int l = 0; l < k; ++l) {
                char c0 = achar1[l];
                packetplayoutmapchunk_chunkmap.a[j++] = (byte)(c0 & 255);
                packetplayoutmapchunk_chunkmap.a[j++] = (byte)(c0 >> 8 & 255);
            }
        }

        for(iterator = arraylist.iterator(); iterator.hasNext(); j = a(chunksection1.getEmittedLightArray().a(), packetplayoutmapchunk_chunkmap.a, j)) {
            chunksection1 = (ChunkSection)iterator.next();
        }

        if (flag1) {
            for(iterator = arraylist.iterator(); iterator.hasNext(); j = a(chunksection1.getSkyLightArray().a(), packetplayoutmapchunk_chunkmap.a, j)) {
                chunksection1 = (ChunkSection)iterator.next();
            }
        }

        if (flag) {
            a(biomeIndex, packetplayoutmapchunk_chunkmap.a, j);
        }

        return packetplayoutmapchunk_chunkmap;
    }

    private static int a(byte[] abyte, byte[] abyte1, int i) {
        System.arraycopy(abyte, 0, abyte1, i, abyte.length);
        return i + abyte.length;
    }

    public static class ChunkMap {
        public byte[] a;
        public int b;

        public ChunkMap() {
        }
    }
}
