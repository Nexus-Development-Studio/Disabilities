package cc.synkdev.disabilities.command;

import cc.synkdev.disabilities.manager.*;
import cc.synkdev.disabilities.object.Disability;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@CommandAlias("disability")
public class DisabilityCommand extends BaseCommand {
    @Subcommand("give")
    @CommandPermission("disabilities.command.give")
    @CommandCompletion("@players @disabilities")
    public void onGive(CommandSender sender, String[] args) {
        if (args.length < 2) {
            Utils.sendMessage(sender, "<dark_red>Usage: /disability give <player> <disability>");
            return;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!(target.isOnline() || target.hasPlayedBefore())) {
            Utils.sendMessage(sender, "<dark_red>Player not found.");
            return;
        }
        Disability d = Utils.getDisability(args[1]);
        if (d == null) {
            Utils.sendMessage(sender, "<dark_red>Disability not found.");
            return;
        }
        PlayerManager.disable(target, d);
        Utils.sendMessage(sender, "<green>Successfully gave " + target.getName() + " the " + d.getName() + " disability.");
    }
    @Subcommand("remove")
    @CommandPermission("disabilities.command.remove")
    @CommandCompletion("@players @disabilities")
    public void onRemove(CommandSender sender, String[] args) {
        if (args.length < 2) {
            Utils.sendMessage(sender, "<dark_red>Usage: /disability remove <player> <disability>");
            return;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!(target.isOnline() || target.hasPlayedBefore())) {
            Utils.sendMessage(sender, "<dark_red>Player not found.");
            return;
        }
        Disability d = Utils.getDisability(args[1]);
        if (d == null) {
            Utils.sendMessage(sender, "<dark_red>Disability not found.");
            return;
        }
        PlayerManager.enable(target, d);
        Utils.sendMessage(sender, "<green>Successfully removed " + target.getName() + " the " + d.getName() + " disability.");
    }

    @Subcommand("reset")
    @CommandPermission("disabilities.command.reset")
    @CommandCompletion("@players")
    public void onReset(CommandSender sender, String[] args) {
        if (args.length < 1) {
            Utils.sendMessage(sender, "<dark_red>Usage: /disability reset <player>");
            return;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!target.isOnline()) {
            Utils.sendMessage(sender, "<dark_red>The player must be online!");
            return;
        }

        PlayerManager.reset(Objects.requireNonNull(target.getPlayer()));
        Utils.sendMessage(sender, "<green>Successfully reset " + target.getName() + "'s disabilities.");
    }

    @Subcommand("reload")
    @CommandPermission("disabilities.command.reload")
    public void onReload(CommandSender sender) {
        long time = System.currentTimeMillis();
        ConfigManager.load();
        Bukkit.getScheduler().cancelTask(DisabilityManager.getTaskId());
        DisabilityManager.registerTask();
        Bukkit.getOnlinePlayers().forEach(PlayerManager::sendScoreboard);
        FileManager.load();
        Utils.sendMessage(sender, "<green>Reloaded config in "+(System.currentTimeMillis()-time)+"ms.");
    }

    @Subcommand("list")
    @CommandPermission("disabilities.command.list")
    public void onList(CommandSender sender, String[] args) {
        if (args.length < 1) {
            Utils.sendMessage(sender, "<dark_red>Usage: /disability list <player>");
            return;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!(target.isOnline() || target.hasPlayedBefore())) {
            Utils.sendMessage(sender, "<dark_red>Player not found.");
            return;
        }
        Utils.sendMessage(sender, "<green>"+target.getName()+"'s disabilities: <yellow>"+String.join(", ", PlayerManager.getPlayerMap().getOrDefault(target.getUniqueId(), new ArrayList<>()).stream().map(s -> DisabilityManager.getDTypes().stream().filter(d -> d.getId().equals(s))).findFirst().flatMap(Stream::findFirst).get().getName()));
    }

    @Subcommand("next")
    @CommandPermission("disabilities.command.next")
    public void onNext(CommandSender sender) {
        Utils.sendMessage(sender, "<green>Skipping timer and jumping to next disability");
        assignDisabilities();
        DisabilityManager.setTimer(System.currentTimeMillis()/1000+ConfigManager.getTimer());
        Bukkit.getScheduler().cancelTask(DisabilityManager.getTaskId());
        DisabilityManager.registerTask();
    }

    public static void assignDisabilities() {
        for (Player pl : Bukkit.getOnlinePlayers()) {
            List<String> currentList = PlayerManager.getPlayerMap().getOrDefault(pl.getUniqueId(), new ArrayList<>());
            List<Disability> available = new ArrayList<>(new ArrayList<>(DisabilityManager.getDTypes()).stream().filter(disability -> !currentList.contains(disability.getId())).toList());
            if (available.isEmpty()) continue;
            Collections.shuffle(available);
            PlayerManager.disable(pl, available.getFirst());
        }
    }
}
