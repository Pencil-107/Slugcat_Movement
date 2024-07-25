package pencil.slugcatmovement;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.entity.player.*;

public class RainworldMovement implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("slugcatmovement");
	public static final String MOD_ID = "slugcatmovement";

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.


		LOGGER.info("Hello Fabric world!");
	}

	public static void  makeSwim(PlayerEntity player, Float speed) {
		player.setMovementSpeed(speed);
		player.setPose(EntityPose.SWIMMING);

	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}