package net.bexla.orevspelcompat.datagen;

import com.google.common.collect.ImmutableList;
import net.bexla.orevolution.init.RegItems;
import net.bexla.orevspelcompat.OrevSpelCompat;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class GENLootDrops extends LootTableProvider {
    public GENLootDrops(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, BuiltInLootTables.all(), ImmutableList.of(
                new LootTableProvider.SubProviderEntry(BlockLoot::new, LootContextParamSets.BLOCK)
        ), provider);
    }

    protected void validate(WritableRegistry<LootTable> writableregistry, ValidationContext validationcontext, ProblemReporter.Collector problemreporter$collector) {
    }

    public static class BlockLoot extends BlockLootSubProvider {
        protected BlockLoot(HolderLookup.Provider registries) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
        }

        protected void generate() {
            ore(OrevSpelCompat.ANDESITE_TIN_ORE, RegItems.RAW_TIN);
            ore(OrevSpelCompat.GRANITE_TIN_ORE, RegItems.RAW_TIN);
            ore(OrevSpelCompat.DIORITE_TIN_ORE, RegItems.RAW_TIN);
            ore(OrevSpelCompat.TUFF_TIN_ORE, RegItems.RAW_TIN);

            ore(OrevSpelCompat.ANDESITE_PLATINUM_ORE, RegItems.RAW_PLATINUM);
            ore(OrevSpelCompat.GRANITE_PLATINUM_ORE, RegItems.RAW_PLATINUM);
            ore(OrevSpelCompat.DIORITE_PLATINUM_ORE, RegItems.RAW_PLATINUM);
            ore(OrevSpelCompat.TUFF_PLATINUM_ORE, RegItems.RAW_PLATINUM);

            add(OrevSpelCompat.BLACKSTONE_TUNGSTEN_ORE.get(), b -> createOreDrop(b, OrevSpelCompat.RAW_TUNGSTEN_NUGGET.get(), 1, 6, RegItems.RAW_TUNGSTEN.get()));
            add(OrevSpelCompat.BLACKSTONE_CASSITERITE_ORE.get(), b -> createOreDrop(b, OrevSpelCompat.RAW_CASSITERITE_NUGGET.get(), 1, 6, RegItems.RAW_CASSITERITE.get()));
        }

        public void ore(Supplier<? extends Block> block, Supplier<? extends Item> drop) {
            add(block.get(), (result) -> createOreDrop(result, drop.get()));
        }

        protected LootTable.Builder createOreDrop(Block block, Item nuggetDrop, float minNuggets, float maxNuggets, Item rawDrop) {
            HolderLookup.RegistryLookup<Enchantment> enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
            Holder<Enchantment> fortune = enchantments.getOrThrow(Enchantments.FORTUNE);

            return LootTable.lootTable()
                    .withPool(
                            LootPool.lootPool()
                                    .setRolls(ConstantValue.exactly(1))
                                    .add(
                                            AlternativesEntry.alternatives(
                                                    LootItem.lootTableItem(Items.AIR)
                                                            .when(this.hasSilkTouch()),

                                                    this.applyExplosionDecay(
                                                            block,
                                                            LootItem.lootTableItem(nuggetDrop)
                                                                    .apply(SetItemCountFunction.setCount(
                                                                            UniformGenerator.between(minNuggets, maxNuggets)
                                                                    ))
                                                                    .apply(ApplyBonusCount.addOreBonusCount(fortune))
                                                    )
                                            )
                                    )
                    )
                    .withPool(
                            LootPool.lootPool()
                                    .setRolls(ConstantValue.exactly(1))
                                    .add(
                                            AlternativesEntry.alternatives(

                                                    LootItem.lootTableItem(block)
                                                            .when(this.hasSilkTouch()),

                                                    this.applyExplosionDecay(
                                                            block,
                                                            LootItem.lootTableItem(rawDrop)
                                                                    .apply(ApplyBonusCount.addOreBonusCount(fortune))
                                                    )
                                            )
                                    )
                    );
        }

        protected @NotNull Iterable<Block> getKnownBlocks() {
            return OrevSpelCompat.BLOCKHELPER.getDeferredRegister().getEntries().stream().map(Supplier::get).collect(Collectors.toList());
        }
    }
}
