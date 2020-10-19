package org.l2x9.chatbridgebeta.discordevents;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.l2x9.chatbridgebeta.ChatBridgeBeta;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Commands extends ListenerAdapter {
    ChatBridgeBeta plugin;

    public Commands(ChatBridgeBeta chatBridgeBeta) {
        plugin = chatBridgeBeta;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String PREFIX = "!=";
        if (event.getMessage().getContentRaw().startsWith(PREFIX)) {
            String[] args = event.getMessage().getContentRaw().split(" ");
            switch (args[0]) {
                case "!=online":
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setTitle("Online Players");
                    embedBuilder.setColor(Color.GRAY);
                    Player[] players = Bukkit.getServer().getOnlinePlayers();
                    if (players.length > 0) {
                        List<String> playerNames = new ArrayList<>();
                        for (Player player : players) {
                            playerNames.add(player.getName());
                        }
                        String nameList = String.join("\n", playerNames);
                        embedBuilder.setDescription(nameList + "\n\n" + playerNames.size() + " / 100");
                        embedBuilder.setAuthor(plugin.getJda().getSelfUser().getAsTag(), null, plugin.getJda().getSelfUser().getAvatarUrl());
                        event.getChannel().sendMessage(embedBuilder.build()).queue();
                    } else {
                        event.getChannel().sendMessage("No online players :flushed:").queue();
                    }
                    break;
                case "!=tps":
                    EmbedBuilder embedBuilder1 = new EmbedBuilder();
                    embedBuilder1.setTitle("Current TPS");
                    embedBuilder1.setColor(ChatBridgeBeta.getTPSColor(plugin.getTps()));
                    embedBuilder1.setDescription("Current TPS: " + Math.round(plugin.getTps()));
                    embedBuilder1.setAuthor(plugin.getJda().getSelfUser().getAsTag(), null, plugin.getJda().getSelfUser().getAvatarUrl());
                    event.getChannel().sendMessage(embedBuilder1.build()).queue();
                    break;
            }
        }
    }
}