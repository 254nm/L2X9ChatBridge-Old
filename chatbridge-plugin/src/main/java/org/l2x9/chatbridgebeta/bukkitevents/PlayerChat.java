package org.l2x9.chatbridgebeta.bukkitevents;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.l2x9.chatbridgebeta.ChatBridge;
import org.l2x9.chatbridgebeta.antispam.Cooldown;

import java.awt.*;
import java.util.Objects;

public class PlayerChat implements Listener {
    ChatBridge plugin;

    public PlayerChat(ChatBridge chatBridge) {
        plugin = chatBridge;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Cooldown cm = plugin.cm;
        Player player = event.getPlayer();
        String[] words = event.getMessage().split(" ");
        boolean hasBlockedWord = false;
        for (String word : words) {
            if (Objects.requireNonNull(plugin.getL2X9CoreBlockedWords()).contains(word.toLowerCase())) {
                hasBlockedWord = true;
                break;
            }
        }
        if (cm.checkCooldown(player) && !hasBlockedWord) {
            cm.setCooldown(player, 1);
            sendEmbed(event.getMessage().replace("§a", ""), player.getName(), plugin.getChannel());
        }
    }

    private void sendEmbed(String message, String playerName, TextChannel channel) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setDescription("**<" + playerName + ">** " + message);
        if (message.startsWith(">")) {
            embedBuilder.setColor(new Color(110, 255, 59));
        } else {
            embedBuilder.setColor(Color.GRAY);
        }
        channel.sendMessage(embedBuilder.build()).queue();
    }
}