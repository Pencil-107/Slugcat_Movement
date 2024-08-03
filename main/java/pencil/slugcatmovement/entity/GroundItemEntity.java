package pencil.slugcatmovement.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.Random;

public class GroundItemEntity extends PathAwareEntity {

    public static ItemStack heldItem = new ItemStack(Items.AIR);

    public GroundItemEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        setStackInHand(this.getActiveHand(), heldItem);
    }


    @Override
    public void setStackInHand(Hand hand, ItemStack stack) {
        super.setStackInHand(hand, stack);
    }

    @Override
    public AttributeContainer getAttributes() {
        return new AttributeContainer(createMobAttributes().build());
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        this.setBodyYaw(new Random().nextFloat());
    }

    @Override
    protected void pushAway(Entity entity) {
        super.pushAway(this);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (player.getMainHandStack().isEmpty()) {
            player.equipStack(EquipmentSlot.MAINHAND, this.getMainHandStack());
            this.remove(RemovalReason.KILLED);
        } else if (!player.getMainHandStack().isEmpty() && player.getOffHandStack().isEmpty()) {
            player.equipStack(EquipmentSlot.OFFHAND, this.getMainHandStack());
            this.remove(RemovalReason.KILLED);
        }
        return super.interactMob(player, hand);
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {

    }
}