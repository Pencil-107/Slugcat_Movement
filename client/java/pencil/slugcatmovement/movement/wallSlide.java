package pencil.slugcatmovement.movement;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;

public class wallSlide {
    public static void tick(MinecraftClient client) { // Runs every tick

        if (client.player != null){ // makes sure the client is actually there

            if (!client.player.isOnGround()) { // check to make sure the player isnt touching the ground
                if (client.player.getVelocity().getY() <= 0) { // Check to see if player is falling

                    if (client.player.getHorizontalFacing() == Direction.NORTH){ // Direction
                        BlockHitResult hit = client.world.raycast( // Raycast Shooter
                                new RaycastContext(
                                        // raycast shoots thin box from center of player torso in the direction of Second Corner
                                        client.player.getPos().subtract(0.1, -.9, 0.1), // First Corner
                                        client.player.getPos().add(0.1, 1, -0.5), // Second Corner
                                        RaycastContext.ShapeType.COLLIDER, // Shape Type
                                        RaycastContext.FluidHandling.NONE, client.player)); // extra Variables
                        if (hit.getType() == HitResult.Type.BLOCK) { // check if the detected thing is a block
                            BlockHitResult blockHit = (BlockHitResult) hit; // sets detected block
                            if (blockHit.getSide() != Direction.UP || blockHit.getSide() != Direction.DOWN) { // checks for side of block
                                client.player.setMovementSpeed(0.07f); // movement speed modifier
                                client.player.setVelocity(client.player.getVelocity().getX(), -0.06, client.player.getVelocity().getX() ); // Slowing down fall with velocity
                            }
                        }
                    }
                    if (client.player.getHorizontalFacing() == Direction.SOUTH) {
                        BlockHitResult hit = client.world.raycast(
                                new RaycastContext(
                                        client.player.getPos().subtract(0.1, -.9, .1),
                                        client.player.getPos().add(0.1, 1, 0.5),
                                        RaycastContext.ShapeType.COLLIDER,
                                        RaycastContext.FluidHandling.NONE, client.player));
                        if (hit.getType() == HitResult.Type.BLOCK) {
                            BlockHitResult blockHit = (BlockHitResult) hit;
                            if (blockHit.getSide() != Direction.UP || blockHit.getSide() != Direction.DOWN) {
                                client.player.setMovementSpeed(0.07f);
                                client.player.fallDistance = 0;
                                client.player.setVelocity(client.player.getVelocity().getX(), -0.06, client.player.getVelocity().getX() );
                                client.player.collidedSoftly = true;
                            }
                        }
                    }
                    if (client.player.getHorizontalFacing() == Direction.EAST){
                        BlockHitResult hit = client.world.raycast(
                                new RaycastContext(
                                        client.player.getPos().subtract(0.1, -0.9, 0.1),
                                        client.player.getPos().add(0.5, 1, 0.1),
                                        RaycastContext.ShapeType.COLLIDER,
                                        RaycastContext.FluidHandling.NONE, client.player));
                        if (hit.getType() == HitResult.Type.BLOCK) {
                            BlockHitResult blockHit = (BlockHitResult) hit;
                            if (blockHit.getSide() != Direction.UP || blockHit.getSide() != Direction.DOWN) {
                                client.player.setMovementSpeed(0.07f);
                                client.player.fallDistance = 0;
                                client.player.setVelocity(client.player.getVelocity().getX(), -0.06, client.player.getVelocity().getX() );
                                client.player.collidedSoftly = true;
                            }
                        }
                    }
                    if (client.player.getHorizontalFacing() == Direction.WEST) {
                        BlockHitResult hit = client.world.raycast(
                                new RaycastContext(
                                        client.player.getPos().subtract(0.1, -0.9, 0.1),
                                        client.player.getPos().add(-0.5, 1, 0.1),
                                        RaycastContext.ShapeType.COLLIDER,
                                        RaycastContext.FluidHandling.NONE, client.player));
                        if (hit.getType() == HitResult.Type.BLOCK) {
                            BlockHitResult blockHit = (BlockHitResult) hit;
                            if (blockHit.getSide() != Direction.UP || blockHit.getSide() != Direction.DOWN) {
                                client.player.setMovementSpeed(0.07f);
                                client.player.fallDistance = 0;
                                client.player.setVelocity(client.player.getVelocity().getX(), -0.06, client.player.getVelocity().getX() );
                                client.player.collidedSoftly = true;
                            }
                        }
                    }
                }
            }
        }
    }
}
