package org.l2x9.betachatbridge.bukkitevents;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.l2x9.betachatbridge.BetaChatBridge;

import java.awt.*;

public class PlayerQuit extends PlayerListener {
    BetaChatBridge plugin;

    public PlayerQuit(BetaChatBridge betaChatBridge) {
        plugin = betaChatBridge;
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
