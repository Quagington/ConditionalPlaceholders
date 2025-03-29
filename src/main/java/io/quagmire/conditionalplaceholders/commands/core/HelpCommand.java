package io.quagmire.conditionalplaceholders.commands.core;

import io.quagmire.conditionalplaceholders.ConditionalPlaceholdersPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class HelpCommand extends io.quagmire.core.commands.help.HelpCommand<ConditionalPlaceholdersPlugin> {
  public HelpCommand(ConditionalPlaceholdersPlugin plugin, Command command, String[] args, CommandSender sender) {
    super(plugin, command, args, sender, plugin.getCommandRegistry());
  }
}
