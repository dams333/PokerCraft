package ch.dams333.pokerCraft.combinaison;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hand {
    private List<Card> cards;

    public Hand(List<Card> cards) {
        this.cards = cards;
    }


    private int valueToNumber(String value){
        if(value.equalsIgnoreCase("j")){
            return 11;
        }
        if(value.equalsIgnoreCase("q")){
            return 12;
        }
        if(value.equalsIgnoreCase("k")){
            return 13;
        }
        if(value.equalsIgnoreCase("a")){
            return 14;
        }
        return Integer.parseInt(value);
    }

    private int getBestCardOfHand(){
        Card best = null;
        for(Card card : cards){
            if(best != null){
                if(valueToNumber(card.getValue()) > valueToNumber(best.getValue())){
                    best = card;
                }
            }else{
                best = card;
            }
        }
        return valueToNumber(best.getValue());
    }


    public Combinaison getBestCombinaison(){
        if(isQuinteFlushRoyal() != null){
            return isQuinteFlushRoyal();
        }else if(isQuinteFlush() != null){
            return isQuinteFlush();
        }else if(isQuarter() != null){
            return isQuarter();
        }else if(isFull() != null){
            return isFull();
        }else if(isFlush() != null){
            return isFlush();
        }else if(isQuinte() != null){
            return isQuinte();
        }else if(isBrelan() != null){
            return isBrelan();
        }else if(isTwoPaire() != null){
            return isTwoPaire();
        }else if(isPaire() != null){
            return isPaire();
        }else {
            return new Combinaison("card", getBestCardOfHand(), getBestCardOfHand());
        }
    }

    private Combinaison isPaire() {
        for(Card card1 : cards){
            for(Card card2 : cards){
                if(card1.getValue().equals(card2.getValue()) && !card1.getColor().equals(card2.getColor())){
                    return  new Combinaison("paire", valueToNumber(card1.getValue()), getBestCardOfHand());
                }
            }
        }
        return null;
    }

    private Combinaison isTwoPaire() {
        List<Card> cards = new ArrayList<>(this.cards);
        Combinaison paire1 = null;
        first:
        for(Card card1 : cards){
            for(Card card2 : cards){
                if(card1.getValue().equals(card2.getValue()) && !card1.getColor().equals(card2.getColor())){
                    paire1 = new Combinaison("paire", valueToNumber(card1.getValue()), getBestCardOfHand());
                    cards.remove(card1);
                    cards.remove(card2);
                    break first;
                }
            }
        }
        if(paire1 != null) {
            for(Card card1 : cards){
                for(Card card2 : cards){
                    if(card1.getValue().equals(card2.getValue()) && !card1.getColor().equals(card2.getColor())){
                        Combinaison paire2 = new Combinaison("paire", valueToNumber(card1.getValue()), getBestCardOfHand());
                        if (paire1.getTo() > paire2.getTo()) {
                            return new Combinaison("2paire", paire1.getTo(), getBestCardOfHand());
                        } else {
                            return new Combinaison("2paire", paire2.getTo(), getBestCardOfHand());
                        }
                    }
                }
            }
        }
        return null;
    }

    private Combinaison isBrelan() {
        for(Card card1 : cards){
            for(Card card2 : cards){
                if(card1.getValue().equals(card2.getValue()) && !card1.getColor().equals(card2.getColor())){
                    for(Card card3 : cards) {
                        if (card1.getValue() == card3.getValue() && card1.getColor() != card3.getColor() && card3.getColor() != card2.getColor()) {
                            return new Combinaison("brelan", valueToNumber(card1.getValue()), getBestCardOfHand());
                        }
                    }
                }
            }
        }
        return null;
    }

    private Combinaison isQuinte() {
        List<String> allQuinte = Arrays.asList("a2345", "23456", "34567", "45678", "56789", "678910", "78910j", "891jq", "91jqk", "1jqka");
        for(String quinte : allQuinte){
            int numberinQuinte = 0;
            char best = quinte.charAt(4);
            if(best == '1') best = 10;
            for(Card card : cards){
                String rank = card.getValue();
                if(rank.equals("10")) rank = "1";
                if(quinte.contains(rank)){
                    numberinQuinte = numberinQuinte + 1;
                    quinte = quinte.replace(rank, "");
                }
            }
            if(numberinQuinte >= 5){
                return new Combinaison("quinte", valueToNumber(String.valueOf(best)), getBestCardOfHand());
            }
        }
        return null;
    }

    private Combinaison isFlush() {
        String best = null;
        for(Card cardTest : cards){
            best = cardTest.getValue();
            int numberInFlush = 1;
            for(Card card : cards){
                if(cardTest.getColor().equals(card.getColor()) && !cardTest.getValue().equals(card.getValue())){
                    numberInFlush = numberInFlush + 1;
                    if(valueToNumber(best) < valueToNumber(card.getValue())){
                        best = card.getValue();
                    }
                }
            }
            if(numberInFlush >= 5){
                return new Combinaison("flush", valueToNumber(best), getBestCardOfHand());
            }
        }
        return null;
    }

    private Combinaison isFull() {
        List<Card> cards = new ArrayList<>(this.cards);
        Combinaison brelan = null;
        first:
        for(Card card1 : cards){
            for(Card card2 : cards){
                if(card1.getValue().equals(card2.getValue()) && !card1.getColor().equals(card2.getColor())){
                    for(Card card3 : cards) {
                        if (card1.getValue().equals(card3.getValue()) && !card1.getColor().equals(card3.getColor()) && !card2.getColor().equals(card3.getColor())) {
                            brelan = new Combinaison("brelan", valueToNumber(card1.getValue()), getBestCardOfHand());
                            cards.remove(card1);
                            cards.remove(card2);
                            cards.remove(card3);
                            break first;
                        }
                    }
                }
            }
        }
        if(brelan != null){
            for(Card card1 : cards){
                for(Card card2 : cards){
                    if(card1.getValue().equals(card2.getValue()) && !card1.getColor().equals(card2.getColor())){
                        Combinaison paire = new Combinaison("paire", valueToNumber(card1.getValue()), getBestCardOfHand());
                        if(paire.getTo() > brelan.getTo()){
                            return new Combinaison("full", paire.getTo(), getBestCardOfHand());
                        }else{
                            return new Combinaison("full", brelan.getTo(), getBestCardOfHand());
                        }
                    }
                }
            }
        }
        return null;
    }

    private Combinaison isQuarter() {
        for(Card card1 : cards){
            for(Card card2 : cards){
                if(card1.getValue().equals(card2.getValue()) && !card1.getColor().equals(card2.getColor())){
                    for(Card card3 : cards) {
                        if (card1.getValue().equals(card3.getValue()) && !card1.getColor().equals(card3.getColor()) && !card2.getColor().equals(card3.getColor())) {
                            for(Card card4 : cards) {
                                if (card1.getValue().equals(card4.getValue()) && !card1.getColor().equals(card4.getColor()) && !card4.getColor().equals(card2.getColor()) && !card4.getColor().equals(card3.getColor())) {
                                    return new Combinaison("quarter", valueToNumber(card1.getValue() ), getBestCardOfHand());
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private Combinaison isQuinteFlush() {
        String best = null;
        Combinaison flush = null;
        for(Card cardTest : cards){
            best = cardTest.getValue();
            int numberInFlush = 1;
            for(Card card : cards){
                if(cardTest.getColor().equals(card.getColor()) && !cardTest.getValue().equals(card.getValue())){
                    numberInFlush = numberInFlush + 1;
                    if(valueToNumber(card.getValue()) > valueToNumber(cardTest.getValue())){
                        best = card.getValue();
                    }
                }
            }
            if(numberInFlush >= 5){
                flush = new Combinaison("flush", valueToNumber(best), getBestCardOfHand());
                break;
            }
        }
        if(flush != null){
            List<String> allQuinte = Arrays.asList("a2345", "23456", "34567", "45678", "56789", "678910", "78910j", "891jq", "91jqk", "1jqka");
            for(String quinte : allQuinte){
                int numberinQuinte = 0;
                char best2 = quinte.charAt(4);
                if(best2 == '1') best2 = 10;
                for(Card card : cards){
                    String rank = card.getValue();
                    if(rank.equals("10")) rank = "1";
                    if(quinte.contains(rank)){
                        numberinQuinte = numberinQuinte + 1;
                        quinte = quinte.replace(rank, "");
                    }
                }
                if(numberinQuinte >= 5){
                    Combinaison quinte2 = new Combinaison("quinte", valueToNumber(String.valueOf(best2)), getBestCardOfHand());
                    if(quinte2.getTo() > flush.getTo()){
                        return new Combinaison("quinteFlush", quinte2.getTo(), getBestCardOfHand());
                    }else{
                        return new Combinaison("quinteFlush", flush.getTo(), getBestCardOfHand());
                    }
                }
            }
        }
        return null;
    }

    private Combinaison isQuinteFlushRoyal() {
        Combinaison flush = null;
        for(Card cardTest : cards){
            int numberInFlush = 1;
            for(Card card : cards){
                if(cardTest.getColor().equals(card.getColor()) && !cardTest.getValue().equals(card.getValue())){
                    numberInFlush = numberInFlush + 1;
                }
            }
            if(numberInFlush >= 5){
                flush = new Combinaison("flush", valueToNumber("a"), getBestCardOfHand());
                break;
            }
        }
        if(flush != null){
            String quinte = "1jqka";
            int numberinQuinte = 0;
            for(Card card : cards){
                String rank = card.getValue();
                if(rank.equals("10")) rank = "1";
                if(quinte.contains(rank)){
                    numberinQuinte = numberinQuinte + 1;
                    quinte = quinte.replace(rank, "");
                }
            }
            if(numberinQuinte >= 5){
                return new Combinaison("quinteFlushRoyal", 14, 14);
            }
        }
        return null;
    }
}
