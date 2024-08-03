package pencil.slugcatmovement.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import pencil.slugcatmovement.init.EntityTypeInit;

import java.util.Random;

public class GroundItemThrowingEntity extends PersistentProjectileEntity {

    public static ItemStack heldItem = new ItemStack(Items.AIR);
    private static ItemStack storedItem = new ItemStack(Items.AIR);

    public GroundItemThrowingEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        GroundItemEntity.heldItem = storedItem;
        GroundItemEntity groundItemEntity = new GroundItemEntity(EntityTypeInit.GROUND_ITEM, this.getWorld());
        groundItemEntity.setPos(this.getBlockPos().getX()+0.5, this.getBlockPos().getY()+0.5, this.getBlockPos().getZ()+0.5);
        groundItemEntity.addVelocity(0, 1, 0);
        this.getWorld().spawnEntity(groundItemEntity);
        this.remove(RemovalReason.KILLED);
        super.onBlockHit(blockHitResult);
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        this.storedItem = heldItem;
        this.setBodyYaw(new Random().nextFloat());
    }

    @Override
    public boolean collidesWith(Entity other) {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
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
    protected ItemStack asItemStack() {
        return null;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {

    }
}
