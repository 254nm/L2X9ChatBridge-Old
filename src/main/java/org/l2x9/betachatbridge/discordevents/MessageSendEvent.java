package org.l2x9.betachatbridge.discordevents;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.l2x9.betachatbridge.BetaChatBridge;

public class MessageSendEvent extends ListenerAdapter {
    BetaChatBridge plugin;

    public MessageSendEvent(BetaChatBridge betaChatBridge) {
        plugin = betaChatBridge;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getChannel().equals(plugin.getChannel()) && !event.getAuthor().isBot() && !event.getMessage().getContentRaw().startsWith("!=")) {
            String author = event.getAuthor().getAsTag().concat(">");
            Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "<Discord -> " + ChatColor.GRAY + author + " " + ChatColor.WHITE + event.getMessage().getContentRaw());
            event.getMessage().addReaction("✔").queue();

        }
    }
}
