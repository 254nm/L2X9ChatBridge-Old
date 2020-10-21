package org.l2x9.chatbridgebeta.antispam;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;
import org.l2x9.chatbridgebeta.ChatBridgeBeta;

import java.util.Arrays;
import java.util.List;

public class ChatEvent extends PlayerListener {
    ChatBridgeBeta plugin;

    public ChatEvent(ChatBridgeBeta chatBridgeBeta) {
        plugin = chatBridgeBeta;
    }

    @Override
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        List<String> list = Arrays.asList("discord", ".", "dot");

        for (String word : list) {
            if (event.getMessage().toLowerCase().contains(word.toLowerCase())) {
                event.setCancelled(true);
                return;
            }
        }

        if (!event.getMessage().startsWith(">")) {
            sendMessage(event.getPlayer(), "<" + player.getName() + "> " + event.getMessage());
        } else {
            sendMessage(event.getPlayer(), "<" + player.getName() + "> " + "&a" + event.getMessage());
        }
    }

    private void sendMessage(Player player, String message) {
        player.sendMessage(message.replace("&", "§"));
    }
}