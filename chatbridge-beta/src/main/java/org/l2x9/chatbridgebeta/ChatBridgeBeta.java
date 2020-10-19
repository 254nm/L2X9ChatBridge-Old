package org.l2x9.chatbridgebeta;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;
import org.l2x9.chatbridgebeta.antispam.ChatEvent;
import org.l2x9.chatbridgebeta.antispam.Cooldown;
import org.l2x9.chatbridgebeta.bukkitevents.PlayerChat;
import org.l2x9.chatbridgebeta.bukkitevents.PlayerJoin;
import org.l2x9.chatbridgebeta.bukkitevents.PlayerQuit;
import org.l2x9.chatbridgebeta.discordevents.Commands;
import org.l2x9.chatbridgebeta.discordevents.MessageSendEvent;

import javax.security.auth.login.LoginException;
import java.awt.*;

public final class ChatBridgeBeta extends JavaPlugin {
    TextChannel channel;
    public Cooldown cm = new Cooldown();
    String channelID = "760624272656826398";
    String botToken = "NjY3NjQ1OTIyMDQ1NTkxNTUz.XiFvuA.ZWLd_Cjs6MVveIp6HEsHsN4boW0";
    JDA jda;

    public static Color getTPSColor(double i) {
        if (i >= 18.0D) {
            return Color.GREEN;
        } else {
            return i >= 13.0D && i < 18.0D ? Color.YELLOW : Color.RED;
        }
    }

    @Override
    public void onDisable() {
        sendEmbed(":x: Server Stopped", channel, Color.RED);
        getJda().shutdown();
    }

    @Override
    public void onEnable() {
        try {
            jda = JDABuilder.createDefault(botToken).addEventListeners(new Object[]{
                    new MessageSendEvent(this),
                    new Commands(this)
            }).build();
            jda.awaitReady();
            channel = jda.getTextChannelById(channelID);
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
        getServer().getPluginManager().registerEvent(Event.Type.PLAYER_CHAT, new PlayerChat(this), Event.Priority.Normal, this);
        getServer().getPluginManager().registerEvent(Event.Type.PLAYER_JOIN, new PlayerJoin(this), Event.Priority.Normal, this);
        getServer().getPluginManager().registerEvent(Event.Type.PLAYER_QUIT, new PlayerQuit(this), Event.Priority.Normal, this);
        getServer().getPluginManager().registerEvent(Event.Type.PLAYER_CHAT, new ChatEvent(this), Event.Priority.Highest, this);
        sendEmbed(":white_check_mark: Server Started", channel, Color.green);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new GetTps(), 100L, 1L);
    }

    public TextChannel getChannel() {
        return channel;
    }

    private void sendEmbed(String message, TextChannel channel, Color color) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(message);
        embedBuilder.setColor(color);
        channel.sendMessage(embedBuilder.build()).queue();
    }

    public JDA getJda() {
        return jda;
    }

    public double getTps() {
        return GetTps.getTPS();
    }
}
