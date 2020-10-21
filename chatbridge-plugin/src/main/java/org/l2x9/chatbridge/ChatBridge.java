package org.l2x9.chatbridge;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.l2x9.chatbridge.antispam.Cooldown;
import org.l2x9.chatbridge.bukkitevents.PlayerAdvancement;
import org.l2x9.chatbridge.bukkitevents.PlayerChat;
import org.l2x9.chatbridge.bukkitevents.PlayerJoin;
import org.l2x9.chatbridge.bukkitevents.PlayerQuit;
import org.l2x9.chatbridge.discordevents.Commands;
import org.l2x9.chatbridge.discordevents.MessageSendEvent;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;


public final class ChatBridge extends JavaPlugin {
    public Cooldown cm = new Cooldown();
    TextChannel channel;
    String channelID;
    String botToken;
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
        saveDefaultConfig();

        channelID = getConfig().getString("channelID");
        botToken = getConfig().getString("botToken");

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

        getServer().getPluginManager().registerEvents(new PlayerAdvancement(this), this);
        getServer().getPluginManager().registerEvents(new PlayerChat(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(this), this);
        sendEmbed(":white_check_mark: Server Started", channel, Color.green);
        if (getServer().getPluginManager().getPlugin("L2X9Core") != null) {
            getLogger().info("[L2X9ChatBridge] Hooked with L2X9Core");
        }
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

    public List<String> getL2X9CoreBlockedWords() {
        if (getServer().getPluginManager().getPlugin("L2X9Core") != null) {
            FileConfiguration l2x9coreConfig = new YamlConfiguration();
            File l2x9conf = new File("plugins/L2X9Core/config.yml");

            try {
                l2x9coreConfig.load(l2x9conf);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }

            return l2x9coreConfig.getStringList("Chat.Blocked-words");
        } else {
            return null;
        }
    }
}