package org.l2x9.betachatbridge;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.l2x9.betachatbridge.antispam.Cooldown;
import org.l2x9.betachatbridge.bukkitevents.PlayerChat;
import org.l2x9.betachatbridge.bukkitevents.PlayerJoin;
import org.l2x9.betachatbridge.bukkitevents.PlayerQuit;
import org.l2x9.betachatbridge.discordevents.Commands;
import org.l2x9.betachatbridge.discordevents.MessageSendEvent;

import javax.security.auth.login.LoginException;
import java.awt.*;

public final class BetaChatBridge extends JavaPlugin {
    public Cooldown cm = new Cooldown();
    TextChannel channel;
    String channelID = "Your channel id here";
    String botToken = "Your token here";
    JDA jda;

    public static Color getTPSColor(String input) {
        if (!input.equals("*20")) {
            double i = Double.parseDouble(input);
            if (i >= 18.0D) {
                return Color.GREEN;
            } else {
                return i >= 13.0D && i < 18.0D ? Color.YELLOW : Color.RED;
            }
        } else {
            return Color.GREEN;
        }
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
        //getServer().getPluginManager().registerEvents(new PlayerAdvancement(this), this); //TODO Add this feature
        getServer().getPluginManager().registerEvents(new PlayerChat(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(this), this);
        sendEmbed(":white_check_mark: Server Started", channel, Color.green);
    }

    @Override
    public void onDisable() {
        sendEmbed(":x: Server Stopped", channel, Color.RED);
        getJda().shutdown();
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

    public String getTps() {
        if (!(Bukkit.getTPS()[0] > 20)) {
            double roundOff = Math.round(Bukkit.getTPS()[0] * 100.0) / 100.0;
            return String.valueOf(roundOff);
        } else {
            return "*20";
        }
    }
}
