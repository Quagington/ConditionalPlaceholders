package io.quagmire.conditionalplaceholders.commands;

import io.quagmire.conditionalplaceholders.ConditionalPlaceholdersPlugin;
import io.quagmire.conditionalplaceholders.messages.Message;
import io.quagmire.core.commands.CoreCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Map;

public abstract class ConditionalPlaceholdersCommand extends CoreCommand<ConditionalPlaceholdersPlugin> {
  public ConditionalPlaceholdersCommand(ConditionalPlaceholdersPlugin plugin, Command command, String[] args, CommandSender sender) {
    super(plugin, command, args, sender, plugin.getCommandRegistry());
  }

  protected void messageSender(Message message) {
    super.messageSender(message.name(), true);
  }

  protected void messageSender(Message message, Map<String, String> renders) {
    super.messageSender(message.name(), true, renders);
  }
}
