package ch.dams333.pokerCraft.managers;

import ch.dams333.pokerCraft.Poker;
import ch.dams333.pokerCraft.combinaison.Card;
import ch.dams333.pokerCraft.combinaison.Combinaison;
import ch.dams333.pokerCraft.combinaison.Hand;
import ch.dams333.pokerCraft.states.GameState;
import ch.dams333.pokerCraft.tasks.GameAfficheTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.*;

public class GameManager {
    Poker main;
    public GameManager(Poker poker) {
        this.main = poker;
        debout = new ArrayList<>();
        spawns = new HashMap<>();
        spawns.put(1, new Location(Bukkit.getWorld("world"), -204.39, 81, 115.46, 18, 22));
        spawns.put(2, new Location(Bukkit.getWorld("world"), -207.6, 81, 115.46, -1, 23));
        spawns.put(3, new Location(Bukkit.getWorld("world"), -210.4, 81, 115.46, -30, 25));
        spawns.put(4, new Location(Bukkit.getWorld("world"), -204.39, 81, 123.7, 159, 26));
        spawns.put(5, new Location(Bukkit.getWorld("world"), -207.6, 81, 123.7, -178, 30));
        spawns.put(6, new Location(Bukkit.getWorld("world"), -210.4, 81, 123.7, -150, 30));
        places = new HashMap<>();
        tapis = new HashMap<>();
        cards = new HashMap<>();
        river = new ArrayList<>();
        bided = new HashMap<>();
        armorStands = new HashMap<>();
    }

    public Map<Integer, Player> places;

    public List<Player> debout;

    private Map<Integer, Location> spawns;

    public Map<Player, Integer> tapis;
    public Map<Player, List<String[]>> cards;

    public Map<Player, Integer> bided;

    public Map<Player, ArmorStand> armorStands;

    public List<String[]> river;

    private int bigBlind;

    public int currentBid;

    public int needToEnchere = 0;

    public void startGame() {
        main.setGameState(GameState.STARTING);
        main.mapManager.resetTable();
        main.cardManager.resetDeck();

        int index = 1;
        for(Player p : Bukkit.getOnlinePlayers()){
            debout.add(p);
            p.teleport(spawns.get(index));
            places.put(index, p);
            index++;
        }

        for(Player p : debout){
            p.getInventory().setHeldItemSlot(0);
            String[] card1 = main.cardManager.getRandomCard().split("_");
            p.getInventory().setItemInMainHand(main.mapManager.getCardMap(card1[0], card1[1]));
            String[] card2 = main.cardManager.getRandomCard().split("_");
            p.getInventory().setItemInOffHand(main.mapManager.getCardMap(card2[0], card2[1]));
            tapis.put(p, 1000);
            List<String[]> cards = new ArrayList<>();
            cards.add(card1);
            cards.add(card2);
            this.cards.put(p, cards);
            this.bided.put(p, 0);

            Location spawnArmorStand = p.getLocation();
            spawnArmorStand.add(0, 0, 0);
            ArmorStand armorStand = (ArmorStand) spawnArmorStand.getWorld().spawnEntity(spawnArmorStand, EntityType.ARMOR_STAND);
            armorStand.setCustomNameVisible(true);
            armorStand.setVisible(false);
            armorStand.setInvulnerable(true);
            armorStand.setGravity(false);
            this.armorStands.put(p, armorStand);
        }

        bigBlind = main.API.random(1, debout.size());

        Bukkit.broadcastMessage(main.name + "La big blind est " + places.get(bigBlind).getName());

        currentBid = 20;

        this.bided.put(places.get(bigBlind), 20);
        this.bided.put(places.get(getSmallBlind()), 10);

        removeMoney(places.get(bigBlind), 20);
        removeMoney(places.get(getSmallBlind()), 10);

        new GameAfficheTask(main).runTaskTimer(main, 5, 5);

        Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
            @Override
            public void run() {
                startEnchereRound();
            }
        }, 60L);
    }

    private Integer getSmallBlind() {
        int smallBlind = bigBlind;
        if(smallBlind < 1){
            smallBlind = debout.size();
        }
        return smallBlind;
    }


    private void startEnchereRound(){
        needToEnchere = bigBlind;
        needToEnchere++;
        if(needToEnchere > places.keySet().size()){
            needToEnchere = 1;
        }

        while (!isDebout(needToEnchere)){
            needToEnchere++;
            if(needToEnchere > places.keySet().size()){
                needToEnchere = 1;
            }
        }

        Bukkit.broadcastMessage(main.name + places.get(needToEnchere).getName() + " doit choisir une action");
        haveToChooseAction(places.get(needToEnchere));
    }

    private void haveToChooseAction(Player p) {

        p.sendMessage(" ");
        p.sendMessage(" ");

        p.sendMessage(ChatColor.DARK_GREEN + "======= Actions =======");
        p.sendMessage("       " + ChatColor.LIGHT_PURPLE + "2 " + ChatColor.GRAY + "Se coucher");

        if(this.bided.get(p) >= currentBid){
            p.sendMessage("       " + ChatColor.LIGHT_PURPLE + "3 " + ChatColor.GRAY + "Checker");
        }
        if(this.bided.get(p) < currentBid){
            p.sendMessage("       " + ChatColor.LIGHT_PURPLE  + "4 " + ChatColor.GRAY + "Suivre");
        }
        p.sendMessage("       " + ChatColor.LIGHT_PURPLE + "5 " + ChatColor.GRAY + "Relancer");

        p.sendMessage(" ");

        p.sendMessage(ChatColor.LIGHT_PURPLE + "Appuyez sur le chiffre correspondant");

        p.sendMessage(" ");
        p.sendMessage(" ");

    }

    private boolean isDebout(int slot){
        Player p = places.get(slot);
        return debout.contains(p);
    }

    private void removeMoney(Player p, int money){
        int tapis = this.tapis.get(p);
        tapis = tapis - money;
        this.tapis.put(p, tapis);
    }

    private void addMoney(Player p, int money){
        int tapis = this.tapis.get(p);
        tapis = tapis + money;
        this.tapis.put(p, tapis);
    }

    private void addBid(Player p, int money){
        int bid = this.bided.get(p);
        bid = bid + money;
        this.bided.put(p, bid);
    }

    public void selectAction(Player p, int slot) {
        if(slot == 1){
            this.couche(p);
        }
        if(slot == 2){
            if(this.bided.get(p) >= currentBid){
                this.check(p);
            }else{
                p.sendMessage(ChatColor.RED + "Votre mise est inférieure à la mise maximale. Vous ne pouvez pas checker !");
            }
        }
        if(slot == 3){
            if(this.bided.get(p) < currentBid){
                this.follow(p);
            }else{
                p.sendMessage(ChatColor.RED + "Votre mise est déjà maximale. Vous ne pouvez pas suivre !");
            }
        }
        if(slot == 4){
            this.relance(p);
            p.sendMessage(ChatColor.BLUE + "Veuillez écrire le montant dans le chat !");
        }
    }

    public Player relance;

    private void relance(Player p) {
        this.relance = p;
    }

    private void follow(Player p) {
        if(this.tapis.get(p) > (this.currentBid - this.bided.get(p))){
            Bukkit.broadcastMessage(main.name + p.getName() + " suit de " + (this.currentBid - this.bided.get(p)));
            removeMoney(p, (this.currentBid - this.bided.get(p)));
            addBid(p, (this.currentBid - this.bided.get(p)));
        }else{
            Bukkit.broadcastMessage(main.name + p.getName() + " fait tapis de " + this.tapis.get(p));
            tapis.put(p, 0);
            addBid(p, this.tapis.get(p));
        }
        this.nextEnchere();
    }

    private void check(Player p) {
        Bukkit.broadcastMessage(main.name + p.getName() + " check");
        this.nextEnchere();
    }

    private void couche(Player p) {
        this.debout.remove(p);
        Bukkit.broadcastMessage(main.name + p.getName() + " se couche");
        main.mapManager.removePlayerCards(needToEnchere);
        p.getInventory().clear();
        p.updateInventory();
        if(!this.testSoloWin()){
            this.nextEnchere();
        }
    }

    private boolean testSoloWin() {
        return false;
    }

    public void hasRelance(int add) {
        if((add + this.bided.get(relance)) > currentBid){
            if(this.tapis.get(relance) > add){
                Bukkit.broadcastMessage(main.name + relance.getName() + " relance de " + add);
                removeMoney(relance, add);
                addBid(relance, add);
                this.currentBid = this.bided.get(relance);
            }else{
                Bukkit.broadcastMessage(main.name + relance.getName() + " fait tapis de " + this.tapis.get(relance));
                addBid(relance, this.tapis.get(relance));
                tapis.put(relance, 0);
                if(this.bided.get(relance) > currentBid){
                    this.currentBid = this.bided.get(relance);
                }
            }
            this.nextEnchere();
        }else{
            relance.sendMessage(ChatColor.RED + "Vous devez envoyer au moins " + (this.currentBid - this.bided.get(relance) + 1) + " pour relancer");
        }
    }

    private void nextEnchere() {
        this.relance = null;
        int nextPlayer = getNextPlayer();
        if(this.bided.get(places.get(nextPlayer)) < this.currentBid){
            needToEnchere = nextPlayer;
            Bukkit.broadcastMessage(main.name + places.get(needToEnchere).getName() + " doit choisir une action");
            haveToChooseAction(places.get(needToEnchere));
        }else{
            Bukkit.broadcastMessage(main.name + "Fin du tour d'enchpre");
            needToEnchere = 0;
            Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                @Override
                public void run() {
                    endTour();
                }
            }, 10L);
        }
    }

    private void endTour() {
        if(main.isGameState(GameState.STARTING)){
            main.setGameState(GameState.FLOP);
            Bukkit.broadcastMessage(main.name + "Révélation du Flop !");
            main.mapManager.flop();
            Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                @Override
                public void run() {
                    startEnchereRound();
                }
            }, 45L);
        }
        else if(main.isGameState(GameState.FLOP)){
            main.setGameState(GameState.TURN);
            Bukkit.broadcastMessage(main.name + "Révélation du Turn !");
            main.mapManager.turn();
            Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                @Override
                public void run() {
                    startEnchereRound();
                }
            }, 15L);
        }
        else if(main.isGameState(GameState.TURN)){
            main.setGameState(GameState.RIVER);
            Bukkit.broadcastMessage(main.name + "Révélation de la River !");
            main.mapManager.river();
            Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                @Override
                public void run() {
                    startEnchereRound();
                }
            }, 15L);
        }
        else if(main.isGameState(GameState.RIVER)){
            Bukkit.broadcastMessage(main.name + "Révélation des cartes !");
            main.mapManager.revealCards();
        }
    }

    private int getNextPlayer() {
        int next = needToEnchere;
        next++;
        if(next > places.keySet().size()){
            next = 1;
        }
        while (!isDebout(next)){
            next++;
            if(next > places.keySet().size()){
                next = 1;
            }
        }
        return next;
    }

    public void calculScore() {
        main.setGameState(GameState.GAME_END);
        Bukkit.broadcastMessage(main.name + "Recherche de la meilleur combinaison de chaque joueur ...");

        Card river1 = new Card(river.get(0)[0], river.get(0)[1]);
        Card river2 = new Card(river.get(1)[0], river.get(1)[1]);
        Card river3 = new Card(river.get(2)[0], river.get(2)[1]);
        Card river4 = new Card(river.get(3)[0], river.get(3)[1]);
        Card river5 = new Card(river.get(4)[0], river.get(4)[1]);

        Map<Player, Combinaison> combinaisons = new HashMap<>();

        for(Player p : debout){
            Card hand1 = new Card(cards.get(p).get(0)[0], cards.get(p).get(0)[1]);
            Card hand2 = new Card(cards.get(p).get(1)[0], cards.get(p).get(1)[1]);

            Hand hand = new Hand(Arrays.asList(river1, river2, river3, river4, river5, hand1, hand2));
            Combinaison best = hand.getBestCombinaison();
            combinaisons.put(p, best);
            Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                @Override
                public void run() {
                    Bukkit.broadcastMessage(main.name + p.getName() + " a " + best.toBeautifulName() + " qui monte " + best.toBeautifulTo() + ". Sa haute carte est " + best.toBeautifulBestCard());
                }
            }, 20L);
        }

        Combinaison bestC = null;
        Player bestP = null;

        for(Player p : combinaisons.keySet()){
            if(bestC == null){
                bestC = combinaisons.get(p);
                bestP = p;
            }else{
                if(combinaisons.get(p).combinaisonPower() > bestC.combinaisonPower()){
                    bestC = combinaisons.get(p);
                    bestP = p;
                }else if(combinaisons.get(p).combinaisonPower() == bestC.combinaisonPower()){
                    if(combinaisons.get(p).getTo() > bestC.getTo()){
                        bestC = combinaisons.get(p);
                        bestP = p;
                    }else if(combinaisons.get(p).getTo() == bestC.getTo()){
                        if(combinaisons.get(p).getHigher() > bestC.getHigher()){
                            bestC = combinaisons.get(p);
                            bestP = p;
                        }
                    }
                }
            }
        }

        Player finalBestP = bestP;
        Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
            @Override
            public void run() {

                debout = new ArrayList<>();
                cards = new HashMap<>();
                river = new ArrayList<>();

                int total = 0;
                for(Player p : bided.keySet()){
                    total += bided.get(p);
                    bided.put(p, 0);
                }

                for(Player p : Bukkit.getOnlinePlayers()){
                    debout.add(p);
                }

                Bukkit.broadcastMessage(main.name + finalBestP.getName() + " a la meilleur combinaison. Il remporte " + total);

                addMoney(finalBestP, total);
                currentBid = 0;

                Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                    @Override
                    public void run() {
                        main.mapManager.resetTable();
                        Bukkit.broadcastMessage(main.name + "Un nouveau tour va commencer...");
                       newRound();
                    }
                }, 200L);

            }
        }, 80L);
    }

    private void newRound(){
        main.setGameState(GameState.STARTING);

        for(Player p : debout) {
            p.getInventory().setHeldItemSlot(0);
            String[] card1 = main.cardManager.getRandomCard().split("_");
            p.getInventory().setItemInMainHand(main.mapManager.getCardMap(card1[0], card1[1]));
            String[] card2 = main.cardManager.getRandomCard().split("_");
            p.getInventory().setItemInOffHand(main.mapManager.getCardMap(card2[0], card2[1]));
            List<String[]> cards = new ArrayList<>();
            cards.add(card1);
            cards.add(card2);
            this.cards.put(p, cards);
            bided.put(p, 0);
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
            @Override
            public void run() {
                bigBlind = bigBlind + 1;
                if(bigBlind > debout.size()){
                    bigBlind = 1;
                }
                Bukkit.broadcastMessage(main.name + "La nouvelle big blind est " + places.get(bigBlind).getName());
                currentBid = 20;

                bided.put(places.get(bigBlind), 20);
                bided.put(places.get(getSmallBlind()), 10);

                removeMoney(places.get(bigBlind), 20);
                removeMoney(places.get(getSmallBlind()), 10);

                Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                    @Override
                    public void run() {
                        startEnchereRound();
                    }
                }, 20L);
            }
        }, 50L);

    }
}
