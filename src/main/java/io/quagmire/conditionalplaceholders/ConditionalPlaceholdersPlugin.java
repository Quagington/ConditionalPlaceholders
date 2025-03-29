package io.quagmire.conditionalplaceholders;

import io.quagmire.conditionalplaceholders.commands.core.HelpCommand;
import io.quagmire.conditionalplaceholders.commands.core.UnknownCommand;
import io.quagmire.conditionalplaceholders.commands.reload.ReloadCommand;
import io.quagmire.conditionalplaceholders.conditional.ConditionalRuleManager;
import io.quagmire.conditionalplaceholders.messages.Message;
import io.quagmire.conditionalplaceholders.placeholders.PlaceholderExpansionWrapper;
import io.quagmire.core.CorePlugin;
import io.quagmire.core.chat.ChatToolkit;
import io.quagmire.core.commands.*;
import io.quagmire.core.folia.impl.PlatformScheduler;
import io.quagmire.core.messages.MessagesManager;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;

public class ConditionalPlaceholdersPlugin extends CorePlugin {
  @Getter private PlatformScheduler scheduler;

  @Getter private final MessagesManager messagesManager;
  @Getter private final ChatToolkit chatToolkit;
  @Getter private CoreCommandRegistry<ConditionalPlaceholdersPlugin> commandRegistry;

  @Getter private final ConditionalRuleManager ruleManager;
  private PlaceholderExpansionWrapper placeholderExpansionWrapper;

  public ConditionalPlaceholdersPlugin() {
    messagesManager = new MessagesManager(this);
    chatToolkit = new ChatToolkit(this);
    ruleManager = new ConditionalRuleManager(this);
  }

  @Override
  public void onEnable() {
    saveDefaultConfig();
    getConfig().options().copyDefaults(true);

    ruleManager.reload();

    scheduler = folia.getScheduler();
    messagesManager.initialize(Message.getInitializers());

    registerCommands();
    registerPlaceholders();
  }

  private void registerCommands() {
    FileConfiguration config = getConfig();
    config.options().copyDefaults(true);

    String alias = config.getString("command.alias", "cpl");
    String permissionPrefix = config.getString("command.permission-prefix", "conditionalplaceholders");
    String displayName = config.getString("command.display-name", "Conditional Placeholders");

    commandRegistry = new CoreCommandRegistry<>(this, ConditionalPlaceholdersPlugin.class);
    commandRegistry.setAlias(alias);
    commandRegistry.setPermissionPrefix(permissionPrefix);
    commandRegistry.setDisplayName(displayName);

    commandRegistry.register(HelpCommand.class);
    commandRegistry.register(ReloadCommand.class);
    commandRegistry.register(UnknownCommand.class);
    commandRegistry.setDefaultCommand("help");
    commandRegistry.setFallbackCommand("unknown");

    Objects.requireNonNull(getCommand("conditionalplaceholders")).setExecutor(new CoreCommandExecutor<>(this));
    Objects.requireNonNull(getCommand("conditionalplaceholders")).setTabCompleter(new CoreCommandTabCompleter<>(this));
  }

  private void registerPlaceholders() {
    if (getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
      getLogger().warning("PlaceholderAPI not found! Disabling plugin...");
      getServer().getPluginManager().disablePlugin(this);
      return;
    }

    placeholderExpansionWrapper = new PlaceholderExpansionWrapper(this);
    placeholderExpansionWrapper.register();
  }

  @Override
  public void onDisable() {
    if (placeholderExpansionWrapper != null) {
      placeholderExpansionWrapper.unregister();
      getLogger().info("Unregistered placeholders!");
    }

    scheduler.cancelAllTasks();
  }
}