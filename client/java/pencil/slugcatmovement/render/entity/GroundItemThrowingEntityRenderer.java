package pencil.slugcatmovement.render.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import pencil.slugcatmovement.entity.GroundItemEntity;
import pencil.slugcatmovement.entity.GroundItemThrowingEntity;

public class GroundItemThrowingEntityRenderer extends EntityRenderer<GroundItemThrowingEntity> {

    private final ItemRenderer itemRenderer;
    private final EntityRendererFactory.Context cntx;

    public GroundItemThrowingEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.cntx = context;
    }

    public void render(GroundItemThrowingEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        ItemStack itemStack;

        if (entity.heldItem != null && entity.heldItem.getItem() != Items.AIR) {
            itemStack = entity.heldItem;
            BakedModel bakedModel = this.itemRenderer.getModel(itemStack, entity.getWorld(), (LivingEntity)null, entity.getId());
            this.itemRenderer.renderItem(itemStack, ModelTransformationMode.GROUND, false, matrixStack, vertexConsumerProvider, i, OverlayTexture.DEFAULT_UV, bakedModel);
        }
    }

    @Override
    public Identifier getTexture(GroundItemThrowingEntity entity) {
        return new Identifier("slugcatmovement", "textures/item/"+entity.heldItem.getItem().toString()+".png");
    }
}