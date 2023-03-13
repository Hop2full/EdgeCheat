//I made this and this is not obfuscated so feel free to fork it- Jaswin
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Statistic;
import org.bukkit.Location;

public class AntiCheat extends JavaPlugin {

    private double lastY = 0;
    private int lastBlockPlaced = 0;
    private double maxSpeed = 10.0;
    private Location lastLoc = null;
    private int lastHealth = 0;
    private int lastAttackCount = 0;
    private int threshold = 10;

    @Override
    public void onEnable() {
        getLogger().info("AntiCheat plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("AntiCheat plugin disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("anticheat") && sender instanceof Player && sender.isOp()) {
            Player player = (Player) sender;
            check(player);
            return true;
        }
        return false;
    }

    public boolean isFlying(Player player) {
        Location loc = player.getLocation();
        double y = loc.getY();
        boolean isFlying = false;
        if (y > lastY) {
            isFlying = true;
        }
        lastY = y;
        return isFlying;
    }

    public boolean isScaffold(Player player) {
        int blockPlaced = player.getStatistic(Statistic.BLOCK_PLACED);
        boolean isScaffold = false;
        if (blockPlaced - lastBlockPlaced > threshold) {
            isScaffold = true;
        }
        lastBlockPlaced = blockPlaced;
        return isScaffold;
    }

    public boolean isSpeed(Player player) {
        Location loc = player.getLocation();
        double speed = loc.distance(lastLoc) / 0.05;
        boolean isSpeed = false;
        if (speed > maxSpeed) {
            isSpeed = true;
        }
        lastLoc = loc;
        return isSpeed;
    }

    public boolean isAntiKnockback(Player player) {
        int health = player.getHealth();
        boolean isAntiKnockback = false;
        if (health == lastHealth) {
            isAntiKnockback = true;
        }
        lastHealth = health;
        return isAntiKnockback;
    }

    public boolean isKillAura(Player player) {
        int attackCount = player.getStatistic(Statistic.ENTITY_KILLED_BY);
        boolean isKillAura = false;
        if (attackCount - lastAttackCount > threshold) {
            isKillAura = true;
        }
        lastAttackCount = attackCount;
        return isKillAura;
    }

    public void check(Player player) {
        if (isFlying(player)) {
            kickPlayer(player, "Flying detected");
        }

        if (isScaffold(player)) {
            kickPlayer(player, "Scaffold detected");
        }

        if (isSpeed(player)) {
            kickPlayer(player, "Speed detected");
        }

        if (isAntiKnockback(player)) {
            kickPlayer(player, "Anti-knockback detected");
        }

        if (isKillAura(player)) {
            kickPlayer(player, "KillAura detected");
        }
    }

    public void kickPlayer(Player player, String reason) {
        player.kickPlayer("You have been kicked for using cheats: " + reason);
    }
}
public void kickPlayer(Player player, String reason) {
    player.kickPlayer("You have been kicked for hacking.");
}
import org.bukkit.plugin.java.JavaPlugin;

public class EdgeCheat extends JavaPlugin {
    private AntiCheat antiCheat;

    @Override
    public void onEnable() {
        antiCheat = new AntiCheat();
        getServer().getPluginManager().registerEvents(antiCheat, this);
        getCommand("anticheat").setExecutor(antiCheat);
        getLogger().info("EdgeCheat enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("EdgeCheat disabled!");
    }
}
