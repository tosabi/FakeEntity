package me.newtondev.entity.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ReflectionUtil {

    private static final String VERSION;

    static {
        String version = Bukkit.getServer().getClass().getPackage().getName();
        version = version.substring(version.lastIndexOf('.') + 1);
        VERSION = version;
    }

    public static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + VERSION + "." + name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static boolean versionEqualsOrHigherThan(String value) {
        String[] values = VERSION.split("_");
        String[] diff = value.split("_");

        return (Integer.parseInt(values[1]) >= Integer.parseInt(diff[1]));
    }

    public static String getVersion() {
        return VERSION;
    }
}
