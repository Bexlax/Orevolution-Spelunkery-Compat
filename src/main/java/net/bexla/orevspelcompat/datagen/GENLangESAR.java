package net.bexla.orevspelcompat.datagen;

import net.bexla.orevolution.content.types.providers.LangProvider;
import net.bexla.orevspelcompat.OrevSpelCompat;
import net.minecraft.data.PackOutput;

public class GENLangESAR extends LangProvider {
    public GENLangESAR(PackOutput output) {
        super(output, OrevSpelCompat.MODID, "es_ar");
    }

    @Override
    protected void addTranslations() {
        addItem(OrevSpelCompat.RAW_TIN_NUGGET, "Pepita de estaño crudo");
        addItem(OrevSpelCompat.RAW_PLATINUM_NUGGET, "Pepita de platino crudo");
        addItem(OrevSpelCompat.RAW_TUNGSTEN_NUGGET, "Pepita de tungsteno crudo");

        addBlock(OrevSpelCompat.ANDESITE_TIN_ORE, "Mineral de estaño de andesita");
        addBlock(OrevSpelCompat.GRANITE_TIN_ORE, "Mineral de estaño de granito");
        addBlock(OrevSpelCompat.DIORITE_TIN_ORE, "Mineral de estaño de diorita");
        addBlock(OrevSpelCompat.TUFF_TIN_ORE, "Mineral de estaño de toba");

        addBlock(OrevSpelCompat.ANDESITE_PLATINUM_ORE, "Mineral de platino de andesita");
        addBlock(OrevSpelCompat.GRANITE_PLATINUM_ORE, "Mineral de platino de grano");
        addBlock(OrevSpelCompat.DIORITE_PLATINUM_ORE, "Mineral de platino de diorita");
        addBlock(OrevSpelCompat.TUFF_PLATINUM_ORE, "Mineral de platino de tierra");

        addBlock(OrevSpelCompat.BLACKSTONE_TUNGSTEN_ORE, "Mineral de tungsteno de piedra negra");
        addBlock(OrevSpelCompat.BLACKSTONE_CASSITERITE_ORE, "Mineral de casiterita de piedra negra");
    }
}
