# ConditionalPlaceholders

ConditionalPlaceholders is a powerful Minecraft plugin that extends PlaceholderAPI by adding conditional logic to placeholders. It allows server administrators to create dynamic, condition-based content that changes based on placeholder values.

![Version](https://img.shields.io/badge/version-1.0.0-blue.svg)
![API Version](https://img.shields.io/badge/api--version-1.21-yellow.svg)
![Folia Support](https://img.shields.io/badge/folia--support-yes-brightgreen.svg)

## Features

- Apply conditional logic to any PlaceholderAPI placeholder
- Support for multiple comparison operators: `=`, `!=`, `>`, `>=`, `<`, `<=`
- Define fallback values when no conditions match
- Easy to configure rule-based system
- Full color code support in outputs
- Folia compatible

## Requirements

- Java 21 or higher
- Paper/Spigot 1.20.4+
- PlaceholderAPI
- Quagmire Core

## Installation

1. Download the latest version of ConditionalPlaceholders
2. Place the JAR file in your server's `plugins` folder
3. Restart your server
4. Configure the plugin using the `config.yml` file

## Usage

### Basic Syntax

The basic syntax for using conditional placeholders is:

```
%conditional_rulename%
```

Where `rulename` is the name of a rule you've defined in the configuration.

### Configuration

Rules are defined in the `config.yml` file. Here's an example structure:

```yaml
rules:
  health_status:
    placeholder: "%player_health%"
    rules:
      - "<=5;&#FF0000&lCRITICAL"
      - "<=10;&#FF7F00&lLOW"
      - "<=15;&#FFFF00&lMEDIUM"
      - "*;&#00FF00&lHEALTHY"
  
  rank_display:
    placeholder: "%luckperms_primary_group_name%"
    rules:
      - "=admin;&#FF0000Administrator"
      - "=mod;&#00FF00Moderator"
      - "=vip;&#FFD700VIP Player"
      - "*;&#AAAAAA Player"
```

### Rule Format

Each rule follows this format:

```
"condition;format"
```

- **condition**: The comparison operator and value to check against
- **format**: The text to display if the condition is met (supports colors and placeholders)

The wildcard condition `*` is used as a fallback when no other conditions match.

### Comparison Operators

The following operators are supported:

- `=` - Equal to
- `!=` - Not equal to
- `>` - Greater than
- `>=` - Greater than or equal to
- `<` - Less than
- `<=` - Less than or equal to

### Examples

**Display different messages based on player health:**
```
Your health is %conditional_health_status%
```

**Show rank-specific messages:**
```
Welcome, %conditional_rank_display%!
```

**Create conditional economy displays:**
```
Balance: %conditional_money_display%
```

## Commands

- `/conditionalplaceholders` or `/cpl` - Base command
- `/cpl help` - Shows the help menu
- `/cpl reload` - Reloads the plugin configuration

## Permissions

- `conditionalplaceholders.command` - Permission to use basic plugin commands
- `conditionalplaceholders.reload` - Permission to use the reload command

## Developer API

For developers looking to integrate with ConditionalPlaceholders, you can access the plugin instance and rule manager:

```java
ConditionalPlaceholdersPlugin plugin = (ConditionalPlaceholdersPlugin) Bukkit.getPluginManager().getPlugin("ConditionalPlaceholders");
ConditionalRuleManager ruleManager = plugin.getRuleManager();
```

## Support

If you encounter any issues or have questions, feel free to create an issue on our GitHub repository.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contributing

We welcome contributions to the project! If you'd like to contribute:

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -am 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Credits

- **Author**: Quagmire (me@quagmire.dev) 