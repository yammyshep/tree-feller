package com.thizthizzydizzy.treefeller;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.data.EnchantmentRegistryEntry;
import io.papermc.paper.registry.event.RegistryEvents;
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;

@SuppressWarnings("UnstableApiUsage")
public class TreeFellerBootstrap implements PluginBootstrap {
    public static final TypedKey<Enchantment> TREEFELLER_ENCHANT_KEY = TypedKey.create(RegistryKey.ENCHANTMENT, Key.key("treefeller:treefeller"));

    @Override
    public void bootstrap(BootstrapContext context) {
        //TODO: Preload configuration and only register enchantment if enabled
        context.getLifecycleManager().registerEventHandler(RegistryEvents.ENCHANTMENT.freeze().newHandler(event -> {
            event.registry().register(TREEFELLER_ENCHANT_KEY,
                builder -> builder.description(Component.text("TreeFeller"))
                    .supportedItems(event.getOrCreateTag(ItemTypeTagKeys.AXES))
                    .anvilCost(1)
                    .maxLevel(1)
                    .weight(10)
                    .minimumCost(EnchantmentRegistryEntry.EnchantmentCost.of(1,1))
                    .maximumCost(EnchantmentRegistryEntry.EnchantmentCost.of(3, 1))
                    .activeSlots(EquipmentSlotGroup.ANY)
            );
        }));
    }
}
