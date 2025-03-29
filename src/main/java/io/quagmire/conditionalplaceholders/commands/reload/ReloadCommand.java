package io.quagmire.conditionalplaceholders.commands.reload;

import io.quagmire.conditionalplaceholders.ConditionalPlaceholdersPlugin;
import io.quagmire.conditionalplaceholders.commands.ConditionalPlaceholdersCommand;
import io.quagmire.conditionalplaceholders.messages.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class ReloadCommand extends ConditionalPlaceholdersCommand {
  public ReloadCommand(ConditionalPlaceholdersPlugin plugin, Command command, String[] args, CommandSender sender) {
    super(plugin, command, args, sender);
    setDescription("Reloads the configuration.");
    setPermission(getPermissionPrefix() + ".reload");
    setSyntax("");
  }

  @Override
  public boolean validate() {
    if (!sender.hasPermission(permission)) {
      messageSender(Message.NO_PERMISSIONS);
      return false;
    }
    return true;
  }

  @Override
  public void execute() {
    try {
      plugin.reloadConfig();
      plugin.getMessagesManager().reload();
      plugin.getRuleManager().reload();
      messageSender(Message.RELOAD_SUCCESS);
    } catch (Exception ex) {
      ex.printStackTrace();
      messageSender(Message.RELOAD_FAILURE);
    }
  }

  @Override
  public List<String> tab() {
    return Collections.emptyList();
  }

  @Override
  public String subcommand() {
    return "reload";
  }
}