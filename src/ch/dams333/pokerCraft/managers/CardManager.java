package ch.dams333.pokerCraft.managers;

import ch.dams333.pokerCraft.Poker;

import java.util.ArrayList;
import java.util.List;

public class CardManager {
    Poker main;
    public CardManager(Poker poker) {
        this.main = poker;
        cards = new ArrayList<>();
    }

    private List<String> cards;

    public void resetDeck(){
        cards = new ArrayList<>();
        cards.add("pi_2");
        cards.add("pi_3");
        cards.add("pi_4");
        cards.add("pi_5");
        cards.add("pi_6");
        cards.add("pi_7");
        cards.add("pi_8");
        cards.add("pi_9");
        cards.add("pi_10");
        cards.add("pi_j");
        cards.add("pi_q");
        cards.add("pi_k");
        cards.add("pi_a");
        cards.add("co_2");
        cards.add("co_3");
        cards.add("co_4");
        cards.add("co_5");
        cards.add("co_6");
        cards.add("co_7");
        cards.add("co_8");
        cards.add("co_9");
        cards.add("co_10");
        cards.add("co_j");
        cards.add("co_q");
        cards.add("co_k");
        cards.add("co_a");
        cards.add("tr_2");
        cards.add("tr_3");
        cards.add("tr_4");
        cards.add("tr_5");
        cards.add("tr_6");
        cards.add("tr_7");
        cards.add("tr_8");
        cards.add("tr_9");
        cards.add("tr_10");
        cards.add("tr_j");
        cards.add("tr_q");
        cards.add("tr_k");
        cards.add("tr_a");
        cards.add("ca_2");
        cards.add("ca_3");
        cards.add("ca_4");
        cards.add("ca_5");
        cards.add("ca_6");
        cards.add("ca_7");
        cards.add("ca_8");
        cards.add("ca_9");
        cards.add("ca_10");
        cards.add("ca_j");
        cards.add("ca_q");
        cards.add("ca_k");
        cards.add("ca_a");
    }

    public String getRandomCard(){
        int random = main.API.random(0, cards.size() - 1);
        String card = cards.get(random);
        cards.remove(card);
        return card;
    }

}
