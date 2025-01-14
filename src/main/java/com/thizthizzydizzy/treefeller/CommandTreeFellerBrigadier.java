package com.thizthizzydizzy.treefeller;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.ParsedCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.thizthizzydizzy.treefeller.menu.MenuConfiguration;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("UnstableApiUsage")
public final class CommandTreeFellerBrigadier {
    public static LiteralCommandNode<CommandSourceStack> buildCommand(TreeFeller plugin) {
        return Commands.literal("treefeller").executes(ctx -> {
                    Message.getMessage("unknown-command").send(ctx.getSource().getSender());
                    return 1;
                })
                .then(Commands.literal("help").executes(ctx -> {
                    StringBuilder helpText = new StringBuilder("TreeFeller Help:");
                    for (CommandNode<CommandSourceStack> node : ctx.getRootNode().getChildren()) {
                        helpText.append("\n /treefeller ");
                        helpText.append(node.getUsageText());
                    }
                    ctx.getSource().getSender().sendMessage(helpText.toString());
                    return Command.SINGLE_SUCCESS;
                }))
                .then(Commands.literal("toggle").executes(ctx -> {
                    plugin.toggle((Player) ctx.getSource().getSender());
                    return Command.SINGLE_SUCCESS;
                })
                    .then(Commands.literal("on").executes(ctx -> {
                        plugin.toggle((Player) ctx.getSource().getSender(), true);
                        return Command.SINGLE_SUCCESS;
                    }))
                    .then(Commands.literal("off").executes(ctx -> {
                        plugin.toggle((Player) ctx.getSource().getSender(), false);
                        return Command.SINGLE_SUCCESS;
                    }))
                )
                .then(Commands.literal("reload").executes(ctx -> {
                    plugin.reloadConfig();
                    plugin.reload(ctx.getSource().getSender());
                    Message.getMessage("reload").send(ctx.getSource().getSender());
                    return Command.SINGLE_SUCCESS;
                }))
                .then(Commands.literal("debug").executes(ctx -> {
                    plugin.debug = !plugin.debug;
                    Message.getMessage("debug-"+(plugin.debug?"enable":"disable")).send(ctx.getSource().getSender());
                    return Command.SINGLE_SUCCESS;
                })
                    .then(Commands.literal("on").executes(ctx -> {
                        plugin.debug = true;
                        Message.getMessage("debug-enable").send(ctx.getSource().getSender());
                        return Command.SINGLE_SUCCESS;
                    }))
                    .then(Commands.literal("off").executes(ctx -> {
                        plugin.debug = false;
                        Message.getMessage("debug-disable").send(ctx.getSource().getSender());
                        return Command.SINGLE_SUCCESS;
                    }))
                )
                .then(Commands.literal("config").executes(ctx -> {
                    CommandSender sender = ctx.getSource().getSender();
                    if (!(sender instanceof Player)) {
                        sender.sendMessage("You're not a player!");
                        return 1;
                    }
                    new MenuConfiguration(null, plugin, (Player) sender).openInventory();
                    return Command.SINGLE_SUCCESS;
                }))
            .build();
    }
}
