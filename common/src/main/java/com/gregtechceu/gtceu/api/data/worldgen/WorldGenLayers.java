package com.gregtechceu.gtceu.api.data.worldgen;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.addon.AddonFinder;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.integration.kjs.GTRegistryObjectBuilderTypes;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import org.jetbrains.annotations.NotNull;

/**
 * @author Screret
 * @date 2023/6/9
 * @implNote WorldGenLayers
 */
public enum WorldGenLayers implements IWorldGenLayer, StringRepresentable {
    STONE("stone", new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES)),
    DEEPSLATE("deepslate", new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES)),
    NETHERRACK("netherrack", new TagMatchTest(BlockTags.NETHER_CARVER_REPLACEABLES)),
    ENDSTONE("endstone", WorldGeneratorUtils.END_ORE_REPLACEABLES);

    private final String name;
    @SuppressWarnings("NonFinalFieldInEnum")
    @Getter @Setter
    private RuleTest target;

    WorldGenLayers(String name, RuleTest target) {
        this.name = name;
        this.target = target;
        WorldGeneratorUtils.WORLD_GEN_LAYERS.put(name, this);
    }

    public static void registerAll() {
        AddonFinder.getAddons().forEach(IGTAddon::registerWorldgenLayers);
        if (GTCEu.isKubeJSLoaded()) {
            GTRegistryObjectBuilderTypes.registerFor(GTRegistryObjectBuilderTypes.WORLD_GEN_LAYER.registryKey);
        }
    }

    public static IWorldGenLayer getByName(String name) {
        return WorldGeneratorUtils.WORLD_GEN_LAYERS.get(name);
    }

    @Override
    @NotNull
    public String getSerializedName() {
        return name;
    }
}
