package pencil.slugcatmovement;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.world.StructureSpawns;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.entity.player.*;

public class RainworldMovement implements ModInitializer{
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("slugcatmovement");
	public static final String MOD_ID = "slugcatmovement";

	public static final Identifier CRAWL_PACKET_ID = Identifier.of("tutorial", "crawl_player");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ServerTickEvents.START_SERVER_TICK.register(server1 -> { // Runs every Server Tick
			ServerPlayNetworking.registerGlobalReceiver(CRAWL_PACKET_ID, ((server, player1, handler, buf, responseSender) -> {
				server.execute(() -> { // Set Collision
					player1.setBoundingBox(Box.of(player1.getBoundingBox().getCenter(), buf.readFloat(), buf.readFloat(), buf.readFloat()));
					player1.setPose(EntityPose.SWIMMING);
					player1.setBoundingBox(Box.of(player1.getBoundingBox().getCenter(), 0.6, 0.6, 0.6));
				});
			}));
		});
		LOGGER.info("Hello Fabric world!");
	}

	public static void makeCrawl(PlayerEntity player, Float speed) {
		player.setMovementSpeed(speed);
		//player.setPose(EntityPose.SWIMMING);
		//player.setBoundingBox(Box.of(player.getBoundingBox().getCenter(), 0.6, 0.6, 0.6));
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}

}