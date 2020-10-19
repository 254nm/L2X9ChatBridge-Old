package org.l2x9.chatbridgebeta.bukkitevents;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.l2x9.chatbridgebeta.ChatBridgeBeta;

import java.awt.*;

public class PlayerQuit extends PlayerListener {
    ChatBridgeBeta plugin;

    public PlayerQuit(ChatBridgeBeta chatBridgeBeta) {
        plugin = chatBridgeBeta;
    }

    @Override
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
