package pencil.slugcatmovement.block;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import pencil.slugcatmovement.RainworldMovement;

import java.util.stream.Stream;

public class PoleX extends Block {

    public PoleX(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity.isPlayer()){
            if (!world.isClient) {

                // Obtain all players watching this area
                Stream<PlayerEntity> watchingPlayers = PlayerStream.watching(world,pos);

                // Pass the `BlockPos` information and pass whether the pole is in Y axis
                PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                passedData.writeBoolean(false); // Is pole in y axis?
                passedData.writeBlockPos(pos); // Block pos

                // send the packet to all the players
                watchingPlayers.forEach(playerBuf ->
                        ServerSidePacketRegistry.INSTANCE.sendToPlayer(playerBuf, RainworldMovement.CLIMB_POLE_PACKET_ID,passedData));
            }
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.1f, .5f, 0.4f, .9f, 0.7f, 0.6f);
    }
}
