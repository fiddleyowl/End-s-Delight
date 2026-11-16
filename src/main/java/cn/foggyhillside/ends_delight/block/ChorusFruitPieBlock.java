package cn.foggyhillside.ends_delight.block;

import cn.foggyhillside.ends_delight.utility.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.block.PieBlock;

import java.util.function.Supplier;

public class ChorusFruitPieBlock extends PieBlock {

    public ChorusFruitPieBlock(Properties properties, Supplier<Item> pieSlice) {
        super(properties, pieSlice);
    }

    @Override
    protected InteractionResult consumeBite(Level level, BlockPos pos, BlockState state, Player player) {
        if (!player.canEat(false)) return InteractionResult.PASS;
        if (player.isShiftKeyDown()) Utils.BlockChorusFruitTeleport(level, player);

        // A placed pie feeds the player without consuming a held item, awarding item-use
        // statistics, or starting the slice's cooldown, matching the 1.21.1 block path.
        var slice = getPieSliceItem();
        var food = slice.get(DataComponents.FOOD);
        if (food != null) {
            player.getFoodData().eat(food);
            var consumable = slice.get(DataComponents.CONSUMABLE);
            if (!level.isClientSide() && consumable != null) {
                consumable.onConsumeEffects().forEach(effect -> effect.apply(level, slice, player));
            }
        }
        int bites = state.getValue(BITES) + 1;
        if (bites < getMaxBites()) level.setBlock(pos, state.setValue(BITES, bites), 3);
        else level.removeBlock(pos, false);

        level.playSound(null, pos, SoundEvents.GENERIC_EAT.value(), SoundSource.PLAYERS, 0.8F, 0.8F);
        if (level instanceof ServerLevel server) {
            server.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state),
                    pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5, 3, 0.1, 0.1, 0.1, 0.001);
        }
        return InteractionResult.SUCCESS;
    }

}
