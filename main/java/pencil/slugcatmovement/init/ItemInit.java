package pencil.slugcatmovement.init;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import pencil.slugcatmovement.RainworldMovement;
import pencil.slugcatmovement.item.SpearItem;

public class ItemInit {

    public static final Item SPEAR_ITEM = register("spear_item", new SpearItem(new Item.Settings().maxDamage(250)));

    public static <T extends Item> T register(String name, T item) {
        return Registry.register(Registries.ITEM, RainworldMovement.id(name), item);
    }

    public static void load() {}
}
