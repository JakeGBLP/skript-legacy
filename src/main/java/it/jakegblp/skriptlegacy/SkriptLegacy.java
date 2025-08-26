package it.jakegblp.skriptlegacy;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.util.Timespan;
import ch.njol.skript.util.Version;
import it.jakegblp.updatechecker.UpdateChecker;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.DrilldownPie;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class SkriptLegacy extends JavaPlugin {

    private static SkriptLegacy instance;
    private SkriptAddon addon;

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        instance = this;
        addon = Skript.registerAddon(this);
        int pluginId = 26035;
        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new DrilldownPie("skript_version", () -> {
            Map<String, Map<String, Integer>> map = new HashMap<>();

            Version skriptVersion = Skript.getVersion();
            Map<String, Integer> entry = new HashMap<>();
            entry.put(skriptVersion.toString(), 1);

            map.put(skriptVersion.getMajor()+"."+skriptVersion.getMinor()+"."+skriptVersion.getRevision(), entry);

            return map;
        }));
        Bukkit.getPluginManager().registerEvents(new UpdateChecker(this), this);
        long end = System.currentTimeMillis();
        UpdateChecker.messageConsole("&aSkript-legacy has been enabled! &8[&7" + new Timespan(end - start) + "&8]");
    }

}
