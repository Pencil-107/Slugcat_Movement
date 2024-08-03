package pencil.slugcatmovement.geo.player;

import net.minecraft.util.Identifier;
import pencil.slugcatmovement.RainworldMovement;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class SlugcatPlayerSurvivor extends GeoModel {

    private final Identifier model = new Identifier(RainworldMovement.MOD_ID, "geo/player/slugcat_survivor.geo.json");
    private final Identifier texture = new Identifier(RainworldMovement.MOD_ID, "textures/entity/slugcat_survivor.png");
    private final Identifier animations = new Identifier(RainworldMovement.MOD_ID, "animations/entity/slugcat_crawl.animation.json");

    @Override
    public Identifier getModelResource(GeoAnimatable animatable) {
        return model;
    }

    @Override
    public Identifier getTextureResource(GeoAnimatable animatable) {
        return texture;
    }

    @Override
    public Identifier getAnimationResource(GeoAnimatable animatable) {
        return animations;
    }
}
