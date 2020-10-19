package org.l2x9.chatbridge.discordevents;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.l2x9.chatbridge.ChatBridge;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Commands extends ListenerAdapter {
    ChatBridge plugin;

    public Commands(ChatBridge chatBridge) {
        plugin = chatBridge;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().startsWith(">")) {
            String[] args = event.getMessage().getContentRaw().split(" ");
            EmbedBuilder embedBuilder = new EmbedBuilder();
            switch (args[0]) {
                case ">online":
                    embedBuilder.setTitle("Online Players");
                    embedBuilder.setColor(Color.GRAY);
                    Collection<? extends Player> players = Bukkit.getOnlinePlayers();
                    if (players.size() > 0) {
                        List<String> playerNames = new ArrayList<>();
                        for (Player player : players) {
                            playerNames.add(player.getName());
                        }
                        String nameList = String.join("\n", playerNames);
                        embedBuilder.setDescription(nameList + "\n\n" + playerNames.size() + " / 100");
                        embedBuilder.setAuthor(plugin.getJda().getSelfUser().getAsTag().concat(" By 254n_m"), null, plugin.getJda().getSelfUser().getAvatarUrl());
                        event.getChannel().sendMessage(embedBuilder.build()).queue();
                    } else {
                        event.getChannel().sendMessage("No online players :flushed:").queue();
                    }
                    embedBuilder.clear();
                    break;
                case ">tps":
                    embedBuilder.setTitle("Current TPS");
                    embedBuilder.setColor(ChatBridge.getTPSColor(plugin.getTps()));
                    embedBuilder.setDescription("Current TPS: " + plugin.getTps());
                    embedBuilder.setAuthor(plugin.getJda().getSelfUser().getAsTag().concat(" By 254n_m"), null, plugin.getJda().getSelfUser().getAvatarUrl());
                    event.getChannel().sendMessage(embedBuilder.build()).queue();
                    embedBuilder.clear();
                    break;
            }
        }
    }
}