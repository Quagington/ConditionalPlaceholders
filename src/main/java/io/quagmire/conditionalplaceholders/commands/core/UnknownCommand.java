package io.quagmire.conditionalplaceholders.commands.core;

import io.quagmire.conditionalplaceholders.ConditionalPlaceholdersPlugin;
import io.quagmire.core.commands.CoreCommandRegistry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class UnknownCommand extends io.quagmire.core.commands.unknown.UnknownCommand<ConditionalPlaceholdersPlugin> {
  public UnknownCommand(ConditionalPlaceholdersPlugin plugin, Command command, String[] args, CommandSender sender) {
    super(plugin, command, args, sender, plugin.getCommandRegistry());
  }

  @Override
  protected String getMessage() {
    CoreCommandRegistry<ConditionalPlaceholdersPlugin> registry = plugin.getCommandRegistry();
    String primaryColor = registry.getPrimaryColor();
    String secondaryColor = registry.getSecondaryColor();
    return primaryColor + "Unknown command provided. " + secondaryColor + "Use " + primaryColor + "/" + registry.getAlias() + " help" + secondaryColor + " for a list of commands.";
  }
}
