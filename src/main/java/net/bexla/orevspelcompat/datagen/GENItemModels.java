package net.bexla.orevspelcompat.datagen;

import com.teamabnormals.blueprint.core.data.client.BlueprintItemModelProvider;
import net.bexla.orevspelcompat.OrevSpelCompat;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

import static net.bexla.orevspelcompat.OrevSpelCompat.lc;

public class GENItemModels extends BlueprintItemModelProvider {
    public GENItemModels(PackOutput output, ExistingFileHelper helper) {
        super(output, OrevSpelCompat.MODID, helper);
    }

    public @NotNull String getName() {
        return OrevSpelCompat.MODID + " Item Models";
    }

    private ItemModelBuilder ingredient(Supplier<? extends Item> item) {
        return this.normalItem(item, "ingredient");
    }

    @Override
    protected void registerModels() {
        block(OrevSpelCompat.ANDESITE_TIN_ORE);
        block(OrevSpelCompat.GRANITE_TIN_ORE);
        block(OrevSpelCompat.TUFF_TIN_ORE);
        block(OrevSpelCompat.DIORITE_TIN_ORE);

        block(OrevSpelCompat.ANDESITE_PLATINUM_ORE);
        block(OrevSpelCompat.GRANITE_PLATINUM_ORE);
        block(OrevSpelCompat.TUFF_PLATINUM_ORE);
        block(OrevSpelCompat.DIORITE_PLATINUM_ORE);

        block(OrevSpelCompat.BLACKSTONE_TUNGSTEN_ORE);
        block(OrevSpelCompat.BLACKSTONE_CASSITERITE_ORE);

        ingredient(OrevSpelCompat.RAW_TIN_NUGGET);
        ingredient(OrevSpelCompat.RAW_PLATINUM_NUGGET);
        ingredient(OrevSpelCompat.RAW_TUNGSTEN_NUGGET);
        ingredient(OrevSpelCompat.RAW_CASSITERITE_NUGGET);
    }

    public ItemModelBuilder normalItem(Supplier<? extends Item> item, String subfolder) {
        return generated(item, itemTex(item.get(), subfolder));
    }

    public ItemModelBuilder generated(Supplier<? extends ItemLike> itemLike, ResourceLocation texture) {
        return generated(name(itemLike.get()), texture);
    }

    public ItemModelBuilder generated(String name, ResourceLocation texture) {
        return withExistingParent(name, mcLoc("item/generated")).texture("layer0", texture);
    }

    public static ResourceLocation itemTex(ItemLike item, String subfolder) {
        ResourceLocation name = key(item);
        return lc("item/" + subfolder + "/" + name.getPath());
    }

    protected String blockName(Supplier<? extends Block> block) {
        return BuiltInRegistries.BLOCK.getKey(block.get()).getPath();
    }

    public ItemModelBuilder block(Supplier<? extends Block> block) {
        return block(block, blockName(block));
    }

    public ItemModelBuilder block(Supplier<? extends Block> block, String name) {
        return withExistingParent(blockName(block), modLoc("block/" + name));
    }
}
