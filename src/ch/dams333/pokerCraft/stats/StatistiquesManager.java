package ch.dams333.pokerCraft.stats;

import ch.dams333.pokerCraft.Poker;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StatistiquesManager {
    public StatistiquesManager(Poker main) {
        this.main = main;
        statistiques = new ArrayList<>();
    }

    private List<Statistique> statistiques;
    private Poker main;

    public void serialize(){

        for(Statistique stat : this.statistiques){
            ConfigurationSection sec = main.getConfig().createSection(stat.getPlayerUUID().toString());
            sec.set("Games", stat.getGames());
            sec.set("Rounds", stat.getRounds());
            sec.set("WinGames", stat.getWinsGame());
            sec.set("WinRounds", stat.getWinsRound());
            sec.set("Couches", stat.getCouches());
            sec.set("Check", stat.getCheck());
            sec.set("Follow", stat.getFollow());
            sec.set("Relance", stat.getRelance());
            sec.set("Tapis", stat.getTapis());
            sec.set("CompleteRound", stat.getCompleteRound());
        }

        main.saveConfig();
    }
    public void deserialize(){
        for(String playerUUID : main.getConfig().getKeys(false)){
            ConfigurationSection sec = main.getConfig().getConfigurationSection(playerUUID);
            int games = sec.getInt("Games");
            int rounds = sec.getInt("Rounds");
            int winGames = sec.getInt("WinGames");
            int winRounds = sec.getInt("WinRounds");
            int couches = sec.getInt("Couches");
            int check = sec.getInt("Check");
            int follow = sec.getInt("Follow");
            int relance = sec.getInt("Relance");
            int tapis = sec.getInt("Tapis");
            int completeRound = sec.getInt("CompleteRound");
            statistiques.add(new Statistique(UUID.fromString(playerUUID), games, rounds, winGames, winRounds, couches, check, follow, relance, tapis, completeRound));
        }
    }

    public void createStat(Player p){
        for(Statistique stat : this.statistiques){
            if(stat.getPlayerUUID().equals(p.getUniqueId())){
                return;
            }
        }
        this.statistiques.add(new Statistique(p));
    }

    public Statistique getPlayerStat(Player p){
        for(Statistique stat : this.statistiques){
            if(stat.getPlayerUUID().equals(p.getUniqueId())){
                return stat;
            }
        }
        return null;
    }

    public void newGame(Player p) {
        Statistique stat = getPlayerStat(p);
        statistiques.remove(stat);
        stat.addGame();
        statistiques.add(stat);
    }

    public void newRound(Player p) {
        Statistique stat = getPlayerStat(p);
        statistiques.remove(stat);
        stat.addRound();
        statistiques.add(stat);
    }

    public void makeTapis(Player p) {
        Statistique stat = getPlayerStat(p);
        statistiques.remove(stat);
        stat.addTapis();
        statistiques.add(stat);
    }

    public void makeFollow(Player p) {
        Statistique stat = getPlayerStat(p);
        statistiques.remove(stat);
        stat.addFollow();
        statistiques.add(stat);
    }

    public void makeCheck(Player p) {
        Statistique stat = getPlayerStat(p);
        statistiques.remove(stat);
        stat.addCheck();
        statistiques.add(stat);
    }

    public void makeCouche(Player p) {
        Statistique stat = getPlayerStat(p);
        statistiques.remove(stat);
        stat.addCouche();
        statistiques.add(stat);
    }

    public void addRoundWin(Player p) {
        Statistique stat = getPlayerStat(p);
        statistiques.remove(stat);
        stat.addRoundWin();
        statistiques.add(stat);
    }

    public void makeRelance(Player p) {
        Statistique stat = getPlayerStat(p);
        statistiques.remove(stat);
        stat.addRelance();
        statistiques.add(stat);
    }

    public void addGameWin(Player p) {
        Statistique stat = getPlayerStat(p);
        statistiques.remove(stat);
        stat.addGameWin();
        statistiques.add(stat);
    }

    public void addCompleteRound(Player p) {
        Statistique stat = getPlayerStat(p);
        statistiques.remove(stat);
        stat.addCompleteRound();
        statistiques.add(stat);
    }

    public boolean pseudoHasStat(String pseudo) {
        if(Bukkit.getPlayer(pseudo) == null) {
            for (Statistique statistique : this.statistiques) {
                if (Bukkit.getOfflinePlayer(pseudo).getUniqueId().equals(statistique.getPlayerUUID())) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public Statistique getPseudoStat(String pseudo) {
        if(Bukkit.getPlayer(pseudo) == null) {
            for (Statistique statistique : this.statistiques) {
                if (Bukkit.getOfflinePlayer(pseudo).getUniqueId().equals(statistique.getPlayerUUID())) {
                    return statistique;
                }
            }
            return null;
        }
        for (Statistique statistique : this.statistiques) {
            if (Bukkit.getPlayer(pseudo).getUniqueId().equals(statistique.getPlayerUUID())) {
                return statistique;
            }
        }
        return null;
    }

    public void removeRound(Player p) {
        Statistique stat = getPlayerStat(p);
        statistiques.remove(stat);
        stat.removeRound();
        statistiques.add(stat);
    }
}
