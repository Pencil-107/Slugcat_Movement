package pencil.slugcatmovement;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pencil.slugcatmovement.init.BlockInit;
import pencil.slugcatmovement.init.ItemInit;

import java.util.UUID;

public class RainworldMovement implements ModInitializer{
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("slugcatmovement");
	public static final String MOD_ID = "slugcatmovement";

	public static final Identifier CRAWL_PACKET_ID = Identifier.of("tutorial", "crawl_player");

	@Override
	public void onInitialize() {
		ItemInit.load();
		BlockInit.load();
		LOGGER.info("Hello Fabric world!");

		ServerSidePacketRegistry.INSTANCE.register(CRAWL_PACKET_ID, (packetContext, attachedData) -> {
			Boolean moving = attachedData.readBoolean(); // Get the speed set earlier in the client
			packetContext.getTaskQueue().execute(() -> { // Execute on the main thread
				if(packetContext.getPlayer() != null){
					packetContext.getPlayer().setPose(EntityPose.SWIMMING);
					packetContext.getPlayer().setBoundingBox(Box.of(packetContext.getPlayer().getBoundingBox().getCenter(), 0.6f, 0.6f, 0.6f));
				}

			});
		});
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}

}