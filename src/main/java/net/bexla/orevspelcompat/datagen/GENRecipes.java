package net.bexla.orevspelcompat.datagen;

import com.teamabnormals.blueprint.core.data.server.BlueprintRecipeProvider;
import net.bexla.orevolution.Orevolution;
import net.bexla.orevolution.init.RegItems;
import net.bexla.orevspelcompat.OrevSpelCompat;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static net.bexla.orevspelcompat.OrevSpelCompat.lc;

public class GENRecipes extends BlueprintRecipeProvider {
    
    public GENRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(OrevSpelCompat.MODID, output, provider);
    }

    public static final Ingredient TIN_ORES = Ingredient.of(OrevSpelCompat.ANDESITE_TIN_ORE.get(), OrevSpelCompat.GRANITE_TIN_ORE.get(), OrevSpelCompat.DIORITE_TIN_ORE.get(), OrevSpelCompat.TUFF_TIN_ORE.get());
    public static final Ingredient PLATINUM_ORES = Ingredient.of(OrevSpelCompat.ANDESITE_PLATINUM_ORE.get(), OrevSpelCompat.GRANITE_PLATINUM_ORE.get(), OrevSpelCompat.DIORITE_PLATINUM_ORE.get(), OrevSpelCompat.TUFF_PLATINUM_ORE.get());
    public static final Ingredient TUNGSTEN_ORES = Ingredient.of(OrevSpelCompat.BLACKSTONE_TUNGSTEN_ORE.get());
    public static final Ingredient CASSITERITE_ORES = Ingredient.of(OrevSpelCompat.BLACKSTONE_CASSITERITE_ORE.get());

    @Override
    protected void buildRecipes(RecipeOutput consumer, HolderLookup.Provider provider) {
        ore(RegItems.TIN_INGOT.get(), TIN_ORES, 200, 0.1F, "orevolution:tin_ingot", consumer);
        ore(RegItems.PLATINUM_INGOT.get(), PLATINUM_ORES, 200, 0.1F, "orevolution:platinum_ingot", consumer);
        ore(RegItems.TUNGSTEN_INGOT.get(), TUNGSTEN_ORES, 200, 0.3F, "orevolution:tungsten_ingot", consumer);
        ore(RegItems.CASSITERITE_INGOT.get(), CASSITERITE_ORES, 200, 0.2F, "orevolution:cassiterite_ingot", consumer);

        smeltingRecipe(RegItems.TIN_NUGGET.get(), Ingredient.of(OrevSpelCompat.RAW_TIN_NUGGET.get()), 40, 0.1F).save(consumer, lc("tin_nugget_from_smelting"));
        smeltingRecipe(RegItems.PLATINUM_NUGGET.get(), Ingredient.of(OrevSpelCompat.RAW_PLATINUM_NUGGET.get()), 40, 0.1F).save(consumer, lc("platinum_nugget_from_smelting"));
        smeltingRecipe(RegItems.TUNGSTEN_NUGGET.get(), Ingredient.of(OrevSpelCompat.RAW_TUNGSTEN_NUGGET.get()), 40, 0.1F).save(consumer, lc("tungsten_nugget_from_smelting"));
        smeltingRecipe(RegItems.CASSITERITE_NUGGET.get(), Ingredient.of(OrevSpelCompat.RAW_CASSITERITE_NUGGET.get()), 40, 0.1F).save(consumer, lc("cassiterite_nugget_from_smelting"));

        blastingRecipe(RegItems.TIN_NUGGET.get(), Ingredient.of(OrevSpelCompat.RAW_TIN_NUGGET.get()), 12, 0.1F).save(consumer, lc("tin_nugget_from_blasting"));
        blastingRecipe(RegItems.PLATINUM_NUGGET.get(), Ingredient.of(OrevSpelCompat.RAW_PLATINUM_NUGGET.get()), 12, 0.1F).save(consumer, lc("platinum_nugget_from_blasting"));
        blastingRecipe(RegItems.TUNGSTEN_NUGGET.get(), Ingredient.of(OrevSpelCompat.RAW_TUNGSTEN_NUGGET.get()), 12, 0.1F).save(consumer, lc("tungsten_nugget_from_blasting"));
        smeltingRecipe(RegItems.CASSITERITE_NUGGET.get(), Ingredient.of(OrevSpelCompat.RAW_CASSITERITE_NUGGET.get()), 12, 0.1F).save(consumer, lc("cassiterite_nugget_from_blasting"));

        autoCompact(RegItems.RAW_TIN.get(), OrevSpelCompat.RAW_TIN_NUGGET.get(), consumer);
        autoCompact(RegItems.RAW_PLATINUM.get(), OrevSpelCompat.RAW_PLATINUM_NUGGET.get(), consumer);
        autoCompact(RegItems.RAW_TUNGSTEN.get(), OrevSpelCompat.RAW_TUNGSTEN_NUGGET.get(), consumer);
        autoCompact(RegItems.RAW_CASSITERITE.get(), OrevSpelCompat.RAW_CASSITERITE_NUGGET.get(), consumer);
    }

    protected void oreSmeltingRecipe(ItemLike result, Ingredient ingredients, int time, float xp, String group, RecipeOutput consumer) {
        smeltingRecipe(result, ingredients, time, xp).group(group).save(consumer, Orevolution.lc(getItemName(result) + "_from_smelting_" + getItemName(Arrays.stream(ingredients.getItems()).findFirst().get().getItem())));
    }

    public SimpleCookingRecipeBuilder smeltingRecipe(ItemLike result, Ingredient ingredient, int time, float exp) {
        return SimpleCookingRecipeBuilder.smelting(ingredient, RecipeCategory.MISC, result, exp, time)
                .unlockedBy(getHasName(Arrays.stream(ingredient.getItems()).findFirst().get().getItem()), has(Arrays.stream(ingredient.getItems()).findFirst().get().getItem()));
    }

    protected void oreBlastingRecipe(ItemLike result, Ingredient ingredients, int time, float xp, String group, RecipeOutput consumer) {
        blastingRecipe(result, ingredients, time, xp).group(group).save(consumer, Orevolution.lc(getItemName(result) + "_from_blasting_" + getItemName(Arrays.stream(ingredients.getItems()).findFirst().get().getItem())));
    }

    public SimpleCookingRecipeBuilder blastingRecipe(ItemLike result, Ingredient ingredient, int time, float exp) {
        return SimpleCookingRecipeBuilder.blasting(ingredient, RecipeCategory.MISC, result, exp, time)
                .unlockedBy(getHasName(Arrays.stream(ingredient.getItems()).findFirst().get().getItem()), has(Arrays.stream(ingredient.getItems()).findFirst().get().getItem()));
    }

    public void autoCompact(Item itemOut, Item itemIn, RecipeOutput consumer) {
        compact(itemOut, itemIn).save(consumer, Orevolution.lc(getItemName(itemOut) + "_from_" + getItemName(itemIn)));
        unCompact(itemIn, itemOut).save(consumer, Orevolution.lc(getItemName(itemIn) + "_from_" + getItemName(itemOut)));
    }

    public ShapedRecipeBuilder compact(Item itemOut, Item itemIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, itemOut)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', itemIn)
                .unlockedBy(getHasName(itemIn), has(itemIn));
    }
    public ShapelessRecipeBuilder unCompact(Item itemOut, Item itemIn) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, itemOut, 9)
                .requires(itemIn)
                .unlockedBy(getHasName(itemIn), has(itemIn));
    }

    public void ore(ItemLike result, Ingredient ingredients, int time, float xp, String group, RecipeOutput consumer) {
        oreSmeltingRecipe(result, ingredients, time, xp, group, consumer);
        oreBlastingRecipe(result, ingredients, time / 2, xp, group, consumer);
    }
}

