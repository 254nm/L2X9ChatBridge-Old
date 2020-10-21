package org.l2x9.chatbridge.bukkitevents;

import io.papermc.lib.PaperLib;
import me.alexprogrammerde.headapi.HeadAPI;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.l2x9.chatbridge.ChatBridge;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class PlayerJoin implements Listener {
    ChatBridge plugin;

    public PlayerJoin(ChatBridge chatBridge) {
        plugin = chatBridge;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        sendEmbed(event.getPlayer(), plugin.getChannel());
    }

    private void sendEmbed(Player player, TextChannel channel) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setDescription(":white_check_mark: " + player.getName());
        embedBuilder.setColor(Color.GREEN);

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
