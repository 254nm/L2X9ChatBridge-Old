package org.l2x9.chatbridgebeta.bukkitevents;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.l2x9.chatbridgebeta.ChatBridge;

import java.awt.*;

public class PlayerQuit implements Listener {
    ChatBridge plugin;

    public PlayerQuit(ChatBridge chatBridge) {
        plugin = chatBridge;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        sendEmbed(event.getPlayer().getName(), plugin.getChannel());
    }

    private void sendEmbed(String playerName, TextChannel channel) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setDescription(":heavy_minus_sign: " + playerName);
        embedBuilder.setColor(Color.RED);
        channel.sendMessage(embedBuilder.build()).queue();
    }
}
