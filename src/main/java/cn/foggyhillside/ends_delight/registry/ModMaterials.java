package cn.foggyhillside.ends_delight.registry;

import cn.foggyhillside.ends_delight.EndsDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ToolMaterial;

public class ModMaterials {
    public static final ToolMaterial DRAGON_EGG_SHELL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_IRON_TOOL, 1250, 6.0F, 2.5F, 14, repairTag("dragon_egg_shell"));
    public static final ToolMaterial END_STONE = new ToolMaterial(
            BlockTags.INCORRECT_FOR_STONE_TOOL, 200, 4.0F, 1.0F, 5, repairTag("end_stone"));
    public static final ToolMaterial PURPUR = new ToolMaterial(
            BlockTags.INCORRECT_FOR_STONE_TOOL, 200, 4.0F, 1.0F, 5, repairTag("purpur"));
    public static final ToolMaterial DRAGON_TOOTH = new ToolMaterial(
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1561, 8.0F, 3.5F, 10, repairTag("dragon_tooth"));

    private static TagKey<net.minecraft.world.item.Item> repairTag(String name) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(EndsDelight.MODID, "repair_materials/" + name));
    }
}
