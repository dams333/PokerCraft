package ch.dams333.pokerCraft.managers;

import ch.dams333.pokerCraft.Poker;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MapManager {
    Poker main;
    public MapManager(Poker poker) {
        this.main = poker;

        this.colorLocations = new HashMap<>();
        this.valueSlots = new HashMap<>();

        this.colorLocations.put("ca", new Location(Bukkit.getWorld("world"), -209, 67, 104));
        this.colorLocations.put("co", new Location(Bukkit.getWorld("world"), -209, 66, 104));
        this.colorLocations.put("tr", new Location(Bukkit.getWorld("world"), -209, 64, 104));
        this.colorLocations.put("pi", new Location(Bukkit.getWorld("world"), -209, 65, 104));

        this.valueSlots.put("a", 0);
        this.valueSlots.put("2", 1);
        this.valueSlots.put("3", 2);
        this.valueSlots.put("4", 3);
        this.valueSlots.put("5", 4);
        this.valueSlots.put("6", 5);
        this.valueSlots.put("7", 6);
        this.valueSlots.put("8", 7);
        this.valueSlots.put("9", 8);
        this.valueSlots.put("10", 9);
        this.valueSlots.put("j", 10);
        this.valueSlots.put("q", 11);
        this.valueSlots.put("k", 12);

        playerCardsLocations = new HashMap<>();
        riverLocations = new ArrayList<>();

        playerCardsLocations.put(1, Arrays.asList(new Location(Bukkit.getWorld("world"), -203.5, 80, 117.5), new Location(Bukkit.getWorld("world"), -204.5, 80, 117.5)));
        playerCardsLocations.put(2, Arrays.asList(new Location(Bukkit.getWorld("world"), -206.5, 80, 117.5), new Location(Bukkit.getWorld("world"), -207.5, 80, 117.5)));
        playerCardsLocations.put(3, Arrays.asList(new Location(Bukkit.getWorld("world"), -209.5, 80, 117.5), new Location(Bukkit.getWorld("world"), -210.5, 80, 117.5)));

        playerCardsLocations.put(4, Arrays.asList(new Location(Bukkit.getWorld("world"), -203.5, 80, 121.5), new Location(Bukkit.getWorld("world"), -204.5, 80, 121.5)));
        playerCardsLocations.put(5, Arrays.asList(new Location(Bukkit.getWorld("world"), -206.5, 80, 121.5), new Location(Bukkit.getWorld("world"), -207.5, 80, 121.5)));
        playerCardsLocations.put(6, Arrays.asList(new Location(Bukkit.getWorld("world"), -209.5, 80, 121.5), new Location(Bukkit.getWorld("world"), -210.5, 80, 121.5)));

        riverLocations.add(new Location(Bukkit.getWorld("world"), -205.5, 80, 119.5));
        riverLocations.add(new Location(Bukkit.getWorld("world"), -206.5, 80, 119.5));
        riverLocations.add(new Location(Bukkit.getWorld("world"), -207.5, 80, 119.5));
        riverLocations.add(new Location(Bukkit.getWorld("world"), -208.5, 80, 119.5));
        riverLocations.add(new Location(Bukkit.getWorld("world"), -209.5, 80, 119.5));

    }

    private Map<String, Location> colorLocations;
    private Map<String, Integer> valueSlots;

    private Map<Integer, List<Location>> playerCardsLocations;
    private List<Location> riverLocations;

    public ItemStack getCardMap(String color, String value){
        Chest chest = (Chest) colorLocations.get(color).getBlock().getState();
        return chest.getInventory().getItem(valueSlots.get(value));
    }

    public ItemStack getGreenMap(){
        Chest chest = (Chest) new Location(Bukkit.getWorld("world"), -209, 68, 104).getBlock().getState();
        return chest.getInventory().getItem(0);
    }

    public ItemStack getInteroMap(){
        Chest chest = (Chest) new Location(Bukkit.getWorld("world"), -209, 68, 104).getBlock().getState();
        return chest.getInventory().getItem(1);
    }

    private ItemFrame getFrameFromLocation(Location loc){
        for(Entity entity : loc.getWorld().getNearbyEntities(loc, 0.1, 1, 0.1)){
            if(entity instanceof ItemFrame){
                return (ItemFrame) entity;
            }
        }
        return null;
    }

    private void setMap(ItemStack map, Location loc){
        ItemFrame frame = getFrameFromLocation(loc);
        frame.setItem(map);
    }

    public void resetTable(){
        for(Location location : riverLocations){
            setMap(getGreenMap(), location);
        }
        for(int players : playerCardsLocations.keySet()){
            for(Location location : playerCardsLocations.get(players)){
                setMap(getInteroMap(), location);
            }
        }
    }

    public void removePlayerCards(int place) {
        for(Location location : playerCardsLocations.get(place)){
            setMap(getGreenMap(), location);
        }
    }

    public void flop() {
        String[] card1 = main.cardManager.getRandomCard().split("_");
        setMap(getCardMap(card1[0], card1[1]), riverLocations.get(0));
        Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
            @Override
            public void run() {
                String[] card2 = main.cardManager.getRandomCard().split("_");
                setMap(getCardMap(card2[0], card2[1]), riverLocations.get(1));
                Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                    @Override
                    public void run() {
                        String[] card3 = main.cardManager.getRandomCard().split("_");
                        setMap(getCardMap(card3[0], card3[1]), riverLocations.get(2));
                        main.gameManager.river.add(card1);
                        main.gameManager.river.add(card2);
                        main.gameManager.river.add(card3);
                    }
                }, 15L);
            }
        }, 15L);

    }

    public void turn() {
        String[] card1 = main.cardManager.getRandomCard().split("_");
        setMap(getCardMap(card1[0], card1[1]), riverLocations.get(3));
        main.gameManager.river.add(card1);
    }

    public void river() {
        String[] card1 = main.cardManager.getRandomCard().split("_");
        setMap(getCardMap(card1[0], card1[1]), riverLocations.get(4));
        main.gameManager.river.add(card1);
    }

    public void revealCards() {
        for(Player p : main.gameManager.debout){
            int place = 0;
            for(int pl : main.gameManager.places.keySet()){
                if(main.gameManager.places.get(pl) == p){
                    place = pl;
                }
            }
            int finalPlace = place;
            Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                @Override
                public void run() {
                    setMap(getCardMap(main.gameManager.cards.get(p).get(0)[0], main.gameManager.cards.get(p).get(0)[1]), playerCardsLocations.get(finalPlace).get(0));
                    p.getInventory().setItemInMainHand(null);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                        @Override
                        public void run() {
                            setMap(getCardMap(main.gameManager.cards.get(p).get(1)[0], main.gameManager.cards.get(p).get(1)[1]), playerCardsLocations.get(finalPlace).get(1));
                            p.getInventory().setItemInOffHand(null);
                        }
                    }, 20L);
                }
            }, 15L);
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
            @Override
            public void run() {
                main.gameManager.calculScore();
            }
        }, 60L);
    }
}
