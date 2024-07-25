package pencil.slugcatmovement;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EntityPose;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.lwjgl.glfw.GLFW;

public class RainworldMovementClient implements ClientModInitializer {

	private static KeyBinding crawlKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
    "key.slugcatmovement.crawl", // The translation key of the keybinding's name
	InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
	GLFW.GLFW_KEY_LEFT_ALT, // The keycode of the key
    "category.slugcatmovement.movement" // The translation key of the keybinding's category.
	));

	@Override
	public void onInitializeClient() {

		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			pencil.slugcatmovement.movement.wallJump.tick(client);
			pencil.slugcatmovement.movement.wallSlide.tick(client);
		});

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (crawlKey.isPressed()) {
				// pencil.slugcatmovement.movement.Crawl.tick(client);
			}
		});
	}
}