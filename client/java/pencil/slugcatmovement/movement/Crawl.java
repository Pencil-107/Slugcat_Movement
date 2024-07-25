package pencil.slugcatmovement.movement;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import pencil.slugcatmovement.RainworldMovement;

public class Crawl {
    public static float crawlSpeed = 3f;


    // Currently Broken
    public static void tick(MinecraftClient client) {
        if (client.options.sneakKey.isPressed()) {
            RainworldMovement.makeSwim(client.player, crawlSpeed);
            client.player.setMovementSpeed(crawlSpeed);
        }
    }
}
