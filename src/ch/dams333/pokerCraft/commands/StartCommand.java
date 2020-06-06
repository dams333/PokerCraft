package ch.dams333.pokerCraft.commands;

import ch.dams333.pokerCraft.Poker;
import ch.dams333.pokerCraft.states.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor {
    Poker main;
    public StartCommand(Poker poker) {
        this.main = poker;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            //if(Bukkit.getOnlinePlayers().size() > 1){    //DEBUG
                if(Bukkit.getOnlinePlayers().size() <= 6){
                    if (main.isGameState(GameState.PREGAME)) {
                        p.sendMessage(ChatColor.DARK_GREEN + "Démarrage de la partie");
                        main.gameManager.startGame();
                        return true;
                    }
                    p.sendMessage(ChatColor.RED + "La partie a déja démarrée");
                    return true;
                }
                p.sendMessage(ChatColor.RED + "Il faut être au maximum 6");
                return true;
            //}
            //p.sendMessage(ChatColor.RED + "Il faut être au moins 2");
            //return true;
        }
        sender.sendMessage(ChatColor.RED + "Il faut être connecté sur le serveur pour faire cela");
        return false;
    }
}
