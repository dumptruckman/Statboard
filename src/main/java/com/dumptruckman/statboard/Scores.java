package com.dumptruckman.statboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

public class Scores {

    public static final OfflinePlayer SERVER_PING = Bukkit.getOfflinePlayer(ChatColor.AQUA + "Server Ping");
    public static final OfflinePlayer PLAYERS_ONLINE = Bukkit.getOfflinePlayer(ChatColor.DARK_PURPLE + "Players Online");
    public static final OfflinePlayer CITIZENS_ONLINE = Bukkit.getOfflinePlayer(ChatColor.LIGHT_PURPLE + "►Citizens On");
    public static final OfflinePlayer TIME = Bukkit.getOfflinePlayer(ChatColor.GRAY + "MC Time");
    public static final OfflinePlayer MONEY = Bukkit.getOfflinePlayer(ChatColor.DARK_GREEN + "$ " + ChatColor.GREEN + "Money" + ChatColor.DARK_GREEN + " $");
    public static final OfflinePlayer TICKS_PER_SECOND = Bukkit.getOfflinePlayer(ChatColor.YELLOW + "Server TPS");

    public static final OfflinePlayer MCMMO_POWER_LEVEL = Bukkit.getOfflinePlayer(ChatColor.GOLD + "★MCMMO Total★");
    public static final OfflinePlayer MCMMO_MINING = Bukkit.getOfflinePlayer(ChatColor.GOLD + "★Mining★");
    public static final OfflinePlayer MCMMO_EXCAVATION = Bukkit.getOfflinePlayer(ChatColor.GOLD + "★Excavation★");
    public static final OfflinePlayer MCMMO_WOODCUTTING = Bukkit.getOfflinePlayer(ChatColor.GOLD + "★Woodcutting★");
    public static final OfflinePlayer MCMMO_REPAIR = Bukkit.getOfflinePlayer(ChatColor.GOLD + "★Repair★");
    public static final OfflinePlayer MCMMO_ARCHERY = Bukkit.getOfflinePlayer(ChatColor.GOLD + "★Archery★");
    public static final OfflinePlayer MCMMO_SWORDS = Bukkit.getOfflinePlayer(ChatColor.GOLD + "★Swords★");

    public static final OfflinePlayer PVE_KILLS = Bukkit.getOfflinePlayer(ChatColor.RED + "Mob Kills");
    public static final OfflinePlayer PVP_KILLS = Bukkit.getOfflinePlayer(ChatColor.DARK_RED + "Player Kills");
}
