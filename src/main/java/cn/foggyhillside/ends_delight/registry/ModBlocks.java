package cn.foggyhillside.ends_delight.registry;

import cn.foggyhillside.ends_delight.EndsDelight;
import cn.foggyhillside.ends_delight.block.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.ToIntFunction;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(EndsDelight.MODID);

    public static final DeferredBlock<Block> CHORUS_FRUIT_PIE = BLOCKS.register("chorus_fruit_pie",
            id -> new ChorusFruitPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE).setId(ResourceKey.create(Registries.BLOCK, id)), ModItems.CHORUS_FRUIT_PIE_SLICE));
    public static final DeferredBlock<Block> CHORUS_SUCCULENT = BLOCKS.register("chorus_succulent",
            id -> new ChorusSucculentBlock(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, id)).strength(0.0F, 0.0F).mapColor(MapColor.COLOR_PURPLE).lightLevel((state) -> {
                return 1 + 2 * state.getValue(ChorusSucculentBlock.SUCCULENT);
            }).sound(SoundType.FUNGUS).noOcclusion().pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> STEAMED_DRAGON_EGG_BLOCK = BLOCKS.register("steamed_dragon_egg_block",
            id -> new DragonEggFeastBlock(Block.Properties.ofFullCopy(Blocks.DRAGON_EGG).setId(ResourceKey.create(Registries.BLOCK, id)),ModItems.STEAMED_DRAGON_EGG,true));
    public static final DeferredBlock<Block> DRAGON_MEAT_STEW_BLOCK = BLOCKS.register("dragon_meat_stew_block",
            id -> new DragonEggFeastBlock(Block.Properties.ofFullCopy(Blocks.DRAGON_EGG).setId(ResourceKey.create(Registries.BLOCK, id)),ModItems.DRAGON_MEAT_STEW,true));
    public static final DeferredBlock<Block> GRILLED_SHULKER_BLOCK = BLOCKS.register("grilled_shulker_block",
            id -> new GrilledShulkerBlock(Block.Properties.ofFullCopy(Blocks.SHULKER_BOX).setId(ResourceKey.create(Registries.BLOCK, id)),ModItems.GRILLED_SHULKER,true));
    public static final DeferredBlock<Block> END_STOVE = BLOCKS.register("end_stove",
            id -> new EndStoveBlock(Block.Properties.ofFullCopy(Blocks.BRICKS).setId(ResourceKey.create(Registries.BLOCK, id)).lightLevel(litBlockEmission(13))));
    public static final DeferredBlock<Block> DRAGON_LEG_BLOCK = BLOCKS.register("dragon_leg_with_sauce_block",
            id -> new DragonLegBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE).setId(ResourceKey.create(Registries.BLOCK, id))));
    public static final DeferredBlock<Block> CHORUS_FRUIT_CRATE = BLOCKS.register("chorus_fruit_crate",
            id -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).setId(ResourceKey.create(Registries.BLOCK, id)).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    private static ToIntFunction<BlockState> litBlockEmission(int lightValue) {
        return (state) -> state.getValue(BlockStateProperties.LIT) ? lightValue : 0;
    }
}
