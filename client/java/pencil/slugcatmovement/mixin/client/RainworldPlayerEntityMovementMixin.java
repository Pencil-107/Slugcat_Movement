package pencil.slugcatmovement.mixin.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pencil.slugcatmovement.RainworldMovementClient;

@Mixin(LivingEntity.class)
public abstract class RainworldPlayerEntityMovementMixin extends Entity {

    @Shadow public abstract @Nullable EntityAttributeInstance getAttributeInstance(EntityAttribute attribute);


    @Shadow public abstract AttributeContainer getAttributes();

    public RainworldPlayerEntityMovementMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (this.isPlayer()) {
            this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.2);
            if (RainworldMovementClient.getCrawlingClient()) {
                this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.4);
            }
        }
    }

    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    private void jump(CallbackInfo ci) {
        if (this.isPlayer()) {
            if (RainworldMovementClient.getCrawlingClient() && RainworldMovementClient.jumpHeld) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "setSprinting", at = @At("HEAD"), cancellable = true)
    private void sprint(CallbackInfo ci) {
        if (this.isPlayer()) {
            ci.cancel();
        }
    }
}
