package org.l2x9.chatbridge.bukkitevents;

import io.papermc.lib.PaperLib;
import me.alexprogrammerde.headapi.HeadAPI;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.l2x9.chatbridge.ChatBridge;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PlayerAdvancement implements Listener {
    ChatBridge plugin;

    public PlayerAdvancement(ChatBridge chatBridge) {
        plugin = chatBridge;
    }

    @EventHandler
    public void onPlayerAdvancement(PlayerAdvancementDoneEvent event) {
        sendEmbed(event.getPlayer(), event.getAdvancement(), plugin.getChannel());
    }

    private void sendEmbed(Player player, Advancement advancement, TextChannel channel) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setDescription(":magic_wand: " + player.getName() + " did get the advancement " + advancement.getKey().getKey());
        embedBuilder.setColor(Color.RED);

        if (PaperLib.isPaper()) {
            embedBuilder.setThumbnail("attachment://head.png");

            try {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(HeadAPI.resize(HeadAPI.getHeadImage(player), 30, 30), "png", os);
                InputStream is = new ByteArrayInputStream(os.toByteArray());

                channel.sendFile(is, "head.png").embed(embedBuilder.build()).queue();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            channel.sendMessage(embedBuilder.build()).queue();
        }
    }
}