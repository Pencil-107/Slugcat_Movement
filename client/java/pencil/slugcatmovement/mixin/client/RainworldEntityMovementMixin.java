package pencil.slugcatmovement.mixin.client;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pencil.slugcatmovement.RainworldMovementClient;

@Mixin(Entity.class)
public abstract class RainworldEntityMovementMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        if (RainworldMovementClient.getCrawlingClient()) {
            RainworldMovementClient.setCrawlingClient().setPose(EntityPose.SWIMMING);
            RainworldMovementClient.setCrawlingClient().setBoundingBox(Box.of(RainworldMovementClient.setCrawlingClient().getBoundingBox().getCenter(), 0.6f, 0.6f, 0.6f));
        }
    }
}