package pencil.slugcatmovement.render.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.Arm;

public class GroundItemEntityModel <T extends ItemEntity> extends AnimalModel<T> implements ModelWithArms {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    private final ModelPart root;
    private final ModelPart rightArm;

    public GroundItemEntityModel(ModelPart root) {
        super( true, 16.0F, 0.0F, 2.0F, 2.0F, 24.0F);
        this.root = root.getChild("root");
        this.rightArm = root.getChild("right_arm");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData meshdefinition = new ModelData();
        ModelPartData partdefinition = meshdefinition.getRoot();

        ModelPartData root = partdefinition.addChild("root", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 8.0F, 8.0F), ModelTransform.pivot(0.0F, 26.0F, 0.0F));
        root.addChild("right_arm", ModelPartBuilder.create().uv(8, 8).cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(meshdefinition, 16, 16);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        rightArm.setPivot(5.0F, 6.0F, 0.0F);
        rightArm.setAngles(0.0F, 0.0F, -82.5F);
        root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        rightArm.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return null;
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.root, this.rightArm);
    }

    @Override
    public void setArmAngle(Arm arm, MatrixStack matrices) {
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
