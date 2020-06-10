package ch.dams333.pokerCraft.commands;

import ch.dams333.pokerCraft.Poker;
import ch.dams333.pokerCraft.stats.Statistique;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatCommand implements CommandExecutor {
    Poker main;
    public StatCommand(Poker poker) {
        this.main = poker;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length == 1){
                String pseudo = args[0];
                if(main.statistiquesManager.pseudoHasStat(pseudo) && main.statistiquesManager.getPseudoStat(pseudo).getRounds() > 0){
                    Statistique stat = main.statistiquesManager.getPseudoStat(pseudo);
                    p.sendMessage(ChatColor.DARK_GREEN + "======= " + pseudo + " ... =======");
                    p.sendMessage(ChatColor.GRAY + "... " + ChatColor.LIGHT_PURPLE + "a prit part à " + ChatColor.BOLD + stat.getGames() + ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + " parties" + " pour un total de " + ChatColor.BOLD + stat.getCompleteRound() + ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + " rounds");
                    p.sendMessage(" ");
                    p.sendMessage(ChatColor.GRAY + "... " + ChatColor.LIGHT_PURPLE + "se couche " + ChatColor.BOLD + (Math.round((stat.getCouches() * 100) / stat.getRounds())) + ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + "% du temps");
                    p.sendMessage(ChatColor.GRAY + "... " + ChatColor.LIGHT_PURPLE + "check " + ChatColor.BOLD + (Math.round((stat.getCheck() * 100) / stat.getRounds())) + ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + "% du temps");
                    p.sendMessage(ChatColor.GRAY + "... " + ChatColor.LIGHT_PURPLE + "suit " + ChatColor.BOLD + (Math.round((stat.getFollow() * 100) / stat.getRounds())) + ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + "% du temps");
                    p.sendMessage(ChatColor.GRAY + "... " + ChatColor.LIGHT_PURPLE + "relance " + ChatColor.BOLD + (Math.round((stat.getRelance() * 100) / stat.getRounds())) + ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + "% du temps");
                    p.sendMessage(ChatColor.GRAY + "... " + ChatColor.LIGHT_PURPLE + "fait tapis " + ChatColor.BOLD + (Math.round((stat.getTapis() * 100) / stat.getRounds())) + ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + "% du temps");
                    p.sendMessage(" ");
                    p.sendMessage(ChatColor.GRAY + "... " + ChatColor.LIGHT_PURPLE + "à gagner " + ChatColor.BOLD + stat.getWinsRound() + ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + " rounds");
                    p.sendMessage(ChatColor.GRAY + "... " + ChatColor.LIGHT_PURPLE + "à gagner " + ChatColor.BOLD + stat.getWinsGame() + ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + " games");
                    return true;
                }
                p.sendMessage(ChatColor.RED + "Ce joueur n'a pas de statistique");
                return true;
            }
            p.sendMessage(ChatColor.RED + "/stat <pseudo>");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Il faut etre connecté sur le serveur pour faire cela");
        return false;
    }
}
