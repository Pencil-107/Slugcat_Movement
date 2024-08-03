package pencil.slugcatmovement.movement;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

public class wallJump {
    public static void tick(MinecraftClient client) { // runs every Tick

        if(client.options.jumpKey.wasPressed()) { // checks for jump input
            if (!client.player.isOnGround()) { // check to make sure the player isnt touching the ground

                if (client.player.getHorizontalFacing() == Direction.NORTH){ // checks direction
                    BlockHitResult hit = client.world.raycast( // Raycast Shooter
                            new RaycastContext(
                                    // raycast shoots thin box from center of player torso in the direction of Second Corner
                                    new Vec3d(client.player.getBoundingBox().minX, client.player.getBoundingBox().minY+0.5, client.player.getBoundingBox().minZ), // First Corner
                                    new Vec3d(client.player.getBoundingBox().maxX, client.player.getBoundingBox().maxY, client.player.getBoundingBox().maxZ + 0.5), // Second Corner
                                    RaycastContext.ShapeType.COLLIDER, // ShapeType
                                    RaycastContext.FluidHandling.NONE, client.player)); // extra Variables
                    if (hit.getType() == HitResult.Type.BLOCK) { // check if the detected thing is a block
                        BlockHitResult blockHit = (BlockHitResult) hit; // sets detected block
                        if (blockHit.getSide() != Direction.UP || blockHit.getSide() != Direction.DOWN) { // checks for side of block
                            Vec3d v3 = new Vec3d(0, .5, -.4); // the Velocity Values
                            client.player.setVelocity(v3); // changes Velocity
                        }
                    }
                }
                if (client.player.getHorizontalFacing() == Direction.SOUTH) {
                    BlockHitResult hit = client.world.raycast(
                            new RaycastContext(
                                    new Vec3d(client.player.getBoundingBox().minX, client.player.getBoundingBox().minY+0.5, client.player.getBoundingBox().minZ - 0.5), // First Corner
                                    new Vec3d(client.player.getBoundingBox().maxX, client.player.getBoundingBox().maxY, client.player.getBoundingBox().maxZ), // Second Corner
                                    RaycastContext.ShapeType.COLLIDER,
                                    RaycastContext.FluidHandling.NONE, client.player));
                    if (hit.getType() == HitResult.Type.BLOCK) {
                        BlockHitResult blockHit = (BlockHitResult) hit;
                        if (blockHit.getSide() != Direction.UP || blockHit.getSide() != Direction.DOWN) {
                            Vec3d v3 = new Vec3d(0, .5, .4);
                            client.player.setVelocity(v3);
                        }
                    }
                }
                if (client.player.getHorizontalFacing() == Direction.EAST){
                    BlockHitResult hit = client.world.raycast(
                            new RaycastContext(
                                    new Vec3d(client.player.getBoundingBox().minX - 0.5, client.player.getBoundingBox().minY+0.5, client.player.getBoundingBox().minZ), // First Corner
                                    new Vec3d(client.player.getBoundingBox().maxX, client.player.getBoundingBox().maxY, client.player.getBoundingBox().maxZ), // Second Corner
                                    RaycastContext.ShapeType.COLLIDER,
                                    RaycastContext.FluidHandling.NONE, client.player));
                    if (hit.getType() == HitResult.Type.BLOCK) {
                        BlockHitResult blockHit = (BlockHitResult) hit;
                        if (blockHit.getSide() != Direction.UP || blockHit.getSide() != Direction.DOWN) {
                            Vec3d v3 = new Vec3d(.4, .5, 0);
                            client.player.setVelocity(v3);
                        }
                    }
                }
                if (client.player.getHorizontalFacing() == Direction.WEST) {
                    BlockHitResult hit = client.world.raycast(
                            new RaycastContext(
                                    new Vec3d(client.player.getBoundingBox().minX, client.player.getBoundingBox().minY+0.5, client.player.getBoundingBox().minZ), // First Corner
                                    new Vec3d(client.player.getBoundingBox().maxX + 0.5, client.player.getBoundingBox().maxY, client.player.getBoundingBox().maxZ), // Second Corner
                                    RaycastContext.ShapeType.COLLIDER,
                                    RaycastContext.FluidHandling.NONE, client.player));
                    if (hit.getType() == HitResult.Type.BLOCK) {
                        BlockHitResult blockHit = (BlockHitResult) hit;
                        if (blockHit.getSide() != Direction.UP || blockHit.getSide() != Direction.DOWN) {
                            Vec3d v3 = new Vec3d(-.4, .5, 0);
                            client.player.setVelocity(v3);
                        }
                    }
                }
            }
        }
    }

}
