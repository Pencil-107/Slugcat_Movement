package pencil.slugcatmovement.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.resource.featuretoggle.ToggleableFeature;
import net.minecraft.util.TypeFilter;
import org.jetbrains.annotations.Nullable;
import pencil.slugcatmovement.entity.GroundItemEntity;
import pencil.slugcatmovement.entity.GroundItemThrowingEntity;
import pencil.slugcatmovement.entity.SpearEntity;

public class EntityTypeInit<T extends Entity> implements ToggleableFeature, TypeFilter<Entity, T> {

    public static final EntityType<GroundItemThrowingEntity> GROUND_ITEM_THROWING = register(
            "ground_item_throwing",
            EntityType.Builder.<GroundItemThrowingEntity>create(GroundItemThrowingEntity::new, SpawnGroup.MISC).setDimensions(0.2f, 0.2f)
    );

    public static final EntityType<GroundItemEntity> GROUND_ITEM = register(
            "ground_item",
            EntityType.Builder.<GroundItemEntity>create(GroundItemEntity::new, SpawnGroup.MISC).setDimensions(0.6f, 0.6f)
    );

    public static final EntityType<SpearEntity> SPEAR = register(
            "spear",
            EntityType.Builder.<SpearEntity>create(SpearEntity::new, SpawnGroup.MISC).setDimensions(0.5F, 0.5F).maxTrackingRange(4).trackingTickInterval(20)
    );

    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, id, type.build(id));
    }

    public static void load() {
    }

    @Override
    public FeatureSet getRequiredFeatures() {
        return null;
    }

    @Nullable
    @Override
    public T downcast(Entity obj) {
        return null;
    }

    @Override
    public Class<? extends Entity> getBaseClass() {
        return null;
    }
}
