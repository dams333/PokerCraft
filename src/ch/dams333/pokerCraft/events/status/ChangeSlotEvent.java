package ch.dams333.pokerCraft.events.status;

import ch.dams333.pokerCraft.Poker;
import ch.dams333.pokerCraft.states.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class ChangeSlotEvent implements Listener {
    Poker main;
    public ChangeSlotEvent(Poker poker) {
        this.main = poker;
    }

    @EventHandler
    public void slot(PlayerItemHeldEvent e){
        if(!main.isGameState(GameState.PREGAME)){
            e.setCancelled(true);
            if(main.gameManager.needToEnchere > 0 && main.gameManager.places.get(main.gameManager.needToEnchere).getUniqueId() == e.getPlayer().getUniqueId()){
                main.gameManager.selectAction(e.getPlayer(), e.getNewSlot());
            }
        }
    }

}
