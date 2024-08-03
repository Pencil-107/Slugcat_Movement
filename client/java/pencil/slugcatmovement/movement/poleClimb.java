package pencil.slugcatmovement.movement;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import pencil.slugcatmovement.RainworldMovementClient;
import pencil.slugcatmovement.init.BlockInit;

public class poleClimb {
    public static void tick(MinecraftClient client, Boolean vertical, KeyBinding grabKey, BlockPos pole) {
        if (vertical && RainworldMovementClient.climbing && !RainworldMovementClient.climbJumping) {
            client.player.setVelocity(0, 0, 0); // slowdown player in any direction away from the pole direction

            // speedup player along pole axis based on movement direction
            if (pole != null) {
                if (client.options.forwardKey.isPressed() && client.player.getPitch() < 0) {
                    client.player.setVelocity(0, 0.15, 0);
                }
                if (client.options.forwardKey.isPressed() && client.player.getPitch() > 0) {
                    client.player.setVelocity(0, -0.15, 0);
                }
            }
        }

        // Pole refrence reset
        if (!RainworldMovementClient.verticalClimb || client.player.isOnGround()) {
            RainworldMovementClient.climbJumping = false;
        }

        // if player leaves the poles range stop them from climbing
        if (client.player.getBlockStateAtPos().getBlock() != BlockInit.POLE_Y && RainworldMovementClient.climbing) {
            RainworldMovementClient.verticalClimb = false;
            RainworldMovementClient.polePos = null;
        }

        // Pole Jump
        if (client.options.jumpKey.isPressed() && RainworldMovementClient.climbing && RainworldMovementClient.verticalClimb && pole != null) {
            RainworldMovementClient.climbJumping = true;
            client.player.setVelocity(((pole.getX() +.5)-client.player.getPos().getX())*-.5, 0.6, ((pole.getZ() +.5)-client.player.getPos().getZ())*-.5);
            RainworldMovementClient.climbing = false;
        }

        // climb onto XZ poles
        if (RainworldMovementClient.climbing && grabKey.isPressed() && !RainworldMovementClient.verticalClimb && pole != null) {
            grabKey.setPressed(false);
            if (client.player.getBlockPos().getY() < pole.getY()) {
                client.player.setVelocity(client.player.getVelocity().getX(), 0.6, client.player.getVelocity().getZ());
            }
        }
    }
}
