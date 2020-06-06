package ch.dams333.pokerCraft.events.connexion;

import ch.dams333.pokerCraft.Poker;
import ch.dams333.pokerCraft.states.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {
    Poker main;
    public JoinEvent(Poker poker) {
        this.main = poker;
    }

    @EventHandler
    public void join(PlayerJoinEvent e){
        if(main.isGameState(GameState.PREGAME)){
            Player p = e.getPlayer();
            p.setGameMode(GameMode.ADVENTURE);
            p.setHealth(20);
            p.setFoodLevel(20);
            p.getInventory().clear();
            p.updateInventory();
            p.teleport(new Location(Bukkit.getWorld("world"), -197.5, 79, 119.5, 90, 10));
        }else{
            e.getPlayer().kickPlayer(ChatColor.RED + "La partie a déjà dàmarrée");
        }
    }

}
