package ch.dams333.pokerCraft.events.status;

import ch.dams333.pokerCraft.Poker;
import ch.dams333.pokerCraft.states.GameState;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class StatusEvent implements Listener {
    Poker main;
    public StatusEvent(Poker poker) {
        this.main = poker;
    }

    @EventHandler
    public void health(EntityDamageEvent e){
        if(e.getEntity() instanceof Player || e.getEntity() instanceof ItemFrame){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void food(FoodLevelChangeEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void move(PlayerMoveEvent e){
        if(!main.isGameState(GameState.PREGAME)){
            if(e.getFrom().getX() != e.getTo().getX() || e.getFrom().getY() != e.getTo().getY() || e.getFrom().getZ() != e.getTo().getZ()){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void interEntity(PlayerInteractEntityEvent e){
        if(e.getRightClicked() instanceof ItemFrame){
            e.setCancelled(true);
        }
    }


}
