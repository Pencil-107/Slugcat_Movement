package pencil.slugcatmovement.init;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import pencil.slugcatmovement.RainworldMovement;
import pencil.slugcatmovement.block.PoleY;

public class BlockInit {
    public static final Block POLE_Y = registerWithItem("pole_y", new PoleY(AbstractBlock.Settings.create()
            .strength(1.5F, 6.0F)));

    public static <T extends Block> T register(String name, T block){
        return Registry.register(Registries.BLOCK, RainworldMovement.id(name), block);
    }

    public static  <T extends Block> T registerWithItem(String name, T block, Item.Settings settings) {
        T registered = register(name, block);
        ItemInit.register(name, new BlockItem(registered, settings));
        return registered;
    }

    public static <T extends Block> T registerWithItem(String name, T block) {
        return registerWithItem(name, block, new Item.Settings());
    }

    public static void load() {}
}
