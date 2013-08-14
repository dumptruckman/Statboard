package com.dumptruckman.statboard;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.datatypes.skills.SkillType;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.UPlayer;
import com.massivecraft.factions.event.FactionsEventMembershipChange;
import mc.alk.tracker.Tracker;
import mc.alk.tracker.TrackerInterface;
import mc.alk.tracker.objects.Stat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Criterias;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statboard extends JavaPlugin implements Listener {

    private static final String SIDEBOARD_OBJ = "Statsboard";

    private Economy economy;
    private TrackerInterface pvpTracker;
    private TrackerInterface pveTracker;

    private final TicksPerSecondTask tpsTask = new TicksPerSecondTask(this, 30);
    private int lastTPS = 0;

    private Map<Player, Scoreboard> scoreboardMap = new HashMap<Player, Scoreboard>(Bukkit.getMaxPlayers());

    private PingChecker pingChecker;

    @Override
    public void onEnable() {

        final String packageName = Bukkit.getServer().getClass().getPackage().getName();
        final String bukkitVersion = packageName.substring(packageName.lastIndexOf('.') + 1);

        try {
            pingChecker = (PingChecker) Class.forName("com.dumptruckman.statboard.cbcompat.CraftPingChecker_" + bukkitVersion).newInstance();
        } catch (Exception e) {
            getLogger().warning("Your craftbukkit version is not supported by this plugin yet.  The Server Ping feature will not function.");
            pingChecker = null;
        }

        if (getServer().getPluginManager().getPlugin("Factions") == null) {
            getLogger().severe("Factions not found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (!setupEconomy()) {
            getLogger().severe("Economy not found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (getServer().getPluginManager().getPlugin("mcMMO") == null) {
            getLogger().severe("mcMMO not found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        Tracker tracker = (Tracker) Bukkit.getPluginManager().getPlugin("BattleTracker");
        if (tracker != null){
            pvpTracker = Tracker.getInterface("pvp");
            pveTracker = Tracker.getInterface("pve");
        } else {
            getLogger().severe("BattleTracker not found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getServer().getPluginManager().registerEvents(this, this);
        tpsTask.start();
        new ShowTPSTask().runTaskTimer(this, 0L, 20L);
    }

    private class ShowTPSTask extends BukkitRunnable {
        @Override
        public void run() {
            lastTPS = tpsTask.getTicksPerSecond();
            for (Player player : Bukkit.getOnlinePlayers()) {
                updateTps(player);
                updateTime(player);
                updateBalance(player);
                updateStats(player);
                updateTracker(player);
                updatePing(player);
            }
        }
    }

    private void updatePing(Player player) {
        if (pingChecker != null) {
            Objective obj = getScoreboard(player).getObjective(SIDEBOARD_OBJ);
            Score score = obj.getScore(Scores.SERVER_PING);
            score.setScore(pingChecker.getPlayerPing(player));
        }
    }

    private void updateTps(Player player) {
        Scoreboard scoreboard = getScoreboard(player);
        Objective objective = scoreboard.getObjective(SIDEBOARD_OBJ);
        Score score = objective.getScore(Scores.TICKS_PER_SECOND);
        score.setScore((int) (player.getWorld().getTime() / 1000));
    }

    private void updateTime(Player player) {
        Scoreboard scoreboard = getScoreboard(player);
        Objective objective = scoreboard.getObjective(SIDEBOARD_OBJ);
        Score score = objective.getScore(Scores.TIME);
        score.setScore(lastTPS);
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    private void updatePlayersOnline() {
        Player[] onlinePlayers = Bukkit.getOnlinePlayers();
        for (Player p : Bukkit.getOnlinePlayers()) {
            Scoreboard scoreboard = getScoreboard(p);
            Objective objective = scoreboard.getObjective(SIDEBOARD_OBJ);
            Score score = objective.getScore(Scores.PLAYERS_ONLINE);
            score.setScore(onlinePlayers.length);
        }
    }

    private void updatePlayersInFaction(Faction faction) {
        List<Player> players = faction.getOnlinePlayers();
        for (Player player : players) {
            Scoreboard scoreboard = getScoreboard(player);
            Objective objective = scoreboard.getObjective(SIDEBOARD_OBJ);
            Score score = objective.getScore(Scores.CITIZENS_ONLINE);
            score.setScore(players.size());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void playerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Scoreboard scoreboard = player.getScoreboard();
        scoreboardMap.put(player, scoreboard);

        // Set up health objective

        Objective objective = scoreboard.getObjective("health");
        if (objective == null) {
            objective = scoreboard.registerNewObjective("health", Criterias.HEALTH);
            objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
            objective.setDisplayName("Health");
        }

        // Set up sideboard objective

        objective = scoreboard.getObjective(SIDEBOARD_OBJ);
        if (objective == null) {
            objective = scoreboard.registerNewObjective(SIDEBOARD_OBJ, "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(ChatColor.GREEN.toString() + ChatColor.BOLD + "www.MCPVE.com");
        }

        updatePlayersOnline();

        updateBalance(player);

        updatePlayersInFaction(UPlayer.get(player).getFaction());

        updateTps(player);

        updateTime(player);

        updateStats(player);

        updateTracker(player);

        updatePing(player);
    }

    private void updateBalance(Player player) {
        getScoreboard(player).getObjective(SIDEBOARD_OBJ).getScore(Scores.MONEY).setScore((int) economy.getBalance(player.getName()));
    }

    private void updateStats(Player player) {
        Objective obj = getScoreboard(player).getObjective(SIDEBOARD_OBJ);
        Score score = obj.getScore(Scores.MCMMO_POWER_LEVEL);
        score.setScore(ExperienceAPI.getPowerLevel(player));
        score = obj.getScore(Scores.MCMMO_MINING);
        score.setScore(ExperienceAPI.getLevel(player, SkillType.MINING.toString()));
        score = obj.getScore(Scores.MCMMO_EXCAVATION);
        score.setScore(ExperienceAPI.getLevel(player, SkillType.EXCAVATION.toString()));
        score = obj.getScore(Scores.MCMMO_WOODCUTTING);
        score.setScore(ExperienceAPI.getLevel(player, SkillType.WOODCUTTING.toString()));
        score = obj.getScore(Scores.MCMMO_REPAIR);
        score.setScore(ExperienceAPI.getLevel(player, SkillType.REPAIR.toString()));
        score = obj.getScore(Scores.MCMMO_ARCHERY);
        score.setScore(ExperienceAPI.getLevel(player, SkillType.ARCHERY.toString()));
        score = obj.getScore(Scores.MCMMO_SWORDS);
        score.setScore(ExperienceAPI.getLevel(player, SkillType.SWORDS.toString()));
    }

    private void updateTracker(Player player) {
        Objective obj = getScoreboard(player).getObjective(SIDEBOARD_OBJ);
        Stat pvpStats = pvpTracker.loadRecord(player);
        if (pvpStats != null) {
            Score score = obj.getScore(Scores.PVP_KILLS);
            score.setScore(pvpStats.getWins());
        }
        Stat pveStats = pveTracker.loadRecord(player);
        if (pveStats != null) {
            Score score = obj.getScore(Scores.PVE_KILLS);
            score.setScore(pveStats.getWins());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerQuit(PlayerQuitEvent event) {
        updatePlayersOnline();
        scoreboardMap.remove(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void factionMembershipChange(FactionsEventMembershipChange event) {
        final Player player = Bukkit.getPlayerExact(event.getUPlayer().getName());
        if (player == null) {
            throw new IllegalStateException("Player should not be null!");
        }

        final Faction oldFaction = UPlayer.get(player).getFaction();
        final Faction newFaction = event.getNewFaction();

        Bukkit.getScheduler().runTask(this, new Runnable() {
            @Override
            public void run() {
                updatePlayersInFaction(oldFaction);
                updatePlayersInFaction(newFaction);
            }
        });
    }

    public Scoreboard getScoreboard(Player player) {
        return scoreboardMap.get(player);
    }
}
