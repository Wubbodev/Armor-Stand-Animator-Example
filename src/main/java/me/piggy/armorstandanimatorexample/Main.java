package me.piggy.armorstandanimatorexample;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin implements CommandExecutor {

    ArmorStand armorstand;
    ArmorStandAnimator animator;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        getCommand("animate").setExecutor(this);

    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player p = (Player) sender;

            armorstand = createArmorstand(p.getLocation());

            animator = createAnimator(new File(getDataFolder(), "animation.animc"), armorstand);

            startAnimation();

        }
        return true;
    }

    public ArmorStand createArmorstand(Location loc) {

        ArmorStand armorstand = loc.getWorld().spawn(loc, ArmorStand.class);
        armorstand.setArms(true);
        armorstand.setCollidable(false);
        armorstand.setVisible(true);
        armorstand.setInvulnerable(true);
        armorstand.setBasePlate(false);

        return armorstand;
    }

    public ArmorStandAnimator createAnimator(File file, ArmorStand armorstand) {
        return new ArmorStandAnimator(file, armorstand);
    }

    public void startAnimation() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, ArmorStandAnimator::updateAll, 0, 1);
    }
}
