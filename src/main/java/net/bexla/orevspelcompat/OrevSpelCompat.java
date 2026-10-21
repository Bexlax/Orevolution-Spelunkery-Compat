package net.bexla.orevspelcompat;

import com.mojang.logging.LogUtils;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.bexla.orevspelcompat.datagen.*;
import net.minecraft.DetectedVersion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import org.slf4j.Logger;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

@Mod(OrevSpelCompat.MODID)
public class OrevSpelCompat {
    public static final String MODID = "orevspelcompat";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation lc(String id) {
        return ResourceLocation.fromNamespaceAndPath(MODID, id);
    }

    public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MODID);

    public static final ItemSubRegistryHelper ITEMHELPER = REGISTRY_HELPER.getItemSubHelper();
    public static final BlockSubRegistryHelper BLOCKHELPER = REGISTRY_HELPER.getBlockSubHelper();

    public static <T extends Block> DeferredBlock<T> baseRegisterBlock(String name, Supplier<? extends T> block, Function<DeferredBlock<T>, Supplier<? extends Item>> item) {
        DeferredBlock<T> register = BLOCKHELPER.createBlockNoItem(name, block);
        ITEMHELPER.createItem(name, item.apply(register));
        return register;
    }

    public static <B extends Block> DeferredBlock<B> block(String name, Supplier<? extends Block> block) {
        return (DeferredBlock<B>) baseRegisterBlock(name, block, OrevSpelCompat::registerBlockItem);
    }

    private static <T extends Block> Supplier<BlockItem> registerBlockItem(final DeferredBlock<T> block) {
        return () -> new BlockItem(Objects.requireNonNull(block.get()), new Item.Properties());
    }

    public static DeferredItem<Item> normalItem(String name) {
        return ITEMHELPER.createItem(name, () -> new Item(new Item.Properties()));
    }

    public static final DeferredBlock<Block> ANDESITE_TIN_ORE = block("andesite_tin_ore", () -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE)));
    public static final DeferredBlock<Block> DIORITE_TIN_ORE = block("diorite_tin_ore", () -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE)));
    public static final DeferredBlock<Block> GRANITE_TIN_ORE = block("granite_tin_ore", () -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE)));
    public static final DeferredBlock<Block> TUFF_TIN_ORE = block("tuff_tin_ore", () -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE)));

    public static final DeferredBlock<Block> ANDESITE_PLATINUM_ORE = block("andesite_platinum_ore", () -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_ORE)));
    public static final DeferredBlock<Block> DIORITE_PLATINUM_ORE = block("diorite_platinum_ore", () -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_ORE)));
    public static final DeferredBlock<Block> GRANITE_PLATINUM_ORE = block("granite_platinum_ore", () -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_ORE)));
    public static final DeferredBlock<Block> TUFF_PLATINUM_ORE = block("tuff_platinum_ore", () -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_ORE)));

    public static final DeferredBlock<Block> BLACKSTONE_TUNGSTEN_ORE = block("blackstone_tungsten_ore", () -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_QUARTZ_ORE)));
    public static final DeferredBlock<Block> BLACKSTONE_CASSITERITE_ORE = block("blackstone_cassiterite_ore", () -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_QUARTZ_ORE)));

    public static final DeferredItem<Item> RAW_TIN_NUGGET = normalItem("raw_tin_nugget");
    public static final DeferredItem<Item> RAW_PLATINUM_NUGGET = normalItem("raw_platinum_nugget");
    public static final DeferredItem<Item> RAW_TUNGSTEN_NUGGET = normalItem("raw_tungsten_nugget");
    public static final DeferredItem<Item> RAW_CASSITERITE_NUGGET = normalItem("raw_cassiterite_nugget");

    public OrevSpelCompat(IEventBus modEventBus, ModContainer modContainer) {
        BLOCKHELPER.register(modEventBus);
        ITEMHELPER.register(modEventBus);

        modEventBus.addListener(this::dataSetup);
    }

    private void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        boolean client = event.includeClient();
        generator.addProvider(client, new GENBlockStateModels(output, helper));
        generator.addProvider(client, new GENItemModels(output, helper));

        var lang = new GENLangENUS(output);

        generator.addProvider(client, lang);
        generator.addProvider(client, new GENLangESAR(output));

        boolean server = event.includeServer();
        generator.addProvider(server, new GENLootDrops(output, provider));
        GENBlockTags blockTags = new GENBlockTags(output, provider, helper);
        generator.addProvider(server, blockTags);
        generator.addProvider(server, new GENRecipes(output, provider));

        generator.addProvider(server, new PackMetadataGenerator(output).add(PackMetadataSection.TYPE,
                new PackMetadataSection(
                        Component.literal("Orevolution-Spelunkery Compat resources"),
                        DetectedVersion.BUILT_IN.getPackVersion(PackType.CLIENT_RESOURCES)
                )
        ));
    }
}
