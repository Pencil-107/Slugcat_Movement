package pencil.slugcatmovement.block;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import pencil.slugcatmovement.RainworldMovement;
import java.util.stream.Stream;

public class PoleY extends Block {

    public PoleY(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity.isPlayer()) {
            if (!world.isClient) {

                // Pass the `BlockPos` information and pass whether the pole is in Y axis
                PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                passedData.writeBoolean(true); // Is pole in y axis?
                passedData.writeBlockPos(pos); // Block pos

                // send the packet to the player
                ServerSidePacketRegistry.INSTANCE.sendToPlayer((ServerPlayerEntity) entity, RainworldMovement.CLIMB_POLE_PACKET_ID,passedData);
            }
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.4f, 0f, 0.4f, 0.6f, 1.0f, 0.6f);
    }
}
