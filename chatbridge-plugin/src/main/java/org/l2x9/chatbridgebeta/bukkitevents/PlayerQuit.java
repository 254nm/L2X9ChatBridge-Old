package org.l2x9.chatbridgebeta.bukkitevents;

import me.alexprogrammerde.headapi.HeadAPI;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.l2x9.chatbridgebeta.ChatBridge;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PlayerQuit implements Listener {
    ChatBridge plugin;

    public PlayerQuit(ChatBridge chatBridge) {
        plugin = chatBridge;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        sendEmbed(event.getPlayer(), plugin.getChannel());
    }

    private void sendEmbed(Player player, TextChannel channel) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setDescription(":no_entry: " + player.getName());
        embedBuilder.setColor(Color.RED);
        embedBuilder.setImage("attachment://head.png");

        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(HeadAPI.getHeadImage(player), "png", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());

            channel.sendFile(is, "head.png").embed(embedBuilder.build()).queue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
