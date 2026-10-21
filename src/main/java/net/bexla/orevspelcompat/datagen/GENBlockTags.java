package net.bexla.orevspelcompat.datagen;

import net.bexla.orevolution.content.data.utility.OrevolutionTags;
import net.bexla.orevspelcompat.OrevSpelCompat;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class GENBlockTags extends IntrinsicHolderTagsProvider<Block> {
    public GENBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> future, @Nullable ExistingFileHelper helper) {
        super(output, Registries.BLOCK, future, (block) -> block.builtInRegistryHolder().key(), OrevSpelCompat.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256380_) {
        tag(OrevolutionTags.Blocks.TIN_ORES)
                .add(OrevSpelCompat.ANDESITE_TIN_ORE.get())
                .add(OrevSpelCompat.DIORITE_TIN_ORE.get())
                .add(OrevSpelCompat.GRANITE_TIN_ORE.get())
                .add(OrevSpelCompat.TUFF_TIN_ORE.get());
        tag(OrevolutionTags.Blocks.PLATINUM_ORES)
                .add(OrevSpelCompat.ANDESITE_PLATINUM_ORE.get())
                .add(OrevSpelCompat.DIORITE_PLATINUM_ORE.get())
                .add(OrevSpelCompat.GRANITE_PLATINUM_ORE.get())
                .add(OrevSpelCompat.TUFF_PLATINUM_ORE.get());
        tag(OrevolutionTags.Blocks.TUNGSTEN_ORES)
                .add(OrevSpelCompat.BLACKSTONE_TUNGSTEN_ORE.get());
        tag(OrevolutionTags.Blocks.CASSITERITE_ORES)
                .add(OrevSpelCompat.BLACKSTONE_CASSITERITE_ORE.get());
    }
}
