package net.bexla.orevspelcompat;

import net.bexla.orevolution.Orevolution;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.function.Supplier;

@EventBusSubscriber(modid = OrevSpelCompat.MODID)
public class BuildCreativeTabsEvent {
    @SubscribeEvent
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> tab = event.getTabKey();

        if (tab == CreativeModeTabs.INGREDIENTS) {
            putBefore(event, Items.COAL, OrevSpelCompat.RAW_TIN_NUGGET);
            putAfter(event, OrevSpelCompat.RAW_TIN_NUGGET.get(), OrevSpelCompat.RAW_PLATINUM_NUGGET);
            putAfter(event, OrevSpelCompat.RAW_PLATINUM_NUGGET.get(), OrevSpelCompat.RAW_TUNGSTEN_NUGGET);
            putAfter(event, OrevSpelCompat.RAW_TIN_NUGGET.get(), OrevSpelCompat.RAW_CASSITERITE_NUGGET);
        }
        if (tab == CreativeModeTabs.NATURAL_BLOCKS) {
            putBefore(event, Items.IRON_ORE,
                    OrevSpelCompat.ANDESITE_TIN_ORE,
                    OrevSpelCompat.DIORITE_TIN_ORE,
                    OrevSpelCompat.GRANITE_TIN_ORE,
                    OrevSpelCompat.TUFF_TIN_ORE);

            putAfter(event, OrevSpelCompat.TUFF_TIN_ORE.get(), OrevSpelCompat.BLACKSTONE_CASSITERITE_ORE);

            putBefore(event, Items.DIAMOND_ORE,
                    OrevSpelCompat.ANDESITE_PLATINUM_ORE,
                    OrevSpelCompat.DIORITE_PLATINUM_ORE,
                    OrevSpelCompat.GRANITE_PLATINUM_ORE,
                    OrevSpelCompat.TUFF_PLATINUM_ORE);

            putAfter(event, OrevSpelCompat.TUFF_PLATINUM_ORE.get(), OrevSpelCompat.BLACKSTONE_TUNGSTEN_ORE);
        }
    }

    @SafeVarargs
    private static void putAfter(BuildCreativeModeTabContentsEvent event, ItemLike after, Supplier<? extends ItemLike>... supplier) {
        for (int i = supplier.length - 1; i >= 0; i--) {
            ItemLike key = supplier[i].get();
            event.insertAfter(new ItemStack(after), new ItemStack(key), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    @SafeVarargs
    private static void putBefore(BuildCreativeModeTabContentsEvent event, ItemLike before, Supplier<? extends ItemLike>... supplier) {
        for (Supplier<? extends ItemLike> supplier1 : supplier) {
            ItemLike key = supplier1.get();
            event.insertBefore(new ItemStack(before), new ItemStack(key), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
