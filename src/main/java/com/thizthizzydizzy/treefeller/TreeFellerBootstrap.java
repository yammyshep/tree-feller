package com.thizthizzydizzy.treefeller;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.data.EnchantmentRegistryEntry;
import io.papermc.paper.registry.event.RegistryEvents;
import io.papermc.paper.registry.keys.tags.EnchantmentTagKeys;
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys;
import io.papermc.paper.tag.PostFlattenTagRegistrar;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;

import java.util.Set;

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
                    .anvilCost(7)
                    .maxLevel(1)
                    .weight(1)
                    .minimumCost(EnchantmentRegistryEntry.EnchantmentCost.of(15,1))
                    .maximumCost(EnchantmentRegistryEntry.EnchantmentCost.of(50, 1))
                    .activeSlots(EquipmentSlotGroup.MAINHAND)
            );
        }));

        context.getLifecycleManager().registerEventHandler(LifecycleEvents.TAGS.postFlatten(RegistryKey.ENCHANTMENT), event -> {
            final PostFlattenTagRegistrar<Enchantment> registrar = event.registrar();
            registrar.addToTag(EnchantmentTagKeys.TRADEABLE, Set.of(TREEFELLER_ENCHANT_KEY));
            registrar.addToTag(EnchantmentTagKeys.NON_TREASURE, Set.of(TREEFELLER_ENCHANT_KEY));
            registrar.addToTag(EnchantmentTagKeys.IN_ENCHANTING_TABLE, Set.of(TREEFELLER_ENCHANT_KEY));
        });
    }
}
