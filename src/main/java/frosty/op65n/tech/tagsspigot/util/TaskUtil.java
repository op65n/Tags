package frosty.op65n.tech.tagsspigot.util;

import frosty.op65n.tech.tagsspigot.TagsPlugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class TaskUtil {

    private static final TagsPlugin PLUGIN = JavaPlugin.getPlugin(TagsPlugin.class);
    private static final BukkitScheduler SCHEDULER = PLUGIN.getServer().getScheduler();

    public static void async(final Runnable runnable) {
        SCHEDULER.scheduleAsyncDelayedTask(PLUGIN, runnable);
    }

    public static void queue(final Runnable runnable) {
        SCHEDULER.scheduleSyncDelayedTask(PLUGIN, runnable);
    }

}
