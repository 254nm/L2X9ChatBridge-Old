package org.l2x9.chatbridgebeta.bukkitevents;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.l2x9.chatbridgebeta.ChatBridge;

import java.awt.*;

public class PlayerJoin extends PlayerListener {
    ChatBridge plugin;

    public PlayerJoin(ChatBridge chatBridge) {
        plugin = chatBridge;
    }

    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        sendEmbed(event.getPlayer().getName(), plugin.getChannel());
    }

    private void sendEmbed(String playerName, TextChannel channel) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setDescription(":heavy_plus_sign: " + playerName);
        embedBuilder.setColor(Color.GREEN);
        channel.sendMessage(embedBuilder.build()).queue();
    }
}
