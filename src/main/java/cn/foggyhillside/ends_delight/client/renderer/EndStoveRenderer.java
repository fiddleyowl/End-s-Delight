package cn.foggyhillside.ends_delight.client.renderer;

import cn.foggyhillside.ends_delight.block.entity.EndStoveBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import vectorwing.farmersdelight.client.renderer.DefaultStoveRenderer;

public class EndStoveRenderer extends DefaultStoveRenderer<EndStoveBlockEntity> {
    public EndStoveRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }
}
