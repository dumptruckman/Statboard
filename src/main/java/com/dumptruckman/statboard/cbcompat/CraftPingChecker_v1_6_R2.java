package com.dumptruckman.statboard.cbcompat;

import com.dumptruckman.statboard.PingChecker;
import org.bukkit.craftbukkit.v1_6_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class CraftPingChecker_v1_6_R2 implements PingChecker {

    @Override
    public int getPlayerPing(final Player player) {
        return ((CraftPlayer) player).getHandle().ping;
    }
}
