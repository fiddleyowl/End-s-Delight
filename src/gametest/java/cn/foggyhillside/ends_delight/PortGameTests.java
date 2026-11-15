package cn.foggyhillside.ends_delight;

import cn.foggyhillside.ends_delight.block.entity.EndStoveBlockEntity;
import cn.foggyhillside.ends_delight.registry.ModBlocks;
import cn.foggyhillside.ends_delight.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import vectorwing.farmersdelight.common.block.AbstractStoveBlock;
import vectorwing.farmersdelight.common.block.entity.AbstractStoveBlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.resource.NeoForgeReloadListeners;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.Map;
import java.util.function.Consumer;

/** Runs only from the development source set; excluded from the distributed jar. */
@EventBusSubscriber(modid = EndsDelight.MODID)
public class PortGameTests {
    private static final Map<String, Consumer<GameTestHelper>> TESTS = Map.of(
            "resources", PortGameTests::resources,
            "consumption", PortGameTests::consumption,
            "pie_teleport", PortGameTests::pieTeleport,
            "stove_and_teleport", PortGameTests::stoveAndTeleport);

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(EndsDelight.MODID, path);
    }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        TESTS.forEach((name, function) -> event.register(Registries.TEST_FUNCTION, id(name), () -> function));
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
