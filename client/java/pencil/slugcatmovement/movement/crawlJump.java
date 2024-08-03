package pencil.slugcatmovement.movement;

import net.minecraft.client.MinecraftClient;
import pencil.slugcatmovement.RainworldMovementClient;

public class crawlJump {
    public static float heldTime = 60;
    public static final float heldTimeMax = 60;
    public static boolean pressed = false;
    public static float crawlJumpMultiplier = 1f;

    public static void tick(MinecraftClient client) {
        if (client.options.jumpKey.isPressed() && !client.options.forwardKey.isPressed()) {
            if (heldTime > 0) {
                --heldTime;
            }
            pressed = true;
            RainworldMovementClient.jumpHeld = true;
        }
        if (!client.options.jumpKey.isPressed() && pressed == true && !client.options.forwardKey.isPressed() && client.player.isOnGround()) {
            if (heldTime == 0) {
                client.player.addVelocity(client.player.getRotationVector().getX()* crawlJumpMultiplier, 0.6, client.player.getRotationVector().getZ()* crawlJumpMultiplier);
                RainworldMovementClient.jumpHeld = false;
                heldTime = heldTimeMax;
                pressed = false;
            } else if (!client.options.forwardKey.isPressed() && client.player.isOnGround()){
                RainworldMovementClient.jumpHeld = false;
                client.player.addVelocity(0, 0.6, 0);
                heldTime = heldTimeMax;
                pressed = false;
            }
        }
    }
}
