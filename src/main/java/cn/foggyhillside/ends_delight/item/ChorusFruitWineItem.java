package cn.foggyhillside.ends_delight.item;

import cn.foggyhillside.ends_delight.EndsDelight;
import cn.foggyhillside.ends_delight.utility.Utils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.item.DrinkableItem;

public class ChorusFruitWineItem extends DrinkableItem {

    // The per-instance EffectCures API was removed in 26.1. Packs can preserve
    // their former milk-cure exclusions through this effect tag.
    public static final TagKey<MobEffect> IMMUNE_EFFECTS = TagKey.create(Registries.MOB_EFFECT,
            Identifier.fromNamespaceAndPath(EndsDelight.MODID, "immune_to_chorus_fruit_wine"));

    public ChorusFruitWineItem(Item.Properties properties) {
        super(properties, false, true);
    }

    @Override
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        Utils.ItemChorusFruitTeleport(stack, level, consumer);
        var candidates = consumer.getActiveEffects().stream()
                .map(effect -> effect.getEffect())
                .filter(effect -> effect.value().getCategory() == MobEffectCategory.HARMFUL && !effect.is(IMMUNE_EFFECTS))
                .toList();
        if (!candidates.isEmpty()) {
            // removeEffect fires the cancellable NeoForge removal event.
            consumer.removeEffect(candidates.get(level.getRandom().nextInt(candidates.size())));
        }
    }

}
