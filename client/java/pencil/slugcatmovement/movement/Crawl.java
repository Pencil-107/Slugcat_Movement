package pencil.slugcatmovement.movement;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EntityPose;
import net.minecraft.util.math.Box;
import org.lwjgl.glfw.GLFW;
import pencil.slugcatmovement.RainworldMovement;

public class Crawl {
    public static float crawlSpeed = 3f;


    // Currently Broken Crawl Mechanic
    //public static void tick(MinecraftClient client) {
       // RainworldMovement.makeCrawl(client.player, crawlSpeed);
       // client.player.setMovementSpeed(crawlSpeed);
       // client.player.setPose(EntityPose.SWIMMING);
       // client.player.setSwimming(true);
    //}
}
