package pencil.slugcatmovement;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.EntityPose;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pencil.slugcatmovement.entity.GroundItemThrowingEntity;
import pencil.slugcatmovement.init.BlockInit;
import pencil.slugcatmovement.init.EntityTypeInit;
import pencil.slugcatmovement.init.ItemInit;
import pencil.slugcatmovement.init.itemGroupInit;

public class RainworldMovement implements ModInitializer{
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("slugcatmovement");
	public static final String MOD_ID = "slugcatmovement";

	public static final Identifier SLUGCAT_MISC_PACKET_ID = Identifier.of("slugcatmovement", "slugcat_misc_player");
	public static final Identifier CLIMB_POLE_PACKET_ID = Identifier.of("slugcatmovement", "climb_pole_player");
	public static final Identifier CRAWL_PACKET_ID = Identifier.of("slugcatmovement", "crawl_player");
	public static final Identifier DROP_PACKET_ID = Identifier.of("slugcatmovement", "drop_item");

	public static boolean Dropped = false;

	@Override
	public void onInitialize() {
		ItemInit.load();
		itemGroupInit.load();
		BlockInit.load();
		EntityTypeInit.load();
		LOGGER.info("Hello Fabric world!");

		// Crawling packet receiver
		ServerSidePacketRegistry.INSTANCE.register(CRAWL_PACKET_ID, (packetContext, attachedData) -> {
			packetContext.getTaskQueue().execute(() -> { // Execute on the main thread
				if(packetContext.getPlayer() != null){
					packetContext.getPlayer().setPose(EntityPose.SWIMMING);
					packetContext.getPlayer().setBoundingBox(Box.of(packetContext.getPlayer().getBoundingBox().getCenter(), 0.6f, 0.6f, 0.6f));
					packetContext.getPlayer().jump();
				}

			});
		});

		// Drop packet receiver
		ServerSidePacketRegistry.INSTANCE.register(DROP_PACKET_ID, (packetContext, attachedData) -> {
			ItemStack stack = attachedData.readItemStack();
			boolean leftHanded = attachedData.readBoolean();
			Dropped = false;
			GroundItemThrowingEntity.heldItem = ItemStack.EMPTY;
			packetContext.getTaskQueue().execute(() -> { // Execute on the main thread
				if(packetContext.getPlayer() != null && stack != null && !Dropped){

					// Spawn Entity
					GroundItemThrowingEntity.heldItem = stack;
					GroundItemThrowingEntity dropEntity = new GroundItemThrowingEntity(EntityTypeInit.GROUND_ITEM_THROWING, packetContext.getPlayer().getWorld());
					if (packetContext.getPlayer().getPose() == EntityPose.SWIMMING) {
						dropEntity.setPos(packetContext.getPlayer().getPos().getX(), packetContext.getPlayer().getPos().getY(), packetContext.getPlayer().getPos().getZ());
					} else {
						dropEntity.setPos(packetContext.getPlayer().getPos().getX(), packetContext.getPlayer().getPos().getY()+1, packetContext.getPlayer().getPos().getZ());
					}
					dropEntity.setVelocity(packetContext.getPlayer(), packetContext.getPlayer().getPitch(), packetContext.getPlayer().getYaw(), 0.0F, 2, 1.0F);
					packetContext.getPlayer().getWorld().spawnEntity(dropEntity);
					if (!leftHanded) {
						packetContext.getPlayer().getMainHandStack().setCount(0);
					} else if (leftHanded) {
						packetContext.getPlayer().getOffHandStack().setCount(0);
					}
					Dropped = true;
				}

			});
		});

		// Misc packet receiver
		// Runs once
		ServerSidePacketRegistry.INSTANCE.register(SLUGCAT_MISC_PACKET_ID, (packetContext, attachedData) -> {
			packetContext.getTaskQueue().execute(() -> { // Execute on the main thread
				if(packetContext.getPlayer() != null){
					CommandManager commandManager = packetContext.getPlayer().getServer().getCommandManager();
					commandManager.executeWithPrefix(packetContext.getPlayer().getCommandSource(), "/gamerule fallDamage false");
					commandManager.executeWithPrefix(packetContext.getPlayer().getCommandSource(), "/gamerule sendCommandFeedback false");
				}
			});
		});
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}

}