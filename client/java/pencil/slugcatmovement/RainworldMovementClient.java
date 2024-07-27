package pencil.slugcatmovement;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import org.lwjgl.glfw.GLFW;

public class RainworldMovementClient implements ClientModInitializer, ModInitializer {

	private static KeyBinding crawlKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
    "key.slugcatmovement.crawl", // The translation key of the keybinding's name
	InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
	GLFW.GLFW_KEY_LEFT_ALT, // The keycode of the key
    "category.slugcatmovement.movement" // The translation key of the keybinding's category.
	));

	public static Boolean walking;

	public static PlayerEntity playerEntityRefrence;

	@Override
	public void onInitializeClient() {
		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			// Walljumping and Wallsliding
			pencil.slugcatmovement.movement.wallJump.tick(client);
			pencil.slugcatmovement.movement.wallSlide.tick(client);

			// Crawling
			if (client.player != null) { // Makes sure client exists
				if (client.options.forwardKey.isPressed() || client.options.leftKey.isPressed() || client.options.rightKey.isPressed() || client.options.backKey.isPressed()) {
					walking = true;
				} else {
					walking = false;
				}

				playerEntityRefrence = MinecraftClient.getInstance().player; // REQUIRED OR GAME WILL CRASH
				if (crawlKey.isPressed()) { // Checks for Crawl key
					client.player.setPose(EntityPose.SWIMMING);
					// Pass the `Crawl Speed` information
					PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
					passedData.writeBoolean(walking);
					// Send packet to server to change the crawl state for us
					ClientSidePacketRegistry.INSTANCE.sendToServer(RainworldMovement.CRAWL_PACKET_ID, passedData);
				}
			}
		});

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			// Fixes Visual Bugs
			if (client.player != null) {
				playerEntityRefrence = MinecraftClient.getInstance().player;  // REQUIRED OR GAME WILL CRASH
				if (crawlKey.isPressed()) {
					client.player.setPose(EntityPose.SWIMMING);
				}
			}
		});
	}

	public static Boolean getCrawlingClient() {
		return crawlKey.isPressed();
	}

	public static PlayerEntity setCrawlingClient() {
		return playerEntityRefrence;
	}

	@Override
	public void onInitialize() {

	}
}