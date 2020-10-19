package org.l2x9.chatbridgebeta.bukkitevents;

import me.alexprogrammerde.headapi.HeadAPI;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.l2x9.chatbridgebeta.ChatBridge;
import org.l2x9.chatbridgebeta.antispam.Cooldown;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class PlayerChat implements Listener {
    ChatBridge plugin;

    public PlayerChat(ChatBridge chatBridge) {
        plugin = chatBridge;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Cooldown cm = plugin.cm;
        Player player = event.getPlayer();
        String[] words = event.getMessage().split(" ");
        boolean hasBlockedWord = false;
        for (String word : words) {
            if (Objects.requireNonNull(plugin.getL2X9CoreBlockedWords()).contains(word.toLowerCase())) {
                hasBlockedWord = true;
                break;
            }
        }
        if (cm.checkCooldown(player) && !hasBlockedWord) {
            cm.setCooldown(player, 1);
            sendEmbed(event.getMessage().replace("§a", ""), player, plugin.getChannel());
        }
    }

    private void sendEmbed(String message, Player player, TextChannel channel) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setDescription("**<" + player.getName() + ">** " + message);
        if (message.startsWith(">")) {
            embedBuilder.setColor(new Color(110, 255, 59));
        } else {
            embedBuilder.setColor(Color.GRAY);
        }
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