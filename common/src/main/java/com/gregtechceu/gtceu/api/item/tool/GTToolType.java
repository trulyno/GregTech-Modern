package com.gregtechceu.gtceu.api.item.tool;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.sound.ExistingSoundEntry;
import com.gregtechceu.gtceu.api.sound.SoundEntry;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;
import com.gregtechceu.gtceu.api.data.tag.TagUtil;
import com.gregtechceu.gtceu.common.item.tool.behavior.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.function.UnaryOperator;

/**
 * @author KilaBash
 * @date 2023/2/23
 * @implNote GTToolType
 */
public enum GTToolType {
    SWORD("sword", "swords", b -> b.attacking().attackDamage(3.0F).attackSpeed(-2.4F), false),
    PICKAXE("pickaxe", "pickaxes", b -> b.blockBreaking().attackDamage(1.0F).attackSpeed(-2.8F).behaviors(TorchPlaceBehavior.INSTANCE), true),
    SHOVEL("shovel", "shovels", b -> b.blockBreaking().attackDamage(1.5F).attackSpeed(-3.0F).behaviors(GrassPathBehavior.INSTANCE), true),
    AXE("axe", "axes", b -> b.blockBreaking()
            .attackDamage(5.0F).attackSpeed(-3.2F).baseEfficiency(2.0F)
            .behaviors(DisableShieldBehavior.INSTANCE, TreeFellingBehavior.INSTANCE), true),
    HOE("hoe", "hoes", b -> b.cannotAttack().attackSpeed(-1.0F), true),

    MINING_HAMMER("mining_hammer", "mining_hammers", b ->
            b.blockBreaking().aoe(1, 1, 0)
                    .efficiencyMultiplier(0.4F).attackDamage(1.5F).attackSpeed(-3.2F)
                    .durabilityMultiplier(3.0F)
                    .behaviors(TorchPlaceBehavior.INSTANCE), TagUtil.createBlockTag("mineable/pickaxe", true), GTCEu.id("item/tools/mining_hammer"), null, false),
    SPADE("spade", "spades", b -> b.blockBreaking().aoe(1, 1, 0)
            .efficiencyMultiplier(0.4F).attackDamage(1.5F).attackSpeed(-3.2F)
            .durabilityMultiplier(3.0F)
            .behaviors(GrassPathBehavior.INSTANCE), TagUtil.createBlockTag("mineable/shovel", true), GTCEu.id("item/tools/spade"), null, false),

    SAW("saw", "saws", b -> b.crafting().damagePerCraftingAction(2)
            .attackDamage(-1.0F).attackSpeed(-2.6F)
            .behaviors(HarvestIceBehavior.INSTANCE), GTSoundEntries.SAW_TOOL),
    HARD_HAMMER("hammer", "hammers", b -> b.blockBreaking().crafting().damagePerCraftingAction(2)
            .attackDamage(1.0F).attackSpeed(-2.8F)
            .behaviors(new EntityDamageBehavior(2.0F, IronGolem.class)), GTSoundEntries.FORGE_HAMMER),
    SOFT_MALLET("mallet", "mallets", b -> b.crafting().cannotAttack().attackSpeed(-2.4F), GTSoundEntries.SOFT_MALLET_TOOL),
    WRENCH("wrench", "wrenches", b -> b.blockBreaking().crafting().sneakBypassUse()
            .attackDamage(1.0F).attackSpeed(-2.8F)
            .behaviors(BlockRotatingBehavior.INSTANCE, new EntityDamageBehavior(3.0F, IronGolem.class)), GTSoundEntries.WRENCH_TOOL),
    FILE("file", "files", b-> b.crafting().damagePerCraftingAction(4)
            .cannotAttack().attackSpeed(-2.4F), GTSoundEntries.FILE_TOOL),
    CROWBAR("crowbar", "crowbars", b -> b.blockBreaking().crafting()
            .attackDamage(2.0F).attackSpeed(-2.4F)
            .sneakBypassUse().behaviors(RotateRailBehavior.INSTANCE), new ExistingSoundEntry(SoundEvents.ITEM_BREAK, SoundSource.BLOCKS)),
    SCREWDRIVER("screwdriver", "screwdrivers", b -> b.crafting().damagePerCraftingAction(4).sneakBypassUse()
            .attackDamage(-1.0F).attackSpeed(3.0F)
            .behaviors(new EntityDamageBehavior(3.0F, Spider.class)), GTSoundEntries.SCREWDRIVER_TOOL),
    MORTAR("mortar", "mortars", b -> b.crafting().damagePerCraftingAction(2).cannotAttack().attackSpeed(-2.4F), GTSoundEntries.MORTAR_TOOL),
    WIRE_CUTTER("wire_cutter", "wire_cutters", b -> b.blockBreaking().crafting().damagePerCraftingAction(4).attackDamage(-1.0F).attackSpeed(-2.4F), GTSoundEntries.WIRECUTTER_TOOL),
    SCYTHE("scythe", "scythes", b -> b.blockBreaking().attacking()
            .attackDamage(5.0F).attackSpeed(-3.0F).durabilityMultiplier(3.0F)
            .aoe(2, 2, 2)
            .behaviors(HoeGroundBehavior.INSTANCE, HarvestCropsBehavior.INSTANCE)
            .canApplyEnchantment(EnchantmentCategory.DIGGER)),
    KNIFE("knife", "knives", b -> b.crafting().attacking().attackSpeed(3.0F)),
    BUTCHERY_KNIFE("butchery_knife", "butchery_knives", b -> b.attacking()
            .attackDamage(1.5F).attackSpeed(-1.3F).defaultEnchantment(Enchantments.MOB_LOOTING, 3)),
    //    GRAFTER("grafter", 1, 1, GTCEu.id("item/tools/handle_hammer"), GTCEu.id("item/tools/hammer")),
    PLUNGER("plunger", "plungers", b -> b.cannotAttack().attackSpeed(-2.4F).sneakBypassUse()
            .behaviors(PlungerBehavior.INSTANCE), GTSoundEntries.PLUNGER_TOOL),
    SHEARS("shears", "shears", b -> b),
    ;

    public final String name;
    public final TagKey<Item> itemTag;
    public final TagKey<Block> harvestTag;
    public final ResourceLocation modelLocation;
    @Nullable
    public final SoundEntry soundEntry;

    public final UnaryOperator<ToolDefinitionBuilder> toolDefinition;

    GTToolType(String name, UnaryOperator<ToolDefinitionBuilder> toolDefinition, TagKey<Block> harvestTag, TagKey<Item> itemTag, ResourceLocation modelLocation, SoundEntry soundEntry) {
        this.name = name;
        this.itemTag = itemTag;
        this.harvestTag = harvestTag;
        this.modelLocation = modelLocation;
        this.soundEntry = soundEntry;
        this.toolDefinition = toolDefinition;
    }

    GTToolType(String name, String plural, UnaryOperator<ToolDefinitionBuilder> toolDefinition, TagKey<Block> harvestTag, ResourceLocation modelLocation, SoundEntry soundEntry, boolean isVanilla) {
        this(name, toolDefinition, harvestTag, isVanilla ? TagUtil.createItemTag(plural, true) : TagUtil.createPlatformItemTag("tools/" + plural, plural), modelLocation, soundEntry);
    }

    GTToolType(String name, String plural, UnaryOperator<ToolDefinitionBuilder> toolDefinition, ResourceLocation modelLocation, SoundEntry soundEntry, boolean isVanilla) {
        this(name, plural, toolDefinition, isVanilla ? TagUtil.createBlockTag("mineable/" + name, true) : TagUtil.createPlatformUnprefixedTag(BuiltInRegistries.BLOCK, "forge:mineable/" + name, "fabric:mineable/" + name), modelLocation, soundEntry, isVanilla);
    }

    GTToolType(String name, String plural, UnaryOperator<ToolDefinitionBuilder> toolDefinition, SoundEntry soundEntry, boolean isVanilla) {
        this(name, plural, toolDefinition, GTCEu.id(String.format("item/tools/%s", name)), soundEntry, isVanilla);
    }

    GTToolType(String name, String plural, UnaryOperator<ToolDefinitionBuilder> toolDefinition, SoundEntry soundEntry) {
        this(name, plural, toolDefinition, soundEntry, false);
    }

    GTToolType(String name, String plural, UnaryOperator<ToolDefinitionBuilder> toolDefinition, boolean isVanilla) {
        this(name, plural, toolDefinition, null, isVanilla);
    }

    GTToolType(String name, String plural, UnaryOperator<ToolDefinitionBuilder> toolDefinition) {
        this(name, plural, toolDefinition, false);
    }

    public boolean is(ItemStack itemStack) {
        return ToolHelper.is(itemStack, this);
    }

    public String getUnlocalizedName() {
        return "item.gtceu.tool." + name;
    }
}
