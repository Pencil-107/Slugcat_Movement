package pencil.slugcatmovement;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;
import org.lwjgl.glfw.GLFW;
import pencil.slugcatmovement.entity.GroundItemEntity;
import pencil.slugcatmovement.entity.GroundItemThrowingEntity;
import pencil.slugcatmovement.init.EntityTypeInit;
import pencil.slugcatmovement.render.entity.GroundItemEntityRenderer;
import pencil.slugcatmovement.render.entity.GroundItemThrowingEntityRenderer;
import pencil.slugcatmovement.render.entity.SpearEntityModel;
import pencil.slugcatmovement.render.entity.SpearEntityRenderer;

public class RainworldMovementClient implements ClientModInitializer {

	public static final EntityModelLayer MODEL_SPEAR_LAYER = new EntityModelLayer(new Identifier("slugcatmovement", "spear"), "main");

	// Food variables
	public static int foodLevel = 0;
	public static int foodRequired = 8;
	public static int lastFoodLevel = 0;
	public static int maxFoodLevel = 14;
	public static boolean eating = false;
	public static boolean starving = false;

	public static boolean cycling = false;

	// has slugcat survived this cycle so far
	public static boolean survived = true;

	// Karma variables
	public static int karmaLevel = 0;
	public static int lastKarmaLevel = 0;
	public static int maxKarmaLevel = 9;
	public static boolean karmaShielded = true;

	// stored item variables
	public static ItemStack storedItem = ItemStack.EMPTY;
	public static float storeTime = 40;
	public static float storeTimeMax = 40;
	public static float lastTime = storeTime;

	// A variable for time held
	public static float timeHeld = 0;

	private static KeyBinding crawlKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
    "key.slugcatmovement.crawl", // The translation key of the keybinding's name
	InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
	GLFW.GLFW_KEY_LEFT_CONTROL, // The keycode of the key
    "category.slugcatmovement.rainworld" // The translation key of the keybinding's category.
	));

	private static KeyBinding grabKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.slugcatmovement.grab", // The translation key of the keybinding's name
			InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
			GLFW.GLFW_KEY_E, // The keycode of the key
			"category.slugcatmovement.rainworld" // The translation key of the keybinding's category.
	));

	private static KeyBinding dropKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.slugcatmovement.drop", // The translation key of the keybinding's name
			InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
			GLFW.GLFW_KEY_Q, // The keycode of the key
			"category.slugcatmovement.rainworld" // The translation key of the keybinding's category.
	));

	public static KeyBinding one = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.slugcatmovement.test1", // The translation key of the keybinding's name
			InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
			GLFW.GLFW_KEY_T, // The keycode of the key
			"category.slugcatmovement.rainworld" // The translation key of the keybinding's category.
	));
	private static KeyBinding two = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.slugcatmovement.test2", // The translation key of the keybinding's name
			InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
			GLFW.GLFW_KEY_Y, // The keycode of the key
			"category.slugcatmovement.rainworld" // The translation key of the keybinding's category.
	));
	private static KeyBinding three = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.slugcatmovement.test3", // The translation key of the keybinding's name
			InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
			GLFW.GLFW_KEY_U, // The keycode of the key
			"category.slugcatmovement.rainworld" // The translation key of the keybinding's category.
	));
	private static KeyBinding enterKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.slugcatmovement.test4", // The translation key of the keybinding's name
			InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
			GLFW.GLFW_KEY_ENTER, // The keycode of the key
			"category.slugcatmovement.rainworld" // The translation key of the keybinding's category.
	));

	public static boolean climbJumping = false; // checks if jumping from poll
	public static boolean climbing = false; // checks if climbing
	public static boolean verticalClimb = false; // checks if climb is vertical
	public static BlockPos polePos = null; // pole position while climbing

	public static boolean miscSet = false; // checks if the nisc packets were sent

	public static PlayerEntity playerEntityCrawl; // player entity for clientside crawling
	public static boolean jumpHeld;

	@Override
	public void onInitializeClient() {

		EntityRendererRegistry.INSTANCE.register(EntityTypeInit.SPEAR, SpearEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(EntityTypeInit.GROUND_ITEM, GroundItemEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(EntityTypeInit.GROUND_ITEM_THROWING, GroundItemThrowingEntityRenderer::new);

		// Entity Models
		EntityModelLayerRegistry.registerModelLayer(MODEL_SPEAR_LAYER, SpearEntityModel::getTexturedModelData);

		ClientTickEvents.START_CLIENT_TICK.register(client -> {

			if (client.player != null) {
				if (one.wasPressed() && karmaLevel < maxKarmaLevel) {
					++karmaLevel;
				}
				if (two.wasPressed() && karmaLevel > 0) {
					--karmaLevel;
				}
				if (three.wasPressed()) {
					//client.setScreen(new KarmaScreen(Text.empty()));
				}
				if (enterKey.wasPressed()) {
					if (cycling) {
						cycling = false;
					} else {
						cycling = true;
					}
				}


				client.player.getInventory().selectedSlot = 0;
				client.player.getHungerManager().setFoodLevel(19);
				if (foodLevel == maxFoodLevel) {
					client.player.getHungerManager().setFoodLevel(20);
				}
			}

			if (client.player != null && grabKey.isPressed()) {
				HitResult hit = client.cameraEntity.raycast(3.5, 0, false);
				if (hit.getType() == HitResult.Type.ENTITY) {
					EntityHitResult entityHitResult = (EntityHitResult) hit;
					if (entityHitResult.getEntity().getType() == EntityTypeInit.GROUND_ITEM) {
						if (client.player.getMainHandStack().isEmpty()) {
							client.player.equipStack(EquipmentSlot.MAINHAND, GroundItemEntity.heldItem);
							entityHitResult.getEntity().remove(Entity.RemovalReason.KILLED);
						} else if (!client.player.getMainHandStack().isEmpty() && client.player.getOffHandStack().isEmpty()) {
							client.player.equipStack(EquipmentSlot.OFFHAND, GroundItemEntity.heldItem);
							entityHitResult.getEntity().remove(Entity.RemovalReason.KILLED);
						}
					}
				}
			}

			if (client.player != null && client.player.isUsingItem() && client.player.getActiveItem().isFood() && foodLevel < maxFoodLevel) {
				eating = true;
			} else {
				eating = false;
			}

			if (client.player != null && client.player.isUsingItem() && client.player.getItemUseTimeLeft() <= 1f && eating  && foodLevel < maxFoodLevel){
				if (client.player.getMainHandStack().getItem().getFoodComponent() != null && eating) {
					if (client.player.getMainHandStack().getItem().getFoodComponent().isSnack()){
						client.player.getHungerManager().setFoodLevel(19);
						++foodLevel;
						client.player.getActiveItem().setCount(0);
						client.player.playSound(SoundEvent.of(Identifier.of("minecraft", "entity.player.burp")), 1, 1);
					} else {
						client.player.getHungerManager().setFoodLevel(19);
						foodLevel = foodLevel + 2;
						client.player.getActiveItem().setCount(0);
						client.player.playSound(SoundEvent.of(Identifier.of("minecraft", "entity.player.burp")), 1, 1);
					}
					eating = false;
				}
				if (client.player.getOffHandStack().getItem().getFoodComponent()  != null && eating) {
					if (client.player.getOffHandStack().getItem().getFoodComponent().isSnack()) {
						client.player.getHungerManager().setFoodLevel(19);
						++foodLevel;
						client.player.getActiveItem().setCount(0);
						client.player.playSound(SoundEvent.of(Identifier.of("minecraft", "entity.player.burp")), 1, 1);
					} else {
						client.player.getHungerManager().setFoodLevel(19);
						foodLevel = foodLevel + 2;
						client.player.getActiveItem().setCount(0);
						client.player.playSound(SoundEvent.of(Identifier.of("minecraft", "entity.player.burp")), 1, 1);
					}
					eating = false;
				}
			}


			client.options.sprintKey.setBoundKey(new KeyBinding(
					"key.slugcatmovement.temp", // The translation key of the keybinding's name
					InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
					GLFW.GLFW_KEY_MINUS, // The keycode of the key
					"category.slugcatmovement.rainworld" // The translation key of the keybinding's category.
			).getDefaultKey());
			client.options.inventoryKey.setBoundKey(new KeyBinding(
					"key.slugcatmovement.temp", // The translation key of the keybinding's name
					InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
					GLFW.GLFW_KEY_MINUS, // The keycode of the key
					"category.slugcatmovement.rainworld" // The translation key of the keybinding's category.
			).getDefaultKey());
			client.options.dropKey.setBoundKey(new KeyBinding(
					"key.slugcatmovement.temp", // The translation key of the keybinding's name
					InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
					GLFW.GLFW_KEY_MINUS, // The keycode of the key
					"category.slugcatmovement.rainworld" // The translation key of the keybinding's category.
			).getDefaultKey());
			client.options.sneakKey.setBoundKey(new KeyBinding(
					"key.slugcatmovement.temp", // The translation key of the keybinding's name
					InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
					GLFW.GLFW_KEY_MINUS, // The keycode of the key
					"category.slugcatmovement.rainworld" // The translation key of the keybinding's category.
			).getDefaultKey());
			client.options.sprintKey.setPressed(false);
			client.options.inventoryKey.setPressed(false);
			client.options.dropKey.setPressed(false);
			client.options.sneakKey.setPressed(false);

			// Walljumping and Wallsliding Initialization
			// Called in START_CLIENT_TICK because otherwise ground checks would break
			if (!verticalClimb && !crawlKey.isPressed()) {
				pencil.slugcatmovement.movement.wallJump.tick(client);
				pencil.slugcatmovement.movement.wallSlide.tick(client);
			}

			if (client.player != null && dropKey.wasPressed() && !client.player.getMainHandStack().isEmpty()) {
				PacketByteBuf buf = PacketByteBufs.create();

				buf.writeItemStack(client.player.getMainHandStack());
				buf.writeBoolean(false);

				ClientSidePacketRegistry.INSTANCE.sendToServer(RainworldMovement.DROP_PACKET_ID, buf);
				client.player.getMainHandStack().setCount(0);
			} else if (client.player != null && dropKey.wasPressed() && !client.player.getOffHandStack().isEmpty() && client.player.getMainHandStack().isEmpty()) {
				PacketByteBuf buf = PacketByteBufs.create();

				buf.writeItemStack(client.player.getOffHandStack());
				buf.writeBoolean(true);

				ClientSidePacketRegistry.INSTANCE.sendToServer(RainworldMovement.DROP_PACKET_ID, buf);
				client.player.getOffHandStack().setCount(0);
			}

			// Crawling Initialization
			if (client.player != null) { // Makes sure client exists

				playerEntityCrawl = MinecraftClient.getInstance().player; // REQUIRED OR GAME WILL CRASH
				if (crawlKey.isPressed()) { // Checks for Crawl key
					client.player.setPose(EntityPose.SWIMMING);
					// Send packet to server to change the crawl state for us
					ClientSidePacketRegistry.INSTANCE.sendToServer(RainworldMovement.CRAWL_PACKET_ID, PacketByteBufs.empty());
					// check for crawl jumping
					pencil.slugcatmovement.movement.crawlJump.tick(client);
				}
			}
		});

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null) {

				if (!miscSet) {
					// Misc Packet Handler
					// disables normal fall damage
					ClientSidePacketRegistry.INSTANCE.sendToServer(RainworldMovement.SLUGCAT_MISC_PACKET_ID, PacketByteBufs.empty());
					miscSet = true;
				}

				if (client.player != null) {
					client.player.getInventory().selectedSlot = 0;
					client.player.getHungerManager().setFoodLevel(19);
					if (foodLevel == maxFoodLevel) {
						client.player.getHungerManager().setFoodLevel(20);
					}
				}

				client.options.sprintKey.setBoundKey(new KeyBinding(
						"key.slugcatmovement.temp", // The translation key of the keybinding's name
						InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
						GLFW.GLFW_KEY_MINUS, // The keycode of the key
						"category.slugcatmovement.rainworld" // The translation key of the keybinding's category.
				).getDefaultKey());

				client.options.inventoryKey.setBoundKey(new KeyBinding(
						"key.slugcatmovement.temp", // The translation key of the keybinding's name
						InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
						GLFW.GLFW_KEY_MINUS, // The keycode of the key
						"category.slugcatmovement.rainworld" // The translation key of the keybinding's category.
				).getDefaultKey());
				client.options.sneakKey.setBoundKey(new KeyBinding(
						"key.slugcatmovement.temp", // The translation key of the keybinding's name
						InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
						GLFW.GLFW_KEY_MINUS, // The keycode of the key
						"category.slugcatmovement.rainworld" // The translation key of the keybinding's category.
				).getDefaultKey());
				client.options.sprintKey.setPressed(false);
				client.options.inventoryKey.setPressed(false);
				client.options.dropKey.setPressed(false);
				client.options.sneakKey.setPressed(false);

				// Fixes Visual Bugs
				playerEntityCrawl = MinecraftClient.getInstance().player;  // REQUIRED OR GAME WILL CRASH

				if (crawlKey.isPressed()) {
					client.player.setPose(EntityPose.SWIMMING);
				}

				// Pole Climbing Initialization
				if (climbing && !crawlKey.isPressed()) {
					pencil.slugcatmovement.movement.poleClimb.tick(client, verticalClimb, grabKey, polePos);
				}

				// Pole Climbing Packet Receiver
				ClientSidePacketRegistry.INSTANCE.register(RainworldMovement.CLIMB_POLE_PACKET_ID,
					(packetContext, attachedData) -> {
						// Get the BlockPos we set on the server
						verticalClimb = attachedData.readBoolean();
						polePos = attachedData.readBlockPos();

						packetContext.getTaskQueue().execute(() -> {
							// start climbing
							climbing = true;
						});
					});
			}
		});
	}

	public static void onHibernated() {
		if (foodLevel >= foodRequired && survived && !starving) {
			foodLevel = foodLevel-foodRequired;
			if (karmaLevel < maxKarmaLevel) {
				++karmaLevel;
			}
			lastFoodLevel = foodLevel;
		} else if (survived && foodLevel < foodRequired && !starving) {
			foodLevel = 0;
			lastFoodLevel = foodLevel;
			starving = true;
		} else if (survived && foodLevel >= maxFoodLevel && starving) {
			foodLevel = 0;
			lastFoodLevel = foodLevel;
			starving = false;
		} else if (survived && foodLevel <= maxFoodLevel && starving) {
			foodLevel = lastFoodLevel;
			if (karmaLevel > 0) {
				--karmaLevel;
			}
			survived = false;
		} else if (!survived) {
			foodLevel = lastFoodLevel;
			if (karmaLevel > 0 && !karmaShielded) {
				--karmaLevel;
			} else {
				karmaShielded = false;
			}
		}
	}

	public static Boolean getCrawlingClient() {
		return crawlKey.isPressed();
	}

	public static PlayerEntity setCrawlingClient() {
		return playerEntityCrawl;
	}

}