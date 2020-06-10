package ch.dams333.pokerCraft.stats;

import org.bukkit.entity.Player;

import java.util.UUID;

public class Statistique {

    private UUID playerUUID;
    private int games;
    private int rounds;
    private int winsGame;
    private int winsRound;
    private int couches;
    private int check;
    private int follow;
    private int relance;
    private int tapis;
    private int completeRound;

    public void addGame(){
        this.games++;
    }
    public void addRound(){
        this.rounds++;
    }
    public void addGameWin(){
        this.winsGame++;
    }
    public void addRoundWin(){
        this.winsRound++;
    }
    public void addCouche(){
        couches++;
    }
    public void addCheck(){
        check++;
    }
    public void addFollow(){
        follow++;
    }
    public void addRelance(){
        relance++;
    }
    public void addTapis(){
        tapis++;
    }

    public int getCompleteRound() {
        return completeRound;
    }

    public void addCompleteRound() {completeRound++;}

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public int getGames() {
        return games;
    }

    public int getRounds() {
        return rounds;
    }

    public int getWinsGame() {
        return winsGame;
    }

    public int getWinsRound() {
        return winsRound;
    }

    public int getCouches() {
        return couches;
    }

    public int getCheck() {
        return check;
    }

    public int getFollow() {
        return follow;
    }

    public int getRelance() {
        return relance;
    }

    public int getTapis() {
        return tapis;
    }



    public Statistique(Player p) {
        this.playerUUID = p.getUniqueId();
        this.games = 0;
        this.rounds = 0;
        this.winsGame = 0;
        this.winsRound = 0;
        this.couches = 0;
        this.check = 0;
        this.follow = 0;
        this.relance = 0;
        this.tapis = 0;
        this.completeRound = 0;
    }

    public Statistique(UUID playerUUID, int games, int rounds, int winsGame, int winsRound, int couches, int check, int follow, int relance, int tapis, int completeRound) {

        this.playerUUID = playerUUID;
        this.games = games;
        this.rounds = rounds;
        this.winsGame = winsGame;
        this.winsRound = winsRound;
        this.couches = couches;
        this.check = check;
        this.follow = follow;
        this.relance = relance;
        this.tapis = tapis;
        this.completeRound = completeRound;
    }

    public void removeRound() {
        rounds--;
    }
}
