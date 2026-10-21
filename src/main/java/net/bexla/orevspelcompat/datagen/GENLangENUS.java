package net.bexla.orevspelcompat.datagen;

import net.bexla.orevolution.content.types.providers.LangProvider;
import net.bexla.orevspelcompat.OrevSpelCompat;
import net.minecraft.data.PackOutput;

public class GENLangENUS extends LangProvider {
    public GENLangENUS(PackOutput output) {
        super(output, OrevSpelCompat.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addItem(OrevSpelCompat.RAW_TIN_NUGGET, "Raw Tin Nugget");
        addItem(OrevSpelCompat.RAW_PLATINUM_NUGGET, "Raw Platinum Nugget");
        addItem(OrevSpelCompat.RAW_TUNGSTEN_NUGGET, "Raw Tungsten Nugget");
        addItem(OrevSpelCompat.RAW_CASSITERITE_NUGGET, "Raw Cassiterite Nugget");

        addBlock(OrevSpelCompat.ANDESITE_TIN_ORE, "Andesite Tin Ore");
        addBlock(OrevSpelCompat.GRANITE_TIN_ORE, "Granite Tin Ore");
        addBlock(OrevSpelCompat.DIORITE_TIN_ORE, "Diorite Tin Ore");
        addBlock(OrevSpelCompat.TUFF_TIN_ORE, "Tuff Tin Ore");

        addBlock(OrevSpelCompat.ANDESITE_PLATINUM_ORE, "Andesite Platinum Ore");
        addBlock(OrevSpelCompat.GRANITE_PLATINUM_ORE, "Granite Platinum Ore");
        addBlock(OrevSpelCompat.DIORITE_PLATINUM_ORE, "Diorite Platinum Ore");
        addBlock(OrevSpelCompat.TUFF_PLATINUM_ORE, "Tuff Platinum Ore");

        addBlock(OrevSpelCompat.BLACKSTONE_TUNGSTEN_ORE, "Blackstone Tungsten Ore");
        addBlock(OrevSpelCompat.BLACKSTONE_CASSITERITE_ORE, "Blackstone Cassiterite Ore");
    }
}
