package ch.dams333.pokerCraft.events.status;

import ch.dams333.pokerCraft.Poker;
import ch.dams333.pokerCraft.states.GameState;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {
    Poker main;
    public ChatEvent(Poker poker) {
        this.main = poker;
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent e) {
        if (!main.isGameState(GameState.PREGAME)) {
            e.setCancelled(true);
            if (main.gameManager.relance != null && main.gameManager.relance == e.getPlayer()) {
                if (main.API.isInt(e.getMessage())) {
                    main.gameManager.hasRelance(Integer.parseInt(e.getMessage()));
                } else {
                    e.getPlayer().sendMessage(ChatColor.RED + "Veuillez entrer un nombre valide");
                }
            }
        }
    }

}
