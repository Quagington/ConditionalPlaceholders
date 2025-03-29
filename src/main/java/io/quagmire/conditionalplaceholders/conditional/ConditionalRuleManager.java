package io.quagmire.conditionalplaceholders.conditional;

import io.quagmire.conditionalplaceholders.ConditionalPlaceholdersPlugin;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConditionalRuleManager {
  private final ConditionalPlaceholdersPlugin plugin;
  private final Map<String, ConditionalRuleSet> rules;

  public ConditionalRuleManager(ConditionalPlaceholdersPlugin plugin) {
    this.plugin = plugin;
    this.rules = new HashMap<>();
  }

  public @Nullable ConditionalRuleSet getRule(String id) {
    return rules.get(id);
  }

  public void reload() {
    try {
      File directory = new File(plugin.getDataFolder(), "placeholders");
      if (!directory.exists() || !directory.isDirectory()) {
        directory.mkdir();
        return;
      }

      File[] files = getFilesRecursively(directory);

      Map<String, ConditionalRuleSet> rules = new HashMap<>();
      for (File file : files) {
        try {
          if (!file.isFile() || !file.getName().endsWith(".yml")) continue;

          YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
          for (String key : yamlConfiguration.getKeys(false)) {
            ConditionalRuleSet rule = ConditionalRuleSet.deserialize(key, yamlConfiguration.getConfigurationSection(key));
            if (rules.containsKey(key)) {
              plugin.getLogger().warning("Found duplicate rule set item with id " + key + " in " + file.getPath());
              continue;
            }

            if (rule == null) {
              plugin.getLogger().warning("Failed to load rule set " + file.getName());
              continue;
            }

            rules.put(key, rule);
            plugin.getLogger().info("Loaded placeholder rule " + rule.getId());
          }
        } catch (Exception e) {
          plugin.getLogger().warning("Failed to load descriptor " + file.getName());
          e.printStackTrace();
        }
      }

      this.rules.clear();
      this.rules.putAll(rules);
    } catch (Exception e) {
      e.printStackTrace();
      plugin.getLogger().severe("Failed to parse placeholders!");
    }
  }

  private File[] getFilesRecursively(File directory) {
    List<File> fileList = new ArrayList<>();
    addFilesRecursively(directory, fileList);
    return fileList.toArray(new File[0]);
  }

  private void addFilesRecursively(File directory, List<File> fileList) {
    File[] files = directory.listFiles();
    if (files == null) return;

    for (File file : files) {
      if (file.isDirectory()) {
        addFilesRecursively(file, fileList);
      } else {
        fileList.add(file);
      }
    }
  }
}
