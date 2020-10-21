package org.l2x9.chatbridgebeta.bukkitevents;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.l2x9.chatbridgebeta.ChatBridgeBeta;

import java.awt.*;

public class PlayerJoin extends PlayerListener {
    ChatBridgeBeta plugin;

    public PlayerJoin(ChatBridgeBeta chatBridgeBeta) {
        plugin = chatBridgeBeta;
    }

    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        sendEmbed(event.getPlayer().getName(), plugin.getChannel());
    }

    private void sendEmbed(String playerName, TextChannel channel) {
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setDescription(":white_check_mark: " + playerName);
        embedBuilder.setColor(Color.GREEN);

        channel.sendMessage(embedBuilder.build()).queue();
    }
}
