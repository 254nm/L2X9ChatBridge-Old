package org.l2x9.betachatbridge.bukkitevents;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.l2x9.betachatbridge.BetaChatBridge;
import org.l2x9.betachatbridge.antispam.Cooldown;

import java.awt.*;

public class PlayerChat implements Listener {
    BetaChatBridge plugin;

    public PlayerChat(BetaChatBridge betaChatBridge) {
        plugin = betaChatBridge;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Cooldown cm = plugin.cm;
        Player player = event.getPlayer();
        if (cm.checkCooldown(player)) {
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