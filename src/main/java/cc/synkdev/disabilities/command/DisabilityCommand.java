package cc.synkdev.disabilities.command;

import cc.synkdev.disabilities.manager.PlayerManager;
import cc.synkdev.disabilities.manager.Utils;
import cc.synkdev.disabilities.object.Disability;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

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
}
