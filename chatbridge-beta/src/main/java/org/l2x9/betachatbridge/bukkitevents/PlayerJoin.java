package org.l2x9.betachatbridge.bukkitevents;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.l2x9.betachatbridge.BetaChatBridge;

import java.awt.*;

public class PlayerJoin extends PlayerListener {
    BetaChatBridge plugin;

    public PlayerJoin(BetaChatBridge betaChatBridge) {
        plugin = betaChatBridge;
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
