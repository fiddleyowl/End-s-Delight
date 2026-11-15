package cn.foggyhillside.ends_delight.block;

import net.minecraft.world.item.Item;
import vectorwing.farmersdelight.common.block.PieBlock;

import java.util.function.Supplier;

// PieBlock now consumes the slice item itself, including its shift-triggered teleport.
public class ChorusFruitPieBlock extends PieBlock {

    public ChorusFruitPieBlock(Properties properties, Supplier<Item> pieSlice) {
        super(properties, pieSlice);
    }

}
