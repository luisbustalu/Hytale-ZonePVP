package anonimo.ZonePVP.utils;

import anonimo.ZonePVP.Main;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.world.WorldMapTracker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZoneUtils {

    private static final Pattern ZONE_PATTERN = Pattern.compile("Zone(\\d+)");
    private static final Integer MINIMUM_ZONE = Main.CONFIG.get().getMinPVPZone();

    /**
     * Checks if PvP is allowed based on the player's current zone.
     * @param player The victim or player to check.
     * @return true if PvP is enabled, false if it's a protected zone.
     */
    public static boolean isPvPEnabled(Player player) {
        if (player == null) return false;

        WorldMapTracker worldMapTracker = player.getWorldMapTracker();
        if (worldMapTracker.getCurrentZone() == null) {
            return false; // Default to false if zone is unknown
        }

        String zoneName = worldMapTracker.getCurrentZone().regionName(); // e.g., "Zone1_Tier1"
        Matcher matcher = ZONE_PATTERN.matcher(zoneName);

        if (matcher.find()) {
            try {
                int zoneLevel = Integer.parseInt(matcher.group(1));

                // Logic: Enable PvP if zoneLevel is higher or equal than MINIMUM_ZONE, disable otherwise.
                return zoneLevel >= MINIMUM_ZONE;

            } catch (NumberFormatException e) {
                return true;
            }
        }

        return true;
    }
}