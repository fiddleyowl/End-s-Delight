package cn.foggyhillside.ends_delight.registry;

import cn.foggyhillside.ends_delight.EndsDelight;
import cn.foggyhillside.ends_delight.FoodList;
import cn.foggyhillside.ends_delight.item.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.FoodValues;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.DrinkableItem;
import vectorwing.farmersdelight.common.item.KnifeItem;
import vectorwing.farmersdelight.common.tag.ModTags;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(EndsDelight.MODID);

    //BlockItem
    public static final DeferredItem<BlockItem> CHORUS_FRUIT_CRATE = ITEMS.register("chorus_fruit_crate",
            id -> new BlockItem(ModBlocks.CHORUS_FRUIT_CRATE.get(), new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))));
    public static final DeferredItem<BlockItem> END_STOVE = ITEMS.register("end_stove",
            id -> new BlockItem(ModBlocks.END_STOVE.get(), new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))));
    //knife
    public static final DeferredItem<Item> DRAGON_EGG_SHELL_KNIFE = ITEMS.register("dragon_egg_shell_knife",
            id -> new KnifeItem(ModMaterials.DRAGON_EGG_SHELL, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).tool(ModMaterials.DRAGON_EGG_SHELL, ModTags.Blocks.MINEABLE_WITH_KNIFE, 0.5F, -2.0F, 0.0F)));
    public static final DeferredItem<Item> PURPUR_KNIFE = ITEMS.register("purpur_knife",
            id -> new KnifeItem(ModMaterials.PURPUR, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).tool(ModMaterials.PURPUR, ModTags.Blocks.MINEABLE_WITH_KNIFE, 0.5F, -2.0F, 0.0F)));
    public static final DeferredItem<Item> END_STONE_KNIFE = ITEMS.register("end_stone_knife",
            id -> new KnifeItem(ModMaterials.END_STONE, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).tool(ModMaterials.END_STONE, ModTags.Blocks.MINEABLE_WITH_KNIFE, 0.5F, -2.0F, 0.0F)));
    public static final DeferredItem<Item> DRAGON_TOOTH_KNIFE = ITEMS.register("dragon_tooth_knife",
            id -> new KnifeItem(ModMaterials.DRAGON_TOOTH, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).tool(ModMaterials.DRAGON_TOOTH, ModTags.Blocks.MINEABLE_WITH_KNIFE, 0.5F, -2.0F, 0.0F)));
    //Ingredients And Other Foods
    public static final DeferredItem<Item> ENDER_PEARL_GRAIN = ITEMS.register("ender_pearl_grain",
            id -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))));
    public static final DeferredItem<Item> CHORUS_FRUIT_GRAIN = ITEMS.register("chorus_fruit_grain",
            id -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.CHORUS_FRUIT_GRAIN, FoodList.CHORUS_FRUIT_GRAIN_CONSUMABLE)));
    public static final DeferredItem<BlockItem> CHORUS_SUCCULENT = ITEMS.register("chorus_succulent",
            id -> new BlockItem(ModBlocks.CHORUS_SUCCULENT.get(), new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.CHORUS_SUCCULENT, FoodList.CHORUS_SUCCULENT_CONSUMABLE)));
    public static final DeferredItem<Item> DRIED_CHORUS_FLOWER = ITEMS.register("dried_chorus_flower",
            id -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))));
    public static final DeferredItem<Item> DRAGON_TOOTH = ITEMS.register("dragon_tooth",
            id -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))));
    public static final DeferredItem<Item> NON_HATCHABLE_DRAGON_EGG = ITEMS.register("non_hatchable_dragon_egg",
            id -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).rarity(Rarity.RARE)));
    public static final DeferredItem<Item> HALF_DRAGON_EGG_SHELL = ITEMS.register("half_dragon_egg_shell",
            id -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> LIQUID_DRAGON_EGG = ITEMS.register("liquid_dragon_egg",
            id -> new ConsumableItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.LIQUID_DRAGON_EGG, FoodList.LIQUID_DRAGON_EGG_CONSUMABLE), true));
    public static final DeferredItem<Item> FRIED_DRAGON_EGG = ITEMS.register("fried_dragon_egg",
            id -> new ConsumableItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.FRIED_DRAGON_EGG, FoodList.FRIED_DRAGON_EGG_CONSUMABLE), true));
    public static final DeferredItem<Item> SHULKER_MEAT = ITEMS.register("shulker_meat",
            id -> new ConsumableItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.SHULKER_MEAT, FoodList.SHULKER_MEAT_CONSUMABLE), true));
    public static final DeferredItem<Item> SHULKER_MEAT_SLICE = ITEMS.register("shulker_meat_slice",
            id -> new ConsumableItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.SHULKER_MEAT_SLICE, FoodList.SHULKER_MEAT_SLICE_CONSUMABLE), true));
    public static final DeferredItem<Item> ROASTED_SHULKER_MEAT = ITEMS.register("roasted_shulker_meat",
            id -> new ConsumableItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.ROASTED_SHULKER_MEAT, FoodList.ROASTED_SHULKER_MEAT_CONSUMABLE), true));
    public static final DeferredItem<Item> ROASTED_SHULKER_MEAT_SLICE = ITEMS.register("roasted_shulker_meat_slice",
            id -> new ConsumableItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.ROASTED_SHULKER_MEAT_SLICE, FoodList.ROASTED_SHULKER_MEAT_SLICE_CONSUMABLE), true));
    public static final DeferredItem<Item> DRAGON_LEG = ITEMS.register("dragon_leg",
            id -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.DRAGON_LEG, FoodList.DRAGON_LEG_CONSUMABLE)));
    public static final DeferredItem<Item> SMOKED_DRAGON_LEG = ITEMS.register("smoked_dragon_leg",
            id -> new ConsumableItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.SMOKED_DRAGON_LEG, FoodList.SMOKED_DRAGON_LEG_CONSUMABLE), true));
    public static final DeferredItem<Item> RAW_DRAGON_MEAT = ITEMS.register("raw_dragon_meat",
            id -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.RAW_DRAGON_MEAT, FoodList.RAW_DRAGON_MEAT_CONSUMABLE)));
    public static final DeferredItem<Item> ROASTED_DRAGON_MEAT = ITEMS.register("roasted_dragon_meat",
            id -> new ConsumableItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.ROASTED_DRAGON_MEAT, FoodList.ROASTED_DRAGON_MEAT_CONSUMABLE), true));
    public static final DeferredItem<Item> RAW_DRAGON_MEAT_CUTS = ITEMS.register("raw_dragon_meat_cuts",
            id -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.RAW_DRAGON_MEAT_CUTS, FoodList.RAW_DRAGON_MEAT_CUTS_CONSUMABLE)));
    public static final DeferredItem<Item> ROASTED_DRAGON_MEAT_CUTS = ITEMS.register("roasted_dragon_meat_cuts",
            id -> new ConsumableItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.ROASTED_DRAGON_MEAT_CUTS, FoodList.ROASTED_DRAGON_MEAT_CUTS_CONSUMABLE), true));
    public static final DeferredItem<Item> RAW_ENDERMITE_MEAT = ITEMS.register("raw_ender_mite_meat",
            id -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.RAW_ENDERMITE_MEAT, FoodList.RAW_ENDERMITE_MEAT_CONSUMABLE)));
    public static final DeferredItem<Item> DRIED_ENDERMITE_MEAT = ITEMS.register("dried_endermite_meat",
            id -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.DRIED_ENDERMITE_MEAT, FoodList.DRIED_ENDERMITE_MEAT_CONSUMABLE)));
    public static final DeferredItem<Item> ENDERMAN_GRISTLE = ITEMS.register("enderman_gristle",
            id -> new EndermanGristleItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.ENDERMAN_GRISTLE, FoodList.ENDERMAN_GRISTLE_CONSUMABLE), 0.3F, false));
    public static final DeferredItem<Item> CHORUS_SAUCE = ITEMS.register("chorus_sauce",
            id -> new ConsumableItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.CHORUS_SAUCE, FoodList.CHORUS_SAUCE_CONSUMABLE).craftRemainder(Items.BOWL).usingConvertsTo(Items.BOWL).stacksTo(64)));
    public static final DeferredItem<Item> SHULKER_OMELETTE_MIXTURE = ITEMS.register("shulker_omelette_mixture",
            id -> new ConsumableItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.SHULKER_OMELETTE_MIXTURE, FoodList.SHULKER_OMELETTE_MIXTURE_CONSUMABLE),true));
    public static final DeferredItem<Item> SHULKER_OMELETTE = ITEMS.register("shulker_omelette",
            id -> new ConsumableItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.SHULKER_OMELETTE, FoodList.SHULKER_OMELETTE_CONSUMABLE),true));
    public static final DeferredItem<Item> RAW_ENDER_SAUSAGE = ITEMS.register("raw_ender_sausage",
            id -> new EndermanGristleItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.RAW_ENDER_SAUSAGE, FoodList.RAW_ENDER_SAUSAGE_CONSUMABLE), 0.3F, false));
    public static final DeferredItem<Item> ENDER_SAUSAGE = ITEMS.register("ender_sausage",
            id -> new EndermanGristleItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.ENDER_SAUSAGE, FoodList.ENDER_SAUSAGE_CONSUMABLE), 0.2F, true, true));
    public static final DeferredItem<Item> ENDER_BAMBOO_RICE = ITEMS.register("ender_bamboo_rice",
            id -> new ConsumableItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.ENDER_BAMBOO_RICE, FoodList.ENDER_BAMBOO_RICE_CONSUMABLE), true));
    public static final DeferredItem<Item> STUFFED_RICE_CAKE = ITEMS.register("stuffed_rice_cake",
            id -> new ConsumableChorusItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.STUFFED_RICE_CAKE, FoodList.STUFFED_RICE_CAKE_CONSUMABLE), true));
    public static final DeferredItem<Item> CHORUS_FLOWER_PIE = ITEMS.register("chorus_flower_pie",
            id -> new ConsumableChorusItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.CHORUS_FLOWER_PIE, FoodList.CHORUS_FLOWER_PIE_CONSUMABLE), true, true));
    public static final DeferredItem<Item> CHORUS_COOKIE = ITEMS.register("chorus_cookie",
            id -> new ConsumableChorusItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.CHORUS_COOKIE, FoodList.CHORUS_COOKIE_CONSUMABLE), true));
    public static final DeferredItem<Item> CHORUS_FRUIT_POPSICLE = ITEMS.register("chorus_fruit_popsicle",
            id -> new ChorusFruitPopsicleItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.CHORUS_FRUIT_POPSICLE, FoodList.CHORUS_FRUIT_POPSICLE_CONSUMABLE).stacksTo(16).usingConvertsTo(Items.STICK)));
    //Drink
    public static final DeferredItem<Item> CHORUS_FRUIT_WINE = ITEMS.register("chorus_fruit_wine",
            id -> new ChorusFruitWineItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.CHORUS_FRUIT_WINE, FoodList.CHORUS_FRUIT_WINE_CONSUMABLE).craftRemainder(Items.GLASS_BOTTLE).usingConvertsTo(Items.GLASS_BOTTLE).stacksTo(16)));
    public static final DeferredItem<Item> CHORUS_FRUIT_MILK_TEA = ITEMS.register("chorus_fruit_milk_tea",
            id -> new BubbleTeaItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.CHORUS_FRUIT_MILK_TEA, FoodList.CHORUS_FRUIT_MILK_TEA_CONSUMABLE).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE).usingConvertsTo(Items.GLASS_BOTTLE)));
    public static final DeferredItem<Item> BUBBLE_TEA = ITEMS.register("bubble_tea",
            id -> new BubbleTeaItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.BUBBLE_TEA, FoodList.BUBBLE_TEA_CONSUMABLE).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE).usingConvertsTo(Items.GLASS_BOTTLE)));
    public static final DeferredItem<Item> DRAGON_BREATH_SODA = ITEMS.register("dragon_breath_soda",
            id -> new DrinkableItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.DRAGON_BREATH_SODA, FoodList.DRAGON_BREATH_SODA_CONSUMABLE).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE).usingConvertsTo(Items.GLASS_BOTTLE), true));
    public static final DeferredItem<Item> CHORUS_FLOWER_TEA = ITEMS.register("chorus_flower_tea",
            id -> new ChorusFlowerTeaItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.CHORUS_FLOWER_TEA, FoodList.CHORUS_FLOWER_TEA_CONSUMABLE).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE).usingConvertsTo(Items.GLASS_BOTTLE), true, true));
    //Pie
    public static final DeferredItem<BlockItem> CHORUS_FRUIT_PIE = ITEMS.register("chorus_fruit_pie",
            id -> new BlockItem(ModBlocks.CHORUS_FRUIT_PIE.get(), new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))));
    public static final DeferredItem<Item> CHORUS_FRUIT_PIE_SLICE = ITEMS.register("chorus_fruit_pie_slice",
            id -> new ConsumableChorusItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodValues.PIE_SLICE, FoodValues.consumableFor(FoodValues.PIE_SLICE)), true, true));
    //Congee
    public static final DeferredItem<Item> ENDER_CONGEE = ITEMS.register("ender_congee",
            id -> new ConsumableItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.ENDER_CONGEE, FoodList.ENDER_CONGEE_CONSUMABLE).craftRemainder(Items.BOWL).usingConvertsTo(Items.BOWL).stacksTo(16), true));
    //Soup
    public static final DeferredItem<Item> DRAGON_BREATH_AND_CHORUS_SOUP = ITEMS.register("dragon_breath_and_chorus_soup",
            id -> new DrinkableChorusItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.DRAGON_BREATH_AND_CHORUS_SOUP, FoodList.DRAGON_BREATH_AND_CHORUS_SOUP_CONSUMABLE).craftRemainder(Items.BOWL).usingConvertsTo(Items.BOWL).stacksTo(16), true, true));
    public static final DeferredItem<Item> SHULKER_SOUP = ITEMS.register("shulker_soup",
            id -> new ConsumableItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.SHULKER_SOUP, FoodList.SHULKER_SOUP_CONSUMABLE).craftRemainder(Items.BOWL).usingConvertsTo(Items.BOWL).stacksTo(16), true));
    //ConsumableItem
    public static final DeferredItem<Item> ENDER_NOODLE = ITEMS.register("ender_noodle",
            id -> new ConsumableItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.ENDER_NOODLE, FoodList.ENDER_NOODLE_CONSUMABLE).craftRemainder(Items.BOWL).usingConvertsTo(Items.BOWL).stacksTo(16), true));
    public static final DeferredItem<Item> ENDERMAN_GRISTLE_STEW = ITEMS.register("enderman_gristle_stew",
            id -> new EndermanGristleItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.ENDERMAN_GRISTLE_STEW, FoodList.ENDERMAN_GRISTLE_STEW_CONSUMABLE).craftRemainder(Items.BOWL).usingConvertsTo(Items.BOWL).stacksTo(16), 0.2F, true, true));
    public static final DeferredItem<Item> STIR_FRIED_SHULKER_MEAT = ITEMS.register("stir_fried_shulker_meat",
            id -> new ConsumableItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.STIR_FRIED_SHULKER_MEAT, FoodList.STIR_FRIED_SHULKER_MEAT_CONSUMABLE).stacksTo(16).craftRemainder(Items.BOWL).usingConvertsTo(Items.BOWL), true));
    public static final DeferredItem<Item> ROASTED_DRAGON_STEAK = ITEMS.register("roasted_dragon_steak",
            id -> new ConsumableItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.ROASTED_DRAGON_STEAK, FoodList.ROASTED_DRAGON_STEAK_CONSUMABLE).stacksTo(16).craftRemainder(Items.BOWL).usingConvertsTo(Items.BOWL), true));
    public static final DeferredItem<Item> END_MIXED_SALAD = ITEMS.register("end_mixed_salad",
            id -> new ConsumableItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.END_MIXED_SALAD, FoodList.END_MIXED_SALAD_CONSUMABLE).craftRemainder(Items.BOWL).usingConvertsTo(Items.BOWL).stacksTo(16), true));
    public static final DeferredItem<Item> ASSORTED_SALAD = ITEMS.register("assorted_salad",
            id -> new ConsumableItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.ASSORTED_SALAD, FoodList.ASSORTED_SALAD_CONSUMABLE).craftRemainder(Items.SHULKER_SHELL).usingConvertsTo(Items.SHULKER_SHELL).stacksTo(16), true));
    //BarbecueStick
    public static final DeferredItem<Item> END_BARBECUE_STICK = ITEMS.register("end_barbecue_stick",
            id -> new ConsumableItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.END_BARBECUE_STICK, FoodList.END_BARBECUE_STICK_CONSUMABLE), true));
    //Feast
    public static final DeferredItem<BlockItem> DRAGON_LEG_BLOCK = ITEMS.register("dragon_leg_with_sauce_block",
            id -> new BlockItem(ModBlocks.DRAGON_LEG_BLOCK.get(), new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).stacksTo(1)));
    public static final DeferredItem<Item> DRAGON_LEG_WITH_SAUCE = ITEMS.register("dragon_leg_with_sauce",
            id -> new ConsumableItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.DRAGON_LEG_WITH_SAUCE, FoodList.DRAGON_LEG_WITH_SAUCE_CONSUMABLE).stacksTo(16).craftRemainder(Items.BOWL).usingConvertsTo(Items.BOWL), true));
    public static final DeferredItem<BlockItem> STEAMED_DRAGON_EGG_BLOCK = ITEMS.register("steamed_dragon_egg_block",
            id -> new BlockItem(ModBlocks.STEAMED_DRAGON_EGG_BLOCK.get(), new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).stacksTo(1)));
    public static final DeferredItem<Item> STEAMED_DRAGON_EGG = ITEMS.register("steamed_dragon_egg",
            id -> new ConsumableItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.STEAMED_DRAGON_EGG, FoodList.STEAMED_DRAGON_EGG_CONSUMABLE).craftRemainder(Items.BOWL).usingConvertsTo(Items.BOWL).stacksTo(16), true));
    public static final DeferredItem<BlockItem> DRAGON_MEAT_STEW_BLOCK = ITEMS.register("dragon_meat_stew_block",
            id -> new BlockItem(ModBlocks.DRAGON_MEAT_STEW_BLOCK.get(), new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).stacksTo(1)));
    public static final DeferredItem<Item> DRAGON_MEAT_STEW = ITEMS.register("dragon_meat_stew",
            id -> new ConsumableItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.DRAGON_MEAT_STEW, FoodList.DRAGON_MEAT_STEW_CONSUMABLE).craftRemainder(Items.BOWL).usingConvertsTo(Items.BOWL).stacksTo(16), true));
    public static final DeferredItem<BlockItem> GRILLED_SHULKER_BLOCK = ITEMS.register("grilled_shulker_block",
            id -> new BlockItem(ModBlocks.GRILLED_SHULKER_BLOCK.get(), new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).stacksTo(1)));
    public static final DeferredItem<Item> GRILLED_SHULKER = ITEMS.register("grilled_shulker",
            id -> new ConsumableItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).food(FoodList.GRILLED_SHULKER, FoodList.GRILLED_SHULKER_CONSUMABLE).craftRemainder(Items.BOWL).usingConvertsTo(Items.BOWL).stacksTo(16), true));
}
