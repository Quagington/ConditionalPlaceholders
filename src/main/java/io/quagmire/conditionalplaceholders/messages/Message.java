package io.quagmire.conditionalplaceholders.messages;

import io.quagmire.core.messages.MessageInitializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Message {
  PREFIX,
  ERROR_GENERIC,
  NO_PERMISSIONS,
  PLAYER_NOT_FOUND,
  PLAYER_ONLY,
  INVALID_NUMBER,
  RELOAD_SUCCESS,
  RELOAD_FAILURE;

  private static Map<Message, String> getDefaultValueMapping()  {
    Map<Message, String> map = new HashMap<>();
    map.put(Message.PREFIX, "&8| &6&lPLACEHOLDERS&8 |");
    map.put(Message.ERROR_GENERIC, "&cAn error has occurred, please try again. If the error persists please contact an administrator.!");
    map.put(Message.NO_PERMISSIONS, "&cYou do not have permission to execute this command!");
    map.put(Message.PLAYER_NOT_FOUND, "&cPlayer not found!");
    map.put(Message.INVALID_NUMBER, "&cInvalid number!");
    map.put(Message.RELOAD_SUCCESS, "%prefix% &aConfiguration reloaded!");
    map.put(Message.RELOAD_FAILURE, "%prefix% &cConfiguration could not be reloaded!");
    map.put(Message.PLAYER_ONLY, "%prefix% &cOnly players can execute this command!");
    return map;
  }

  public static String getDefaultValue(Message message) {
    return getDefaultValueMapping().get(message);
  }

  public static List<MessageInitializer> getInitializers() {
    List<MessageInitializer> initializers = new ArrayList<>();
    for (Message message : Message.values()) {
      initializers.add(new MessageInitializer(message.name().toLowerCase(), getDefaultValueMapping().get(message)));
    }
    return initializers;
  }

  public static Message getMessage(String str) {
    for (Message message : Message.values()) {
      if (message.toString().equalsIgnoreCase(str)) {
        return message;
      }
    }
    return null;
  }
}

