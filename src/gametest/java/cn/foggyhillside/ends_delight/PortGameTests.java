package cn.foggyhillside.ends_delight;

import cn.foggyhillside.ends_delight.block.entity.EndStoveBlockEntity;
import cn.foggyhillside.ends_delight.block.DragonLegBlock;
import cn.foggyhillside.ends_delight.item.ChorusFruitWineItem;
import cn.foggyhillside.ends_delight.registry.ModBlocks;
import cn.foggyhillside.ends_delight.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.ProblemReporter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.BlockItem;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.AABB;
import net.minecraft.stats.Stats;
import vectorwing.farmersdelight.common.block.AbstractStoveBlock;
import vectorwing.farmersdelight.common.block.entity.AbstractStoveBlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.resource.NeoForgeReloadListeners;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.Map;
import java.util.List;
import java.util.function.Consumer;

/** Runs only from the development source set; excluded from the distributed jar. */
@EventBusSubscriber(modid = EndsDelight.MODID)
public class PortGameTests {
    private static final Map<String, Consumer<GameTestHelper>> TESTS = Map.of(
            "resources", PortGameTests::resources,
            "consumption", PortGameTests::consumption,
            "pie_teleport", PortGameTests::pieTeleport,
            "knife_durability", PortGameTests::knifeDurability,
            "popsicle_remainder", PortGameTests::popsicleRemainder,
            "stove_sync_and_reload", PortGameTests::stoveSyncAndReload,
            "wine_cure_policy", PortGameTests::wineCurePolicy,
            "dragon_leg_servings", PortGameTests::dragonLegServings,
            "stove_and_teleport", PortGameTests::stoveAndTeleport);

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(EndsDelight.MODID, path);
    }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        TESTS.forEach((name, function) -> event.register(Registries.TEST_FUNCTION, id(name), () -> function));
        event.register(Registries.MOB_EFFECT, id("wine_immune_test"),
                () -> new MobEffect(MobEffectCategory.HARMFUL, 0x550055) {});
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("port"));
        TESTS.keySet().forEach(name -> event.registerTest(id(name), new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, id(name)),
                new TestData<>(environment, Identifier.withDefaultNamespace("empty"), 100, 0, true))));
    }

    private static void resources(GameTestHelper helper) {
        var server = helper.getLevel().getServer();
        for (var item : ModItems.ITEMS.getEntries()) {
            var stack = new ItemStack(item.get());
            var name = stack.getHoverName().getContents();
            String prefix = item.get() instanceof BlockItem ? "block" : "item";
            String expectedKey = prefix + "." + EndsDelight.MODID + "." + item.getId().getPath();
            helper.assertTrue(name instanceof TranslatableContents translated && translated.getKey().equals(expectedKey),
                    "Item name must retain its upstream translation key: " + expectedKey);
        }
        var resources = server.getResourceManager().listResources("recipe", location ->
                location.getNamespace().equals(EndsDelight.MODID) && location.getPath().endsWith(".json"));
        helper.assertTrue(resources.size() == 75, "Expected all 75 upstream recipes");
        resources.keySet().forEach(location -> {
            String path = location.getPath();
            var key = ResourceKey.create(Registries.RECIPE, id(path.substring(7, path.length() - 5)));
            helper.assertTrue(server.getRecipeManager().byKey(key).isPresent(), "Recipe did not load: " + key);
        });
        helper.assertTrue(server.getAdvancements().getAllAdvancements().stream()
                .filter(a -> a.id().getNamespace().equals(EndsDelight.MODID)).count() == 12,
                "Expected all 12 upstream advancements");
        var modifiers = server.getServerResources().managers().getListener(NeoForgeReloadListeners.LOOT_MODIFIERS_KEY);
        server.getResourceManager().listResources("loot_modifiers", location ->
                location.getNamespace().equals(EndsDelight.MODID) && location.getPath().endsWith(".json"))
                .keySet().forEach(location -> {
                    String path = location.getPath();
                    var modifierId = id(path.substring("loot_modifiers/".length(), path.length() - 5));
                    helper.assertTrue(modifiers.getModifier(modifierId) != null, "Loot modifier did not load: " + modifierId);
                });
        helper.succeed();
    }

    private static void pieTeleport(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.getFoodData().setFoodLevel(0);
        player.setShiftKeyDown(true);
        int[] attempts = {0};
        Consumer<EntityTeleportEvent.ItemConsumption> listener = event -> {
            if (event.getEntity() == player) {
                attempts[0]++;
                event.setCanceled(true);
            }
        };
        NeoForge.EVENT_BUS.addListener(listener);
        try {
            BlockPos pos = helper.absolutePos(new BlockPos(0, 1, 0));
            var state = ModBlocks.CHORUS_FRUIT_PIE.get().defaultBlockState();
            helper.getLevel().setBlockAndUpdate(pos.below(), Blocks.END_STONE.defaultBlockState());
            helper.getLevel().setBlockAndUpdate(pos, state);
            state.useWithoutItem(helper.getLevel(), player,
                    new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, false));
            helper.assertTrue(attempts[0] == 1, "Eating placed chorus pie must trigger exactly one teleport");
            helper.assertTrue(player.getFoodData().getFoodLevel() == 3, "Pie must retain slice nutrition");
        } finally {
            NeoForge.EVENT_BUS.unregister(listener);
        }

        // Do not cancel this teleport: the old smoke test returned before the cooldown path.
        var shifted = helper.makeMockPlayer(GameType.SURVIVAL);
        shifted.getFoodData().setFoodLevel(0);
        shifted.setShiftKeyDown(true);
        BlockPos pos = helper.absolutePos(new BlockPos(0, 1, 0));
        shifted.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        var state = ModBlocks.CHORUS_FRUIT_PIE.get().defaultBlockState();
        helper.getLevel().setBlockAndUpdate(pos, state);
        var hit = new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, false);
        state.useWithoutItem(helper.getLevel(), shifted, hit);
        helper.assertTrue(!shifted.getCooldowns().isOnCooldown(new ItemStack(ModItems.CHORUS_FRUIT_PIE_SLICE.get())),
                "Eating a placed pie must not start the held slice cooldown");

        var serverPlayer = helper.makeMockServerPlayerInLevel();
        try {
            serverPlayer.getFoodData().setFoodLevel(0);
            int uses = serverPlayer.getStats().getValue(Stats.ITEM_USED.get(ModItems.CHORUS_FRUIT_PIE_SLICE.get()));
            helper.getLevel().setBlockAndUpdate(pos, state);
            state.useWithoutItem(helper.getLevel(), serverPlayer, hit);
            helper.assertTrue(serverPlayer.getStats().getValue(Stats.ITEM_USED.get(ModItems.CHORUS_FRUIT_PIE_SLICE.get())) == uses,
                    "Eating a placed pie must not award held-item consumption statistics");
        } finally {
            helper.getLevel().getServer().getPlayerList().remove(serverPlayer);
        }
        helper.succeed();
    }

    private static void knifeDurability(GameTestHelper helper) {
        for (var item : List.of(ModItems.END_STONE_KNIFE, ModItems.PURPUR_KNIFE,
                ModItems.DRAGON_EGG_SHELL_KNIFE, ModItems.DRAGON_TOOTH_KNIFE)) {
            var player = helper.makeMockPlayer(GameType.SURVIVAL);
            var knife = new ItemStack(item.get());
            player.setItemInHand(InteractionHand.MAIN_HAND, knife);
            knife.postHurtEnemy(player, player);
            helper.assertTrue(knife.getDamageValue() == 1, "A knife attack must cost one durability: " + item.getId());
        }
        helper.succeed();
    }

    private static void popsicleRemainder(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.igniteForSeconds(10);
        var single = new ItemStack(ModItems.CHORUS_FRUIT_POPSICLE.get());
        helper.assertTrue(single.finishUsingItem(helper.getLevel(), player).isEmpty(),
                "The original chorus popsicle does not leave a stick");
        helper.assertTrue(!player.isOnFire(), "Popsicle must retain its fire-extinguishing effect");
        var stack = new ItemStack(ModItems.CHORUS_FRUIT_POPSICLE.get(), 2);
        var result = stack.finishUsingItem(helper.getLevel(), player);
        helper.assertTrue(result.is(ModItems.CHORUS_FRUIT_POPSICLE.get()) && result.getCount() == 1,
                "Eating from a stack must consume exactly one popsicle");
        helper.assertTrue(!player.getInventory().contains(new ItemStack(Items.STICK)),
                "Eating stacked popsicles must not insert a stick into the inventory");
        helper.succeed();
    }

    private static void stoveSyncAndReload(GameTestHelper helper) {
        BlockPos pos = helper.absolutePos(new BlockPos(0, 1, 0));
        var state = ModBlocks.END_STOVE.get().defaultBlockState().setValue(AbstractStoveBlock.LIT, true);
        helper.getLevel().setBlockAndUpdate(pos, state);
        var stove = (EndStoveBlockEntity) helper.getLevel().getBlockEntity(pos);
        for (int slot = 0; slot < 6; slot++) {
            var input = new ItemStack(ModItems.RAW_DRAGON_MEAT.get());
            helper.assertTrue(stove.placeFood(null, input, stove.getCookingRecipe(input).orElseThrow()),
                    "All six stove slots must accept food");
        }
        var registries = helper.getLevel().registryAccess();
        for (CompoundTag update : List.of(stove.getUpdateTag(registries), stove.getUpdatePacket().getTag())) {
            helper.assertTrue(update.contains("Inventory"), "Both chunk and live updates must include the stove inventory");
            var mirror = new EndStoveBlockEntity(pos, state);
            mirror.handleUpdateTag(TagValueInput.create(ProblemReporter.DISCARDING, registries, update));
            for (int slot = 0; slot < 6; slot++) {
                helper.assertTrue(mirror.getItems().getStackInSlot(slot).is(ModItems.RAW_DRAGON_MEAT.get()),
                        "Receiving the update must restore every stove slot");
            }
        }
        for (int tick = 0; tick < 100; tick++) AbstractStoveBlockEntity.serverTick(helper.getLevel(), pos, state, stove);
        var saved = stove.saveWithoutMetadata(registries);
        var restored = new EndStoveBlockEntity(pos, state);
        restored.setLevel(helper.getLevel());
        restored.loadWithComponents(TagValueInput.create(ProblemReporter.DISCARDING, registries, saved));
        for (int tick = 0; tick < 499; tick++) AbstractStoveBlockEntity.serverTick(helper.getLevel(), pos, state, restored);
        helper.assertTrue(restored.isFull(), "Reloading must preserve food and the original cooking deadline");
        AbstractStoveBlockEntity.serverTick(helper.getLevel(), pos, state, restored);
        helper.assertTrue(restored.isEmpty(), "All six slots must finish at the original 600-tick deadline after reload");
        int outputCount = helper.getLevel().getEntities(EntityType.ITEM, new AABB(pos).inflate(2),
                entity -> entity.getItem().is(ModItems.ROASTED_DRAGON_MEAT.get()))
                .stream().mapToInt(entity -> entity.getItem().getCount()).sum();
        helper.assertTrue(outputCount == 6, "Cooking after reload must release exactly six outputs");
        helper.succeed();
    }

    private static void wineCurePolicy(GameTestHelper helper) {
        var immune = BuiltInRegistries.MOB_EFFECT.getOrThrow(ResourceKey.create(Registries.MOB_EFFECT, id("wine_immune_test")));
        helper.assertTrue(immune.is(ChorusFruitWineItem.IMMUNE_EFFECTS), "Wine immunity tag must be loaded");
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.addEffect(new MobEffectInstance(immune, 200));
        player.addEffect(new MobEffectInstance(MobEffects.POISON, 200));
        player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 200));
        player.addEffect(new MobEffectInstance(MobEffects.SPEED, 200));
        var result = new ItemStack(ModItems.CHORUS_FRUIT_WINE.get()).finishUsingItem(helper.getLevel(), player);
        helper.assertTrue(result.is(Items.GLASS_BOTTLE), "Wine must return its bottle");
        helper.assertTrue(player.hasEffect(immune) && player.hasEffect(MobEffects.SPEED),
                "Wine must preserve immune and beneficial effects");
        helper.assertTrue(player.hasEffect(MobEffects.POISON) != player.hasEffect(MobEffects.SLOWNESS),
                "Wine must remove exactly one eligible negative effect");

        var protectedPlayer = helper.makeMockPlayer(GameType.SURVIVAL);
        protectedPlayer.addEffect(new MobEffectInstance(MobEffects.POISON, 200));
        int[] removals = {0};
        Consumer<MobEffectEvent.Remove> listener = event -> {
            if (event.getEntity() == protectedPlayer) {
                removals[0]++;
                event.setCanceled(true);
            }
        };
        NeoForge.EVENT_BUS.addListener(listener);
        try {
            new ItemStack(ModItems.CHORUS_FRUIT_WINE.get()).finishUsingItem(helper.getLevel(), protectedPlayer);
            helper.assertTrue(removals[0] == 1 && protectedPlayer.hasEffect(MobEffects.POISON),
                    "Wine must honor the removal event and fire it once");
        } finally {
            NeoForge.EVENT_BUS.unregister(listener);
        }
        helper.succeed();
    }

    private static void dragonLegServings(GameTestHelper helper) {
        BlockPos pos = helper.absolutePos(new BlockPos(0, 1, 0));
        BlockPos other = pos.north();
        var head = ModBlocks.DRAGON_LEG_BLOCK.get().defaultBlockState().setValue(DragonLegBlock.FACING, Direction.NORTH);
        helper.getLevel().setBlock(pos, head, 2);
        helper.getLevel().setBlock(other, head.setValue(DragonLegBlock.PART, BedPart.FOOT), 3);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var bowls = new ItemStack(Items.BOWL, 6);
        player.setItemInHand(InteractionHand.MAIN_HAND, bowls);
        for (int portion = 1; portion <= 6; portion++) {
            BlockPos clicked = portion % 2 == 0 ? other : pos;
            var state = helper.getLevel().getBlockState(clicked);
            state.useItemOn(player.getMainHandItem(), helper.getLevel(), player, InteractionHand.MAIN_HAND,
                    new BlockHitResult(Vec3.atCenterOf(clicked), Direction.UP, clicked, false));
            helper.assertTrue(helper.getLevel().getBlockState(pos).getValue(DragonLegBlock.SERVINGS) == 6 - portion
                            && helper.getLevel().getBlockState(other).getValue(DragonLegBlock.SERVINGS) == 6 - portion,
                    "Taking a serving from either half must update both halves");
        }
        helper.assertTrue(bowls.isEmpty(), "Each serving must consume one bowl in survival");
        helper.assertTrue(player.getInventory().countItem(ModItems.DRAGON_LEG_WITH_SAUCE.get()) == 6,
                "Six bowls must produce exactly six servings");
        helper.getLevel().getBlockState(pos).useItemOn(ItemStack.EMPTY, helper.getLevel(), player, InteractionHand.MAIN_HAND,
                new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, false));
        helper.assertTrue(helper.getLevel().getBlockState(pos).isAir() && helper.getLevel().getBlockState(other).isAir(),
                "Clearing the leftovers must remove both halves");
        helper.succeed();
    }

    private static void consumption(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.getFoodData().setFoodLevel(0);
        var serving = new ItemStack(ModItems.STEAMED_DRAGON_EGG.get());
        var remainder = serving.finishUsingItem(helper.getLevel(), player);
        helper.assertTrue(remainder.is(Items.BOWL), "Steamed egg must return its bowl");
        helper.assertTrue(player.getFoodData().getFoodLevel() == 12, "Food nutrition changed");
        helper.assertTrue(player.hasEffect(MobEffects.REGENERATION), "Food effects were lost during migration");
        helper.assertTrue(player.getEffect(MobEffects.REGENERATION).getAmplifier() == 2, "Wrong effect strength");
        var tea = new ItemStack(ModItems.BUBBLE_TEA.get());
        helper.assertTrue(tea.get(DataComponents.CONSUMABLE).animation() == ItemUseAnimation.DRINK,
                "Tea must use the drinking animation");
        helper.assertTrue(new ItemStack(ModItems.ENDERMAN_GRISTLE.get()).getUseDuration(player) == 16,
                "Fast foods must retain their original use duration");
        var knife = new ItemStack(ModItems.DRAGON_TOOTH_KNIFE.get());
        helper.assertTrue(knife.getMaxDamage() == 1561, "Knife durability changed");
        helper.assertTrue(knife.get(DataComponents.TOOL).getMiningSpeed(Blocks.PUMPKIN.defaultBlockState()) == 8.0F,
                "Knife must use its material speed on knife-mineable blocks");
        helper.assertTrue(knife.get(DataComponents.REPAIRABLE).isValidRepairItem(new ItemStack(ModItems.DRAGON_TOOTH.get())),
                "Knife repair ingredient did not load");
        helper.succeed();
    }

    private static void stoveAndTeleport(GameTestHelper helper) {
        BlockPos stovePos = new BlockPos(0, 1, 0);
        helper.setBlock(stovePos, ModBlocks.END_STOVE.get().defaultBlockState().setValue(AbstractStoveBlock.LIT, true));
        var stove = (EndStoveBlockEntity) helper.getLevel().getBlockEntity(helper.absolutePos(stovePos));
        helper.assertTrue(stove != null && stove.getItems().getSlots() == 6, "End stove must retain all six slots");
        var rawMeat = new ItemStack(ModItems.RAW_DRAGON_MEAT.get());
        var recipe = stove.getCookingRecipe(rawMeat).orElseThrow();
        helper.assertTrue(stove.placeFood(null, rawMeat, recipe), "Stove must accept its campfire cooking recipes");
        for (int tick = 0; tick < 601; tick++) {
            AbstractStoveBlockEntity.serverTick(helper.getLevel(), stove.getBlockPos(), stove.getBlockState(), stove);
        }
        helper.assertTrue(stove.isEmpty(), "Stove must finish cooking and release its output");
        helper.assertItemEntityPresent(ModItems.ROASTED_DRAGON_MEAT.get(), stovePos.above(), 2.0);

        BlockPos landingPos = helper.absolutePos(new BlockPos(2, 1, 0));
        helper.getLevel().setBlockAndUpdate(landingPos.below(), Blocks.END_STONE.defaultBlockState());
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setPos(landingPos.getX() - 1, landingPos.getY() - 1, landingPos.getZ());
        boolean teleported = EndermanGristleTransport.randomTeleport(player,
                landingPos.getX() + 0.5, landingPos.getY(), landingPos.getZ() + 0.5, false, 0.3F);
        helper.assertTrue(teleported, "Gristle teleport failed at a valid landing position");
        helper.assertTrue(player.getHealth() == 14.0F, "Teleport must retain its health cost");
        helper.succeed();
    }
}
