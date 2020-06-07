package ch.dams333.pokerCraft;

import ch.dams333.damsLib.DamsLIB;
import ch.dams333.pokerCraft.commands.StartCommand;
import ch.dams333.pokerCraft.events.connexion.JoinEvent;
import ch.dams333.pokerCraft.events.status.ChangeSlotEvent;
import ch.dams333.pokerCraft.events.status.ChatEvent;
import ch.dams333.pokerCraft.events.status.StatusEvent;
import ch.dams333.pokerCraft.managers.CardManager;
import ch.dams333.pokerCraft.managers.GameManager;
import ch.dams333.pokerCraft.managers.MapManager;
import ch.dams333.pokerCraft.states.GameState;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class Poker extends JavaPlugin{

    public DamsLIB API;

    public MapManager mapManager;
    public GameManager gameManager;
    public CardManager cardManager;

    private GameState gameState;

    public String name = ChatColor.GOLD + "[" + ChatColor.DARK_GREEN + "PokerCraft" + ChatColor.GOLD + "] " + ChatColor.GRAY;

    @EventHandler
    public void onEnable(){
        API = (DamsLIB) getServer().getPluginManager().getPlugin("DamsLIB");
        this.mapManager = new MapManager(this);
        this.gameManager = new GameManager(this);
        this.cardManager = new CardManager(this);
        mapManager.resetTable();
        getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
        getServer().getPluginManager().registerEvents(new StatusEvent(this), this);
        getServer().getPluginManager().registerEvents(new ChangeSlotEvent(this), this);
        getServer().getPluginManager().registerEvents(new ChatEvent(this), this);
        gameState = GameState.PREGAME;
        getCommand("start").setExecutor(new StartCommand(this));

    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    public boolean isGameState(GameState gameState){
        return  this.gameState == gameState;
    }

    @EventHandler
    public void onDisable(){
        gameManager.central.remove();
        for(Player p : gameManager.armorStands.keySet()){
            gameManager.armorStands.get(p).remove();
        }
    }
}
