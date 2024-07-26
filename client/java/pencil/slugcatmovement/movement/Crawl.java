package pencil.slugcatmovement.movement;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EntityPose;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.profiling.jfr.sample.NetworkIoStatistics;
import net.minecraft.world.World;
import org.lwjgl.glfw.GLFW;
import pencil.slugcatmovement.RainworldMovement;

public class Crawl {
    public static float crawlSpeed = 3f;
    // Currently Broken Crawl Mechanic
    public static void tick(MinecraftClient client) {
        Float playerBox = 0.6f;
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeFloat(playerBox);
        // Iterate over all players tracking a position in the world and send the packet to each player
        assert client.player != null;
        ClientPlayNetworking.send(RainworldMovement.CRAWL_PACKET_ID, buf);
        //RainworldMovement.makeCrawl(client.player, crawlSpeed);
        client.player.setBoundingBox(Box.of(client.player.getBoundingBox().getCenter(), 0.6, 0.6, 0.6));
        client.player.setMovementSpeed(crawlSpeed);
        client.player.setPose(EntityPose.SWIMMING);
    }
}
