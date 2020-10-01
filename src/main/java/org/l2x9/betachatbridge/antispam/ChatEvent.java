package org.l2x9.betachatbridge.antispam;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

import java.util.Arrays;
import java.util.List;

public class ChatEvent extends PlayerListener {
    Cooldown cm = new Cooldown();

    @Override
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        if (cm.checkCooldown(player)) {
            cm.setCooldown(player, 3);
        } else {
            event.setCancelled(true);

        }
        List<String> list = Arrays.asList("discord", ".", "dot");
        boolean hasBlackListedWord = false;
        for (String word : list) {
            if (event.getMessage().toLowerCase().contains(word)) {
                event.setCancelled(true);
                hasBlackListedWord = true;

            }
        }
        if (hasBlackListedWord) {
            if (!event.getMessage().startsWith(">")) {
                sendMessage(event.getPlayer(), "<" + player.getName() + "> " + event.getMessage());

            } else {
                sendMessage(event.getPlayer(), "<" + player.getName() + "> " + "&a" + event.getMessage());
            }
        }
    }

    private void sendMessage(Player player, String message) {
        player.sendMessage(message.replace("&", "§"));
    }
}