package org.l2x9.chatbridgebeta.discordevents;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.l2x9.chatbridgebeta.ChatBridgeBeta;

public class MessageSendEvent extends ListenerAdapter {
    ChatBridgeBeta plugin;

    public MessageSendEvent(ChatBridgeBeta chatBridgeBeta) {
        plugin = chatBridgeBeta;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getChannel().equals(plugin.getChannel()) && !event.getAuthor().isBot() && !event.getMessage().getContentRaw().startsWith("!=")) {
            String author = event.getAuthor().getAsTag().concat(">");
            Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "<Discord -> " + ChatColor.GRAY + author + " " + ChatColor.WHITE + event.getMessage().getContentRaw());
            event.getMessage().addReaction(":white_check_mark:").queue();
        }
    }
}
