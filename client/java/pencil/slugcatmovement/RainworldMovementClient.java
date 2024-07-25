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
    "category.slugcatmovement.slugcatmovement" // The translation key of the keybinding's category.
	));

	public static float crawlSpeed;

	@Override
	public void onInitializeClient() {

		crawlSpeed = 3f;

		ClientTickEvents.START_CLIENT_TICK.register(client -> {
					if(client.options.jumpKey.wasPressed()) {
						if (!client.player.isOnGround()) {
							if (client.player.getHorizontalFacing() == Direction.NORTH){
								BlockHitResult hit = client.world.raycast(new RaycastContext(client.player.getPos().subtract(0.1, -.9, 0.1), client.player.getPos().add(0.1, 1, 0.5), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, client.player));
								//HitResult hit = client.player.raycast(-.35, 0, false);
								if (hit.getType() == HitResult.Type.BLOCK) {
									BlockHitResult blockHit = (BlockHitResult) hit;
									if (blockHit.getSide() != Direction.UP || blockHit.getSide() != Direction.DOWN) {
										Vec3d v3 = new Vec3d(0, .5, -.4);
										client.player.setVelocity(v3);
									}
								}
							}
							if (client.player.getHorizontalFacing() == Direction.SOUTH) {
								BlockHitResult hit = client.world.raycast(new RaycastContext(client.player.getPos().subtract(0.1, -.9, 0.1), client.player.getPos().add(0.1, 1, -0.5), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, client.player));
								//HitResult hit = client.player.raycast(-.35, 0, false);
								if (hit.getType() == HitResult.Type.BLOCK) {
									BlockHitResult blockHit = (BlockHitResult) hit;
									if (blockHit.getSide() != Direction.UP || blockHit.getSide() != Direction.DOWN) {
										Vec3d v3 = new Vec3d(0, .5, .4);
										client.player.setVelocity(v3);
									}
								}
							}
							if (client.player.getHorizontalFacing() == Direction.EAST){
								BlockHitResult hit = client.world.raycast(new RaycastContext(client.player.getPos().subtract(0.1, -.9, 0.1), client.player.getPos().add(-0.5, 1, 0.1), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, client.player));
								//HitResult hit = client.player.raycast(-.35, 0, false);
								if (hit.getType() == HitResult.Type.BLOCK) {
									BlockHitResult blockHit = (BlockHitResult) hit;
									if (blockHit.getSide() != Direction.UP || blockHit.getSide() != Direction.DOWN) {
										Vec3d v3 = new Vec3d(.4, .5, 0);
										client.player.setVelocity(v3);
									}
								}
							}
							if (client.player.getHorizontalFacing() == Direction.WEST) {
								BlockHitResult hit = client.world.raycast(new RaycastContext(client.player.getPos().subtract(.1, -.9, 0.1), client.player.getPos().add(0.5, 1, .1), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, client.player));
								//HitResult hit = client.player.raycast(-.35, 0, false);
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
				if (client.player != null){
					if (!client.player.isOnGround()) {
						if (client.player.getVelocity().getY() <= 0) {
							if (client.player.getHorizontalFacing() == Direction.NORTH){
								BlockHitResult hit = client.world.raycast(new RaycastContext(client.player.getPos().subtract(0.1, -.9, 0.1), client.player.getPos().add(0.1, 1, -0.5), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, client.player));
								//HitResult hit = client.player.raycast(-.35, 0, false);
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
							if (client.player.getHorizontalFacing() == Direction.SOUTH) {
								BlockHitResult hit = client.world.raycast(new RaycastContext(client.player.getPos().subtract(0.1, -.9, .1), client.player.getPos().add(0.1, 1, 0.5), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, client.player));
								//HitResult hit = client.player.raycast(-.35, 0, false);
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
								BlockHitResult hit = client.world.raycast(new RaycastContext(client.player.getPos().subtract(0.1, -0.9, 0.1), client.player.getPos().add(0.5, 1, 0.1), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, client.player));
								//HitResult hit = client.player.raycast(-.35, 0, false);
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
								BlockHitResult hit = client.world.raycast(new RaycastContext(client.player.getPos().subtract(0.1, -0.9, 0.1), client.player.getPos().add(-0.5, 1, 0.1), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, client.player));
								//HitResult hit = client.player.raycast(-.35, 0, false);
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
			if (crawlKey.isPressed()) {
				RainworldMovement.makeSwim(client.player, crawlSpeed);
				client.player.setMovementSpeed(crawlSpeed);
			}
		});

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (crawlKey.isPressed()) {
				RainworldMovement.makeSwim(client.player, crawlSpeed);
				client.player.setMovementSpeed(crawlSpeed);
			}
		});
	}
}