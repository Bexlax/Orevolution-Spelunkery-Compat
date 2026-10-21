package net.bexla.orevspelcompat.datagen;

import com.teamabnormals.blueprint.core.data.client.BlueprintBlockStateProvider;
import net.bexla.orevspelcompat.OrevSpelCompat;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

import static net.bexla.orevspelcompat.OrevSpelCompat.lc;

public class GENBlockStateModels extends BlueprintBlockStateProvider {

    public GENBlockStateModels(PackOutput output, ExistingFileHelper help) {
        super(output, OrevSpelCompat.MODID, help);
    }

    @Override
    public @NotNull String getName() {
        return OrevSpelCompat.MODID + " Block States";
    }

    private void ore(Supplier<? extends Block> block) {
        this.simpleBlock(block, "ore");
    }

    @Override
    protected void registerStatesAndModels() {
        ore(OrevSpelCompat.ANDESITE_TIN_ORE);
        ore(OrevSpelCompat.GRANITE_TIN_ORE);
        ore(OrevSpelCompat.TUFF_TIN_ORE);
        ore(OrevSpelCompat.DIORITE_TIN_ORE);

        ore(OrevSpelCompat.ANDESITE_PLATINUM_ORE);
        ore(OrevSpelCompat.GRANITE_PLATINUM_ORE);
        ore(OrevSpelCompat.TUFF_PLATINUM_ORE);
        ore(OrevSpelCompat.DIORITE_PLATINUM_ORE);

        ore(OrevSpelCompat.BLACKSTONE_TUNGSTEN_ORE);
        ore(OrevSpelCompat.BLACKSTONE_CASSITERITE_ORE);
    }

    public void pillar(DeferredBlock<Block> block) {
        if (block.get() instanceof RotatedPillarBlock log)
            this.axisBlock(log, suffix(this.blockTexture(block.get(), "ore"), "_side"), suffix(this.blockTexture(block.get(), "ore"), "_top"));
    }

    public void simpleBlock(Supplier<? extends Block> block, String subfolder) {
        simpleBlock(block.get(), cubeAll(block.get(), subfolder));
    }

    public ModelFile cubeAll(Block block, String subfolder) {
        return models().cubeAll(name(block), blockTexture(block, subfolder));
    }

    public ResourceLocation blockTexture(Block block, String subfolder) {
        ResourceLocation name = key(block);
        return lc("block/" + subfolder + "/" + name.getPath());
    }

    public ModelFile cubeColumn(Block block, String subfolder) {
        return models().cubeColumn(name(block), blockTextureFace(block, "side", subfolder), blockTextureFace(block, "top", subfolder));
    }

    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    public ResourceLocation blockTextureFace(Block block, String face, String subfolder) {
        ResourceLocation name = key(block);
        return lc("block/" + subfolder + "/" + name.getPath() + "_" + face);
    }

    public void cubeColumnBlock(DeferredBlock<Block> block) {
        cubeColumnBlock(block, blockTextureFace(block.get(), "side", "ore"), blockTextureFace(block.get(), "top", "ore"));
    }
}
