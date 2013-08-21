package com.dumptruckman.statboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

public class Scores {

    public static final String SERVER_NAME = ChatColor.GREEN.toString() + ChatColor.UNDERLINE + "play.10HEARTS.com";

    public static final OfflinePlayer SERVER_PING = Bukkit.getOfflinePlayer(ChatColor.DARK_GREEN + "Server Ping");
    public static final OfflinePlayer PLAYERS_ONLINE = Bukkit.getOfflinePlayer(ChatColor.DARK_PURPLE + "Online");
    public static final OfflinePlayer TIME = Bukkit.getOfflinePlayer(ChatColor.GRAY + "MC Time");
    public static final OfflinePlayer MONEY = Bukkit.getOfflinePlayer(ChatColor.DARK_GREEN + "$ " + ChatColor.GREEN + "Snides" + ChatColor.DARK_GREEN + " $");
    public static final OfflinePlayer TICKS_PER_SECOND = Bukkit.getOfflinePlayer(ChatColor.DARK_GREEN + "Server Speed");

    //public static final OfflinePlayer MCMMO_RANK = Bukkit.getOfflinePlayer(ChatColor.GOLD + "★MCTOP Rank★");
    public static final OfflinePlayer MCMMO_POWER_LEVEL = Bukkit.getOfflinePlayer(ChatColor.LIGHT_PURPLE + "MCMMO Power");
    //public static final OfflinePlayer MCMMO_MINING = Bukkit.getOfflinePlayer(ChatColor.GOLD + "★Mining★");
    //public static final OfflinePlayer MCMMO_EXCAVATION = Bukkit.getOfflinePlayer(ChatColor.GOLD + "★Excavation★");
    //public static final OfflinePlayer MCMMO_WOODCUTTING = Bukkit.getOfflinePlayer(ChatColor.GOLD + "★Woodcutting★");
    //public static final OfflinePlayer MCMMO_REPAIR = Bukkit.getOfflinePlayer(ChatColor.GOLD + "★Repair★");
    //public static final OfflinePlayer MCMMO_ARCHERY = Bukkit.getOfflinePlayer(ChatColor.GOLD + "★Archery★");
    //public static final OfflinePlayer MCMMO_SWORDS = Bukkit.getOfflinePlayer(ChatColor.GOLD + "★Swords★");

    //public static final OfflinePlayer PVE_KILLS = Bukkit.getOfflinePlayer(ChatColor.RED + "Mob Kills");
    public static final OfflinePlayer PVP_KILLS = Bukkit.getOfflinePlayer(ChatColor.BLUE + "Battles Won");
    public static final OfflinePlayer PVP_DEATHS = Bukkit.getOfflinePlayer(ChatColor.BLUE + "Battles Lost");
    public static final OfflinePlayer PVP_STREAK = Bukkit.getOfflinePlayer(ChatColor.BLUE + "Current KS");
    public static final OfflinePlayer PVP_MAX_STREAK = Bukkit.getOfflinePlayer(ChatColor.BLUE + "Max KS");
    public static final OfflinePlayer PVP_RATING = Bukkit.getOfflinePlayer(ChatColor.BLUE + "Rating");
    public static final OfflinePlayer PVP_MAX_RATING = Bukkit.getOfflinePlayer(ChatColor.BLUE + "Max Rating");
    //public static final OfflinePlayer PVP_RANK = Bukkit.getOfflinePlayer(ChatColor.DARK_RED + "PvP Rank");
    //public static final OfflinePlayer PVP_KD_RATIO = Bukkit.getOfflinePlayer(ChatColor.DARK_RED + "PvP KD Ratio");

    public static final OfflinePlayer FACTION_LAND = Bukkit.getOfflinePlayer(ChatColor.GOLD + "Faction Land");
    public static final OfflinePlayer FACTION_POWER = Bukkit.getOfflinePlayer(ChatColor.GOLD + "Faction Power");
    public static final OfflinePlayer PLAYER_POWER = Bukkit.getOfflinePlayer(ChatColor.GOLD + "Player Power");
    public static final OfflinePlayer CITIZENS_ONLINE = Bukkit.getOfflinePlayer(ChatColor.GOLD + "Faction Online");

}
